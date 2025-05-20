package client.gui.components;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import sound.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import common.ResourceService;

public class MyButton extends JButton implements MouseListener{

	/**
	 * @param args
	 */

	String text = "";
	public static Font font = new Font("Arial", 0, 14);
	public static int offset = 0;
	
	// Image cache
	Image imageNormal;
	Image imageClicked;
	
	boolean isClicked = false;

	public MyButton() {
		super();
		init();
	}

	public MyButton(String text) {
		super();
		this.text = text;
		init();
	}

	private void init() {
		this.setLayout(null);
		this.addMouseListener(this);
		// load images once
		this.imageNormal = ResourceService.getImage("/images/button.png");
		this.imageClicked = ResourceService.getImage("/images/button_2.png");

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
		g.drawImage(imageNormal, 0, 0, g.getClipBounds().width, g.getClipBounds().height,
				null);
		this.setFont(font);
		int labelWidth = 9 * text.length();
		int labelheigth = this.getFont().getSize();
		Rectangle reg_this = this.getBounds();
		int div_x = reg_this.x;
		int div_y = reg_this.y;
		if (labelWidth < reg_this.width) {
			div_x = (reg_this.width - labelWidth) / 2;
		}
		if ((labelheigth / 2) < reg_this.height) {
			div_y = (reg_this.height + (labelheigth / 2)) / 2;
		}
		if (isClicked){
			offset = 2;
		}else {
			offset = 0;
		}
		// TODO Kay
		g.setColor(new Color(86,41,0));
		g.drawString(text, div_x + 2, div_y + 2);

		// TODO Kay
		g.setColor(new Color(125, 60, 0));
		g.drawString(text,offset +  div_x - 1, offset + div_y + 1);
		g.drawString(text,offset +  div_x - 1, offset + div_y - 1);
		g.drawString(text,offset +  div_x + 1, offset + div_y - 1);
		g.drawString(text, offset + div_x + 1, offset + div_y + 1);

		// TODO Kay
		g.setColor(new Color(255,216,0));
		g.drawString(text, offset + div_x, offset + div_y);
		g.setColor(Color.white);

		int borderwidth = 3;
		if (!isClicked) {
			g.drawImage(imageNormal, reg_this.width - borderwidth, 0, borderwidth, reg_this.height, null);
			g.drawImage(imageClicked, 0, reg_this.height - borderwidth, reg_this.width, borderwidth, null);
		} else {
			g.drawImage(imageNormal, 0, 0, borderwidth, reg_this.height, null);
			g.drawImage(imageClicked, 0, 0, reg_this.width, borderwidth, null);
		}

	}


	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {
		SoundPlayer.getInstance().button2();
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		isClicked = true;
		SoundPlayer.getInstance().button();
		repaint();
	}

	public void mouseReleased(MouseEvent arg0) {
		isClicked = false;
		repaint();
	}


	
}
