/* This file is part of the JIGL Java Image and Graphics Library.
 * 
 * Copyright 1999 Brigham Young University.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * A copy of the GNU Lesser General Public License is contained in
 * the 'licenses' directory accompanying this distribution.
 */
package jigl.image.io;

import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import jigl.image.exceptions.ImageNotSupportedException;
import jigl.image.types.ColorImage;
import jigl.image.types.GrayImage;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;

/**
 * ImageInputStreamJAI reads in the following file types: PGM,PPM,PNG,JPEG,TIFF,GIF using the Java
 * Advanced Imaging API and com.sun.media.jai.codec package.
 */
public class ImageInputStreamJAI {

	/** Width of the image */
	protected int X;
	/** Height of the image */
	protected int Y;
	/** Image that will be loaded */
	protected PlanarImage img = null;
	/** Flag of whether the loaded image is gray image. */
	protected boolean grayImg = false;

	/** Image type */
	protected int type = 0;
	/** Stores the image content. */
	private BufferedInputStream data;
	/** File name of loaded image file */
	private String name;

	public ImageInputStreamJAI() {
	}

	/**
	 * Opens a ImageInputSteamJAI from a filename.
	 * 
	 * @param fn the filename to open
	 */
	public ImageInputStreamJAI(String fn) throws IOException {
		data = new BufferedInputStream(new FileInputStream(fn));
		type = readMagic();
		name = fn;

		if (type < 2)
			grayImg = true;
		img = readImage(fn);
		X = img.getWidth();
		Y = img.getHeight();
		if (fn.endsWith(".pgm"))
			grayImg = true;

	}

	/** Utility routine to read a byte from a InputStream object. */
	private int readByte(InputStream data) throws IOException {
		int b = data.read();
		return b;
	}

	/** Gets the magic number and returns the type */
	public int readMagic() throws IOException {

		int t;
		char c[] = new char[2];

		// get file type magic (first two bytes)

		c[0] = (char) readByte(data);
		c[1] = (char) readByte(data);

		String str = new String(c);

		// determine type

		if (str.equals("P2")) {
			t = 0;
		} else if (str.equals("P5")) {
			t = 1;
		} else if (str.equals("P7")) {
			t = 2;
		} else if (str.equals("P8")) {
			t = 3;
		} else
			t = 999;
		data.close();
		return t;
	}

	/** Reads data from a InputStream object and returns a JIGL image.. */
	public jigl.image.Image read() throws InterruptedException, ImageNotSupportedException,
			IllegalPBMFormatException, IOException {
		System.out.println("Detected type= " + type);
		if (type == 0 || type == 1 || type == 999) {
			ColorImage ci = new ColorImage(X, Y);
			GrayImage gi = new GrayImage(X, Y);
			java.awt.image.Raster r = img.getData();
			int[] ar = new int[3];
			for (int x = 0; x < X; x++)
				for (int y = 0; y < Y; y++) {
					if (grayImg)
						gi.set(x, y, r.getPixel(x, y, ar)[0]);
					else {
						int[] val = r.getPixel(x, y, ar);
						Integer[] val2 = new Integer[val.length];
						for(int i = 0; i < val.length; i++) {
							val2[i] = val[i];
						}
						ci.set(x, y, val2);
					}

				}
			if (grayImg)
				return gi;
			else
				return ci;
		}
		data.close();
		ImageInputStream is = new ImageInputStream(name);
		jigl.image.Image img = is.read();
		is.close();
		return img;
	}

	/**
	 * Opens a ImageInputSteamJAI from a filename
	 * 
	 * @param the complete URL
	 */
	//public ImageInputSteamJAI(String url, int i) throws InterruptedException, IOException, ImageNotSupportedException, MalformedURLException {
	//}

	/** Returns the width of the image */
	public int X() {
		return X;
	}

	/** Returns the height of the image */
	public int Y() {
		return Y;
	}

	/*
	 * @(#)JAIImageReader.java	4.2 99/03/05
	 *
	 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
	 *
	 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
	 * modify and redistribute this software in source and binary code form,
	 * provided that i) this copyright notice and license appear on all copies of
	 * the software; and ii) Licensee does not utilize the software in a manner
	 * which is disparaging to Sun.
	 *
	 * This software is provided "AS IS," without a warranty of any kind. ALL
	 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
	 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
	 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
	 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
	 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
	 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
	 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
	 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
	 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
	 * POSSIBILITY OF SUCH DAMAGES.
	 *
	 * This software is not designed or intended for use in on-line control of
	 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
	 * the design, construction, operation or maintenance of any nuclear
	 * facility. Licensee represents and warrants that it will not use or
	 * redistribute the Software for such purposes.
	 */

	/**
	 * Utility routine to read directly from an image file and returns a PlanarImage.
	 */
	protected PlanarImage readImage(String filename) throws IOException {
		// Use the JAI API unless JAI_IMAGE_READER_USE_CODECS is set
		if (System.getProperty("JAI_IMAGE_READER_USE_CODECS") == null) {
			return JAI.create("fileload", filename);
		} else {
			//			try {
			// Use the ImageCodec APIs
			SeekableStream stream = new FileSeekableStream(filename);
			String[] names = ImageCodec.getDecoderNames(stream);
			ImageDecoder dec = ImageCodec.createImageDecoder(names[0], stream, null);
			RenderedImage im = dec.decodeAsRenderedImage();
			return PlanarImage.wrapRenderedImage(im);
			//			} catch (Exception e) {
			//				e.printStackTrace();
			//				return null;
			//			}
		}
	}
}
