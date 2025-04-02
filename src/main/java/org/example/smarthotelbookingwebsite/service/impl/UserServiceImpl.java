package org.example.smarthotelbookingwebsite.service.impl;

import org.example.smarthotelbookingwebsite.entity.User;
import org.example.smarthotelbookingwebsite.dto.UserDTO;
import org.example.smarthotelbookingwebsite.repo.UserRepository;
import org.example.smarthotelbookingwebsite.service.UserService;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return modelMapper.map(user,UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    @Override
    public UserDTO searchUser(String username) {
        if (userRepository.existsByEmail(username)) {
            User user=userRepository.findByEmail(username);
            return modelMapper.map(user,UserDTO.class);
        } else {
            return null;
        }
    }

    @Override
    public void deleteUser(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("User not found");

        }
        userRepository.deleteByEmail(email);

    }

    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            System.out.println("Email Already Used");
            return VarList.Not_Acceptable;
        } else {
            System.out.println("Created");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userDTO.setRole("USER");
            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }
    @Override
    public void updateUserRole(String email, String newRole) {
        User user = userRepository.findByEmail(String.valueOf(email));

        user.setRole(newRole); // Update only the role
        userRepository.save(user); // Save the updated user
    }

    @Override
    public List<UserDTO> getAll() {
       return modelMapper.map(userRepository.findAll(),new TypeToken<List<UserDTO>>() {}.getType());
    }

    @Override
    public boolean allReadyUsedEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return modelMapper.map(user, UserDTO.class);
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    @Override
    public UserDTO getUserNameById(Long id) {
        User user = userRepository.findById(String.valueOf(id)).orElse(null);
        if (user != null) {
            return modelMapper.map(user, UserDTO.class);
        } else {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
    }
}
