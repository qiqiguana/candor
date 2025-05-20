package net.sf.sugar.scl;

/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 07-Sep-2008
 * Time: 21:50:23
 */
public class LocalReferenceException extends Exception {

    public LocalReferenceException(String msg)  {
        super(msg);
    }

    public LocalReferenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
