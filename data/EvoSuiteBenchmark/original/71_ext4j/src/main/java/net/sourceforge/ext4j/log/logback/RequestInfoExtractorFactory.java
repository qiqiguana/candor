/**
 * 
 */
package net.sourceforge.ext4j.log.logback;



/**
 * @author luc
 *
 */
public class RequestInfoExtractorFactory {
	
	private static IRequestInfoExtractor mInstance;
	
	static {
		initInstance();
	}
	
	public static IRequestInfoExtractor getExtractorInstance() {
		return mInstance;
	}

	private static void initInstance() {
		if (!useSpring()) {
			mInstance = new StubRequestInfoExtractor();
		}
	}

	private static boolean useSpring() {
		Class c = load("org.springframework.web.context.request.RequestContextHolder");
		if (c == null) return false;
		c = load("net.sourceforge.ext4j.log.logback.SpringRequestInfoExtractor");
		setInstance(c);
		return true;
	}

	private static Class load(String pClassName) {
		try {
			return RequestInfoExtractorFactory.class.getClassLoader().loadClass(pClassName);
			//return Class.forName(pClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void setInstance(Class pClass) {
		try {
			mInstance = (IRequestInfoExtractor) pClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
