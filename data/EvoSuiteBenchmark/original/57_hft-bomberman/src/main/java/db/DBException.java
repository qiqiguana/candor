package db;

/**
 * This class adopts the database message handling 
 * 
 * @author Daniel Tunjic
 *
 */

public class DBException extends Exception{

    public DBException() {
        super();
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(Throwable cause) {
        super(cause);
    }

}