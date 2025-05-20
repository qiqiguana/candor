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
import jigl.image.NumericImage;
import jigl.image.types.ComplexImage;
import jigl.util.MathUtil;

/**
 * Computes the Fast Fourier Transform of an image. Please note, the input images <i>must</i> be
 * square and a power of two. To get the correct shape of Fourier transform, you should shift the
 * result image both right by half width and up by half height.
 * <p>
 * If you apply a forward FFT and finally apply an inverse FFT, be sure to adjust the range of the
 * final image because it may become very big or small.
 */
public final class FFT {
	/** Static constant definition, which is 1 */
	private static final int REAL = 1;
	/** Static constant definition, which is 2 */
	private static final int IMAG = 2;

	private FFT() { }

	/** Perform the forward Fast Fourier Transform on <code>im</code> */
	public static ComplexImage forward(Image<?> im) {
		return doFFT(im, true);
	}

	/** Perform the forward Fast Fourier Transform on <code>im</code> */
	public static ComplexImage reverse(Image<?> im) {
		return doFFT(im, false);
	}

	/** Utility function */
	private static ComplexImage doFFT(Image<?> im, boolean forward) {
		if (im.X() != im.Y())
			throw new IllegalArgumentException("The width and height of the image must be same in order to apply FFT.");
		if(!MathUtil.powerOf2(im.X()) || !MathUtil.powerOf2(im.Y()))
			throw new IllegalArgumentException("The width and height of the image should be power of 2 in order to apply FFT.");

		final int d1 = im.X();
		final int d2 = im.Y();
		
		final int dimlen[] = { d1, d2 };
		
		// +1 for 1-based arrays and *2 for complex
		double fftdata[] = new double[(d1 + 1) * (d2 + 1) * 2];
		
		for(int x = 0; x < d1; x++) {
			for(int y = 0; y < d2; y++) {
				final int addrbase = 2 * (y * d1 + x);
				fftdata[addrbase + REAL] = realComponent(im, x, y);
				fftdata[addrbase + IMAG] = imaginaryComponent(im, x, y);
			}
		}
		
		return fourn(fftdata, dimlen, 2, forward);
	}
	
	private static double realComponent(Image<?> img, final int x, final int y) {
		if(img instanceof NumericImage<?>) {
			NumericImage<?> nimg = (NumericImage<?>) img;
			return nimg.get(x,y).doubleValue();
		} else if(img instanceof ComplexImage) {
			ComplexImage cimg = (ComplexImage) img;
			return cimg.getReal(x, y);
		} else {
			throw new IllegalArgumentException("Don't know how to handle this image type");
		}
	}
	
	private static double imaginaryComponent(Image<?> img, final int x, final int y) {
		if(img instanceof NumericImage<?>) {
			return 0;
		} else if(img instanceof ComplexImage) {
			ComplexImage cimg = (ComplexImage) img;
			return cimg.getImaginary(x, y);
		} else {
			throw new IllegalArgumentException("Don't know how to handle this image type");
		}
	}
	
	/**
	 * FIXME: Refactor so there aren't 27 variables in this method and so the code is readable
	 * Utility methods
	 */
	private static ComplexImage fourn(double data[], int nn[], final int ndim, final boolean forward) {
		final double sign = forward ? 1.0 : -1.0;
		final double sign2pi = sign * 2 * Math.PI;

		int ntot = 1;
		for(int dimension : nn) {
			ntot *= dimension;
		}
		
		int nprev = 1;
		for (int idim = ndim - 1; idim >= 0; idim--) {
			final int n = nn[idim];
			final int nrem = ntot / (n * nprev);
			final int ip1 = nprev << 1;
			final int ip2 = ip1 * n;
			final int ip3 = ip2 * nrem;
			int i2rev = 1;
			
			for (int i2 = 1; i2 <= ip2; i2 += ip1) { /* bit reversal */
				if (i2 < i2rev) {
					for (int i1 = i2; i1 <= i2 + ip1 - 2; i1 += 2) {
						for (int i3 = i1; i3 <= ip3; i3 += ip2) {
							final int i3rev = i2rev + i3 - i2;
							swap(data, i3, i3rev);
							swap(data, i3+1, i3rev+1);
						} // for i3
					} // for i1
				} // if i2
				int ibit = ip2 >> 1;
				while (ibit >= ip1 && i2rev > ibit) {
					i2rev -= ibit;
					ibit >>= 1;
				} // while ibit
				i2rev += ibit;
			} // for i2
			int ifp1 = ip1;
			while (ifp1 < ip2) {
				final int ifp2 = ifp1 << 1;
				final double theta = sign2pi * ip1 / ifp2;
				double wtemp = Math.sin(0.5 * theta);
				final double wpr = -2.0 * wtemp * wtemp;
				final double wpi = Math.sin(theta);
				double wr = 1.0;
				double wi = 0.0;
				for (int i3 = 1; i3 <= ifp1; i3 += ip1) {
					for (int i1 = i3; i1 <= i3 + ip1 - 2; i1 += 2) {
						for (int i2 = i1; i2 <= ip3; i2 += ifp2) {
							final int k1 = i2;
							final int k2 = k1 + ifp1;
							final double tempr = wr * data[k2] - wi * data[k2 + 1];
							final double tempi = wr * data[k2 + 1] + wi * data[k2];
							data[k2] = data[k1] - tempr;
							data[k2 + 1] = data[k1 + 1] - tempi;
							data[k1] += tempr;
							data[k1 + 1] += tempi;
						} // for i2
					} // for i1
					//TODO: figure out if the wtemp = wr assignment is intentional
					wr = (wtemp = wr) * wpr - wi * wpi + wr;
					wi = wi * wpr + wtemp * wpi + wi;
				} // for i3
				ifp1 = ifp2;
			} // while ifp1 < ip2
			nprev *= n;
		} // for idim
		return convertToComplex(data, nn);
	}
	
	private static void swap(double[] arr, final int idx1, final int idx2) {
		final double tmp = arr[idx1];
		arr[idx1] = arr[idx2];
		arr[idx2] = tmp;
	}

	/** Utility method */
	private static ComplexImage convertToComplex(double data[], int nn[]) {
		ComplexImage ci = new ComplexImage(nn[0], nn[1]);
		int addrbase = 0;
		for (int i = 0; i < nn[0]; i++)
			for (int j = 0; j < nn[1]; j++) {
				addrbase = 2 * (j * nn[0] + i);
				ci.setReal(i, j, (float) data[addrbase + REAL]);
				ci.setImaginary(i, j, (float) data[addrbase + IMAG]);
			}
		return ci;
	}
}
