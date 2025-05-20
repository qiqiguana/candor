/**
 * Copyright 2008 (C) Keith Bishop (bishi@users.sourceforge.net)
 *
 * All rights reserved.
 *
 * This file is part of FSPath.
 *
 * FSPath is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FSPath is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FSPath.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * DefaultFSPath.java
 *
 * Created on 18 September 2006, 00:25
 *
 */

package net.sf.sugar.fspath;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import net.sf.sugar.fspath.xpath.RegexFunctionResolver;
import org.w3c.dom.Attr;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 *  The default implementation of the FSPath interface.
 *  This class uses the JDK's XPath implementation as the basis for
 *  FSPath queries.
 *  On instantiation, a DOM is created of the filesystem metadata starting form the <code>rootDirectory</code>.
 *  This DOM can then be queried using standard XML tools.
 *  <br/>
 *  Whilst this approach has been relatively quick to implement, it is still tied to the limitations of XPath.
 *  Future implementations of this class are likely to implement the FSPath language fully themselves wthout relying on XPath.
 *  <br/>
 *  todo: develop some front end substitution to enable the short queries i.e. /var/www etc
 *
 * @author keith
 *  $Id$
 */
public class DefaultFSPath implements FSPath {

    /**
     *  Escape characters, we must escape any characters that are
     *  illegal in XML attribute text.
     *
     *  i.e. &amp; " < >
     */
    private Map escapeChars;

    private DocumentBuilder documentBuilder;

    private XPath xpath;

    private Document dom;

    /**
     *  The date format used to correspond to the xs:date format i.e. yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    private DateFormat format;

    private File rootDirectory;


    public DefaultFSPath() {
        //used for unit test instantiation

        this.xpath = XPathFactory.newInstance().newXPath();

        //this.xpath.setNamespaceContext(new FSNamespaceContext());

        //this effectively enables the user of our custom XPath function
        //fs:match()
        this.xpath.setXPathFunctionResolver(new RegexFunctionResolver());

        this.escapeChars = this.createEscapeCharsMap();

        this.format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    }

    /**
     * Creates a new instance of DefaultFSPath, based on the directory supplied
     */
    public DefaultFSPath(File currentDir) {
        this();

        this.rootDirectory = currentDir;

        //check if file is a directory
        if (! currentDir.isDirectory()) {
            throw new InstantiationError("the java.io.File specified must be a Directory");
        }

        try {
            this.createDocumentBuilder();

            //build DOM representation
            this.dom = this.buildDOM(currentDir);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            throw new InstantiationError("FSDom threw a ParserConfigurationException : " + pce.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError("FSDom threw an IOException : " + ioe.getMessage());
        }


    }

    protected Map createEscapeCharsMap() {
        Map<String, String> escapeChars = new HashMap<String, String>();

        escapeChars.put("&", "&#26;");
        escapeChars.put("<", "&#3c;");
        escapeChars.put(">", "&#3e;");
        escapeChars.put("\"", "&#22;");

        return escapeChars;
    }

    protected void createDocumentBuilder() throws ParserConfigurationException {
        try {
            this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            throw pce;
        }
    }

    protected Document buildDOM(File currentDir) throws IOException {
        Document dom = this.documentBuilder.newDocument();

        dom.appendChild(this.createChildElement(dom, currentDir));

        return dom;
    }

    private Element createChildElement(Document dom, File currentFile) throws IOException {

        Element currentElement = null;

        if (currentFile.isDirectory()) {

            currentElement = dom.createElement(FSPathAttributes.dir.name());

            //recurse and create child elements for all its children
            File[] children = currentFile.listFiles();

            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    currentElement.appendChild(this.createChildElement(dom, children[i]));
                }
            }
        } else {
            currentElement = dom.createElement(FSPathAttributes.file.name());
        }

        currentElement.setAttribute(FSPathAttributes.name.name(), currentFile.getName());
        currentElement.setAttribute(FSPathAttributes.absolutePath.name(), currentFile.getAbsolutePath());
        currentElement.setAttribute(FSPathAttributes.canRead.name(), Boolean.toString(currentFile.canRead())); //optional for speed?
        currentElement.setAttribute(FSPathAttributes.canWrite.name(), Boolean.toString(currentFile.canWrite())); //optional for speed?
        currentElement.setAttribute(FSPathAttributes.canonicalPath.name(), currentFile.getCanonicalPath());
        currentElement.setAttribute(FSPathAttributes.exists.name(), Boolean.toString(currentFile.exists())); //optional for speed?
        currentElement.setAttribute(FSPathAttributes.isAbsolute.name(), Boolean.toString(currentFile.isAbsolute()));
        currentElement.setAttribute(FSPathAttributes.isDirectory.name(), Boolean.toString(currentFile.isDirectory()));
        currentElement.setAttribute(FSPathAttributes.isFile.name(), Boolean.toString(currentFile.isFile()));
        currentElement.setAttribute(FSPathAttributes.isHidden.name(), Boolean.toString(currentFile.isHidden()));
        currentElement.setAttribute(FSPathAttributes.lastModified.name(), this.format.format(new Date(currentFile.lastModified())));
        currentElement.setAttribute(FSPathAttributes.length.name(), Long.toString(currentFile.length()));
        currentElement.setAttribute(FSPathAttributes.parent.name(), currentFile.getParent());
        currentElement.setAttribute(FSPathAttributes.path.name(), currentFile.getPath());
       
        return currentElement;
    }

    /**
     *  Calls this.query(expression, XPathConstants.NODESET)
     *
     *  Note : This method MUST be passed an expression which returns a nodeset.
     *
     *  @param expression the FSPath expression to execute.
     *  @returns <code>FSPathResultList</code> the FSPathResult objects contained
     *  in this list will be of type <code>java.io.File</code>,
     *  <code>java.lang.Double</code>, <code>java.lang.Boolean</code>,
     *  <code>java.lang.String</code>
     */
    public FSPathResultList query(String expression) {
        return this.query(expression, XPathConstants.NODESET);
    }

    /**
     *
     */
    public FSPathResultList query(String expression, QName returnType) {

        FSPathResultList results = new FSPathResultListImpl();

        try {

            if (XPathConstants.NODESET.equals(returnType)) {
                NodeList nodelist = (NodeList)this.xpath.evaluate(expression,
                                                              this.dom.getDocumentElement(),
                                                              XPathConstants.NODESET);
                if (nodelist.getLength() > 0) {
                    for (int i = 0; i < nodelist.getLength(); i++) {
                        processNode(nodelist.item(i), results);
                    }
                }
                return results;
            }

            if (XPathConstants.NODE.equals(returnType)) {
                Node node = (Node)this.xpath.evaluate(expression,
                                                      this.dom.getDocumentElement(),
                                                      XPathConstants.NODE);
                processNode(node, results);

                return results;
            }

            if (XPathConstants.BOOLEAN.equals(returnType)) {
                Boolean result = (Boolean)this.xpath.evaluate(expression,
                                                              this.dom.getDocumentElement(),
                                                              XPathConstants.BOOLEAN);

               results.add(new FSPathResult(result));

               return results;
            }

            if (XPathConstants.NUMBER.equals(returnType)) {
                Double result = (Double)this.xpath.evaluate(expression,
                                                            this.dom.getDocumentElement(),
                                                            XPathConstants.NUMBER);
                results.add(new FSPathResult(result));

                return results;
            }

            if (XPathConstants.STRING.equals(returnType)) {
                String result = (String)this.xpath.evaluate(expression,
                                                            this.dom.getDocumentElement(),
                                                            XPathConstants.STRING);

                results.add(new FSPathResult(result));

                return results;
            }


        } catch (XPathExpressionException xpee) {
            System.out.println("Invalid FSPath expression : " + xpee.getCause().getMessage());
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            System.out.println("IllegalArgumentException");
        } catch (ParseException pe) {
            pe.printStackTrace();
            System.out.println("ParseException");
        }


        return results;
    }

    private void processNode(Node node, FSPathResultList results)
                                                throws XPathExpressionException,
                                                       IllegalArgumentException,
                                                       ParseException {

        //as the only elements in our dom are files or directories we will try to
        //create File objects of those nodes.
        if (node.getNodeType() == Node.ELEMENT_NODE) {

            String fileName = node.getAttributes().getNamedItem(FSPathAttributes.absolutePath.name()).getNodeValue();
            //System.out.println("Filename : " + fileName);
            results.add(new FSPathResult(new File(fileName)));

            return;
        }

        if (node.getNodeType() == Node.ATTRIBUTE_NODE) {

            Attr attr = (Attr)node;

            //now work out which attirutes were Dates, Longs and Strings

            if (FSPathAttributes.absolutePath.name().equals(attr.getName())
                | FSPathAttributes.canonicalPath.name().equals(attr.getName())
                | FSPathAttributes.name.name().equals(attr.getName())
                | FSPathAttributes.parent.name().equals(attr.getName())
                | FSPathAttributes.path.name().equals(attr.getName())) {

                results.add(new FSPathResult(attr.getValue()));

                return;
            }

            if (FSPathAttributes.canRead.name().equals(attr.getName())
                | FSPathAttributes.canWrite.name().equals(attr.getName())
                | FSPathAttributes.exists.name().equals(attr.getName())
                | FSPathAttributes.isAbsolute.name().equals(attr.getName())
                | FSPathAttributes.isDirectory.name().equals(attr.getName())
                | FSPathAttributes.isFile.name().equals(attr.getName())
                | FSPathAttributes.isHidden.name().equals(attr.getName())) {

                results.add(new FSPathResult(new Boolean(attr.getValue())));

                return;
            }

            if (FSPathAttributes.lastModified.name().equals(attr.getName())) {
                results.add(new FSPathResult(this.format.parse(attr.getValue())));

                return;
            }

            if (FSPathAttributes.length.name().equals(attr.getName())) {
                results.add(new FSPathResult(Double.parseDouble(attr.getValue())));

                return;
            }
        }
    }

    public File getRootDirectory() {
        return this.rootDirectory;
    }



}
