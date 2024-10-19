package com.project.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entities.InboxConversation;
import com.project.entities.InboxMessage;

public interface MessageRepository extends JpaRepository<InboxMessage, String> {

	@Query("SELECT m FROM InboxMessage m join InboxConversation c on (m.inboxConversation = c) where (c.id =?1) ORDER BY m.sendDate")
	List<InboxMessage> findByInboxConversationId(String id);
}
