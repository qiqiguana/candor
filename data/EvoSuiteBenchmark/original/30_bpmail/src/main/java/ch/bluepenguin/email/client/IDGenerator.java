/*
 * Created on 09.06.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.bluepenguin.email.client;

/**
 * @author Christian
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class IDGenerator {
	private static int counter;
	public static Integer next() {
		return new Integer(counter++);
	}
}
