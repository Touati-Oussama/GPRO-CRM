package com.project.entities;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Projet {

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Long id;
	private String designation;
	private String description;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime  dateCreation = LocalDateTime.now();
	
	@ManyToOne
	@JoinColumn(name="societe_id",nullable=false)
	private Societe societe;

	@ManyToOne
	@JoinColumn(name="sepicialite_id",nullable=false)
	private Sepicialite specialite;
	
	
	public Sepicialite getSpecialite() {
		return specialite;
	}

	public void setSpecialite(Sepicialite specialite) {
		this.specialite = specialite;
	}

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

	public Societe getSociete() {
		return societe;
	}

	public void setSociete(Societe societe) {
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
		return "Projet [id=" + id + ", designation=" + designation + ", description=" + description + ", sociéte="
				+ societe + ", specialite=" + specialite + "]";
	}

	public Projet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Projet(Long id, String designation, String description) {
		super();
		this.id = id;
		this.designation = designation;
		this.description = description;
	}
	
	
	
}
