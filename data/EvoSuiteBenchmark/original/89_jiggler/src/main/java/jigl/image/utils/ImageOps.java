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
package jigl.image.utils;

import java.io.IOException;

import jigl.image.Image;
import jigl.image.exceptions.ColorModelNotSupportedException;
import jigl.image.exceptions.ColorModelUnknownException;
import jigl.image.exceptions.ImageNotSupportedException;
import jigl.image.io.IllegalPBMFormatException;
import jigl.image.io.ImageInputStream;
import jigl.image.io.ImageOutputStream;
import jigl.image.types.ColorImage;
import jigl.image.types.ComplexImage;
import jigl.image.types.GrayImage;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/**
 * ImageOps is a class that allows basic operations on images from the Command Line. Below is a
 * table of the currently supported operations and their syntax <BR>
 * &nbsp; <BR>
 * &nbsp;
 * <TABLE BORDER WIDTH="100%" BGCOLOR="#CCCCCC" >
 * <TR>
 * <TD COLSPAN="3" BGCOLOR="#F0F000">
 * <CENTER>Supported Operations</CENTER></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="25%" BGCOLOR="#59ACFF">
 * <CENTER>Operation</CENTER></TD>
 * 
 * <TD BGCOLOR="#59ACFF">
 * <CENTER>Syntax</CENTER></TD>
 * 
 * <TD BGCOLOR="#59ACFF">
 * <CENTER>Comments</CENTER></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Add&nbsp; two images</FONT></TD>
 * 
 * <TD WIDTH="40%"><FONT SIZE=-1>&nbsp;-add &lt;image1> &lt;image2> &lt;output file></FONT></TD>
 * 
 * <TD ROWSPAN="4" WIDTH="40%"><FONT SIZE=-1>&nbsp;* Note: Some image types are incompatible for
 * these operations (in any order).&nbsp;</FONT>&nbsp;
 * <UL>
 * <LI>
 * <FONT SIZE=-1>GrayImage&amp;ColorImage (or RealColorImage)</FONT></LI>
 * 
 * <LI>
 * <FONT SIZE=-1>ComplexImage&amp;ColorImage (or RealColorImage)</FONT></LI>
 * 
 * <LI>
 * <FONT SIZE=-1>RealGrayImage&amp;ColorImage (or RealColorImage)</FONT></LI>
 * </UL>
 * </TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="20%"><FONT SIZE=-1>Subtract two images</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-subtract &lt;image1> &lt;image2> &lt;output file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Multiply two images</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-multiply &lt;image1> &lt;image2> &lt;output file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Divide two images</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-divide &lt;image1> &lt;image2> &lt;output file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Add a constant to all the pixels in an image</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-addconst &lt;image1> &lt;const><sup>+</sup> &lt;output file></FONT></TD>
 * 
 * <TD ROWSPAN="4" WIDTH="40%"><FONT SIZE=-1>* Note: It is <B>not </B>possible to use these
 * operations using float or double values on eithier a GrayImage&nbsp; or a
 * ColorImage.&nbsp;</FONT>&nbsp;
 * 
 * <P>
 * <FONT SIZE=-1>* Note: ColorImages and RealColorImages require 3 constants, one for each
 * plane.&nbsp; Complex Images require two, one for the real plane and one for the imaginary
 * plane.&nbsp;</FONT>
 * </TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Subtract a constant from all the pixels in an image</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-subtractconst &lt;image1> &lt;const><sup>+</sup> &lt;output file></FONT>
 * </TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Multiply a constant by all the pixels in an image</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-multiplyconst &lt;image1> &lt;const><sup>+</sup> &lt;output file></FONT>
 * </TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Divide all the pixels in an image by a constant</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-divideconst &lt;image1> &lt;const><sup>+</sup> &lt;output file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Clip an arbitary region of an image</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-clip &lt;image1> &lt;const1> &lt;const2> &lt;output file></FONT></TD>
 * 
 * <TD WIDTH="40%"><FONT SIZE=-1>&nbsp;const1 is the low value&nbsp;</FONT>&nbsp; <BR>
 * <FONT SIZE=-1>&nbsp;const2 is the high value</FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Clear an image to a value</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-clear &lt;image1> &lt;const> &lt;output file></FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;the constant is the value to set it to</FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>ByteSize operation</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-bytesize &lt;image1> &lt;output file></FONT></TD>
 * 
 * <TD></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Diff operation</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-diff &lt;image1> &lt;image2> &lt;output file></FONT></TD>
 * 
 * <TD></TD>
 * </TR>
 * </TABLE>
 *<FONT SIZE=-1>* ImageOps also support a "-debug" option.&nbsp; This will display some helpful
 * imformation in debugging problems</FONT> <FONT SIZE=-1>you might encounter.</FONT>
 * 
 * <P>
 * <U><FONT SIZE=-1>Examples of a Command-lines:</FONT></U>
 * 
 * <P>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java ImageOpps -add
 * C:\pictures\test1.ppm C:\pictures\test2.ppm C:\Output\out_test.ppm</FONT></FONT>
 * 
 * <P>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java ImageOpps -mulitiplyconst
 * C:\pictures\test1.ppm 5 C:\Output\out_test.ppm -debug</FONT></FONT>
 * 
 * <P>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java ImageOpps -clip
 * C:\pictures\test1.ppm 40 150 C:\Output\out_test.ppm</FONT></FONT>
 */
public class ImageOps {
	/** Debug flag. */
	private static boolean debug;

	/** Command line parameters */
	private static String[] param;

	/** For command line operations. */
	public static void main(String[] argv) {
		int last = 0;
		debug = false;
		param = argv;
		String op = argv[0];
		jigl.image.Image im3 = null;
		// int outfile=0;

		try {
			if (argv[argv.length - 1].equals("-debug")) {
				debug = true;
				last = argv.length - 2;
			} else
				last = argv.length - 1;

			if (op.equals("-add"))
				im3 = basic(0);
			else if (op.equals("-subtract"))
				im3 = basic(1);
			else if (op.equals("-multiply"))
				im3 = basic(2);
			else if (op.equals("-divide"))
				im3 = basic(3);
			else if (op.equals("-addconst"))
				im3 = basicConst(0);
			else if (op.equals("-subtractconst"))
				im3 = basicConst(1);
			else if (op.equals("-multiplyconst"))
				im3 = basicConst(2);
			else if (op.equals("-divideconst"))
				im3 = basicConst(3);
			else if (op.equals("-clear"))
				im3 = clear();
			else if (op.equals("-clip"))
				im3 = clip();
			else if (op.equals("-bytesize"))
				im3 = byteSize();
			else if (op.equals("-diff"))
				im3 = basic(4);
			else {
				throw new InvalidCommandLineException();
			}

			// create a new ImageOutputStream
			ImageOutputStream os = new ImageOutputStream(argv[last]);
			os.write(im3);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/** Utility method: open an image file. */
	private static jigl.image.Image imageOpen(int file) throws InterruptedException, IOException,
			IllegalPBMFormatException, ImageNotSupportedException {
		Image image = null;
		if (debug == true)
			System.out.println("Opening file: " + param[file]);
		ImageInputStream is = new ImageInputStream(param[file]);
		image = is.read();
		is.close();

		if (debug == true) {
			if (image instanceof GrayImage) {
				System.out.println("Gray image");
			} else if (image instanceof RealGrayImage) {
				System.out.println("RealGray image");
			} else if (image instanceof ColorImage) {
				System.out.println("Color image");
			} else if (image instanceof RealColorImage) {
				System.out.println("RealColor image");
			} else if (image instanceof ComplexImage) {
				System.out.println("Complex image");
			} else {
				System.out.println("unknown image type");
			}
		}
		return image;

	}

	/**
	 * Utility method: handling the addition, subtraction, multiplicationa and division of two
	 * images.
	 */
	private static Image basic(int which) throws IncompatibleImagesException,
			ImageNotSupportedException, ColorModelNotSupportedException, InterruptedException,
			ColorModelUnknownException, IllegalPBMFormatException, IOException {

		// jigl.image.Image image3=null;
		jigl.image.Image image1 = null;
		jigl.image.Image image2 = null;
		GrayImage g1 = null;
		GrayImage g2 = null;
		ComplexImage c1 = null;
		ComplexImage c2 = null;
		RealGrayImage rg1 = null;
		RealGrayImage rg2 = null;
		ColorImage co1 = null;
		ColorImage co2 = null;
		RealColorImage rc1 = null;
		RealColorImage rc2 = null;

		image1 = imageOpen(1);
		image2 = imageOpen(2);

		// Convert to the appropriate type
		if (image1 instanceof GrayImage)
			g1 = ImageConverter.toGray(image1);
		if (image2 instanceof GrayImage)
			g2 = ImageConverter.toGray(image2);
		if (image1 instanceof RealGrayImage)
			rg1 = ImageConverter.toRealGray(image1);
		if (image2 instanceof RealGrayImage)
			rg2 = ImageConverter.toRealGray(image2);
		if (image1 instanceof ColorImage)
			co1 = ImageConverter.toColor(image1);
		if (image2 instanceof ColorImage)
			co2 = ImageConverter.toColor(image2);
		if (image1 instanceof RealColorImage)
			rc1 = ImageConverter.toRealColor(image1);
		if (image2 instanceof RealColorImage)
			rc2 = ImageConverter.toRealColor(image2);
		if (image1 instanceof ComplexImage)
			c1 = ImageConverter.toComplex(image1);
		if (image2 instanceof ComplexImage)
			c2 = ImageConverter.toComplex(image2);

		if ((g1 != null) && (g2 != null)) {
			if (debug == true)
				System.out.println("Both images are GrayImages");
			if (which == 0)
				g1.add(g2);
			else if (which == 1)
				g1.subtract(g2);
			else if (which == 2)
				g1.multiply(g2);
			else if (which == 3)
				g1.divide(g2);
			else if (which == 4)
				g1.diff(g2);
			return g1;
		}

		else if ((g1 != null) && (c2 != null)) {
			if (debug == true)
				System.out.println("Warning: First image converted from GrayImage to ComplexImage");
			c1 = ImageConverter.toComplex(image1);
			if (which == 0)
				c1.add(c2);
			else if (which == 1)
				c1.subtract(c2);
			else if (which == 2)
				c1.multiply(c2);
			else if (which == 3)
				c1.divide(c2);
			else if (which == 4)
				c1.diff(c2);
			return c1;
		}

		else if ((g1 != null) && (rg2 != null)) {
			if (debug == true)
				System.out
						.println("Warning: First image converted from GrayImage to RealGrayImage");
			rg1 = ImageConverter.toRealGray(image1);
			if (which == 0)
				rg1.add(rg2);
			else if (which == 1)
				rg1.subtract(rg2);
			else if (which == 2)
				rg1.multiply(rg2);
			else if (which == 3)
				rg1.divide(rg2);
			else if (which == 4)
				rg1.diff(rg2);
			return rg1;
		}

		else if ((c1 != null) && (c2 != null)) {
			if (debug == true)
				System.out.println("Both images are ComplexImages");
			if (which == 0)
				c1.add(c2);
			else if (which == 1)
				c1.subtract(c2);
			else if (which == 2)
				c1.multiply(c2);
			else if (which == 3)
				c1.divide(c2);
			else if (which == 4)
				c1.diff(c2);
			return c1;
		}

		else if ((c1 != null) && (g2 != null)) {
			if (debug == true)
				System.out
						.println("Warning: Second image converted from GrayImage to ComplexImage");
			c2 = ImageConverter.toComplex(image2);
			if (which == 0)
				c1.add(c2);
			else if (which == 1)
				c1.subtract(c2);
			else if (which == 2)
				c1.multiply(c2);
			else if (which == 3)
				c1.divide(c2);
			else if (which == 4)
				c1.diff(c2);
			return c1;
		}

		else if ((c1 != null) && (rg2 != null)) {
			if (debug == true)
				System.out
						.println("Warning: Second image converted from RealGrayImage to ComplexImage");
			c2 = ImageConverter.toComplex(image2);
			if (which == 0)
				c1.add(c2);
			else if (which == 1)
				c1.subtract(c2);
			else if (which == 2)
				c1.multiply(c2);
			else if (which == 3)
				c1.divide(c2);
			else if (which == 4)
				c1.diff(c2);
			return c1;
		}

		else if ((co1 != null) && (co2 != null)) {
			if (debug == true)
				System.out.println("Both Images are ColorImages");
			if (which == 0)
				co1.add(co2);
			else if (which == 1)
				co1.subtract(co2);
			else if (which == 2)
				co1.multiply(co2);
			else if (which == 3)
				co1.divide(co2);
			else if (which == 4)
				co1.diff(co2);
			return co1;
		}

		else if ((co1 != null) && (rc2 != null)) {
			if (debug == true)
				System.out
						.println("Warning: First image converted from ColorImage to RealColorImage");
			rc1 = ImageConverter.toRealColor(image1);
			if (which == 0)
				rc1.add(rc2);
			else if (which == 1)
				rc1.subtract(rc2);
			else if (which == 2)
				rc1.multiply(rc2);
			else if (which == 3)
				rc1.divide(rc2);
			else if (which == 4)
				rc1.diff(rc2);
			return rc1;
		}

		else if ((rc1 != null) && (rc2 != null)) {
			if (debug == true)
				System.out.println("Both images are RealColorImage");
			if (which == 0)
				rc1.add(rc2);
			else if (which == 1)
				rc1.subtract(rc2);
			else if (which == 2)
				rc1.multiply(rc2);
			else if (which == 3)
				rc1.divide(rc2);
			else if (which == 4)
				rc1.diff(rc2);
			return rc1;

		}

		else if ((rc1 != null) && (co2 != null)) {
			if (debug == true)
				System.out
						.println("Warning: Second image converted from ColorImage to RealColorImage");
			rc2 = ImageConverter.toRealColor(image2);
			if (which == 0)
				rc1.add(rc2);
			else if (which == 1)
				rc1.subtract(rc2);
			else if (which == 2)
				rc1.multiply(rc2);
			else if (which == 3)
				rc1.divide(rc2);
			else if (which == 4)
				rc1.diff(rc2);
			return rc1;
		} else {
			throw new IncompatibleImagesException();
		}

	}

	/** Utility method; handling the addition of a contant to an image. */
	private static Image basicConst(int which) throws IncompatibleImagesException,
			ColorModelNotSupportedException, ColorModelUnknownException, InterruptedException,
			IllegalPBMFormatException, IOException, ImageNotSupportedException {

		jigl.image.Image image1 = null;
		GrayImage g1 = null;
		ComplexImage c1 = null;
		RealGrayImage rg1 = null;
		ColorImage co1 = null;
		RealColorImage rc1 = null;

		image1 = imageOpen(1);

		// Convert to the appropriate type
		if (image1 instanceof GrayImage)
			g1 = ImageConverter.toGray(image1);
		if (image1 instanceof RealGrayImage)
			rg1 = ImageConverter.toRealGray(image1);
		if (image1 instanceof ColorImage)
			co1 = ImageConverter.toColor(image1);
		if (image1 instanceof RealColorImage)
			rc1 = ImageConverter.toRealColor(image1);
		if (image1 instanceof ComplexImage)
			c1 = ImageConverter.toComplex(image1);

		if (g1 != null) {
			Integer int_val = Integer.valueOf(param[2]);
			int val = int_val.intValue();
			if (debug == true)
				System.out.println("Adding " + val + " to a GrayImage");
			if (which == 0)
				g1.add(val);
			else if (which == 1)
				g1.subtract(val);
			else if (which == 2)
				g1.multiply(val);
			else if (which == 3)
				g1.divide(val);
			return g1;
		}

		else if (rg1 != null) {
			Float float_val = Float.valueOf(param[2]);
			float val = float_val.floatValue();
			if (debug == true)
				System.out.println("Adding " + val + " to a RealGrayImage");
			if (which == 0)
				rg1.add(val);
			else if (which == 1)
				rg1.subtract(val);
			else if (which == 2)
				rg1.multiply(val);
			else if (which == 3)
				rg1.divide(val);
			return rg1;
		}

		else if (co1 != null) {
			Integer f_val1 = Integer.valueOf(param[2]);
			Integer f_val2 = Integer.valueOf(param[3]);
			Integer f_val3 = Integer.valueOf(param[4]);
			int val1 = f_val1.intValue();
			int val2 = f_val2.intValue();
			int val3 = f_val3.intValue();
			if (debug == true)
				System.out.println("Adding " + val1 + ", " + val2 + ", " + val3
						+ " to a ColorImage");
			if (which == 0)
				co1.add(val1, val2, val3);
			else if (which == 1)
				co1.subtract(val1, val2, val3);
			else if (which == 2)
				co1.multiply(val1, val2, val3);
			else if (which == 3)
				co1.divide(val1, val2, val3);
			return co1;
		}

		else if (rc1 != null) {
			Float f_val1 = Float.valueOf(param[2]);
			Float f_val2 = Float.valueOf(param[3]);
			Float f_val3 = Float.valueOf(param[4]);
			float val1 = f_val1.floatValue();
			float val2 = f_val2.floatValue();
			float val3 = f_val3.floatValue();
			if (debug == true)
				System.out.println("Adding " + val1 + ", " + val2 + ", " + val3
						+ " to a RealColorImage");
			if (which == 0)
				rc1.add(val1, val2, val3);
			else if (which == 1)
				rc1.subtract(val1, val2, val3);
			else if (which == 2)
				rc1.multiply(val1, val2, val3);
			else if (which == 3)
				rc1.divide(val1, val2, val3);
			return rc1;
		}

		else if (c1 != null) {
			Float f_val1 = Float.valueOf(param[2]);
			Float f_val2 = Float.valueOf(param[3]);
			float val1 = f_val1.floatValue();
			float val2 = f_val2.floatValue();
			if (debug == true)
				System.out.println("Adding " + val1 + ", " + val2 + " to a ComplexImage");
			if (which == 0)
				c1.add(val1, val2);
			else if (which == 1)
				c1.subtract(val1, val2);
			else if (which == 2)
				c1.multiply(val1, val2);
			else if (which == 3)
				c1.divide(val1, val2);
			return c1;
		} else {
			throw new IncompatibleImagesException();
		}

	}

	/* --------------- Other basic operations --------------------- */

	/** Scale the range to [0..255] */
	private static Image byteSize() throws ImageNotSupportedException,
			ColorModelNotSupportedException, ColorModelUnknownException, InterruptedException,
			IllegalPBMFormatException, IOException {

		jigl.image.Image image1 = null;
		GrayImage g1 = null;
		// ComplexImage c1=null;
		RealGrayImage rg1 = null;
		ColorImage co1 = null;
		RealColorImage rc1 = null;
		image1 = imageOpen(0);

		// Convert to the appropriate type
		if (image1 instanceof GrayImage)
			g1 = ImageConverter.toGray(image1);
		if (image1 instanceof RealGrayImage)
			rg1 = ImageConverter.toRealGray(image1);
		if (image1 instanceof ColorImage)
			co1 = ImageConverter.toColor(image1);
		if (image1 instanceof RealColorImage)
			rc1 = ImageConverter.toRealColor(image1);
		// if (image1 instanceof ComplexImage)
		// c1=ImageConverter.toComplex(image1);

		if (g1 != null) {
			if (debug)
				System.out.println("Performing ByteSize Operation on a GrayImage");
			g1.byteSize();
			return g1;
		}

		else if (rg1 != null) {
			if (debug)
				System.out.println("Performing ByteSize Operation on a RealGrayImage");
			rg1.byteSize();
			return rg1;
		}

		else if (co1 != null) {
			if (debug)
				System.out.println("Performing ByteSize Operation on a ColorImage");
			co1.byteSize();
			return co1;
		}

		else if (rc1 != null) {
			if (debug)
				System.out.println("Performing ByteSize Operation on a RealColorImage");
			rc1.byteSize();
			return rc1;
		}

		// else if (c1!=null) {
		// c1.byteSize();
		// return c1;
		// }
		else {
			System.out.println("Invalid image type");
			System.exit(0);
		}

		return null;

	}

	/** Clip an image. */
	private static Image clip() throws ImageNotSupportedException, ColorModelNotSupportedException,
			ColorModelUnknownException, InterruptedException, IllegalPBMFormatException,
			IOException {

		jigl.image.Image image1 = null;
		GrayImage g1 = null;
		// ComplexImage c1=null;
		RealGrayImage rg1 = null;
		ColorImage co1 = null;
		RealColorImage rc1 = null;

		image1 = imageOpen(1);

		// Convert to the appropriate type

		if (image1 instanceof GrayImage)
			g1 = ImageConverter.toGray(image1);
		if (image1 instanceof RealGrayImage)
			rg1 = ImageConverter.toRealGray(image1);
		if (image1 instanceof ColorImage)
			co1 = ImageConverter.toColor(image1);
		if (image1 instanceof RealColorImage)
			rc1 = ImageConverter.toRealColor(image1);
		// if (image1 instanceof ComplexImage)
		// c1=ImageConverter.toComplex(image1);

		if (g1 != null) {
			Integer int_val1 = Integer.valueOf(param[2]);
			Integer int_val2 = Integer.valueOf(param[3]);
			int val1 = int_val1.intValue();
			int val2 = int_val2.intValue();
			if (debug)
				System.out
						.println("Performing Clip Operation on a GrayImage between the values of "
								+ val1 + " and " + val2);
			g1.clip(val1, val2);
			return g1;
		}

		else if (rg1 != null) {
			Integer int_val1 = Integer.valueOf(param[2]);
			Integer int_val2 = Integer.valueOf(param[3]);
			int val1 = int_val1.intValue();
			int val2 = int_val2.intValue();
			if (debug)
				System.out
						.println("Performing Clip Operation on a RealGrayImage between the values of "
								+ val1 + " and " + val2);
			rg1.clip(val1, val2);
			return rg1;
		}

		else if (co1 != null) {
			Integer int_val1 = Integer.valueOf(param[2]);
			Integer int_val2 = Integer.valueOf(param[3]);
			int val1 = int_val1.intValue();
			int val2 = int_val2.intValue();
			if (debug)
				System.out
						.println("Performing Clip Operation on a ColorImage between the values of "
								+ val1 + " and " + val2);
			co1.clip(val1, val2);
			return co1;
		}

		else if (rc1 != null) {
			Integer int_val1 = Integer.valueOf(param[2]);
			Integer int_val2 = Integer.valueOf(param[3]);
			int val1 = int_val1.intValue();
			int val2 = int_val2.intValue();
			if (debug)
				System.out
						.println("Performing Clip Operation on a RealColorImage between the values of "
								+ val1 + " and " + val2);
			rc1.clip(val1, val2);
			return rc1;
		}

		// else if (c1!=null) {
		// c1.clip();
		// return c1;
		// }
		else
			throw new ImageNotSupportedException();

	}

	/** Clear an image with a constant. */
	private static Image clear() throws ImageNotSupportedException,
			ColorModelNotSupportedException, ColorModelUnknownException, InterruptedException,
			IllegalPBMFormatException, IOException {

		jigl.image.Image image1 = null;
		GrayImage g1 = null;
		// ComplexImage c1=null;
		RealGrayImage rg1 = null;
		ColorImage co1 = null;
		RealColorImage rc1 = null;

		image1 = imageOpen(1);

		// Convert to the appropriate type
		try {
			if (image1 instanceof GrayImage)
				g1 = ImageConverter.toGray(image1);
			if (image1 instanceof RealGrayImage)
				rg1 = ImageConverter.toRealGray(image1);
			if (image1 instanceof ColorImage)
				co1 = ImageConverter.toColor(image1);
			if (image1 instanceof RealColorImage)
				rc1 = ImageConverter.toRealColor(image1);
			// if (image1 instanceof ComplexImage)
			// c1=ImageConverter.toComplex(image1);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		Integer int_val1 = Integer.valueOf(param[1]);
		int val1 = int_val1.intValue();

		if (g1 != null) {
			if (debug)
				System.out.println("Performing Clear Operation on a GrayImage with the value "
						+ val1);
			g1.clear(val1);
			return g1;
		}

		else if (rg1 != null) {
			if (debug)
				System.out.println("Performing Clear Operation on a RealGrayImage with the value "
						+ val1);
			rg1.clear(int_val1.floatValue());
			return rg1;
		}

		else if (co1 != null) {
			if (debug)
				System.out.println("Performing Clear Operation on a ColorImage with the value "
						+ val1);
			co1.clear(new Integer[] {val1, val1, val1});
			return co1;
		}

		else if (rc1 != null) {
			if (debug)
				System.out.println("Performing Clear Operation on a RealColorImage with the value "
						+ val1);
			rc1.clear(new Float[] {int_val1.floatValue(), int_val1.floatValue(), int_val1.floatValue()});
			return rc1;
		}

		// else if (c1!=null) {
		// c1.clip();
		// return c1;
		// }
		else {
			throw new ImageNotSupportedException();
		}
	}
}
