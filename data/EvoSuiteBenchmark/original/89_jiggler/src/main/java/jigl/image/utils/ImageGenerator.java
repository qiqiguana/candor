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

import jigl.image.Image;
import jigl.image.io.ImageOutputStream;
import jigl.image.types.ComplexImage;
import jigl.image.types.RealGrayImage;

/**
 * ImageGenerator is a class that builds various images. ImageGenerator supports command line
 * options. All the methods are static. <BR>
 * &nbsp; <BR>
 * &nbsp; <CENTER>
 * <TABLE BORDER CELLPADDING=4 WIDTH="100%" BGCOLOR="#CCCCCC" >
 * <TR>
 * <TD COLSPAN="3" BGCOLOR="#F0F000">
 * <CENTER><B><FONT SIZE=+1>ImageGenerator Command Lines</FONT></B></CENTER></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="20%" BGCOLOR="#59ACFF">
 * <CENTER>Type of image</CENTER></TD>
 * 
 * <TD BGCOLOR="#59ACFF">
 * <CENTER>Syntax</CENTER></TD>
 * 
 * <TD BGCOLOR="#59ACFF">
 * <CENTER>Comments</CENTER></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="10%">&nbsp;<FONT SIZE=-1>Uniform</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-uniform &lt;X> &lt;Y> &lt;color> &lt;output file></FONT></TD>
 * 
 * <TD ROWSPAN="5"><FONT
 * SIZE=-1>&nbsp;&lt;X>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp
 * ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; width
 * (integer value)&nbsp;</FONT>&nbsp; <BR>
 * <FONT
 * SIZE=-1>&nbsp;&lt;Y>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; height (integer
 * value)&nbsp;</FONT>&nbsp; <BR>
 * <FONT SIZE=-1>&lt;orientation>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -h for
 * horizonal, -v for vertical&nbsp;</FONT>&nbsp; <BR>
 * <FONT SIZE=-1>&lt;std
 * dev>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp; standard deviation&nbsp;</FONT>&nbsp; <BR>
 * <FONT SIZE=-1>&lt;frequency>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * frequency&nbsp;</FONT>&nbsp; <BR>
 * <FONT
 * SIZE=-1>&lt;color*>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&
 * nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; color (integer 0..255)</FONT>&nbsp; <BR>
 * <FONT
 * SIZE=-1>&lt;direction>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp
 * ;&nbsp; -a for ascending, -d for descending</FONT>&nbsp; <BR>
 * <FONT SIZE=-1>&lt;output file>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; File
 * to output to</FONT>&nbsp; <BR>
 * <FONT
 * SIZE=-1>&lt;phase>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp
 * ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; phase in degrees</FONT>&nbsp; <BR>
 * <FONT SIZE=-1>&lt;mean
 * x>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&
 * nbsp;&nbsp;&nbsp;&nbsp;&nbsp; mean x value</FONT>&nbsp; <BR>
 * <FONT SIZE=-1>&lt;mean
 * y>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&
 * nbsp;&nbsp;&nbsp;&nbsp; mean y value</FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="20%"><FONT SIZE=-1>&nbsp;Ramp</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-ramp &lt;X> &lt;Y> &lt;orientation> &lt;direction> &lt;output
 * file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="20%"><FONT SIZE=-1>&nbsp;Gaussian</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>-gaussian&nbsp; &lt;X> &lt;Y> &lt;mean x> &lt;mean y> &lt;std dev> &lt;output
 * file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="20%"><FONT SIZE=-1>&nbsp;Sinusoidal</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>-sinusoid &lt;X> &lt;Y> &lt;orientation> &lt;frequency> &lt;phase> &lt;output
 * file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="20%"><FONT SIZE=-1>&nbsp;Stripes</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>-stripes&nbsp; &lt;X> &lt;Y> &lt;color1> &lt;color2> &lt;orientation>
 * &lt;frequency> &lt;output file></FONT></TD>
 * </TR>
 * </TABLE>
 * </CENTER> &nbsp; <CENTER>Examples of possible Command-lines:</CENTER> &nbsp; <CENTER>
 * <TABLE BORDER CELLPADDING=10 COLS=1 WIDTH="640" >
 * <TR>
 * <TD><FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java ImageGenerator -uniform 250
 * 300 45 D:\pictures\test1.pgm</FONT></FONT>&nbsp;
 * 
 * <P>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java ImageGenerator -ramp 250 300 -h
 * -a D:\pictures\test1.pgm</FONT></FONT>&nbsp; <BR>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;</FONT></FONT>&nbsp; <BR>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java ImageGenerator -gaussian 250 300
 * 0 0 45 D:\pictures\test1.pgm</FONT></FONT>&nbsp;
 * 
 * <P>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java ImageGenerator -sinusoid 250 300
 * -v 45 45 D:\pictures\test1.pgm</FONT></FONT>&nbsp;
 * 
 * <P>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java ImageGenerator -stripes 250 300
 * 0 255 -h 34 D:\pictures\test1.pgm</FONT></FONT>
 * </TD>
 * </TR>
 * </TABLE>
 * </CENTER>
 * <P>
 * &nbsp;&nbsp;&nbsp;<FONT FACE="Courier New,Courier"><FONT SIZE=-1></FONT></FONT>
 */
public class ImageGenerator {
	/** Vertical Orientation */
	public final static int VERTICAL = 0;
	/** Horizontal Orientation */
	public final static int HORIZONTAL = 1;
	/** Ascending Direction */
	public final static int ASCENDING = 2;
	/** Descending Direction */
	public final static int DESCENDING = 3;

	/** Commandline parameters */
	private static String[] param;
	/** Constant PI in Math */
	private static double PI = Math.PI;

	/**
	 * For commandline options. Images are saved in the following format:<br>
	 * <br>
	 * 
	 * <pre>
	 * GrayImage -> PGM file<br>
	 * 		RealGrayImage -> PRGM file<br>
	 * 		ColorImage -> PPM file<br>
	 * 		RealColorImage -> PRCM file<br>
	 * 		ComplexImage -> PPM file
	 * </pre>
	 * 
	 * <br>
	 * Command lines are different for different type of images generated.
	 */
	public static void main(String[] argv) {

		int last = 0;
		param = argv;

		try {
			String op = argv[0];
			Image image = null;
			//			int outfile=0;
			last = argv.length - 1;

			if (op.equals("-uniform"))
				image = uniform_parse();
			else if (op.equals("-ramp"))
				image = ramp_parse();
			else if (op.equals("-gaussian"))
				image = gaussian_parse();
			else if (op.equals("-sinusoid"))
				image = sinusoid_parse();
			else if (op.equals("-stripes"))
				image = stripes_parse();
			else {
				throw new InvalidCommandLineException();
			}
			//System.out.println("last arg is "+argv[last]);
			ImageOutputStream os = new ImageOutputStream(argv[last]);
			os.write(image);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Return a uniform RealGrayImage image. */
	private static RealGrayImage uniform_parse() {
		Integer int_val1 = Integer.valueOf(param[1]);
		Integer int_val2 = Integer.valueOf(param[2]);
		int X = int_val1.intValue();
		int Y = int_val2.intValue();

		Float int_val3 = Float.valueOf(param[3]);
		float val = int_val3.floatValue();

		return uniform(X, Y, val);
	}

	/**
	 * Returns a RealGray uniform image
	 * 
	 * @param X width of the image
	 * @param Y height of the image
	 * @param color the grayscale of the image
	 */
	public static RealGrayImage uniform(int X, int Y, float color) {

		RealGrayImage image = new RealGrayImage(X, Y);

		for (int x = 0; x < X; x++)
			for (int y = 0; y < Y; y++)
				image.set(x, y, color);

		return image;
	}

	/** Return a RealGray stripe image */
	private static RealGrayImage stripes_parse() throws InvalidCommandLineException {

		Integer int_val1 = Integer.valueOf(param[1]);
		Integer int_val2 = Integer.valueOf(param[2]);
		int X = int_val1.intValue();
		int Y = int_val2.intValue();

		Float int_val3 = Float.valueOf(param[3]);
		float color2 = int_val3.floatValue();
		Float int_val4 = Float.valueOf(param[4]);
		float color1 = int_val4.floatValue();

		Integer int_val5 = Integer.valueOf(param[6]);
		int frequency = int_val5.intValue();

		int orient = 9;
		String orientation = param[5];
		if (orientation.equals("-h"))
			orient = HORIZONTAL;
		else if (orientation.equals("-v"))
			orient = VERTICAL;
		else {
			throw new InvalidCommandLineException();
		}

		return stripes(X, Y, color1, color2, orient, frequency);
	}

	/**
	 * Returns a RealGray stripe image
	 * 
	 * @param X width of the image
	 * @param Y height of the image
	 * @param color1 the first color
	 * @param color2 the second color
	 * @param orientation either horizontal or vertical
	 * @param frequency the frequency of the square wave
	 * @see jigl.image.utils.ImageGenerator#VERTICAL
	 * @see jigl.image.utils.ImageGenerator#HORIZONTAL
	 */
	public static RealGrayImage stripes(int X, int Y, float color1, float color2, int orientation,
			int frequency) {
		int flag = 2;
		RealGrayImage image = new RealGrayImage(X, Y);
		if (orientation == HORIZONTAL) {
			int swit = X / (frequency * 2);
			for (int x = 0; x < X; x++)
				for (int y = 0; y < Y; y++) {
					if ((flag == 1) || (x == 0))
						image.set(x, y, color1);
					else
						image.set(x, y, color2);
					if (((x % swit) == 0) && (y == Y - 1))
						if (flag == 1)
							flag = 2;
						else
							flag = 1;
				}
		} else if (orientation == VERTICAL) {
			int swit = Y / (frequency * 2);
			for (int y = 0; y < Y; y++)
				for (int x = 0; x < X; x++) {
					if ((flag == 1) || (y == 0))
						image.set(x, y, color1);
					else
						image.set(x, y, color2);

					if (((y % swit) == 0) && (x == X - 1))
						if (flag == 1)
							flag = 2;
						else
							flag = 1;
				}
		}
		return image;
	}

	/** Returns a RealGray ramp image. */
	private static RealGrayImage ramp_parse() throws InvalidCommandLineException {

		Integer int_val1 = Integer.valueOf(param[1]);
		Integer int_val2 = Integer.valueOf(param[2]);
		int X = int_val1.intValue();
		int Y = int_val2.intValue();
		int orient = 9;
		int direct = 9;
		String orientation = param[3];
		if (orientation.equals("-h"))
			orient = HORIZONTAL;
		else if (orientation.equals("-v"))
			orient = VERTICAL;
		else {
			throw new InvalidCommandLineException();
		}
		String direction = param[4];
		if (direction.equals("-a"))
			direct = ASCENDING;
		else if (direction.equals("-d"))
			direct = DESCENDING;
		else {
			throw new InvalidCommandLineException();
		}

		return ramp(X, Y, orient, direct);

	}

	/**
	 * Returns a RealGray ramp image
	 * 
	 * @param X width of the image
	 * @param Y height of the image
	 * @param orientation eithier verticle or horizonal
	 * @param direction eithier ascending or descending
	 * @see jigl.image.utils.ImageGenerator#VERTICAL
	 * @see jigl.image.utils.ImageGenerator#HORIZONTAL
	 * @see jigl.image.utils.ImageGenerator#ASCENDING
	 * @see jigl.image.utils.ImageGenerator#DESCENDING
	 */
	public static RealGrayImage ramp(int X, int Y, int orientation, int direction) {

		RealGrayImage image = new RealGrayImage(X, Y);

		if (orientation == HORIZONTAL) {
			double rate = 255 / (double) X;
			double val = 0;
			for (int x = 0; x < X; x++) {
				for (int y = 0; y < Y; y++) {
					if (direction == ASCENDING)
						image.set(x, y, (float) val);
					else if (direction == DESCENDING)
						image.set(x, y, (255 - (float) val));

				}
				val = val + rate;
			}
		}

		else if (orientation == VERTICAL) {
			double rate = 255 / (double) Y;
			double val = 0;
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					if (direction == ASCENDING)
						image.set(x, y, (float) val);
					else if (direction == DESCENDING)
						image.set(x, y, (255 - (float) val));

				}
				val = val + rate;
			}
		}

		//PMhandler.displayImage(ImageConverter.toGray(image),"test");
		//PMhandler.writeToFile(ImageConverter.toGray(image),"test.pgm");
		return image;
	}

	private static RealGrayImage gaussian_parse() {

		Integer int_val1 = Integer.valueOf(param[1]);
		Integer int_val2 = Integer.valueOf(param[2]);
		int X = int_val1.intValue();
		int Y = int_val2.intValue();
		Float int_val3 = Float.valueOf(param[3]);
		Float int_val4 = Float.valueOf(param[4]);
		float x0 = int_val3.intValue();
		float y0 = int_val4.intValue();
		Float f_val = Float.valueOf(param[5]);
		float std = f_val.floatValue();

		return gaussian(X, Y, x0, y0, std);
	}

	/**
	 * Returns a RealGray gaussian image
	 * 
	 * @param X width of the image
	 * @param Y height of the image
	 * @param x0 x coordiant of the mean
	 * @param y0 y cooridant of the mean
	 * @param std standard deviation
	 */
	public static RealGrayImage gaussian(int X, int Y, float x0, float y0, float std) {
		float val = 0;
		float x_prime = 0;
		float y_prime = 0;
		RealGrayImage image = new RealGrayImage(X, Y);

		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {
				x_prime = (float) x - x0;
				y_prime = (float) y - y0;
				if (x_prime > (X / 2))
					x_prime -= (float) X;
				if (x_prime < (-X / 2))
					x_prime += (float) X;
				if (y_prime > (Y / 2))
					y_prime -= (float) Y;
				if (y_prime < (-Y / 2))
					y_prime += (float) Y;

				val = (float) (1 / (2 * PI * (std * std)))
						* (float) java.lang.Math
								.exp(-1
										* (((x_prime * x_prime) + (y_prime * y_prime)) / (2 * (std * std))));
				image.set(x, y, val);
			}
		}
		image.byteSize();
		return image;
	}

	/**
	 * Build a Gaussian image in frequency domain with standard deviation <code>sigma</code>, width
	 * <code>nx</code> and height <code>ny</code>.
	 * 
	 * @param sigma the standard deviation
	 * @param nx the width of the image
	 * @param ny the height of the image
	 * @param order_x order of x differentiation
	 * @param order_y oreder of y differentiation
	 * @debug the debug flag
	 */
	public static ComplexImage buildGaussInFreqDomain(float sigma, int nx, int ny, int order_x,
			int order_y, boolean debug)

	{

		if (debug == true) {
			System.out.println("Building a Gaussian in the Frequency Domain.");
			System.out.println("Order of x differentiation is " + order_x);
			System.out.println("Order of y differentiation is " + order_y);
		}

		float dnx = nx;
		float dny = ny;
		float xhalf = dnx / 2.0f;
		float yhalf = dny / 2.0f;
		;
		float FreqStdDev = 1.0f / (2.0f * (float) PI * sigma);
		float denominator = 2.0f * FreqStdDev * FreqStdDev;
		float nu_x, nu_y, nu_squared;
		float r_val, i_val, r_tmp, i_tmp;
		float sum = 0.0f;
		int i, j, d; // loop vars

		ComplexImage cImg = new ComplexImage(nx, ny);

		/* Calculating constants for convolution with Gaussian */

		if (debug == true)
			System.out.println("2 * (frequency standard deviation)^2 = " + denominator);
		for (j = 0; j < ny; j++) {
			nu_y = (float) ((j < yhalf) ? j : j - ny) / dny;
			for (i = 0; i < nx; i++) {
				nu_x = (float) ((i < xhalf) ? i : i - nx) / dnx;
				// Compute the value of the filter at that position
				nu_squared = nu_x * nu_x + nu_y * nu_y;
				//				float arg = -(nu_squared/denominator);
				r_val = (float) Math.exp((float) (-(nu_squared / denominator)));
				i_val = 0;
				// Apply differentiation in x
				for (d = 0; d < order_x; d++) {
					r_tmp = -i_val * nu_x * 2f * (float) PI;
					i_tmp = r_val * nu_x * 2f * (float) PI;
					r_val = r_tmp;
					i_val = i_tmp;
				}
				// Apply differentiation in y
				for (d = 0; d < order_y; d++) {
					r_tmp = -i_val * nu_y * 2f * (float) PI;
					i_tmp = r_val * nu_y * 2f * (float) PI;
					r_val = r_tmp;
					i_val = i_tmp;
				}
				sum += r_val;
				// Normalize by number of pixels
				// r_val /= nx * ny;
				// i_val /= nx * ny;
				// r_val *= 1000;
				// i_val *= 1000;
				// And write results to image
				//cImg.set(j*nx+i, r_val, i_val);
				cImg.set(i, j, r_val, i_val);
			}
		}
		// cImg.divide(sum,1f);
		//tg.end();
		return cImg;

	}

	/**
	 * Build a linear filter in frequency domain (Gaussian).
	 * 
	 * @param sigma the standard deviation
	 * @param nx the width of the image
	 * @param ny the height of the image
	 * @order_x order of x differentiation
	 * @order_y oreder of y differentiation
	 * @debug the debug flag
	 */
	public static ComplexImage buildLinearFilter(float sigma, int nx, int ny, int order_x,
			int order_y, boolean debug) {

		if (debug == true) {
			System.out.println("Building a linear filter in the Frequency Domain.");
			System.out.println("Order of x differentiation is " + order_x);
			System.out.println("Order of y differentiation is " + order_y);
		}

		float dnx = nx;
		float dny = ny;
		float xhalf = dnx / 2.0f;
		float yhalf = dny / 2.0f;
		;
		float FreqStdDev = 1.0f / (2.0f * (float) PI * sigma);
		float denominator = 2.0f * FreqStdDev * FreqStdDev;
		float nu_x, nu_y, nu_squared;
		float r_val, i_val, r_tmp, i_tmp;
		float sum = 0.0f;
		int i, j, d; // loop vars

		ComplexImage cImg = new ComplexImage(nx, ny);

		/* Calculating constants for convolution with Gaussian */

		if (debug == true)
			System.out.println("2 * (frequency standard deviation)^2 = " + denominator);
		for (j = 0; j < ny; j++) {
			nu_y = (float) ((j < yhalf) ? j : j - ny) / dny;
			for (i = 0; i < nx; i++) {
				nu_x = (float) ((i < xhalf) ? i : i - nx) / dnx;
				// Compute the value of the filter at that position
				nu_squared = (float) Math.sqrt(nu_x * nu_x + nu_y * nu_y);
				if (nu_squared < 0)
					nu_squared = -nu_squared;
				if (nu_squared < sigma)
					r_val = (float) (1 - (nu_squared / sigma));
				else
					r_val = 0;
				i_val = 0;
				// Apply differentiation in x
				for (d = 0; d < order_x; d++) {
					r_tmp = -i_val * nu_x * 2f * (float) PI;
					i_tmp = r_val * nu_x * 2f * (float) PI;
					r_val = r_tmp;
					i_val = i_tmp;
				}
				// Apply differentiation in y
				for (d = 0; d < order_y; d++) {
					r_tmp = -i_val * nu_y * 2f * (float) PI;
					i_tmp = r_val * nu_y * 2f * (float) PI;
					r_val = r_tmp;
					i_val = i_tmp;
				}
				sum += r_val;
				// Normalize by number of pixels
				// r_val /= nx * ny;
				// i_val /= nx * ny;
				// r_val *= 1000;
				// i_val *= 1000;
				// And write results to image
				//cImg.set(j*nx+i, r_val, i_val);
				cImg.set(i, j, r_val, i_val);
			}
		}
		// cImg.divide(sum,1f);
		//tg.end();
		return cImg;

	}

	/**
	 * Build a Gaussian function in space domain with range[0.0 .. 255.0]. When
	 * <code>x0=X/2 and y0= Y/2</code>, the Gaussian image is center-symmetric.
	 * 
	 * @param x0 center position in x direction.
	 * @param y0 center position in y direction
	 */
	public static RealGrayImage ngaussian(int X, int Y, float x0, float y0, float std) {
		float val = 0;
		float x_prime = 0;
		float y_prime = 0;
		RealGrayImage image = new RealGrayImage(X, Y);

		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {
				x_prime = (float) x - x0;
				y_prime = (float) y - y0;
				if (x_prime > (X / 2))
					x_prime -= (float) X;
				if (x_prime < (-X / 2))
					x_prime += (float) X;
				if (y_prime > (Y / 2))
					y_prime -= (float) Y;
				if (y_prime < (-Y / 2))
					y_prime += (float) Y;

				val = (float) java.lang.Math.exp(-1
						* (((x_prime * x_prime) + (y_prime * y_prime)) / (2 * (std * std))));
				image.set(x, y, val);
			}
		}
		image.byteSize();
		return image;
	}

	/** Returns a RealGray sinusoidal image. */
	private static RealGrayImage sinusoid_parse() throws InvalidCommandLineException {

		Integer int_val1 = Integer.valueOf(param[1]);
		Integer int_val2 = Integer.valueOf(param[2]);
		int X = int_val1.intValue();
		int Y = int_val2.intValue();

		Float val3 = Float.valueOf(param[4]);
		float frequency = val3.floatValue();
		//		boolean check=true;

		//System.out.println("para[5]= "+param[5]);
		Float val4 = Float.valueOf(param[5]);
		float phase = val4.floatValue();

		int orient = 9;
		String orientation = param[3];
		if (orientation.equals("-h"))
			orient = HORIZONTAL;
		else if (orientation.equals("-v"))
			orient = VERTICAL;
		else {
			throw new InvalidCommandLineException();
		}

		return sinusoid(X, Y, orient, frequency, phase);
	}

	/**
	 * Returns a RealGray sinusoidal image
	 * 
	 * @param X width of the image
	 * @param Y height of the image
	 * @param orientation eithier horizonal or verticle
	 * @param frequency the frequency of the wave
	 * @param phase the phase of the wave in degrees
	 * @see jigl.image.utils.ImageGenerator#VERTICAL
	 * @see jigl.image.utils.ImageGenerator#HORIZONTAL
	 */
	public static RealGrayImage sinusoid(int X, int Y, int orientation, float frequency, float phase) {

		phase = (float) (phase * PI) / 180;
		float sine = 0;
		RealGrayImage image = new RealGrayImage(X, Y);
		if (orientation == HORIZONTAL) {
			for (int x = 0; x < X; x++) {
				for (int y = 0; y < Y; y++) {
					sine = (float) java.lang.Math.sin((2 * PI * frequency * x / X) + phase);
					image.set(x, y, sine);
				}
			}
			image.byteSize();
		}

		else if (orientation == VERTICAL) {
			//			float value = Y/(frequency*frequency);
			//			double rate = 255/(double)value;
			//			double val=0;
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					sine = (float) java.lang.Math.sin(2 * PI * frequency * y / X);
					image.set(x, y, sine);
				}
			}
			image.byteSize();
		}

		return image;
	}

}
