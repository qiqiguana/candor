/**
 * JhoveGenerator.java
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

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import java.io.*;
import javax.xml.transform.stream.*;
import edu.harvard.hul.ois.jhove.*;
import org.w3c.dom.*;

/**
 * This is the main entry point for users of the code.
 * 
 * @author Howard Ding
 * 	
 */
public class JhoveGenerator {

	/**
	 * The main method is just for informal testing.
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String filename = args[0];
		JhoveResult result = analyzeFileNamed(filename, "ID-Type", "ID-Value");
		System.out.println("-----RawJhove-----\n");
		XMLToStream(result.jhoveMetadata, System.out);
		System.out.println("-----BaseMetadata-----\n");
		XMLToStream(result.baseMetadata, System.out);
		System.out.println("-----SpecificMetadata-----\n");
		XMLToStream(result.specificMetadata, System.out);
		
	}

	/**
	 * This is also just for testing.
	 * 
	 * @param n
	 * @param stream
	 * @throws Exception
	 */
	private static void XMLToStream (Node n, PrintStream stream) 
		throws Exception 
	{
		if (n != null) {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			Source s = new DOMSource(n);
			t.transform(s, new StreamResult(stream));
			stream.println("\n");
		}
	}
	
	/**
	 * This is the main entry point for clients.  Supply a filename, a PREMIS ID type,
	 * a PREMIS ID value, and receive back a JhoveResult that contains the
	 * raw Jhove metadata, the PREMIS object metadata appropriate for the MIME type of
	 * the file, and perhaps another set of metadata if the profile calls for it
	 * for that MIME type.
	 * 
	 * Note that Jhove must be configured properly for this to work.  See the readme.txt 
	 * for the project to see how to do this.
	 * 
	 * @param filename Path to the file to be analyzed
	 * @param idType ID type of the file to be analyzed
	 * @param idValue ID value of the file to be analyzed
	 * @return JhoveResult containing the original Jhove metadata and
	 * transformed metadata.
	 * @throws JhoveException
	 * @throws Exception
	 */
	
	public static JhoveResult analyzeFileNamed (String filename, String idType, String idValue) 
		throws JhoveException, Exception
	{
		JhoveTransformer transformer = JhoveTransformerFactory.getJhoveTransformer(filename);
		return transformer.generateMetadata(idType, idValue);
	}
	
}
