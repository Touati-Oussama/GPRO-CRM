package com.project.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TrelloBoard {

	@Id
	private String id;
	private String idListToDo;
	private String idListDoing;
	private String idListDone;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Projet projet;

	public String getId() {
		return id;
	}

	public String getIdListToDo() {
		return idListToDo;
	}

	public String getIdListDoing() {
		return idListDoing;
	}

	public String getIdListDone() {
		return idListDone;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIdListToDo(String idListToDo) {
		this.idListToDo = idListToDo;
	}

	public void setIdListDoing(String idListDoing) {
		this.idListDoing = idListDoing;
	}

	public void setIdListDone(String idListDone) {
		this.idListDone = idListDone;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	public TrelloBoard() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
