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

    /**
     * Returns an icon read from <code>content</code> and rescaled at a given <code>height</code>.
     *
     * @param content an object containing an image
     * @param height the desired height of the returned icon
     * @param waitingComponent a waiting component. If <code>null</code>, the returned icon will
     *            be read immediately in the current thread.
     */
    public Icon getIcon(Content content, final int height, Component waitingComponent) {
        Map<Integer, Icon> contentIcons = this.icons.get(content);
        if (contentIcons == null) {
            contentIcons = Collections.synchronizedMap(new HashMap<Integer, Icon>());
            this.icons.put(content, contentIcons);
        }
        Icon icon = contentIcons.get(height);
        if (icon == null) {
            // Tolerate null content
            if (content == null) {
                icon = new Icon() {

                    public void paintIcon(Component c, Graphics g, int x, int y) {
                    }

                    public int getIconWidth() {
                        return Math.max(0, height);
                    }

                    public int getIconHeight() {
                        return Math.max(0, height);
                    }
                };
            } else if (content == this.errorIconContent || content == this.waitIconContent) {
                // Load error and wait icons immediately in this thread
                icon = createIcon(content, height, null);
            } else if (waitingComponent == null) {
                // Load icon immediately in this thread
                icon = createIcon(content, height, getIcon(this.errorIconContent, height, null));
            } else {
                // For content different from error icon and wait icon,
                // load it in a different thread with a virtual proxy
                icon = new IconProxy(content, height, waitingComponent, getIcon(this.errorIconContent, height, null), getIcon(this.waitIconContent, height, null));
            }
            // Store the icon in icons map
            contentIcons.put(height, icon);
        }
        return icon;
    }
}
