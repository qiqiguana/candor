package de.huxhorn.lilith.swing.table.renderer;

import java.awt.*;
import javax.swing.border.AbstractBorder;

/**
 * This is basically a mutable, simplified LineBorder-EmptyBorder combination.
 */
public class ConditionalBorder extends AbstractBorder {

    /**
     * Returns the insets of the border.
     *
     * @param c the component for which this border insets value applies
     */
    public Insets getBorderInsets(Component c) {
        int actualThickness = thickness + innerThickness;
        return new Insets(actualThickness, actualThickness, actualThickness, actualThickness);
    }
}
