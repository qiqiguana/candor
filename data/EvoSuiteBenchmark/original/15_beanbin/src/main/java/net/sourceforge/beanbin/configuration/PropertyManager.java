package net.sourceforge.beanbin.configuration;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertyManager {
	private ResourceBundle bundle;
	
	public PropertyManager() {
		this.bundle = ResourceBundle.getBundle("beanbin");
	}
	
	public Property getProperty(String name) {
		String value = null;
		try {
			value = bundle.getString(name);	
		} catch(MissingResourceException e) {
			value = bundle.getString("beanbin." + name);
		}
		
		return new Property(name, value);
	}
	
	public List<String> getAllPropertyNames() {
		List<String> list = new ArrayList<String>();
		Enumeration<String> names = bundle.getKeys();
		while(names.hasMoreElements()) {
			list.add(names.nextElement());
		}
		return list;
	}
}