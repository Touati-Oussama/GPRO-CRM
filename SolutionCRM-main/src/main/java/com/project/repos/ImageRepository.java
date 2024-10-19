package com.project.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entities.ImageDB;
import com.project.entities.User;



public interface ImageRepository extends JpaRepository<ImageDB, String>  {

	/*ImageDB findByUser(User user);
	ImageDB findByUserUsername(String username);*/

	
}
