package com.project.services;

import java.util.List;

import com.project.entities.Projet;
import com.project.response.ProjetDTO;



public interface ProjetService {

	Projet add(Projet projet);
	Projet update(Projet projet);
	void deleteProjet(Projet projet);
	void deleteProjet(Long id);
	Projet getProjet(Long id);
	ProjetDTO findProjetById(Long id);
	List<Projet> getAll();
	List<ProjetDTO> findAll();
	List<ProjetDTO> findBySocieteId(Long id);
	List<ProjetDTO> findBySocieteName(String societe);
	Boolean existsBySpecialiteId(Long id);
	Boolean existsBySocieteId(Long id);
	Boolean existsByDesignation(String designation);
	Projet findByDesignation(String designation);
}
