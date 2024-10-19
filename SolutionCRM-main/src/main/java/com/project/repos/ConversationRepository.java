package com.project.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.InboxConversation;

public interface ConversationRepository extends JpaRepository<InboxConversation, String> {

	InboxConversation findBySubject(String subject);
}
