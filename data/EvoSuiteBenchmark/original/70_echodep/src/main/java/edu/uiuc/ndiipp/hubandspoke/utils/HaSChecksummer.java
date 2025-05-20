/**
 * HaSChecksummer.java
 * 
 * $Revision: 852 $
 * 
 * $Date: 2009-04-27 17:57:17 +0100 (Mon, 27 Apr 2009) $
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

package edu.uiuc.ndiipp.hubandspoke.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * <p>Gives us checksums!</p>
 * 
 * <p>To be used in conjunction with a CheckedInputStream. 
 * Digests are computed as bytes are read from the stream.</p> 
 * 
 * <p>For example:<br><br>
 * <code>
 * HaSChecksummer summer = new HaSChecksummer();	
 * File temp = readFile(new CheckedInputStream(new FileInputStream("/var/temp/file"), summer));
 * </code>
 * </p>
 * 
 * @author cordial
 * @see <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/util/zip/CheckedInputStream.html">CheckedInputStream</a>
 * @see <a href="http://java.sun.com/j2se/1.5.0/docs/api/java/util/zip/Checksum.html">Checksum</a>
 */
public class HaSChecksummer implements Checksum {

	private MessageDigest md5;
	private MessageDigest sha1;
	private CRC32 crc32;

	 public HaSChecksummer() {
		reset ();
	    }
	 
	public CRC32 getCRC32(){
		return crc32;
	}
	
	/**
	 * Getter for the raw MD5 Digest
	 * @return - md5
	 */		
	public MessageDigest getMd5Digest() {
		return md5;
	}

	/**
	 * Getter for the raw SHA1 digest
	 * @return - sha1
	 */
	public MessageDigest getSha1Digest() {
		return sha1;
	}
	
	
	/* (non-Javadoc)
	 * @see java.util.zip.Checksum#getValue()
	 */
	public long getValue() {
		return crc32.getValue();
	}

	/**
	 * clear!
	 */
	public void reset() {
        try {
            md5  = MessageDigest.getInstance ("MD5");
            sha1  = MessageDigest.getInstance ("SHA-1");
            crc32 = new CRC32();
        }
        catch (NoSuchAlgorithmException e) {
        	//should never happen
        }

	}

	 /**
     *  Updates the checksum with the argument.
     *  Called when a signed byte is available.
     */
    public void update (byte b){
		if ((md5 != null) && (sha1 != null) && (crc32 !=null)) {
		    md5.update (b);
		    sha1.update (b);
		    crc32.update(b);
		}
    }

    /**
     *  Updates the checksum with the argument.
     *  Called when an unsigned byte is available.
     */
    public void update (int b)
    {
		byte sb;
		if (b > 127) {
		    sb = (byte) (b - 256);
		}
		else {
		    sb = (byte) b;
		}
		update (sb);
    }
    
    /**
     *  Updates the checksum with the argument.
     *  Called when a byte array is available.
     */
    public void update (byte[] b){
		if ((md5 != null) && (sha1 != null)) {
		    md5.update (b);
		    sha1.update (b);
		    crc32.update(b);
		}
    }
    
    /**
     *  Updates the checksum with the argument.
     *  Called when a byte array is available.
     */
    public void update (byte[] b, int off, int len){
		if ((md5 != null) && (sha1 != null)) {
		    md5.update (b, off, len);
		    sha1.update (b, off, len);
		    crc32.update(b, off, len);
		}
    }

    /**
     *  Returns the value of the MD5 digest as a Base64 encoded string.
     *  Returns null if the digest is not available.
     */
    public String getBase64EncodedMD5(){
		String value = null;
		if (md5 != null) {
		     value = new String(Base64.encodeBase64(md5.digest()));
		}
		return value;
    }
    
    /**
     *  Returns the value of the SHA1 digest as a Base64 encoded string.
     *  Returns null if the digest is not available.
     */
    public String getBase64EncodedSHA1(){
		String value = null;
		if (sha1 != null) {
		     value = new String(Base64.encodeBase64(sha1.digest()));
		}
		return value;
    }
    
    /**
     *  Returns the value of the MD5 digest as a Hex encoded string.
     *  Returns null if the digest is not available.
     */
    public String getHexEncodedMD5() {
		String value = null;
		if (md5 != null) {
		     value = new String(Hex.encodeHex(md5.digest()));
		}
		return value;
    }
    
    /**
     *  Returns the value of the SHA1 digest as a Hex encoded string.
     *  Returns null if the digest is not available.
     */
    public String getHexEncodedSHA1() {
		String value = null;
		if (sha1 != null) {
		     value = new String(Hex.encodeHex(sha1.digest()));
		}
		return value;
    }
    
    /**
     *  Returns the value of the CRC32 checksum as a long.
     */
    public long getLongCRC32(){
    	return crc32.getValue();
    }

}
