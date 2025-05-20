package common;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

/**
 * create a ImageIcon Map Preview of a map object
 * 
 * usage example: MapPreview mp = new MapPreview(map); mp.setImageSet("desert");
 * (optional) ImageIcon ii = mp.getMapPreview(150); !!
 * 
 * @author christian
 * 
 */
public class MapPreview {
	private String mapImageSet;
	private Point mapArea;
	private ImageIcon mapPreviewIcon;
	private BufferedImage mapPreview;
	private Map mapMap;
	private String mapFile;

	// map tile Images, for caching purposes
	private static Image TILE_WALL_IMAGE;
	private static Image TILE_STONE_IMAGE;

	private static final Logger logger = Logger.getLogger(MapPreview.class);

	/**
	 * class for creating map Preview of Map map
	 * 
	 * @param map -
	 *            map for which preview should be created
	 */
	public MapPreview(Map map) {
		mapMap = map;
		mapImageSet = map.getImageSet();
		mapArea = map.getArea();
		mapFile = map.getFilePath();
	}

	/**
	 * set imageSet, overrides default map imageSet
	 * 
	 * @param imageSet -
	 *            String, imageSet (-folder)
	 */
	public void setImageSet(String imageSet) {
		this.mapImageSet = imageSet;
	}

	/**
	 * returns preview of current map
	 * 
	 * @param previewWidth -
	 *            Width of preview. Proportions are constrained
	 * @return BufferedImage with map preview
	 */
	public ImageIcon getMapPreview(int previewWidth) {
		// check if file is available, file name is identical to
		// xml file with appended .png
		File checkFile = new File(mapFile + "_" + mapImageSet + ".png");
		if (checkFile.exists()) {
			logger.info("Loading map preview: " + checkFile.getName());
			BufferedImage mapPreview;
			try {
				mapPreview = ImageIO.read(new File(mapFile + "_" + mapImageSet
						+ ".png"));
				mapPreviewIcon = new ImageIcon();
				mapPreviewIcon.setImage((Image) mapPreview);
			} catch (IOException e) {
				logger.error(e);
			}
		} else {
			// image not found, create it
			logger.info("Creating map preview: " + checkFile.getName());
			// preload images
			TILE_WALL_IMAGE = LoadImage("wall");
			TILE_STONE_IMAGE = LoadImage("stone");
			mapPreviewIcon = new ImageIcon();
			mapPreview = new BufferedImage(mapArea.x * Constants.TILE_BORDER,
					mapArea.y * Constants.TILE_BORDER,
					BufferedImage.TYPE_INT_RGB);
			drawMap(); // draw map to BufferedImage

			try {
				ImageIO
						.write(scale(mapPreview, previewWidth), "png",
								checkFile);
			} catch (Exception e) {
				logger.error("Caught in getMapPreview(): " + e);
			}

			// return / resize and return
			if (previewWidth == (mapArea.x * Constants.TILE_BORDER)) {
				mapPreviewIcon.setImage((Image) mapPreview);
			} else {
				mapPreviewIcon
						.setImage((Image) scale(mapPreview, previewWidth));
			}
		}
		return mapPreviewIcon;

	}

	/**
	 * draws all map tiles to Graphics object
	 * 
	 */
	private void drawMap() {
		logger.info("draw mapPreview...");
		Graphics g = mapPreview.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 600);
		for (Tile tile : mapMap) {
			drawTile(g, tile.getPosition(), tile.getType(), true);
		}
		g.dispose();
	}

	/**
	 * method for scaling/resizing BufferedImages to target width
	 * 
	 * @param bi -
	 *            BufferedImage
	 * @param targetWidth -
	 *            target width of resized image
	 * @return BufferedImage - with map preview
	 */
	private BufferedImage scale(BufferedImage bi, int targetWidth) {
		// calculate scale-factor
		double scaleFactor = (double) targetWidth / (double) bi.getWidth();
		logger.info("resize map preview to width: " + targetWidth);
		AffineTransform tx = new AffineTransform();
		tx.scale(scaleFactor, scaleFactor);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(bi, null);
	}

	/**
	 * loads an image-file into an Image object
	 * 
	 * @param fileName -
	 *            String, name of file, w/o path and ending
	 * @return Image
	 */

	public Image LoadImage(String fileName) {
		Image image = null;
		try {
			image = ImageIO.read(new File("resources/gfx/map/" + mapImageSet
					+ "/" + fileName + ".png"));
		} catch (IOException e) {
			logger.error(e.toString());
		}
		return image;
	}

	/**
	 * draws tile at specific position
	 * 
	 * @param g2d -
	 *            Graphics2D object
	 * @param position -
	 *            Point, coordinates
	 * @param fileName -
	 *            String, name of file, w/o path and ending
	 * @param middle -
	 *            is the position of element set to middle, correct the upper
	 *            left coordinate
	 */
	public void drawTile(Graphics g, Point position, String fileName,
			Boolean middle) {
		Image image;
		int posCorrection = 0;
		if (middle) {
			posCorrection = Constants.TILE_BORDER / 2;
		}
		// use cached image or load image
		if (fileName.equals("wall")) {
			image = TILE_WALL_IMAGE;
		} else if (fileName.equals("stone")) {
			image = TILE_STONE_IMAGE;
		} else {
			image = LoadImage(fileName);
		}
		g.drawImage(image, position.x - posCorrection, position.y
				- posCorrection, null);

	}
}
