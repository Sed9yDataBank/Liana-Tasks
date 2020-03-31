package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findBoardByTitle(String title);

    @Query("SELECT t FROM Task t ORDER BY user_id ASC")
    public Stream<Board> listAllBoardsByUserId();
}
