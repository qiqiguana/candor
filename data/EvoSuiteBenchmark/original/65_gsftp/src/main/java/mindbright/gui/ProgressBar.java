/******************************************************************************
 *
 * Copyright (c) 1998,99 by Mindbright Technology AB, Stockholm, Sweden.
 *                 www.mindbright.se, info@mindbright.se
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *****************************************************************************
 * $Author: webjabber $
 * $Date: 2006/06/13 18:43:27 $
 * $Name:  $
 *****************************************************************************/
package mindbright.gui;

import java.awt.*;

public class ProgressBar extends Canvas {
	long max = 0;

	long current = 0;

	int width = 0;

	int height = 0;

	Color barColor;

	FontMetrics fm;

	Image img;

	Graphics memG;

	public synchronized void setBarColor(Color c) {
		barColor = c;
	}

	public synchronized void setValue(long v) {
		setValue(v, false);
	}

	public synchronized void setValue(long v, boolean repaintNow) {
		current = (v > max ? max : v);
		if (repaintNow) {
			this.update(getGraphics());
		} else {
			repaint();
		}
	}

	public synchronized void setMax(long max, boolean reset) {
		this.max = max;
		if (reset)
			current = 0;
		setValue(current, true);
	}

	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public ProgressBar(int max, int width, int height) {
		super();
		this.max = max;
		this.width = width;
		this.height = height;
		barColor = Color.black;
	}

	public void update(Graphics g) {
		paint(g);
	}

	public synchronized void paint(Graphics g) {
		long d = (max != 0 ? max : 1);
		double perc = ((double) current * 100.0) / (double) d;
		String p = ((int) perc) + "%";
		long w = (current * (width - 2)) / d;

		if (fm == null) {
			fm = g.getFontMetrics(g.getFont());
		}

		if (img == null) {
			setBackground(Color.white);
			img = createImage(width, height);
			memG = img.getGraphics();
		}

		memG.setPaintMode();
		memG.setColor(Color.white);
		memG.fillRect(0, 0, width, height);
		memG.setColor(Color.black);

		memG.drawRect(0, 0, width - 1, height - 1);
		memG.drawString(p, (width / 2) - (fm.stringWidth(p) / 2) + 1,
				(height / 2) + fm.getMaxAscent() + fm.getLeading()
						- (fm.getHeight() / 2));
		memG.setColor(barColor);
		memG.setXORMode(Color.white);
		memG.fillRect(1, 1, (int)w, height - 2);

		g.drawImage(img, 0, 0, this);
	}
}
