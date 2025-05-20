package net.sourceforge.beanbin.data.ejb3;

import java.util.Hashtable;

import javax.naming.InitialContext;

import junit.framework.TestCase;

public class RemoteTest extends TestCase {
	
	private int jndiPort;
	private String serverName;


	public RemoteTest(String serverName, int jndiPort) {
		this.serverName = serverName;
		this.jndiPort = jndiPort;
	}
	
	protected InitialContext getInitialContext() throws Exception {
        Hashtable<String, String> env = new Hashtable<String, String>();

        env.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        env.put("java.naming.provider.url", "jnp://" + serverName + ":" + jndiPort);
        env.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
        env.put("java.naming.factory.url.pkgs", "org.jnp.interfaces");

        InitialContext ic = new InitialContext(env);
        return ic;
	}
}
