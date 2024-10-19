package com.project.request;

import java.util.List;

public class ReclamationModel {

	private String sujet;
	private String details;
	private Long developer;
	private String client;
	private Long type;
	private Long projet;
	private Long status;
	private Long speciality;
	private Long societe;
	private String file;
	
	private List<String> files;
	
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
	public Long getDeveloper() {
		return developer;
	}
	public void setDeveloper(Long developer) {
		this.developer = developer;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getProjet() {
		return projet;
	}
	public void setProjet(Long projet) {
		this.projet = projet;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	
	
	public Long getSpeciality() {
		return speciality;
	}
	public void setSpeciality(Long speciality) {
		this.speciality = speciality;
	}
	public Long getSociete() {
		return societe;
	}
	public void setSociete(Long societe) {
		this.societe = societe;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	
	
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public ReclamationModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	


}
