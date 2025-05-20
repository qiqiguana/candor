/*
 * Copyright (c) 2002, Mikael Ståldal
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions 
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright 
 * notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimer in the 
 * documentation and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the author nor the names of its contributors 
 * may be used to endorse or promote products derived from this software 
 * without specific prior written permission. 
 * 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR 
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY 
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * 
 * Note: This is known as "the modified BSD license". It's an approved 
 * Open Source and Free Software license, see 
 * http://www.opensource.org/licenses/ 
 * and
 * http://www.gnu.org/philosophy/license-list.html
 */

package nu.staldal.lagoon.core;

import org.xml.sax.*;


/**
 * Defines operations a Producer can do on a File target.
 */
public interface FileTarget extends Target
{
    /**
     * Open a new target file, and close the current one.
     * Used for splitting.
     *
     * @param filename  filename to use, must <em>not</em> start with '/'
	 * @param prependFilename  prepend the name of the current main file
	 *                         and an '_' to the new file name.
     */
    public void newTarget(String filename, boolean prependFilename);
	

    /**
     * Open a new target file, without closing the current one.
     *
     * @param filename  filename to use, may start with '/'
	 * @param prependFilename  prepend the name of the current main file
	 *                         and an '_' to the new file name (has no
	 *                         effect if filename starts with '/').
	 *
	 * @return an OutputHandler to send a byte stream to
     */
    public OutputHandler newAsyncTarget(String filename, boolean prependFilename)
		throws java.io.IOException;

	
    /**
     * Open a new target file, without closing the current one.
	 * Uses an <output> specification from the Sitemap.
     *
     * @param filename  filename to use, may start with '/'
	 * @param prependFilename  prepend the name of the current main file
	 *                         and an '_' to the new file name (has no
	 *                         effect if filename starts with '/').
	 * @param output  an <output> specification from the Sitemap.
	 *
	 * @return an SAX ContentHandler to send an XML stream to 
     */
    public ContentHandler newAsyncTargetWithOutput(
			String filename, boolean prependFilename, String output)
		throws java.io.IOException, SAXException;

		
	/**
     * Determine if this file target is a wildcard.
     */
	public boolean isWildcard();
	
}
