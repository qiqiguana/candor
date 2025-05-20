package net.sf.sugar.scl;

/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 02-Aug-2008
 * Time: 16:49:08
 *
 * @author kbishop
 * @version $Id$
 */
public class IncludeException extends Exception {

    public IncludeException(String msg)  {
        super(msg);
    }

    public IncludeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
