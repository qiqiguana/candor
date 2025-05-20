/**
 * 
 */
package client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import client.view.GameCanvas;

/**
 * TODO CHECK AND DELETE
 * @author Andi
 * 
 */
public class GameWindow extends JFrame {

	private GameCanvas gameCanvas;
	/**
	 * 
	 */
	public GameWindow() {
		super("Project Chewbacca");
		gameCanvas = new GameCanvas(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(gameCanvas);
		addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				gameCanvas.dispatchEvent(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				gameCanvas.dispatchEvent(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				gameCanvas.dispatchEvent(e);
			}
			
		});
	}
	
	public GameWindow(boolean undecorated){
		this();
		setUndecorated(undecorated);
		pack();
		setVisible(true);
	}
	
	public void addGameKeyListener(KeyListener keyListener){
		gameCanvas.addKeyListener(keyListener);
	}

	/**
	 * @return the gameCanvas
	 */
	public GameCanvas getGameCanvas() {
		return gameCanvas;
	}
	
	
	
}
