package com.project.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class InboxMessage {

	  @Id
	  @GeneratedValue(generator = "uuid")
	  @GenericGenerator(name = "uuid", strategy = "uuid2")
	  private String id;
	
	private String message;
	private String fileUrl;
	private String userPhoto;
	private LocalDateTime sendDate = LocalDateTime.now();
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User from;
	
	@ManyToOne
	@JsonIgnoreProperties()
	@JoinColumn(name="conversation_id",nullable=false)
	private InboxConversation inboxConversation;

	public String getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public String getFileUrl() {
		return fileUrl;
	}
	
	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public LocalDateTime getSendDate() {
		return sendDate;
	}

	public User getFrom() {
		return from;
	}

	public InboxConversation getInboxConversation() {
		return inboxConversation;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public void setSendDate(LocalDateTime sendDate) {
		this.sendDate = sendDate;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public void setInboxConversation(InboxConversation inboxConversation) {
		this.inboxConversation = inboxConversation;
	}

	@Override
	public String toString() {
		return "InboxMessage [id=" + id + ", message=" + message + ", fileUrl=" + fileUrl + ", sendDate=" + sendDate
				+ ", from=" + from + ", inboxConversation=" + inboxConversation + "]";
	}
	
	
	

}
