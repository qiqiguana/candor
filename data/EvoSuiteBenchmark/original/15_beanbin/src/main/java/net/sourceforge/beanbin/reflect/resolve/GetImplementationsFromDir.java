package net.sourceforge.beanbin.reflect.resolve;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.beanbin.BeanBinException;

public class GetImplementationsFromDir extends GetImplementations {
	private File dir;
	
	public GetImplementationsFromDir(Class parent, File dir) throws BeanBinException {
		super(parent);
		if(!dir.isDirectory()) {
			throw new BeanBinException("File passed must be a directory instead it was a file: " + dir.getAbsolutePath());
		}
		
		this.dir = dir;
	}
	
	public List<Class> getImplementations() {
		List<Class> list = new ArrayList<Class>();				
		addImplementations(list, this.dir);		
		return list;
	}
	
	private void addImplementations(List<Class> list, File dir) {
		for(File file : dir.listFiles()) {
			if(file.isDirectory()) {
				addImplementations(list, file);
			} else if (file.getName().endsWith(".class")) {
				addIfImplementation(list, getRelativePath(file));
			}
		}		
	}

	protected String getRelativePath(File file) {
		String path = file.getAbsolutePath();
		String dirName = this.dir.getName();
		
		int index = path.lastIndexOf(dirName + "/");		
		
		String name = path.substring(index);
		return name;
	}
}
