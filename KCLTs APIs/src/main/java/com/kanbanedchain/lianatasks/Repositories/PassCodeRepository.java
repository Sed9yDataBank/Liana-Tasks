package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.PassCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PassCodeRepository extends JpaRepository<PassCode, UUID> {

    UUID getPassIdByCode(String code);
}
