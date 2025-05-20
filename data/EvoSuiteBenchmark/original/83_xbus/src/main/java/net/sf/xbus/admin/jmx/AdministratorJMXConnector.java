package net.sf.xbus.admin.jmx;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;

/**
 * Provides several services to administrate the xBus via a JMX connection.
 */
public class AdministratorJMXConnector {
	/**
	 * @see net.sf.xbus.admin.Administrator#readEtc(String)
	 */
	static public String readEtc(String filename) throws XException {
		return getMBean().readEtc(filename);
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#readLog(String, int)
	 */
	static public String readLog(String filename) throws XException {
		return getMBean().readLog(filename, 9999);
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#explainErrorcode(String)
	 */
	static public String explainErrorcode(String key) throws XException {
		return getMBean().explainErrorcode(key);
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#stopReceiverService()
	 */
	static public String restartReceiverService() throws XException {
		return getMBean().restartReceiverService();
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#stopReceiverService()
	 */
	static public String stopReceiverService() throws XException {
		return getMBean().stopReceiverService();
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#getReceiverServiceStatus()
	 */
	static public String getReceiverServiceStatus() throws XException {
		return getMBean().getReceiverServiceStatus();
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#getJVMStatus()
	 */
	static public String getJVMStatus() throws XException {
		return getMBean().getJVMStatus();
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#demandStopBackgroundReceiver(String)
	 */
	static public String demandStopBackgroundReceiver(String system)
			throws XException {
		return getMBean().demandStopBackgroundReceiver(system);
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#startBackgroundReceiver(String)
	 */
	static public String startBackgroundReceiver(String system)
			throws XException {
		return getMBean().startBackgroundReceiver(system);
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#getDeletedMessageFilenames()
	 */
	static public String getDeletedMessageFilenames() throws XException {
		return getMBean().getDeletedMessageFilenames();
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#resendDeletedMessage(String)
	 */
	static public String resendDeletedMessage(String system) throws XException {
		return getMBean().resendDeletedMessage(system);
	}

	private static AdministratorMBean getMBean() throws XException {
		try {
			JMXServiceURL url = new JMXServiceURL(
					"service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
			JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

			ObjectName adaptor = new ObjectName("xBus:mbean=Administrator");

			AdministratorMBean mbeanProxy = JMX.newMBeanProxy(mbsc, adaptor,
					AdministratorMBean.class, true);

			return mbeanProxy;

		} catch (Exception e) {
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_ADMIN, Constants.PACKAGE_ADMIN_JMX, "0", e);
		}
	}

}
