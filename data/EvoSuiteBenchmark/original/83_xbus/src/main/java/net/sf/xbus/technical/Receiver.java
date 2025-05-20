package net.sf.xbus.technical;

/**
 * Common interface for all <code>Receiver</code> classes.
 */
public interface Receiver
{
	/**
	 * A <code>Receiver</code> can receive data either as a
	 * <code>String</code> or as an <code>Object</code> from a neighboring
	 * system.<br />
	 * <ul>
	 * <li>When working with <code>Strings</code>, <code>getType</code>
	 * returns <code>Constants.TYPE_TEXT</code></li>
	 * <li>When working with <code>Objects</code>, <code>getType</code>
	 * returns <code>Constants.TYPE_OBJECT</code></li>
	 * </ul>
	 */
	String getType();
}
