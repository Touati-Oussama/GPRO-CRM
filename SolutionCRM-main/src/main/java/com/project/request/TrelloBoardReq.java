package com.project.request;

public class TrelloBoardReq {

	private String idBoard;
	private String idListToDo;
	private String idListDoing;
	private String idListDone;
	private Long projet;
	public String getIdBoard() {
		return idBoard;
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
	public Long getProjet() {
		return projet;
	}
	public void setIdBoard(String idBoard) {
		this.idBoard = idBoard;
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
	public void setProjet(Long projet) {
		this.projet = projet;
	}
	public TrelloBoardReq() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
