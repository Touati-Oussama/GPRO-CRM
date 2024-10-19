package com.project.services;

import java.util.List;

import com.project.entities.Societe;
import com.project.entities.User;
import com.project.response.SocieteDTO;



public interface SocieteService {

	Societe saveSociete(Societe societe);
	Societe findSocieteByName (String societe);
	Societe updateSociete(Societe societe);
	//User findUserByEmail(String email);
	Societe getSociete(Long id);
	List<SocieteDTO>getAllSocietes();
	List<SocieteDTO>getAllSocietesByRespon();
	public Societe findByResponsable(User responsable);
	void deleteSociete(Long id);
	//Boolean existsBySocieteName(String societe);
	Boolean existsByName(String name);
	
}
