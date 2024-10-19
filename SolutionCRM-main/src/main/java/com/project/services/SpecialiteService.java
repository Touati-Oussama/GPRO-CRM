package com.project.services;

import java.util.List;

import com.project.entities.Sepicialite;
import com.project.entities.User;
import com.project.response.SpecialiteDTO;





public interface SpecialiteService {

	Sepicialite add(Sepicialite specialite);
	Sepicialite update(Sepicialite specialite);
	void deleteSpecialite(Sepicialite specialite);
	void deleteSpecialite(Long id);
	Sepicialite getSpecialite(Long id);
	SpecialiteDTO getSpecialiteById(Long id);
	Sepicialite getSpecialiteByNom(String nom);
	List<Sepicialite> getAll();
	List<SpecialiteDTO>findAll();
	Sepicialite addUserToSpecialite(String email, String specialite);
	List<User> findUsersBySepicialite(String spec);
	//List<Sepicialite> findSpecialitiesByUserUsername(String username);
	Boolean existsByStaffId(Long id);
	Boolean existsByNom(String nom);
}
