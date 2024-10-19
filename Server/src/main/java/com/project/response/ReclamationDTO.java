package com.project.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.entities.ComplaintFile;


public class ReclamationDTO {

	private Long id;
	private String sujet;
	private String details;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dateCreation;
	private String type;
	private String status;
	private String projet;
	private String developper;
	private String societe;
	private String speciality;
	private String client;
	private List<ComplaintFile> files;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSujet() {
		return sujet;
	}
	public void setSujet(String sujet) {
		this.sujet = sujet;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getSociete() {
		return societe;
	}
	public void setSociete(String societe) {
		this.societe = societe;
	}
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProjet() {
		return projet;
	}
	public void setProjet(String projet) {
		this.projet = projet;
	}
	
	
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getDevelopper() {
		return developper;
	}
	public void setDevelopper(String developper) {
		this.developper = developper;
	}
	
	
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	
	
	public List<ComplaintFile> getFiles() {
		return files;
	}
	public void setFiles(List<ComplaintFile> files) {
		this.files = files;
	}
	/*public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}*/
	public ReclamationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
