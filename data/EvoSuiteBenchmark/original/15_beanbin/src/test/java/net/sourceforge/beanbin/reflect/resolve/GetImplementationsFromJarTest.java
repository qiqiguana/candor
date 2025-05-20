package net.sourceforge.beanbin.reflect.resolve;

import java.io.File;
import java.util.List;

import junit.extensions.ActiveTestSuite;
import junit.extensions.ExceptionTestCase;
import junit.extensions.RepeatedTest;
import junit.extensions.TestDecorator;
import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sourceforge.beanbin.BeanBinException;

public class GetImplementationsFromJarTest extends TestCase {
	public void testBasic() throws Exception {		
		GetImplementationsFromJar getter = getGetter();
		List<Class> impls = getter.getImplementations();
		assertTrue(impls.contains(TestCase.class));
		assertTrue(impls.contains(TestSuite.class));
		assertTrue(impls.contains(TestDecorator.class));
		assertTrue(impls.contains(TestSetup.class));
		assertTrue(impls.contains(RepeatedTest.class));
		assertTrue(impls.contains(ActiveTestSuite.class));
		assertTrue(impls.contains(ExceptionTestCase.class));
		assertEquals(8, impls.size()); // one is TestSuite$1 not sure how to get at that class...
	}

	
	private GetImplementationsFromJar getGetter() throws Exception, BeanBinException {
		return new GetImplementationsFromJar(Test.class, new File("lib/junit.jar"));
	}
}
