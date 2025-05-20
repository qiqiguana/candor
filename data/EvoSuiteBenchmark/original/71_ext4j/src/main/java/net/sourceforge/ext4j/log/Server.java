/**
 *
 */
package net.sourceforge.ext4j.log;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class Server {

	private static Server mInstance = new Server();

	private String mPublicIP = "NA";
	private String mCurrentHost = "NA";
	private String mInfo = "NA";

	private Server() {
	}

	public static Server getInstance() {
		return mInstance;
	}

	public String getPublicIP() {
		return mPublicIP;
	}

	public void setPublicIP(String pPublicIP) {
		mPublicIP = pPublicIP;
	}

	public String getCurrentHost() {
		return mCurrentHost;
	}

	public void setCurrentHost(String pCurrentHost) {
		mCurrentHost = pCurrentHost;
	}

	public String getInfo() {
		return mInfo;
	}

	protected void setInfo(String pInfo) {
		mInfo = pInfo;
	}



}
