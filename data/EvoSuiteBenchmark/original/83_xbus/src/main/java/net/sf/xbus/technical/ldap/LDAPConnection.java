package net.sf.xbus.technical.ldap;

import java.util.Hashtable;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.TAResource;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;

/**
 * Manages connections to LDAP servers.
 * <p />
 * LDAP connections are not transacted. <code>TAResource</code> is just
 * implemented to open and close the connections.
 */
public class LDAPConnection implements TAResource
{
	static final private String CHAPTER_LDAPCONNECTION = "LDAPConnection";

	/*
	 * Variables used to implement the Singleton pattern
	 */
	private static Hashtable mLDAPConnections = new Hashtable();
	private static final Object classLock = LDAPConnection.class;

	/*
	 * Other variables
	 */
	private String mName = null;
	private boolean mOpen = false;
	private DirContext mContext = null;

	/**
	 * The constructor is private, instances of <code>LDAPConnection</code>
	 * can only be generated via the method <code>getInstance()</code>. Each
	 * instance is put in a <code>Hashtable</code> with the concatenation of
	 * the thread name and the connection name as the key.
	 */
	private LDAPConnection(String name)
	{
		mName = name;
		mLDAPConnections.put(getFullName(name), this);
		TAManager.getInstance().registerResource(this);
	}

	/**
	 * Delivers an open instance of <code>LDAPConnection</code>.
	 * <p />
	 * 
	 * If it is the first call with this name for the actual thread, a new
	 * <code>LDAPConnection</code> object will be created. Subsequent calls in
	 * this thread will deliver the object, that has been created by the first
	 * call.
	 * 
	 * @param name name of the LDAPConnection
	 * @return an open LDAPConnection
	 * @throws XException if something goes wrong
	 */
	public static LDAPConnection getInstance(String name) throws XException
	{
		synchronized (classLock)
		{
			LDAPConnection ldapConnection = (LDAPConnection) mLDAPConnections
					.get(getFullName(name));

			if (ldapConnection == null)
			{
				ldapConnection = new LDAPConnection(name);
			}

			ldapConnection.open();
			return ldapConnection;
		}
	}

	/**
	 * Opens a connection to a LDAP server.
	 * 
	 * @throws XException if something goes wrong
	 */
	public void open() throws XException
	{
		if (!mOpen)
		{
			/*
			 * Connect to the FTP Server
			 */
			Configuration config = Configuration.getInstance();
			String host = config
					.getValue(CHAPTER_LDAPCONNECTION, mName, "Host");
			int port = config.getValueAsIntOptional(CHAPTER_LDAPCONNECTION,
					mName, "Port");
			String baseDN = config.getValue(CHAPTER_LDAPCONNECTION, mName,
					"BaseDN");
			String rootDN = config.getValueOptional(CHAPTER_LDAPCONNECTION,
					mName, "RootDN");
			String password = config.getValueOptional(CHAPTER_LDAPCONNECTION,
					mName, "Password");

			/*
			 * Set up the initial context
			 */
			try
			{
				Hashtable env = new Hashtable();
				env.put(Context.INITIAL_CONTEXT_FACTORY,
						"com.sun.jndi.ldap.LdapCtxFactory");
				env.put(Context.PROVIDER_URL, buildURL(host, port, baseDN));
				if (rootDN != null)
				{
					env.put(Context.SECURITY_PRINCIPAL, rootDN);
				}
				if (password != null)
				{
					env.put(Context.SECURITY_CREDENTIALS, password);
				}
				mContext = new InitialDirContext(env);
			}
			catch (NamingException e)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_LDAP, "0", e);
			}
		}
		mOpen = true;
	}

	/**
	 * Closes the connection to the LDAP server
	 */
	public void close()
	{
		if (mOpen)
		{
			try
			{
				if (mContext != null)
				{
					mContext.close();
				}
			}
			catch (NamingException e)
			{
				Trace.warn("Cannot close context for LDAP connection " + mName);
			}
		}

		mOpen = false;
	}

	/**
	 * Returns the <code>Context</code> of the current connection to the LDAP
	 * server.
	 */
	public DirContext getContext()
	{
		return mContext;
	}

	/**
	 * Creates and returns <code>SearchControls</code> used to search entries
	 * in the LDAP server. Some values are read out of the configuration.
	 * 
	 * @return new <code>SearchControls</code>, initialized with values of
	 *         the configuration
	 * @throws XException if something goes wrong
	 */
	public SearchControls getSearchControls() throws XException
	{
		Configuration config = Configuration.getInstance();
		int scope = getScope();
		long countlim = config.getValueAsIntOptional(CHAPTER_LDAPCONNECTION,
				mName, "CountLimit");
		if (countlim < 0)
		{
			countlim = 0;
		}

		int timelim = config.getValueAsIntOptional(CHAPTER_LDAPCONNECTION,
				mName, "TimeLimit");
		if (timelim < 0)
		{
			timelim = 0;
		}

		SearchControls controls = new SearchControls();
		controls.setSearchScope(scope);
		controls.setCountLimit(countlim);
		controls.setCountLimit(timelim);

		return controls;
	}

	private int getScope() throws XException
	{
		int scope = SearchControls.SUBTREE_SCOPE;

		String configScope = Configuration.getInstance().getValueOptional(
				CHAPTER_LDAPCONNECTION, mName, "Scope");

		if (configScope != null)
		{
			if (configScope.toLowerCase().equals("onelevel"))
			{
				scope = SearchControls.ONELEVEL_SCOPE;
			}
			else if (configScope.toLowerCase().equals("subtree"))
			{
				scope = SearchControls.SUBTREE_SCOPE;
			}
			else
			{
				Vector params = new Vector(1);
				params.add(configScope);
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_LDAP, "1");
			}
		}

		return scope;
	}

	/**
	 * Builds an URL that points to a LDAP server.
	 * 
	 * @return URL that points to a LDAP server.
	 */
	private String buildURL(String host, int port, String baseDN)
	{
		StringBuffer buffer = new StringBuffer("ldap://").append(host);
		if (port > 0)
		{
			buffer.append(":").append(port);
		}
		if (baseDN != null)
		{
			buffer.append("/").append(baseDN);
		}
		return buffer.toString();
	}

	static private String getFullName(String name)
	{
		return new StringBuffer().append(name).append(".").append(
				Thread.currentThread().getName()).toString();
	}

	/**
	 * Empty method, LDAP connections are not transacted.
	 */
	public void commit()
	{
	// intentionally empty

	}

	/**
	 * Empty method, LDAP connections are not transacted.
	 */
	public void rollback()
	{
	// intentionally empty

	}
}
