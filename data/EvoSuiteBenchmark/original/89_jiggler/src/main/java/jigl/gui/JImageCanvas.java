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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JPanel;

import jigl.image.exceptions.ImageNotSupportedException;
import jigl.image.types.BinaryImage;
import jigl.image.types.ColorImage;
import jigl.image.types.ComplexImage;
import jigl.image.types.GrayImage;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/**
 * JImageCanvas is a SWING compatible class made to facilitate the displaying of a JIGL image.
 * JImageCanvas also easily supports a highlight box when active and a mouse drawn selection box.
 * These options can be turned on or off with the showSelectionBox() and showActiveBox() methods.
 * The options are meant to be used with the MouseListener and MouseMotionListener methods,and can
 * be used as in the example code (demonstrating the selecton box):
 * <p>
 * Supports Jigl images, Java images (.gif, .jpg, .jpeg),and PPM images (.ppm, .pgm, .pbm).
 * <P>
 * 
 * <pre>
 * //MouseListener method
 * public void mousePressed(MouseEvent e) {
 * 	if (e.getComponent() instanceof jigl.gui.JImageCanvas) {
 * 		jigl.gui.JImageCanvas canvas = (jigl.gui.JImageCanvas) e.getComponent();
 * 		canvas.clearSelectionBox();
 * 		canvas.setSelectionBoxAnchor(e.getX(), e.getY());
 * 	}
 * }
 * 
 * //MouseMotionListener method
 * public void mouseDragged(MouseEvent e) {
 * 	if (e.getComponent() instanceof jigl.gui.JImageCanvas) {
 * 		jigl.gui.JImageCanvas canvas = (jigl.gui.JImageCanvas) e.getComponent();
 * 		canvas.setSelectionBoxExtent(e.getX(), e.getY());
 * 	}
 * }
 * </pre>
 */
public class JImageCanvas extends JPanel {
	private static final long serialVersionUID = 1L;

	/** Java image */
	protected java.awt.Image image;
	/** JIGL image */
	protected jigl.image.Image jimage;

	private boolean m_bShowSelectionBox = false;
	private boolean m_bShowActive = false;
	private boolean m_bIsActive = false;
	private Color m_activeColor = Color.blue;

	private int m_boxlx = 0;
	private int m_boxly = 0;
	private int m_boxux = 0;
	private int m_boxuy = 0;

	/** Creates an empty JImageCanvas. */
	public JImageCanvas() {
		super();
	}

	/**
	 * Creates a JImageCanvas from a GrayImage.
	 * 
	 * @param image GrayImage
	 * @throws ImageNotSupportedException if the image type is not supported
	 */
	public JImageCanvas(BinaryImage image) throws ImageNotSupportedException {
		super();
		this.image = getJavaImage(image);
		jimage = image;
	}

	/**
	 * Creates a JImageCanvas from a GrayImage.
	 * 
	 * @param image GrayImage
	 * @throws ImageNotSupportedException if the image type is not supported
	 */
	public JImageCanvas(GrayImage image) throws ImageNotSupportedException {
		super();
		this.image = getJavaImage(image);
		jimage = image;
	}

	/**
	 * Creates a JImageCanvas from a ColorImage.
	 * 
	 * @param image ColorImage
	 * @throws ImageNotSupportedException if the image type is not supported
	 */
	public JImageCanvas(ColorImage image) throws ImageNotSupportedException {
		super();
		this.image = getJavaImage(image);
		jimage = image;
	}

	/**
	 * Creates a JImageCanvas from a RealGrayImage.
	 * 
	 * @param image RealGrayImage
	 * @throws ImageNotSupportedException if the image type is not supported
	 */
	public JImageCanvas(RealGrayImage image) throws ImageNotSupportedException {
		super();
		this.image = getJavaImage(image);
		jimage = image;

	}

	/**
	 * Creates a JImageCanvas from a RealColorImage.
	 * 
	 * @param image RealColorImage
	 * @throws ImageNotSupportedException if the image type is not supported
	 */
	public JImageCanvas(RealColorImage image) throws ImageNotSupportedException {
		super();
		this.image = getJavaImage(image);
		jimage = image;

	}

	/**
	 * Creates a JImageCanvas from a ComplexImage.
	 * 
	 * @param image ComplexImage
	 * @throws ImageNotSupportedException if the image type is not supported
	 */
	public JImageCanvas(ComplexImage image) throws ImageNotSupportedException {
		super();
		this.image = getJavaImage(image);
		jimage = image;

	}

	/**
	 * Creates a JImageCanvas from a JIGL Image.
	 * 
	 * @param image jigl.image.Image
	 * @throws ImageNotSupportedException if the image type is not supported
	 */
	public JImageCanvas(jigl.image.Image image) throws ImageNotSupportedException {
		super();
		this.image = getJavaImage(image);
		jimage = image;

	}

	/**
	 * Sets the Java image to <CODE>image</CODE> and JIGL Image to null.
	 * 
	 * @param image java.awt.Image
	 * @see java.awt.Image
	 */
	public void setImage(java.awt.Image image) {
		this.image = image;
		setSize(this.image.getWidth(this), this.image.getHeight(this));
		jimage = null;
	}

	/**
	 * Sets the JIGL Image and Java Image to <CODE>image</CODE>.
	 * 
	 * @param image jigl.image.Image
	 * @throws ImageNotSupportedException if the image type is not supported
	 */
	public void setImage(jigl.image.Image image) throws ImageNotSupportedException {
		this.image = getJavaImage(image);
		jimage = image;
	}

	/**
	 * Returns the JIGL image.
	 * 
	 * @return jigl.image.Image for this JImageCanvas
	 */
	public jigl.image.Image getImage() {
		if (jimage == null)
			System.out.println("Image is null");
		return jimage;
	}

	/**
	 * Returns the Java image.
	 * 
	 * @return java.awt.Image for this JImageCanvas
	 */
	public java.awt.Image getJavaImage() {
		return image;
	}

	/**
	 * Returns an instance of Graphics that, when modified, modifies the Java image.
	 * 
	 * @return Graphics tied to the java.awt.Image
	 * @see java.awt.Graphics
	 */
	public Graphics setOffScreen() {
		java.awt.Image tempImage;
		Graphics g;

		tempImage = image;
		image = this.createImage(image.getWidth(this), image.getHeight(this));
		g = image.getGraphics();
		g.drawImage(tempImage, 0, 0, this);

		return g;
	}

	/**
	 * Returns an instance of Graphics that, when modified, modifies the Java image.
	 * 
	 * @return Graphics tied to the java.awt.Image
	 * @param xfactor Zoom of x axis.
	 * @param yfactor Zoom of y axis.
	 */
	public Graphics setOffScreen(double xfactor, double yfactor) {
		java.awt.Image tempImage;
		Graphics g;

		tempImage = image;
		image = this.createImage((int) (image.getWidth(this) * xfactor), (int) (image
				.getHeight(this) * yfactor));

		if (image == null) {
			System.out.println("createImage returned null!");
		}

		g = image.getGraphics();

		g.drawImage(tempImage, 0, 0, (int) (tempImage.getWidth(this) * xfactor), (int) (tempImage
				.getHeight(this) * yfactor), this);
		return g;
	}

	/**
	 * Overrides Component.setVisible(boolean)
	 * 
	 * @param b flag: true == visible
	 */
	public void setVisible(boolean b) {
		setSize(imWidth(), imHeight());
		super.setVisible(b);

	}

	/**
	 * Returns the image height.
	 * 
	 * @return image height
	 */
	public int imHeight() {
		return image.getHeight(this);
	}

	/**
	 * Returns the image width.
	 * 
	 * @return image width
	 */
	public int imWidth() {
		if (image == null)
			return 0;
		return image.getWidth(this);
	}

	/**
	 * Overridden to reflect image size.
	 * 
	 * @return Dimension of the image
	 */
	public Dimension getSize() {
		if (image == null)
			return new Dimension(0, 0);
		return new Dimension(image.getWidth(this), image.getHeight(this));
	}

	/**
	 * Overridden to reflect image size.
	 * 
	 * @return Dimension of the image
	 * @param rv Dimension object
	 */
	public Dimension getSize(Dimension rv) {
		if (image == null) {
			rv.width = 0;
			rv.height = 0;
		} else {
			rv.width = image.getWidth(this);
			rv.height = image.getHeight(this);
		}
		return rv;
	}

	/**
	 * Overridden to reflect image size.
	 * 
	 * @return image width
	 */
	public int getWidth() {
		if (image == null)
			return 0;
		return image.getWidth(this);
	}

	/**
	 * Overridden to reflect image size.
	 * 
	 * @return image height
	 */
	public int getHeight() {
		if (image == null)
			return 0;
		return image.getHeight(this);
	}

	/**
	 * Overridden to reflect image size.
	 * 
	 * @return Dimension of image
	 */
	public Dimension getPreferredSize() {
		return getSize();
	}

	/**
	 * Takes a jigl image as input, converts it to a java image, and returns the java image.
	 * 
	 * @param img The JIGL image.
	 * @throws ImageNotSupportedException if the image type is not supported
	 * @return java.awt.Image
	 * @see java.awt.Image
	 */
	public java.awt.Image getJavaImage(jigl.image.Image img) throws ImageNotSupportedException//, RuntimeException
	{

		int w = 0;
		int h = 0;
		java.awt.Image jimg = null;

		//    short max=Short.MIN_VALUE;
		//    short min=Short.MAX_VALUE;
		if (img instanceof GrayImage) {
			GrayImage im = (GrayImage) img;
			jimg = createImage(im.getJavaImage());
			w = jimg.getWidth(this);
			h = jimg.getHeight(this);
		} else if (img instanceof BinaryImage) {
			BinaryImage im = (BinaryImage) img;
			jimg = createImage(im.getJavaImage());
			w = jimg.getWidth(this);
			h = jimg.getHeight(this);
		} else if (img instanceof ColorImage) {
			ColorImage im = (ColorImage) img;
			jimg = createImage(im.getJavaImage());
			w = jimg.getWidth(this);
			h = jimg.getHeight(this);
		} else if (img instanceof RealGrayImage) {
			RealGrayImage im = (RealGrayImage) img;
			jimg = createImage(im.getJavaImage());
			w = jimg.getWidth(this);
			h = jimg.getHeight(this);
		} else if (img instanceof RealColorImage) {
			RealColorImage im = (RealColorImage) img;
			jimg = createImage(im.getJavaImage());
			w = jimg.getWidth(this);
			h = jimg.getHeight(this);
		} else if (img instanceof ComplexImage) {
			ComplexImage im = (ComplexImage) img;
			jimg = createImage(im.getJavaImage());
			w = jimg.getWidth(this);
			h = jimg.getHeight(this);
		} else {
			throw new ImageNotSupportedException();
		}

		//    if (w > getSize().width) {
		//      setSize(w,h);
		//    }
		//  if (h > getSize().height) {
		setSize(w, h);
		//    }

		return jimg;
	}

	/**
	 * Repaints the JImageCanvas. Also draws the selection box and active border if those options
	 * are enabled.
	 * 
	 * @param g Graphics
	 */
	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * Overrides the paint method for smoother redraw.
	 * 
	 * @param g Graphics object
	 */
	public void paint(Graphics g) {
		paintComponent(g);
		paintBorder(g);
		paintChildren(g);
	}

	/**
	 * Overrides the paintComponent method for smoother redraw.
	 * 
	 * @param g Graphics object
	 */
	public void paintComponent(Graphics g) {
		//	  setSize(imWidth(),imHeight());
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);

		//System.out.println("PaintCompenent has been called");

		if (m_bShowSelectionBox) {
			if (m_boxux != m_boxlx || m_boxuy != m_boxly) {
				g.setXORMode(Color.white);
				drawBox(g, m_boxux, m_boxuy, m_boxlx, m_boxly);
			}
		}
		if (m_bShowActive) {
			if (m_bIsActive) {
				drawActiveBox(g);
			}
		}
	}

	private void drawActiveBox(Graphics g) {
		g.setXORMode(m_activeColor);
		drawBox(g, 0, 0, imWidth() - 1, imHeight() - 1);
		drawBox(g, 1, 1, imWidth() - 2, imHeight() - 2);
	}

	private void drawBox(Graphics g, int ux, int uy, int lx, int ly) {
		g.drawLine(ux, uy, lx, uy);
		g.drawLine(ux, ly, lx, ly);
		g.drawLine(ux, uy, ux, ly);
		g.drawLine(lx, uy, lx, ly);
	}

	/**
	 * Sets the upper corner of the selection box (ux and uy). Must be followed by
	 * setSelectionBoxExtent for a box to actually be drawn.
	 * 
	 * @param ux Upper x coordinate of box.
	 * @param uy Upper y coordinate of box.
	 */
	public void setSelectionBoxAnchor(int ux, int uy) {
		m_boxux = ux;
		m_boxuy = uy;

		if (m_boxux < 0)
			m_boxux = 0;
		else if (m_boxux > imWidth() - 1)
			m_boxux = imWidth() - 1;
		if (m_boxuy < 0)
			m_boxuy = 0;
		else if (m_boxuy > imWidth() - 1)
			m_boxuy = imWidth() - 1;

		m_boxlx = m_boxux;
		m_boxly = m_boxuy;
	}

	/**
	 * Sets the lower corner of the selection box (lx and ly). The selection box anchor must have
	 * been set previously for this to draw a box.
	 * 
	 * @param lx Lower x coordinate of box.
	 * @param ly Lower y coordinate of box.
	 */
	public void setSelectionBoxExtent(int lx, int ly) {
		Graphics g = this.getGraphics();
		g.setXORMode(Color.white);

		//erase box
		if (m_boxux != m_boxlx || m_boxuy != m_boxly) {
			if (m_bShowSelectionBox) {
				drawBox(g, m_boxux, m_boxuy, m_boxlx, m_boxly);
			}
		}

		m_boxlx = lx;
		m_boxly = ly;

		if (m_boxlx < 0)
			m_boxlx = 0;
		else if (m_boxlx > imWidth() - 1)
			m_boxlx = imWidth() - 1;
		if (m_boxly < 0)
			m_boxly = 0;
		else if (m_boxly > imWidth() - 1)
			m_boxly = imWidth() - 1;

		//draw new box
		if (m_bShowSelectionBox) {
			drawBox(g, m_boxux, m_boxuy, m_boxlx, m_boxly);
		}
	}

	/**
	 * Sets whether or not selection box is displayed.
	 * 
	 * @param b true = box is displayed, false = box is not displayed
	 */
	public void showSelectionBox(boolean b) {
		if (m_bShowSelectionBox == b)
			return;

		m_bShowSelectionBox = b;

		//if box is set, show or hide it (depending on b)
		if (m_boxux != m_boxlx || m_boxuy != m_boxly) {
			Graphics g = this.getGraphics();
			g.setXORMode(Color.white);
			drawBox(g, m_boxux, m_boxuy, m_boxlx, m_boxly);
		}
	}

	/** Clears the selection box. */
	public void clearSelectionBox() {
		//erase box, if drawn
		if (m_boxux != m_boxlx || m_boxuy != m_boxly) {
			if (m_bShowSelectionBox) {
				Graphics g = this.getGraphics();
				g.setXORMode(Color.white);
				drawBox(g, m_boxux, m_boxuy, m_boxlx, m_boxly);
			}
		}

		//clear box
		m_boxux = m_boxuy = m_boxlx = m_boxly = 0;
	}

	/**
	 * Returns the selection box in a Rectangle object. Returns null if no selection box is set.
	 * 
	 * @return cCrrent selection box.
	 */
	public Rectangle getSelectionBox() {
		if (m_boxux == m_boxlx && m_boxuy == m_boxly)
			return null;
		else
			return new Rectangle(m_boxux, m_boxuy, m_boxlx - m_boxux, m_boxly - m_boxuy);
	}

	/**
	 * Returns the upper x (anchor) coordinate of the selection box.
	 * 
	 * @return x anchor coordinate of the selection box.
	 */
	public int getSelectionBoxAnchorX() {
		return m_boxux;
	}

	/**
	 * Returns the upper y (anchor) coordinate of the selection box.
	 * 
	 * @return y anchor coordinate of the selection box.
	 */
	public int getSelectionBoxAnchorY() {
		return m_boxuy;
	}

	/**
	 * Returns the lower x (extent) coordinate of the selection box.
	 * 
	 * @return x extent coordinate of the selection box.
	 */
	public int getSelectionBoxExtentX() {
		return m_boxlx;
	}

	/**
	 * Returns the lower y (extent) coordinate of the selection box.
	 * 
	 * @return y extent coordinate of the selection box.
	 */
	public int getSelectionBoxExtentY() {
		return m_boxly;
	}

	/**
	 * Sets whether or not the image is active. If showActiveBox is set to true, the image will have
	 * a highlight border when active.
	 * 
	 * @param active true = the image is active, false = the image is not active
	 */
	public void setActive(boolean active) {
		if (m_bIsActive == active)
			return;

		m_bIsActive = active;
		if (m_bShowActive) {
			drawActiveBox(((JComponent) this).getGraphics());
		}
	}

	/**
	 * Sets whether or not the image is highlighted with a border when activated.
	 * 
	 * @param b true = show border when active, false = don't show border
	 */
	public void showActiveBox(boolean b) {
		if (m_bShowActive == b)
			return;

		m_bShowActive = b;
		if (m_bIsActive) {
			drawActiveBox(((JPanel) this).getGraphics());
		}
	}

	/**
	 * Sets the color used to highlight the image when active (default is blue).
	 * 
	 * @param color Color object
	 */
	public void setActiveColor(Color color) {
		m_activeColor = color;
	}
}
