package net.sf.xbus.technical;

/**
 * Common interface for all <code>Sender</code> classes.
 */
public interface Sender
{
	/**
	 * A <code>Sender</code> can send data either as a <code>String</code>
	 * or as an <code>Object</code> to the neighboring system.<br />
	 * When working with <code>Strings</code> the Sender must fulfill two
	 * preconditions:
	 * <ul>
	 * <li><code>getType</code> returns <code>Constants.TYPE_TEXT</code></li>
	 * <li>The class implements the interface <code>TextSender</code></li>
	 * </ul>
	 * When working with <code>Objects</code> the Sender must also fulfill two
	 * preconditions:
	 * <ul>
	 * <li><code>getType</code> returns <code>Constants.TYPE_OBJECT</code></li>
	 * <li>The class implements the interface <code>ObjectSender</code></li>
	 * </ul>
	 */
	String getType();
}
