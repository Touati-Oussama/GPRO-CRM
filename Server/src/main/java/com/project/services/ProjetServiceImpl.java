package com.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Projet;
import com.project.entities.Sepicialite;
import com.project.entities.Societe;
import com.project.entities.User;
import com.project.repos.ProjetRepository;
import com.project.response.ProjetDTO;



@Service
public class ProjetServiceImpl implements ProjetService{

	@Autowired
	private ProjetRepository projetRepository;
	
	@Autowired
	SpecialiteService specialiteService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	SocieteService societeService;
	
	@Override
	public Projet add(Projet projet) {
		// TODO Auto-generated method stub
		return this.projetRepository.save(projet);
	}

	@Override
	public Projet update(Projet projet) {
		// TODO Auto-generated method stub
		return this.projetRepository.save(projet);
	}

	@Override
	public void deleteProjet(Projet projet) {
		// TODO Auto-generated method stub
		this.projetRepository.delete(projet);
	}

	@Override
	public void deleteProjet(Long id) {
		// TODO Auto-generated method stub
		this.projetRepository.deleteById(id);
	}

	@Override
	public Projet getProjet(Long id) {
		// TODO Auto-generated method stub
		return this.projetRepository.findById(id).get();
	}

	@Override
	public List<Projet> getAll() {
		// TODO Auto-generated method stub
		return projetRepository.findAll();
	}

	@Override
	public List<ProjetDTO> findAll() {
		// TODO Auto-generated method stub
		List<Projet> tmp = projetRepository.findAll();
		if (tmp == null)
			return null;
		List<ProjetDTO> newList = new ArrayList<>();
		tmp.forEach(p ->{
			ProjetDTO proj = new ProjetDTO();
			proj.setId(p.getId());
			proj.setDesignation(p.getDesignation());
			proj.setDescription(p.getDescription());
			proj.setsociete(p.getSociete().getname());
			proj.setSpecialite(p.getSpecialite().getNom());
			proj.setDateCreation(p.getDateCreation());
			newList.add(proj);
		});
		return newList;
	}

	@Override
	public ProjetDTO findProjetById(Long id) {
		// TODO Auto-generated method stub
		Projet p = projetRepository.findById(id).get();
		if (p == null)
			return null;
		ProjetDTO proj = new ProjetDTO();
		proj.setId(p.getId());
		proj.setDesignation(p.getDesignation());
		proj.setDescription(p.getDescription());
		proj.setsociete(p.getSociete().getname());
		proj.setSpecialite(p.getSpecialite().getNom());
		proj.setDateCreation(p.getDateCreation());			
		return proj;
	}

	public List<ProjetDTO> findBySocieteId(Long id) {
	// TODO Auto-generated method stub
	Societe s = societeService.getSociete(id);
	if (s == null)
		return null;
	List<Projet> projects = projetRepository.findBySociete(s);
	if (projects == null)
		return null;
	List<ProjetDTO> newList = new ArrayList<>();
	projects.forEach(p ->{
		ProjetDTO proj = new ProjetDTO();
		proj.setId(p.getId());
		proj.setDesignation(p.getDesignation());
		proj.setDescription(p.getDescription());
		proj.setsociete(p.getSociete().getname());
		proj.setSpecialite(p.getSpecialite().getNom());
		newList.add(proj);
	});
	return newList;
}

	@Override
	public List<ProjetDTO> findBySocieteName(String societe) {
		// TODO Auto-generated method stub
		Societe s = societeService.findSocieteByName(societe);
		if (s == null)
			return null;
		List<Projet> projects = projetRepository.findBySociete(s);
		if (projects == null)
			return null;
		List<ProjetDTO> newList = new ArrayList<>();
		projects.forEach(p ->{
			ProjetDTO proj = new ProjetDTO();
			proj.setId(p.getId());
			proj.setDesignation(p.getDesignation());
			proj.setDescription(p.getDescription());
			proj.setDateCreation(p.getDateCreation());
			proj.setsociete(p.getSociete().getname());
			proj.setSpecialite(p.getSpecialite().getNom());
			newList.add(proj);
		});
		return newList;
	}

	public Boolean existsBySpecialiteId(Long id) {
		Sepicialite s = specialiteService.getSpecialite(id);
		if (s == null)
			return null;
		return projetRepository.existsBySpecialite(s);
	}

	@Override
	public Boolean existsBySocieteId(Long id) {
		Societe societe = societeService.getSociete(id);
		if (societe == null)
			return null;
		return projetRepository.existsBySociete(societe);
	}

	@Override
	public Boolean existsByDesignation(String designation) {
		// TODO Auto-generated method stub
		return projetRepository.existsByDesignation(designation);
	}

	@Override
	public Projet findByDesignation(String designation) {
		// TODO Auto-generated method stub
		return projetRepository.findByDesignation(designation);
	}

}
