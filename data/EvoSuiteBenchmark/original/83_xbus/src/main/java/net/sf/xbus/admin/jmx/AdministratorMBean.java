package net.sf.xbus.admin.jmx;

/**
 * Provides several services to administrate the xBus.
 */
public interface AdministratorMBean
{
	/**
	 * @see net.sf.xbus.admin.Administrator#readEtc(String)
	 */
	String readEtc(String source);

	/**
	 * @see net.sf.xbus.admin.Administrator#readLog(String, int)
	 */
	String readLog(String source, int expectedLength);

	/**
	 * @see net.sf.xbus.admin.Administrator#explainErrorcode(String)
	 */
	String explainErrorcode(String key);

	/**
	 * @see net.sf.xbus.admin.Administrator#restartReceiverService()
	 */
	String restartReceiverService();

	/**
	 * @see net.sf.xbus.admin.Administrator#stopReceiverService()
	 */
	String stopReceiverService();

	/**
	 * @see net.sf.xbus.admin.Administrator#getReceiverServiceStatus()
	 */
	String getReceiverServiceStatus();

	/**
	 * @see net.sf.xbus.admin.Administrator#getJVMStatus()
	 */
	String getJVMStatus();

	/**
	 * @see net.sf.xbus.admin.Administrator#demandStopBackgroundReceiver(String)
	 */
	String demandStopBackgroundReceiver(String system);

	/**
	 * @see net.sf.xbus.admin.Administrator#startBackgroundReceiver(String)
	 */
	String startBackgroundReceiver(String system);

	/**
	 * @see net.sf.xbus.admin.Administrator#getDeletedMessageFilenames()
	 */
	String getDeletedMessageFilenames();

	/**
	 * @see net.sf.xbus.admin.Administrator#resendDeletedMessage(String)
	 */
	public String resendDeletedMessage(String filename);
}
