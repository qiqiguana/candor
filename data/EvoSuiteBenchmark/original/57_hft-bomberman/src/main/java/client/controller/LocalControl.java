/**
 * 
 */
package client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.BomberClient;

import common.Player;

/**
 * A key listener that is used to control local player.
 * Received key events will set a flag that can be read later on.
 * The events that were stored this way can be transformed into operations
 * on the associated ClientPlayer object.
 * @author Andi
 * 
 */
public class LocalControl implements KeyListener {

	private final Player player;
	private boolean[] keys = new boolean[1024];

	/**
	 * Creates a new LocalControl for player.
	 * @param player The player to control.
	 */
	public LocalControl(Player player) {
		this.player = player;
	}
	
	/**
	 * Reads the key flags and performs operations on the player based on their state.
	 */
	public void processInput() {
		if(!player.isActive()){
			return;
		}
		if (keys[KeyEvent.VK_RIGHT]) {
			player.moveRight();
		}
		if (keys[KeyEvent.VK_LEFT]) {
			player.moveLeft();
		}
		if (keys[KeyEvent.VK_UP]) {
			player.moveUp();
		}
		if (keys[KeyEvent.VK_DOWN]) {
			player.moveDown();
		}
		if (!keys[KeyEvent.VK_RIGHT] && !keys[KeyEvent.VK_LEFT] &&
				!keys[KeyEvent.VK_UP] && !keys[KeyEvent.VK_DOWN]) {
			player.stop();
		}
		if(keys[KeyEvent.VK_SPACE]){
			player.plantBomb();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO cleanup
		keys[e.getKeyCode()] = true;
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_N:
			BomberClient.getInstance().getCurrentSession().getCurrentRound().toggleDisplayNames();
			break;
//		case KeyEvent.VK_ESCAPE:
//			//TODO strange things happening with this
//		    BomberClient.getInstance().quitRunningSession(player.getId());
//		    break;
		}
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {}

}
