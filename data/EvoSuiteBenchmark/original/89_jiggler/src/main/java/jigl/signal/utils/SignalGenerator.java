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
package jigl.signal.utils;

import jigl.signal.RealSignal;
import jigl.signal.Signal;
import jigl.signal.io.SignalOutputStream;

/**
 * SignalGenerator is a class that builds various signals for use in testing.
 * SignalGenerator supports command line options. <BR>
 * &nbsp; <BR>
 * &nbsp; <CENTER>
 * <TABLE BORDER CELLPADDING=4 WIDTH="100%" BGCOLOR="#CCCCCC" >
 * <TR>
 * <TD COLSPAN="3" BGCOLOR="#F0F000">
 * <CENTER><B><FONT SIZE=+1>SignalGenerator Command Lines</FONT></B></CENTER></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="20%" BGCOLOR="#59ACFF">
 * <CENTER>Type of signal</CENTER></TD>
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
 * <TD><FONT SIZE=-1>&nbsp;-uniform &lt;X> &lt;color> &lt;output file></FONT></TD>
 * 
 * <TD ROWSPAN="5"><FONT
 * SIZE=-1>&nbsp;&lt;X>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp
 * ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; width (integer value)&nbsp;</FONT>&nbsp;&nbsp;
 * <BR>
 * <FONT SIZE=-1>&lt;std
 * dev>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp
 * ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; standard
 * deviation&nbsp;</FONT>&nbsp; <BR>
 * <FONT
 * SIZE=-1>&lt;frequency>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp
 * ;&nbsp;&nbsp; frequency&nbsp;</FONT>&nbsp; <BR>
 * <FONT
 * SIZE=-1>&lt;color*>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp
 * ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; color (integer
 * 0..255)</FONT>&nbsp; <BR>
 * <FONT
 * SIZE=-1>&lt;direction>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp
 * ;&nbsp;&nbsp;&nbsp;&nbsp; -a for ascending, -d for descending</FONT>&nbsp; <BR>
 * <FONT SIZE=-1>&lt;output
 * file>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; File to
 * output to</FONT>&nbsp; <BR>
 * <FONT
 * SIZE=-1>&lt;phase>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; phase in
 * degrees</FONT>&nbsp; <BR>
 * <FONT SIZE=-1>&lt;mean
 * x>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp
 * ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; mean x value</FONT>&nbsp; <BR>
 * <FONT SIZE=-1>&lt;mean
 * y>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp
 * ;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; mean y value</FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="20%"><FONT SIZE=-1>&nbsp;Ramp</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-ramp &lt;X>&nbsp; &lt;direction> &lt;output
 * file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="20%"><FONT SIZE=-1>&nbsp;Gaussian</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>-gaussian&nbsp; &lt;X> &lt;mean x> &lt;std dev> &lt;output
 * file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="20%"><FONT SIZE=-1>&nbsp;Sinusoidal</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>-sinusoid &lt;X> &lt;frequency> &lt;phase> &lt;output
 * file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="20%"><FONT SIZE=-1>&nbsp;Stripes</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>-stripes&nbsp; &lt;X>&nbsp; &lt;color1> &lt;color2>
 * &lt;frequency> &lt;output file></FONT></TD>
 * </TR>
 * </TABLE>
 * </CENTER> &nbsp; <CENTER>Examples of possible Command-lines:</CENTER> &nbsp;
 * <CENTER>
 * <TABLE BORDER CELLPADDING=10 COLS=1 WIDTH="640" >
 * <TR>
 * <TD><FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java
 * SignalGenerator -uniform 250&nbsp; 45
 * D:\pictures\test1.pds</FONT></FONT>&nbsp;
 * 
 * <P>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java
 * SignalGenerator -ramp 250 -a D:\pictures\test1.pds</FONT></FONT>&nbsp; <BR>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;</FONT></FONT>&nbsp; <BR>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java
 * SignalGenerator -gaussian 250&nbsp; 45
 * D:\pictures\test1.pds</FONT></FONT>&nbsp;
 * 
 * <P>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java
 * SignalGenerator -sinusoid 250&nbsp; 45
 * D:\pictures\test1.pds</FONT></FONT>&nbsp;
 * 
 * <P>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java
 * SignalGenerator -stripes 250 0 255 34 D:\pictures\test1.pds</FONT></FONT>
 * </TD>
 * </TR>
 * </TABLE>
 * </CENTER>
 */

public class SignalGenerator {
//	public static enum Direction {
//		/** Vertical Orientation */
//		VERTICAL,
//		/** Horizontal Orientation */
//		HORIZONTAL
//	};
	
	private static enum Orientation {
		/** Ascending Direction */
		ASCENDING,
		/** Descending Direction */
		DESCENDING,
		NONE
	};

	private static String[] param;

	/** For command line option. */
	public static void main(String[] argv) {

		try {
			int last = 0;
			param = argv;

			String op = argv[0];
			Signal signal = null;
			// int outfile=0;
			last = argv.length - 1;

			if (op.equals("-uniform"))
				signal = uniform_parse();
			else if (op.equals("-ramp"))
				signal = ramp_parse();
			else if (op.equals("-gaussian"))
				signal = gaussian_parse();
			else if (op.equals("-sinusoid"))
				signal = sinusoid_parse();
			else if (op.equals("-stripes"))
				signal = stripes_parse();
			else {
				throw new InvalidCommandLineException();
			}

			SignalOutputStream os = new SignalOutputStream(argv[last]);
			os.write(signal);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static RealSignal uniform_parse() {
		Integer int_val1 = Integer.valueOf(param[1]);
		int X = int_val1.intValue();

		Float int_val3 = Float.valueOf(param[2]);
		float val = int_val3.floatValue();

		return uniform(X, val);
	}

	/**
	 * Returns a Real uniform signal.
	 * 
	 * @param X
	 *            width of the signal
	 * @param color
	 *            the color of the signal
	 */
	public static RealSignal uniform(int X, float color) {

		RealSignal signal = new RealSignal(X);

		for (int x = 0; x < X; x++)
			signal.set(x, color);

		return signal;
	}

	/** Utility routine to parse command line. */
	private static RealSignal stripes_parse() throws InvalidCommandLineException {

		Integer int_val1 = Integer.valueOf(param[1]);
		int X = int_val1.intValue();

		Float int_val3 = Float.valueOf(param[2]);
		float color2 = int_val3.floatValue();
		Float int_val4 = Float.valueOf(param[3]);
		float color1 = int_val4.floatValue();

		Integer int_val5 = Integer.valueOf(param[4]);
		int frequency = int_val5.intValue();

		return stripes(X, color1, color2, frequency);
	}

	/**
	 * Returns a Real strip signal
	 * 
	 * @param X
	 *            width of the signal
	 * @param color1
	 *            the starting color
	 * @param color2
	 *            the second color
	 * @param frequency
	 *            the frequency of the square wave
	 */
	public static RealSignal stripes(int X, float color1, float color2, int frequency) {
		int flag = 2;
		RealSignal signal = new RealSignal(X);

		int swit = X / (frequency * 2);
		for (int x = 0; x < X; x++) {
			if (flag == 1)
				signal.set(x, color1);
			else
				signal.set(x, color2);

			if ((x % swit) == 0)
				if (flag == 1)
					flag = 2;
				else
					flag = 1;
		}
		return signal;
	}

	/** Utility routine to parse command line. */
	private static RealSignal ramp_parse() throws InvalidCommandLineException {

		Integer int_val1 = Integer.valueOf(param[1]);
		int X = int_val1.intValue();
		Orientation orientation = Orientation.NONE;
		String direction = param[2];
		if (direction.equals("-a"))
			orientation = Orientation.ASCENDING;
		else if (direction.equals("-d"))
			orientation = Orientation.DESCENDING;
		else {
			throw new InvalidCommandLineException();
		}

		return ramp(X, orientation);

	}

	/**
	 * Returns a Real ramp signal
	 * 
	 * @param X
	 *            width of the signal
	 * @param direction
	 *            either ascending or descending
	 */
	public static RealSignal ramp(int X, Orientation direction) {

		RealSignal signal = new RealSignal(X);

		double rate = 255 / (double) X;
		double val = 0;
		for (int x = 0; x < X; x++) {
			if (direction == Orientation.ASCENDING)
				signal.set(x, (float) val);
			else if (direction == Orientation.DESCENDING)
				signal.set(x, (255 - (float) val));
			val = val + rate;
		}
		return signal;
	}

	/** Utility routine to parse command line. */
	private static RealSignal gaussian_parse() {

		Integer int_val1 = Integer.valueOf(param[1]);
		int X = int_val1.intValue();
		Float int_val3 = Float.valueOf(param[2]);
		float x0 = int_val3.intValue();
		Float f_val = Float.valueOf(param[3]);
		float std = f_val.floatValue();

		return gaussian(X, x0, std);
	}

	/**
	 * Returns a Real gaussian signal
	 * 
	 * @param X
	 *            width of the signal
	 * @param x0
	 *            x coordinate of the mean
	 * @param std
	 *            standard deviation
	 */
	public static RealSignal gaussian(int X, float x0, float std) {
		float val = 0;
		float x_prime = 0;
		RealSignal signal = new RealSignal(X);
		for (int x = 0; x < X; x++) {
			x_prime = (float) x - x0;
			if (x_prime > (X / 2))
				x_prime -= (float) X;
			if (x_prime < (-X / 2))
				x_prime += (float) X;
			val = (float) (1 / (2 * java.lang.Math.PI * (std * std)))
					* (float) java.lang.Math.exp(-1 * ((x_prime * x_prime) / (2 * (std * std))));
			signal.set(x, val);
		}
		signal.byteSize();

		return signal;
	}

	/** Utility routine to parse command line. */
	private static RealSignal sinusoid_parse() throws InvalidCommandLineException {

		Integer int_val1 = Integer.valueOf(param[1]);
		int X = int_val1.intValue();

		Float val3 = Float.valueOf(param[2]);
		float frequency = val3.floatValue();
		// boolean check=true;

		Float val4 = Float.valueOf(param[3]);
		float phase = val4.floatValue();

		return sinusoid(X, frequency, phase);
	}

	/**
	 * Returns a Real sinusoidal signal
	 * 
	 * @param X
	 *            width of the signal
	 * @param frequency
	 *            the frequency of the wave
	 * @param phase
	 *            the phase of the wave in degrees
	 */
	public static RealSignal sinusoid(final int X, final float frequency, final float phase) {
		//TODO: replace this with Math.toRadians
		final float phaseInRadians = (float) (phase * java.lang.Math.PI) / 180;
		float sine = 0;
		RealSignal signal = new RealSignal(X);
		for (int i = 0; i < X; i++) {
			sine = (float) java.lang.Math.sin((2 * java.lang.Math.PI * frequency * i / X) + phaseInRadians);
			signal.set(i, sine);

		}

		signal.byteSize();
		return signal;
	}
}
