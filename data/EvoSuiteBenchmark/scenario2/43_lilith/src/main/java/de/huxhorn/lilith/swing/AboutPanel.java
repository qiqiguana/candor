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

    /**
     * This method returns ScrollAreaToolTipText if the point of the <code>MouseEvent</code>
     * is inside the scroll-area and <code>null</code> otherwise.<p />
     * <p/>
     * It's needed by the <code>ToolTipManager</code> .
     *
     * @param evt a <code>MouseEvent</code>.
     * @return The toolTipText value for the <code>ToolTipManager</code>.
     */
    public String getToolTipText(MouseEvent evt) {
        if (handleMouseEvent(evt)) {
            return scrollAreaToolTipText;
        }
        return null;
    }
}
