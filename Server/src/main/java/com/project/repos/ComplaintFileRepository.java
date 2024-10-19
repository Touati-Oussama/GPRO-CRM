package com.project.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.ComplaintFile;
import com.project.entities.Reclamation;



public interface ComplaintFileRepository extends JpaRepository<ComplaintFile, String> {

	List<ComplaintFile> findByReclamation(Reclamation reclamation);
	
}
