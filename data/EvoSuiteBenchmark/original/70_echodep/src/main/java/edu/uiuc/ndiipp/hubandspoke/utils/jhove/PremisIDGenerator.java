/**
 * PremisIDGenerator.java
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

import edu.harvard.hul.ois.mets.File;

/**
 * 
 * This class is a helper for the METSAugmenter that
 * is responsible for returning the PREMIS idType and
 * idValue for a given file.  
 * 
 * Subclass to enable it to do this, and construct
 * an instance of this class to pass to the
 * METSAugmenter constructor for each METS file.
 * 
 * The methods receive a filePath and Harvard METS API
 * File object for each target file (I don't think there
 * is any more information that they can get to help them,
 * but if so, please feel free to correct it).
 * 
 * @author Howard Ding
 *
 */

public abstract class PremisIDGenerator {

	/**
	 * 
	 * @param filePath - path to the file
	 * @param file - Harvard METS API File object from the METS file
	 * @return - PREMIS id type
	 */
	public abstract String idType (String filePath, File file);
	
	/**
	 * 
	 * @param filePath - path to the file
	 * @param file - Harvard METS API File object from the METS file
	 * @return - PREMIS id value
	 */
	public abstract String idValue (String filePath, File file);
	
}
