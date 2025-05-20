package net.sourceforge.beanbin.reflect.resolve;

import java.util.List;

import net.sourceforge.beanbin.BeanBinException;

public abstract class GetImplementations {
	private ClassLoader classloader;
	private Class<?> parent;
	
	public GetImplementations(Class parent) {
		this.classloader = Thread.currentThread().getContextClassLoader();
		this.parent = parent;
	}
	
	public abstract List<Class> getImplementations() throws BeanBinException;
	
	protected ClassLoader getClassLoader() {
		return classloader;
	}
	
	protected Class<?> getParent() {
		return parent;
	}

	protected void addIfImplementation(List<Class> list, String path) {
		try {
			Class type = getClassLoader().loadClass(convertFilePathToQualifiedJavaName(path));
			if(type != null && type != parent) {
				if(getParent().isAssignableFrom(type)) {
					list.add(type);
				}
			}
		} catch (ClassNotFoundException e) {
			// dont add 
		} catch (NoClassDefFoundError e) {
			// dont add 
		}
	}

	protected String convertFilePathToQualifiedJavaName(String name) {
		name = name.substring(0, name.lastIndexOf("."));
		name = name.replaceAll("/", ".");
		return name;
	}

}
