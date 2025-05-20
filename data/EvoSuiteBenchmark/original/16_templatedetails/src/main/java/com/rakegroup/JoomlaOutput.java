/*
 * File: JoomlaOutput.java
 * 
 * Class: com.rakegroup.JoomlaOutput
 * 
 * Copyright 2009 Thomas W. Rake
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.rakegroup;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.DirectoryScanner;
import org.jdom.*;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format;
import java.util.Vector;
import java.util.List;
import java.util.Iterator;


public class JoomlaOutput extends Task {
	private Element root;
	private String output = "templateDetails.xml";
	private boolean nooverwrite = true;
	private java.io.PrintStream xmlfile;
	private String docElement = "install";
	private String docPublic = "-//Joomla! 1.5//DTD template 1.0//EN";
	private String docSystem = "http://dev.joomla.org/xml/1.5/template-install.dtd";

	public void validate() {
		if (output == null) {
			xmlfile = System.out;
		} else {
			if (nooverwrite && (new java.io.File(output).exists())) {
				throw new BuildException("file exists");
			}

			try {
				xmlfile = new java.io.PrintStream(output);
			} catch (java.io.IOException io) {
				throw new BuildException(io.getMessage());
			}

		}
		if (root == null)
			throw new BuildException("install not defined");
		SimpleElement.fixup(getProject(), root);

	}

	public void setOutput(String name) {
		if (name.length() == 0 ) {
			output = null;
		} else {
			output = name;
		}

	}

	public void setOverwrite(boolean overwrite) {
		nooverwrite = !overwrite;
	}
	
	public void setDocelement(String v) {
		docElement=v;
	}
	public void setDocpublic(String v) {
		docPublic=v;
	}
	public void setDocsystem(String v) {
		docSystem=v;
	}

	public void addConfiguredInstall(Install install) {
		root=install;
	}

	public void execute() {
		validate();
		XMLOutputter xmlout = new XMLOutputter(Format.getPrettyFormat());
		Document doc = new Document(root,new DocType(docElement,docPublic,docSystem));
		try {
			xmlout.output(doc, xmlfile);
		} catch (java.io.IOException io) {
			log(io.getMessage());
		}
		log("Done");

	}

	static public class Install extends SimpleElement {

		public Install() {
			super("install");
		}

		public void setType(String value) {
			setAttribute("type", value);
		}

		public void setVersion(String value) {
			setAttribute("version", value);

		}

		public void setClient(String value) {
			setAttribute("client", value);
		}

		public void addConfiguredName(Name name) {
			addContent(name);
		}

		public void addConfiguredCreationDate(CreationDate date) {
			addContent(date);
		}

		public void addConfiguredAuthor(Author author) {
			addContent(author);
		}

		public void addConfiguredAuthorEmail(AuthorEmail email) {
			addContent(email);
		}

		public void addConfiguredAuthorUrl(AuthorUrl url) {
			addContent(url);
		}

		public void addConfiguredCopyright(Copyright right) {
			addContent(right);
		}

		public void addConfiguredLicense(License l) {
			addContent(l);
		}

		public void addConfiguredVersion(Version v) {
			addContent(v);
		}

		public void addConfiguredDescription(Description d) {
			addContent(d);
		}

		public void addConfiguredFiles(Files f) {
			addContent(f);
		}

		public void addConfiguredLanguages(Languages l) {
			addContent(l);
		}

		public void addConfiguredAdministraton(Administration a) {
			addContent(a);
		}

		public void addConfiguredPositions(Positions p) {
			addContent(p);
		}

		public void addConfiguredParams(Params p) {
			addContent(p);

		}

	}

	static public class SimpleElement extends Element {
		public SimpleElement(String name) {
			super(name);
		}

		public void addText(String text) {
			addContent(text);
		}
				
		static public void fixup(Project project,Content c) {
			if (c instanceof Element){
				Iterator ci = ((Element)c).getContent().iterator();
				while(ci.hasNext()) {
					Content v = (Content)ci.next();
					if (v instanceof Text && v.getParent() instanceof SimpleElement) {
						((Text) v).setText(project.replaceProperties(((Text)v).getText()));
					}
					
					if (v instanceof Files) {
						if (((Files)v).setMode) {
							for(Iterator<FileSet> itFSets = ((Files)v).getFileSets().iterator();
								itFSets.hasNext(); ) {
								FileSet fs = itFSets.next();
								DirectoryScanner ds = fs.getDirectoryScanner(project);
								String [] includesFiles = ds.getIncludedFiles();
								for (int i=0; i<includesFiles.length;i++) {
									((Files)v).addContent(new Element("filename").addContent(includesFiles[i]));
								}
							}
						}
					}
					
					if (v instanceof Element)
						fixup(project,(Element)v); 
				}
			}
		}
		
	}

	static public class VectorElement extends SimpleElement {
		public VectorElement(String name) {
			super(name);
		}

		public void addElement(Element e) {
			addContent(e);
		}
	}

	static public class Files extends VectorElement {
		protected Vector<FileSet >fSets;
		protected boolean fileMode = false;
		protected boolean setMode = false;
		public Files() {
			super("files");
			fSets = new Vector<FileSet>();
		}
		
		protected void update(Filename name,FileSet set) {
			if(name != null) {
				if(setMode) {
					throw new BuildException("filename used in fileSet mode");
				}
				fileMode = true;
			}
			if(set != null) {
				if(fileMode) {
					throw new BuildException("FileSet used in filename mode");
				}
				setMode = true;
			}
		}
		
		public void setFolder(String v) {
			setAttribute("folder",v);
		}
		
		public void addConfiguredFilename(Filename f) {
			addContent(f);
			update(f,null);
		}

		public void addConfiguredFolder(Folder f) {
			addContent(f);
		}
		
		public void addConfiguredFileSet(FileSet f) {
			fSets.add(f);
			update(null,f);
		}
		
		public Vector<FileSet> getFileSets() {
			return fSets;
		}

	}

	static public class Languages extends VectorElement {
		public Languages() {
			super("languages");
		}

		public void setFolder(String name) {
			setAttribute("folder", name);
		}

		public void addConfiguredLanguage(Language l) {
			addContent(l);
		}
	}

	static public class Administration extends VectorElement {
		public Administration() {
			super("administration");
		}

		public void addConfiguredFiles(Files files) {
			addContent(files);
		}

		public void addConfiguredLanguages(Languages l) {
			addContent(l);
		}

		public void addConfiguredAdministraton(Administration a) {
			addContent(a);
		}
	}

	static public class Params extends VectorElement {
		public Params() {
			super("params");
		}

		public void addConfiguredParam(Param p) {
			addContent(p);
		}
	}

	static public class Positions extends VectorElement {
		public Positions() {
			super("positions");
		}

		public void addConfiguredPosition(Position p) {
			addContent(p);
		}
	}

	static public class Name extends SimpleElement {
		public Name() {
			super("name");
		}
	}

	static public class CreationDate extends SimpleElement {
		public CreationDate() {
			super("creationDate");
		}
	}

	static public class Author extends SimpleElement {
		public Author() {
			super("author");
		}
	}

	static public class AuthorEmail extends SimpleElement {
		public AuthorEmail() {
			super("authorEmail");
		}
	}

	static public class AuthorUrl extends SimpleElement {
		public AuthorUrl() {
			super("authorUrl");
		}
	}

	static public class Copyright extends SimpleElement {
		public Copyright() {
			super("copyright");
		}
	}

	static public class License extends SimpleElement {
		public License() {
			super("license");
		}
	}

	static public class Version extends SimpleElement {
		public Version() {
			super("version");
		}
	}

	static public class Description extends SimpleElement {
		public Description() {
			super("description");
		}
	}

	static public class Filename extends SimpleElement {
		public Filename() {
			super("filename");
		}
	}

	static public class Folder extends SimpleElement {
		public Folder() {
			super("folder");
		}
	}

	static public class Language extends SimpleElement {
		public Language() {
			super("language");
		}
	}

	static public class Position extends SimpleElement {
		public Position() {
			super("position");
		}
	}

	static public class Param extends SimpleElement {
		public Param() {
			super("param");
		}

		public void addConfiguredOption(Option option) {
			addContent(option);
		}

		public void setNamE(String v) {
			/* This should be called setName but that overrided
			 * a jDom method but case doesnot matter in xml attributes
			 * of this file so we use this HACK.*/
			setAttribute("name", v);
		}

		public void setType(String v) {
			setAttribute("type", v);
		}

		public void setLabel(String v) {
			setAttribute("label", v);
		}

		public void setSize(String v) {
			setAttribute("size", v);
		}

		public void setId(String v) {
			setAttribute("id", v);
		}

		public void setDefault(String v) {
			setAttribute("default", v);
		}

		public void setDescription(String v) {
			setAttribute("description", v);
		}

		public void setSection(String v) {
			setAttribute("section", v);
		}

		public void setScope(String v) {
			setAttribute("scope", v);
		}

		public void setDirectory(String v) {
			setAttribute("directory", v);
		}

		public void setFilter(String v) {
			setAttribute("filter", v);
		}

		public void setExclude(String v) {
			setAttribute("exclude", v);
		}

		public void setStripext(String v) {
			setAttribute("stripext", v);
		}

		public void setHide_none(String v) {
			setAttribute("hide_none", v);
		}

		public void setHide_default(String v) {
			setAttribute("hide_default", v);
		}

		public void setClient(String v) {
			setAttribute("client", v);
		}

		public void setMenu_type(String v) {
			setAttribute("menu_type", v);
		}

		public void setState(String v) {
			setAttribute("state", v);
		}

		public void setRows(String v) {
			setAttribute("rows", v);
		}

		public void setCols(String v) {
			setAttribute("cols", v);
		}

	}

	static public class Option extends SimpleElement {
		public Option() {
			super("option");
		}

		public void setValue(String v) {
			setAttribute("value", v);
		}
	}

}