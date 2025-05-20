/**
 * HaSFileUtils.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.CheckedInputStream;

/**
 * @author cordial
 */
public class HaSFileUtils {
    
    /**
	 * Creates a temp file from the stream in the default system location. 
      * This DOES NOT set deleteOnExit() so be sure to call delete() 
      * explicitly.
	 * 
	 * @param is -- InputStream
	 * @return File -- temp file
	 * @throws IOException 
	 */
	public static File saveStreamToTempFile(InputStream is) 
	throws IOException {
        return saveStreamToTempFile(is, null);
	}
     
     /**
	 * Creates a temp file from the stream in the default system location. 
     * This DOES NOT set deleteOnExit() so be sure to call delete() 
     * explicitly.
	 * 
	 * @param is -- InputStream
     * @param name -- String denoting what to call the temp file.
	 * @return File -- temp file
	 * @throws IOException 
	 */
	public static File saveStreamToTempFile(InputStream is, String name) 
	throws IOException {
         File temp = null;
         if (name == null){
             temp = File.createTempFile("hubandspoke", null);
         } else {
             temp = new File(System.getProperty("java.io.tmpdir")+File.separator+name);
         }
		FileOutputStream fout = new FileOutputStream(temp); 
		byte[] buf = new byte[1024 * 10];
		int read;
		while ((read = is.read(buf)) != -1){
			fout.write(buf, 0, read);
		}
		is.close();
		return temp;
	}
	

	
	/**
	 * Creates a new directory in the computer's temp directory
	 * 	with then name 'echodep' followed by a random integer.
	 * @return
	 */
	public static File createTempDirectory(){
		return createTempDirectory("echodep");
	}
	
	/**
	 * Creates a new directory in the computer's temp directory
	 * 
	 * @param namePrefix -- Name part for return directory; will be
	 * 						followed by a random integer.
	 * @return
	 */
	public static File createTempDirectory(String namePrefix){
		File tempdir = null;
        // hack to create a temporary directory
        try {
                tempdir = File.createTempFile(namePrefix, null);
                tempdir.delete();
                tempdir.mkdir();
        } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
        }
        return tempdir;
	}
     
     /**
	 * Returns the Base64 encoded MD5 Checksum for specified file 
	 * @param f -- File
	 * @return String representing the date in the format specified by RFC 1123
	 * @throws IOException 
	 */
	public static String getBase64MD5(File f) 
	throws IOException {
		HaSChecksummer summer = new HaSChecksummer();
		CheckedInputStream is = new CheckedInputStream(new FileInputStream(f), summer);
		byte[] buf = new byte[(int)f.length()];
		while (is.read(buf) != -1){
			//do nothing -- just read
		}
		is.close();
		return summer.getBase64EncodedMD5();
	}
	
	/**
	 * Returns the Base64 encoded MD5 Checksum for specified InputStream
	 * 
	 * WARNING: This method closes the stream it is passed! If any further processing 
	 * is required you must re-open the stream.
	 * 
	 * @param in -- InputStream
	 * @return String representing the date in the format specified by RFC 1123
	 * @throws IOException 
	 */
	public static String getBase64MD5(InputStream in) 
	throws IOException {
		HaSChecksummer summer = new HaSChecksummer();
		CheckedInputStream is = new CheckedInputStream(in, summer);
		byte[] buf = new byte[1024 * 10];
		while (is.read(buf) != -1){
			//do nothing -- just read
		}
		is.close();
		return summer.getBase64EncodedMD5();
	}
	
	/**
	 * Copies a directory and all of its contents.
	 * 
	 * @param sourceLocation
	 * @param targetLocation
	 */
	public static void copyDirectory(File sourceLocation, File targetLocation)
			throws IOException {

		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]), new File(
						targetLocation, children[i]));
			}
		} else {

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}
	
	/**
	 * Deletes a directory and all of its contents.
	 * 
	 * @param path
	 *            -- directory to delete
	 */
	public static boolean deleteDirectory(File path) {
		boolean success = false;
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				}
				files[i].delete();
			}
			success = path.delete();
		}
		return (success);
	}

}
