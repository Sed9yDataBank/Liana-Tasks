package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.Role;
import com.kanbanedchain.lianatasks.Models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(RoleName roleName);
}
