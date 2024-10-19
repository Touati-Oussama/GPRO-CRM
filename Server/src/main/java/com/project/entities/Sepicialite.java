package com.project.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name="specialite" )
public class Sepicialite {

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Long spec_id;
	
	private String nom;


	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="specialite_user",joinColumns = @JoinColumn(name="spec_id") ,
	inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<User> staff;


	public Long getSpec_id() {
		return spec_id;
	}


	public void setSpec_id(Long spec_id) {
		this.spec_id = spec_id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}





	public List<User> getStaff() {
		return staff;
	}


	public void setStaff(List<User> staff) {
		this.staff = staff;
	}


	@Override
	public String toString() {
		return "Sepicialite [spec_id=" + spec_id + ", nom=" + nom + "]";
	}


	public Sepicialite(Long spec_id, String nom) {
		super();
		this.spec_id = spec_id;
		this.nom = nom;
	}


	public Sepicialite() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
