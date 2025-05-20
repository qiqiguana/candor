package net.sourceforge.beanbin.reflect.resolve;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import net.sourceforge.beanbin.BeanBinException;

public class GetImplementationsFromJar extends GetImplementations {

	private File jar;

	public GetImplementationsFromJar(Class parent, File jar) {
		super(parent);
		this.jar = jar;
	}

	public List<Class> getImplementations() throws BeanBinException {
		List<Class> list = new ArrayList<Class>();
		try {
			JarEntry entry;
			JarInputStream jarStream = new JarInputStream(new FileInputStream(jar));
			
			while ((entry = jarStream.getNextJarEntry()) != null) {
			    String name = entry.getName();
			    if (!entry.isDirectory() && name.endsWith(".class")) {
			    	addIfImplementation(list, name);
			    }
			}
		} catch(IOException e) {
			System.out.println("Could not search jar " + jar.getName() + " because IOException: " + e.getMessage());
		}
		return list;
	}
}
