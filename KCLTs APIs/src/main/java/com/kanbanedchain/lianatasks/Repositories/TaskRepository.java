package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    Optional<Task> findByTitle(String title);

    @Query("SELECT t FROM Task t ORDER BY status ASC")
    public Stream<Task> listAllTasksByStatus();
}
