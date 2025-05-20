/**
 * 
 */
package client.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import client.ClientPlayer;

import common.Actor;
import common.Player;

/**
 * @author Andi
 * 
 */
public class PlayerView extends GameObjectView {

	private static final int WIDTH = 30;

	private static final int HEIGHT = 30;

	private Image playerImage;

	private int lastState = 3;

	private boolean swap = true;

	/**
	 * @param gameObject
	 */
	public PlayerView(Player player, GfxFactory gfxFactory) {
		super(player, gfxFactory);
		lastState = getState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see client.view.GameObjectView#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics gfx) {
		if (!active) {
			return;
		}
		paintPlayerShape(gfx);
	}

	private int leftUpperX() {
		return (int) (gameObject.getPosition().x - WIDTH * 0.5);
	}

	private int leftUpperY() {
		return (int) (gameObject.getPosition().y - HEIGHT * 0.5);
	}

	/**
	 * 
	 */
	private void paintPlayerShape(Graphics gfx) {
		Graphics2D g2fx = (Graphics2D) gfx;
		AffineTransform preTf = g2fx.getTransform();
		
		int state = getState();

		playerImage = gfxFactory.getPlayerImage(gameObject.getId(), state);
		int x = gameObject.getPosition().x;
		int y = gameObject.getPosition().y;

		g2fx.translate(x - 20, y - 60);
		gfx.drawImage(playerImage, 0, 0, null);
		g2fx.setTransform(preTf);

		ClientPlayer player = (ClientPlayer) gameObject;
		if (GameView.displayNames()) {
			String name = player.getName();
			Font font = new Font("Arial", Font.BOLD, 14);
			gfx.setColor(Color.BLACK);
			gfx.setFont(font);
			if (name != null) {
				FontMetrics fm = gfx.getFontMetrics();
				int width = fm.stringWidth(name);
				gfx.drawString(name, x - width / 2, y - 65);
			}
			gfx.setColor(Color.WHITE);
		}
		if(state == 112) {
			this.setVisible(false);
		}
	}

	private int getState() {
		Point moveVec = ((Actor) gameObject).getMoveVector();
		if (moveVec.x == 0 && moveVec.y == 0 && gameObject.isActive()) {
			return lastState;
		}
		int state = 3;

		if (!gameObject.isActive()) {
			state = handleInactiveState();
			lastState = state;
			return state;
		} else if (moveVec.y < 0) {
			state = 1;
		} else if (moveVec.y > 0) {
			state = 3;
		} else if (moveVec.x < 0) {
			state = 4;
		} else if (moveVec.x > 0) {
			state = 2;
		} else {
			state = 3;
		}
		if (swap) {
			state = state + 10;
		}
		swap = !swap;
		lastState = state;
		return state;

	}

	private int handleInactiveState() {
		int state;
		if (lastState < 100) {
			state = 101;
		} else {
			state = Math.min(112, lastState + 1);
			if (state == 112) {
				active = false;
			}
		}
		return state;
	}

}
