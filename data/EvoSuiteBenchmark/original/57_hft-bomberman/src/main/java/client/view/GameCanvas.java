/**
 * 
 */
package client.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * @author Andi
 * 
 */
public class GameCanvas extends Canvas {

	private final int width;
	private final int height;
	private BufferedImage buffer;
	private Graphics2D bufferGfx;
	private boolean ready;

	/**
	 * @param i
	 * @param j
	 */
	public GameCanvas(int width, int height) {
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		prepare();
	}

	/**
	 * 
	 */
	private void prepare() {
		this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Canvas#paint(java.awt.Graphics)
	 */
	@Override
//	public void paintComponent(Graphics gfx) {
//		if(gfx == null){
//			return;
//		}
//		gfx.drawImage(buffer, 0, 0, null);
//	}

	public void paint(Graphics gfx){
		if(gfx == null){
			return;
		}
		gfx.drawImage(buffer, 0, 0, null);
	}
	
	
	public Graphics2D getCanvas() {
		if(!ready){
			bufferGfx = buffer.createGraphics();
			bufferGfx.setRenderingHint(
					RenderingHints.KEY_ANTIALIASING, 
					RenderingHints.VALUE_ANTIALIAS_ON
					);
			ready = true;
		}
		bufferGfx.fillRect(0, 0, width, height);
		return bufferGfx;
	}

	/**
	 * 
	 */
	public void update() {
		paint(getGraphics());
//		paintComponent(getGraphics());
	}

	

}
