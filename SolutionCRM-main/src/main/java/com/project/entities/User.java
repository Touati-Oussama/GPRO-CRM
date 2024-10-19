package com.project.entities;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class User {
	
		@Id
		@GeneratedValue (strategy=GenerationType.IDENTITY)
		private Long user_id;
		@Column(unique=true)
		private String username;

		@Column(unique=true)
		private String email;
		private String nom;
		private String prenom;
		private String telephone;
		private String password;
		private boolean enabled = true; 
		private boolean connected;
		
		@JsonIgnore
		@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
		@JoinTable(name="user_role",joinColumns = @JoinColumn(name="user_id") ,
		 inverseJoinColumns = @JoinColumn(name="role_id"))
		private List<Role> roles;
		 
		 	@JsonIgnore
			@OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
			@JoinColumn(referencedColumnName = "id")
			private ImageDB image;
			  
			@JsonIgnore
			@ManyToMany(cascade=CascadeType.ALL)
			@JoinTable(name="specialite_user",joinColumns = @JoinColumn(name="user_id") ,
			inverseJoinColumns = @JoinColumn(name="spec_id"))
			private List<Sepicialite> specialities;
			
			@JsonIgnore
			@ManyToOne
		    @JoinColumn(name="societe_id")
		    private Societe societe;
			
		
		
		public Long getUser_id() {
			return user_id;
		}
		public void setUser_id(Long user_id) {
			this.user_id = user_id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public Boolean getEnabled() {
			return enabled;
		}
		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}
		public List<Role> getRoles() {
			return roles;
		}
		public void setRoles(List<Role> roles) {
			this.roles = roles;
		}
		
		
		
		public boolean isConnected() {
			return connected;
		}
		public void setConnected(boolean connected) {
			this.connected = connected;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
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
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public ImageDB getImage() {
			return image;
		}
		public void setImage(ImageDB image) {
			this.image = image;
		}
		public List<Sepicialite> getSpecialities() {
			return specialities;
		}
		public void setSpecialities(List<Sepicialite> specialities) {
			this.specialities = specialities;
		}
		public Societe getSociete() {
			return societe;
		}
		public void setSociete(Societe societe) {
			this.societe = societe;
		}
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		
		
		public User(Long user_id, String username, String email, String nom, String prenom, String telephone,
				String password, boolean enabled, List<Role> roles, ImageDB image, List<Sepicialite> specialities,
				Societe societe) {
			super();
			this.user_id = user_id;
			this.username = username;
			this.email = email;
			this.nom = nom;
			this.prenom = prenom;
			this.telephone = telephone;
			this.password = password;
			this.enabled = enabled;
			this.roles = roles;
			this.image = image;
			this.specialities = specialities;
			this.societe = societe;
		}
		
		
		@Override
		public String toString() {
			return "User [user_id=" + user_id + ", username=" + username + ", email=" + email + ", nom=" + nom
					+ ", prenom=" + prenom + ", telephone=" + telephone + ", password=" + password + ", enabled="
					+ enabled + ", roles=" + roles + ", image=" + image + ", specialities=" + specialities
					+ ", societe=" + societe + "]";
		}
		public User() {
			super();
			// TODO Auto-generated constructor stub
		}
	 
	 
	}

