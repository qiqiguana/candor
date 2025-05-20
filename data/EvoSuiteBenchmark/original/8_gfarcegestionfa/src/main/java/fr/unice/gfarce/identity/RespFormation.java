package fr.unice.gfarce.identity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

/**
 * Classe RespFormation <br>
 * Sous-classe de cr&eacute;ation des responsables de Formation &agrave; partir des champs recuper&eacute;s de l'interface graphique.
 * H&eacute;rite de la classe Identite.
 */
@Entity
@Table(name="GFARCE_RESPFORMATION")
@DiscriminatorValue("R")
public class RespFormation extends Identite{
	
	private String telephone;
	
	@OneToMany(mappedBy="responsable",cascade={CascadeType.ALL})
	private Collection<Formation> formations = new ArrayList<Formation>();
	
/**
 * Constructeurs	
 */
	public RespFormation(){
		super();
	}
	
	public RespFormation(String nom,String prenom,String sex,String email, TypeIdentite type, String telephone){
		super(nom,prenom,sex,email, type);
		this.telephone=telephone;
	}
	
/**
 * Accesseurs en lecture 
 */
	public String getTelephone(){
		return this.telephone;
	}

	public Collection<Formation> getFormation() {
	    return this.formations;
	}
	
/**
 * Accesseur en &eacute;criture
 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
/**
 * Ajout de la nouvelle Formation dans l'ArrayList<Formation>.
 * @param formation
 * @return
 */
	public void ajouterFormation(Formation formation) {
		formations.add(formation);
	}

/**
 * Suppression de la Formation dans l'ArrayList<Formation>.
 * @param formation
 * @return
 */
	public void supprimerFormation(Formation formation) {
		if(formations.contains(formation))
			formations.remove(formation);
	}

/**
 * Modification de la Formation dans l'ArrayList<Formation>.
 * @param formation
 * @return
 */
	public void modifierFormation(Formation formation) {
		Formation[] f = (Formation[])formations.toArray(new Formation[formations.size()]);
		for(int i=0; i<f.length; i++){
			if(f[i].getTitre_formation().equals(formation.getTitre_formation()) 
					&& f[i].getDate_limite_candidature().equals(formation.getDate_limite_candidature())){
				f[i]=formation;

			}
		}
	}
}
