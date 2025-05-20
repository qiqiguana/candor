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

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import jigl.image.Image;
import jigl.image.exceptions.ImageNotSupportedException;
import jigl.image.types.ColorImage;
import jigl.image.utils.ImageConverter;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncodeParam;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;
import com.sun.media.jai.codec.PNMEncodeParam;

/**
 * ImageOutputStreamJAI writes images to file (formats supported - PGM,PPM,TIFF,JPEG,BMP,PNG) using
 * Java Advanced Imaging API and com.sun.media.jai.codec package.
 */
public class ImageOutputStreamJAI {
	/** Data to dump */
	protected BufferedOutputStream data;

	/** Number of dimensions */
	// protected int ndims;

	/** Height of the image */
	protected int X;

	/** Width of the image */
	protected int Y;

	boolean isGray = false;

	// TODO: use enum
	/** Flag for one image file type */
	public static final int PGM = 0;
	/** Flag for one image file type */
	public static final int PPM = 1;
	/** Flag for one image file type */
	public static final int TIFF = 2;
	/** Flag for one image file type */
	public static final int JPEG = 3;
	/** Flag for one image file type */
	public static final int BMP = 4;
	/** Flag for one image file type */
	public static final int GIF = 5;// not supported yet
	/** Flag for one image file type */
	public static final int PNG = 6;

	/** Default ImageBufferedOutputStream, does nothing */
	public ImageOutputStreamJAI() {
	}

	/**
	 * Creates an ImageBufferedOutputStream from a file
	 * 
	 * @param fn filename
	 */
	public ImageOutputStreamJAI(String fn) throws IOException {
		if (System.getProperty("JAI_IMAGE_READER_USE_CODECS") == null) {

		}
		FileOutputStream os = new FileOutputStream(fn);
		data = new BufferedOutputStream(os);
	}

	/**
	 * Creates an ImageBufferedOutputStream from another ImageBufferedOutputStream
	 */
	public ImageOutputStreamJAI(ImageOutputStreamJAI stream) {
		data = stream.data;
		X = stream.X;
		Y = stream.Y;
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
	 * @param filename
	 */
	public boolean open(String fn) throws IOException {

		FileOutputStream f = new FileOutputStream(fn);
		data = new BufferedOutputStream(f);
		return true;
	}

	/* Writes a RealGrayImage to a file 
	public void writePRGM(RealGrayImage im) throws IOException{
		
	X = im.X(); 
	Y = im.Y();
		
	// convert to byte size
	RealGrayImage tmpim = (RealGrayImage)im.copy();
	
	// write PGM in raw format
	
		writeRawPRGMHeader(X, Y);			
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				data.write((byte)tmpim.get(x,y));
			}
		}
	}
	
	
	Writes a RealColorImage to a file
	@param im the RealColorImage
	public void writePRCM(RealColorImage im) throws IOException{
		
	X = im.X(); 
	Y = im.Y();
	float[] color = new float[3];
		
	// convert to byte size
	RealColorImage tmpim = (RealColorImage)im.copy();
		
	// write PPM in raw format

		writeRawPRCMHeader(X, Y);			
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				color = tmpim.get(x,y);
				data.write((byte)color[0]);
				data.write((byte)color[1]);
				data.write((byte)color[2]);
			}
		}
	
	}
	
	Writes a ComplexImage to a file
	@param im the ComplexImage
	public void writeComplex(ComplexImage im) throws IOException{
		
	X = im.X(); 
	Y = im.Y();

	// write the header
	double rmax = im.real().max();
	double imax = im.imag().max();
	if(rmax < imax)
	  rmax = imax;
	writeAsciiPPMHeader(X, Y, (int)rmax);			

	// write the data
	int cnt = 0;
	jigl.math.Complex value;
	for (int y = 0; y < Y; y++) {
	  for (int x = 0; x < X; x++) {
				writeString(data, im.getReal(x,y) + " ");
				writeString(data, im.getImag(x,y) + " ");
				if(cnt > 9) {
					writeString(data, "\n");
					cnt = 0;
				}
				cnt++;
	  }
	}
	writeString(data, "\n");
		
	}
	

	void writeRawPRGMHeader(int width, int height) throws IOException {
	writeString(data, "P7\n");
	writeString(data, width + " " + height + "\n");
	writeString(data, "255\n");
	}
	
	void writeRawPRCMHeader(int width, int height) throws IOException{
	writeString(data, "P8\n");
	writeString(data, width + " " + height + "\n");
	writeString(data, "255\n");
	}
	
	void writeAsciiPPMHeader(int width, int height, int maxval) throws IOException {
	writeString(data, "P3\n");
	writeString(data, width + " " + height + "\n");
	writeString(data, maxval + "\n");
	}
	
	static void writeString(BufferedOutputStream out, String str) throws IOException {
	
		out.write(str.getBytes());
	
	}*/

	/**
	 * This function writes out a jigl image in PGM format. Please note that even if the image is
	 * color, it will be converted to gray.
	 * 
	 * @param im jigl image to be written out
	 * @param raw true = binary raw format, false = ASCII format; note that writing in ASCII format
	 *            is slower than binary because of the way JAVA handles writing ASCII characters to
	 *            a file
	 */
	public void writePGM(jigl.image.Image im, boolean raw) throws ImageNotSupportedException {
		PNMEncodeParam param = new PNMEncodeParam();
		param.setRaw(raw);

		writeImage(im, PGM, param);
	}

	/**
	 * This function writes out a jigl image in PGM format as a binary raw file. Please note that
	 * even if the image is color, it will be converted to gray.
	 * 
	 * @param im jigl image to be written out
	 */
	public void writePGM(jigl.image.Image im) throws ImageNotSupportedException {
		writePGM(im, true);
	}

	/**
	 * This function writes out a jigl image in PPM format.
	 * 
	 * @param im jigl image to be written out
	 * @param raw true = binary raw format, false = ASCII format; note that writing in ASCII format
	 *            is slower than binary because of the way JAVA handles writing ASCII characters to
	 *            a file
	 */
	public void writePPM(jigl.image.Image im, boolean raw) throws ImageNotSupportedException {
		PNMEncodeParam param = new PNMEncodeParam();
		param.setRaw(raw);

		writeImage(im, PPM, param);
	}

	/**
	 * This function writes out a jigl image in PPM format as a binary raw file. Please note that
	 * even if the image is color, it will be converted to gray.
	 * 
	 * @param im jigl image to be written out
	 */
	public void writePPM(jigl.image.Image im) throws ImageNotSupportedException {
		writePPM(im, true);
	}

	/**
	 * This function writes out a jigl image in TIFF format.
	 * 
	 * @param im jigl image to be written out
	 */
	public void writeTIFF(jigl.image.Image im) throws ImageNotSupportedException {
		writeImage(im, TIFF, null);
	}

	/**
	 * This function writes out a jigl image in BMP format.
	 * 
	 * @param im jigl image to be written out
	 */
	public void writeBMP(jigl.image.Image im) throws ImageNotSupportedException {
		writeImage(im, BMP, null);
	}

	/**
	 * This function writes out a jigl image in JPEG format.
	 * 
	 * @param im jigl image to be written out
	 * @param quality Some guidelines: 0.75 high quality 0.5 medium quality 0.25 low quality
	 */
	public void writeJPEG(jigl.image.Image im, float quality) throws ImageNotSupportedException {
		JPEGEncodeParam param = new JPEGEncodeParam();
		param.setQuality(quality);

		writeImage(im, JPEG, param);
	}

	/**
	 * This function writes out a jigl image in JPEG format with .75 quality value
	 * 
	 * @param im jigl image to be written out
	 */
	public void writeJPEG(jigl.image.Image im) throws ImageNotSupportedException {
		writeImage(im, JPEG, null);
	}

	/**
	 * This function writes out a jigl image in PNG format.
	 * 
	 * @param im jigl image to be written out
	 */
	public void writePNG(jigl.image.Image im) throws ImageNotSupportedException {
		writeImage(im, PNG, null);
	}

	/**
	 * This function writes out an image (formats supported - PGM,PPM,TIFF,JPEG,BMP,PNG). Please
	 * note that GIF format is not currently supported. Also there is no explicit close operation in
	 * ImageOutputStreamJAI, the file is closed at the end of this function.
	 * 
	 * @param image jigl image to be written out
	 * @param type either PGM,PPM,TIFF,JPEG,BMP,PNG
	 * @param param EncodeParam type, may be null
	 */
	public void writeImage(Image image, int type, ImageEncodeParam param)
			throws ImageNotSupportedException {

		try {
			ColorImage im = ImageConverter.toColor(image);

			BufferedImage bi = null;
			if (type == PGM)
				bi = new BufferedImage(im.X(), im.Y(), BufferedImage.TYPE_BYTE_GRAY);
			else
				bi = new BufferedImage(im.X(), im.Y(), BufferedImage.TYPE_3BYTE_BGR);

			double min = im.minComponent();
			double max = im.maxComponent();

			// keep byte images in original range
			if (min >= 0 && max <= 255) {
				min = 0.0;
				max = 255.0;
			}
			double range = max - min;

			// convert jigl image to java image
			// int index = 0;
			int red = 0;
			int green = 0;
			int blue = 0;
			// int result = 0;
			Integer[] color = new Integer[3];
			for (int y = 0; y < im.Y(); y++) {
				for (int x = 0; x < im.X(); x++) {

					// map image values
					color = im.get(x, y);
					red = (int) ((255.0 / range) * ((double) color[0] - min));
					green = (int) ((255.0 / range) * ((double) color[1] - min));
					blue = (int) ((255.0 / range) * ((double) color[2] - min));

					// take lower 8 bits
					red = 0x00FF & red;
					green = 0x00FF & green;
					blue = 0x00FF & blue;

					// put this pixel in the java image

					bi.setRGB(x, y, (int) ((0x00 << 24) | (red << 16) | (green << 8) | blue));

				}
			}

			ImageEncoder enc = null;
			switch (type) {
			case JPEG:
				enc = ImageCodec.createImageEncoder("jpeg", data, param);
				break;
			case BMP:
				enc = ImageCodec.createImageEncoder("bmp", data, param);
				break;
			case TIFF:
				enc = ImageCodec.createImageEncoder("tiff", data, param);
				break;
			case PGM:
				enc = ImageCodec.createImageEncoder("PNM", data, param);
				break;
			case PPM:
				enc = ImageCodec.createImageEncoder("PNM", data, param);
				break;
			case PNG:
				enc = ImageCodec.createImageEncoder("PNG", data, param);
				break;
			case GIF:
			default:
				throw new ImageNotSupportedException();

			}

			enc.encode(bi);
			data.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;

		}

		System.out.println("Done");

	}
}
