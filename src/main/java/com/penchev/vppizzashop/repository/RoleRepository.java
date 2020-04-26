package com.penchev.vppizzashop.repository;

import com.penchev.vppizzashop.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByAuthority(String authority);
}
