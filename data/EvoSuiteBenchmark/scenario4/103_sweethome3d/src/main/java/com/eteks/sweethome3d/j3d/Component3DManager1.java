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

    private static final String CHECK_OFF_SCREEN_IMAGE_SUPPORT = "com.eteks.sweethome3d.j3d.checkOffScreenSupport";

    private static Component3DManager instance;

    private RenderingErrorObserver renderingErrorObserver;

    // (use Object class to ensure Component3DManager class can run with Java 3D 1.3.1)
    private Object renderingErrorListener;

    private Boolean offScreenImageSupported;

    private GraphicsConfiguration defaultScreenConfiguration;

    private Component3DManager() {
    }

    /**
     * Returns the template to configure the graphics of canvas 3D.
     */
    private GraphicsConfigTemplate3D createGraphicsConfigurationTemplate3D();

    /**
     * Returns an instance of this singleton.
     */
    public static Component3DManager getInstance();

    /**
     * Sets the current rendering error listener bound to <code>VirtualUniverse</code>.
     */
    public void setRenderingErrorObserver(RenderingErrorObserver observer);

    /**
     * Returns the current rendering error listener bound to <code>VirtualUniverse</code>.
     */
    public RenderingErrorObserver getRenderingErrorObserver();

    /**
     * Returns <code>true</code> if offscreen is supported in Java 3D on user system.
     * Will always return <code>false</code> if <code>com.eteks.sweethome3d.j3d.checkOffScreenSupport</code>
     * system is equal to <code>false</code>. By default, <code>com.eteks.sweethome3d.j3d.checkOffScreenSupport</code>
     * is equal to <code>true</code>.
     */
    public boolean isOffScreenImageSupported();

    /**
     * Returns a new <code>canva3D</code> instance that will call <code>renderingObserver</code>
     * methods during the rendering loop.
     * @throws IllegalRenderingStateException  if the canvas 3D couldn't be created.
     */
    private Canvas3D getCanvas3D(GraphicsConfiguration deviceConfiguration, boolean offscreen, final RenderingObserver renderingObserver);

    /**
     * Returns a new on screen <code>canva3D</code> instance. The returned canvas 3D will be associated
     * with the graphics configuration of the default screen device.
     * @throws IllegalRenderingStateException  if the canvas 3D couldn't be created.
     */
    public Canvas3D getOnscreenCanvas3D();

    /**
     * Returns a new on screen <code>canva3D</code> instance which rendering will be observed
     * with the given rendering observer. The returned canvas 3D will be associated with the
     * graphics configuration of the default screen device.
     * @param renderingObserver an observer of the 3D rendering process of the returned canvas.
     *            Caution: The methods of the observer will be called in 3D rendering loop thread.
     * @throws IllegalRenderingStateException  if the canvas 3D couldn't be created.
     */
    public Canvas3D getOnscreenCanvas3D(RenderingObserver renderingObserver);

    /**
     * Returns a new on screen <code>canva3D</code> instance which rendering will be observed
     * with the given rendering observer.
     * @param renderingObserver an observer of the 3D rendering process of the returned canvas.
     *            Caution: The methods of the observer will be called in 3D rendering loop thread.
     * @throws IllegalRenderingStateException  if the canvas 3D couldn't be created.
     */
    public Canvas3D getOnscreenCanvas3D(GraphicsConfiguration deviceConfiguration, RenderingObserver renderingObserver);

    /**
     * Returns a new off screen <code>canva3D</code> at the given size.
     * @throws IllegalRenderingStateException  if the canvas 3D couldn't be created.
     *    To avoid this exception, call {@link #isOffScreenImageSupported() isOffScreenImageSupported()} first.
     */
    public Canvas3D getOffScreenCanvas3D(int width, int height);

    /**
     * Returns an image at the given size of the 3D <code>view</code>.
     * This image is created with an off screen canvas.
     * @throws IllegalRenderingStateException  if the image couldn't be created.
     */
    public BufferedImage getOffScreenImage(View view, int width, int height);

    /**
     * An observer that receives error notifications in Java 3D.
     */
    public static interface RenderingErrorObserver {

        void errorOccured(int errorCode, String errorMessage);
    }

    /**
     * Manages Java 3D 1.5 <code>RenderingErrorListener</code> change matching the given
     * rendering error observer.
     */
    private static class RenderingErrorListenerManager {

        public static Object setRenderingErrorObserver(final RenderingErrorObserver observer, Object previousRenderingErrorListener) {
            if (previousRenderingErrorListener != null) {
                VirtualUniverse.removeRenderingErrorListener((RenderingErrorListener) previousRenderingErrorListener);
            }
            RenderingErrorListener renderingErrorListener = new RenderingErrorListener() {

                public void errorOccurred(RenderingError error) {
                    observer.errorOccured(error.getErrorCode(), error.getErrorMessage());
                }
            };
            VirtualUniverse.addRenderingErrorListener(renderingErrorListener);
            return renderingErrorListener;
        }
    }

    /**
     * An observer that receives notifications during the different steps
     * of the loop rendering a canvas 3D.
     */
    public static interface RenderingObserver {

        /**
         * Called before <code>canvas3D</code> is rendered.
         */
        public void canvas3DPreRendered(Canvas3D canvas3D);

        /**
         * Called after <code>canvas3D</code> is rendered.
         */
        public void canvas3DPostRendered(Canvas3D canvas3D);

        /**
         * Called after <code>canvas3D</code> buffer is swapped.
         */
        public void canvas3DSwapped(Canvas3D canvas3D);
    }
}
