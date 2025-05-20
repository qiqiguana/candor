package de.huxhorn.lilith.swing.table.renderer;

import java.awt.*;
import javax.swing.border.AbstractBorder;

/**
 * This is basically a mutable, simplified LineBorder-EmptyBorder combination.
 */
public class ConditionalBorder extends AbstractBorder {

    public Insets getBorderInsets(Component c, Insets insets) {
        int actualThickness = thickness + innerThickness;
        insets.left = actualThickness;
        insets.top = actualThickness;
        insets.right = actualThickness;
        insets.bottom = actualThickness;
        return insets;
    }
}
