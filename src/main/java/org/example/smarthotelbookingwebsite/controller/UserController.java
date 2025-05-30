package org.example.smarthotelbookingwebsite.controller;

import jakarta.validation.Valid;
import org.example.smarthotelbookingwebsite.dto.AuthDTO;
import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.dto.UserDTO;
import org.example.smarthotelbookingwebsite.entity.User;
import org.example.smarthotelbookingwebsite.service.OtpService;
import org.example.smarthotelbookingwebsite.service.UserService;
import org.example.smarthotelbookingwebsite.service.impl.EmailServiceImpl;
import org.example.smarthotelbookingwebsite.service.impl.OtpServiceImpl;
import org.example.smarthotelbookingwebsite.service.impl.UserServiceImpl;
import org.example.smarthotelbookingwebsite.util.JwtUtil;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userServiceImpl;
    private final OtpServiceImpl otpServiceImpl;
    private final EmailServiceImpl emailServiceImpl;

    public UserController(UserService userService, JwtUtil jwtUtil, UserServiceImpl userServiceImpl, OtpService otpService, OtpServiceImpl otpServiceImpl, EmailServiceImpl emailServiceImpl) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userServiceImpl = userServiceImpl;
        this.otpServiceImpl = otpServiceImpl;
        this.emailServiceImpl = emailServiceImpl;
    }
    @PostMapping("/register/{otp}")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO,
                                                    @PathVariable("otp") String otp) {
        System.out.println("Email: " + userDTO.getEmail());
        System.out.println("OTP: " + otp);
        if (otp==null || otp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(VarList.Bad_Request, "OTP is required!", null));
        }
        try {
            if (otp != null) {
                System.out.println("OTP: " + otp);
                if (!otpServiceImpl.verifyOtp(userDTO.getEmail(), otp)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseDTO(VarList.Bad_Request, "Invalid OTP!", null));
                }
                otpServiceImpl.clearOtp(userDTO.getEmail());
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.Bad_Request, "Invalid OTP!", null));
            }

            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO(userDTO.getEmail(),token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "User registered successfully", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email already in use", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Registration failed", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Error: " + e.getMessage(), null));
        }
    }
    @PostMapping("/send-otp")
    public ResponseEntity<ResponseDTO> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("Email: " + email);
        if (userServiceImpl.allReadyUsedEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(VarList.Bad_Request, "Email is already registered", null));
        }
        String otp = otpServiceImpl.generateOtp(email);
        emailServiceImpl.sendOtpEmail(email, otp);
        return ResponseEntity.ok(new ResponseDTO(VarList.OK, "OTP sent successfully to " + email, null));
    }
    @DeleteMapping(value = "/delete/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity <ResponseDTO> deleteUser(@PathVariable String email,@RequestHeader("Authorization") String token) {

        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        userServiceImpl.deleteUser(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", null));
    }
    @PutMapping("/update/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateUserRole(@PathVariable String email,@RequestHeader("Authorization") String token, @RequestBody Map<String, String> request) {
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        String newRole = request.get("role");

        if (newRole == null || newRole.isEmpty()) {
            return ResponseEntity.status( HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(VarList.Bad_Request, "Role cannot be empty", null));
        }

        userService.updateUserRole(email, newRole);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "User role updated successfully", null));
    }
    @GetMapping("getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager','USER')")
    public ResponseEntity<ResponseDTO> getAllUsers(@RequestHeader("Authorization") String token) {

        String jwt = token.substring(7);

        String username = jwtUtil.getUsernameFromToken(jwt);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "Invalid Token", null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", userService.getAll()));
    }
    @GetMapping("get/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> getUserByEmail(@PathVariable String email,@RequestHeader("Authorization") String token) {
        System.out.println("Email: " + email);
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", userService.getUserByEmail(email)));
    }
    @GetMapping("getName/{Id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseDTO> getUserNameById(@PathVariable Long Id,@RequestHeader("Authorization") String token) {
        System.out.println("Id: " + Id);
        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", userServiceImpl.getUserNameById(Id)));
    }

}
