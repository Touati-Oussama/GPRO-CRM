package com.project.repos;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Projet;
import com.project.entities.Sepicialite;
import com.project.entities.Societe;



public interface ProjetRepository extends JpaRepository<Projet, Long> {

	List<Projet> findBySociete(Societe societe); 
	Boolean existsBySpecialite(Sepicialite specialite);
	Boolean existsBySociete(Societe societe);
	Boolean existsByDesignation(String designation);
	Projet findByDesignation(String designation);
}
