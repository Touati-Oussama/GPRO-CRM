package com.project.response;

public class ReclamationDetails {

	private Long total;
	private Long encours;
	private Long cloture;
	private Long nouv;
	public Long getNouv() {
		return nouv;
	}
	public void setNouv(Long nouv) {
		this.nouv = nouv;
	}
	public ReclamationDetails(Long total, Long encours, Long cloture) {
		super();
		this.total = total;
		this.encours = encours;
		this.cloture = cloture;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getEncours() {
		return encours;
	}
	public void setEncours(Long encours) {
		this.encours = encours;
	}
	public Long getCloture() {
		return cloture;
	}
	public void setCloture(Long cloture) {
		this.cloture = cloture;
	}
	public ReclamationDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
