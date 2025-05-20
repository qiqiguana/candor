package net.sourceforge.beanbin.search.index;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.configuration.PropertyManager;

public class Index {
	public static IndexManager getManager() throws BeanBinException {
		PropertyManager manager = new PropertyManager();
		String className = manager.getProperty("indexmanager").getValue();
		if(className == null) {
			throw new BeanBinException("Set beanbin.indexmanager property in beanbin.properties.");			
		} else {
			try {
				Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
				return (IndexManager)clazz.newInstance();	
			} catch(Exception e) {
				throw new BeanBinException("IndexManager error: " + e.getMessage(), e);
			}
		}
	}
}
