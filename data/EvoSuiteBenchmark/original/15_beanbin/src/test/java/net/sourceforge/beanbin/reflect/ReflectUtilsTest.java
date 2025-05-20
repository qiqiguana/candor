package net.sourceforge.beanbin.reflect;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Entity;

import junit.framework.TestCase;
import net.sourceforge.beanbin.test.SubTestEntity;
import net.sourceforge.beanbin.test.TestEntity;

public class ReflectUtilsTest extends TestCase {
	public void testHasAnno() throws Exception {
		assertTrue(ReflectUtils.hasAnnotation(TestEntity.class, Entity.class));
	}
	
	public void testGetGetters() throws Exception {
		List<Method> getters = ReflectUtils.getGetters(TestEntity.class);
		assertEquals(6, getters.size());		
	}
	
	public void testExtractGenericType() throws Exception {
		ReflectionSearch search = new ReflectionSearch(TestEntity.class).methodsThatHave("getSubs");
		Method method = search.getMethods().get(0);
		assertEquals(SubTestEntity.class, ReflectUtils.extractGenericType(method.getGenericReturnType()));
	}
}
