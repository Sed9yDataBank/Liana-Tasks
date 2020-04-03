package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.DTOs.UserDTO;
import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT u.id FROM User u WHERE u.username=:username")
    long getIdByUsername(@Param("username") String username);

    public static final String FIND_USER = "SELECT id, name, username, email, password FROM users";

    @Query(value = FIND_USER, nativeQuery = true)
    public List<UserDTO> findAllUsers();
}