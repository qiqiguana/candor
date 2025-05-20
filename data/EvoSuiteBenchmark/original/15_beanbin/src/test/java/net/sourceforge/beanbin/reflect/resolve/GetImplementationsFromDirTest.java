package net.sourceforge.beanbin.reflect.resolve;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;
import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.test.Base;
import net.sourceforge.beanbin.test.ImplOne;
import net.sourceforge.beanbin.test.ImplTwo;

public class GetImplementationsFromDirTest extends TestCase {
	/**
	 * This test depends on Base, ImplOne and ImpleTwo from the test classes to be in the classpath
	 * before it will pass successfully.
	 * @throws Exception
	 */
	public void testBasic() throws Exception {		
		GetImplementationsFromDir getter = getGetter();
		List<Class> impls = getter.getImplementations();
		assertTrue(impls.contains(ImplOne.class));
		assertTrue(impls.contains(ImplTwo.class));
		assertEquals(2, impls.size());
	}

	public void testGetRelativePath() throws Exception {
		GetImplementationsFromDir getter = getGetter();
		File classpath = getClasspath();
		String path = classpath.getAbsolutePath() + "/net/sourceforge/beanbin/BeanBin.class";
		assertEquals("net/sourceforge/beanbin/BeanBin.class", getter.getRelativePath(new File(path)));
	}
	
	private GetImplementationsFromDir getGetter() throws Exception, BeanBinException {
		File classpath = getClasspath();
		
		GetImplementationsFromDir getter = new GetImplementationsFromDir(Base.class, classpath);
		return getter;
	}

	private File getClasspath() throws Exception {
		File classpath = new File("bin/net"); // eclipse's internal class path
		if(!classpath.exists()) {
			classpath = new File("build/net");
			if(!classpath.exists()) {
				throw new Exception("This test only supports classpaths originating in either a 'bin' or 'build' director.");
			}
		}
		return classpath;
	}
	
}
