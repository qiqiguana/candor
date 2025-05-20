package client.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import common.ResourceService;

public class SplashFrame extends JFrame{
	
	
	
	public SplashFrame(){
		setUndecorated(true);
		setResizable(false);
		setSize(500, 375);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
		setLocation( (d.width - getSize().width ) / 2, (d.height - getSize().height) / 2 ); 
		setTitle("Fubarman");
		ImageIcon label = ResourceService.getImageIcon("/images/logo.png");
		JLabel picture = new JLabel(label);
		picture.setBounds(0,0,500,375);
		setLayout(null);
		add(picture);
	}

	
	
}
