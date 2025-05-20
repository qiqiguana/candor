/**
 * JhoveTransformer.java
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

import org.w3c.dom.*;
import javax.xml.transform.*;
import edu.harvard.hul.ois.mets.*;

/**
 * 
 * Manages the application of XSLTs to raw Jhove metadata
 * to produce a JhoveResult.  Higher level than the
 * JhoveXSLapplicators; knows the specific ones it needs
 * to use.
 *
 * @author Howard Ding
 */

public abstract class JhoveTransformer {

		/**
		 * Desired information to produce.
		 * 
		 */
		protected JhoveResult result;
	
		public JhoveTransformer () {
			result = new JhoveResult();
		}
		
		/**
		 * Accessor for raw Jhove metadata
		 * @param rawJhoveData Sets the raw Jhove metadata the object
		 * will work on.
		 */
		protected void setRawJhoveData(Node rawJhoveData) {
			result.setJhoveMetadata(rawJhoveData);
		}
	
		/**
		 * Generate the PREMIS Object metadata from the raw jhove metadata.
		 * 
		 * @param idType Type of ID to be placed in PREMIS metadata
		 * @param idValue Value of ID to be placed in PREMIS metadata
		 * @throws TransformerConfigurationException
		 * @throws TransformerException
		 */
		protected void generateBaseMetadata (String idType, String idValue) 
			throws TransformerConfigurationException, TransformerException
		{
			result.setBaseMetadata(this.getBaseApplicator().parameterTransform(result.getJhoveMetadata(), idType, idValue));
		}
		
		/**
		 * Generate the MIME-type specific metadata from the raw Jhove metadata.
		 * 
		 * @throws TransformerConfigurationException
		 * @throws TransformerException
		 */
		protected void generateSpecificMetadata ()
			throws TransformerConfigurationException, TransformerException
		{
			SpecificApplicator applicator = this.getSpecificApplicator();
			if (applicator == null) {
				result.setSpecificMetadata(null);
			} else {
				result.setSpecificMetadata(applicator.transform(result.getJhoveMetadata()));
				result.setSpecificMetadataType(applicator.specificMetadataType());
				result.setSpecificMetadataOtherMDType(applicator.specificMetadataOtherMDType());
			}
		}
		
		/**
		 * Create and return new BaseApplicator for the object to use.
		 * May be overridden if appropriate.
		 * 
		 * @return A BaseApplicator suitable for the raw Jhove metadata.
		 */
		protected BaseApplicator getBaseApplicator () {
			return new BaseApplicator();
		}
		
		/**
		 * Create and return a new SpecificApplicator for the object to use.
		 * 
		 * @return A SpecificApplicator suitable for the raw Jhove metadata.
		 */
		protected abstract SpecificApplicator getSpecificApplicator ();
		
		/**
		 * Perform the actual generation of PREMIS object and MIME-type
		 * specific metadata.  This is the intended method for clients
		 * to use.
		 * 
		 * @param idType PREMIS object ID type for the file
		 * @param idValue PREMIS object ID value for the file
		 * @return
		 *  JhoveResult holding the metadata for the file.
		 * @throws TransformerException
		 * @throws TransformerConfigurationException
		 */
		public JhoveResult generateMetadata (String idType, String idValue) 
			throws TransformerException, TransformerConfigurationException
		{
			this.generateBaseMetadata(idType, idValue);
			this.generateSpecificMetadata();
			return result;
		}
}
