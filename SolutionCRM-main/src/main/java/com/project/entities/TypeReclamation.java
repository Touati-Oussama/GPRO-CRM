package com.project.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name="type_reclamation" )
public class TypeReclamation {

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Long type_id;
	
	private String type;

	
	
	public Long getType_id() {
		return type_id;
	}

	public void setType_id(Long type_id) {
		this.type_id = type_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Type_Reclamation [type_id=" + type_id + ", type=" + type + "]";
	}

	public TypeReclamation(String type) {
		super();
		this.type = type;
	}

	public TypeReclamation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
