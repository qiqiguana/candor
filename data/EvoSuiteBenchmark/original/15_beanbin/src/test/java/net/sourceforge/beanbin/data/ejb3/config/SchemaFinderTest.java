package net.sourceforge.beanbin.data.ejb3.config;

import java.util.List;

import junit.framework.TestCase;
import net.sourceforge.beanbin.test.AnotherEntity;
import net.sourceforge.beanbin.test.Base;
import net.sourceforge.beanbin.test.ImplOne;
import net.sourceforge.beanbin.test.ImplTwo;
import net.sourceforge.beanbin.test.SubTestEntity;
import net.sourceforge.beanbin.test.TestEntity;

public class SchemaFinderTest extends TestCase {
	public void testBasic() throws Exception {
		SchemaFinder finder = new SchemaFinder(TestEntity.class);
		List<Class> schema = finder.getSchema();
		assertTrue(schema.contains(TestEntity.class));
		assertTrue(schema.contains(ImplOne.class));
		assertTrue(schema.contains(ImplTwo.class));
		assertTrue(schema.contains(SubTestEntity.class));
		assertTrue(schema.contains(AnotherEntity.class));
	}
	
	public void testAbstract() throws Exception {
		SchemaFinder finder = new SchemaFinder(Base.class);
		List<Class> schema = finder.getSchema();
		assertTrue(schema.contains(ImplOne.class));
		assertTrue(schema.contains(ImplTwo.class));
		
		finder = new SchemaFinder(TestEntity.class);
		assertTrue(finder.getSchema().contains(Base.class));
	}
}
