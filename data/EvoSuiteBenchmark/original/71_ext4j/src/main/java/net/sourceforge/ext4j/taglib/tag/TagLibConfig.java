/**
 * 
 */
package net.sourceforge.ext4j.taglib.tag;

import java.util.Properties;

/**
 * @author luc
 *
 */
public class TagLibConfig {

	private static Properties mConfig = new Properties();

	public static Properties getConfig() {
		return mConfig;
	}

	public void setConfig(Properties pProperties) {
		mConfig = pProperties;
	}
	
	public static String getProperty(String pKey, String pDefault) {
		return mConfig.getProperty(pKey, pDefault);
	}
	
	
}
