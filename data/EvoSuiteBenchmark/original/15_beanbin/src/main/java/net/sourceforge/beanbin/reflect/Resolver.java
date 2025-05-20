package net.sourceforge.beanbin.reflect;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.reflect.resolve.GetImplementationsFromDir;
import net.sourceforge.beanbin.reflect.resolve.GetImplementationsFromJar;

public class Resolver {
	private ClassLoader classloader;
	
	public Resolver() {
		this.classloader = Thread.currentThread().getContextClassLoader();
	}
	
	public List<Class> findImplementations(Class parent) throws BeanBinException {		
		List<Class> list = new ArrayList<Class>();
		
		// right now this is limited to implementations that are found within the scope
		// of your first package directory.. for instance com.company.stuff.BaseClass will search
		// for implementations within the com package and no where else
		
		String pkgname = parent.getPackage().getName();
		pkgname = pkgname.substring(0, pkgname.indexOf("."));
		System.out.println("finding implementations in package " + pkgname);
		Enumeration<URL> urls;
		try {
			urls = classloader.getResources(pkgname);
		} catch (IOException e1) {
			throw new BeanBinException("IOException: " + e1.getMessage(), e1);
		}
		
		while(urls.hasMoreElements()) {
			try {
				String path = getPath(urls.nextElement());
				System.out.println(path);
				File file = new File(path);
				if(file.isDirectory()) {
					list.addAll(new GetImplementationsFromDir(parent, file).getImplementations());
				} else {
					list.addAll(new GetImplementationsFromJar(parent, file).getImplementations());
				}	
			} catch(IOException e) {
				System.out.println("IOException while scanning: " + e.getMessage());
			}
		}
		return list;
	}	

	private String getPath(URL url) throws UnsupportedEncodingException {
		String path = url.getFile();
		path = URLDecoder.decode(path, "UTF-8");			
		path = removeFilePrefix(path);						
		path = removeJarInfo(path);
		return path;
	}

	private String removeJarInfo(String path) {
		if(path.indexOf('!') != -1) {
			path = path.substring(0, path.indexOf('!'));
		}
		return path;
	}

	private String removeFilePrefix(String path) {
		if(path.startsWith("file:")) {
			path = path.substring(5);
		}
		return path;
	}
}
