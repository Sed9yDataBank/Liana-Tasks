package com.kanbanedchain.lianatasks.Repositories;

import com.kanbanedchain.lianatasks.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
