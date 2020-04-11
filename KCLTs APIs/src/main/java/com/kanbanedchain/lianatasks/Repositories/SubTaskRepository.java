package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.SubTask;
import com.kanbanedchain.lianatasks.Models.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, UUID> {

    Optional<SubTask> findSubTaskByStatus(TaskStatus status);

    Optional<SubTask> findSubTaskByDeadline(LocalDateTime deadline);

    @Query("SELECT t FROM SubTask t ORDER BY deadline ASC")
    public Stream<SubTask> listAllSubTasksByDeadline();

    @Query("SELECT u FROM SubTask u WHERE u.status = COMPLETED")
    Collection<SubTask> findAllCompletedSubTasks();
}
