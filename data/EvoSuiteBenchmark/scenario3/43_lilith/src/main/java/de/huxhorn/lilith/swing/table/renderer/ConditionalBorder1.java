package de.huxhorn.lilith.swing.table.renderer;

import java.awt.*;
import javax.swing.border.AbstractBorder;

/**
 * This is basically a mutable, simplified LineBorder-EmptyBorder combination.
 */
public class ConditionalBorder extends AbstractBorder {

    /**
     * Reinitialize the insets parameter with this Border's current Insets.
     *
     * @param c the component for which this border insets value applies
     * @param insets the object to be reinitialized
     */
    public Insets getBorderInsets(Component c, Insets insets);
}
