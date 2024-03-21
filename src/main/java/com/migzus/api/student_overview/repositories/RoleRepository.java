package com.migzus.api.student_overview.repositories;

import com.migzus.api.student_overview.models.Role;
import com.migzus.api.student_overview.models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleType name);
}
