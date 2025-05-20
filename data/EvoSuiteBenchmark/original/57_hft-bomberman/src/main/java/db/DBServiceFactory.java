package db;

/**
 * This class returns an instance for bomberman database services.
 * The access to the methods for the database services is supplied by this class
 * 
 * @author Daniel Tunjic
 *
 */

public class DBServiceFactory {
	
    private static BombermanDBService instance = null;

    private DBServiceFactory() {
    }
    
    public synchronized static BombermanDBService getInstance() {
        if (instance == null) {

            instance = new BombermanDBService();
        }
        
        return instance;
    }
}