package common;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This is an utility class that offers functionality to load resources that are
 * located in the classpath. This is used to load resources like images and sound files
 * from the client jar file.
 *
 * @author andi
 */
public class ResourceService {

    public static InputStream getInputStream(String path) {
        InputStream resourceAsStream = ResourceService.class.getResourceAsStream(path);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("No such resource: " + path);
        }
        return resourceAsStream;
    }
}
