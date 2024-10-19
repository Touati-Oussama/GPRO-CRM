package com.project.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.TypeReclamation;

public interface TypeReclamationRepository extends JpaRepository<TypeReclamation, Long> {

	Boolean existsByType(String type);
}
