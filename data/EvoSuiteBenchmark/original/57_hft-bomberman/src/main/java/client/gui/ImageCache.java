package client.gui;

import java.awt.Image;

/**
 * class for storing images in association with coordinates and dimensions
 * they should be painted with
 * 
 * @author christian
 * 
 */
public class ImageCache {
	Image img;
	int x, y, w, h;

	/**
	 * create ImageCache Object
	 * 
	 * @param img -
	 *            Image
	 * @param x -
	 *            x Coordinate
	 * @param y -
	 *            y Coordinate
	 * @param w -
	 *            Width
	 * @param h -
	 *            Height
	 */
	public ImageCache(Image img, int x, int y, int w, int h) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	/** returns cached image
	 * @return
	 */
	public Image getImg() {
		return img;
	}

	/** sets image
	 * @param img
	 */
	public void setImg(Image img) {
		this.img = img;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

}
