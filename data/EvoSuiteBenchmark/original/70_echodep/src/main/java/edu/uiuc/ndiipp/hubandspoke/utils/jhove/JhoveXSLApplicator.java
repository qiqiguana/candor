/**
 * JhoveXSLApplicator.java
 * 
 * $Revision: 845 $
 * 
 * $Date: 2009-04-22 18:28:30 +0100 (Wed, 22 Apr 2009) $
 * 
 * Copyright (c) 2009, University Library, University of Illinois at 
 * Urbana-Champaign. All rights reserved. 
 * 
 * Developed by: The Hub and Spoke Project Group 
 *               University of Illinois Urbana-Champaign Library
 *               http://dli.grainger.uiuc.edu/echodep/hands/ 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the 
 * "Software"), to deal with the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to 
 * the following conditions: 
 * 
 * - Redistributions of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimers. 
 * 
 * - Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimers in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * - Neither the names of The Hub and Spoke Project Group, University of 
 * Illinois Urbana-Champaign Library, nor the names of its contributors may 
 * be used to endorse or promote products derived from this Software 
 * without specific prior written permission. 
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE. 
 */

package edu.uiuc.ndiipp.hubandspoke.utils.jhove;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;
import java.util.*;
import java.io.*;

/**
 * 
 * This class manages the application of a specific XSLT
 * stylesheet to raw Jhove input.  
 * Caches the stylesheet during any given run of an application.
 * All XSLs are assumed to reside in the "$HS_HOME/xslt/jhove" 
 * directory.
 * @author Howard Ding
 */

abstract class JhoveXSLApplicator {
	private static Object syncObj = new Object();

	//templateMap is basically a way to fake Smalltalk-like
	//class instance variables.  Is there a better
	//way to do it in Java?
	/**
	 * Map from XSL name to Templates object able to 
	 * instantiate Transformers for that XSL.  Used
	 * to cache the XSLs after they are loaded when 
	 * used the first time.
	 */
	private static Map<String,Templates> templateMap = 
		new HashMap<String,Templates>();
	
	/**
	 * Used to generate Templates for each XSL as needed.
	 */
	private static TransformerFactory transformer_factory =
		TransformerFactory.newInstance();

	/**
	 * Subclasses override this to specify their particular
	 * XSL.
	 * 
	 * @return Filename of the XSL.    
	 */
	protected abstract String XSLName () ;
	
	/**
	 * Used to get to the actual XSL file following the convention
	 * noted in the class comment.
	 * 
	 * @return String giving the full path to the XSL file.
	 */
	private String XSLFile () {
		return System.getenv("HS_HOME") +  File.separator + "xslt" +
			File.separator + "jhove" + File.separator + this.XSLName();
	}
	
	/**
	 * Get a Template for the XSL transformation represented by
	 * the class.
	 * 
	 * @return Template for the transformation.
	 * @throws TransformerConfigurationException
	 */
	private Templates getTemplate () throws TransformerConfigurationException {
		if (templateMap.containsKey(this.XSLName())) {
			return templateMap.get(this.XSLName());
		} else {
			Source s = new StreamSource(new File(this.XSLFile()));
			Templates t = transformer_factory.newTemplates(s);
			templateMap.put(this.XSLName(), t);
			return t;
		}
	}
	
	/**
	 * Get a Transformer for the XSL transformation represented by
	 * the class.
	 * 
	 * @return Transformer for the transformation.
	 * @throws TransformerConfigurationException
	 */
	protected Transformer getTransformer() throws TransformerConfigurationException {
		return this.getTemplate().newTransformer();
	}
	
	/**
	 * This class needed to be synchronized because is changes the javax parser 
	 * DocumentBuilderFactory from the Saxon to the Xerces implementation 
	 * and then back to Saxon before it returns. Without the synchronization, 
	 * this property change could cause side effects in XMLBeans, which 
	 * requires the Saxon implementation of DocumentBuilderFactory.
	 * 
	 * @param jhoveInput
	 *  The raw Jhove XML to transform
	 * @param parameters
	 *  Variable length.  Consists of parameter-name,
	 *  parameter-value pairs of strings (so should 
	 *  always be an even number) to be passed to the
	 *  stylesheet.
	 * @return
	 *  A DOM Node containing the results of the transformation.
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	
	public Node transform (Node jhoveInput, String... parameters)
		throws TransformerConfigurationException, TransformerException {
		
//		synchronized(syncObj){
//			String prevFactory = System.getProperty("javax.xml.parsers.DocumentBuilderFactory");
//			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
//			"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
			
			Transformer t = this.getTransformer();
			this.setTransformParameters(t,parameters);
			DOMResult result = new DOMResult();
			DOMSource dom = new DOMSource(jhoveInput);
			t.transform(dom, result);
			Node resultNode = result.getNode();
			this.postProcess(resultNode);
			
//			System.setProperty("javax.xml.parsers.DocumentBuilderFactory", prevFactory);
			
			return resultNode;
//		}
	}
	
	/**
	 * Actually perform the transform.  This is what clients will
	 * presumably use.
	 * 
	 * @param jhoveInput The raw Jhove XML to transform.
	 * @return A DOM Node containing the results of transformation.
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	
	public Node transform (Node jhoveInput) 
		throws TransformerConfigurationException, TransformerException {
		String [] nothing = {};
		return this.transform(jhoveInput, nothing);
	}
	
	/**
	 * Subclasses may override this method if they
	 * need to do non-XSL processing on the node before
	 * returning it, e.g. date normalization, incorporating
	 * information from other sources, etc.  As yet, I haven't
	 * used it; I added it because I thought I might need it
	 * in a certain case where I turned out not to.
	 * 
	 * @param n Node to be processed
	 * @return processed Node
	 */
	protected Node postProcess (Node n) {
		return n;
	}
	
	/**
	 * Set parameters for the XSL Transform.
	 * 
	 * @param t The transformer to be parameterized.
	 * @param parameters An array of Strings that are parameter-value pairs.
	 */
	private void setTransformParameters (Transformer t, String[] parameters) {
		for (int i = 0; i + 1 < parameters.length; i += 2) {
			t.setParameter(parameters[i], parameters[i+1]);
		}
	}
	
}
