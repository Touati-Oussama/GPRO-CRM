package com.project.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.entities.Sepicialite;
import com.project.entities.User;
import com.project.response.JSONResponse;
import com.project.services.ProjetService;
import com.project.services.SpecialiteService;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/specialite")
public class SpecialiteRestController {

	@Autowired
	private SpecialiteService specialiteService;
	
	@Autowired
	ProjetService projetService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(specialiteService.getAll());
	}
	
	@RequestMapping(path="all",method = RequestMethod.GET)
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(specialiteService.findAll());
	}
	@RequestMapping(path="get/{nom}",method = RequestMethod.GET)
	public ResponseEntity<?> getByNom(@PathVariable("nom") String nom){
		return ResponseEntity.ok(specialiteService.getSpecialiteByNom(nom));
	}
	@RequestMapping(path="{id}",method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable("id") Long id){
		return ResponseEntity.ok(specialiteService.getSpecialiteById(id));
	}
	
	@RequestMapping(path="getUsers/{spec}",method = RequestMethod.GET)
	public ResponseEntity<?> getUsersBySpecialite(@PathVariable("spec") String spec){
		return ResponseEntity.ok(specialiteService.findUsersBySepicialite(spec));
	}
	
	@RequestMapping(path="add",method = RequestMethod.POST)
	public ResponseEntity<?>add(@RequestBody Sepicialite specialite){
		Boolean res = specialiteService.existsByNom(specialite.getNom());		
		if (res) {
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Echec d'ajout, Cette spécialité déja existe !"));
		}
		return ResponseEntity.ok(specialiteService.add(specialite));
	}
	
	@RequestMapping(path="update/{id}",method = RequestMethod.POST)
	public ResponseEntity<?>update(@RequestBody Sepicialite specialite,@PathVariable("id") Long id){
		List<User> tmp= specialiteService.findUsersBySepicialite(specialiteService.getSpecialite(id).getNom());
		Boolean res = projetService.existsBySpecialiteId(id);		
		if (res || tmp.size() != 0) {
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Impossible de modifier cette spécialité"));
		}
		Sepicialite s = specialiteService.getSpecialite(id);
		s.setNom(specialite.getNom());
		return ResponseEntity.ok(specialiteService.update(s));
	}
	
	@RequestMapping(path="delete/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?>delete(@PathVariable("id")Long id){
		List<User> tmp= specialiteService.findUsersBySepicialite(specialiteService.getSpecialite(id).getNom());
		Boolean res = projetService.existsBySpecialiteId(id);
		
		if (res || tmp.size() != 0) {
			return ResponseEntity
					.badRequest()
					.body(new JSONResponse("Impossible de supprimer cette spécialité"));
		}
		specialiteService.deleteSpecialite(id);
		JSONResponse response = new JSONResponse("Spécialité Supprimé !");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(path="affecter/{specialite}", method=RequestMethod.POST)
	public ResponseEntity<?> affecter(@RequestBody User user/*@PathVariable("email") String email*/, @PathVariable("specialite") String specialite){
		return ResponseEntity.ok(specialiteService.addUserToSpecialite(user.getEmail(), specialite));
	}
	
}
