package net.sf.sugar.scl;

/**
 * Created by IntelliJ IDEA.
 * User: kbishop
 * Date: 20-Jul-2008
 * Time: 01:42:44
 */
class SCLParseException extends Exception {

    public SCLParseException(String msg) {
        super(msg);
    }

    @SuppressWarnings({"SameParameterValue"})
    public SCLParseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
