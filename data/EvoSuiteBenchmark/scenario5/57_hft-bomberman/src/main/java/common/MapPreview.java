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
                mapPreview = ImageIO.read(new File(mapFile + "_" + mapImageSet + ".png"));
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
            mapPreview = new BufferedImage(mapArea.x * Constants.TILE_BORDER, mapArea.y * Constants.TILE_BORDER, BufferedImage.TYPE_INT_RGB);
            // draw map to BufferedImage
            drawMap();
            try {
                ImageIO.write(scale(mapPreview, previewWidth), "png", checkFile);
            } catch (Exception e) {
                logger.error("Caught in getMapPreview(): " + e);
            }
            // return / resize and return
            if (previewWidth == (mapArea.x * Constants.TILE_BORDER)) {
                mapPreviewIcon.setImage((Image) mapPreview);
            } else {
                mapPreviewIcon.setImage((Image) scale(mapPreview, previewWidth));
            }
        }
        return mapPreviewIcon;
    }

    public Image LoadImage(String fileName);

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
}
