package org.example.smarthotelbookingwebsite.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.smarthotelbookingwebsite.entity.User;
import org.example.smarthotelbookingwebsite.dto.UserDTO;
import org.example.smarthotelbookingwebsite.repo.UserRepository;
import org.example.smarthotelbookingwebsite.service.UserService;
import org.example.smarthotelbookingwebsite.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    public void deleteUser(Long id) {
        if (!userRepository.existsById(String.valueOf(id))) {
            throw new RuntimeException("User not found");

        }
        userRepository.deleteById(String.valueOf(id));

    }

    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//            userDTO.setRole("USER");
            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }
    @Override
    public void updateUserRole(Long userId, String newRole) {
        User user = userRepository.findById(String.valueOf(userId))
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

        user.setRole(newRole); // Update only the role
        userRepository.save(user); // Save the updated user
    }

}
