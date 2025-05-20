/**
 * Classe OracleIdentiteDao
 */

package fr.unice.gfarce.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.unice.gfarce.identity.Candidat;
import fr.unice.gfarce.identity.RespFormation;
import fr.unice.gfarce.identity.Identite;
import fr.unice.gfarce.identity.Identite.TypeIdentite;
import javax.persistence.PersistenceContext;

/**
 *  Classe permettant d'acc&eacute;der &agrave; la base de donn&eacute;es oracle et d'en modifier les Identites.
 */

public class OracleIdentiteDao implements IdentiteDao{

	String persistUnitName = "Identite";
	@PersistenceContext
	private EntityManager em;

	public static OracleIdentiteDao getDAO() {
		return new OracleIdentiteDao();
	}

/**
 * Ajoute une Identite dans la base de donn&eacute;es.
 * on ajoute &eacute;galement le candidat &agrave; la  formation nomForm.
 * @param identite
 * @param nomForm
 * @param dateLimite
 * @return 
 */
	@Override
	public void insert(Identite identite, String nomForm, Calendar dateLimite) {
		EntityManagerFactory emf = null;
		em = null;
		EntityTransaction tx = null;		
		try{ 
			emf = Persistence.createEntityManagerFactory(persistUnitName);
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			em.persist(identite);
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		finally {	
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
	}
	
/**
 * Modifie une Identite deja pr&eacute;sente dans la base de donn&eacute;es.
 * @param identite
 * @return 
 */
	@Override
	public void update(Identite identite) {
		EntityManagerFactory emf = null;
		em = null;
		EntityTransaction tx = null;
		try{ 
			emf = Persistence.createEntityManagerFactory(persistUnitName);
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			
			Identite i = em.find(identite.getClass(), identite.getId());
			i.setEmail(identite.getEmail());
			i.setNom(identite.getNom());
			i.setPrenom(identite.getPrenom());
			i.setSex(identite.getSex());
			i.setType(identite.getType());
			
			em.merge(identite);
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		finally {	
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
	}
		
/**
 * Supprime une Identite dans la base de donn&eacute;es.
 * @param identite
 * @return 
 */
	@Override
	public void delete(Identite identite){	
		EntityManagerFactory emf = null;
		em = null;
		EntityTransaction tx = null;

		try{ 
			emf = Persistence.createEntityManagerFactory(persistUnitName);
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();	

			Identite ident = em.find(identite.getClass(), identite.getId());

			em.remove(ident);
			tx.commit();
		}
		catch(Exception e1) {
			e1.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		finally {	
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
	}
	
/**
 * Recherche les Identit&eacute;s de la base de donn&eacute;es selon certains crit&egraves;.
 * Retourne toutes les Identites si tous les parametres sont null.
 * @param nom
 * @param prenom
 * @param sex
 * @param email
 * @param type
 * @return List<Identite>
 */
	@SuppressWarnings("unchecked")
	public List<Identite> find(String nom,String prenom,String sex, String email, TypeIdentite type) {
		String a,b,c,e;
		if(nom==null)
			a = "ident.nom like '%'";
		else a="ident.nom='"+nom+"'" ;
		
		if(prenom==null)
			b = "ident.prenom like '%'";
		else b="ident.prenom='"+prenom+"'" ;

		if(sex==null)
			c = "ident.sex like '%'";
		else c="ident.sex='"+sex+"'" ;

		if(type==TypeIdentite.CANDIDAT){
			e="ident.type= :CANDIDAT" ; 
		}
		else if(type==TypeIdentite.FORMATEUR){
			e="ident.type= :FORMATEUR" ; 
		}
		else
			e = "ident.type like '%'";
		
		String s = "select ident from Identite as ident"
			+" where "+a+" and "+b+" and "+c+" and "+e;
		EntityManagerFactory emf = null;
		em = null;
		EntityTransaction tx = null;
		List<Identite> list=null;
		try{ 
			emf = Persistence.createEntityManagerFactory(persistUnitName);
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();		
			
			if(type==TypeIdentite.CANDIDAT){
				list = em.createQuery(s).setParameter("CANDIDAT", TypeIdentite.CANDIDAT).getResultList();
			}
			else if(type==TypeIdentite.FORMATEUR){
				list = em.createQuery(s).setParameter("FORMATEUR", TypeIdentite.FORMATEUR).getResultList();
			}
			else
				list = em.createQuery(s).getResultList();
		}
		catch(Exception e1) {
			e1.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}
		finally {	
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
		return list;
	}


/**	
 * Appelle la m&eacute;thode find() avec le type Candidat.
 * @param nom
 * @param prenom
 * @param sex
 * @param email
 * @return Candidat[]
 */
	@Override
	public Candidat[] findCandidat(String nom,String prenom,String sex, String email){
		List<Identite> resultList = find(nom,prenom,sex,email,TypeIdentite.CANDIDAT);
		if(resultList != null){
			Candidat[] result=resultList.toArray(new Candidat[resultList.size()]);
			return result;
		}
		else{
			return null;
		}
	}
	
/**	
 * Appelle la m&eacute;thode find() avec le type RespFormation.
 * @param nom
 * @param prenom
 * @param sex
 * @param email
 * @return RespFormation[]
 */
	@Override
	public RespFormation[] findRespForm(String nom,String prenom,String sex, String email){
		List<Identite> resultList = find(nom,prenom,sex,email,TypeIdentite.FORMATEUR);
		if(resultList != null){
			RespFormation[] result=resultList.toArray(new RespFormation[resultList.size()]);
			return result;
		}
		else{
			return null;
		}
	}
}
