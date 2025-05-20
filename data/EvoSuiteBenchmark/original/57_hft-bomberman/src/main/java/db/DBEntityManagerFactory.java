package db;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This class creates an instance of the EntityManager. The EntityManager provides services for 
 * persistent data management. It's implemented in the "BombermanDBService" class.
 * 
 * @author Daniel Tunjic
 *
 */

public class DBEntityManagerFactory {

	private static EntityManagerFactory instance = null;

    private DBEntityManagerFactory() {
    }

    public synchronized static EntityManagerFactory getInstance() throws DBException{

        if (instance == null) {
          throw new DBException("Keine Instanz vorhanden");
        }
        return instance;

    }

   
    public synchronized static void createEntityManagerFactory() {
        if (instance == null) {

            instance =  Persistence.createEntityManagerFactory("bomberman");
        }
    }

}
