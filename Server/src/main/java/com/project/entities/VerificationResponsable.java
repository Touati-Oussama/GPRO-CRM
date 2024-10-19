package com.project.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class VerificationResponsable {

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User responsable;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Societe societe;

	public Long getId() {
		return id;
	}

	public User getResponsable() {
		return responsable;
	}

	public Societe getSociete() {
		return societe;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setResponsable(User responsable) {
		this.responsable = responsable;
	}

	public void setSociete(Societe societe) {
		this.societe = societe;
	}

	public VerificationResponsable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
