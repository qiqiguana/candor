package db;

/**
 * This class is the interface to the database. It provides all CRUD
 * services and queries. Every single access to the database happens in this class.
 * 
 * @author Daniel Tunjic
 *
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import logging.Logging;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import client.gui.StartFrame;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class BombermanDBService {

	protected BombermanDBService() {
		try {
			DBEntityManagerFactory.getInstance();
		} catch (DBException ex) {
			DBEntityManagerFactory.createEntityManagerFactory();
		}
	}

	private static Logging logger = Logging.getInstance();

	/**
	 * This method writes a single user into the database
	 * 
	 * @param user
	 * @throws DBException
	 */

	public void saveGameUser(DBGameUser user) throws DBException {
		logger.log(Level.INFO, this, "db: saveGameUser called "+user);
		EntityManagerFactory emf = null;
		try {
			emf = DBEntityManagerFactory.getInstance();
		} catch (HibernateException ex) {
			throw new DBException(ex.getMessage());
		}

		EntityManager em = null;
		EntityTransaction tx = null;

		try {

			em = emf.createEntityManager();
			tx = em.getTransaction();

			tx.begin();
			em.persist(user);
			tx.commit();

		} catch (HibernateException ex) {

			throw new DBException(ex.getMessage());

		} finally {

			try {
				if (em != null && em.isOpen())
					em.close();
			} catch (Exception exCl) {
				logger.log(Level.ERROR, this, exCl.toString());
			}

		}
	}

	/**
	 * This method updates a single user in the database
	 * 
	 * @param user
	 * @throws DBException
	 */

	public void updateGameUser(DBGameUser user) throws DBException {
		logger.log(Level.INFO, this, "db: updateGameUser called "+user);
		EntityManagerFactory emf = null;
		try {
			emf = DBEntityManagerFactory.getInstance();
		} catch (HibernateException ex) {
			throw new DBException(ex.getMessage());
		}

		EntityManager em = null;
		EntityTransaction tx = null;

		try {

			em = emf.createEntityManager();
			tx = em.getTransaction();

			tx.begin();
			DBGameUser userx = em.find(DBGameUser.class, user.getName());
			userx.setScore(user.getScore());
			tx.commit();

		} catch (HibernateException ex) {

			throw new DBException(ex.getMessage());

		} finally {

			try {
				if (em != null && em.isOpen())
					em.close();
			} catch (Exception exCl) {
				logger.log(Level.ERROR, this, exCl.toString());
			}

		}
	}

	/**
	 * This method reads a single user from the database
	 * 
	 * @param user
	 * @throws DBException
	 */

	public DBGameUser getDBUser(String name) throws DBException {
		logger.log(Level.INFO, this, "db: getDBGameUser called");
		DBGameUser gU = new DBGameUser();
		EntityManagerFactory emf = null;

		try {
			emf = DBEntityManagerFactory.getInstance();
		} catch (HibernateException ex) {
			throw new DBException(ex.getMessage());
		}

		EntityManager em = null;

		try {

			em = emf.createEntityManager();

			List userList = em.createQuery(
					"from DBGameUser where name='" + name + "'")
					.getResultList();

			if (userList.size() == 0) {
				logger.log(Level.ERROR, this, "user does not exist");
			} else
				gU = (DBGameUser) userList.get(0);

			em.close();

		} catch (HibernateException ex) {

			throw new DBException(ex.getMessage());

		} finally {

			try {
				if (em != null && em.isOpen())
					em.close();
			} catch (Exception exCl) {
				logger.log(Level.ERROR, this, exCl.toString());
			}
		}
		if (gU.getScore() == null) {
			gU.setScore(0);
		}

		return gU;
	}

	/**
	 * This method deletes a single user from the database
	 * 
	 * @param user
	 * @throws DBException
	 */

	public void deleteDBUser(String name) throws DBException {
		logger.log(Level.INFO, this, "db: deleteDBGameUser called "+name);
		EntityManagerFactory emf = null;

		try {
			emf = DBEntityManagerFactory.getInstance();
		} catch (HibernateException ex) {
			throw new DBException(ex.getMessage());
		}

		EntityManager em = null;
		EntityTransaction tx = null;

		try {

			em = emf.createEntityManager();
			tx = em.getTransaction();

			tx.begin();
			int deleted = em.createQuery(
					"DELETE FROM DBGameUser where NAME = '" + name + "'")
					.executeUpdate();
			tx.commit();

			if (deleted == 0) {
				logger.log(Level.ERROR, this, "user not deleted");
			}

			em.close();

		} catch (HibernateException ex) {

			throw new DBException(ex.getMessage());

		} finally {

			try {
				if (em != null && em.isOpen())
					em.close();
			} catch (Exception exCl) {
				logger.log(Level.ERROR, this, exCl.toString());
			}
		}
	}

	/**
	 * This method deletes a single user from the database
	 * 
	 * @param user
	 * @throws DBException
	 */

	public void updateScore(DBGameUser user) throws DBException {
		logger.log(Level.INFO, this, "db: update Score called "+user);
		EntityManagerFactory emf = null;

		try {
			emf = DBEntityManagerFactory.getInstance();
		} catch (HibernateException ex) {
			throw new DBException(ex.getMessage());
		}

		EntityManager em = null;
		EntityTransaction tx = null;

		try {

			em = emf.createEntityManager();
			tx = em.getTransaction();

			tx.begin();
			int deleted = em.createQuery(
					"UPDATE DBGameUser set SCORE=" + user.getScore()
							+ " where NAME = '" + user.getName() + "'")
					.executeUpdate();
			tx.commit();

			if (deleted == 0) {
				System.out.println("user not updated");
			}

			em.close();

		} catch (HibernateException ex) {

			throw new DBException(ex.getMessage());

		} finally {

			try {
				if (em != null && em.isOpen())
					em.close();
			} catch (Exception exCl) {
				logger.log(Level.ERROR, this, exCl.toString());
			}
		}
	}

	/**
	 * This method returns the top 10 user for the highscore list
	 * 
	 * @return List of top 10 user
	 * @throws DBException
	 */

	public ArrayList<DBGameUser> getTopTenGameUser() throws DBException {
		logger.log(Level.INFO, this, "db: getTopTenGameUser() ");
		
		ArrayList<DBGameUser> topTenUser = new ArrayList<DBGameUser>();

		EntityManagerFactory emf = null;
		try {
			emf = DBEntityManagerFactory.getInstance();
		} catch (HibernateException ex) {
			throw new DBException(ex.getMessage());
		}

		EntityManager em = null;

		try {

			em = emf.createEntityManager();
			
			javax.persistence.Query topTenQuery = em.createQuery(
					"FROM DBGameUser order by score desc");
			topTenQuery.setMaxResults(10);

			List res = topTenQuery.getResultList();
			logger.log(Level.INFO, this,"resultset retreived");
			for (int i = 0; i < res.size(); i++) {
				logger.log(Level.INFO, this, res.get(i).toString());
				DBGameUser usr = (DBGameUser) res
						.get(i);
				topTenUser.add(usr);
			}
			
		} catch (HibernateException ex) {

			throw new DBException(ex.getMessage());

		} finally {

			try {
				if (em != null && em.isOpen())
					em.close();
			} catch (Exception exCl) {
				logger.log(Level.ERROR, this, exCl.toString());
			}
		}
		

		return topTenUser;
	}
}
