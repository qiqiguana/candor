package net.sourceforge.beanbin.configuration;

import java.util.List;

import junit.framework.TestCase;

public class PropertyManagerTest extends TestCase {
	public void testGetAllPropertyNames() throws Exception {
		PropertyManager manager = new PropertyManager();
		List<String> names = manager.getAllPropertyNames();
		assertEquals(7, names.size());
		assertTrue(names.contains("hibernate.dialect"));
		assertTrue(names.contains("driverClass"));
		assertTrue(names.contains("hibernate.show_sql"));
		assertTrue(names.contains("hibernate.connection.autocommit"));
		assertTrue(names.contains("hibernate.current_session_context_class"));
		assertTrue(names.contains("hibernate.cache.provider_class"));
		assertTrue(names.contains("hibernate.treecache.mbean.object_name"));
	}
	
	public void testGetProperty() throws Exception {
		PropertyManager manager = new PropertyManager();
		Property prop = manager.getProperty("hibernate.dialect");
		assertEquals("org.hibernate.dialect.PostgreSQLDialect", prop.getValue());
	}
}
