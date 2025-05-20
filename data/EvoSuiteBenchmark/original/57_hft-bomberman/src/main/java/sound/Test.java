package sound;
// Autor Ghazwan
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import javax.swing.JFrame;

import org.apache.log4j.Level;

import logging.*;


public class Test extends JFrame implements KeyListener {
	
	//for logging
	private static Logging logger = Logging.getInstance();

	public Test(){
		
		this.setSize(100,100);
		this.setVisible(true);
		this.addKeyListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	//private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		//Thread beispiel1 = new Thread( new Sound( ISound.INTRO ) );
		//beispiel1.start();
		//Thread beispiel2 = new Thread( new Sound( ISound.BUTTON ) );
		//beispiel2.start();
		//Thread beispiel3 = new Thread( new Sound( ISound.HOLY ) );
		//beispiel3.start();
		
	/*	Sound test =new Sound();
		test.intro();
		test.button();
		test.button2();
		test.hintergrund();
		test.bWerfen();
		test.explosion1();
		test.explosion2();
		test.kasse();
		test.powerUp();
		test.verliert();
		test.gewinnt();
		test.outro();
	  //test.gameOver();*/
	
		Test t = new Test();
	}

	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		int keyCode = arg0.getKeyCode(); 
		switch(keyCode)
		{
			case KeyEvent.VK_1:
				
				Thread tintro =new Thread( new Sound( ISound.INTRO ) );
				tintro.start();
				//for logging ausgeben
				logger.log( Level.INFO, this, "Hier wird sound Intro aufgerufen"  );
				break;
			
			case KeyEvent.VK_2:
				Thread tbutton =new Thread( new Sound( ISound.BUTTON ) );
				tbutton.start();
				//for logging ausgeben
				logger.log( Level.INFO, this, "Hier wird sound Button aufgerufen"  );
				break;
				
			case KeyEvent.VK_3:
				Thread tbutton2 =new Thread( new Sound( ISound.BUTTON2 ) );
				tbutton2.start();
				//for logging ausgeben
				logger.log( Level.INFO, this, "Hier wird sound Button2 aufgerufen"  );
				break;
				
			case KeyEvent.VK_4:
				Thread thintergrund =new Thread( new Sound( ISound.HINTERGRUND ) );
				thintergrund.start();
				break;
				
			case KeyEvent.VK_5:
				Thread tbWerfen =new Thread( new Sound( ISound.B_WERFEN ) );
				tbWerfen.start();
				break;
				
			case KeyEvent.VK_6:
				Thread texplosion1 =new Thread( new Sound( ISound.EXPLOSION1 ) );
				texplosion1.start();
				break;
				
			case KeyEvent.VK_7:
				Thread texplosion2 =new Thread( new Sound( ISound.EXPLOSION2 ) );
				texplosion2.start();
				break;
				
			case KeyEvent.VK_8:
				Thread tkasse =new Thread( new Sound( ISound.KASSE ) );
				tkasse.start();
				break;
				
			case KeyEvent.VK_9:
				Thread tpowerUp =new Thread( new Sound( ISound.POWER_UP ) );
				tpowerUp.start();
				break;
				
			case KeyEvent.VK_0:
				Thread tverliert =new Thread( new Sound( ISound.VERLIERT ) );
				tverliert.start();
				break;
				
			case KeyEvent.VK_Q:
				Thread tgewinnt =new Thread( new Sound( ISound.GEWINNT ) );
				tgewinnt.start();
				break;
				
			case KeyEvent.VK_W:
				Thread toutro =new Thread( new Sound( ISound.OUTRO ) );
				toutro.start();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) 
	{
	}
	@Override
	public void keyTyped(KeyEvent arg0) 
	{
	}
}

