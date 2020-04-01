package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
