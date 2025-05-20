package com.pmdesigns.jvc.tools;

import java.io.*;
import java.util.*;


/**
 * This class is for bootstrapping the real JVCGenerator.
 * It generated the source files for the generators used by the JVCGenerator.
 */
public class JVCBootstrapGenerator {
	public static final String GENERATOR_METHOD_NAME = "genPage";
	public static final String TEMPLATE_SUFFIX = ".java.tpl";

	
	public static void usage() {
		System.err.println("args   : <template dir> <src root dir> [-force] [-debug]");
	}

	public static void main(String[] args) {
		if (args.length < 2 || args.length > 4) {
			System.err.println("Wrong number of args");
			usage();
			return;
		}

		File tplDir = new File(args[0]);
		File srcRoot = new File(args[1]);
		boolean force = false;
		boolean debug = false;

		if (args.length > 2) {
			for (int i = args.length; i-- > 2; ) {
				if ("-debug".equals(args[i])) {
					debug = true;
				} else if ("-force".equals(args[i])) {
					force = true;
				}
			}
		}

		System.out.println("JVCBootstrapGenerator");
		System.out.println("    template dir:   "+tplDir);
		System.out.println("         src dir:   "+srcRoot);
		System.out.println("           force:   "+force);
		System.out.println("           debug:   "+debug);
		
		try {
			generate(tplDir, srcRoot, force, debug);
		} catch (Exception e) {
			System.err.println("Error: "+e.getMessage());
			if (debug) {
				e.printStackTrace();
			}
		}
	}

	public static void generate(File tplDir, File srcRoot, boolean force, boolean debug) throws
		FileNotFoundException, ParseException {
		
		if (!tplDir.isDirectory()) {
			throw new FileNotFoundException("Template root directory '"+tplDir+"' doesn't exist.");
		}
		
		String pkgPrefix = "com.pmdesigns.jvc.tools.bootstrap";
		File outDir = new File(srcRoot, pkgPrefix.replace('.', File.separatorChar));
		if (!outDir.exists()) outDir.mkdirs();

		TreeSet<String> imports = new TreeSet<String>();

		for (File f : tplDir.listFiles()) {
			String Name = getGeneratorName(f);
			if (Name == null) continue;
			
			// parse template and convert to bootstrap generator
			File outFile = new File(outDir, Name+"Generator.java");

			// check if the generator is out of date
			if (!force && outFile.exists() && outFile.lastModified() >= f.lastModified()) {
				continue;
			}

			// parse the view template
			imports.clear();
			imports.add("java.util.*");
			JVCParser parser = new JVCParser(f.getAbsolutePath(), imports, debug);
			String body = parser.parse();

			// write the page generator
			PrintWriter pw = new PrintWriter(outFile);
			pw.println(JVCGenerator.MACHINE_GENERATED_1+JVCGenerator.MACHINE_GENERATED_2);
			pw.println("package "+pkgPrefix+";");
			pw.println("");
			for (String pkg : imports) {
				pw.println("import "+pkg+";");
			}
			pw.println("");
			pw.println("public class "+Name+"Generator {");
			pw.println("");
			pw.println("public static String "+GENERATOR_METHOD_NAME+"(Map<String,String> map) {");
			pw.println("StringBuilder _sb = new StringBuilder();");
			pw.println(body);
			pw.println("return _sb.toString();");
			pw.println("}");
			pw.println("private static String _nf(String x) { return (x == null) ? \"\" : x; }");
			pw.println("}");
			pw.close();

			System.out.println("wrote: "+outFile);
		}
	}
	
	static String getGeneratorName(File f) {
		if (f.isDirectory()) return null;

		String name = f.getName();
		if (!name.endsWith(TEMPLATE_SUFFIX)) return null;

		name = name.substring(0, name.length()-TEMPLATE_SUFFIX.length()); // remove '.java.tpl'
		return capitalize(name);
	}

	public static String capitalize(String s) {
		if (s == null || s.length() == 0) return s;
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}
}
