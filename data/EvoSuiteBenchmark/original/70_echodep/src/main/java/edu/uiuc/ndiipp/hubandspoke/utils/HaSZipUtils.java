/**
 * HaSZipUtils.java
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class HaSZipUtils {
	
	public static void addFilesToZip(File f, ZipOutputStream zip) 
	throws IOException {
		addFilesToZip(new FileInputStream(f), zip, f.getName());
	}
	
	public static void addFilesToZip(File[] f, ZipOutputStream zip) 
	throws IOException {
		for (int i = 0; i < f.length; i++) {
			addFilesToZip(new FileInputStream(f[i]), zip, f[i].getName());
		}
	}
	
	public static void addFilesToZip(InputStream in, ZipOutputStream zip, String filename) 
	throws IOException {
		ZipEntry ze;
		byte[] buf = new byte[1024 * 4];//for reading the files into the zip	
		ze = new ZipEntry(filename);
		zip.putNextEntry(ze);
		int len;
		while((len = in.read(buf)) > 0) {
            zip.write(buf, 0, len);
        }
		zip.closeEntry();
		in.close();
	}
	
	public static File unzip(ZipInputStream zip, String path) 
	throws FileNotFoundException, IOException {		
		File outdir = new File(path);
		if (!outdir.isDirectory()){
			throw new RuntimeException("Path must be a directory");
		}
		
		BufferedOutputStream dest = null;
		ZipEntry entry;
		
		final int BUFFER = 2048;
		
		while((entry = zip.getNextEntry()) != null) {
			int count;
			byte data[] = new byte[BUFFER];
			// write the files to the disk
			FileOutputStream fos = new 
				FileOutputStream(outdir.getAbsolutePath()+File.pathSeparator+entry.getName());
			dest = new BufferedOutputStream(fos, BUFFER);
			while ((count = zip.read(data, 0, BUFFER)) != -1) {
			       dest.write(data, 0, count);
			    }
			dest.flush();
			dest.close();
		 }
		 zip.close();
		 return outdir;
	}
	
	public static File unzip(ZipFile zip, String path) 
	throws IOException {
		File outdir = new File(path);
		if (!outdir.isDirectory()){
			throw new RuntimeException("Path must be a directory");
		}
		
		Enumeration<?> ents = zip.entries();
		
		final int BUFFER = 2048;
		
		BufferedOutputStream dest = null;
		BufferedInputStream is = null;
		
		while(ents.hasMoreElements()){
			ZipEntry entry = (ZipEntry) ents.nextElement();
			is = new BufferedInputStream
			  (zip.getInputStream(entry));
			int count;
			byte data[] = new byte[BUFFER];
			FileOutputStream fos = new 
				FileOutputStream(outdir.getAbsolutePath()+
						File.separator+entry.getName());
			dest = new BufferedOutputStream(fos, BUFFER);
			while ((count = is.read(data, 0, BUFFER)) != -1) {
			   dest.write(data, 0, count);
			}
			dest.flush();
			dest.close();
			is.close();
		}
		return outdir;
	}
	
	public static File unzip(ZipFile zip, String path, Boolean folders) 
	throws IOException {
		if(!folders){
			return unzip(zip, path);
		} else {
			
			File outdir = new File(path);
			if (!outdir.isDirectory()){
				throw new RuntimeException("Path must be a directory");
			}
			
			Enumeration<?> ents = zip.entries();
			
			final int BUFFER = 2048;
			
			BufferedOutputStream dest = null;
			BufferedInputStream is = null;
			
			while(ents.hasMoreElements()){
				ZipEntry entry = (ZipEntry) ents.nextElement();
				if(entry.getName().indexOf("/") != -1){
					
				}
				String pathName = entry.getName().substring(0, entry.getName().lastIndexOf("/"));
				String entryName = entry.getName().substring(entry.getName().lastIndexOf("/")+1);
				if(pathName != null){
					new File(outdir.getAbsolutePath() + File.separator + pathName).mkdirs();
				}
				is = new BufferedInputStream
				  (zip.getInputStream(entry));
				int count;
				byte data[] = new byte[BUFFER];
				FileOutputStream fos = new 
					FileOutputStream(outdir.getAbsolutePath()+
							File.separator+pathName+File.separator+entryName);
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = is.read(data, 0, BUFFER)) != -1) {
				   dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
				is.close();
			}
			return outdir;
		}
	}
     
     /**
	 * Adds the supplied file to the supplied zip file -- for cases where a zip is 
	 * already on the filesystem (as is the case with the dissemination from DSpace). 
	 * @param zipfile
	 * @param filetoadd
	 * @throws IOException
	 */
	public static void addFileToExistingZip(File zipfile, File filetoadd) throws IOException{
		File temp = File.createTempFile("whileadding", "zip");
		ZipInputStream in = new ZipInputStream(new FileInputStream(zipfile));
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(temp));
		ZipEntry ent;
		byte[] buf = new byte[1024];
	    int bytesRead;
		//copy the contents of the original zip file to a temp file
		while ((ent=in.getNextEntry())!=null){
			out.putNextEntry(ent);
			while ((bytesRead = in.read(buf)) > 0) {
		        out.write(buf, 0, bytesRead);
			}
			out.closeEntry(); 
		}
		FileInputStream newin = new FileInputStream(filetoadd);
		out.putNextEntry(new ZipEntry(filetoadd.getName()));
		while ((bytesRead = newin.read(buf)) > 0) {
	        out.write(buf, 0, bytesRead);
		}
		out.closeEntry();
		out.flush();
		out.close();
		in.close();
		newin.close();
		zipfile.delete();
		temp.renameTo(zipfile);
	}

}
