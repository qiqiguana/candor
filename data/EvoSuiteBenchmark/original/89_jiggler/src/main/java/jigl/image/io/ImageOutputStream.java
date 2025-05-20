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

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jigl.image.Image;
import jigl.image.exceptions.ColorModelNotSupportedException;
import jigl.image.exceptions.ColorModelUnknownException;
import jigl.image.exceptions.UnknownImageTypeException;
import jigl.image.types.ColorImage;
import jigl.image.types.ComplexImage;
import jigl.image.types.GrayImage;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/**
 * ImageOutputStream writes images to file. Images are saved in the following formats. BinayImage is
 * not supported but can be treated as GrayImage. <br>
 * <br>
 * 
 * <pre>
 * GrayImage -> PGM file
 * 	RealGrayImage -> PRGM file
 * 	ColorImage -> PPM file
 * 	RealColorImage -> PRCM file
 * 	ComplexImage -> PPM file
 * </pre>
 */
public class ImageOutputStream {

	/** Data to dump */
	protected BufferedOutputStream data;

	/** Number of dimensions */
	protected int ndims;
	/** Height of the image */
	protected int X;
	/** Width of the image */
	protected int Y;

	/** Default ImageOutputStream, does nothing */
	public ImageOutputStream() {
	}

	/**
	 * Creates an ImageOutputStream from a filename
	 * 
	 * @param fn filename
	 */
	public ImageOutputStream(String fn) throws IOException {
		FileOutputStream f = new FileOutputStream(fn);
		data = new BufferedOutputStream(f);

	}

	/** Creates an ImageOutputStream from another ImageOutputStream */
	public ImageOutputStream(ImageOutputStream stream) {
		data = stream.data;
		ndims = stream.ndims;
		X = stream.X;
		Y = stream.Y;
	}

	/** Returns the number of dimensions */
	public int ndims() {
		return ndims;
	}

	/** Returns the width */
	public int X() {
		return X;
	}

	/** Returns the height */
	public int Y() {
		return Y;
	}

	/**
	 * Opens a file
	 * 
	 * @param fn filename
	 */
	public boolean open(String fn) throws IOException {
		FileOutputStream f = new FileOutputStream(fn);
		data = new BufferedOutputStream(f);

		return true;
	}

	/**
	 * Closes the file
	 * 
	 * @param filename
	 */
	public void close() throws IOException {
		data.close();
	}

	/**
	 * Writes a JIGL image to a file
	 * 
	 * @param im JIGL image
	 */
	public void write(Image<?> im) throws UnknownImageTypeException,
			ColorModelNotSupportedException, ColorModelUnknownException, IOException {
		if (im instanceof GrayImage) {
			write((GrayImage) im);
		} else if (im instanceof ColorImage) {
			write((ColorImage) im);
		} else if (im instanceof RealGrayImage) {
			write((RealGrayImage) im);
		} else if (im instanceof RealColorImage) {
			write((RealColorImage) im);
		} else if (im instanceof ComplexImage) {
			write((ComplexImage) im);
		} else {
			throw new UnknownImageTypeException();
		}
	}

	/**
	 * Writes a GrayImage to a file
	 * 
	 * @param im the GrayImage
	 */
	public void write(GrayImage im) throws IOException {
		X = im.X();
		Y = im.Y();

		// convert to byte size
		GrayImage tmpim = im.copy();
		tmpim.byteSize();

		// write PGM in raw format
		writeRawPGMHeader(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				data.write(tmpim.get(x, y).byteValue());
			}
		}
	}

	/**
	 * Writes a GrayImage to a file, byteSizing it first
	 * 
	 * @param im the GrayImage
	 */
	public void writeByteSized(GrayImage im) throws IOException {
		X = im.X();
		Y = im.Y();

		// convert to byte size
		GrayImage tmpim = im.copy();
		tmpim.byteSize();

		// write PGM in raw format

		writeRawPGMHeader(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				data.write(tmpim.get(x, y).byteValue());
			}
		}
	}

	/** Writes a RealGrayImage to a file */
	public void write(RealGrayImage im) throws IOException {
		X = im.X();
		Y = im.Y();

		// convert to byte size
		RealGrayImage tmpim = im.copy();
		tmpim.byteSize();

		// write PGM in raw format

		writeRawPRGMHeader(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				data.write(tmpim.get(x, y).byteValue());
			}
		}
	}

	/**
	 * Writes a ColorImage to a file
	 * 
	 * @param im the ColorImage
	 */
	public void write(ColorImage im) throws IOException {
		X = im.X();
		Y = im.Y();
		Integer[] color = new Integer[3];

		// convert to byte size
		ColorImage tmpim = im.copy();
		tmpim.byteSize();

		// write PPM in raw format

		writeRawPPMHeader(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				color = tmpim.get(x, y);
				data.write(color[0].byteValue());
				data.write(color[1].byteValue());
				data.write(color[2].byteValue());
			}
		}
	}

	/**
	 * Writes a RealColorImage to a file
	 * 
	 * @param im the RealColorImage
	 */
	public void write(RealColorImage im) throws IOException {
		X = im.X();
		Y = im.Y();
		Float[] color = new Float[3];

		// convert to byte size
		RealColorImage tmpim = im.copy();
		tmpim.byteSize();

		// write PPM in raw format
		writeRawPRCMHeader(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				color = tmpim.get(x, y);
				data.write(color[0].byteValue());
				data.write(color[1].byteValue());
				data.write(color[2].byteValue());
			}
		}
	}

	/**
	 * Writes a ComplexImage to a file
	 * 
	 * @param im the ComplexImage
	 */
	public void write(ComplexImage im) throws IOException {
		X = im.X();
		Y = im.Y();

		// write the header
		double rmax = im.real().max();
		double imax = im.imaginary().max();
		if (rmax < imax)
			rmax = imax;
		writeAsciiPPMHeader(X, Y, (int) rmax);

		// write the data
		int cnt = 0;
		//    jigl.math.Complex value;
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				writeString(data, im.getReal(x, y) + " ");
				writeString(data, im.getImaginary(x, y) + " ");
				if (cnt > 9) {
					writeString(data, "\n");
					cnt = 0;
				}
				cnt++;
			}
		}
		writeString(data, "\n");

	}

	/** Utility routine to write PGM_RAW file header. */
	void writeRawPGMHeader(int width, int height) throws IOException {
		writeString(data, "P5\n");
		writeString(data, width + " " + height + "\n");
		writeString(data, "255\n");
	}

	/** Utility routine to write PRGM_RAW file header. */
	void writeRawPRGMHeader(int width, int height) throws IOException {
		writeString(data, "P7\n");
		writeString(data, width + " " + height + "\n");
		writeString(data, "255\n");
	}

	/** Utility routine to write PGM_ASCII file header. */
	void writeAsciiPGMHeader(int width, int height, int maxval) throws IOException {
		writeString(data, "P2\n");
		writeString(data, width + " " + height + "\n");
		writeString(data, maxval + "\n");
	}

	/** Utility routine to write PPM_RAW file header. */
	void writeRawPPMHeader(int width, int height) throws IOException {
		writeString(data, "P6\n");
		writeString(data, width + " " + height + "\n");
		writeString(data, "255\n");
	}

	/** Utility routine to write PRCM_RAW file header. */
	void writeRawPRCMHeader(int width, int height) throws IOException {
		writeString(data, "P8\n");
		writeString(data, width + " " + height + "\n");
		writeString(data, "255\n");
	}

	/** Utility routine to write PPM_ASCII file header. */
	void writeAsciiPPMHeader(int width, int height, int maxval) throws IOException {
		writeString(data, "P3\n");
		writeString(data, width + " " + height + "\n");
		writeString(data, maxval + "\n");
	}

	/** Utility routine to write a String object to a OutputStream object. */
	static void writeString(OutputStream out, String str) throws IOException {
		out.write(str.getBytes());
	}

	/** Writes a RealGrayImage to a file, byteSizing first */
	public void writeByteSized(RealGrayImage im) throws IOException {
		X = im.X();
		Y = im.Y();

		// convert to byte size
		RealGrayImage tmpim = im.copy();
		tmpim.byteSize();

		// write PGM in raw format
		writeRawPRGMHeader(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				data.write(tmpim.get(x, y).byteValue());
			}
		}
	}

	/**
	 * Writes a ColorImage to a file byteSizing first
	 * 
	 * @param im the ColorImage
	 */
	public void writeByteSized(ColorImage im) throws IOException {
		X = im.X();
		Y = im.Y();
		Integer[] color = new Integer[3];

		// convert to byte size
		ColorImage tmpim = im.copy();
		tmpim.byteSize();

		// write PPM in raw format

		writeRawPPMHeader(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				color = tmpim.get(x, y);
				data.write(color[0].byteValue());
				data.write(color[1].byteValue());
				data.write(color[2].byteValue());
			}
		}
	}

	/**
	 * Writes a RealColorImage to a file, byteSizing first
	 * 
	 * @param im the RealColorImage
	 */
	public void writeByteSized(RealColorImage im) throws IOException {
		X = im.X();
		Y = im.Y();
		Float[] color = new Float[3];

		// convert to byte size
		RealColorImage tmpim = im.copy();
		tmpim.byteSize();

		// write PPM in raw format
		writeRawPRCMHeader(X, Y);
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				color = tmpim.get(x, y);
				data.write(color[0].byteValue());
				data.write(color[1].byteValue());
				data.write(color[2].byteValue());
			}
		}
	}

}
