package net.sf.xbus.base.core;

/**
 * <code>TAResource</code> is the interface for all classes, that should be
 * managed by a {@link  TAManager}.
 */
public interface TAResource
{
	/**
	 * Opens a resource.
	 * 
	 * @throws XException if something goes wrong
	 */
	public void open() throws XException;

	/**
	 * Performs a commit on the resource.
	 * 
	 * @throws XException if something goes wrong
	 */
	public void commit() throws XException;

	/**
	 * Performs a rollback on the resource.
	 * 
	 * @throws XException if something goes wrong
	 */
	public void rollback() throws XException;

	/**
	 * Closes a resource.
	 * 
	 * @throws XException if something goes wrong
	 */
	public void close() throws XException;
}
