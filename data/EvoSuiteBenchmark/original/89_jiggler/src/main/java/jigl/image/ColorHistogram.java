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
package jigl.image;

import jigl.image.types.ColorImage;

/**
 * A Color Histogram is a 3-Dimensional array that contains the count for a ColorImage.
 */
public class ColorHistogram {
	/** Store the data of histogram */
	protected int[][][] m_data;
	/** The number of precision bits used for red color */
	protected int m_rbits = 5;
	/** The number of precision bits used for green color */
	protected int m_gbits = 5;
	/** The number of precision bits used for blue color */
	protected int m_bbits = 5;

	/** The range for red color */
	private int m_rsize = 32;
	/** The range for green color */
	private int m_gsize = 32;
	/** The range for blue color */
	private int m_bsize = 32;

	/** Default constructor. 5 bits of precision are used for each dimension. (32X32X32 array) */
	public ColorHistogram(ColorImage image) {
		int a = 0;
		int b = 0;
		Integer[] val = new Integer[3];
		try {

			m_data = new int[m_rsize][m_gsize][m_bsize];
			for (int h = 0; h < m_rsize; h++) {
				for (int i = 0; i < m_gsize; i++) {
					for (int j = 0; j < m_bsize; j++) {
						m_data[h][i][j] = 0;
					}
				}
			}

			int rprec = 1 << (8 - m_rbits);
			int gprec = 1 << (8 - m_gbits);
			int bprec = 1 << (8 - m_bbits);

			for (int x = 0; x < image.X(); x++) {
				for (int y = 0; y < image.Y(); y++) {
					a = x;
					b = y;
					val = image.get(x, y);
					m_data[val[0] / rprec][val[1] / gprec][val[2] / bprec]++;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception: " + a + " " + b + " " + val[0] + " " + val[1] + " "
					+ val[2]);
		}
	}

	/**
	 * Constructor. Array is (2^rbits)X(2^gbits)X(2^bbits) in size. Only values from 1 to 8 are
	 * valid.
	 */
	public ColorHistogram(ColorImage image, int rbits, int gbits, int bbits) {
		m_rbits = rbits;
		m_gbits = gbits;
		m_bbits = bbits;

		if (m_rbits < 1)
			m_rbits = 1;
		if (m_rbits > 8)
			m_rbits = 8;
		if (m_gbits < 1)
			m_gbits = 1;
		if (m_gbits > 8)
			m_gbits = 8;
		if (m_bbits < 1)
			m_bbits = 1;
		if (m_bbits > 8)
			m_bbits = 8;

		m_rsize = 1 << m_rbits;
		m_gsize = 1 << m_gbits;
		m_bsize = 1 << m_bbits;

		int a = 0;
		int b = 0;
		Integer[] val = new Integer[3];
		try {

			m_data = new int[m_rsize][m_gsize][m_bsize];
			for (int h = 0; h < m_rsize; h++) {
				for (int i = 0; i < m_gsize; i++) {
					for (int j = 0; j < m_bsize; j++) {
						m_data[h][i][j] = 0;
					}
				}
			}

			int rprec = 1 << (8 - m_rbits);
			int gprec = 1 << (8 - m_gbits);
			int bprec = 1 << (8 - m_bbits);

			for (int x = 0; x < image.X(); x++) {
				for (int y = 0; y < image.Y(); y++) {
					a = x;
					b = y;
					val = image.get(x, y);
					m_data[val[0] / rprec][val[1] / gprec][val[2] / bprec]++;
				}
			}
		} catch (Exception e) {
			System.out.println(a + " " + b + " " + val[0] + " " + val[1] + " " + val[2]);
		}
	}

	/** Returns the count of the colorHistogram at <i>r</i> <i>g</i> <i>b</i> */
	public int count(int r, int g, int b) {
		return m_data[r][g][b];
	}

	/** Returns the mean of the entire ColorHistogram as an int[]. */
	public float[] mean() {
		int count = 0;
		float[] total = new float[3];

		for (int k = 0; k < 3; k++)
			total[k] = 0;

		for (int h = 0; h < 32; h++)
			for (int i = 0; i < 32; i++)
				for (int j = 0; j < 32; j++)
					if (m_data[h][i][j] != 0) {
						count = count + m_data[h][i][j];
						total[0] = total[0] + h * m_data[h][i][j];
						total[1] = total[1] + i * m_data[h][i][j];
						total[2] = total[2] + j * m_data[h][i][j];
					}
		float[] results = new float[3];
		results[0] = total[0] / count;
		results[1] = total[1] / count;
		results[2] = total[2] / count;
		return results;
	}
}
