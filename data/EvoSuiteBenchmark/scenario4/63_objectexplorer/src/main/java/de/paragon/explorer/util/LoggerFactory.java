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
    public static Logger make();

    /**
     * privater Konstruktur --> kein Erzeugen einer Instanz sinnvoll!
     */
    private LoggerFactory() {
    }
}
