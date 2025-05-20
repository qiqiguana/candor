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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import jigl.signal.DiscreteSignal;
import jigl.signal.RealSignal;
import jigl.signal.Signal;
import jigl.signal.SignalNotSupportedException;

/**
 * SignalInputStream retrieves signals from a file into a BufferedInputOutput object. Supports
 * DAT(only on local machine), PDS_RAW, PRS_RAW. <br>
 * 
 * <pre>
 * PDS_RAW ( .pds) --> DiscreteSignal
 * 	PRS_RAW (.prs)--> RealSignal
 * 	DAT (.dat)--> RealSignal
 * </pre>
 */
public class SignalInputStream {

	/** Store signal contents */
	protected BufferedInputStream data;
	/** Number of dimensions the signal has */
	protected int ndims;
	/** Width of the signal */
	protected int X;

	/**
	 * TODO: Make this an enum Type of the file:
	 * <P>
	 * <DT>UNKNOWN = 0</DT>
	 * <DT>PDS_ASCII = 1 -- not Implemented</DT>
	 * <DT>PRS_ASCII = 2 -- not Implemented</DT>
	 * <DT>PDS_RAW = 3</DT>
	 * <DT>PRS_RAW = 4</DT>
	 * <DT>DAT_FILE =5</DT>
	 */
	private int type;
	private static final int UNKNOWN = 0;
	private static final int PDS_ASCII = 1; // not Implemented;
	private static final int PRS_ASCII = 2; // not Implemented;
	private static final int PDS_RAW = 3;
	private static final int PRS_RAW = 4;
	private static final int DAT_FILE = 5;
	/** maximum value */
	protected int maxval;
	/** maximum value */
	protected float maxvalf;

	/**
	 * Opens a SignalInputStream from a filename
	 * 
	 * @param fn the filename to open
	 */
	public SignalInputStream(String fn) throws FileNotFoundException, SignalNotSupportedException,
			IOException {

		if (fn.endsWith(".dat"))// one value per line
		{
			ndims = 1;
			data = new BufferedInputStream(new FileInputStream(fn));
			type = 5;
			X = 0;
			char c;
			do {
				c = readChar(data);
				if (c == '\n')
					X = X + 1;
			} while (c != '|');

			data = new BufferedInputStream(new FileInputStream(fn));
			maxvalf = 1;

		} else {
			ndims = 1;
			data = new BufferedInputStream(new FileInputStream(fn));
			type = readMagic();
			readHeader();
		}

	}

	/**
	 * Opens a SignalInputStream from a filename (on the web)
	 * 
	 * @param the complete URL
	 */
	public SignalInputStream(String url, int i) throws IOException, SignalNotSupportedException,
			MalformedURLException {

		URL ur = new URL(url);

		ndims = 1;
		data = new BufferedInputStream(ur.openStream());
		type = readMagic();
		readHeader();
	}

	/** Makes a new SignalInputStream from another SignalInputStream */
	public SignalInputStream(SignalInputStream stream) {
		data = stream.data;
		ndims = stream.ndims;
		X = stream.X;
		type = stream.type;
	}

	/** Returns the number of Dimensions that the signal has */
	public int ndims() {
		return ndims;
	}

	/** Returns the width of the signal */
	public int X() {
		return X;
	}

	/**
	 * Returns the type of signal
	 * <DL>
	 * <DL>
	 * <DT>UNKNOWN = 0</DT>
	 * <DT>PDS_ASCII = 1 -- not Implemented</DT>
	 * <DT>PRS_ASCII = 2 -- not Implemented</DT>
	 * <DT>PDS_RAW = 3</DT>
	 * <DT>PRS_RAW = 4</DT>
	 * <DT>DAT = 5</DT>
	 */
	public int type() {
		return type;
	}

	/** Closes the InputStream */
	public void close() throws IOException {
		data.close();
	}

	/** Gets the magic number and returns the type */
	public int readMagic() throws SignalNotSupportedException, IOException {

		int t;
		char c[] = new char[2];

		// get file type magic (first two bytes)

		c[0] = (char) readByte(data);
		c[1] = (char) readByte(data);

		String str = new String(c);

		// determine type
		if (str.equals("S1")) {
			t = PDS_ASCII;
		} else if (str.equals("S2")) {
			t = PRS_ASCII;
		} else if (str.equals("S3")) {
			t = PDS_RAW;
		} else if (str.equals("S4")) {
			t = PRS_RAW;
		} else {
			t = UNKNOWN;
			throw new SignalNotSupportedException();
		}

		return t;
	}

	/** Reads the file header: determines type, size and range of values */
	public void readHeader() throws SignalNotSupportedException, IOException {

		switch (type) {
		case PDS_ASCII:
		case PRS_ASCII:
		case PDS_RAW:
		case PRS_RAW:

			// get signal dimensions
			X = readInt(data);
			ndims = 1;

			// get data range (maximum value)
			if (type == PDS_RAW) {
				maxval = readInt(data);
			} else if (type == PRS_RAW) {
				maxvalf = readFloat(data);
			} else {
				maxval = 1;
			}
			break;
		default:
			throw new SignalNotSupportedException();
		}

	}

	/** Reads in the Signal */
	public Signal read() throws SignalNotSupportedException, IllegalPBMFormatException, IOException {

		// the different possible signal types to return
		DiscreteSignal ds = null;
		RealSignal rs = null;

		switch (type) {
		case PDS_RAW:
			ds = new DiscreteSignal(X);
			break;
		case PRS_RAW:
			rs = new RealSignal(X);
			break;
		case DAT_FILE:
			rs = new RealSignal(X);
			break;
		default:
			throw new SignalNotSupportedException();

		}

		int s;
		float sr;
		double t;
		// char c;
		// int value;
		// read signal data

		for (int x = 0; x < X; x++) {
			switch (type) {
			case PDS_RAW:
				s = readByte(data);
				if (maxval != 255) {
					s = fixDepth(s);
				}
				ds.set(x, s);
				break;
			case PRS_RAW:
				sr = readBytef(data);
				if (maxvalf != 255) {
					sr = fixDepth(sr);
				}
				rs.set(x, sr);
				break;
			case DAT_FILE:
				t = readFloat(data);
				sr = (float) t;
				rs.set(x, sr);
				break;
			default:
				throw new SignalNotSupportedException();
			}
		}

		// return the right signal type
		switch (type) {
		case PDS_RAW:
			return ds;
		case PRS_RAW:
			return rs;
		case DAT_FILE:
			// rs.byteSize();
			return rs;
		default:
			throw new SignalNotSupportedException();

		}

	}

	// functions imported from JPM library
	//
	/** Utility routine to read a byte. */
	private int readByte(InputStream data) throws IOException {
		int b = data.read();
		return b;
	}

	/** Utility routine to read a float. */
	private float readBytef(InputStream data) throws IOException {
		float b = data.read();
		return b;
	}

	/** Utility routine to read an ASCII integer, ignoring comments. */
	private int readInt(InputStream data) throws IOException {
		char c;
		int i;

		c = readNonwhiteChar(data);

		if (!((c >= '0' && c <= '9') || c == '-'))
			throw new IOException("junk in file where integer should be");

		i = 0;

		if (c == '-') {
			c = readChar(data);
			do {
				i = i * 10 + c - '0';
				c = readChar(data);
			} while (c >= '0' && c <= '9');
			i = -i;
		} else {
			do {
				i = i * 10 + c - '0';
				c = readChar(data);
			} while (c >= '0' && c <= '9');
		}

		return i;
	}

	/** Utility routine to read a float from an InputStream object. */
	private float readFloat(InputStream data) throws IOException {
		char c;
		double i, cons;
		boolean flag = true;

		cons = (float) 0.1;
		c = readNonwhiteChar(data);

		if (!((c >= '0' && c <= '9') || c == '-'))
			throw new IOException("junk in file where integer should be: " + c);

		i = 0;
		if (c == '-') {
			c = readChar(data);
			do {
				i = i * 10 + c - '0';
				c = readChar(data);
			} while (c >= '0' && c <= '9');
			i = -i;
			flag = false;

		} else {
			do {
				i = i * 10 + c - '0';
				c = readChar(data);
			} while (c >= '0' && c <= '9');
		}

		if (c == ('.')) {
			c = readChar(data);
			do {
				if (flag == true) {
					i = i + cons * (c - '0');
				} else
					i = i + -(cons * (c - '0'));
				c = readChar(data);
				cons = cons * 0.1;
			} while (c >= '0' && c <= '9');
		}

		return (float) i;
	}

	/** Utility routine to read a character, ignoring comments. */
	private char readChar(InputStream data) throws IOException {
		char c;
		int num;

		num = readByte(data);
		if (num == -1)
			return '|';
		c = (char) num;
		if (c == '#') {
			do {
				c = (char) readByte(data);
			} while (c != '\n' && c != '\r');
		}

		return c;
	}

	/** Utility routine to read the first non-whitespace character. */
	private char readNonwhiteChar(InputStream data) throws IOException {
		char c;

		do {
			c = readChar(data);
		} while (c == ' ' || c == '\t' || c == '\n' || c == '\r');

		return c;
	}

	/** Utility routine to rescale a pixel value from a non-eight-bit maxval. */
	private int fixDepth(int p) {
		return (p * 255 + maxval / 2) / maxval;
	}

	/** Utility routine to rescale a pixel value from a non-eight-bit maxval. */
	private float fixDepth(float p) {
		return (p * 255 + maxvalf / 2) / maxval;
	}

	// /**Current bytes value*/
	// private int bitshift = -1;
	// /**Current bit position in the current byte.*/
	// private int bits;
	//
	// /** Utility routine to read a bit, packed eight to a byte, big-endian.*/
	// private boolean readBit(InputStream in) throws IOException {
	// if (bitshift == -1) {
	// bits = readByte(in);
	// bitshift = 7;
	// }
	// boolean bit = (((bits >> bitshift) & 1) != 0);
	// --bitshift;
	// return bit;
	// }

}
