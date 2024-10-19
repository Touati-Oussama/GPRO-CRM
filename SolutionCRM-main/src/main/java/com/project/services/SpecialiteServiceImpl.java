package com.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.entities.Sepicialite;
import com.project.entities.User;
import com.project.repos.SpecialiteRepository;
import com.project.repos.UserRepository;
import com.project.response.SpecialiteDTO;

@Transactional
@Service
public class SpecialiteServiceImpl implements SpecialiteService{

	@Autowired
	private SpecialiteRepository specialiteRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Sepicialite add(Sepicialite specialite) {
		// TODO Auto-generated method stub
		return specialiteRepository.save(specialite);
	}

	@Override
	public Sepicialite update(Sepicialite specialite) {
		// TODO Auto-generated method stub
		return specialiteRepository.save(specialite);
	}

	@Override
	public void deleteSpecialite(Sepicialite specialite) {
		// TODO Auto-generated method stub
		specialiteRepository.delete(specialite);
	}

	@Override
	public void deleteSpecialite(Long id) {
		// TODO Auto-generated method stub
		specialiteRepository.deleteById(id);
	}

	@Override
	public Sepicialite getSpecialite(Long id) {
		// TODO Auto-generated method stub
		return specialiteRepository.findById(id).get();
	}

	@Override
	public List<Sepicialite> getAll() {
		// TODO Auto-generated method stub
		return specialiteRepository.findAll();
	}

	@Override
	public Sepicialite addUserToSpecialite(String username, String specialite) {
		// TODO Auto-generated method stub
		User staff = userRepository.findByUsername(username);
		Sepicialite spec = specialiteRepository.findByNom(specialite);
		//spec.getStaff().add(staff);
		return spec;
	}

	@Override
	public Sepicialite  getSpecialiteByNom(String nom) {
		// TODO Auto-generated method stub
		return specialiteRepository.findByNom(nom);
	}

	@Override
	public List<SpecialiteDTO> findAll() {
		// TODO Auto-generated method stub
		List<Sepicialite> tmp = specialiteRepository.findAll();
		List<SpecialiteDTO> list = new ArrayList<>();
		tmp.forEach(spec ->{
			SpecialiteDTO s = new SpecialiteDTO();
			s.setId(spec.getSpec_id());
			s.setSpecialite(spec.getNom());
			s.setNbEmpl(spec.getStaff().size());
			list.add(s);
		});
		return list;
	}

	@Override
	public SpecialiteDTO getSpecialiteById(Long id) {
		// TODO Auto-generated method stub
		Sepicialite spec = specialiteRepository.findById(id).get();
		SpecialiteDTO s = new SpecialiteDTO();
		s.setId(spec.getSpec_id());
		s.setSpecialite(spec.getNom());
		s.setNbEmpl(spec.getStaff().size());
		return s;
	}

	@Override
	public List<User> findUsersBySepicialite(String spec) {
		return null;// userRepository.findUsersBySpecialitiesNom(spec);
	}

	@Override
	public Boolean existsByStaffId(Long id) {
		// TODO Auto-generated method stub
		User staff = userRepository.findById(id).get();
		if (staff == null)
			return null;
		return specialiteRepository.existsByStaff(staff);
	}

	@Override
	public Boolean existsByNom(String nom) {
		// TODO Auto-generated method stub
		return specialiteRepository.existsByNom(nom);
	}



}
