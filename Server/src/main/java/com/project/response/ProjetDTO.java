package com.project.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProjetDTO {

	private Long id;
	private String designation;
	private String description;
	private String specialite;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime  dateCreation;
	
	private String societe;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSpecialite() {
		return specialite;
	}
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	public String getsociete() {
		return societe;
	}
	public void setsociete(String societe) {
		this.societe = societe;
	}
	
	
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}
	@Override
	public String toString() {
		return "ProjetDTO [id=" + id + ", designation=" + designation + ", description=" + description + ", specialite="
				+ specialite + ", societe=" + societe + "]";
	}
	public ProjetDTO(Long id, String designation, String description, String specialite, String societe) {
		super();
		this.id = id;
		this.designation = designation;
		this.description = description;
		this.specialite = specialite;
		this.societe = societe;
	}
	public ProjetDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
