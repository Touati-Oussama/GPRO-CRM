package com.project.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.entities.TypeReclamation;
import com.project.response.JSONResponse;
import com.project.services.ReclamationService;
import com.project.services.TypeService;



@RestController
@CrossOrigin(origins="*")
@RequestMapping("/types")
public class TypeRestController {

	@Autowired
	TypeService typeService;
	
	@Autowired
	ReclamationService reclamationService;
	
	@RequestMapping(path="all",method = RequestMethod.GET)
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(typeService.getAll());
	}
	
	@RequestMapping(path="/{id}",method = RequestMethod.GET)
	public ResponseEntity<?> getType(@PathVariable("id") Long id){
		return ResponseEntity.ok(typeService.getTypeReclamation(id));
	}
	
	@RequestMapping(path="add",method = RequestMethod.POST)
	public ResponseEntity<?>add(@RequestBody TypeReclamation type){
		Boolean res = typeService.existsByType(type.getType());
		if (res)
		{
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Echec d'ajout. Ce type de réclamation déja exist!"));
		}
		return ResponseEntity.ok(typeService.add(type));
	}
	
	@RequestMapping(path="update/{id}",method = RequestMethod.POST)
	public ResponseEntity<?>update(@RequestBody TypeReclamation type,@PathVariable("id") Long id){
		Boolean res = reclamationService.existsByType(id);
		if (res)
		{
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Echec de modification.Ce type contient des réclamations !"));
		}
		TypeReclamation t = typeService.getTypeReclamation(id);
		t.setType(type.getType());
		return ResponseEntity.ok(typeService.update(t));
	}
	
	@RequestMapping(path="delete/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?>delete(@PathVariable("id")Long id){
		
		Boolean res = reclamationService.existsByType(id);
		if (res)
		{
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Echec de suppression.Ce type contient des réclamations !"));
		}
		typeService.deleteTypeReclamation(id);
		JSONResponse response = new JSONResponse("Type Supprimé !");
		return ResponseEntity.ok(response);
	}
	
}
