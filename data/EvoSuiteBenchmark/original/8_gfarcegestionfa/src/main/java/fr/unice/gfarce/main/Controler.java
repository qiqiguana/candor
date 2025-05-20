package fr.unice.gfarce.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import fr.unice.gfarce.dao.DaoFactory;
import fr.unice.gfarce.dao.DaoFactory.TypeFabrique;
import fr.unice.gfarce.dao.DaoFactoryException;
import fr.unice.gfarce.dao.FormationDao;
import fr.unice.gfarce.dao.IdentiteDao;
import fr.unice.gfarce.identity.Candidat;
import fr.unice.gfarce.identity.Formation;
import fr.unice.gfarce.identity.Identite;
import fr.unice.gfarce.identity.Identite.TypeIdentite;
import fr.unice.gfarce.identity.RespFormation;
import fr.unice.gfarce.interGraph.FenetreChoix;
import fr.unice.gfarce.xls.WritingXLS;

/**
 * Fait la liaison entre l'interface graphique et le moteur de l'application
 * Rend comprehensible les requetes de l'interface graphique pour les bases de
 * donnees
 * @author scarankle
 *
 */
public class Controler {

	DaoFactory df;
	IdentiteDao idao;
	FormationDao fdao;
	Candidat[] tabcand;
	String formationName;
	TypeFabrique tf;
	/**
	 * Constructeur initialisant les dao et le type de la base
	 * @param tf type de la base
	 * @throws DaoFactoryException
	 */
	public Controler() throws DaoFactoryException{
		FenetreChoix chFen = new FenetreChoix(this);
		chFen.setVisible(true);		
	}

	public void setTypeDataBase(String type) throws DaoFactoryException{
		if(type.equals("db4o")){
			bdChoice(TypeFabrique.DB4O);
		}
		if(type.equals("oracle")){
			bdChoice(TypeFabrique.ORACLE);
		}
	}

	public void launchDatabase() throws IOException{
		
		idao = df.getIdentiteDao();
		fdao = df.getFormationDao();
		tabcand=null;
		formationName ="";
	}

	/**
	 * 
	 */
	private void bdChoice(TypeFabrique tfab) throws DaoFactoryException{
		DaoFactory.setTypeDao(tfab);
		df=DaoFactory.getDaoFactory();
	}

	/**
	 * Cree un candidat ou un responsable de formation et l'insert dans la base
	 * @param nom
	 * @param prenom
	 * @param sex
	 * @param email
	 * @param type
	 * @param date_naissance
	 * @param diplome
	 * @param photo
	 * @param nationalite
	 * @param telephone
	 * @param bourse
	 * @param acceptation
	 * @param formation
	 * @throws IOException 
	 * @return le candidat ou le responsable de formation
	 */
	public Identite createIdentite(String nom,String prenom,String sex, String email, TypeIdentite type,String date_naissance,String diplome,String photo,String nationalite,String telephone,int bourse,int acceptation,String formation) throws IOException{
		Identite iden=null;
		switch(type){
		case CANDIDAT:
			Calendar c= creerCalendar(date_naissance);
			String[] forma = formation.split("\\.");
			Formation[] form = fdao.findFormation(forma[0],creerCalendar(forma[1]));
			iden = new Candidat(nom, prenom, sex, email,type,c, diplome, photo, nationalite,bourse,acceptation,form[0]);
			idao.insert(iden, forma[0], creerCalendar(forma[1]));
			break;
		case FORMATEUR:
			iden = new RespFormation(nom,prenom,sex,email,type,telephone);		
			idao.insert(iden,null,null);
			break;
		default:
			System.err.println("gros probleme");
		}
		return iden;
	}

	/**
	 * cree un calendar a partir d'une string date de la forme dd/mm/yyyy
	 * @param date
	 * @return
	 */
	private Calendar creerCalendar(String date){
		Calendar c = Calendar.getInstance();
		String[] d = date.split("/");
		c.set(Integer.parseInt(d[2]),Integer.parseInt(d[1]),Integer.parseInt(d[0]));
		return c;
	}

	/**
	 * cree une formation et l'enregistre dans la base 
	 * @param titre
	 * @param date_limite
	 * @param montant
	 * @param responsable
	 * @return
	 */
	public Formation createFormation(String titre,String date_limite,int montant,String responsable){
		Calendar c = creerCalendar(date_limite);
		String[] respF = responsable.split(" ");
		RespFormation rf = idao.findRespForm(respF[0],respF[1],null,null)[0];
		Formation f = new Formation(titre,c,montant,rf);
		fdao.insert(f, respF[0], respF[1]);		
		return f;

	}
	/**
	 * Cherche des candidats dans la base
	 * @param nom
	 * @param prenom
	 * @return un tableau contenant les candidats
	 */
	public Candidat[] findCandidat(String nom,String prenom){
		return idao.findCandidat(nom, prenom,null,null);
	}

	/**
	 * Cherche des responsables de formation dans la base
	 * @param nom
	 * @param prenom
	 * @return un tableau contenant les responsables de formation
	 */
	public RespFormation[] findRespForm(String nom,String prenom){
		return idao.findRespForm(nom,prenom,null,null);
	}

	/**
	 * Cherche des formations dans la base
	 * @param name
	 * @param date
	 * @return un tableau contenant les formations
	 */
	public Formation[] findFormation(String name,String date){
		Calendar c=null;
		if(date!=null){
			c = creerCalendar(date);
		}
		return fdao.findFormation(name,c);
	}

	/**
	 * Retourne toutes les formations
	 * @return un tableau contenant toutes les formations
	 */
	public Formation[] findAllFormation(){
		return fdao.findFormation(null,null);
	}

	/**
	 * Retourne tous les Candidats d'une formation donnee
	 * @param formation
	 * @param date
	 * @return tableau contenant tous les etudiants d'une formation donnee
	 */
	public Candidat[] findCandidatInFormation(String formation, String date){
		Calendar c = creerCalendar(date);
		Formation[] form = fdao.findFormation(formation,c);
		Collection<Candidat> cand = form[0].getCandidat();
		this.tabcand = new Candidat[cand.size()];
		this.tabcand = cand.toArray(this.tabcand);
		return tabcand;		
	}

	/**
	 * Exporte sous forme de fichier excel les candidats d'une formation
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @throws IOException
	 * @throws ParseException
	 */
	public void exportExcel(String form) throws RowsExceededException, WriteException, IOException, ParseException{
		if(form != null){
			String[] f = form.split("\\.");
			this.tabcand = findCandidatInFormation(f[0],f[1]);
			if(this.tabcand == null){
				System.err.println("erreur tabcand null");
			}

			else{
				WritingXLS w = new WritingXLS();
				w.exportXLS(this.tabcand,formationName+"gfarce");
			}
		}
	}

	/**
	 * Fait un update d'un candidat
	 * @param nom
	 * @param prenom
	 * @param sex
	 * @param email
	 * @param date_naissance
	 * @param diplome
	 * @param photo
	 * @param nationalite
	 * @param bourse
	 * @param acceptation
	 * @param formation
	 * @throws Exception 
	 */
	public void updateCandidat(String nom,String prenom,String sex, String email,String date_naissance,String diplome,byte[] photo,String nationalite,int bourse,String acceptation,String formation) throws Exception{
		Candidat[] cand=idao.findCandidat(nom, prenom, null, null);
		Calendar c = creerCalendar(date_naissance);
		cand[0].setSex(sex);
		cand[0].setEmail(email);
		cand[0].setDate_naissance(c);
		cand[0].setBource(bourse);
		cand[0].setNationalite(nationalite);
		String[] forma = formation.split("\\.");
		Formation[] form = fdao.findFormation(forma[0],creerCalendar(forma[1]));
		cand[0].setFormation(form[0]);
		if(acceptation.equals("ok")){
			cand[0].setAcceptation(1);
		}
		else{
			cand[0].setAcceptation(0);
		}
		idao.update(cand[0]);
	}

	/**
	 * Fait un update d'un responsable
	 * @param nom
	 * @param prenom
	 * @param sex
	 * @param email
	 * @param telephone
	 * @throws Exception 
	 */
	public void updateResponsable(String nom,String prenom,String sex,String email,String telephone) throws Exception{
		RespFormation[] rf = idao.findRespForm(nom, prenom,null,null);
		rf[0].setSex(sex);
		rf[0].setTelephone(telephone);
		rf[0].setEmail(email);
		idao.update(rf[0]);
	}

	/**
	 * Fait un update d'une formation
	 * @param titre
	 * @param date_limite
	 * @param montant
	 * @param responsable
	 */
	public void updateFormation(String titre,String date_limite,int montant,String responsable){
		Formation[] f = fdao.findFormation(titre,creerCalendar(date_limite));
		f[0].setDate_limite_candidature(creerCalendar(date_limite));
		f[0].setMontant_inscription(montant);
		String[] resp = responsable.split(" ");
		RespFormation[] rf = idao.findRespForm(resp[0],resp[1],null,null);
		f[0].setResponsable(rf[0]);
		fdao.update(f[0]);
	}

	/**
	 * Enleve un candidat ou un responsable de formation de la base
	 * @param nom
	 * @param prenom
	 */
	public void deleteIdentite(String nom,String prenom,TypeIdentite type){
		if(type == TypeIdentite.FORMATEUR){
			RespFormation ident= idao.findRespForm(nom, prenom,null,null)[0];
			idao.delete(ident);
		}
		if(type== TypeIdentite.CANDIDAT){
			Candidat ident = idao.findCandidat(nom, prenom, null,null)[0];
			idao.delete(ident);
		}
	}

	/**
	 * Enleve une formation de la base
	 * @param nom
	 * @param date
	 */
	public void deleteFormation(String nom,String date){
		Formation form = fdao.findFormation(nom, creerCalendar(date))[0];
		fdao.delete(form);
	}
}
