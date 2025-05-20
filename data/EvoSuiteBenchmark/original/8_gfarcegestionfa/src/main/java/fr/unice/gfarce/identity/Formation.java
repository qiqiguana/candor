package fr.unice.gfarce.identity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.*;


/**
 * Classe Formation <br>
 * Classe de cr&ecute;ation des Formations &agrave; partir des champs recuper&eacute;s de l'interface graphique.
 */
@Entity
@Table(name="GFARCE_FORMATION")
public class Formation{
	@Id
	@GeneratedValue
	int id;
	
	String titre_formation;
	
	@Temporal(TemporalType.DATE)
	private java.util.Calendar date_limite_candidature;
	
	int montant_inscription;
	
	RespFormation responsable;
		
	@OneToMany(mappedBy="formation",cascade={CascadeType.ALL})
	private Collection<Candidat> candidats = new ArrayList<Candidat>();
	
/**
 * Constructeur vide
 */
	public Formation(){}
	
/**
 * Constructeur pour les recherches db4o
 */
	public Formation(int id){
		this.id=id;
	}

	public Formation(String titre,Calendar date_limite,int montant,RespFormation responsable){
		this.titre_formation = titre;
		this.date_limite_candidature = date_limite;
		this.montant_inscription = montant;
		this.responsable = responsable;
	}

/**
 * Accesseurs en lecture 
 */
	public int getId(){
		return this.id;
	}
	public String getTitre_formation() {
		return titre_formation;
	}

	public Calendar getDate_limite_candidature() {
		return date_limite_candidature;
	}

	public int getMontant_inscription() {
		return montant_inscription;
	}
	
	@ManyToOne
	public RespFormation getResponsable() {
		return responsable;
	}
	
	public Collection<Candidat> getCandidat() {
	    return this.candidats;
	}

/**
 * Accesseurs en &eacute;criture
 */
	public void setDate_limite_candidature(Calendar date_limite_candidature) {
		this.date_limite_candidature = date_limite_candidature;
	}
	
	public void setTitre_formation(String titre_formation) {
		this.titre_formation = titre_formation;
	}
	
	public void setMontant_inscription(int montant_inscription) {
		this.montant_inscription = montant_inscription;
	}

	public void setResponsable(RespFormation responsable) {
		this.responsable = responsable;
	}
	
	public void setCandidat(Collection<Candidat> candidats){
		this.candidats = candidats;		
	}

/**
 * Ajout du nouveau candidat dans l'ArrayList<Candidat>.
 * @param candidat
 * @return
 */
	public void ajouterCandidat(Candidat candidat) {
		candidats.add(candidat);
	}	
	
/**
 * Suppression du candidat dans l'ArrayList<Candidat>.
 * @param candidat
 * @return
 */
	public void supprimerCandidat(Candidat candidat) {
		if(candidats.contains(candidat)){
			candidats.remove(candidat);
		}
		else System.out.println("pas trouve");
	}

/**
 * Modification du candidat dans l'ArrayList<Candidat>.
 * @param candidat
 * @return
 */
	public void modifierCandidat(Candidat candidat) {
		Candidat[] c = (Candidat[])candidats.toArray(new Candidat[candidats.size()]);
		for(int i=0; i<c.length; i++){
			if(c[i].getNom().equals(candidat.getNom()) 
					&& c[i].getPrenom().equals(candidat.getPrenom())){
				c[i]=candidat;				
			}
		}
	}
}
