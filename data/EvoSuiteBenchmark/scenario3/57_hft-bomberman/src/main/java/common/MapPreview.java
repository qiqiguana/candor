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
    public ImageIcon getMapPreview(int previewWidth);
}
