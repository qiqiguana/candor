/**
 * 
 */
package client.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import org.apache.log4j.Logger;

import common.Constants;
import common.GameObject;
import common.Tile;

/**
 * @author Andi
 * 
 */
public class TileView extends GameObjectView {

	private int state = 1;
	private String imageSet;

	private Logger logger = Logger.getLogger(TileView.class);

	/**
	 * @param gameObject
	 * @param gfxFactory
	 */
	public TileView(GameObject gameObject, GfxFactory gfxFactory) {
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
		if (!gameObject.isActive()) {
			state = Math.min(state + 1, 8);
			image = gfxFactory.getWallImage(state);
			if(state == 8) {
				this.setVisible(false);
			}
		} else {
			String type = ((Tile) gameObject).getType();
			image = gfxFactory.getTileImage(type);
		}
		Point position = gameObject.getPosition();
		gfx.drawImage(image, position.x - Constants.HALF_TILE, position.y  - Constants.HALF_TILE, null);

	}
}
