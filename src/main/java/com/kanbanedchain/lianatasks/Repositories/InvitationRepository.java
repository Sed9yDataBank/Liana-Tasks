package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, UUID>{

    Boolean existsByUserId(UUID userId);

    @Query("SELECT i.passId FROM Invitation i WHERE i.userId=:userId")
    UUID getPassIdByUserId(@Param("userId") UUID userId);

    @Query("SELECT i.passId FROM Invitation i WHERE i.userId=:userId")
    List<UUID> findByUserId(UUID userId);

    List<Invitation> findByPassId(UUID passId);
}
