/**
 * JhoveResult.java
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
import edu.harvard.hul.ois.mets.*;

/**
 * 
 * This is a simple container class to bind together
 * raw Jhove data, the transformed "base" metadata
 * required for a file (Administrative Metadata section,
 * Requirement 4 of METS profile), and the transformed 
 * "specific" metadata (Administrative Metadata section,
 * requirements 5-9).  All are represented as DOM Nodes. 
 * The "specific" metadata field may be null if none is
 * specified for a given MIME type.
 *
 * @author Howard Ding
 */

public class JhoveResult {

	/**
	 * The raw Jhove output upon which the other metadata is based.
	 */
	Node jhoveMetadata = null;
	
	/**
	 * The PREMIS object metadata that is required for each file.
	 */
	Node baseMetadata = null;
	
	/**
	 * The MIME type specific metadata if required for the file.
	 */
	Node specificMetadata = null;

	/**
	 * The Mdtype as specified in the Harvard METS API
	 */
	Mdtype specificMetadataType = null;
	
	/**
	 * A string for the "OTHERMD" attribute - needed and used
	 * only when the specificMetadataType is not NISOIMG
	 * (at least for now)
	 */
	String specificMetadataOtherMDType = null;
	
	/**
	 * Constructor if all metadata is known.
	 * @param jhoveMetadata Raw Jhove metadata
	 * @param baseMetadata PREMIS object metadata
	 * @param specificMetadata MIME type specific metadata (possibly null)
	 */
	public JhoveResult (Node jhoveMetadata, Node baseMetadata, Node specificMetadata) {
		this.jhoveMetadata = jhoveMetadata;
		this.baseMetadata = baseMetadata;
		this.specificMetadata = specificMetadata;
	}
	
	/**
	 * Constructor if no metadata is known.
	 *
	 */
	public JhoveResult () {
		
	}
	
	/**
	 * Accessor for PREMIS object metadata
	 * 
	 * @return Node representing PREMIS object metadata
	 */
	public Node getBaseMetadata() {
		return baseMetadata;
	}
	
	/**
	 *  Accessor for PREMIS object metadata
	 * @param baseMetadata Node representing PREMIS object metadata
	 */
	public void setBaseMetadata(Node baseMetadata) {
		this.baseMetadata = baseMetadata;
	}
	
	/**
	 * Accessor for raw Jhove metadata
	 * @return Node representing raw Jhove metadata
	 */
	public Node getJhoveMetadata() {
		return jhoveMetadata;
	}
	
	/**
	 *  Accessor for raw Jhove metadata
	 * @param jhoveMetadata Node representing raw Jhove metadata
	 */
	public void setJhoveMetadata(Node jhoveMetadata) {
		this.jhoveMetadata = jhoveMetadata;
	}
	
	/**
	 * Accessor for MIME type specific metadata
	 * @return Node representing MIME type specific metadata
	 */
	public Node getSpecificMetadata() {
		return specificMetadata;
	}
	
	/**
	 * Accessor for MIME type specific metadata
	 * @param specificMetadata Node representing MIME type specific metadata
	 */
	public void setSpecificMetadata(Node specificMetadata) {
		this.specificMetadata = specificMetadata;
	}

	/**
	 * Accessor for specificMetadataOtherMDType
	 * @return String naming the metadata type
	 */
	public String getSpecificMetadataOtherMDType() {
		return specificMetadataOtherMDType;
	}

	/**
	 * Accessor for specificMetadataOtherMDType
	 * 
	 * @param specificMetadataOtherMDType String naming the metadata type
	 */
	public void setSpecificMetadataOtherMDType(String specificMetadataOtherMDType) {
		this.specificMetadataOtherMDType = specificMetadataOtherMDType;
	}

	/**
	 * Accessor for specificMetadataType
	 * 
	 * @return Mdtype (from Harvard METS API) for Mdtype
	 */
	public Mdtype getSpecificMetadataType() {
		return specificMetadataType;
	}

	/**
	 * Accessor for specificMetadataType
	 * 
	 * @param specificMetadataType Mdtype (from Harvard METS API) for Mdtype
	 */
	public void setSpecificMetadataType(Mdtype specificMetadataType) {
		this.specificMetadataType = specificMetadataType;
	}
	
}
