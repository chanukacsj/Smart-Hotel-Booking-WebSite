package org.example.smarthotelbookingwebsite.service;
import org.example.smarthotelbookingwebsite.dto.UserDTO;

import java.util.List;


public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);
    void deleteUser(String email);
    void updateUserRole(String email, String newRole);
    List<UserDTO> getAll();
    boolean allReadyUsedEmail(String email);
}