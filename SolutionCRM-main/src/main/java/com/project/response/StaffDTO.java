package com.project.response;

import java.util.List;

public class StaffDTO {

	private Long id;
	private String nom;
	private String prenom;
	private String username;
	private String telephone;
	private String email;
	private String password;
	private List<String> specialites;
	private boolean isEnabled;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
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
	public List<String> getSpecialites() {
		return specialites;
	}
	public void setSpecialites(List<String> specialites) {
		this.specialites = specialites;
	}
	
	
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public StaffDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
