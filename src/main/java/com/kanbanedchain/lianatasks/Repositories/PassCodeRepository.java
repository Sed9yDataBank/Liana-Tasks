package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.PassCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PassCodeRepository extends JpaRepository<PassCode, UUID> {

    @Query("SELECT p.pid FROM PassCode p WHERE p.code=:code")
    long getPidByCode(@Param("code") String code);
}
