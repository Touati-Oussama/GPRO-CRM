package com.project.restController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.entities.Etat;
import com.project.entities.Reclamation;
import com.project.entities.User;
import com.project.request.ReclamationModel;
import com.project.request.ReplyReclamation;
import com.project.response.JSONResponse;
import com.project.services.EmailSenderService;
import com.project.services.ProjetService;
import com.project.services.ReclamationService;
import com.project.services.UserService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/reclamations")
public class ReclamationRestController {
	
	@Autowired
	ReclamationService reclamationService;
	
	@Autowired
	EmailSenderService emailSenderService;
	

	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjetService projetService;
	
	@RequestMapping(path = "add",method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody ReclamationModel reclamation){
		
		User client = userService.findUserByUsername(reclamation.getClient());
		String message = "Dear "+ client.getNom() + " " + client.getPrenom() + " : \n" + "\n" +
		 " we recieved your complaint, and our customer service team is now loking into the issue. Thank you for bringing this matter to our attention. Within 4 day(s) we will provide a more substantive response to you probleme and a suitable solution."
				+ "\n" + "\n" + "Best regards";
		emailSenderService.sendEmail(client.getEmail(),"Complaint",message);
		return  ResponseEntity.ok(reclamationService.add(reclamation));
	}
	
	
	@RequestMapping(path = "reply", method = RequestMethod.POST)
	public ResponseEntity<?> reply(@RequestBody ReplyReclamation replyReclamation){
		Reclamation r = reclamationService.findById(replyReclamation.getId());
		r.setEtat(Etat.ClOTURE);
		emailSenderService.sendEmail(r.getClient().getEmail(), "Solution " + r.getSujet(),replyReclamation.getReply());
		return ResponseEntity.ok(reclamationService.update(r));
	}
	
	
	@RequestMapping(path = "update/status/{idReclamation}/{status}",method = RequestMethod.POST)
	public ResponseEntity<?> updateStatus(@PathVariable("idReclamation") Long reclamationId, @PathVariable("status") String status){
		Reclamation r = reclamationService.findById(reclamationId);
		if (status.equals(Etat.EN_ATTENTE.name()))
			r.setEtat(Etat.EN_ATTENTE);
		else if (status.equals(Etat.EN_COURS.name()))
			r.setEtat(Etat.EN_COURS);
		else if (status.equals(Etat.ClOTURE.name()))
			r.setEtat(Etat.ClOTURE);
		else
			return ResponseEntity.badRequest().body(new JSONResponse("Etat introuvable"));
		return  ResponseEntity.ok(reclamationService.update(r));
	}
	
	@RequestMapping(path = "update/employee/{idReclamation}/{idEmployee}",method = RequestMethod.POST)
	public ResponseEntity<?> forwardToEmployee(@PathVariable("idReclamation") Long reclamationId, @PathVariable("idEmployee") Long id){
		Reclamation r = reclamationService.findById(reclamationId);
		r.setDeveloppeur(userService.getUser(id));
		return  ResponseEntity.ok(reclamationService.update(r));
	}
	
	@RequestMapping(path = "client/username/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> all(@PathVariable("username") String username){
		return ResponseEntity.ok(reclamationService.findByClient(username));
	}
	
	@RequestMapping(path = "/details/status", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByStatus(){
		return ResponseEntity.ok(reclamationService.getDétailsByEtat());
	}
	
	@RequestMapping(path = "/details/status/societe", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByStatusAndSociety(@RequestParam("societe") String societe){
		return ResponseEntity.ok(reclamationService.getDétailsByEtatAndSoicety(societe));
	}
	
	@RequestMapping(path = "/details/status/societe/dates", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByStatusAndSociety(@RequestParam("societe") String societe,@RequestParam("date1") String date1, @RequestParam("date2") String date2){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
		return ResponseEntity.ok(reclamationService.getDétailsByEtatAndSoicetyAndDates(societe,dateTime1,dateTime2));
	}
	
	@RequestMapping(path = "/details/user", method = RequestMethod.GET)
	public ResponseEntity<?> details(@RequestParam("username") String username){
		return ResponseEntity.ok(reclamationService.getDétailsByUser(username));
	}
	
	@RequestMapping(path = "/details/user/dates", method = RequestMethod.GET)
	public ResponseEntity<?> details(@RequestParam("username") String username,@RequestParam("date1") String date1, @RequestParam("date2") String date2){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
		return ResponseEntity.ok(reclamationService.getDétailsByUserAndDates(username,dateTime1,dateTime2));
	}

	
	@RequestMapping(path = "/details/projet", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByPorjet(){
		return ResponseEntity.ok(reclamationService.totalByProjet());
	}
	
	@RequestMapping(path = "/details/projet/dates", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByPorjet(@RequestParam("date1") String date1, @RequestParam("date2") String date2){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
		return ResponseEntity.ok(reclamationService.totalByProjetAndates(dateTime1,dateTime2));
	}
	@RequestMapping(path = "/details/projet/status", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByPorjet(@RequestParam("status") String status){
		return ResponseEntity.ok(reclamationService.totalByProjetAndEtat(status));
	}
	
	@RequestMapping(path = "/details/projet/status/dates", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByPorjet(@RequestParam("status") String status,@RequestParam("date1") String date1, @RequestParam("date2") String date2){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
		return ResponseEntity.ok(reclamationService.totalByProjetAndEtatAndDates(status,dateTime1,dateTime2));
	}
	@RequestMapping(path = "/details/type", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByType(){
		return ResponseEntity.ok(reclamationService.totalByType());
	}
	
	@RequestMapping(path = "/details/type/dates", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByType(@RequestParam("date1") String date1, @RequestParam("date2") String date2){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
		return ResponseEntity.ok(reclamationService.totalByTypeAndDates(dateTime1,dateTime2));
	}
	
	@RequestMapping(path = "/details/type/status", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByType(@RequestParam("status") String status){
		return ResponseEntity.ok(reclamationService.totalByTypeAndEtat(status));
	}
	
	@RequestMapping(path = "/details/type/status/dates", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByType(@RequestParam("status") String status,@RequestParam("date1") String date1, @RequestParam("date2") String date2){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
		return ResponseEntity.ok(reclamationService.totalByTypeAndEtatAndDates(status,dateTime1,dateTime2));
	}
	@RequestMapping(path = "/details/personnel", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByDeveloppeurs(){
		return ResponseEntity.ok(reclamationService.totalByDeveloppeurs());
	}
	
	@RequestMapping(path = "/details/personnel/dates", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByDeveloppeurs(@RequestParam("date1") String date1, @RequestParam("date2") String date2){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
		return ResponseEntity.ok(reclamationService.totalByDeveloppeursAndDates(dateTime1,dateTime2));
	}
	
	@RequestMapping(path = "/details/personnel/status", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByDeveloppeurs(@RequestParam("status") String status){
		return ResponseEntity.ok(reclamationService.totalByDeveloppeursAndEtat(status));
	}
	
	@RequestMapping(path = "/details/client", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByClient(@RequestParam("username") String username){
		User client = userService.findUserByUsername(username);
		return ResponseEntity.ok(reclamationService.detailsByClient(client));
	}
	
	@RequestMapping(path = "/details/personnel/status/dates", method = RequestMethod.GET)
	public ResponseEntity<?> detailsByDeveloppeurs(@RequestParam("status") String status,@RequestParam("date1") String date1, @RequestParam("date2") String date2){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
		LocalDateTime dateTime1 = LocalDateTime.parse(date1, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(date2, formatter);
		return ResponseEntity.ok(reclamationService.totalByDeveloppeursAndEtatAndDates(status,dateTime1,dateTime2));
	}
	
	
	
	@RequestMapping(path = "projet", method = RequestMethod.GET)
	public ResponseEntity<?> findByProjet(@RequestParam Long id){
		return ResponseEntity.ok(reclamationService.findByProjetId(id));
	}
	@RequestMapping(path = "type", method = RequestMethod.GET)
	public ResponseEntity<?> findByType(@RequestParam Long id){
		return ResponseEntity.ok(reclamationService.findByTypeId(id));
	}
	
	
	@RequestMapping(path = "employee/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> findByEmployee(@PathVariable("username") String username){
		return ResponseEntity.ok(reclamationService.findByDeveloppeur(username));
	}
	
	@RequestMapping(path = "all", method = RequestMethod.GET)
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(reclamationService.findAll());
	}
	
	@RequestMapping(path = "{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable("id") Long id){
		return ResponseEntity.ok(reclamationService.getReclamation(id));
	}
	
	@RequestMapping(path = "client/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getClientByRecId(@PathVariable("id") Long id){
		Reclamation r = reclamationService.findById(id);
		return ResponseEntity.ok(r.getClient());
	}
	
	
	@RequestMapping(path = "societe/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getSocietyName(@PathVariable("name") String name){
		
		return ResponseEntity.ok(reclamationService.findBySociete(name));
	}
	
	
	@RequestMapping(path = "filter", method = RequestMethod.GET)
	public ResponseEntity<?> findByFilter(@RequestParam String keyword){
		
		return ResponseEntity.ok(reclamationService.findByFilter(keyword));
	}
	
	@RequestMapping(path = "filter/status", method = RequestMethod.GET)
	public ResponseEntity<?> findByFilterAndStatus(@RequestParam("keyword") String keyword, @RequestParam("status") String status){
		
		return ResponseEntity.ok(reclamationService.findByFilterAndEtat(keyword,status));
	}
	
	
	@RequestMapping(path = "/status/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getByStatus(@PathVariable("name") String status){
		return ResponseEntity.ok(reclamationService.findByEtat(status));
	}
	
	@RequestMapping(path = "/employee/{username}/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getByStatus(@PathVariable("username") String username, @PathVariable("name") String status){
		return ResponseEntity.ok(reclamationService.findByDeveloppeurAndEtat(username, status));
	}
	
	@RequestMapping(path = "/societe/{societe}/status/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getBySocieteAndStatus(@PathVariable("societe") String societe, @PathVariable("name") String status){
		return ResponseEntity.ok(reclamationService.findBySocieteAndEtat(societe, status));
	}
	
	@RequestMapping(path = "/status", method = RequestMethod.GET)
	public ResponseEntity<?> getByListStatus(){
		return ResponseEntity.ok(reclamationService.findByStatusList());
	}
	
	
	@RequestMapping(path="delete/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?>delete(@PathVariable("id")Long id){
		reclamationService.deleteReclamation(id);
		JSONResponse response = new JSONResponse("Reclamation Supprimé !");
		return ResponseEntity.ok(response);
	}
}
