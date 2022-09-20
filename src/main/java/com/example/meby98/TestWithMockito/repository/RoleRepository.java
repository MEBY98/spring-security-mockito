package com.example.meby98.TestWithMockito.repository;

import com.example.meby98.TestWithMockito.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
