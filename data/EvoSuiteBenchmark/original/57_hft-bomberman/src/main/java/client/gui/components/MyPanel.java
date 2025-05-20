package client.gui.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.gui.ImageCache;

import common.ResourceService;

public class MyPanel extends JPanel {

	String bild = "null";
	Color col = new Color(0, 0, 255);

	Vector vectorLabels = new Vector();
	Vector<ImageCache> vectorImages = new Vector<ImageCache>();

	String picture = "/images/background.png";

	String titel = "";

	Font font = MyButton.font;
	
	/** Creates a new instance of PicturePanel */
	public MyPanel() {
		this.setLayout(new FlowLayout());	
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon icon;
		Image i;
		icon = ResourceService.getImageIcon(picture);
		i = icon.getImage();

		g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
		g.drawImage(i, 0, 0, g.getClipBounds().width, g.getClipBounds().height,
				null);

		// Draw Titel:
		g.setFont(new Font("Arial", 0, 14));

		int div_x = 2;
		int div_y = 2 + 12;
		// TODO Kay
		// Die Hintergrundfarbe fuer die Schatten
		g.setColor(new Color(86,41,0));
		g.drawString(titel, div_x + 2, div_y + 2);
		
		// TODO Kay
		// Die Farbe für Umrandung
		g.setColor(new Color(125,60,0));
		g.drawString(titel, div_x - 1, div_y + 1);
		g.drawString(titel, div_x - 1, div_y - 1);
		g.drawString(titel, div_x + 1, div_y - 1);
		g.drawString(titel, div_x + 1, div_y + 1);
		// TODO Kay
		// Die Farbe des Textes
		g.setColor(new Color(255,216,0));
		g.drawString(titel, div_x, div_y);

		g.setFont(font);
		String text = "";
		for (int j = 0; j < vectorLabels.size(); j++) {

			JLabel cash = (JLabel) vectorLabels.get(j);

			text = cash.getText();

			if ((text != null) && (cash.isVisible())) {
				if (text.startsWith("<HTML>")){
					text = text.substring(6);
					String [] parts = text.split("<br>");
					div_x = cash.getX();
					div_y = cash.getY() + 14;
					for (int k= 0; k < parts.length; k++){
						String text2 = parts[k];
						// TODO Kay
						g.setColor(new Color(86,41,0));
						g.drawString(text2, div_x + 2, div_y + 2);

						// TODO Kay
						g.setColor(new Color(125,60,0));
						g.drawString(text2, div_x - 1, div_y + 1);
						g.drawString(text2, div_x - 1, div_y - 1);
						g.drawString(text2, div_x + 1, div_y - 1);
						g.drawString(text2, div_x + 1, div_y + 1);

						// TODO Kay
						g.setColor(new Color(255,216,0));
						g.drawString(text2, div_x, div_y);
						
						div_y += 14;
					}
				}else
				if (!text.equals("Image")) {
					div_x = cash.getX();
					div_y = cash.getY() + 14;
					// TODO Kay
					g.setColor(new Color(86,41,0));
					g.drawString(text, div_x + 2, div_y + 2);

					// TODO Kay
					g.setColor(new Color(125,60,0));
					g.drawString(text, div_x - 1, div_y + 1);
					g.drawString(text, div_x - 1, div_y - 1);
					g.drawString(text, div_x + 1, div_y - 1);
					g.drawString(text, div_x + 1, div_y + 1);

					// TODO Kay
					g.setColor(new Color(255,216,0));
					g.drawString(text, div_x, div_y);
				}
			}else 
			
			{
				Rectangle reg = cash.getBounds();
// g.drawImage(cash.getIcon(), reg.x,reg.y, null);
			}
		}
		for (int j = 0; j < vectorImages.size(); j++) {
			ImageCache ic = vectorImages.get(j);
			if(ic.getImg() != null){
				g.drawImage(ic.getImg(), ic.getX(), ic.getY(), ic.getW(), ic.getH(),null);
			}			
		}
	}

	public void setBild(String bild) {
		this.bild = bild;
	}

	public void setColor(Color col) {
		this.col = col;
	}

	public void addLabel(JLabel label) {
		vectorLabels.add(label);
	}
	
	public void addImage(ImageCache ic){
		vectorImages.add(ic);
	}

	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}
	
	public void setFont(Font font){
		this.font = font;
	}
}
