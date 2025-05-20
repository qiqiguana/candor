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

    /**
     * Power up image loading
     *
     * @param type A String value describing the type of powerUp
     *  *
     */
    public Image getPowerUpImage(String type);
}
