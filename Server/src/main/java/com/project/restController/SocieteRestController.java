package com.project.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.entities.Societe;
import com.project.response.JSONResponse;
import com.project.services.ProjetService;
import com.project.services.SocieteService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/societe")
public class SocieteRestController {

	@Autowired
	SocieteService societeService;
	
	@Autowired
	ProjetService projetService;
	
	@RequestMapping(path="all",method = RequestMethod.GET)
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(societeService.getAllSocietes());
	}
	
	@RequestMapping(path="all/responsable",method = RequestMethod.GET)
	public ResponseEntity<?> getAllResponsableNull(){
		return ResponseEntity.ok(societeService.getAllSocietesByRespon());
	}
	
	@RequestMapping(path="/{id}",method = RequestMethod.GET)
	public ResponseEntity<?> getSociete(@PathVariable("id") Long id){
		return ResponseEntity.ok(societeService.getSociete(id));
	}
	
	@RequestMapping(path="/get/{name}",method = RequestMethod.GET)
	public ResponseEntity<?> getSociete(@PathVariable("name") String name){
		return ResponseEntity.ok(societeService.findSocieteByName(name));
	}
	
	@RequestMapping(path="add",method = RequestMethod.POST)
	public ResponseEntity<?>add(@RequestBody Societe societe){
		Boolean res = societeService.existsByName(societe.getname());
		if (res)
		{
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Echec d'ajout, Cette société déja existe !"));
		}
		return ResponseEntity.ok(societeService.saveSociete(societe));
	}
	
	@RequestMapping(path="delete/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?>delete(@PathVariable("id")Long id){
		Boolean res = projetService.existsBySocieteId(id);
		if (res)
		{
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Echec de suppression, Cette société contient des projet(s) !"));
		}
		societeService.deleteSociete(id);
		JSONResponse response = new JSONResponse("Sociéte Supprimé !");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(path="update/{id}",method = RequestMethod.POST)
	public ResponseEntity<?>update(@RequestBody Societe societe,@PathVariable("id") Long id){
		Boolean res = projetService.existsBySocieteId(id);
		if (res)
		{
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Echec de modification, Cette société contient des projet(s) !"));
		}
		Societe s = societeService.getSociete(id);	
		s.setname(societe.getname());
		return ResponseEntity.ok(societeService.updateSociete(s));
	}
}
