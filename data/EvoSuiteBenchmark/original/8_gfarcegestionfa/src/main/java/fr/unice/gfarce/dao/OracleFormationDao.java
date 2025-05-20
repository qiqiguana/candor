/**
 * Classe OracleFormationDao
 */

package fr.unice.gfarce.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import java.util.*;

import fr.unice.gfarce.identity.Formation;

/**
 *  Classe permettant d'acc&eacute;der a la base de donnees oracle et d'en modifier les Formations.
 */

public class OracleFormationDao implements FormationDao{

	String persistUnitName = "Identite";
	@PersistenceContext
	private EntityManager em;

	
/**
 * Ajoute une formation dans la base de donn&eacute;es.
 * @param formation
 * @param nomResp
 * @param prenomResp
 * @return
 */
	@Override
	public void insert(Formation formation,String nomResp, String prenomResp) {	
		EntityManagerFactory emf = null;
		EntityTransaction tx = null;
		try{ 
			emf = Persistence.createEntityManagerFactory(persistUnitName);
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			em.persist(formation);
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
 * Modifie une Formation deja pr&eacute;sente dans la base de donn&eacute;es.
 * @param f
 * @return 
 */
	@Override
	public void update(Formation formation) {
		EntityManagerFactory emf = null;
		EntityTransaction tx = null;		
		try{ 
			emf = Persistence.createEntityManagerFactory(persistUnitName);
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			Formation f = em.find(formation.getClass(), formation.getId());
			if(f==null){
				throw new DaoException("Formation a updater '"+formation.getTitre_formation()+"' non trouvee", 2);
			}
			else{
				f.setDate_limite_candidature(formation.getDate_limite_candidature());
				f.setMontant_inscription(formation.getMontant_inscription());
				f.setTitre_formation(formation.getTitre_formation());
				f.setCandidat(formation.getCandidat());
				f.setResponsable(formation.getResponsable());

				em.merge(f);
				tx.commit();
			}
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
 * Supprime une Formation de la base de donn&eacute;es.
 * @param formation
 * @return
 */
	@Override
	public void delete(Formation formation) {
		EntityManagerFactory emf = null;
		EntityTransaction tx = null;	
		em=null;
		try{ 
			emf = Persistence.createEntityManagerFactory(persistUnitName);
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			
			Formation f = em.find(formation.getClass(), formation.getId());
			if(f==null){
				throw new DaoException("Formation a supprimer '"+formation.getTitre_formation()+"' non trouvee", 2);
			}
			else{
				em.remove(f);
				tx.commit();
			}
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
 * Recherche des Formations de la base de donn&eacute;es selon certains crit&egrave;res.
 * Retourne toutes les formations de la base si name=date=null
 * @param name
 * @param date
 * @return Formation[]
 */
	@SuppressWarnings("unchecked")
	@Override
	public Formation[] findFormation(String name,Calendar date){
		EntityManagerFactory emf = null;
		EntityTransaction tx = null;
		em=null;
		Formation result[]=null;
		try{ 
			emf = Persistence.createEntityManagerFactory(persistUnitName);
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			List<Formation> resultList=null;
			String select="select f from Formation as f ";
			if(name!=null && date!=null){
				select+="where f.titre_formation='"+name+"' and f.date_limite_candidature=?1";
				resultList = em.createQuery(select)
								.setParameter(1, date, TemporalType.DATE).getResultList();
			}else if(name !=null && date==null){
				select+="where f.titre_formation='"+name+"'";
				resultList = em.createQuery(select)
								.getResultList();
			}
			else if(name==null && date==null){
				resultList = em.createQuery(select).getResultList();
			}
			if(resultList!=null){
				result=resultList.toArray(new Formation[resultList.size()]);
			}
			else{
				throw new DaoException("Formation '"+name+"' non trouvee", 2);
			}
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
		return result;
	}
}
