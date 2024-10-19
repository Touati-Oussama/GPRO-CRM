package com.project.response;

public class SpecialiteDTO {
	
	private Long id;
	private String specialite;
	private int nbEmpl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSpecialite() {
		return specialite;
	}
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	public int getNbEmpl() {
		return nbEmpl;
	}
	public void setNbEmpl(int nbEmpl) {
		this.nbEmpl = nbEmpl;
	}
	@Override
	public String toString() {
		return "SpecialiteDTO [id=" + id + ", specialite=" + specialite + ", nbEmpl=" + nbEmpl + "]";
	}
	public SpecialiteDTO(Long id, String specialite, int nbEmpl) {
		super();
		this.id = id;
		this.specialite = specialite;
		this.nbEmpl = nbEmpl;
	}
	public SpecialiteDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
