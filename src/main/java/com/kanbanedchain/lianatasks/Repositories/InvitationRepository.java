package com.kanbanedchain.lianatasks.Repositories;

import java.util.List;
import java.util.UUID;

import com.kanbanedchain.lianatasks.Models.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long>{

    Boolean existsByUid(Long uid);

    @Query("SELECT i.pid FROM Invitations i WHERE i.uid=:uid")
    Long getPidByUid(@Param("uid") Long uid);

    @Query("SELECT i.pid FROM Invitations i WHERE i.uid=:uid")
    List<Long> findByUid(Long uid);

    List<Invitation> findByPid(Long pid);
}
