/**
 * 
 */
package client.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * @author andi
 *
 */
public class OverlayView extends View {

	private String text;
	
	private static Font font = new Font("Dialog", Font.PLAIN, 72);
	
	private static FontMetrics fontMetrics;
	
	private boolean dirty = true;
	
	private int baseX;
	
	private int baseY;
	
	public void setText(String text){
		this.text = text;
		this.dirty = true;
	}
	
	/* (non-Javadoc)
	 * @see client.view.View#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics gfx) {
		if(text == null){
			return;
		}
		if(dirty){
			fontMetrics = gfx.getFontMetrics();
			baseX = getBaseX(text, gfx);
			baseY = getBaseY(text, gfx);
		}
		Color pre = gfx.getColor();
		gfx.setColor(Color.RED);
		gfx.setFont(font);
		gfx.drawString(text, baseX, baseY);
		gfx.setColor(pre);
	}
	
	private int getBaseX(String string, Graphics gfx){
		return (800-fontMetrics.stringWidth(string))/2;
	}
	
	private int getBaseY(String string, Graphics gfx){
		return 300 + fontMetrics.getHeight()/2;
	}

}
