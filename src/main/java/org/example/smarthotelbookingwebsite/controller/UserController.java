package org.example.smarthotelbookingwebsite.controller;

import jakarta.validation.Valid;
import org.example.smarthotelbookingwebsite.dto.AuthDTO;
import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
import org.example.smarthotelbookingwebsite.dto.UserDTO;
import org.example.smarthotelbookingwebsite.service.UserService;
import org.example.smarthotelbookingwebsite.service.impl.UserServiceImpl;
import org.example.smarthotelbookingwebsite.util.JwtUtil;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userServiceImpl;

    //constructor injection
    public UserController(UserService userService, JwtUtil jwtUtil, UserServiceImpl userServiceImpl) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userServiceImpl = userServiceImpl;
    }
    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            int res = userService.saveUser(userDTO);
            System.out.println(userDTO.getUsername() + " " + userDTO.getEmail() + " " + userDTO.getRole() + " " + userDTO.getPassword());
            switch (res) {
                case VarList.Created -> {
                    System.out.println("Created");
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(userDTO.getEmail());
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/delete/{email}")
    public ResponseEntity <ResponseDTO> deleteUser(@PathVariable String email) {
        userServiceImpl.deleteUser(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", null));
    }
    @CrossOrigin(origins = "*")
    @PutMapping("/update/{email}")
    public ResponseEntity<ResponseDTO> updateUserRole(@PathVariable String email, @RequestBody Map<String, String> request) {
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
    public ResponseEntity<ResponseDTO> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", userService.getAll()));
    }
}
