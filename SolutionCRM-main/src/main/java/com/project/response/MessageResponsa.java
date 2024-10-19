package com.project.response;

import java.time.LocalDateTime;

public class MessageResponsa {

    private String id;
    private String message;
    private LocalDateTime sendDate;
    private String inboxConversationId;
    private Long fromId;
    private String fromName;
    private String userPhoto;
    private String fileUrl;
	public MessageResponsa() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public String getMessage() {
		return message;
	}
	public LocalDateTime getSendDate() {
		return sendDate;
	}
	public String getInboxConversationId() {
		return inboxConversationId;
	}
	public Long getFromId() {
		return fromId;
	}
	public String getFromName() {
		return fromName;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setSendDate(LocalDateTime sendDate) {
		this.sendDate = sendDate;
	}
	public void setInboxConversationId(String inboxConversationId) {
		this.inboxConversationId = inboxConversationId;
	}
	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
    
    
    
}
