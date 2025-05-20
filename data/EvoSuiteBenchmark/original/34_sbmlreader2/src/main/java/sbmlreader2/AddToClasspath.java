package sbmlreader2;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * Adds additional resources on runtime to the classpath.
 * @author Matthias Koenig
 *
 */
public class AddToClasspath {

	@SuppressWarnings("unchecked")
	private static final Class[] parameters = new Class[] { URL.class };
	
	/*
	 * Adds given path as URL to the CLASSPATH on runtime.
	 */
	@SuppressWarnings("unchecked")
	public static void addToClassPath(URL ClassPath_URL) throws Exception{
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;
		Method method = sysclass.getDeclaredMethod("addURL", parameters);
		method.setAccessible(true);
		method.invoke(sysloader, new Object[] {ClassPath_URL});
		
		String Classpath = System.getProperty("java.class.path");
		Classpath += System.getProperty("path.separator") + ClassPath_URL.toString();
		System.setProperty("java.class.path", Classpath);
	}
	
	public static void addFile(String s) throws Exception {
		File f = new File(s);
		addFile(f);
	}// end method

	@SuppressWarnings("deprecation")
	public static void addFile(File f) throws Exception {
		addToClassPath(f.toURL());
	}// end method
	
}
