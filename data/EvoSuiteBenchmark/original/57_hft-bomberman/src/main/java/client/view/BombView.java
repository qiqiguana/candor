/**
 * 
 */
package client.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import client.ClientBomb;

import common.Bomb;
import common.Constants;

/**
 * @author andi
 * 
 */
public class BombView extends GameObjectView {

	private Image bombImage;

	private int scaleSinX;

	private int explosionState = 1;

	/**
	 * @param gameObject
	 * @param gfxFactory
	 */
	public BombView(Bomb gameObject, GfxFactory gfxFactory) {
		super(gameObject, gfxFactory);
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
		Bomb bomb = (Bomb) gameObject;
		
	
		
		bombImage = gfxFactory.getBombImage(bomb.getPlanterId());
		Graphics2D g2fx = (Graphics2D) gfx;
		AffineTransform preTf = g2fx.getTransform();

		if (gameObject.isActive()) {
			g2fx.translate(gameObject.getPosition().x, gameObject.getPosition().y);
			pump(g2fx);
			paintBomb((Graphics2D) gfx);
		} else {
			paintExplosion((Graphics2D) gfx);
		}

		g2fx.setTransform(preTf);

	}

	/**
	 * Transforms the passed Graphics2D object to show a "pulsating" effect.
	 * 
	 * @param g2fx
	 *            The graphics that will be transformed.
	 */
	private void pump(Graphics2D g2fx) {
		double scale = Math.sin(scaleSinX++) / 8 + 1.0;
		AffineTransform pumpScale = AffineTransform.getScaleInstance(scale, scale);
		g2fx.transform(pumpScale);
	}
	
	private Rectangle getHorizontalHitBox() {
		Point position = gameObject.getPosition();
		int diameter = ((Bomb)gameObject).getDiameter();
		int x = position.getLocation().x
				- ((diameter * Constants.TILE_BORDER) / 2);
		int y = position.getLocation().y - (Constants.TILE_BORDER / 2);
		Rectangle rectangle = new Rectangle(x, y + 5, diameter
				* Constants.EXPLOSION_LENGTH - 1, Constants.EXPLOSION_WIDTH);
		return rectangle;
	}

	private Rectangle getVerticalHitBox() {
		Point position = gameObject.getPosition();
		int diameter = ((Bomb)gameObject).getDiameter();
		int x = position.getLocation().x - (Constants.TILE_BORDER / 2);
		int y = position.getLocation().y
				- ((diameter * Constants.TILE_BORDER) / 2);
		Rectangle rectangle = new Rectangle(x + 5, y,
				Constants.EXPLOSION_WIDTH, diameter
						* Constants.EXPLOSION_LENGTH - 1);
		return rectangle;
	}

	private void paintExplosion(Graphics2D g2fx) {
		if (explosionState == 7) {
			return;
		}
		Shape preClip = g2fx.getClip();
		ClientBomb bomb = (ClientBomb) gameObject;
		int diameter = bomb.getDiameter();
		int id = bomb.getPlanterId();

		explosionState = explosionState + 1;
		Image explosionImage = gfxFactory.getExplosionImage(diameter, id);
		g2fx.clip(bomb.getSpreadBox());
		g2fx.drawImage(explosionImage, bomb.getPosition().x-explosionImage.getWidth(null) / 2, bomb.getPosition().y-explosionImage.getHeight(null) / 2, null);
		g2fx.setClip(preClip);
		this.setVisible(false);
	}

	private void paintBomb(Graphics2D g2fx) {
		g2fx.drawImage(bombImage, -20, -20, null);
	}

}
