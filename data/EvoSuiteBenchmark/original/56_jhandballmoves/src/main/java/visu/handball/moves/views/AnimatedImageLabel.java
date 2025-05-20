/*
 * Created on 20.02.2007
 * Created by Thomas Halm
 * Copyright (C) 2006  Richard Doerfler, Thomas Halm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package visu.handball.moves.views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class AnimatedImageLabel extends JLabel implements Runnable {

	private Image displayImage;

	private BufferedImage bi;

	private BufferedImage bi_gray;

	private int colorWidth;

	private boolean stopAnimation;

	public AnimatedImageLabel(URL imageUrl) {
		loadImage(imageUrl);

		setSize(displayImage.getWidth(this), displayImage.getWidth(this));
		setPreferredSize(new Dimension(displayImage.getWidth(this),
				displayImage.getHeight(this)));

		createBufferedImage();
	}

	public void loadImage(URL imageUrl) {
		displayImage = Toolkit.getDefaultToolkit().getImage(imageUrl);
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(displayImage, 1);
		try {
			mt.waitForAll();
		} catch (Exception e) {
			System.out.println("Exception while loading.");
		}
		if (displayImage.getWidth(this) == -1) {
			System.out.println("No image");
			System.exit(0);
		}
	}

	public void createBufferedImage() {
		bi = new BufferedImage(displayImage.getWidth(this), displayImage
				.getHeight(this), BufferedImage.TYPE_INT_RGB);
		// Kopie von bi erstellen
		bi_gray = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		Graphics g2d = bi.createGraphics();

		// Grafik in das BufferedImage einfügen
		g2d.drawImage(displayImage, 0, 0, this);

		// graues Bild aus Original erstellen
		ColorConvertOp colorConvert = new ColorConvertOp(ColorSpace
				.getInstance(ColorSpace.CS_GRAY), null);
		colorConvert.filter(bi, bi_gray);
	}

	public void update(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		paintComponent(g);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;

		// zuerst vollständiges graues Bild zeichnen
		g2D.drawImage(bi_gray, 0, 0, this);
		// Clipping erstellen von links nach rechts, bis colorWidth
		g2D.setClip(0, 0, colorWidth, displayImage.getHeight(this));
		// Farbiges Bild wird in Clipping-Area gezeichnet
		g2D.drawImage(bi, 0, 0, this);
	}

	public boolean next() {
		if (colorWidth < displayImage.getWidth(this)) {
			colorWidth++;
			return true;
		}
		return false;
	}

	public void run() {
		while (next() && !stopAnimation) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					updateUI();
				}
			});
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void startAnimation() {
		new Thread(this).start();
	}

	public synchronized void stopAnimation() {
		stopAnimation = true;
		notify();
	}

}
