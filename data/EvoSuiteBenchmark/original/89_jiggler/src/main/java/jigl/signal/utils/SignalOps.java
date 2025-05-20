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

import java.io.IOException;

import jigl.signal.ColorModelNotSupportedException;
import jigl.signal.ColorModelUnknownException;
import jigl.signal.ComplexSignal;
import jigl.signal.DiscreteSignal;
import jigl.signal.RealSignal;
import jigl.signal.Signal;
import jigl.signal.SignalNotSupportedException;
import jigl.signal.io.IllegalPBMFormatException;
import jigl.signal.io.SignalInputStream;
import jigl.signal.io.SignalOutputStream;

/**
 * SignalOps is a class that allows basic operations on signals from the Command
 * Line. Below is a table of the currently supported operations and their syntax <BR>
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
 * <TD><FONT SIZE=-1>Add&nbsp; two signals</FONT></TD>
 * 
 * <TD WIDTH="40%"><FONT SIZE=-1>&nbsp;-add &lt;signal1> &lt;signal2> &lt;output
 * file></FONT></TD>
 * 
 * <TD ROWSPAN="4" WIDTH="40%"><FONT SIZE=-1>&nbsp;</FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD WIDTH="20%"><FONT SIZE=-1>Subtract two signals</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-subtract &lt;signal1> &lt;signal2> &lt;output
 * file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Multiply two signals</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-multiply &lt;signal1> &lt;signal2> &lt;output
 * file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Divide two signals</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-divide &lt;signal1> &lt;signal2> &lt;output
 * file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Add a constant to all the pixels in a signal</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-addconst &lt;signal> &lt;const>+ &lt;output
 * file></FONT></TD>
 * 
 * <TD ROWSPAN="4" WIDTH="40%"><FONT SIZE=-1>* Note: It is <B>not </B>possible
 * to use these operations using float or double values on a
 * DiscreteSignal.&nbsp;</FONT>&nbsp;
 * 
 * <P>
 * <FONT SIZE=-1>*Note:&nbsp; ComplexSignals require two constants, one for the
 * real plane and one for the imaginary plane.&nbsp;</FONT>
 * </TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Subtract a constant from all the pixels in a signal</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-subtractconst &lt;signal> &lt;const>+ &lt;output
 * file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Multiply a constant by all the pixels in a signal</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-multiplyconst &lt;signal> &lt;const>+ &lt;output
 * file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Divide all the pixels in a signal by a constant</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-divideconst &lt;signal> &lt;const>+ &lt;output
 * file></FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Clip an arbitary region of a signal</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-clip &lt;signal> &lt;const1> &lt;const2> &lt;output
 * file></FONT></TD>
 * 
 * <TD WIDTH="40%"><FONT SIZE=-1>&nbsp;const1 is the low
 * value&nbsp;</FONT>&nbsp; <BR>
 * <FONT SIZE=-1>&nbsp;const2 is the high value</FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Clear an signal to a value</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-clear &lt;signal> &lt;const> &lt;output file></FONT>
 * </TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;the constant is the value to set it to</FONT></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>ByteSize operation</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>&nbsp;-bytesize &lt;signal> &lt;output file></FONT></TD>
 * 
 * <TD></TD>
 * </TR>
 * 
 * <TR>
 * <TD><FONT SIZE=-1>Diff operation</FONT></TD>
 * 
 * <TD><FONT SIZE=-1>-diff &lt;signal> &lt;output file></FONT></TD>
 * 
 * <TD></TD>
 * </TR>
 * </TABLE>
 *<FONT SIZE=-1>* SignalOpps also support a "-debug" option.&nbsp; This will
 * display some helpful imformation in debugging problems</FONT> <BR>
 * <FONT SIZE=-1>you might encounter.</FONT>
 * 
 * <P>
 * <U><FONT SIZE=-1>Examples of a Command-lines:</FONT></U>
 * 
 * <P>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java SignalOpps
 * -add C:\pictures\test1.dat C:\pictures\test2.ppm
 * C:\Output\out_test.dat</FONT></FONT>
 * 
 * <P>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java SignalOpps
 * -mulitiplyconst C:\pictures\test1.dat 5 C:\Output\out_test.dat
 * -debug</FONT></FONT>
 * 
 * <P>
 * <FONT FACE="Courier New,Courier"><FONT SIZE=-1>&nbsp;&nbsp; java SignalOpps
 * -clip C:\pictures\test1.dat 40 150 C:\Output\out_test.dat</FONT></FONT>
 */
public class SignalOps {
	/** Debug state flag. */
	private static boolean debug;

	/** Command line parameters. */
	private static String[] param;

	/** main method */
	public static void main(String[] arsv) {
		try {
			int last = 0;
			debug = false;
			param = arsv;
			String op = arsv[0];
			jigl.signal.Signal sig3 = null;
			// int outfile=0;
			if (arsv[arsv.length - 1].equals("-debug")) {
				debug = true;
				last = arsv.length - 2;
			} else
				last = arsv.length - 1;

			if (op.equals("-add"))
				sig3 = basic(0);
			else if (op.equals("-subtract"))
				sig3 = basic(1);
			else if (op.equals("-multiply"))
				sig3 = basic(2);
			else if (op.equals("-divide"))
				sig3 = basic(3);
			else if (op.equals("-addconst"))
				sig3 = basicConst(0);
			else if (op.equals("-subtractconst"))
				sig3 = basicConst(1);
			else if (op.equals("-multiplyconst"))
				sig3 = basicConst(2);
			else if (op.equals("-divideconst"))
				sig3 = basicConst(3);
			else if (op.equals("-clear"))
				sig3 = clear();
			else if (op.equals("-clip"))
				sig3 = clip();
			else if (op.equals("-bytesize"))
				sig3 = byteSize();
			else if (op.equals("-diff"))
				sig3 = basic(4);
			else {
				throw new InvalidCommandLineException();
			}

			// create a new SignalOutputStream
			SignalOutputStream os = new SignalOutputStream(arsv[last]);
			os.write(sig3);
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/** Utility routine to open a signal file. */
	private static jigl.signal.Signal signalOpen(int file) throws IOException,
			IllegalPBMFormatException, SignalNotSupportedException {
		Signal signal = null;

		if (debug == true)
			System.out.println("Opening file: " + param[file]);
		SignalInputStream is = new SignalInputStream(param[file]);
		signal = is.read();
		is.close();

		if (debug == true) {
			if (signal instanceof DiscreteSignal) {
				System.out.println("Gray signal");
			} else if (signal instanceof RealSignal) {
				System.out.println("Real signal");
			} else if (signal instanceof ComplexSignal) {
				System.out.println("Complex signal");
			} else {
				System.out.println("unknown signal type");
			}
		}
		return signal;

	}

	/**
	 * Utility routine to implement basic operations of addition, subtraction,
	 * multiplication and division between two signal.
	 */
	private static Signal basic(int which) throws IncompatibleSignalException,
			SignalNotSupportedException, ColorModelNotSupportedException,
			ColorModelUnknownException, IllegalPBMFormatException, IOException {

		// jigl.signal.Signal signal3=null;
		jigl.signal.Signal signal1 = null;
		jigl.signal.Signal signal2 = null;
		DiscreteSignal ds1 = null;
		DiscreteSignal ds2 = null;
		ComplexSignal cs1 = null;
		ComplexSignal cs2 = null;
		RealSignal rs1 = null;
		RealSignal rs2 = null;

		signal1 = signalOpen(1);
		signal2 = signalOpen(2);

		// Convert to the appropriate type
		if (signal1 instanceof DiscreteSignal)
			ds1 = SignalConverter.toDiscrete(signal1);
		if (signal2 instanceof DiscreteSignal)
			ds2 = SignalConverter.toDiscrete(signal2);
		if (signal1 instanceof RealSignal)
			rs1 = SignalConverter.toReal((RealSignal) signal1);
		if (signal2 instanceof RealSignal)
			rs2 = SignalConverter.toReal((RealSignal) signal2);
		if (signal1 instanceof ComplexSignal)
			cs1 = SignalConverter.toComplex(signal1);
		if (signal2 instanceof ComplexSignal)
			cs2 = SignalConverter.toComplex(signal2);

		if ((ds1 != null) && (ds2 != null)) {
			if (debug == true)
				System.out.println("Both signals are DiscreteSignals");
			if (which == 0)
				ds1.add(ds2);
			else if (which == 1)
				ds1.subtract(ds2);
			else if (which == 2)
				ds1.multiply(ds2);
			else if (which == 3)
				ds1.divide(ds2);
			else if (which == 4)
				ds1.diff(ds2);
			return ds1;
		}

		else if ((ds1 != null) && (cs2 != null)) {
			if (debug == true)
				System.out
						.println("Warning: First signal converted from DiscreteSignal to ComplexSignal");
			cs1 = SignalConverter.toComplex(signal1);
			if (which == 0)
				cs1.add(cs2);
			else if (which == 1)
				cs1.subtract(cs2);
			else if (which == 2)
				cs1.multiply(cs2);
			else if (which == 3)
				cs1.divide(cs2);
			else if (which == 4)
				cs1.diff(cs2);
			return cs1;
		} else if ((ds1 != null) && (rs2 != null)) {
			if (debug == true)
				System.out
						.println("Warning: First signal converted from DiscreteSignal to RealSignal");
			rs1 = SignalConverter.toReal(signal1);
			if (which == 0)
				rs1.add(rs2);
			else if (which == 1)
				rs1.subtract(rs2);
			else if (which == 2)
				rs1.multiply(rs2);
			else if (which == 3)
				rs1.divide(rs2);
			else if (which == 4)
				rs1.diff(rs2);
			return rs1;
		}

		else if ((cs1 != null) && (cs2 != null)) {
			if (debug == true)
				System.out.println("Both signals are ComplexSignals");
			if (which == 0)
				cs1.add(cs2);
			else if (which == 1)
				cs1.subtract(cs2);
			else if (which == 2)
				cs1.multiply(cs2);
			else if (which == 3)
				cs1.divide(cs2);
			else if (which == 4)
				cs1.diff(cs2);
			return cs1;
		}

		else if ((cs1 != null) && (ds2 != null)) {
			if (debug == true)
				System.out
						.println("Warning: Second signal converted from DiscreteSignal to ComplexSignal");
			cs2 = SignalConverter.toComplex(signal2);
			if (which == 0)
				cs1.add(cs2);
			else if (which == 1)
				cs1.subtract(cs2);
			else if (which == 2)
				cs1.multiply(cs2);
			else if (which == 3)
				cs1.divide(cs2);
			else if (which == 4)
				cs1.diff(cs2);
			return cs1;
		}

		else if ((cs1 != null) && (rs2 != null)) {
			if (debug == true)
				System.out
						.println("Warning: Second signal converted from RealSignal to ComplexSignal");
			cs2 = SignalConverter.toComplex(signal2);
			if (which == 0)
				cs1.add(cs2);
			else if (which == 1)
				cs1.subtract(cs2);
			else if (which == 2)
				cs1.multiply(cs2);
			else if (which == 3)
				cs1.divide(cs2);
			else if (which == 4)
				cs1.diff(cs2);
			return cs1;
		}

		else {
			throw new IncompatibleSignalException();
		}

	}

	/************************************************************************************************************************
	 * /*********************************************Constant Basic Functions
	 * ************************************************* /
	 ************************************************************************************************************************/
	/**
	 * Utility routine to implement basic operations between a signal and a
	 * constant.
	 */
	private static Signal basicConst(int which) throws IncompatibleSignalException,
			ColorModelNotSupportedException, ColorModelUnknownException, IllegalPBMFormatException,
			IOException, SignalNotSupportedException {

		jigl.signal.Signal signal1 = null;
		DiscreteSignal ds1 = null;
		ComplexSignal cs1 = null;
		RealSignal rs1 = null;

		signal1 = signalOpen(1);

		// Convert to the appropriate type
		if (signal1 instanceof DiscreteSignal)
			ds1 = SignalConverter.toDiscrete(signal1);
		if (signal1 instanceof RealSignal)
			rs1 = SignalConverter.toReal((RealSignal) signal1);
		if (signal1 instanceof ComplexSignal)
			cs1 = SignalConverter.toComplex(signal1);

		if (ds1 != null) {
			Integer int_val = Integer.valueOf(param[2]);
			int val = int_val.intValue();
			if (debug == true)
				System.out.println("Adding " + val + " to a DiscreteSignal");
			if (which == 0)
				ds1.add(val);
			else if (which == 1)
				ds1.subtract(val);
			else if (which == 2)
				ds1.multiply(val);
			else if (which == 3)
				ds1.divide(val);
			return ds1;
		}

		else if (rs1 != null) {
			Float float_val = Float.valueOf(param[2]);
			float val = float_val.floatValue();
			if (debug == true)
				System.out.println("Adding " + val + " to a RealSignal");
			if (which == 0)
				rs1.add(val);
			else if (which == 1)
				rs1.subtract(val);
			else if (which == 2)
				rs1.multiply(val);
			else if (which == 3)
				rs1.divide(val);
			return rs1;
		}

		else if (cs1 != null) {
			Float f_val1 = Float.valueOf(param[2]);
			Float f_val2 = Float.valueOf(param[3]);
			float val1 = f_val1.floatValue();
			float val2 = f_val2.floatValue();
			if (debug == true)
				System.out.println("Adding " + val1 + ", " + val2 + " to a ComplexSignal");
			if (which == 0)
				cs1.add(val1, val2);
			else if (which == 1)
				cs1.subtract(val1, val2);
			else if (which == 2)
				cs1.multiply(val1, val2);
			else if (which == 3)
				cs1.divide(val1, val2);
			return cs1;
		} else {
			throw new IncompatibleSignalException();
		}

	}

	/************************************************************************************************************************
	 * /*********************************************Other Basic Functions
	 * ************************************************* /
	 ************************************************************************************************************************/

	/** utility routine to implement byteSize operation. */
	private static Signal byteSize() throws SignalNotSupportedException,
			ColorModelNotSupportedException, ColorModelUnknownException, IllegalPBMFormatException,
			IOException {

		jigl.signal.Signal signal1 = null;
		DiscreteSignal ds1 = null;
		// ComplexSignal cs1=null;
		RealSignal rs1 = null;

		signal1 = signalOpen(0);

		// Convert to the appropriate type
		if (signal1 instanceof DiscreteSignal)
			ds1 = SignalConverter.toDiscrete(signal1);
		if (signal1 instanceof RealSignal)
			rs1 = SignalConverter.toReal((RealSignal) signal1);
		// if (signal1 instanceof ComplexSignal)
		// cs1=SignalConverter.toComplex(signal1);

		if (ds1 != null) {
			if (debug == true)
				System.out.println("Performing ByteSize Operation on a DiscreteSignal");
			ds1.byteSize();
			return ds1;
		}

		else if (rs1 != null) {
			if (debug == true)
				System.out.println("Performing ByteSize Operation on a RealSignal");
			rs1.byteSize();
			return rs1;
		}

		// else if (cs1!=null) {
		// cs1.byteSize();
		// return cs1;
		// }
		else {
			System.out.println("Invalid signal type");
			System.exit(0);
		}

		return null;

	}

	/** Utility routine to implement clip operation. */
	private static Signal clip() throws SignalNotSupportedException,
			ColorModelNotSupportedException, ColorModelUnknownException, IllegalPBMFormatException,
			IOException {

		jigl.signal.Signal signal1 = null;
		DiscreteSignal ds1 = null;
		// ComplexSignal cs1=null;
		RealSignal rs1 = null;

		signal1 = signalOpen(1);

		// Convert to the appropriate type

		if (signal1 instanceof DiscreteSignal)
			ds1 = SignalConverter.toDiscrete(signal1);
		if (signal1 instanceof RealSignal)
			rs1 = SignalConverter.toReal((RealSignal) signal1);
		// if (signal1 instanceof ComplexSignal)
		// cs1=SignalConverter.toComplex(signal1);

		if (ds1 != null) {
			Integer int_val1 = Integer.valueOf(param[2]);
			Integer int_val2 = Integer.valueOf(param[3]);
			int val1 = int_val1.intValue();
			int val2 = int_val2.intValue();
			if (debug == true)
				System.out
						.println("Performing Clip Operation on a DiscreteSignal between the values of "
								+ val1 + " and " + val2);
			ds1.clip(val1, val2);
			return ds1;
		}

		else if (rs1 != null) {
			Integer int_val1 = Integer.valueOf(param[2]);
			Integer int_val2 = Integer.valueOf(param[3]);
			int val1 = int_val1.intValue();
			int val2 = int_val2.intValue();
			if (debug == true)
				System.out
						.println("Performing Clip Operation on a RealSignal between the values of "
								+ val1 + " and " + val2);
			rs1.clip(val1, val2);
			return rs1;
		}

		// else if (cs1!=null) {
		// cs1.clip();
		// return cs1;
		// }
		else
			throw new SignalNotSupportedException();

	}

	/** Utility operation to do clear operation. */
	private static Signal clear() throws SignalNotSupportedException,
			ColorModelNotSupportedException, ColorModelUnknownException, IllegalPBMFormatException,
			IOException {

		jigl.signal.Signal signal1 = null;
		DiscreteSignal ds1 = null;
		// ComplexSignal cs1=null;
		RealSignal rs1 = null;

		signal1 = signalOpen(1);

		// Convert to the appropriate type

		if (signal1 instanceof DiscreteSignal)
			ds1 = SignalConverter.toDiscrete(signal1);
		if (signal1 instanceof RealSignal)
			rs1 = SignalConverter.toReal((RealSignal) signal1);
		// if (signal1 instanceof ComplexSignal)
		// cs1=SignalConverter.toComplex(signal1);

		Integer int_val1 = Integer.valueOf(param[1]);
		int val1 = int_val1.intValue();

		if (ds1 != null) {
			if (debug == true)
				System.out.println("Performing Clear Operation on a DiscreteSignal with the value "
						+ val1);
			ds1.clear(val1);
			return ds1;
		}

		else if (rs1 != null) {
			if (debug == true)
				System.out.println("Performing Clear Operation on a RealSignal with the value "
						+ val1);
			rs1.clear(val1);
			return rs1;
		}

		// else if (cs1!=null) {
		// cs1.clip();
		// return cs1;
		// }
		else {
			throw new SignalNotSupportedException();
		}

	}

}
