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
     * Returns an icon read from <code>content</code>.
     *
     * @param content an object containing an image
     * @param waitingComponent a waiting component. If <code>null</code>, the returned icon will
     *            be read immediately in the current thread.
     */
    public Icon getIcon(Content content, Component waitingComponent) {
        return getIcon(content, -1, waitingComponent);
    }
}
