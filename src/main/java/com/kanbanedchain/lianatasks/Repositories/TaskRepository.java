package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
