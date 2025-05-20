/*
 * Originally written by Doug Lea and released into the public domain. This may
 * be used for any purposes whatsoever without acknowledgment. Thanks for the
 * assistance and support of Sun Microsystems Labs, and everyone contributing,
 * testing, and using this code.
 */
package net.sf.xbus.base.core.timeoutcall;

import net.sf.xbus.base.core.XException;

/**
 * Interface for runnable actions that bear results and/or throw Exceptions.
 * This interface is designed to provide a common protocol for result-bearing
 * actions that can be run independently in threads, in which case they are
 * ordinarily used as the bases of Runnables that set FutureResults
 * <p>
 * <p>[<a
 * href="http://gee.cs.oswego.edu/dl/classes/EDU/oswego/cs/dl/util/concurrent/intro.html">
 * Introduction to this package. </a>]
 * 
 * @see FutureResult
 */

public interface Callable
{
	/**
	 * Performs some action that returns a result or throws an exception
	 */
	public Object call() throws XException;

	/**
	 * Stops the running call
	 */
	public void stop() throws XException;

}
