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

import java.awt.Component;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import jigl.image.exceptions.ImageNotSupportedException;
import jigl.image.types.ColorImage;
import jigl.image.types.GrayImage;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/**
 * <p>
 * ImageInputStream will read a image file(PBM, PGM, PPM, PRGM, PRCM, GIF, JPG or JPEG) and store
 * the content in a InputSream object.
 * </p>
 * <p>
 * 
 * <pre>
 * .pgm and .pbm -> GrayImage
 * 	.prgm -> RealGrayImage
 * 	.ppm -> ColorImage
 * 	.prcm -> RealColorImage
 * 	.gif, .jpg and .jpeg -> ColorImage
 * </pre>
 * 
 * </p>
 */
public class ImageInputStream extends Component {
	private static final long serialVersionUID = 1L;

	/** InputStream for the Data */
	protected BufferedInputStream data;
	/** Number of dimensions the image has (Protected) */
	protected int ndims;
	/** Width of the image (Protected) */
	protected int X;
	/** Height of the image (Protected) */
	protected int Y;
	/** Image that will be loaded (Protected) */
	protected java.awt.Image img = null;

	/**
	 * Type of the file:
	 * <P>
	 * <DT>UNKNOWN = 0</DT>
	 * <DT>PGM_ASCII = 1</DT>
	 * <DT>PGM_ASCII = 2</DT>
	 * <DT>PPM_ASCII = 3</DT>
	 * <DT>PBM_RAW = 4</DT>
	 * <DT>PGM_RAW = 5</DT>
	 * <DT>PPM_RAW = 6</DT>
	 * <DT>PRGM_RAW = 7</DT>
	 * <DT>PRCM_RAW = 8</DT>
	 * <DT>TIFF = 9 -- not yet implemented</DT>
	 * <DT>JIGL_GRAY = 10</DT>
	 * <DT>JIGL_COLOR = 11</DT>
	 * <DT>GIFF = N/A uses Java read-in method and then converted to a JIGL image</DT>
	 * <DT>JPEG = N/A uses Java read-in method and then converted to a JIGL image</DT>
	 * <DT>JPG = N/A uses Java read-in method and then converted to a JIGL image</DT>
	 */
	private ImageFileType type;

	private enum ImageFileType {
		UNKNOWN, PBM_ASCII, PGM_ASCII, PPM_ASCII, PBM_RAW, PGM_RAW, PPM_RAW, PRGM_RAW,
		/** not yet implemented */
		PRCM_RAW,
//		/** not yet implemented */
//		TIFF, JIGL_GRAY, JIGL_COLOR
	};

	/** Maximum value (integer) of the Image (Protected) */
	protected int maxval;
	/** Maximum value (float) of the Image (Protected) */
	protected float maxvalf;

	/**
	 * Constructs a ImageInputStream object from a image file. Please note, GIF, JPG, and JPEG files
	 * are dependent on the filename extension (not on the header).
	 * 
	 * @param fileName the filename to open
	 */
	public ImageInputStream(String fileName) throws InterruptedException, FileNotFoundException,
			ImageNotSupportedException, IOException {

		if (fileName.endsWith(".gif") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {

			Toolkit toolkit = Toolkit.getDefaultToolkit();
			img = toolkit.getImage(fileName);
			MediaTracker m_tracker = new MediaTracker(this);
			m_tracker.addImage(img, 0);
			m_tracker.waitForAll();
		} else {
			ndims = 2;
			data = new BufferedInputStream(new FileInputStream(fileName));
			type = readMagic();
			readHeader();
		}
	}

	/**
	 * Constructs a ImageInputStream object from an online file.
	 * 
	 * @param url the complete URL
	 */
	public ImageInputStream(String url, int i) throws InterruptedException, IOException,
			ImageNotSupportedException, MalformedURLException {

		URL ur = new URL(url);
		if (url.endsWith(".gif") || url.endsWith(".jpg") || url.endsWith(".jpeg")) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			img = toolkit.getImage(ur);
			MediaTracker m_tracker = new MediaTracker(this);
			m_tracker.addImage(img, 0);
			m_tracker.waitForAll();
		} else {
			ndims = 2;
			data = new BufferedInputStream(ur.openStream());
			type = readMagic();
			readHeader();
		}
	}

	/**
	 * Constructs a ImageInputStream object from another ImageInputStream object (shallow copy).
	 */
	public ImageInputStream(ImageInputStream stream) {
		data = stream.data;
		ndims = stream.ndims;
		X = stream.X;
		Y = stream.Y;
		type = stream.type;
	}

	/** Returns the number of Dimensions that the image has */
	public int ndims() {
		return ndims;
	}

	/** Returns the width of the image */
	public int X() {
		return X;
	}

	/** Returns the height of the image */
	public int Y() {
		return Y;
	}

	/**
	 * Returns the type of image
	 * <DL>
	 * <DL>
	 * <DT>UNKNOWN = 0</DT>
	 * <DT>PGM_ASCII = 1</DT>
	 * <DT>PGM_ASCII = 2</DT>
	 * <DT>PPM_ASCII = 3</DT>
	 * <DT>PBM_RAW = 4</DT>
	 * <DT>PGM_RAW = 5</DT>
	 * <DT>PPM_RAW = 6</DT>
	 * <DT>GIF = 7 -- not yet implemented</DT>
	 * <DT>JPEG = 8 -- not yet implemented</DT>
	 * <DT>TIFF = 9 -- not yet implemented</DT>
	 * <DT>JIGL_GRAY = 10</DT>
	 * <DT>JIGL_COLOR = 11</DT>
	 * </DL>
	 * </DL>
	 */
	public ImageFileType type() {
		return type;
	}

	/** Closes the InputStream */
	public void close() throws IOException {
		if (data != null)
			data.close();
	}

	/** Gets the magic number and returns the type */
	public ImageFileType readMagic() throws ImageNotSupportedException, IOException {

		ImageFileType t;
		char c[] = new char[2];

		// get file type magic (first two bytes)

		c[0] = (char) readByte(data);
		c[1] = (char) readByte(data);

		String str = new String(c);

		// determine type
		if (str.equals("P1")) {
			t = ImageFileType.PBM_ASCII;
		} else if (str.equals("P2")) {
			t = ImageFileType.PGM_ASCII;
		} else if (str.equals("P3")) {
			t = ImageFileType.PPM_ASCII;
		} else if (str.equals("P4")) {
			t = ImageFileType.PBM_RAW;
		} else if (str.equals("P5")) {
			t = ImageFileType.PGM_RAW;
		} else if (str.equals("P6")) {
			t = ImageFileType.PPM_RAW;
		} else if (str.equals("P7")) {
			t = ImageFileType.PRGM_RAW;
		} else if (str.equals("P8")) {
			t = ImageFileType.PRCM_RAW;
		} else {
			t = ImageFileType.UNKNOWN;
			throw new ImageNotSupportedException();
		}

		return t;
	}

	/** Reads the file header: determines type, size and range of values */
	public void readHeader() throws ImageNotSupportedException, IOException {

		switch (type) {
		case PBM_ASCII:
		case PGM_ASCII:
		case PPM_ASCII:
		case PBM_RAW:
		case PGM_RAW:
		case PPM_RAW:
		case PRGM_RAW:
		case PRCM_RAW:

			// get image dimensions
			X = readInt(data);
			Y = readInt(data);
			ndims = 2;

			// get data range (maximum value)
			if (type != ImageFileType.PBM_ASCII && type != ImageFileType.PBM_RAW
					&& type != ImageFileType.PRGM_RAW && type != ImageFileType.PRCM_RAW) {
				maxval = readInt(data);
			} else if (type != ImageFileType.PBM_ASCII && type != ImageFileType.PBM_RAW) {
				maxvalf = readFloat(data);
			} else {
				maxval = 1;
			}
			break;
		default:
			throw new ImageNotSupportedException();
		}

	}

	/** Reads in the Image
	 * TODO: replace the switch statements with polymorphism
	 */
	public jigl.image.Image read() throws ImageNotSupportedException, IllegalPBMFormatException,
			IOException {

		if (img == null) {
			// the different possible image types to return
			GrayImage g_im = null;
			RealGrayImage rg_im = null;
			ColorImage c_im = null;
			RealColorImage rc_im = null;
			// ComplexImage cx_im = null;

			switch (type) {
			case PBM_ASCII:
			case PBM_RAW:
			case PGM_RAW:
			case PGM_ASCII:
				g_im = new GrayImage(X, Y);
				break;
			case PPM_ASCII:
			case PPM_RAW:
				c_im = new ColorImage(X, Y);
				break;
			case PRGM_RAW:
				rg_im = new RealGrayImage(X, Y);
				break;
			case PRCM_RAW:
				rc_im = new RealColorImage(X, Y);
				break;
			default:
				throw new ImageNotSupportedException();

			}

			int /* col, */r, g, b;
			float rf, gf, bf;
			char c;
			int value;
			Integer[] color = new Integer[3];
			Float[] colorf = new Float[3];

			// read image data

			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					switch (type) {
					case PBM_ASCII:
						c = readChar(data);
						if (c == '1') {
							value = 0;
						} else if (c == '0') {
							value = 0xff;
						} else {
							throw new IllegalPBMFormatException();

						}
						g_im.set(x, y, value);
						break;
					case PGM_ASCII:
						g = readInt(data);
						g_im.set(x, y, g);
						break;
					case PPM_ASCII:
						r = readInt(data);
						g = readInt(data);
						b = readInt(data);
						color[0] = r;
						color[1] = g;
						color[2] = b;
						c_im.set(x, y, color);
						break;
					case PBM_RAW:
						if (readBit(data)) {
							value = 0;
						} else {
							value = 0xff;
						}
						g_im.set(x, y, value);
						break;
					case PGM_RAW:
						g = readByte(data);
						if (maxval != 255) {
							g = fixDepth(g);
						}
						g_im.set(x, y, g);
						break;
					case PRGM_RAW:
						gf = readBytef(data);
						if (maxvalf != 255) {
							gf = fixDepth(gf);
						}
						rg_im.set(x, y, gf);
						break;
					case PPM_RAW:
						r = readByte(data);
						g = readByte(data);
						b = readByte(data);
						if (maxval != 255) {
							r = fixDepth(r);
							g = fixDepth(g);
							b = fixDepth(b);
						}
						color[0] = r;
						color[1] = g;
						color[2] = b;
						c_im.set(x, y, color);
						break;
					case PRCM_RAW:
						rf = readBytef(data);
						gf = readBytef(data);
						bf = readBytef(data);
						if (maxvalf != 255) {
							rf = fixDepth(rf);
							gf = fixDepth(gf);
							bf = fixDepth(bf);
						}
						colorf[0] = rf;
						colorf[1] = gf;
						colorf[2] = bf;
						rc_im.set(x, y, colorf);
						break;
					}
				}
			}

			// return the right image type
			switch (type) {
			case PBM_ASCII:
			case PBM_RAW:
			case PGM_RAW:
			case PGM_ASCII:
				return g_im;
			case PPM_ASCII:
			case PPM_RAW:
				return c_im;
			case PRGM_RAW:
				return rg_im;
			case PRCM_RAW:
				return rc_im;
			default:
				throw new ImageNotSupportedException();

			}
		} else {
			ColorImage image = new ColorImage(img);
			return image;
		}

	}

	// functions imported from JPM library
	//
	/** Utility routine to read a byte. */
	private int readByte(InputStream is) throws IOException {
		int b = is.read();
		return b;
	}

	private float readBytef(InputStream is) throws IOException {
		float b = is.read();
		return b;
	}

	/** Utility routine to read an ASCII integer, ignoring comments. */
	private int readInt(InputStream is) throws IOException {
		char c;
		int i;

		c = readNonwhiteChar(is);

		if (!((c >= '0' && c <= '9') || c == '-'))
			throw new IOException("junk in file where integer should be");

		i = 0;

		if (c == '-') {
			c = readChar(is);
			do {
				i = i * 10 + c - '0';
				c = readChar(is);
			} while (c >= '0' && c <= '9');
			i = -i;
		} else {
			do {
				i = i * 10 + c - '0';
				c = readChar(is);
			} while (c >= '0' && c <= '9');
		}

		return i;
	}

	/** Utility function */
	private float readFloat(InputStream is) throws IOException {
		char c;
		float i, cons;
		boolean flag = true;

		cons = (float) 0.1;
		c = readNonwhiteChar(is);

		if (!((c >= '0' && c <= '9') || c == '-'))
			throw new IOException("junk in file where integer should be");

		i = 0;

		if (c == '-') {
			c = readChar(is);
			do {
				i = i * 10 + c - '0';
				c = readChar(is);
			} while (c >= '0' && c <= '9');
			i = -i;
			flag = false;
		} else {
			do {
				i = i * 10 + c - '0';
				c = readChar(is);
			} while (c >= '0' && c <= '9');
		}

		c = readChar(is);
		if (c == ('.')) {
			readChar(is);
			do {
				if (flag == true)
					i = i + cons * (c - '0');
				else
					i = i + -(cons * (c - '0'));
				c = readChar(is);
				cons = cons * (float) 0.1;
			} while (c >= '0' && c <= '9');
		}

		return i;
	}

	/** Utility routine to read a character, ignoring comments. */
	private char readChar(InputStream is) throws IOException {
		char c;

		c = (char) readByte(is);
		if (c == '#') {
			do {
				c = (char) readByte(is);
			} while (c != '\n' && c != '\r');
		}

		return c;
	}

	/** Utility routine to read the first non-whitespace character. */
	private char readNonwhiteChar(InputStream is) throws IOException {
		char c;

		do {
			c = readChar(is);
		} while (c == ' ' || c == '\t' || c == '\n' || c == '\r');

		return c;
	}

	/** Utility routine to rescale a pixel value from a non-eight-bit maxval. */
	private int fixDepth(int p) {
		return (p * 255 + maxval / 2) / maxval;
	}

	private float fixDepth(float p) {
		return (p * 255 + maxvalf / 2) / maxval;
	}

	/** Current bit position in the current byte. */
	private int bitshift = -1;
	
	/** Current bytes value. */
	private int bits;

	/** Utility routine to read a bit, packed eight to a byte, big-endian. */
	private boolean readBit(InputStream in) throws IOException {
		if (bitshift == -1) {
			bits = readByte(in);
			bitshift = 7;
		}
		boolean bit = (((bits >> bitshift) & 1) != 0);
		--bitshift;
		return bit;
	}

}
