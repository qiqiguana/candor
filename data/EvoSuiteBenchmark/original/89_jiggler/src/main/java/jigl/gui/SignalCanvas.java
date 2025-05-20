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
package jigl.gui;

import java.awt.Canvas;
import java.awt.Graphics;

import jigl.image.types.GrayImage;
import jigl.image.types.RealGrayImage;
import jigl.signal.BadSignalException;
import jigl.signal.ComplexSignal;
import jigl.signal.DiscreteSignal;
import jigl.signal.RealSignal;
import jigl.signal.utils.SignalConverter;

/** SignalCanvas is a class made to facilitate the displaying of a JIGL signal */
public class SignalCanvas extends Canvas {
	private static final long serialVersionUID = 1L;

	/** Java signal */
	protected java.awt.Image image;
	/** JIGL signal */
	protected jigl.signal.Signal jsignal;

	/** Creates an empty SignalCanvas */
	public SignalCanvas() {
		super();
	}

	/**
	 * Creates an SignalCanvas from a DiscreteSignal
	 * 
	 * @param sig GraySignal
	 */
	public SignalCanvas(DiscreteSignal sig) throws BadSignalException {
		super();
		image = getJavaSignal(sig);
		jsignal = sig;
	}

	/**
	 * Creates an SignalCanvas from a RealSignal
	 * 
	 * @param sig RealGraySignal
	 */
	public SignalCanvas(RealSignal sig) throws BadSignalException {
		super();
		image = getJavaSignal(sig);
		jsignal = sig;
	}

	/**
	 * Creates an SignalCanvas from a ComplexSignal
	 * 
	 * @param sig ComplexSignal
	 */
	public SignalCanvas(ComplexSignal sig) throws BadSignalException {
		super();
		image = getJavaSignal(sig);
		jsignal = sig;
	}

	/**
	 * Creates an SignalCanvas from a JIGL Signal
	 * 
	 * @param sig JIGL Signal
	 */
	public SignalCanvas(jigl.signal.Signal sig) throws BadSignalException {
		super();
		image = getJavaSignal(sig);
		jsignal = sig;
	}

	/** Sets the Java signal to <i>sig</i> and jsignal to null */
	public void setSignal(java.awt.Image sig) {
		image = sig;
		jsignal = null;
	}

	/** Sets the Java signal to <i>sig</i> and jsignal to <i>sig</i> */
	public void setSignal(jigl.signal.Signal sig) throws BadSignalException {
		image = getJavaSignal(sig);
		jsignal = sig;
	}

	/** Returns the JIGL signal */
	public jigl.signal.Signal getSignal() {
		return jsignal;
	}

	/** Returns the Java signal */
	public java.awt.Image getJavaSignal() {
		return image;
	}

	/**
	 * Returns an instance of Graphics that, when modified, modifies the Java signal
	 */
	public Graphics setOffScreen() {
		java.awt.Image tempSignal;
		Graphics g;

		tempSignal = image;
		image = this.createImage(image.getWidth(this), image.getHeight(this));
		g = image.getGraphics();
		g.drawImage(tempSignal, 0, 0, this);

		return g;

	}

	/**
	 * Returns an instance of Graphics that, when modified, modifies the Java signal
	 * 
	 * @param xfactor zoom of x axis
	 * @param yfactor zoom of y axis
	 */
	public Graphics setOffScreen(double xfactor, double yfactor) {
		java.awt.Image tempSignal;
		Graphics g;

		tempSignal = image;
		image = this.createImage((int) (image.getWidth(this) * xfactor), (int) (image
				.getHeight(this) * yfactor));

		g = image.getGraphics();

		g.drawImage(tempSignal, 0, 00, (int) (tempSignal.getWidth(this) * xfactor),
				(int) (tempSignal.getHeight(this) * yfactor), this);

		return g;

	}

	/** Overrides Component.setVisible(boolean) */
	public void setVisible(boolean b) {
		setSize(imWidth(), imHeight());
		super.setVisible(b);

	}

	/** Returns the signal width. */
	public int imWidth() {
		return image.getWidth(this);
	}

	/** Return the signal height. */
	public int imHeight() {
		return image.getHeight(this);
	}

	/**
	 * Takes a jigl signal as input, converts it to a java signal, and returns the java signal.
	 * 
	 * @param sig the JIGL signal
	 */
	public java.awt.Image getJavaSignal(jigl.signal.Signal sig) throws BadSignalException,
			RuntimeException {

		// int w = 0;
		// int h = 0;
		java.awt.Image jsig = null;

		if (sig instanceof DiscreteSignal) {

			int max = Integer.MIN_VALUE;
			int min = Integer.MAX_VALUE;

			DiscreteSignal nsig = SignalConverter.toDiscrete(sig);
			for (int x = 0; x < nsig.length(); x++) {
				if ((short) nsig.get(x) > max)
					max = (short) nsig.get(x);
				if ((short) nsig.get(x) < min)
					min = (short) nsig.get(x);

			}

			short[][] new_data = new short[max][nsig.length()];
			GrayImage gimage = new GrayImage(nsig.length(), max);
			for (int x = 0; x < nsig.length(); x++)
				// FIXME: put braces on this for
				for (int y = 0; y < max; y++) {
					if ((max - y) < nsig.get(x))
						new_data[y][x] = 0;
					else
						new_data[y][x] = 255;
					gimage.set(x, y, (int) new_data[y][x]);
				}
			// w = gimage.X();
			// h = gimage.Y();
			jsig = createImage(gimage.getJavaImage());
			return jsig;
		} else {
			float max = Float.MIN_VALUE;
			float min = Float.MAX_VALUE;
			RealSignal nsig = SignalConverter.toReal(sig);
			for (int x = 0; x < nsig.length(); x++) {
				if (nsig.get(x) > max)
					max = nsig.get(x);
				if (nsig.get(x) < min)
					min = nsig.get(x);
			}

			float[][] new_data = new float[(int) max][nsig.length()];
			RealGrayImage gimage = new RealGrayImage(nsig.length(), (int) max);
			for (int x = 0; x < nsig.length(); x++) {
				for (int y = 0; y < max; y++) {
					// try{
					if ((max - y) < nsig.get(x))
						new_data[y][x] = 0;
					else
						new_data[y][x] = 255;
					gimage.set(x, y, new_data[y][x]);
					// }catch (Exception e)
					// {System.out.println(x+" uh oh  "+y);}

				}
			}
			// w = gimage.X();
			// h = gimage.Y();
			jsig = createImage(gimage.getJavaImage());
			return jsig;

		}

	}

	/**
	 * Repaints the SignalCanvas (including the selection box)
	 * 
	 * @param g Graphics
	 */
	public void update(Graphics g) {
		java.awt.Image i = image;
		g.drawImage(i, 0, 0, this);
		// Graphics gr = this.getGraphics();

	}

	/** Overridden for smoother repainting */
	public void paint(Graphics g) {
		update(g);
	}

}
