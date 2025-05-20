package fr.unice.gfarce.identity ;

import javax.persistence.*;

/**
 * Classe Identite <br>
 * Super-classe poss&egrave;dant les propri&eacute;t&eacute; et m&eacute;thodes communes &agrave; Candidat et RespFormation. 
 */
@Entity 
@Table(name="GFARCE_IDENTITE")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public  class Identite{
	
	public enum TypeIdentite{CANDIDAT, FORMATEUR};
	
	@Id
	@GeneratedValue
	private int id;
	
	private String nom;
	
	private String prenom;
	
	private String sex;
	
	private String email;
	
	@Enumerated(EnumType.ORDINAL)
	private TypeIdentite type;
	
/**
 * Constructeur vide
 */
	public Identite(){}

/**
 * Constructeur pour les recherches db4o
 */
	public Identite(int id){
		this.id = id;
	}

	public Identite (String nom,String prenom,String sex, String email, TypeIdentite type){
		this.nom = nom;
		this.prenom=prenom;
		this.sex = sex;
		this.email = email;
		this.type= type;
	}

/**
 * Accesseurs en lecture 
 */
	public int getId(){
		return this.id;
	}

	public String getNom(){
		return this.nom;
	}

	public String getPrenom(){
		return this.prenom;
	}

	public String getSex(){
		return this.sex;
	}

	public String getEmail(){
		return this.email;
	}
	
	public TypeIdentite getType() {
		return type;
	}
	
/**
 * Accesseurs en &eacute;criture
 */
	public void setId(int id) {
		this.id = id;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setType(TypeIdentite type) {
			this.type = type;
	}
}
