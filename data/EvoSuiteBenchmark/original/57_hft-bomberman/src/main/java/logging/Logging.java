package logging;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;

/*
 *@author  Tawatchai Siripanya************
 * Email dongathome@yahoo.com
 * Date 15.04.2008
 * <br>
 *************************************************
 *<p>
 *Usage:
 *</p>
 * ************************************************
 * <br>
 *1. import logging.*
 *<br>
 *or import logging.Logging
 *<br>
 * 2. object declaration
 * <br>
 * private static Logging logger = Logging.getInstance();  
 * <br> 
 * 3. define your logging level such as INFO,WARN,ERROR ,etc.
 * <br>
 * Example:
 * logger.log( Level.ERROR, this, "E002" );
 *  Where 
 * -E002 is your error code  which will be translated to English and German
 *  more about the error code will be provided later. *  
 * <br>
 * <br>  
 * more information about  logging levels please have a look at
 * http://supportweb.cs.bham.ac.uk/docs/tutorials/docsystem/build/tutorials/log4j/log4j.html
 *****************************************************/

public class Logging {
	private static final String LOG4J_CONFIG_FILE = "log4j.properties";
	private static final String MEIN_LOGGER_NAME = "Logging";
	private static final String MESSAGES_RESBUNDLE = "lib/messages";
	private static ResourceBundle messagesResBundle;
	private static Logging meinLogger;
	private static Logger log4jLogger;

	/*
	 *  private in order to be Singleton
	 */
	private Logging() {
		init();
	}

	private synchronized void init() {
		try {
			DOMConfigurator.configureAndWatch(LOG4J_CONFIG_FILE, 60 * 1000);
			log4jLogger = Logger.getLogger(MEIN_LOGGER_NAME);
			messagesResBundle = ResourceBundle.getBundle(MESSAGES_RESBUNDLE);
			log4jLogger.setResourceBundle(messagesResBundle);
		} catch (MissingResourceException ex) {
			System.err.println("Error: '" + MESSAGES_RESBUNDLE
					+ "'-.properties-Data is missing!");
		} catch (Exception ex) {
			System.err.println("error occurred while initializing the Logger");
		}
	}

	/*
	 * Singleton-Instance
	 */ 
	public static synchronized Logging getInstance() {
		if (meinLogger == null)
			meinLogger = new Logging();
		return meinLogger;
	}

	public synchronized void log(Level level, Object caller, String id /*
																		 * ,
																		 * String[]
																		 * parms
																		 */) {
		MDC.put("clss", caller.getClass().getName());
		MDC.put("id", id);

		String message = id;

		if (null != messagesResBundle) {
			try {
				message = messagesResBundle.getString(id);
			} catch (MissingResourceException ex) {/**/
			}

		}

		/*
		 * if( null != parms ) message = MessageFormat.format( message, parms );
		 */

		switch (level.toInt()) {
		case Priority.ALL_INT:
		case Priority.DEBUG_INT:
			log4jLogger.debug(message);
			break;
		case Priority.INFO_INT:
			log4jLogger.info(message);
			break;
		case Priority.WARN_INT:
			log4jLogger.warn(message);
			break;
		case Priority.ERROR_INT:
			log4jLogger.error(message);
			break;
		case Priority.FATAL_INT:
			log4jLogger.fatal(message);
			break;
		}
	}

	public boolean isEnabledFor(Level level) {
		return log4jLogger.isEnabledFor(level);
	}
}
