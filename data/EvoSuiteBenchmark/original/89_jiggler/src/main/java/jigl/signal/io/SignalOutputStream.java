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
package jigl.signal.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jigl.signal.DiscreteSignal;
import jigl.signal.RealSignal;
import jigl.signal.UnknownSignalTypeException;

/**
 * SignalOutputStream outputs signals to a file that JIGL can read. To accommodate other programs to
 * display signals, JIGL also support the ".dat" extension. Normally JIGL will automatically detect
 * what type to output (DiscreteSignal, RealSignal, etc.) However in order to output a DAT file, the
 * filename passed to the constructor MUST have a ".dat" extension. For example you might use the
 * name -- <i>myfile.dat</i>
 * <p>
 * Supports only DiscreteSignal and RealSignal. <br>
 * 
 * <pre>
 * DiscreteSignal --> PDS_RAW ( .pds) or DAT (.dat)
 * 	RealSignal --> PRS_RAW (.prs) or DAT
 * </pre>
 * <p>
 * To write a Signal object to a file, first call <code>open(String)</code>, then call
 * <code>write(Signal)</code> and finally call <code>close()</code>.
 */

public class SignalOutputStream {

	/** Data to dump */
	protected BufferedOutputStream data;

	/** Number of dimensions */
	protected int ndims;
	/** Height of the signal */
	protected int X;
	/** Flag for DAT file */
	private boolean toDat = false;

	/** Default SignalOutputStream, does nothing */
	public SignalOutputStream() {
	}

	/**
	 * Creates an SignalOutputStream from a filename
	 * 
	 * @param fn filename
	 */
	public SignalOutputStream(String fn) throws IOException {
		FileOutputStream f = new FileOutputStream(fn);
		data = new BufferedOutputStream(f);
		if (fn.endsWith(".dat"))
			toDat = true;
	}

	/** Creates an SignalOutputStream from another SignalOutputStream (shallow copy) */
	public SignalOutputStream(SignalOutputStream stream) {
		data = stream.data;
		ndims = stream.ndims;
		X = stream.X;
	}

	/** Returns the number of dimensions */
	public int ndims() {
		return ndims;
	}

	/** Returns the width */
	public int X() {
		return X;
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

	/**
	 * Closes the file
	 * 
	 * @param filename
	 */
	public void close() throws IOException {
		data.close();
	}

	/**
	 * Writes a JIGL signal to a file
	 * 
	 * @param im JIGL signal
	 */
	public void write(jigl.signal.Signal im) throws UnknownSignalTypeException, IOException {
		if (im instanceof DiscreteSignal) {
			write((DiscreteSignal) im);
		} else if (im instanceof RealSignal) {
			write((RealSignal) im);
		} else {
			throw new UnknownSignalTypeException();
		}
	}

	/**
	 * Writes a DiscreteSignal to a PDS_RAW file
	 * 
	 * @param im the DiscreteSignal
	 */
	public void write(DiscreteSignal s) throws IOException {

		if (toDat == false) {
			X = s.length();

			// convert to byte size
			DiscreteSignal tmps = (DiscreteSignal) s.copy();
			tmps.byteSize();

			// write PDS in raw format
			writeRawPDSHeader(X);
			for (int x = 0; x < X; x++) {
				data.write((byte) tmps.get(x));
			}
		} else//DAT file
		{
			Integer f = null;
			for (int x = 0; x < s.length(); x++) {
				f = new Integer(s.get(x));
				writeString(data, f.toString() + "\n");
			}
		}
	}

	/** Writes a RealSignal to a PRS_RAW file */
	public void write(RealSignal s) throws IOException {

		if (toDat == false) {

			X = s.length();

			// convert to byte size
			RealSignal tmps = (RealSignal) s.copy();
			tmps.byteSize();

			// write PRS in raw format

			writeRawPRSMHeader(X);
			for (int x = 0; x < X; x++) {
				data.write((byte) tmps.get(x));
			}
		} else {//DAT file
			Float f = null;
			for (int x = 0; x < s.length(); x++) {
				f = new Float(s.get(x));
				writeString(data, f.toString() + "\n");
			}
		}

	}

	/** Utility routine to write the header of PDS_RAW file. */
	private void writeRawPDSHeader(int width) throws IOException {
		writeString(data, "S3\n");
		writeString(data, width + "\n");
		writeString(data, "255\n");
	}

	/** Utility routine to write the header of PRS_RAW file. */
	private void writeRawPRSMHeader(int width) throws IOException {
		writeString(data, "S4\n");
		writeString(data, width + "\n");
		writeString(data, "255\n");
	}

	/** Write a string to an OutputStream object. */
	public static void writeString(OutputStream out, String str) throws IOException {

		out.write(str.getBytes());

	}

}
