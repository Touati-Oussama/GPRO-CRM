package com.project.response;

public class ReclamationDetailsClient {

	private long total;
	private long attente;
	private long encours;
	private long cloture;
	public long getTotal() {
		return total;
	}
	public long getAttente() {
		return attente;
	}
	public long getEncours() {
		return encours;
	}
	public long getCloture() {
		return cloture;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public void setAttente(long attente) {
		this.attente = attente;
	}
	public void setEncours(long encours) {
		this.encours = encours;
	}
	public void setCloture(long cloture) {
		this.cloture = cloture;
	}
	public ReclamationDetailsClient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
