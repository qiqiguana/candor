/**
 * [ObjectExplorer4J - Tool zur grafischen Darstellung von Objekten und ihren
 * Referenzen]
 * 
 * Copyright (C) [2009] [PARAGON Systemhaus GmbH]
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 **/
package de.paragon.explorer.util;

import org.apache.log4j.Logger;

/**
 * Factory für Log4J.
 * 
 * @author Stefan Jockenhövel
 * 
 * @see http://www.javaspecialists.eu/archive/Issue137.html
 * @see http://shemnon.com/speling/2006/12/dry-logs-there-are-better-ways.html
 *      --> comment
 */
public final class LoggerFactory {
	/**
	 * 
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

	/**
	 * privater Konstruktur --> kein Erzeugen einer Instanz sinnvoll!
	 */
	private LoggerFactory() {
	}
}
