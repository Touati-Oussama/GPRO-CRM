package com.project.restController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.entities.InboxConversation;
import com.project.entities.InboxMessage;
import com.project.entities.Projet;
import com.project.entities.Societe;
import com.project.entities.User;
import com.project.repos.ConversationRepository;
import com.project.repos.MessageRepository;
import com.project.request.ProjetModel;
import com.project.response.JSONResponse;
import com.project.services.SocieteService;
import com.project.services.UserService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/rooms")
public class ChatRoomRestController {

	@Autowired
	UserService userService;
	
	@Autowired
	ConversationRepository conversationRepository;
	
	@Autowired
	SocieteService societeService;
	
	
	@Autowired
	MessageRepository messageRepository;
	
	@RequestMapping(path="all",method = RequestMethod.GET)
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(conversationRepository.findAll());
	}
	
	@RequestMapping(path="",method = RequestMethod.GET)
	public ResponseEntity<?> findBySubject(@RequestParam("subject") String subject){
		return ResponseEntity.ok(conversationRepository.findBySubject(subject));
	}
	
	@RequestMapping(path="add",method = RequestMethod.POST)
	public ResponseEntity<?>add(@RequestParam String societe){
		List<User> customers = userService.findBySocieteName(societe);
		List<User> teams = userService.getAllStaffs();
		Societe s = societeService.findSocieteByName(societe);
		InboxConversation conversation = new InboxConversation();
		conversation.setSubject(societe);
		conversation.setSociete(s);
		List <User> participants = new ArrayList<User>();
		participants.addAll(teams);
		participants.addAll(customers);
		conversation.setParticipants(participants);
		
		return ResponseEntity.ok(conversationRepository.save(conversation));
	}
	
	@RequestMapping(path="/update",method = RequestMethod.POST)
	public ResponseEntity<?>update(@RequestParam String societe){
		InboxConversation conversation = conversationRepository.findBySubject(societe);
		List<InboxMessage> tmp = messageRepository.findByInboxConversationId(conversation.getId());
		conversation.setNbMsg(new Long(tmp.size()));		
		return ResponseEntity.ok(conversationRepository.save(conversation));
	}
	

}
