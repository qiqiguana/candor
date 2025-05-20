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

    /**
     * Loads the {@link ImageIcon} that is located at the specified path.
     *
     * @param path The location of the image within the classpath.
     * @return The loaded {@link ImageIcon}
     */
    public static ImageIcon getImageIcon(String path) {
        return new ImageIcon(getImage(path));
    }
}
