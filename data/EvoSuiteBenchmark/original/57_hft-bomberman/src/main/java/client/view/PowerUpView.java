package client.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import org.apache.log4j.Logger;

import common.Constants;
import common.GameObject;
import common.PowerUp;

public class PowerUpView extends GameObjectView {

	private String type = null;

	private Logger logger = Logger.getLogger(PowerUpView.class);

	/**
	 * @param gameObject
	 * @param gfxFactory
	 */
	public PowerUpView(GameObject gameObject, GfxFactory gfxFactory) {
		super(gameObject, gfxFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see client.view.GameObjectView#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics gfx) {
		Image image = null;
		if (gameObject.isActive() && gameObject.isVisible()) {
			image = gfxFactory
					.getPowerUpImage(((PowerUp) gameObject).getType());
		} else if (!gameObject.isActive()) {
			this.setVisible(false);
			return;
		} else {
			return;
		}
		Point position = gameObject.getPosition();
		gfx.drawImage(image, position.x - Constants.HALF_POWERUP, position.y
				- Constants.HALF_POWERUP, null);
	}
}
