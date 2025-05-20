package client.view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import common.ResourceService;

/**
 * @author Andreas Glauner
 * @autor Adam Kozielski
 */
public class GfxFactory {

    private HashMap<Integer, Image> wallImageCache;

    private HashMap<String, Image> tileImageCache;

    private HashMap<String, Image> powerUpImageCache;

    private HashMap<String, Image> o;

    private HashMap<Integer, Image>[] playerImageCache;

    private HashMap<Integer, Image>[] bombImageCache;

    private HashMap<Integer, Integer> idTranslator;

    private HashMap<Integer, Image>[] explosionImageCache;

    private ExplosionGfxFactory explosionGfxFactory;

    private int currentId = 1;

    private static String basePath = "/gfx";

    private String tileSet = "ice";

    private Logger logger = Logger.getLogger(GfxFactory.class);

    public GfxFactory() {
    }

    /**
     * @param id The player id.
     * @param state
     * @return
     */
    public Image getPlayerImage(int id, int state);

    public Image getTileImage(String type);

    private int translateId(int id);

    private Image loadImage(String path);

    /**
     * @param id The planter's id.
     * @return
     */
    public Image getBombImage(int id);

    /**
     * @param id The planter's id.
     * @return
     */
    public Image getBombImage(int id, int state);

    public Image getExplosionImage(int diameter, int id);

    public void setTileSet(String tileSet);

    public Image getWallImage(int state);

    /**
     * Power up image loading
     * @param type A String value describing the type of powerUp
     *  *
     */
    public Image getPowerUpImage(String type);
}
