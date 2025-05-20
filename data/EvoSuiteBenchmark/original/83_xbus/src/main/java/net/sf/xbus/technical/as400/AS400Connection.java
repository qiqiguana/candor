package net.sf.xbus.technical.as400;

import java.beans.PropertyVetoException;
import java.util.Hashtable;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;

import com.ibm.as400.access.AS400;

/**
 * <code>AS400Connection</code> manages connections to AS400 machines.
 * <p>
 * 
 * It implements the <b>Singleton</b>-Design-Pattern: Named instances of
 * <code>AS400Connection</code> are created for every thread.
 * <p>
 * 
 * <b>Configuration:</b>
 * <p>
 * <table border>
 * <tr>
 * <th>Chapter</th>
 * <th>Section</th>
 * <th>Key</th>
 * <th>Content</th>
 * </tr>
 * <tr>
 * <td>AS400Connection</td>
 * <td><i>name</i></td>
 * <td>SystemName</td>
 * <td>System name or IP-address of the AS400</td>
 * </tr>
 * <tr>
 * <td>AS400Connection</td>
 * <td><i>name</i></td>
 * <td>User</td>
 * <td>User for accessing the AS400</td>
 * </tr>
 * <tr>
 * <td>AS400Connection</td>
 * <td><i>name</i></td>
 * <td>Password</td>
 * <td>Password for the user</td>
 * </tr>
 * </table>
 */
public class AS400Connection
{
	private static final String AS400CONNECTION = "AS400Connection";
	private static final String SYSTEMNAME = "SystemName";
	private static final String USER = "User";
	private static final String PASSWORD = "Password";
	private static final String TEST_SYSTEM = "XBUS_TEST";

	private static Hashtable mAS400Connections = new Hashtable();
	private static final Object classLock = AS400Connection.class;

	private AS400 mSystem = null;
	private boolean mIsTestSystem;

	/**
	 * The constructor is private, instances of <code>AS400Connection</code>
	 * can only be generated via the method <code>getInstance()</code>. Each
	 * instance is put in a <code>Hashtable</code> with the name of the thread
	 * and the name of the connection as the key.
	 * 
	 * @param name more than one AS400 can be connected, differenced by their
	 *            name
	 * @throws XException if something goes wrong
	 */
	private AS400Connection(String name) throws XException
	{
		Configuration config = Configuration.getInstance();
		String systemName = config.getValue(AS400CONNECTION, name, SYSTEMNAME);
		String user = config.getValue(AS400CONNECTION, name, USER);
		String password = config.getValue(AS400CONNECTION, name, PASSWORD);

		mIsTestSystem = TEST_SYSTEM.equals(systemName);

		if (!mIsTestSystem)
		{
			mSystem = new AS400(systemName, user, password);

			try
			{
				mSystem.setGuiAvailable(false);
			}
			catch (PropertyVetoException e)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "0", e);
			}
		}
		mAS400Connections.put(getKey(name), this);
	}

	/**
	 * Delivers an instance of <code>AS400Connection</code>.
	 * <p>
	 * 
	 * If it is the first call with the given name for the actual thread, a new
	 * <code>AS400Connection</code>-object gets created. Subsequent calls
	 * with the given name in this thread will deliver the object, that has been
	 * created by the first call.
	 * 
	 * @param name more than one AS400 can be connected, differenced by their
	 *            name
	 * @throws XException if any error occurs
	 */
	public static AS400Connection getInstance(String name) throws XException
	{
		synchronized (classLock)
		{
			AS400Connection as400Connection = (AS400Connection) mAS400Connections
					.get(getKey(name));

			if (as400Connection == null)
			{
				as400Connection = new AS400Connection(name);
			}
			return as400Connection;
		}
	}

	/**
	 * Returns the <code>AS400</code> object for the connection.
	 * 
	 * @return <code>AS400</code> object for the connection
	 */
	public AS400 getSystem()
	{
		return mSystem;
	}

	/**
	 * Helper method for writing testdrivers when no AS400 is available.
	 * 
	 * @return <code>true</code> if this is not a real AS400
	 */
	public boolean isTestSystem()
	{
		return mIsTestSystem;
	}

	static private String getKey(String name)
	{
		return new StringBuffer().append(Thread.currentThread().getName())
				.append(name).toString();
	}

	static public void clear()
	{
		mAS400Connections.clear();
	}
}
