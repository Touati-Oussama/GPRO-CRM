package com.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.TypeReclamation;
import com.project.repos.TypeReclamationRepository;




@Service
public class TypeServiceImpl implements TypeService{

	@Autowired
	TypeReclamationRepository  typeReclamationRepository;
	
	@Override
	public TypeReclamation add(TypeReclamation type) {
		// TODO Auto-generated method stub
		return typeReclamationRepository.save(type);
	}

	@Override
	public TypeReclamation update(TypeReclamation type) {
		// TODO Auto-generated method stub
		return typeReclamationRepository.save(type);
	}

	@Override
	public void deleteTypeReclamation(TypeReclamation type) {
		// TODO Auto-generated method stub
		typeReclamationRepository.delete(type);
	}

	@Override
	public void deleteTypeReclamation(Long id) {
		// TODO Auto-generated method stub
		typeReclamationRepository.deleteById(id);
	}

	@Override
	public TypeReclamation getTypeReclamation(Long id) {
		// TODO Auto-generated method stub
		return typeReclamationRepository.findById(id).get();
	}

	@Override
	public List<TypeReclamation> getAll() {
		// TODO Auto-generated method stub
		return typeReclamationRepository.findAll();
	}

	@Override
	public Boolean existsByType(String type) {
		// TODO Auto-generated method stub
		return typeReclamationRepository.existsByType(type);
	}

}
