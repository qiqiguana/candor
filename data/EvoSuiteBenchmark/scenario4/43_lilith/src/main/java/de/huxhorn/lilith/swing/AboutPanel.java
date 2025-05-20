package de.huxhorn.lilith.swing;

import de.huxhorn.sulky.swing.GraphicsUtilities;
import de.huxhorn.sulky.swing.filters.ColorTintFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

/**
 * <code>AboutPanel</code> is a component which has a background-image and a
 * rectangle in which a given text is scrolling (the scroll-area). You may also
 * specify an Image (e.g. a png-file with alpha-channel) that is drawn before
 * the scroll-text itself. An optional version-string may be given that will be
 * painted centered relative to the scroll-area.
 *
 * @author Joern Huxhorn
 */
public class AboutPanel extends JComponent {

    private final Logger logger = LoggerFactory.getLogger(AboutPanel.class);

    public static final String BACKGROUND_IMAGE_RESOURCE = "background.png";

    public static final String ABOUT_IMAGE_RESOURCE = "about.png";

    public static final String TEXT_RESOURCE_PREFIX = "about.";

    public static final String SCROLL_TEXT_RESOURCE = TEXT_RESOURCE_PREFIX + "scroll.text";

    public static final String VERSION_TEXT_RESOURCE = TEXT_RESOURCE_PREFIX + "version.text";

    public static final String VERSION_HEIGHT_RESOURCE = TEXT_RESOURCE_PREFIX + "version.height";

    public static final String SCROLL_AREA_RESOURCE_BASE = TEXT_RESOURCE_PREFIX + "scroll.area.";

    public static final String SCROLL_AREA_X_RESOURCE = SCROLL_AREA_RESOURCE_BASE + "x";

    public static final String SCROLL_AREA_Y_RESOURCE = SCROLL_AREA_RESOURCE_BASE + "y";

    public static final String SCROLL_AREA_WIDTH_RESOURCE = SCROLL_AREA_RESOURCE_BASE + "width";

    public static final String SCROLL_AREA_HEIGHT_RESOURCE = SCROLL_AREA_RESOURCE_BASE + "height";

    public static final String SCROLL_AREA_TOOLTIP_TEXT_RESOURCE = SCROLL_AREA_RESOURCE_BASE + "tooltip.text";

    public static final String TEXT_RESOURCE_BUNDLE_RESOURCE = "TextResources";

    public static final int MOUSE_DISABLED = 0;

    public static final int MOUSE_COMPONENT = 1;

    public static final int MOUSE_SCROLLAREA = 2;

    public static final int MOUSE_BACKGROUND = 3;

    //private static final int SCROLL_SLEEP_TIME = 50;
    private static final int SCROLL_PIXELS = 1;

    private BufferedImage backgroundImage;

    private BufferedImage aboutImage;

    //private ImageIcon aboutImageIcon;
    private FontMetrics fontMetrics;

    private Insets insets;

    private Dimension size;

    private Dimension preferredSize;

    private Point offscreenOffset;

    private String[] scrollLines;

    private String versionText;

    private String scrollAreaToolTipText;

    private int versionHeight;

    private int scrollPosition;

    private int maxScrollPosition;

    private int minScrollPosition;

    private Rectangle maxScrollArea;

    private Rectangle backgroundImageArea;

    private Rectangle translatedBackgroundImageArea;

    private Rectangle translatedScrollArea;

    private Rectangle scrollArea;

    private Rectangle paintArea;

    private BufferedImage offscreenImage;

    private BufferedImage scrollImage;

    private boolean scrolling;

    //private boolean painted;
    private int mouseEventHandling = MOUSE_BACKGROUND;

    //private transient Thread scrollThread;
    private boolean debug;

    private Timer timer;

    /**
     * Creates a new <code>AboutPanel</code> initialized with the given parameters.
     *
     * @param backgroundImageUrl The URL to the Background-Image of the
     *                           AboutPanel. This parameter is mandatory.
     * @param scrollArea         The Rectangle inside the background-image where
     *                           scrolling should take place. This parameter is optional. If it's null
     *                           then the scroll-area is set to (0, 0, background.width,
     *                           background.height).
     */
    public AboutPanel(URL backgroundImageUrl, Rectangle scrollArea, String scrollText) throws IOException {
    }

    public boolean isDebug();

    public void setDebug(boolean debug);

    /**
     * Creates a new <code>AboutPanel</code> initialized with the given parameters.
     *
     * @param backgroundImageUrl The URL to the Background-Image of the
     *                           AboutPanel. This parameter is mandatory.
     * @param scrollArea         The Rectangle inside the background-image where
     *                           scrolling should take place. This parameter is optional. If it's null
     *                           then the scroll-area is set to (0, 0, background.width,
     *                           background.height).
     * @param versionText        The String describing the version of the program.
     *                           It is painted centered to the scroll-rectangle at the specified height.
     *                           This parameter is optional.
     * @param versionHeight      The height at which the version-string is
     *                           supposed to be painted. This parameter is optional but should be given
     *                           a correct value if versionText!=null..
     */
    public AboutPanel(URL backgroundImageUrl, Rectangle scrollArea, String scrollText, String versionText, int versionHeight) throws IOException {
    }

    /**
     * Creates a new <code>AboutPanel</code> initialized with the given parameters.
     *
     * @param backgroundImageUrl The URL to the Background-Image of the
     *                           AboutPanel. This parameter is mandatory.
     * @param scrollArea         The Rectangle inside the background-image where
     *                           scrolling should take place. This parameter is optional. If it's null
     *                           then the scroll-area is set to (0, 0, background.width,
     *                           background.height).
     * @param imageUrl           The URL to the Image that will be painted at the
     *                           start of the scroll-area. This parameter is optional.
     * @param versionText        The String describing the version of the program.
     *                           It is painted centered to the scroll-rectangle at the specified height.
     *                           This parameter is optional.
     * @param versionHeight      The height at which the version-string is
     *                           supposed to be painted. This parameter is optional but should be given
     *                           a correct value if versionText!=null..
     */
    public AboutPanel(URL backgroundImageUrl, Rectangle scrollArea, String scrollText, URL imageUrl, String versionText, int versionHeight) throws IOException {
    }

    public AboutPanel() {
    }

    private void init(URL backgroundImageUrl, Rectangle scrollArea, String scrollText, URL imageUrl, String versionText, int versionHeight) throws IOException;

    private void initAttributes();

    public void setScrollText(String ScrollText);

    protected void setScrollLines(String[] scrollLines);

    /**
     * Sets the backgroundImage attribute of the <code>AboutPanel</code> object
     */
    public void setBackgroundImage(URL imageUrl) throws IOException;

    /**
     * Sets the backgroundImage attribute of the <code>AboutPanel</code> object
     *
     * @param BackgroundImage The new backgroundImage value
     */
    public void setBackgroundImage(BufferedImage BackgroundImage);

    public void setAboutImage(URL imageUrl) throws IOException;

    public void setAboutImage(BufferedImage AboutImage);

    /**
     * Sets the scrollArea attribute of the <code>AboutPanel</code> object
     *
     * @param ScrollArea The new scrollArea value
     */
    public void setScrollArea(Rectangle ScrollArea);

    /**
     * Description of the Method
     */
    private void flushScrollImage();

    /**
     * Description of the Method
     */
    private void flushOffscreenImage();

    /**
     * Description of the Method
     */
    private void updateBackgroundAttributes();

    /**
     * Sets the ToolTipText that will appear if the user moves the mouse over the
     * scroll-area of this component.
     *
     * @param toolTipText The new ScrollAreaToolTipText value
     */
    public void setScrollAreaToolTipText(String toolTipText);

    /**
     * Gets the ScrollAreaToolTipText attribute of the <code>AboutPanel</code>
     * object
     *
     * @return The ScrollAreaToolTipText value
     */
    public String getScrollAreaToolTipText();

    /**
     * This method returns ScrollAreaToolTipText if the point of the <code>MouseEvent</code>
     * is inside the scroll-area and <code>null</code> otherwise.<p />
     * <p/>
     * It's needed by the <code>ToolTipManager</code> .
     *
     * @param evt a <code>MouseEvent</code>.
     * @return The toolTipText value for the <code>ToolTipManager</code>.
     */
    public String getToolTipText(MouseEvent evt);

    protected boolean handleMouseEvent(MouseEvent evt);

    /**
     * Increases the ScrollPosition by SCROLL_PIXELS. This method is called by the
     * scroll-thread and calls <code>setScrollPosition</code>, therefore causing a
     * repaint of the scroll-area..
     *
     * @see #setScrollPosition
     */
    protected void increaseScrollPosition();

    /**
     * Sets the scrollPosition attribute of the <code>AboutPanel</code> object. The
     * value will be corrected according Minimum- and MaximumScrollPosition.
     * Changing the scroll-position will result in a repaint of the scroll-area.
     *
     * @param scrollPosition The new scrollPosition value. This value indicates
     *                       the height-offset of the scroll-area.
     * @see #getMinimumScrollPosition
     * @see #getMaximumScrollPosition
     */
    public void setScrollPosition(int scrollPosition);

    /**
     * Gets the ScrollPosition attribute of the <code>AboutPanel</code> object
     *
     * @return this value indicates the height-offset of the scroll-area.
     */
    public int getScrollPosition();

    /**
     * Gets the MinimumScrollPosition attribute of the <code>AboutPanel</code>
     * object. It's value is the negated value of the scroll-area-height.
     *
     * @return The MinimumScrollPosition value
     */
    public int getMinimumScrollPosition();

    /**
     * Gets the MaximumScrollPosition attribute of the <code>AboutPanel</code>
     * object. It's value is the height needed for all lines of text plus (if
     * available) the height of the image with an additional empty line.
     *
     * @return The MaximumScrollPosition value
     */
    public int getMaximumScrollPosition();

    /**
     * This method creates the offscreen-image when needed (when called for the
     * first time or recreated because of a changed font) and updates it on
     * subsequent calls by calling <code>updateOffscreenImage()</code>.
     */
    private void processOffscreenImage();

    /**
     * Updates the offscreen-image to represent the current scroll-position. It
     * calls <code>initScrollImage()</code>.
     *
     * @param g <code>Graphics</code>-object
     */
    private void drawScrollArea(Graphics2D g);

    /**
     * Initializes the scroll-image if needed. The scroll-image is as high as
     * needed to contain all the scroll-lines and (if available) the image.
     */
    private void initScrollImage();

    public static ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal);

    /**
     * Sets the font attribute of the <code>AboutPanel</code> object. Setting it
     * will result in the recreation of all buffers. The font can even be safely
     * changed while the component is visible. It will be used for the version- and
     * scroll-text.<p />
     * <p/>
     * If the parameter is <code>null</code> then <code>UIManager.getFont( "Label.font" )</code>
     * will be used.
     *
     * @param newFont The new font value.
     */
    public void setFont(Font newFont);

    /**
     * Paints this component.
     *
     * @param _g <code>Graphics</code>-object
     */
    public void paintComponent(Graphics _g);

    /**
     * Makes sure that the private attributes size, paintArea, offscreenOffset and
     * translated areas have sane values. It's called on component-resize.
     */
    private void calculateAttributes();

    /**
     * This methods takes the insets (the border) of this component into account
     * when the preferred size is calculated. Any border will work. It is called by
     * the property-change-listener if the border was changed.
     */
    protected void calculatePreferredSize();

    /**
     * This method requests a repaint of the scroll-area. The rest of the component
     * will not be repainted. It is called by <code>setScrollPosition()</code> .
     *
     * @see
     */
    private void repaintScrollArea();

    /**
     * This method calls <code>super.addNotify()</code> and notifies the
     * scroll-thread by calling <code>setScrolling(true)</code>. It also
     * (re)initializes the scroll-position to MinimumScrollPosition (this is always
     * the negative height of the scroll-rectangle) and registers tbis component at
     * the <code>ToolTipManager</code>.
     *
     * @see #setScrolling
     * @see #setScrollPosition
     * @see #getMinimumScrollPosition
     */
    public void addNotify();

    /**
     * This method calls <code>super.removeNotify()</code> and sends the
     * scroll-thread into a wait-state by calling <code>setScrolling(false)</code>
     * . It also unregisters this component from the <code>ToolTipManager</code>.
     *
     * @see #setScrolling
     */
    public void removeNotify();

    /**
     * This method is used to set the scrolling-property of this component. A value
     * of <code>true</code> will notify the scroll-thread that it has to resume
     * work. A value of <code>false</code> will send it into wait-state instead.
     *
     * @param Scrolling The new scrolling value
     */
    public void setScrolling(boolean Scrolling);

    /**
     * This method returns <code>true</code> if scrolling is currently active. If
     * it returns <code>false</code> then the scroll-thread is waiting.
     *
     * @return The scrolling value
     */
    public boolean isScrolling();

    /**
     * Description of the Class
     *
     * @author Joern Huxhorn
     */
    class AboutComponentListener extends ComponentAdapter {

        /**
         * Description of the Method
         *
         * @param e Description of the Parameter
         */
        public void componentResized(ComponentEvent e) {
            AboutPanel.this.calculateAttributes();
        }
    }

    /**
     * Description of the Class
     *
     * @author Joern Huxhorn
     */
    class AboutPropertyChangeListener implements PropertyChangeListener {

        /**
         * Description of the Method
         *
         * @param evt Description of the Parameter
         */
        public void propertyChange(PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            if (propertyName.equals("border")) {
                calculatePreferredSize();
            } else if (propertyName.equals("foreground")) {
                flushScrollImage();
            } else if (propertyName.equals("background")) {
                flushScrollImage();
            }
            //			else if ( propertyName.equals( "locale" ) )
            //			{
            //				initResources();
            //			}
        }
    }

    /**
     * This <code>MouseInputListener</code> handles the pause/resume on click as
     * well as the dragging inside the scroll-area.
     *
     * @author Joern Huxhorn
     */
    class AboutMouseInputListener extends MouseInputAdapter {

        Point lastPoint = null;

        boolean scrollingBeforePress = false;

        boolean dragged = false;

        /**
         * Description of the Method
         *
         * @param evt Description of the Parameter
         */
        public void mousePressed(MouseEvent evt) {
            if (handleMouseEvent(evt)) {
                // always stop scrolling if mouse is pressed inside
                // the scroll-area
                lastPoint = evt.getPoint();
                scrollingBeforePress = isScrolling();
                setScrolling(false);
            } else {
                lastPoint = null;
            }
            dragged = false;
        }

        /**
         * Description of the Method
         *
         * @param evt Description of the Parameter
         */
        public void mouseReleased(MouseEvent evt) {
            if (dragged) {
                // set scrolling-attribute to the value before the user dragged.
                lastPoint = null;
                setScrolling(scrollingBeforePress);
            }
        }

        /**
         * Description of the Method
         *
         * @param evt Description of the Parameter
         */
        public void mouseClicked(MouseEvent evt) {
            // this is only called after mouseReleased if no drag occurred.
            if (handleMouseEvent(evt)) {
                // toggle scrolling.
                setScrolling(!scrollingBeforePress);
            }
            dragged = false;
        }

        /**
         * Description of the Method
         *
         * @param evt Description of the Parameter
         */
        public void mouseDragged(MouseEvent evt) {
            // only drag if original press was inside scroll-rectangle
            if (lastPoint != null) {
                dragged = true;
                Point currentPoint = evt.getPoint();
                int yOffset = lastPoint.y - currentPoint.y;
                setScrollPosition(getScrollPosition() + yOffset);
                lastPoint = currentPoint;
            }
        }
    }

    private class TimerActionListener implements ActionListener {

        private final Logger logger = LoggerFactory.getLogger(AboutPanel.class);

        private long lastRepaintStart;

        private long frequency = 25;

        public void actionPerformed(ActionEvent e) {
            long currentTime = System.nanoTime() / 1000000;
            long meanTime = currentTime - lastRepaintStart;
            if (meanTime > frequency) {
                if (logger.isDebugEnabled())
                    logger.debug("Tick! meanTime={}", meanTime);
                increaseScrollPosition();
                lastRepaintStart = currentTime;
            }
        }
    }
}
