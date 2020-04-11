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
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT u.userId FROM User u WHERE u.username=:username")
    UUID getIdByUsername(@Param("username") String username);

    @Query("SELECT u.boards FROM User u WHERE u.userId=:userId")
    List<Board> findBoardById(UUID userId);

    public static final String FIND_USER = "SELECT userId, name, username, email, password FROM users";

    @Query(value = FIND_USER, nativeQuery = true)
    public List<UserDTO> findAllUsers();
}