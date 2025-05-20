package com.eteks.sweethome3d.j3d;

import java.awt.GraphicsConfigTemplate;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.IllegalRenderingStateException;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.RenderingError;
import javax.media.j3d.RenderingErrorListener;
import javax.media.j3d.Screen3D;
import javax.media.j3d.View;
import javax.media.j3d.VirtualUniverse;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewingPlatform;

/**
 * Manager of <code>Canvas3D</code> instantiations and Java 3D error listeners.
 * Note: this class is compatible with Java 3D 1.3 at runtime but requires Java 3D 1.5 to compile.
 *
 * @author Emmanuel Puybaret
 */
public class Component3DManager {

    /**
     * Returns a new on screen <code>canva3D</code> instance which rendering will be observed
     * with the given rendering observer. The returned canvas 3D will be associated with the
     * graphics configuration of the default screen device.
     *
     * @param renderingObserver an observer of the 3D rendering process of the returned canvas.
     *            Caution: The methods of the observer will be called in 3D rendering loop thread.
     * @throws IllegalRenderingStateException if the canvas 3D couldn't be created.
     */
    public Canvas3D getOnscreenCanvas3D(RenderingObserver renderingObserver) {
        return getCanvas3D(null, false, renderingObserver);
    }
}
