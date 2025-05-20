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

import jigl.image.exceptions.InvalidKernelException;

/** ImageKernel is an extension of GrayImage with some pre-defined kernels. */
public class ImageKernel extends RealGrayImage {
	/**
	 * Creates a kernel of one of the predefined types.<br/>
	 * 
	 * <pre>
	 * 0 - UNIFORM		1 - SOBEL_X		2 - SOBEL_Y
	 * 	3 - PREWITT_X		4 - PREWITT_Y		5 - LAPLACIAN
	 * 	6 - LAPLACIAN_8		7 - UNSHARP		8 - UNSHARP_8
	 * 	9 - D_XX		10 - D_YY		11 - D_XY
	 * </pre>
	 * 
	 * @param type kernel type
	 * @see KernelType
	 */
	public ImageKernel(KernelType type) throws InvalidKernelException {
		super(kernelData(type));
	}
	
	private static float[][] kernelData(KernelType type) {
		switch (type) {
		case UNIFORM:
			return new float[][]{ { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
		case SOBEL_X:
			return new float[][]{ { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
		case SOBEL_Y:
			return new float[][]{ { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };
		case PREWITT_X:
			return new float[][]{ { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };
		case PREWITT_Y:
			return new float[][]{ { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };
		case LAPLACIAN:
			return new float[][]{ { 0, 1, 0 }, { 1, -4, 1 }, { 0, 1, 0 } };
		case LAPLACIAN_8:
			return new float[][]{ { 1, 1, 1 }, { 1, -8, 1 }, { 1, 1, 1 } };
		case UNSHARP:
			return new float[][]{ { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };
		case UNSHARP_8:
			return new float[][]{ { -1, -1, -1 }, { -1, 9, -1 }, { -1, -1, -1 } };
		//		case 9: {float[][] data1={{0,0,0},{1,1,0},{0,1,0}};
//	         data2=data1;
//				 break;}
		case D_XX:
			return new float[][]{ { 1, -2, 1 }, { 1, -2, 1 }, { 1, -2, 1 } };
		case D_YY:
			return new float[][]{ { 1, 1, 1 }, { -2, -2, -2 }, { 1, 1, 1 } };
		case D_XY:
			return new float[][]{ { 1, 0, -1 }, { 0, 0, 0 }, { -1, 0, 1 } };
		default:
			throw new InvalidKernelException();
		}
	}

	/**
	 * Creates a uniform kernel of specified size
	 * 
	 * @param val uniform value for the kernel
	 * @param dimension size of kernel (dimension X dimension)
	 */
	public ImageKernel(float val, int dimension) {
		super(dimension, dimension);
		for (int x = 0; x < dimension; x++)
			for (int y = 0; y < dimension; y++)
				data[x][y] = val;

	}

	/**
	 * Creates a kernel out of the given array. The kernel created will have dimensions equal to the
	 * number of elements in the first row.
	 * 
	 * @param dat two-dimensional data array
	 */
	public ImageKernel(float[][] dat) {
		super(dat);
	}

	/** Creates an ImageKernel from a RealGrayImage */
	public ImageKernel(RealGrayImage img) {
		super(img);
	}

	/** "Normalizes" the ImageKernel by 255. However the result kernel is not totally normalized. */
	public void normalize255() {
		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {
				data[y][x] = data[y][x] / 255;
			}
		}

	}

	/** Normalize the kernel by total grayscales. */
	public void normalize() {
		float total = 0;
		for (int x = 0; x < X; x++)
			for (int y = 0; y < Y; y++)
				total += data[y][x];
		if (total == 0.0f)
			total = 1.0f;

		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {
				data[y][x] = data[y][x] / total;
			}
		}

	}
}
