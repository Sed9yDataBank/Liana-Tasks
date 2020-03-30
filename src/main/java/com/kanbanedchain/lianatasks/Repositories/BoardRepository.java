package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
