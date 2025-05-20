package client.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import javax.imageio.ImageIO;

import common.ResourceService;

/**
 * @author andi
 *
 */
public class ExplosionGfxFactory {

	private HashMap<String, Image> explImageCache;

	private Stack<AffineTransform> transformations;

	public ExplosionGfxFactory() {
		explImageCache = new HashMap<String, Image>();
		transformations = new Stack<AffineTransform>();
	}

	public Image getExplosionImage(int diameter, int id) {
		BufferedImage image = new BufferedImage(diameter*40, diameter*40, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gfx = image.createGraphics();

		int size = image.getHeight();
		int center = diameter / 2;

		Image end = loadExplosionImage(id, "end");
		Image middle = loadExplosionImage(id, "cross");
		Image conn = loadExplosionImage(id, "straight");

		
		for (int y = 0; y < diameter; y++) {
			pushTransform(gfx.getTransform());
			if (y == 0) {
				Point tr = getTranslation(center, y);
				gfx.translate(tr.x, tr.y);
				gfx.drawImage(end, 0, 0, null);
			} else if (y == diameter - 1) {
				Point tr = getTranslation(center, y);
				gfx.rotate(Math.PI, size/2,size/2);
				gfx.translate(tr.x, 0);
				gfx.drawImage(end, 0, 0, null);
			} else if (y == center) {
				Point tr = getTranslation(center, y);
				gfx.translate(tr.x, tr.y);
				gfx.drawImage(middle, 0, 0, null);
			} else {
				Point tr = getTranslation(center, y);
				gfx.translate(tr.x, tr.y);
				gfx.drawImage(conn, 0, 0, null);
			}
			gfx.setTransform(popTransform());
		}
		
		for (int x = 0; x < diameter; x++) {
			pushTransform(gfx.getTransform());
			
			if (x == 0) {
				Point tr = getTranslation(x, center);
				gfx.rotate(-Math.PI/2, size/2,size/2);
				gfx.translate(tr.y, 0);
				gfx.drawImage(end, 0, 0, null);
			} else if (x == diameter - 1) {
				Point tr = getTranslation(x, center);
				gfx.rotate(Math.PI/2, size/2,size/2);
				gfx.translate(tr.y, 0);
				gfx.drawImage(end, 0, 0, null);
				
			} else if (x == center) {
				Point tr = getTranslation(x,center);
				gfx.translate(tr.x, tr.y);
				gfx.drawImage(middle, 0, 0, null);
			} else {
				Point tr = getTranslation(x, center);
				gfx.rotate(Math.PI/2, size/2,size/2);
				gfx.translate(tr.y, tr.x);
				gfx.drawImage(conn, 0, 0, null);
			}
			gfx.setTransform(popTransform());
		}
		return image;

	}

	private Image loadExplosionImage(int id, String imagename) {

		String path = "/gfx/player/" + id + "/" + imagename + ".png";
		if (explImageCache.keySet().contains(path)) {
			return explImageCache.get(path);
		} else {
			return loadImage(path);
		}

	}

	private Image loadImage(String path) {
		return ResourceService.getImage(path);
	}

	private void pushTransform(AffineTransform transform) {
		transformations.push(transform);
	}

	private AffineTransform popTransform() {
		return transformations.pop();
	}

	private Point getTranslation(int tileX, int tileY) {
		int trX = tileX * 40;
		int trY = tileY * 40;
		return new Point(trX, trY);
	}

}
