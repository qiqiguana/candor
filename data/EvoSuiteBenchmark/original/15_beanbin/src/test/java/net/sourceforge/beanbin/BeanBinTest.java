package net.sourceforge.beanbin;

import net.sourceforge.beanbin.data.ejb3.RemoteTest;

public class BeanBinTest extends RemoteTest {
	public BeanBinTest(String serverName, int jndiPort) throws Exception {
		super(serverName, jndiPort);
		BeanBinDAOFactory.setInitialContext(getInitialContext());
	}
}
