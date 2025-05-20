package de.paragon.explorer.util;

import org.apache.log4j.Logger;

/**
 * Factory f�r Log4J.
 *
 * @author Stefan Jockenh�vel
 * @see http://www.javaspecialists.eu/archive/Issue137.html
 * @see http://shemnon.com/speling/2006/12/dry-logs-there-are-better-ways.html
 *      --> comment
 */
public final class LoggerFactory {

    /**
     * Erzeugt einen Logger in der aufrufenden Klasse.
     *
     * @return Logger
     */
    public static Logger make() {
        // Throwable t = new Throwable();
        // StackTraceElement directCaller = t.getStackTrace()[1];
        // return Logger.getLogger(directCaller.getClassName());
        final Thread t = Thread.currentThread();
        final StackTraceElement directCaller = t.getStackTrace()[2];
        final String className = directCaller.getClassName();
        return Logger.getLogger(className);
    }
}
