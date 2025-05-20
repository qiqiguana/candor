package com.eteks.sweethome3d.swing;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import com.eteks.sweethome3d.model.Content;
import com.eteks.sweethome3d.tools.ResourceURLContent;

/**
 * Singleton managing icons cache.
 *
 * @author Emmanuel Puybaret
 */
public class IconManager {

    private static IconManager instance;

    // Icon used if an image content couldn't be loaded
    private final Content errorIconContent;

    // Icon used while an image content is loaded
    private final Content waitIconContent;

    // Map storing loaded icons
    private final Map<Content, Map<Integer, Icon>> icons;

    // Executor used by IconProxy to load images
    private ExecutorService iconsLoader;

    private IconManager() {
    }

    /**
     * Returns an instance of this singleton.
     */
    public static IconManager getInstance();

    /**
     * Clears the loaded resources cache and shutdowns the multithreaded service
     * that loads icons.
     */
    public void clear();

    /**
     * Returns the icon displayed for wrong content resized at a given height.
     */
    public Icon getErrorIcon(int height);

    /**
     * Returns the icon displayed for wrong content.
     */
    public Icon getErrorIcon();

    /**
     * Returns <code>true</code> if the given <code>icon</code> is the error icon
     * used by this manager to indicate it couldn't load an icon.
     */
    public boolean isErrorIcon(Icon icon);

    /**
     * Returns the icon displayed while a content is loaded resized at a given height.
     */
    public Icon getWaitIcon(int height);

    /**
     * Returns the icon displayed while a content is loaded.
     */
    public Icon getWaitIcon();

    /**
     * Returns <code>true</code> if the given <code>icon</code> is the wait icon
     * used by this manager to indicate it's currently loading an icon.
     */
    public boolean isWaitIcon(Icon icon);

    /**
     * Returns an icon read from <code>content</code>.
     * @param content an object containing an image
     * @param waitingComponent a waiting component. If <code>null</code>, the returned icon will
     *            be read immediately in the current thread.
     */
    public Icon getIcon(Content content, Component waitingComponent);

    /**
     * Returns an icon read from <code>content</code> and rescaled at a given <code>height</code>.
     * @param content an object containing an image
     * @param height  the desired height of the returned icon
     * @param waitingComponent a waiting component. If <code>null</code>, the returned icon will
     *            be read immediately in the current thread.
     */
    public Icon getIcon(Content content, final int height, Component waitingComponent);

    /**
     * Returns an icon created and scaled from its content.
     * @param content the content from which the icon image is read
     * @param height  the desired height of the returned icon
     * @param errorIcon the returned icon in case of error
     */
    private Icon createIcon(Content content, int height, Icon errorIcon);

    /**
     * Proxy icon that displays a temporary icon while waiting
     * image loading completion.
     */
    private class IconProxy implements Icon {

        private Icon icon;

        public IconProxy(final Content content, final int height, final Component waitingComponent, final Icon errorIcon, Icon waitIcon) {
            this.icon = waitIcon;
            if (iconsLoader == null) {
                iconsLoader = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
            }
            // Load the icon in a different thread
            iconsLoader.execute(new Runnable() {

                public void run() {
                    icon = createIcon(content, height, errorIcon);
                    waitingComponent.repaint();
                }
            });
        }

        public int getIconWidth() {
            return this.icon.getIconWidth();
        }

        public int getIconHeight() {
            return this.icon.getIconHeight();
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            this.icon.paintIcon(c, g, x, y);
        }

        public Icon getIcon() {
            return this.icon;
        }
    }
}
