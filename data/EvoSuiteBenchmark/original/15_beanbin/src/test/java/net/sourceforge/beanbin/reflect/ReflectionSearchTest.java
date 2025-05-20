package net.sourceforge.beanbin.reflect;

import java.lang.reflect.Method;
import java.util.List;

import junit.framework.TestCase;
import net.sourceforge.beanbin.query.SearchType;
import net.sourceforge.beanbin.test.TestEntity;

public class ReflectionSearchTest extends TestCase {
	public void testMethodSearch() throws Exception {
		ReflectionSearch search = new ReflectionSearch(TestEntity.class).methodsThatHave("get*");
		List<Method> getters = search.getMethods();
		assertTrue(getters != null);
		
		assertEquals(8, getters.size());
		
		search.methodsThatHave("@Column");
		
		assertEquals(3, search.getMethods().size());
	}
	
	public void testAnnoSearch() throws Exception {
		ReflectionSearch search = new ReflectionSearch(TestEntity.class);
		assertTrue(search.hasAnnotation("@Entity"));
		assertFalse(search.hasAnnotation("@Column"));
	}
	
	public void testIntersect() throws Exception {
		MethodReflectionCriteria criteria = new MethodReflectionCriteria(TestEntity.class, "get*", SearchType.EQUALS);
		List<Method> master = criteria.getResults();
		criteria = new MethodReflectionCriteria(TestEntity.class, "@Column", SearchType.EQUALS);
		List<Method> toadd = criteria.getResults();
		
		new ReflectionSearch(TestEntity.class).intersect(master, toadd);
		assertEquals(3, master.size());
	}
	
	public void testUnion() throws Exception {
		MethodReflectionCriteria criteria = new MethodReflectionCriteria(TestEntity.class, "get*", SearchType.EQUALS);
		List<Method> master = criteria.getResults();
		
		criteria = new MethodReflectionCriteria(TestEntity.class, "setAnInt", SearchType.EQUALS);
		List<Method> toadd = criteria.getResults();
		
		new ReflectionSearch(TestEntity.class).union(master, toadd);
		
		assertEquals(9, master.size());
	}
}
