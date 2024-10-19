package com.project.response;

public class ReclamationDetailsParDev {

	private String cloture = "Cloturé";
	private Long nbReclamationClo;
	private String encours = "En Cours";
	private Long nbReclamationEn;
	
	
	public String getCloture() {
		return cloture;
	}


	public Long getNbReclamationClo() {
		return nbReclamationClo;
	}


	public String getEncours() {
		return encours;
	}


	public Long getNbReclamationEn() {
		return nbReclamationEn;
	}


	public void setCloture(String cloture) {
		this.cloture = cloture;
	}


	public void setNbReclamationClo(Long nbReclamationClo) {
		this.nbReclamationClo = nbReclamationClo;
	}


	public void setEncours(String encours) {
		this.encours = encours;
	}


	public void setNbReclamationEn(Long nbReclamationEn) {
		this.nbReclamationEn = nbReclamationEn;
	}


	public ReclamationDetailsParDev() {
		super();
		// TODO Auto-generated constructor stub
	}
}
