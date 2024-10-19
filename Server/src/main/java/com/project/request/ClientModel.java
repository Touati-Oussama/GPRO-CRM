package com.project.request;

import java.util.List;



public class ClientModel {

	private String nom;
	private String prenom;
	private String username;
	private String telephone;
	private String email;
	private String password;
	private String image;
	private Long societe;
	private String role;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getSociete() {
		return societe;
	}
	public void setSociete(Long societe) {
		this.societe = societe;
	}
	
	

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public ClientModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClientModel(String nom, String prenom, String username, String telephone, String email, String password, Long societe,String image) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.username = username;
		this.telephone = telephone;
		this.email = email;
		this.password = password;
		this.societe = societe;
		this.image = image;
	}
	@Override
	public String toString() {
		return "ClientModel [nom=" + nom + ", prenom=" + prenom + ", username=" + username + ", telephone=" + telephone
				+ ", email=" + email + ", password=" + password +", societe=" + societe  + "]";
	}
	
	
}
