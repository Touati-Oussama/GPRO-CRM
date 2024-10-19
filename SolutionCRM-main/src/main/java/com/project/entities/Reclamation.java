package com.project.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Reclamation {

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Long id;
	private String sujet;
	private String details;
	
	@Enumerated(EnumType.STRING)
	private Etat etat = Etat.EN_ATTENTE;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime  dateCreation = LocalDateTime.now();
	
	@ManyToOne
	@JoinColumn(name="type_id",nullable=false)
	private TypeReclamation type;
	
	
	@JsonIgnore
	@OneToMany(mappedBy ="reclamation"  ,cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ComplaintFile> files;
	
	
	/*@ManyToOne
	@JoinColumn(name="status_id",nullable=false)
	private StatusReclamation status;*/
	
	@ManyToOne
	@JoinColumn(name="dev_id")
	private User developpeur;

    @ManyToOne
    @JoinColumn(name="projet_id", nullable=false)
    private Projet projet;
    
	@ManyToOne
	@JoinColumn(name="client_id",nullable=false)
	private User client;
	
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

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public TypeReclamation getType() {
		return type;
	}

	public void setType(TypeReclamation type) {
		this.type = type;
	}

	

	public List<ComplaintFile> getFile() {
		return files;
	}

	public void setFile(List<ComplaintFile> files) {
		this.files = files;
	}

	public User getDeveloppeur() {
		return developpeur;
	}

	public void setDeveloppeur(User developpeur) {
		this.developpeur = developpeur;
	}

	
	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	
	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}



	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	@Override
	public String toString() {
		return "Reclamation [id=" + id + ", sujet=" + sujet + ", details=" + details + ", etat=" + etat
				+ ", dateCreation=" + dateCreation + ", type=" + type + ", developpeur=" + developpeur + ", projet="
				+ projet + ", client=" + client + "]";
	}

	public Reclamation(Long id, String sujet, String details, LocalDateTime dateCreation) {
		super();
		this.id = id;
		this.sujet = sujet;
		this.details = details;
		this.dateCreation = dateCreation;
	}

	


	public Reclamation(Long id, String sujet, String details, LocalDateTime dateCreation, TypeReclamation type,
			Etat status, User developpeur, Projet projet, User client) {
		super();
		this.id = id;
		this.sujet = sujet;
		this.details = details;
		this.dateCreation = dateCreation;
		this.type = type;
		this.developpeur = developpeur;
		this.projet = projet;
		this.client = client;
		this.etat = status;
	}

	public Reclamation() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
