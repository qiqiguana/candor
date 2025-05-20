package net.sf.xbus.admin.jmx;

/**
 * Provides several services to administrate the xBus.
 */
public class Administrator implements AdministratorMBean
{
	net.sf.xbus.admin.Administrator admin = new net.sf.xbus.admin.Administrator();

	/**
	 * @see net.sf.xbus.admin.Administrator#readEtc(String)
	 */
	public String readEtc(String source)
	{
		return admin.readEtc(source);
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#readLog(String, int)
	 */
	public String readLog(String source, int expectedLength)
	{
		return admin.readLog(source, expectedLength);
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#explainErrorcode(String)
	 */
	public String explainErrorcode(String key)
	{
		return admin.explainErrorcode(key);
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#restartReceiverService()
	 */
	public String restartReceiverService()
	{
		return admin.restartReceiverService();
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#stopReceiverService()
	 */
	public String stopReceiverService()
	{
		return admin.stopReceiverService();
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#getReceiverServiceStatus()
	 */
	public String getReceiverServiceStatus()
	{
		return admin.getReceiverServiceStatus();
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#getJVMStatus()
	 */
	public String getJVMStatus()
	{
		return admin.getJVMStatus();
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#demandStopBackgroundReceiver(String)
	 */
	public String demandStopBackgroundReceiver(String system)
	{
		return admin.demandStopBackgroundReceiver(system);
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#startBackgroundReceiver(String)
	 */
	public String startBackgroundReceiver(String system)
	{
		return admin.startBackgroundReceiver(system);
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#getDeletedMessageFilenames()
	 */
	public String getDeletedMessageFilenames()
	{
		return admin.getDeletedMessageFilenames();
	}

	/**
	 * @see net.sf.xbus.admin.Administrator#resendDeletedMessage(String)
	 */
	public String resendDeletedMessage(String filename)
	{
		return admin.resendDeletedMessage(filename);
	}

}
