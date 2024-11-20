package com.udyata.linentrack.linentrack.repository.role;

import com.udyata.linentrack.linentrack.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}