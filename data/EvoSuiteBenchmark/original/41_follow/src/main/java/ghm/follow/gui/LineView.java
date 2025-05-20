package ghm.follow.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.plaf.TextUI;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainView;

public class LineView extends PlainView
{
	public LineView(Element e)
	{
		super(e);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.text.PlainView#drawLine(int, java.awt.Graphics, int, int)
	 */
	protected void drawLine(int lineIndex, Graphics g, int x, int y)
	{
		JTextComponent comp = (JTextComponent) getContainer();
		TextUI ui = comp.getUI();
		if (ui instanceof LineTextUI)
		{
			LineTextUI ltui = (LineTextUI) ui;
			// highlight current line
			if (ltui.getSelectedIndex() > -1 && ltui.getSelectedIndex() == lineIndex)
			{
				FontMetrics fm = comp.getFontMetrics(comp.getFont());
				Rectangle rec = new Rectangle((int) x, (int) y - fm.getMaxAscent() + 1, comp
						.getWidth(), fm.getMaxAscent());
				Graphics2D g2d = (Graphics2D) g;
				Color blue = new Color(204, 204, 255);
				// g2d.setColor(blue);
				// g2d.fill(rec);
				g2d.setColor(blue.darker());
				g2d.draw(rec);
			}
		}
		super.drawLine(lineIndex, g, x, y);
	}

}