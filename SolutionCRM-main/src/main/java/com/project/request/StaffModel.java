package com.project.request;

import java.util.List;

public class StaffModel {

	private String nom;
	private String prenom;
	private String username;
	private String telephone;
	private String email;
	private String password;
	private List<String> specialites;
	private String image;
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<String> getSpecialites() {
		return specialites;
	}
	public void setSpecialites(List<String> specialite) {
		this.specialites = specialite;
	}
	public StaffModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UserModel [nom=" + nom + ", prenom=" + prenom + ", telephone=" + telephone + ", email=" + email
				+ ", password=" + password + ", specialite=" + specialites + "]";
	}

	
	
	
}
