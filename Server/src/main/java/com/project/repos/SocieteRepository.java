package com.project.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entities.Societe;
import com.project.entities.User;



public interface SocieteRepository extends JpaRepository<Societe, Long> {

	Societe findByName(String name);
	//Boolean existsById(Long id);
	Boolean existsByName(String name);
	
	
	List<Societe> findByResponsable(User responsable);
	
	@Query("SELECT s from Societe s WHERE s.responsable = ?1")
	Societe getByResponsable(User responsbale);
}
