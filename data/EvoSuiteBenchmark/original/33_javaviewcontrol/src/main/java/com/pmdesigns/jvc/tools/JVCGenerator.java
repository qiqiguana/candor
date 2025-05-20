package com.pmdesigns.jvc.tools;

import java.io.*;
import java.util.*;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Generate the a working web application (the view and controller part) based
 * on a set view templates.
 */
public class JVCGenerator {
	// break into this comment into two lines so that this file doesnt get deleted by 'realclean'
	public static final String MACHINE_GENERATED_1 = "/* This is a machine generated file.";
	public static final String MACHINE_GENERATED_2 = " DO NOT EDIT. */";
	public static final String GENERATOR_METHOD_NAME = "genPage";
	public static final String TEMPLATE_SUFFIX = ".jhtml";

	private File docRoot;
	private File srcRoot;
	private File srcDir;
	private String pkgPrefix;
	private boolean force;
	private boolean debug;
	private Map<String,Class> generators;
	
	private static final String[] genNames = {"Application","BaseController","BaseGenerator","Helpers",
											  "LocalBaseController","PageController","PageGenerator"};
	
	
	public static void usage() {
		System.err.println("args   : <template root dir> <src root dir> [<package prefix>] [-force] [-debug]");
		System.err.println("example: ./templates ./src com.pmdesigns -debug");
	}

	public static void main(String[] args) {
		if (args.length < 2 || args.length > 5) {
			System.err.println("Wrong number of args");
			usage();
			return;
		}

		JVCGenerator generator = new JVCGenerator();
		generator.docRoot = new File(args[0]);
		generator.srcRoot = new File(args[1]);
		generator.pkgPrefix = "";
		generator.force = false;
		generator.debug = false;

		if (args.length > 2) {
			for (int i = args.length; i-- > 2; ) {
				if ("-debug".equals(args[i])) {
					generator.debug = true;
				} else if ("-force".equals(args[i])) {
					generator.force = true;
				} else {
					generator.pkgPrefix = args[i];
				}
			}
		}
		
		try {
			generator.generate();

		} catch (InvocationTargetException e) {
			// unwrap invocation target exception if possible
			Throwable t = e.getTargetException();
			if (t == null) t = e;
			System.err.println("Invocation error: "+t.getMessage());
			if (generator.debug) {
				t.printStackTrace();
			}

		} catch (Exception e) {
			System.err.println("Error: "+e.getMessage());
			if (generator.debug) {
				e.printStackTrace();
			}
		}
	}

	public void generate() throws Exception {
		
		if (!docRoot.isDirectory()) {
			throw new FileNotFoundException("Template root directory '"+docRoot+"' doesn't exist.");
		}
		
		// get bootstrap generator classes
		generators = new HashMap<String,Class>();
		for (String name : genNames) {
			String className = "com.pmdesigns.jvc.tools.bootstrap."+name+"Generator";
			generators.put(name, Class.forName(className));
		}
		
		String relPath = File.separator; // always ends with '/'
		srcDir = new File(srcRoot, pkgPrefix.replace('.', File.separatorChar));

		// create view generators and controllers corresponding to the view templates
		walkDir(docRoot, relPath);

		// create an Application class, if necessary.
		File f = new File(srcDir, "Application.java");
		if (!f.exists()) {
			makeClass("Application", relPath, "Application", "", null, null, f);
		}
			
		// create BaseGenerator class, if necessary
		f = new File(srcDir, "generators/BaseGenerator.java");
		if (force || !f.exists()) {
			makeClass("BaseGenerator", relPath+"generators/", "BaseGenerator", "Generators", null, null, f);
		}
			
		// create BaseController class, if necessary
		f = new File(srcDir, "controllers/BaseController.java");
		if (!f.exists()) {
			makeClass("BaseController", relPath+"controllers/", "BaseController", "Controllers", null, null, f);
		}

		// create a Helpers class, if necessary.
		// (putting in utils directory so that things work without a package prefix, hmm)
		f = new File(srcDir, "utils/Helpers.java");
		if (!f.exists()) {
			makeClass("Helpers", relPath, "Helpers", "Utils", null, null, f);
		}
	}
	
	private void walkDir(File templateDir, String relPath) throws Exception {

		makeGeneratorsAndControllers(templateDir, relPath);

		for (File f : templateDir.listFiles()) {
			if (f.isDirectory()) {
				String name = f.getName();
				walkDir(new File(templateDir, name), relPath+name+File.separatorChar);
			}
		}
	}
	
	private void makeGeneratorsAndControllers(File templateDir, String relPath) throws Exception {
		TreeSet<String> imports = new TreeSet<String>();
		boolean atRoot = (relPath.length() == 1);
		String DirName = (atRoot ? "" : capitalize(templateDir.getName()));
		boolean hasTemplates = false;
		File outFile;
		
		for (File f : templateDir.listFiles()) {
			if (!isTemplate(f)) continue;
			hasTemplates = true;
			
			// parse template and convert to page generator
			String name = f.getName();
			name = name.substring(0, name.length()-TEMPLATE_SUFFIX.length());
			String Name = capitalize(name);
			outFile = new File(new File(srcDir, "generators"+relPath), Name+"Generator.java");

			// check if the generator is out of date
			if (!force && outFile.exists() && outFile.lastModified() >= f.lastModified()) {
				continue;
			}

			// parse the view template
			imports.clear();
			JVCParser parser = new JVCParser(f.getAbsolutePath(), imports, debug);
			String body = parser.parse();

			// and finally make the generator
			makeClass("PageGenerator", relPath, Name, DirName, imports, body, outFile);

			// create an empty controller for this page (unless one already exists)
			outFile = new File(new File(srcDir, "controllers"+relPath), Name+"Controller.java");
			if (outFile.exists()) {
				System.out.println("Controller exists, NOT overwriting:\n    "+outFile);
			} else {
				makeClass("PageController", relPath, Name, DirName, null, null, outFile);
			}
		}

		// don't create 'local' base controller unless there are templates in this directory
		// and we're not at the root (the 'global' base controller goes there)
		if (hasTemplates && !atRoot) {
			outFile = new File(new File(srcDir, "controllers"+relPath), "Base"+DirName+"Controller.java");
			if (outFile.exists()) {
				System.out.println("Controller exists, NOT overwriting:\n    "+outFile);
			} else {
				makeClass("LocalBaseController", relPath, DirName, DirName, null, null, outFile);
			}
		}
	}

	private void makeClass(String generatorName, String relPath, String name, String dirName,
						   Set<String> imports, String body, File outFile) throws Exception {
		// build the parameter map, templates expect the following keys:
		// map.get("machine.generated")
		// map.get("package.prefix")
		// map.get("package.prefix.dot")
		// map.get("dot.relative.package")
		// map.get("page.name")
		// map.get("dir.name")
		// map.get("imports")
		// map.get("page.body")
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("machine.generated", MACHINE_GENERATED_1+MACHINE_GENERATED_2);
		
		if (pkgPrefix.length() > 0) {
			map.put("package.prefix", pkgPrefix);
			map.put("package.prefix.dot", pkgPrefix+".");
		} else {
			map.put("package.prefix", "");
			map.put("package.prefix.dot", "");
		}
		
		if (relPath.length() > 1) {
			String relPkg = relPath.substring(0,relPath.length()-1).replace(File.separatorChar, '.');
			map.put("dot.relative.package", relPkg);
		} else {
			map.put("dot.relative.package", "");
		}

		map.put("page.name", name);
		map.put("dir.name", dirName);
		map.put("page.body", body);

		if (imports != null) {
			StringBuilder sb = new StringBuilder();
			for (String imp : imports) {
				sb.append("import ").append(imp).append(";\n");
			}
			map.put("imports", sb.toString());
		} else {
			map.put("imports", "");
		}

		// generate the file, finally!
		Class generator = generators.get(generatorName);
		Class[] args = {Class.forName("java.util.Map")};
		Method meth = generator.getMethod(GENERATOR_METHOD_NAME, args);
		String s = (String) meth.invoke(null, map);

		// write to output file
		File parent = outFile.getParentFile();
		if (!parent.exists()) parent.mkdirs();
		PrintWriter pw = new PrintWriter(outFile);
		pw.println(s);
		pw.close();
			
		System.out.println("wrote: "+outFile);
	}

	private static boolean isTemplate(File f) {
		if (f.isDirectory()) return false;

		String name = f.getName();
		return name.endsWith(TEMPLATE_SUFFIX) && name.charAt(0) != '_';
	}

	
	public static String capitalize(String s) {
		if (s == null || s.length() == 0) return s;
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	private static String appendPkg(String prefix, String pkg) {
		return (prefix == null || prefix.length() == 0) ? pkg : prefix+"."+pkg;
	}
}
