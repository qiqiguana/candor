package net.sf.xbus.base.core.reflection;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.trace.Trace;

/**
 * <code>XBUSClassLoader</code> manages an <code>URLClassLoader</code>, that
 * loads all jar-files under <code>XBUS_HOME/lib</code>,
 * <code>XBUS_HOME/lib/runtime</code> and <code>XBUS_HOME/plugin/lib</code>. All
 * *Bootstrap classes use this <code>URLClassLoader</code> to start the
 * functionality of the xBus. For this reason, only the library
 * <code>xbus-bootstrap.jar</code> must be in the <code>CLASSPATH</code> when
 * starting the xBus.
 */
public class XBUSClassLoader extends URLClassLoader {
	static private XBUSClassLoader mClassLoader = null;

	private static final Object classLock = XBUSClassLoader.class;

	private XBUSClassLoader(URL[] urlArray, ClassLoader parent) {
		super(urlArray, parent);
	}

	/**
	 * Returns an instance of the <code>URLClassLoader</code>, that loads all
	 * jar files under <code>XBUS_HOME/lib</code>,
	 * <code>XBUS_HOME/lib/runtime</code> and <code>XBUS_HOME/plugin/lib</code>.
	 * The first call will initialize the <code>URLClassLoader</code>.
	 * 
	 * @param parent
	 *            the parent <code>ClassLoader</code>
	 * @return an <code>URLClassLoader</code> suitable for the xBus
	 */
	static public XBUSClassLoader getInstance(ClassLoader parent) {
		return createClassLoader(parent);
	}

	static private XBUSClassLoader createClassLoader(ClassLoader parent) {
		synchronized (classLock) {
			if (mClassLoader == null) {
				/*
				 * Check if XBUS_HOME is set. This is done here, because loading
				 * the configuration will be the first point, where XBUS_HOME is
				 * used.
				 */
				if (Constants.XBUS_HOME == null) {
					Trace.error("XBUS_HOME has not been set!");
					System.exit(1);
				}

				Vector urls = new Vector();
				addUrls(urls, Constants.XBUS_HOME + "/lib");
				addUrls(urls, Constants.XBUS_HOME + "/lib/runtime");
				addUrls(urls, Constants.XBUS_HOME + "/plugin/lib");
				addUrls(urls, Constants.XBUS_HOME + "/test/lib");
				URL[] urlArray = new URL[urls.size()];
				for (int i = 0; i < urls.size(); i++) {
					urlArray[i] = (URL) (urls.elementAt(i));
				}
				mClassLoader = new XBUSClassLoader(urlArray, parent);
			}
		}
		return mClassLoader;
	}

	private static void addUrls(Vector urls, String dirName) {
		File libPath = new File(dirName);
		File[] jars = libPath.listFiles();
		for (int i = 0; (jars != null) && (i < jars.length); i++) {
			if ((jars[i].isFile())
					&& ((jars[i].getName().endsWith("jar"))
							|| (jars[i].getName().endsWith("zip")))) {
				try {
					urls.add(jars[i].toURL());
				} catch (MalformedURLException e) {
					Trace.error(e);
					System.exit(1);
				}
			}
		}
	}

}
