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
     * Loads the {@link Image} that is located at the specified path.
     *
     * @param path The location of the image within the classpath.
     * @return The loaded {@link Image} or null if there was an IOException while trying
     * to read the image from a stream.
     */
    public static Image getImage(String path) {
        BufferedImage image;
        try {
            image = ImageIO.read(getInputStream(path));
        } catch (IOException e) {
            image = null;
        }
        return image;
    }

    /**
     * Creates a connection to the resource that is located at the specified
     * path via an {@link InputStream}.
     * @param path The location of the resource within the classpath.
     * @return The {@link InputStream} that points the requested resource.
     */
    public static InputStream getInputStream(String path);
}
