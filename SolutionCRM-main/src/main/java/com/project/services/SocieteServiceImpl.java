package com.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Societe;
import com.project.entities.User;
import com.project.repos.SocieteRepository;
import com.project.repos.UserRepository;
import com.project.response.SocieteDTO;
import com.project.response.SpecialiteDTO;


@Service
public class SocieteServiceImpl implements SocieteService{

	@Autowired
	SocieteRepository societeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Societe saveSociete(Societe societe) {
		// TODO Auto-generated method stub
		return societeRepository.save(societe);
	}

	@Override
	public Societe findSocieteByName(String societe) {
		// TODO Auto-generated method stub
		return societeRepository.findByName(societe);
	}

	@Override
	public Societe updateSociete(Societe societe) {
		// TODO Auto-generated method stub
		return societeRepository.save(societe);
	}

	@Override
	public Societe getSociete(Long id) {
		// TODO Auto-generated method stub
		return societeRepository.findById(id).get();
	}

	@Override
	public List<SocieteDTO> getAllSocietes() {
		// TODO Auto-generated method stub
		List<Societe> tmp = societeRepository.findAll();
		List<SocieteDTO> list = new ArrayList<>();
		tmp.forEach(soc ->{
			SocieteDTO s = new SocieteDTO();
			s.setId(soc.getId());
			s.setName(soc.getname());
			s.setNbEmpl(userRepository.findBySociete(soc).size());
			if(soc.getResponsable()!= null)
				s.setResponsable(soc.getResponsable().getNom()+ " " + soc.getResponsable().getPrenom());
			else
				s.setResponsable(null);
			list.add(s);
		});
		return list;
	}
	
	@Override
	public Societe findByResponsable(User responsable) {
		// TODO Auto-generated method stub
		return societeRepository.getByResponsable(responsable);
	}

	@Override
	public List<SocieteDTO> getAllSocietesByRespon() {
		// TODO Auto-generated method stub
		List<Societe> tmp = societeRepository.findByResponsable(null);
		List<SocieteDTO> list = new ArrayList<>();
		tmp.forEach(soc ->{
			SocieteDTO s = new SocieteDTO();
			s.setId(soc.getId());
			s.setName(soc.getname());
			s.setNbEmpl(userRepository.findBySociete(soc).size());
			if(soc.getResponsable()!= null)
				s.setResponsable(soc.getResponsable().getNom()+ " " + soc.getResponsable().getPrenom());
			else
				s.setResponsable(null);
			list.add(s);
		});
		return list;
	}
	
	
	@Override
	public void deleteSociete(Long id) {
		societeRepository.deleteById(id);
		
	}

	@Override
	public Boolean existsByName(String name) {
		// TODO Auto-generated method stub
		return societeRepository.existsByName(name);
	}





}
