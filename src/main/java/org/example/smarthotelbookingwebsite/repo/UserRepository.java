package org.example.smarthotelbookingwebsite.repo;

import org.example.smarthotelbookingwebsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User,String> {

    User findByEmail(String userName);

    boolean existsByEmail(String userName);

    int deleteByEmail(String userName);

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Long findIdByEmailADD(@Param("email") String email);
}