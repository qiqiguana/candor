package client.gui;

import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.gui.components.MyPanel;
import client.view.GameCanvas;

public class Cash extends MyPanel{

	
	GameCanvas game = new GameCanvas(800,600);
	
	public Cash (){
		super();
		setSize(800, 600);
//		game.setBackground(Color.white);
//		game.setSize(800, 600);
		add(game);
	}
	
}
