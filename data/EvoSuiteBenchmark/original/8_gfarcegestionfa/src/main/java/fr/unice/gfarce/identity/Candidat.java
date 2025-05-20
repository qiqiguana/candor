package fr.unice.gfarce.identity;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.persistence.*;


/**
 * Classe Candidat <br>
 * Sous-classe de cr&eacute;ation des candidats &agrave; partir des champs recuper&eacute;s de l'interface graphique.
 * H&eacute;rite de la classe Identite.
 */
@Entity
@Table(name="GFARCE_CANDIDAT")
@DiscriminatorValue("C")
public class Candidat extends Identite {

	@Temporal(TemporalType.DATE)
	private java.util.Calendar date_naissance;

	private String diplome;

	@Lob
	public byte[] photo;

	private String nationalite;

	private int bourse ;

	private int acceptation ; 

	private Formation formation ;


/**
 * Constructeurs
 */
	public Candidat(){
		super();
	}

	public Candidat(String nom,String prenom,String sex, String email, TypeIdentite type, Calendar date_naissance,String diplome,String photo,String nationalite,int bourse,int acceptation,Formation f) throws IOException{
		super(nom,prenom,sex,email, type);
		this.date_naissance = date_naissance;
		this.diplome = diplome;
		this.photo = photoToByte(photo);
		this.date_naissance = date_naissance;
		this.nationalite = nationalite;	
		this.bourse = bourse;
		this.acceptation = acceptation;
		this.formation = f;
	}

	public Candidat(String nom,String prenom,String sex, String email, Calendar date_naissance,String diplome,String photo,String nationalite,int bourse,int acceptation,Formation f) throws IOException{
		this(nom, prenom, sex, email, TypeIdentite.CANDIDAT, date_naissance, diplome, photo, nationalite,bourse,acceptation,f);
	}
	
	/**
	 * Conversion de l'image gif en tableau de byte.
	 * @param path
	 * @return byte[]
	 * @throws IOException
	 */
	private byte[] photoToByte(String path) throws IOException{
		File f = new File(path);
		byte[] b =null;
		if(f.exists()){
			FileImageInputStream fi;
			fi = new FileImageInputStream(f);
			int taille = (int)f.length() * 8 ;

			b = new byte[taille];
			fi.read(b) ;
		}
		return b;
	}

/**
 * Enregistre la photo dans le dossier img.
 * Si le tableau de byte est null aucune image n'est cr&eacute;&eacute;e.
 * Le nom de la photo est compos&eacute; du nom du Candidat et de son id.
 * @param
 * @return
 * @throws IOException
 */
	public void savePhoto() throws IOException{				
		if(this.photo != null){
			String filename = ""+this.getNom()+"-"+this.getId();
			File f  = new File("img",filename +".gif");
			FileImageOutputStream fo = new FileImageOutputStream(f);
			fo.write(this.photo);
		}
	}

	// method pour alex 
	// a completer suivant si alex a besoin d'un Image ou une bufferedImage ou autre
	// permet d'afficher l'image sans lenregistrer en dur
/**
 * 
 * @param
 * @return
 */
	public void afficherPhoto(){
	}

/**
 * Accesseurs en lecture
 */
	public java.util.Calendar getDateNaissance (){
		return date_naissance;
	}

	public String getDiplome(){
		return this.diplome;

	}

	public byte[] getPhoto(){
		return this.photo;
	}

	public String getNationalite(){
		return nationalite;
	}
	
	@ManyToOne
	public Formation getFormation(){
		return this.formation;
	}
	
	public int getBource(){
		return this.bourse;
	}
	
	public int getAcceptation(){
		return this.acceptation;
	}

/**
 * Accesseurs en &eacute;criture
 */
	public void setDate_naissance(java.util.Calendar date_naissance) {
		this.date_naissance = date_naissance;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}

	public void setPhoto(String photo) throws IOException {
		this.photo = photoToByte(photo);
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public void setBource(int bourse){
		this.bourse = bourse;
	}

	public void setAcceptation(int a){
		this.acceptation = a;
	}

	public void setFormation(Formation f){
		this.formation = f;
	}
}
