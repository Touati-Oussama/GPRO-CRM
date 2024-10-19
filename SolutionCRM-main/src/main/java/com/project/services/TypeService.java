package com.project.services;

import java.util.List;

import com.project.entities.TypeReclamation;





public interface TypeService {

	TypeReclamation add(TypeReclamation type);
	TypeReclamation update(TypeReclamation type);
	void deleteTypeReclamation(TypeReclamation type);
	void deleteTypeReclamation(Long id);
	TypeReclamation getTypeReclamation(Long id);
	List<TypeReclamation> getAll();
	Boolean existsByType(String type);
}
