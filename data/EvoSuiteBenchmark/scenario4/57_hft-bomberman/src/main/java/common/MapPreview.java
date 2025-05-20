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
    }

    /**
     * set imageSet, overrides default map imageSet
     *
     * @param imageSet -
     *            String, imageSet (-folder)
     */
    public void setImageSet(String imageSet);

    /**
     * returns preview of current map
     *
     * @param previewWidth -
     *            Width of preview. Proportions are constrained
     * @return BufferedImage with map preview
     */
    public ImageIcon getMapPreview(int previewWidth);

    /**
     * draws all map tiles to Graphics object
     */
    private void drawMap();

    /**
     * method for scaling/resizing BufferedImages to target width
     *
     * @param bi -
     *            BufferedImage
     * @param targetWidth -
     *            target width of resized image
     * @return BufferedImage - with map preview
     */
    private BufferedImage scale(BufferedImage bi, int targetWidth);

    public Image LoadImage(String fileName);

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
    public void drawTile(Graphics g, Point position, String fileName, Boolean middle);
}
