package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.SubTask;
import com.kanbanedchain.lianatasks.Models.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
    Optional<SubTask> findSubTaskByStatus(TaskStatus status);

    @Query("SELECT t FROM SubTask t ORDER BY deadline ASC")
    public Stream<SubTask> listAllSubTasksByDeadline();

}
