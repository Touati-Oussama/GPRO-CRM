package com.project.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entities.Sepicialite;
import com.project.entities.User;





public interface SpecialiteRepository extends JpaRepository<Sepicialite, Long> {

	Sepicialite findByNom(String nom);
	
	List<Sepicialite> findSpecialitiesByStaffUsername(String username);
	Boolean existsByStaff(User staff);
	Boolean existsByNom(String nom);
}
