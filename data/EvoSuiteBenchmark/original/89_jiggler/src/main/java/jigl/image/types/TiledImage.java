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
package jigl.image.types;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

import jigl.image.ROI;

/** Allows raw PPM files to be opened without being completely in memory. */
public class TiledImage {
	/** A RandomAccessFile to store the content of the image. */
	private RandomAccessFile m_file;

	private Tile[] m_cache;// Tile?

	/** Width of image */
	private int m_X;

	/** Height of image */
	private int m_Y;

	// /**Maximum value, default is 255.*/
	// private int m_maxVal;
	/** The length of the file header */
	private long m_offset;// offset in file to data

	/** Tile size in x direction */
	private int m_tileSizeX;

	/** tile size in y direction. */
	private int m_tileSizeY;

	/** Number of tiles in x direction */
	private int m_numTilesX;

	/** Number of tiles in y direction */
	// private int m_numTilesY;

	/** Number of cache misses. */
	private int m_cacheMisses = 0;

	/** Number of tiles in cache. */
	private static int m_nTilesInCache = 10;

	// these are just for optimization:
	/** For optimization */
	private int prevX = -1;

	/** For optimization */
	private int prevY = -1;

	/** For optimization */
	private int prevTile = -1;

	/** For optimization */
	private int prevOffset = -1;

	/** For optimization */
	// private Tile tempTile = null;

	/**
	 * Constructs a TiledImage object from a color image of the specified size.
	 * 
	 * @param filename name of new PPM image file.
	 * @param imageSizeX size of new image.
	 * @param imageSizeY size of new image.
	 * @param tileSizeX size of tiles to use.
	 * @param tileSizeY size of tiles to use.
	 * @throws IOException if an IO error occurs.
	 */
	public TiledImage(String filename, int imageSizeX, int imageSizeY, int tileSizeX, int tileSizeY)
			throws IOException {
		m_file = new RandomAccessFile(filename, "rw");

		byte[] header;
		String str = "P6 " + String.valueOf(imageSizeX) + " " + String.valueOf(imageSizeY)
				+ " 255\n";

		header = str.getBytes();

		// System.out.println("header: " + (char)header[0] + (char)header[1] +
		// (char)header[2] + (char)header[3]
		// + (char)header[4] + (char)header[5] + (char)header[6] +
		// (char)header[7]);

		m_X = imageSizeX;
		m_Y = imageSizeY;

		// m_maxVal = 255;

		m_offset = header.length;

		m_file.setLength((m_offset + m_X * m_Y * 3));
		m_file.write(header);

		m_cache = new Tile[m_nTilesInCache];

		for (int i = 0; i < m_nTilesInCache; i++) {
			m_cache[i] = null;
		}

		m_tileSizeX = tileSizeX;
		m_tileSizeY = tileSizeY;

		m_numTilesX = (int) ((float) m_X / (float) m_tileSizeX + 1.0);// round
		// up!
		// m_numTilesY = (int)((float)m_Y/(float)m_tileSizeY + 1.0);//round up!
	}

	/**
	 * Constructs a TiledImage object from the existing raw PPM file specified by
	 * <code>ppmfilename</code> and reads the header information.
	 * 
	 * @param ppmfilename file to open.
	 * @param tileSizeX size of tiles to use.
	 * @param tileSizeY size of tiles to use.
	 * @throws FileNotFoundException if the file does not exist, cannot be opened.
	 * @throws IOException if the file is not a valid raw PPM image.
	 */
	public TiledImage(String ppmfilename, int tileSizeX, int tileSizeY)
			throws FileNotFoundException, IOException {
		// header must have "P6"
		m_file = new RandomAccessFile(ppmfilename, "rw");
		System.out.println("using the new one");
		char[] type = new char[2];

		type[0] = (char) m_file.readByte();
		type[1] = (char) m_file.readByte();

		String str = new String(type);

		// make sure type is PPM raw
		if (!str.equals("P6")) {
			throw new IOException();
		}

		skipWhite(m_file);// also skips comments

		// get the width of the image
		m_X = readInt(m_file);
		skipWhite(m_file);

		// get the height of the image
		m_Y = readInt(m_file);
		skipWhite(m_file);

		// get data range (maximum value)
		// m_maxVal = readInt(m_file);
		skipWhite(m_file);

		// remember the offset to the actual data
		m_offset = m_file.getFilePointer();

		m_cache = new Tile[m_nTilesInCache];

		for (int i = 0; i < m_nTilesInCache; i++) {
			m_cache[i] = null;
		}

		m_tileSizeX = tileSizeX;
		m_tileSizeY = tileSizeY;

		m_numTilesX = (int) ((float) m_X / (float) m_tileSizeX + 1.0);// round
		// up!
		// m_numTilesY = (int)((float)m_Y/(float)m_tileSizeY + 1.0);//round up!
		System.out.println("finished constructor");
	}

	/**
	 * Returns the width of the image.
	 * 
	 * @return width of image
	 */
	public int X() {
		return m_X;
	}

	/**
	 * Returns the height of the image.
	 * 
	 * @return height of image
	 */
	public int Y() {
		return m_Y;
	}

	/**
	 * Returns the sub-image specified by <code>roi</code>. Throws IOException if <code>roi</code>
	 * goes outside the image.
	 * 
	 * @param roi Region of Interest to return.
	 * @throws IOException if <code>roi</code> is outside the image.
	 */
	public ColorImage getImage(ROI roi) throws IOException {
		Date start = new Date();

		m_cacheMisses = 0;

		ColorImage image = new ColorImage(roi.X(), roi.Y());

		Integer[] pixel = new Integer[3];

		for (int y = roi.uy(), newY = 0; y <= roi.ly(); y++, newY++) {
			for (int x = roi.ux(), newX = 0; x <= roi.lx(); x++, newX++) {
				pixel = get(x, y);
				image.set(newX, newY, pixel);
			}
		}// 1 second on 500X500 image with 500X500 tiles

		/*
		 * //buffer will be big enough to hold one "row" of data in the x
		 * direction in the roi // byte[] buffer = new byte[3*(m_X*roi.Y() +
		 * roi.X())]; byte[] buffer = new byte[3*roi.X()];
		 * 
		 * // int skipAmount; // m_file.seek(m_offset + 3*(roi.uy()*m_X +
		 * roi.ux())); // m_file.readFully(buffer);
		 * 
		 * //stream is easily accessed, and returns bytes as integers from 0 to
		 * 255 //instead of treating them as signed numbers (-128 - 128)
		 * ByteArrayInputStream stream; // ByteArrayInputStream stream = new
		 * ByteArrayInputStream(buffer); // skipAmount = 3*(m_X - roi.X());
		 * 
		 * for (int y = roi.uy(), newY = 0; y < roi.ly(); y++, newY++) {
		 * m_file.seek(m_offset + 3*(y*m_X + roi.ux()));
		 * m_file.readFully(buffer); stream = new ByteArrayInputStream(buffer);
		 * 
		 * for (int x = roi.ux(), newX = 0; x < roi.lx(); x++, newX++) {
		 * pixel[0] = stream.read(); pixel[1] = stream.read(); pixel[2] =
		 * stream.read(); image.set(newX,newY,pixel); } //
		 * stream.skip(skipAmount); }
		 */
		// .56 seconds on 500X500 image

		Date stop = new Date();

		System.out.println("getImage took "
				+ Double.toString((double) (stop.getTime() - start.getTime()) / 1000)
				+ "seconds, with " + m_cacheMisses + " misses");
		return image;
	}

	/**
	 * Closes the file. this must be called to insure that all data is written back to the file
	 */
	public void close() throws IOException {
		flush();
		m_file.close();
	}

	/** Flushes all data to the file. writes out any dirty tiles */
	public void flush() throws IOException {
		for (int i = 0; i < m_cache.length; i++) {
			if (m_cache[i] != null) {
				if (m_cache[i].m_bDirty) {
					writeTileToFile(m_cache[i]);
				}
			}
		}
	}

	/** Set the color at pixel(x,y). */
	public void set(int x, int y, int[] val) throws IOException, ArrayIndexOutOfBoundsException {
		if (x < 0 || x >= m_X || y < 0 || y >= m_Y)
			throw new ArrayIndexOutOfBoundsException();

		Tile tempTile = getPreparedTile(x, y);

		tempTile.write(val[0]);
		tempTile.write(val[1]);
		tempTile.write(val[2]);

		tempTile.m_bDirty = true;
	}

	/** Return the color at (x,y) */
	public Integer[] get(int x, int y) throws IOException, ArrayIndexOutOfBoundsException {
		// System.out.println("get(" + x + "," + y + ")");
		if (x < 0 || x >= m_X || y < 0 || y >= m_Y)
			throw new ArrayIndexOutOfBoundsException();

		Integer[] pixel = new Integer[3];

		Tile tempTile = getPreparedTile(x, y);

		// System.out.println("pixel[0]:" + (pixel[0] = tempTile.read()));
		// System.out.println("pixel[1]:" + (pixel[1] = tempTile.read()));
		// System.out.println("pixel[2]:" + (pixel[2] = tempTile.read()));
		pixel[0] = tempTile.read();
		pixel[1] = tempTile.read();
		pixel[2] = tempTile.read();
		return pixel;
	}

	/**
	 * makes sure that the tile containing (x,y) is in the cache (writes out old tile if it is
	 * dirty) and sets the stream pointer to the (x,y) position in the stream.
	 */
	private Tile getPreparedTile(int x, int y) throws IOException {
		int tileNum = getTileNum(x, y);
		int cacheIndex = getTileIndex(tileNum);

		if (m_cache[cacheIndex] == null) {
			m_cache[cacheIndex] = new Tile();
		}

		if (m_cache[cacheIndex].m_nID != tileNum) {
			m_cacheMisses++;
			getTileInfoFromFile(m_cache[cacheIndex], tileNum);
		}

		int offset = getPixelOffsetInTile(x, y, tileNum);

		if (m_cache[cacheIndex].m_nPos != offset) {
			m_cache[cacheIndex].m_nPos = offset;
		}

		return m_cache[cacheIndex];
	}

	/** Utility routine to get the tile index in the cache. */
	private int getTileIndex(int tilenum) {
		return tilenum % m_cache.length;// (m_cache.length == 9)
	}

	// /**Utility routine to get the tile index in the cache.*/
	// private int getTileIndex(int x, int y)
	// {
	// return getTileIndex(getTileNum(x,y));
	// }

	/** Utility routine. */
	private int getTileNum(int x, int y) {
		return y / m_tileSizeY * m_numTilesX + x / m_tileSizeX;
	}

	/** Utility routine. */
	private int getTileOffsetX(int tileNum) {
		return (tileNum % m_numTilesX) * m_tileSizeX;
	}

	/** Utility routine. */
	private int getTileOffsetY(int tileNum) {
		return (tileNum / m_numTilesX) * m_tileSizeY;
	}

	/** Utility routine. */
	private int getPixelOffsetInTile(int x, int y, int tileNum) {
		// offset of the 0,0 position of the tile relative to the
		// 0,0 position of the image is:
		// x:(tileNum%m_numTilesX)*m_tileSizeX;
		// y:(tileNum/m_numTilesX)*m_tileSizeY;

		// offset of the x,y point in the tile is:
		// x:(x - (tileNum%m_numTilesX)*m_tileSizeX);
		// y:(y - (tileNum/m_numTilesX)*m_tileSizeY);

		// optimization. if we're getting the next tile, just
		// increment the previous offset by 3
		if (prevX == x - 1 && prevY == y && prevTile == tileNum) {
			prevX++;
			prevOffset += 3;
		} else// compute the offset
		{
			prevX = x;
			prevY = y;
			prevTile = tileNum;
			prevOffset = 3 * ((y - (tileNum / m_numTilesX) * m_tileSizeY) * m_tileSizeX + (x - (tileNum % m_numTilesX)
					* m_tileSizeX));
		}

		return prevOffset;
	}

	/** Utility routine to write a Tile object to file. */
	private void writeTileToFile(Tile tile) throws IOException {
		int offsetX = getTileOffsetX(tile.m_nID);
		int offsetY = getTileOffsetY(tile.m_nID);

		int nMaxX, nMaxY;

		if (tile.m_bPartialX)
			nMaxX = tile.m_nMaxX;
		else
			nMaxX = m_tileSizeX;

		if (tile.m_bPartialY)
			nMaxY = tile.m_nMaxY;
		else
			nMaxY = m_tileSizeY;

		for (int y = 0; y < nMaxY; y++) {
			m_file.seek(m_offset + 3 * ((y + offsetY) * m_X + offsetX));
			m_file.write(tile.m_data, 3 * y * m_tileSizeX, 3 * nMaxX);
		}

		tile.m_bDirty = false;
	}

	/** Utility routine. */
	private void getTileInfoFromFile(Tile tile, int tileNum) throws IOException {
		int offsetY = getTileOffsetY(tileNum);
		int offsetX = getTileOffsetX(tileNum);

		if (tile.m_bDirty) {// write tile out to file
			writeTileToFile(tile);
		}

		int nMaxX, nMaxY;

		if (offsetX + m_tileSizeX > m_X) {// tile goes off right side of image
			tile.m_bPartialX = true;
			tile.m_nMaxX = m_X - offsetX - 1;
			nMaxX = tile.m_nMaxX;
		} else {
			tile.m_bPartialX = false;
			nMaxX = m_tileSizeX;
		}

		if (offsetY + m_tileSizeY > m_Y) {// tile goes off bottom of image
			tile.m_bPartialY = true;
			tile.m_nMaxY = m_Y - offsetY - 1;
			nMaxY = tile.m_nMaxY;
		} else {
			tile.m_bPartialY = false;
			nMaxY = m_tileSizeY;
		}

		tile.m_data = new byte[3 * m_tileSizeX * m_tileSizeY];
		tile.m_nID = tileNum;
		tile.m_nPos = 0;

		for (int y = 0; y < nMaxY; y++) {
			m_file.seek(m_offset + 3 * ((y + offsetY) * m_X + offsetX));
			m_file.readFully(tile.m_data, 3 * y * m_tileSizeX, 3 * nMaxX);
		}

	}

	/** Inner class Tile to store the content of a tile. */
	final class Tile {
		public byte[] m_data = null;
		public int m_nID = -1;
		public int m_nPos = -1;
		public boolean m_bDirty = false;
		public boolean m_bPartialX = false;
		public boolean m_bPartialY = false;
		public int m_nMaxX = -1;
		public int m_nMaxY = -1;

		public final int read() {
			if ((int) m_data[m_nPos] >= 0) {
				// System.out.println("1converting " + m_data[m_nPos] + " to " +
				// (int)m_data[m_nPos]);
				return (int) m_data[m_nPos++];
			} else {
				// System.out.println("2converting " + m_data[m_nPos] + " to " +
				// ((int)(-(m_data[m_nPos])) + 127));
				return (int) (m_data[m_nPos++]) + 256;
			}
		}

		public final void write(int val) {
			if (val >= 128) {
				m_data[m_nPos++] = (byte) (val - 256);
			} else {
				m_data[m_nPos++] = (byte) (val);
			}
		}
	}

	// will read up to 16 consecutive non-white characters and
	// attempt to convert it to an integer.
	private int readInt(RandomAccessFile file) throws IOException {
		char[] str = new char[16];

		char temp;

		int i = 0;

		long pos = file.getFilePointer();
		temp = (char) file.readByte();

		while (temp != ' ' && temp != '\n' && temp != '\r' && temp != '\t' && i < 16) {
			pos = file.getFilePointer();
			str[i++] = temp;
			temp = (char) file.readByte();
		}

		file.seek(pos);

		return Integer.parseInt(new String(str, 0, i));
	}

	// will skip all white-space characters and comments (lines starting with
	// #),
	// and leave the file pointer on the first non-white character on an
	// uncommented line
	private void skipWhite(RandomAccessFile file) throws IOException {
		long pos = file.getFilePointer();
		char c = (char) file.readByte();

		while (c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '#') {
			if (c == '#') {
				// System.out.println("skipping comment");
				file.readLine();
				pos = file.getFilePointer();
			} else {
				// System.out.println("skipping whitespace");
				pos = file.getFilePointer();
			}
			c = (char) file.readByte();
		}

		file.seek(pos);
	}

}
