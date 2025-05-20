/**
 *
 */
package net.sourceforge.ext4j.log.filter;

import java.net.URL;
import java.net.URLConnection;

import javax.servlet.FilterConfig;

import net.sourceforge.ext4j.log.Server;

/**
 * @author luc
 *
 */
public class AmazonInitLogger implements IInitLogger {

	public void init(FilterConfig pConfig) throws Exception {
		setPublicHostname();
		setPublicIP();
	}

	private void setPublicIP() {
		try {
			URL oURL = new URL("http://169.254.169.254/latest/meta-data/public-ipv4");
			URLConnection oConn = oURL.openConnection();
			oConn.setConnectTimeout(1000);
			oConn.setReadTimeout(1000);
			Server.getInstance().setPublicIP((String) oConn.getContent());
		} catch (Exception e) {
			// ignore
			//e.printStackTrace();
			System.err.println("Could not get public IP (Amazon EC2). Application will run fine but logs might not show actual public IP. Error Message: " + e.getMessage());
		}
	}

	private void setPublicHostname() {
		try {
			URL oURL = new URL("http://169.254.169.254/latest/meta-data/public-hostname");
			URLConnection oConn = oURL.openConnection();
			oConn.setConnectTimeout(1000);
			oConn.setReadTimeout(1000);
			Server.getInstance().setCurrentHost((String) oConn.getContent());
		} catch (Exception e) {
			// ignore
			//e.printStackTrace();
			System.err.println("Could not get public hostname (Amazon EC2). Application will run fine but logs might not show actual public hostname. Error Message: " + e.getMessage());
		}
	}
}
