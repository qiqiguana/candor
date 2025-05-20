/*
 * $Id: CodeGenerator.java,v 1.8 2005/12/28 09:46:40 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/tools/CodeGenerator.java,v $
 * IFX-Framework - IFX XML to JavaBean application framework.
 * Copyright (C) 2003  The IFX-Framework Team
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.sourceforge.ifx.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

/**
 * CodeGenerator uses JDOM to parse the IFX Schema file and build the source
 * code for the IFX beans under the given directory.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.8 $
 */
public class CodeGenerator {

    /** Where will the output be generated relative to calling dir? */
    private static final String OUTPUT_DIR = "src-gen";
    /** Where is the GPL header kept relative to calling dir? */
    private static final String GPL_TEMPLATE = "docs/lgpl.template";
    /** What is going to be the package root of the beans? */
    private static final String DEFAULT_PACKAGE_ROOT = "org.sourceforge.ifx.framework.";
    /** Base class from which all our objects descend from */
    private static final String ROOT_CLASS = 
        "org.sourceforge.ifx.basetypes.IFXObject";
    /** Basetype from which all ifx beans inherit from */
    private static final String IBASETYPE = 
        "org.sourceforge.ifx.basetypes.IBaseType";
    /** Standard javadoc comment */
    private static final String DEFAULT_CLASS_JAVADOC = "Generated code.";
    /** Some xsd: to Java mappings */
    private static final String[][] BASE_MAP_DATA = {
        {"xsd:string", "org.sourceforge.ifx.basetypes.IFXString"},
        {"xsd:hexBinary", "org.sourceforge.ifx.basetypes.IFXHexBinary"},
        {"xsd:base64Binary", "org.sourceforge.ifx.basetypes.IFXBase64Binary"},
        {"xsd:date", "org.sourceforge.ifx.basetypes.IFXDate"},
        {"xsd:time", "org.sourceforge.ifx.basetypes.IFXTime"},
        {"xsd:dateTime", "org.sourceforge.ifx.basetypes.IFXDateTime"},
        {"xsd:decimal", "org.sourceforge.ifx.basetypes.IFXDecimal"},
        {"xsd:long", "org.sourceforge.ifx.basetypes.IFXLong"},
        {"xsd:ID", "org.sourceforge.ifx.basetypes.IFXString"},
        {"xsd:boolean", "org.sourceforge.ifx.basetypes.IFXBoolean"}
    };

    private String filename = null;
    private Map fqcnMap = new HashMap();
    private Map beanMap = new HashMap();
    private String headerTemplate = null;
    private Namespace defNS;

    /**
     * Default constructor. Sets up some global variables.
     */
    public CodeGenerator() {
        this.headerTemplate = getHeaderTemplate();
        this.defNS = Namespace.getNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
    }

    /**
     * Sets the name of the xsd file to be read to generate the beans. The
     * file name should be specified relative to the current calling directory.
     * @param filename the name of the xsd file to read.
     */
    public void setInputFileName(String filename) {
        this.filename = filename;
    }
    
    /**
     * Parses the xsd file and hands off the generation of source files
     * to the generateSource() method.
     * @exception Exception if any is thrown.
     */
    public void run() throws Exception {
        // open main file
        SAXBuilder builder = new SAXBuilder(false);
        File mainSchemaFile = new File(this.filename);
        String basedir = mainSchemaFile.getParent();
        Document mainDoc = builder.build(new FileInputStream(mainSchemaFile));
        Element schemaElement = mainDoc.getRootElement();
        // handle imports
        Map namespaceMap = new HashMap();
        List namespaces = schemaElement.getAdditionalNamespaces();
        for (int i = 0; i < namespaces.size(); i++) {
            Namespace ns = (Namespace) namespaces.get(i);
            namespaceMap.put(ns.getURI(), ns.getPrefix());
        }
        List children = schemaElement.getChildren("import", schemaElement.getNamespace());
        Document[] importedDocs = new Document[children.size()];
        Map documentPackageMap = new HashMap();
        for (int i = 0; i < importedDocs.length; i++) {
            Element importElement = (Element) children.get(i);
            String namespaceURI = importElement.getAttributeValue("namespace");
            String schemaLocation = importElement.getAttributeValue("schemaLocation");
            importedDocs[i] = builder.build(new FileInputStream(basedir + File.separatorChar + schemaLocation));
            documentPackageMap.put(importedDocs[i], DEFAULT_PACKAGE_ROOT + (String) namespaceMap.get(namespaceURI) + ".");
            buildBaseNameMap(importedDocs[i], (String) documentPackageMap.get(importedDocs[i]));
        }
        // build the base name map for the main doc
        buildBaseNameMap(mainDoc, DEFAULT_PACKAGE_ROOT);
        // now generate the source for the imports
        for (int i = 0; i < importedDocs.length; i++) {
            parseDocument(importedDocs[i], (String) documentPackageMap.get(importedDocs[i]));
        }
        // and generate the source for the main file
        parseDocument(mainDoc, DEFAULT_PACKAGE_ROOT);
        // clean up and complete
        System.out.print("Writing element and bean mappings");
        writeMaps();
        System.out.println("...done");
        System.out.print("Writing package.html files for Javadocs");
        writePackageHtml();
        System.out.println("...done");
    }

    /**
     * Builds a Map of bean names to fully qualified class names and 
     * populates a class global Map object.
     * @param beanElements a List of Elements, each Element corresponds
     * to an IFX Framework bean.
     * @param packageRoot the package root for this Document. This
     * is the same as the default package root for the main document, but
     * is offset by the namespace for the imported Document objects. 
     */
    public void buildBaseNameMap(Document doc, String packageRoot) throws Exception {
        // build mappings for the base types first, if not already built
        if (fqcnMap.get("xsd:string") == null) {
            for (int i = 0; i < BASE_MAP_DATA.length; i++) {
                fqcnMap.put(BASE_MAP_DATA[i][0], BASE_MAP_DATA[i][1]);
            }
        }
        // build up the mappings for this document object.
        List elements = doc.getRootElement().getChildren();
        for (Iterator it = elements.iterator(); it.hasNext();) {
            Element element = (Element) it.next();
            if ("import".equals(element.getName())) {
                continue;
            }
            String classname = getClassNameFromElement(element, packageRoot);
            String beanName = classname.substring(classname.lastIndexOf('.') + 1);
            String oldClass = (String) fqcnMap.get(beanName);
            if (oldClass != null && oldClass.startsWith("java.")) {
                // prevents overwriting map for Long and Boolean IFX types
                continue;
            } else {
                fqcnMap.put(beanName, classname);
            }
        }
    }

    /**
     * Parses the Document object and generates the source for each of the
     * elements in teh Document object.
     * @param doc the Document object to parse.
     * @param packageRoot the package root.
     * @throws Exception if one is thrown.
     */
    public void parseDocument(Document doc, String packageRoot) throws Exception {
        Element rootElement = doc.getRootElement();
        List beanElements = rootElement.getChildren();
        int numElements = beanElements.size();
        for (int i = 0; i < numElements; i++) {
            Element beanElement = (Element) beanElements.get(i);
            if ("import".equals(beanElement.getName())) {
                continue;
            }
            String className = getClassNameFromElement(beanElement, packageRoot);
            System.out.print("Generating: " + className);
            generateSource(className, beanElement);
            System.out.println("...done");
            beanMap.put(beanElement.getAttributeValue("name"), className);
        }
    }

    /**
     * Generates the fully qualified class name from the Element and the
     * package root string.
     * @param element the Element to parse.
     * @param packageRoot the root of the package tree this bean should live in.
     * @return the fully qualified class name.
     */
    private String getClassNameFromElement(Element element, String packageRoot) {
        String packageNameKey = element.getName();
        String packageName = packageRoot + packageNameKey.toLowerCase();
        String nameAttr = element.getAttributeValue("name");
        return packageName + "." + normalize(nameAttr);
    }
        
    /**
     * Builds a String from the supplied license template.
     * @return the header template for each source file.
     */
    private String getHeaderTemplate() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(GPL_TEMPLATE)));
            String line;
            StringBuffer buf = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            return buf.toString();
        } catch (Exception e) { return ""; }
    }

    /**
     * The workhorse method. Parses and generates the bean source for
     * each top level element in the xsd file.
     * @param classname the fully qualified class name to generate.
     * @param beanElement the top level element.
     * @exception Exception if any are thrown.
     */
    private void generateSource(String classname, Element beanElement) 
            throws Exception {
        String beanName = classname.substring(classname.lastIndexOf('.') + 1);
        String packageName = classname.substring(0, classname.lastIndexOf('.'));
        JavaSource source = new JavaSource();
        // set the superclass to IFXObject by default, this will be reset
        // by some classes which actually have a superclass defined by the
        // IFX spec. The idea here is that if a class does not have a parent
        // it will extend the IFXObject, not java.lang.Object.
        source.setSuperClass(ROOT_CLASS);
        source.setPackageName(packageName);
        source.setClassName(beanName);
        if (beanElement.getName().equals("simpleType")) {
            // restriction is modelled as a superclass
            Element restrictionElement = 
                beanElement.getChild("restriction", defNS);
            if (restrictionElement != null) {
                String base = restrictionElement.getAttributeValue("base");
                if (base.startsWith("xsd:")) {
                    source.setInterface(IBASETYPE);
                }
                base = normalize(base);
                source.setSuperClass((String) fqcnMap.get(base));
                Element maxlengthElement = 
                    restrictionElement.getChild("maxlength", defNS);
                if (maxlengthElement != null) {
                    source.setClassJavadocs("maxlength = " + 
                        maxlengthElement.getAttributeValue("value"));
                }
            }
            Element annotationElement = 
                beanElement.getChild("annotation", defNS);
            if (annotationElement != null) {
                Element documentationElement = 
                    annotationElement.getChild("documentation", defNS);
                source.setClassJavadocs(documentationElement.getText());
            }
        } else if (beanElement.getName().equals("complexType")) {
            // annotation: defines some documentation
            Element annotationElement = 
                beanElement.getChild("annotation", defNS);
            if (annotationElement != null) {
                Element documentationElement = 
                    annotationElement.getChild("documentation", defNS);
                source.setClassJavadocs(documentationElement.getText());
            }
            // complexContent: this defines a superclass
            Element complexContentElement = 
                beanElement.getChild("complexContent", defNS);
            if (complexContentElement != null) {
                Element extensionElement = 
                    complexContentElement.getChild("extension", defNS);
                if (extensionElement != null) {
                    String base = extensionElement.getAttributeValue("base");
                    if (base.startsWith("xsd:")) {
                        source.setInterface(IBASETYPE);
                    }
                    base = normalize(base);
                    source.setSuperClass((String) fqcnMap.get(base));
                }
            }
            // optional attribute id appears in some complexType elements.
            // This is handled by having a special id attribute
            Element attributeElement = beanElement.getChild("attribute", defNS);
            if (attributeElement != null) {
                String name = attributeElement.getAttributeValue("name");
                String type = attributeElement.getAttributeValue("type");
                name = normalize(name);
                type = normalize(type);
                source.addMemberVariable(
                    name, (String) fqcnMap.get(type), false);
            }
            List childElements = beanElement.getChildren();
            Iterator childIter = childElements.iterator();
            while (childIter.hasNext()) {
                Element childElement = (Element) childIter.next();
                if (childElement.getName().equals("sequence")) {
                    // sequence: defines a composite, content can be an element
                    // or another sequence, for sequence drill down to the 
                    // element and addMemberVariable, sequence can be choice
                    processSequence(childElement, source, false);
                } else if (childElement.getName().equals("choice")) {
                    // choice: interface description, take the first one and 
                    // prefix I to it and put in interface directory.
                    processChoice(childElement, source, false);
                }
            }
        } else if (beanElement.getName().equals("element")) {
            String type = beanElement.getAttributeValue("type");
            if (type != null) {
                // simply extends another type at the top level
                source.setSuperClass(
                    (String) fqcnMap.get(normalize(type)));
            } else {
                // contains a complex type composed of other element
                Element complexElement = 
                    beanElement.getChild("complexType", defNS);
                if (complexElement != null) {
                    List complexElementChildren = 
                        complexElement.getChildren();
                    Iterator complexElementChildrenIter = 
                        complexElementChildren.iterator();
                    while (complexElementChildrenIter.hasNext()) {
                        Element complexElementChild = 
                            (Element) complexElementChildrenIter.next();
                        if (complexElementChild.getName().equals(
                                "sequence")) {
                            processSequence(complexElementChild, source, false);
                        } else if (complexElementChild.getName().equals(
                                "choice")) {
                            processChoice(complexElementChild, source, false);
                        } else if (complexElementChild.getName().equals(
                                "complexContent")) {
                            Element extensionElement = 
                              complexElementChild.getChild("extension", defNS);
                            String base = 
                                extensionElement.getAttributeValue("base");
                            if (base.startsWith("xsd:")) {
                                source.setInterface(IBASETYPE);
                            }
                            base = normalize(base);
                            source.addMemberVariable(
                                base,(String) fqcnMap.get(base), false);
                        }
                    }
                }
            }
        }
        // dump the java source
        VelocityWriter writer = new VelocityWriter();
        writer.write(classname, source);
    }

    /**
     * Figures out the file name from a fully qualified class name for an
     * IFX bean and returns a File handle for it.
     * @param classname the fully qualified class name of the IFX bean.
     * @return the File handle.
     */
    private File getFileName(String classname) throws Exception {
        File f = new File(OUTPUT_DIR + File.separator + 
            classname.replace('.', File.separatorChar) + ".java");
        f.getParentFile().mkdirs();
        return f;
    }

    /**
     * Normalizes XML name declarations to Java like names. Replaces dots
     * (.) with underscore(_), and colon(:) with dot(.).
     * @param s the String to normalize.
     * @return the normalized string.
     */
    private String normalize(String s) {
        String temp = s;
        if (s.startsWith("xsd:")) {
            return temp;
        } else if (s.startsWith("ifx:")) {
            return temp.substring(4);
        } else {
            if (s.indexOf(':') > -1) {
                temp = s.substring(s.indexOf(':') + 1);
            }
            return temp.replace('.', '_');
        }
    }

    /**
     * Processes the sequence element and adds a property to the source
     * object if it finds an element. This is a recursive function, if
     * it finds an embedded sequence, it will call itself, if it finds
     * an embedded choice element, then it will call the choice, and 
     * daisy chaining is also possible.
     * @param seqEl the sequence element.
     * @param source the JavaSource object to update.
     * @param hasMultiple if maxOccurs=unbounded on enclosing element.
     * @exception Exception if thrown.
     */
    private void processSequence(Element seqEl, JavaSource source, 
            boolean hasMultiple) throws Exception {
        boolean hasMulti = hasMultiple;
        String maxOccurs = seqEl.getAttributeValue("maxOccurs");
        if (("unbounded").equals(maxOccurs)) {
            hasMulti = true;
        }
        List sequenceElements = seqEl.getChildren();
        Iterator sequenceElementIter = sequenceElements.iterator();
        while (sequenceElementIter.hasNext()) {
            Element sequenceElement = (Element) sequenceElementIter.next();
            if (sequenceElement.getName().equals("element")) {
                String ref = sequenceElement.getAttributeValue("ref");
                if (ref != null) {
                    ref = normalize(ref);
                } else {
                    ref = sequenceElement.getAttributeValue("name");
                    String type = sequenceElement.getAttributeValue("type");
                    if (type != null) {
                        ref = type;
                    }
                    ref = normalize(ref);
                }
                maxOccurs = sequenceElement.getAttributeValue("maxOccurs");
                hasMulti = hasMulti || ("unbounded").equals(maxOccurs);
                source.addMemberVariable(
                    ref, (String) fqcnMap.get(ref), hasMulti);
            } else if (sequenceElement.getName().equals("sequence")) {
                processSequence(sequenceElement, source, hasMulti);
            } else if (sequenceElement.getName().equals("choice")) {
                processChoice(sequenceElement, source, hasMulti);
            }
        }
    }

    /**
     * Processes the choice element. This builds a marker interface by
     * prefixing the first element with I and putting it into the
     * interfaces directory. This is also a recursive method, although
     * recursion is more likely to be of the daisy chain variety than 
     * the direct one, going by the data.
     * @param chEl the choice element.
     * @param source the JavaSource object to update.
     * @param hasMultiple if maxOccurs=unbounded for enclosing element.
     * @exception Exception if thrown.
     */
    private void processChoice(Element chEl, JavaSource source, 
            boolean hasMultiple) throws Exception {
        boolean hasMulti = hasMultiple;
        String maxOccurs = chEl.getAttributeValue("maxOccurs");
        if (("unbounded").equals(maxOccurs)) {
            hasMulti = true;
        }
        List elementElements = chEl.getChildren("element", defNS);
        String ref0 = null;
        if (elementElements.size() > 0) {
            ref0 = ((Element) elementElements.get(0)).getAttributeValue("ref");
            // for version 1.7 on, @ref has been replaced by @type
            if (ref0 == null) {
                ref0 = ((Element) elementElements.get(0)).getAttributeValue("type");
            }
            ref0 = normalize(ref0);
        }
        // build the marker interface
        MarkerInterface mi = new MarkerInterface();
        mi.setName("I" + (ref0 == null ? "Choice_" : ref0));
        mi.setPackage(DEFAULT_PACKAGE_ROOT + "interfaces");
        VelocityWriter writer = new VelocityWriter();
        writer.write(mi.getPackage() + "." + mi.getName(), mi);
        // build properties for each of the embedded elements
        int numElementElements = elementElements.size();
        for (int i = 0; i < numElementElements; i++) {
            Element elementElement = (Element) elementElements.get(i);
            String ref = elementElement.getAttributeValue("ref");
            // for version 1.7, @ref has been replaced with @type
            if (ref != null) {
                ref = normalize(ref);
            } else {
                ref = elementElement.getAttributeValue("name");
                String type = elementElement.getAttributeValue("type");
                if (type != null) {
                    ref = type;
                }
                ref = normalize(ref);
            }
            maxOccurs = elementElement.getAttributeValue("maxOccurs");
            hasMulti = hasMulti || ("unbounded").equals(maxOccurs);
            if (hasMulti) {
                source.addMemberVariable(
                    ref, (String) fqcnMap.get(ref), true);
            } else {
                source.addMemberVariable(
                    ref, (String) fqcnMap.get(ref), false);
            }
        }
        // hand off for embedded sequences
        List sequenceElements = chEl.getChildren("sequence", defNS);
        int numSequenceElements = sequenceElements.size();
        for (int i = 0; i < numSequenceElements; i++) {
            Element sequenceElement = (Element) sequenceElements.get(i);
            processSequence(sequenceElement, source, hasMulti);
        }
    }

    /**
     * Writes out a bean,properties file for use by IfxXmlDecoder.
     * @exception Exception if one is thrown by the method.
     */
    private void writeMaps() throws Exception {
        PropertyFile elementMapProps = new PropertyFile();
        PropertyFile beanMapProps = new PropertyFile();
        Iterator iter = beanMap.keySet().iterator();
        while (iter.hasNext()) {
            String elementName = (String) iter.next();
            String className = (String) beanMap.get(elementName);
            elementMapProps.addProperty(elementName, className);
            beanMapProps.addProperty(className, elementName);
        }
        VelocityWriter writer = new VelocityWriter();
        writer.setBaseDir(OUTPUT_DIR);
        writer.setSuffix("properties");
        writer.write("org.sourceforge.ifx.framework.elementmap", 
            elementMapProps);
        writer.write("org.sourceforge.ifx.framework.beanmap", beanMapProps);
    }

    /**
     * Generate the package.html file for each generated package.
     */
    private void writePackageHtml() {
        try {
            VelocityWriter writer = new VelocityWriter();
            writer.setBaseDir(OUTPUT_DIR);
            writer.setSuffix("html");
            String[] packageNames = {
                "complextype", "element", "interfaces", "simpletype"
            };
            for (int i = 0; i < packageNames.length; i++) {
                PackageHtml packageHtmlBean = new PackageHtml();
                packageHtmlBean.setPackageName(packageNames[i]);
                writer.write("org.sourceforge.ifx.framework." + 
                    packageNames[i] + ".package", packageHtmlBean);
            }
        } catch (Exception e) { e.printStackTrace();} //:IGNORE: will not happen
    }

    /**
     * This is how we are called.
     * @param argv an array of arguments, only one is needed, its the
     * xsd file to read.
     */
    public static void main(String[] argv) {
        try {
            CodeGenerator generator = new CodeGenerator();
            generator.setInputFileName(argv[0]);
            generator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
