package com.lokaly.repository;

import com.lokaly.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, String> {
}
