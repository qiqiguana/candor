package client.gui;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import client.BomberClient;
import client.gui.components.MyPanel;
import client.view.GameCanvas;

import java.awt.color.*;


public class Game extends MyPanel implements KeyListener{
	
	public GameCanvas gameCanvas ;
	
	JPanel cash = new JPanel();
	
	public Game(){
		super();
		setLayout(null);
		this.setBounds(0, 0, 800, 600);
		//setBackground(Color.red);
		gameCanvas = new GameCanvas(800,600);
		gameCanvas.setBounds(0, 0, 800, 600);
//		cash.setBounds(0,0,800,600);
//		cash.setBackground(Color.blue);
		gameCanvas.setBackground(Color.white);
		gameCanvas.setVisible(true);
//		gameCanvas.addMouseListener(this);
//		gameCanvas.setVisible(true);
//		this.add(cash);
		this.add(gameCanvas);
//		this.addMouseListener(this);
		this.setFocusable(true);
		this.setEnabled(true);
		this.requestFocus();
		
	}
		

	@Override
	public void keyPressed(KeyEvent evt) {
		System.out.println("Key pressed");
		if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.out.println("Escape pressedS");
			System.out.println(evt.getKeyCode());
			BomberClient.getInstance().closeSession();
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}
