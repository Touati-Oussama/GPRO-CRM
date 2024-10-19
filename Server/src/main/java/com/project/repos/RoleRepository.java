package com.project.repos;

import org.springframework.data.jpa.repository.JpaRepository;


import com.project.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByRole(String role);

}
