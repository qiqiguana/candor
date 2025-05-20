/**
 * TechMDInvestigator.java
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

import java.io.File;
import java.io.PrintStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;

import edu.harvard.hul.ois.jhove.JhoveException;

/**
 * Command-line utility to see what our Augmentation routine will produce
 *  
 * @author Matt Cordial
 */
public class TechMDInvestigator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			File f = new File(args[0]);
			runJhove(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void runJhove(File file) throws JhoveException, Exception{
		JhoveResult result = JhoveGenerator.analyzeFileNamed(file.getAbsolutePath(), "file", file.getAbsolutePath());
		XMLToStream(result.jhoveMetadata, System.out);
		//String jhoveModule = determineModule(result.getJhoveMetadata());
		
	}
	
	/**
	 * Transform XML to stream
	 * 
	 * @param n
	 * @param stream
	 * @throws Exception
	 */
	protected static void XMLToStream (Node n, PrintStream stream) throws Exception {
		if (n != null) {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			Source s = new DOMSource(n);
			t.transform(s, new StreamResult(stream));
			stream.println("\n");
		}
	}
	
	/**
	 * Extracts the MIME type from Jhove metadata
	 * 
	 * @param jhoveInput The raw Jhove metadata
	 * @return String that encodes the MIME type given in the
	 * Jhove metadata
	 * @throws XPathExpressionException
	 */
	protected static String determineModule (Node jhoveInput)
		throws XPathExpressionException 
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(JhoveNamespaceContext.getInstance());
		String mime = xpath.evaluate("/jhove:jhove/jhove:repInfo/jhove:mimeType", jhoveInput);
		return mime;
	}

}
