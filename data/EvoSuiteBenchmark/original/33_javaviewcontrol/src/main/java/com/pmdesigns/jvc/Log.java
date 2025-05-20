package com.pmdesigns.jvc;

import org.apache.log4j.Logger;

/**
 * Wraps log4j using reflection to avoid runtime dependencies.
 * (Based on net.sf.persist.Log)
 */
public final class Log {

	public static final String LOG_NAME = "jvc.dispatcher";
	
	private static boolean log4jAvailable = false;
	static {
		try {
			Class.forName("org.apache.log4j.Logger");
			log4jAvailable = true;
		} catch (ClassNotFoundException e) {
			log4jAvailable = false;
		}
	}

	private Log() {
	}

	public static void debug(final Object message) {
		if (log4jAvailable) {
			Logger.getLogger(LOG_NAME).debug(message);
		} else {
			System.out.println(LOG_NAME+" debug - "+message);
		}
	}

	public static void debug(final Object message, Throwable t) {
		if (log4jAvailable) {
			Logger.getLogger(LOG_NAME).debug(message, t);
		} else {
			System.out.println(LOG_NAME+" debug - "+message);
			t.printStackTrace(System.out);
		}
	}

	public static void info(final Object message) {
		if (log4jAvailable) {
			Logger.getLogger(LOG_NAME).info(message);
		} else {
			System.out.println(LOG_NAME+" info - "+message);
		}
	}

	public static void info(final Object message, Throwable t) {
		if (log4jAvailable) {
			Logger.getLogger(LOG_NAME).info(message, t);
		} else {
			System.out.println(LOG_NAME+" info - "+message);
			t.printStackTrace(System.out);
		}
	}

	public static void warn(final Object message) {
		if (log4jAvailable) {
			Logger.getLogger(LOG_NAME).warn(message);
		} else {
			System.out.println(LOG_NAME+" warn - "+message);
		}
	}

	public static void warn(final Object message, Throwable t) {
		if (log4jAvailable) {
			Logger.getLogger(LOG_NAME).warn(message, t);
		} else {
			System.out.println(LOG_NAME+" warn - "+message);
			t.printStackTrace(System.out);
		}
	}

	public static void error(final Object message) {
		if (log4jAvailable) {
			Logger.getLogger(LOG_NAME).error(message);
		} else {
			System.out.println(LOG_NAME+" error - "+message);
		}
	}

	public static void error(final Object message, Throwable t) {
		if (log4jAvailable) {
			Logger.getLogger(LOG_NAME).error(message, t);
		} else {
			System.out.println(LOG_NAME+" error - "+message);
			t.printStackTrace(System.out);
		}
	}
}
