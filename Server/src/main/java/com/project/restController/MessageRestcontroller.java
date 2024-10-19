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
import com.project.entities.User;
import com.project.repos.ConversationRepository;
import com.project.repos.MessageRepository;
import com.project.request.MessageModel;
import com.project.response.MessageResponsa;
import com.project.services.UserService;
import com.project.services.WebSocketService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/messages")
public class MessageRestcontroller {

	@Autowired
	UserService userService;
	@Autowired
	private WebSocketService webSocketService;
	
	@Autowired
	ConversationRepository conversationRepository;
	
	@Autowired
	MessageRepository messageRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@RequestParam String conversationId){
		List<InboxMessage> tmp = messageRepository.findByInboxConversationId(conversationId);
		List<MessageResponsa> lists = new ArrayList<MessageResponsa>();
		tmp.forEach(m->{
			MessageResponsa message = new MessageResponsa();
			message.setId(m.getId());
			message.setMessage(m.getMessage());
			message.setSendDate(m.getSendDate());
			message.setInboxConversationId(m.getInboxConversation().getId());
			message.setFromId((m.getFrom().getUser_id()));
			message.setUserPhoto(m.getUserPhoto());
			message.setFromName(m.getFrom().getNom() +" "+ m.getFrom().getPrenom());
			lists.add(message);
		});
		return ResponseEntity.ok(lists);
	}
	
	
	@RequestMapping(path="add",method = RequestMethod.POST)
	public ResponseEntity<?>add(@RequestBody MessageModel msg){
		System.out.println("***********************************"+msg.getUserPhoto() +" ******************");
		InboxMessage message = new InboxMessage();
		message.setFrom(userService.getUser(Long.parseLong(msg.getFromId())));
		message.setInboxConversation(conversationRepository.findById(msg.getInboxConversationId()).get());
		message.setMessage(msg.getMessage());
		message.setUserPhoto(msg.getUserPhoto());
		InboxMessage m = messageRepository.save(message);
		MessageResponsa mess = new MessageResponsa();
		mess.setId(m.getId());
		mess.setMessage(m.getMessage());
		mess.setSendDate(m.getSendDate());
		mess.setInboxConversationId(m.getInboxConversation().getId());
		mess.setFromId((m.getFrom().getUser_id()));
		mess.setUserPhoto(m.getUserPhoto());
		notifyFrontend("new Message");
		return ResponseEntity.ok(mess);
	}
	
	
	private void notifyFrontend(String entityTopic) {
        webSocketService.sendMessage(entityTopic);
    }
}
