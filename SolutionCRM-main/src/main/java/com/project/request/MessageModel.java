package com.project.request;

public class MessageModel {

	private String inboxConversationId;
	private String message;
	private String fromId;
	private String userPhoto;
	
	public String getInboxConversationId() {
		return inboxConversationId;
	}
	public String getMessage() {
		return message;
	}
	public String getFromId() {
		return fromId;
	}
	public void setInboxConversationId(String conversationId) {
		this.inboxConversationId = conversationId;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	
	
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public MessageModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
