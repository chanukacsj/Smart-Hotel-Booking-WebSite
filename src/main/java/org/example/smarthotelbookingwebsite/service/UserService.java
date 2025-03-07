package org.example.smarthotelbookingwebsite.service;


import org.example.smarthotelbookingwebsite.dto.UserDTO;


public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);

    void deleteUser(Long id);

    void updateUserRole(Long id, String newRole);
}