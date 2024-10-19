package com.project.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class InboxConversation {

	  @Id
	  @GeneratedValue(generator = "uuid")
	  @GenericGenerator(name = "uuid", strategy = "uuid2")
	  private String id;
	
	private String subject;
	private Long nbMsg = new Long(0);
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDate = LocalDateTime.now();
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastModifiedDate = LocalDateTime.now();;
	
 	@JsonIgnore
	@OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JoinColumn(referencedColumnName = "id")
	private Societe societe;
	
	@ManyToMany
	@JoinTable(name="conversations_users",joinColumns = @JoinColumn(name="id") ,
	inverseJoinColumns = @JoinColumn(name="user_id"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<User> participants = new ArrayList<>();
	
	public String getId() {
		return id;
	}
	public String getSubject() {
		return subject;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
	public List<User> getParticipants() {
		return participants;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
	
	public Societe getSociete() {
		return societe;
	}
	public void setSociete(Societe societe) {
		this.societe = societe;
	}
	public Long getNbMsg() {
		return nbMsg;
	}
	public void setNbMsg(Long nbMsg) {
		this.nbMsg = nbMsg;
	}
	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}
	@Override
	public String toString() {
		return "InboxConversation [id=" + id + ", subject=" + subject + ", startDate=" + startDate
				+ ", lastModifiedDate=" + lastModifiedDate + ", participants=" + participants + "]";
	}
	
	
}
