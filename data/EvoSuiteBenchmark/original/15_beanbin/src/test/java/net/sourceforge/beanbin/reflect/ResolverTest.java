package net.sourceforge.beanbin.reflect;

import java.util.List;

import junit.framework.TestCase;
import net.sourceforge.beanbin.test.Base;
import net.sourceforge.beanbin.test.ImplOne;
import net.sourceforge.beanbin.test.ImplTwo;

public class ResolverTest extends TestCase {
	public void testFindImplementations() throws Exception {
		Resolver resolve = new Resolver();
		List<Class> list = resolve.findImplementations(Base.class);
		assertTrue(list.contains(ImplOne.class));
		assertTrue(list.contains(ImplTwo.class));
		assertEquals(2, list.size());
	}
}
