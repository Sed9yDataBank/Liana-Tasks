package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
}
