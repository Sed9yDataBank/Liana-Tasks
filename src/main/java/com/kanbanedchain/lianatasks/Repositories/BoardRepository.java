package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {

    @Query("SELECT u.users FROM Board u WHERE u.boardId=:boardId")
    List<User> findUserByBoardsId(UUID boardId);

    List<Board> findByAdmin(UUID admin);
}
