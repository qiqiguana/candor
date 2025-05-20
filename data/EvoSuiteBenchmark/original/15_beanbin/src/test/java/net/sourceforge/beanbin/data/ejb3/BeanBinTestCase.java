package net.sourceforge.beanbin.data.ejb3;

import net.sourceforge.beanbin.BeanBinDAOFactory;

public abstract class BeanBinTestCase extends RemoteTest {
	public BeanBinTestCase(String serverName, int jndiPort) throws Exception {
		super(serverName, jndiPort);
		BeanBinDAOFactory.setInitialContext(getInitialContext());
	}	
}
