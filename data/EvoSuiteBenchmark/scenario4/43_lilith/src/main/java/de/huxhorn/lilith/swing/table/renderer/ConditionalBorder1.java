package de.huxhorn.lilith.swing.table.renderer;

import java.awt.*;
import javax.swing.border.AbstractBorder;

/**
 * This is basically a mutable, simplified LineBorder-EmptyBorder combination.
 */
public class ConditionalBorder extends AbstractBorder {

    private static final long serialVersionUID = -2372658104457011019L;

    private int thickness;

    private int innerThickness;

    private Color borderColor;

    public ConditionalBorder(Color color) {
    }

    public ConditionalBorder(Color color, int thickness) {
    }

    public ConditionalBorder(Color color, int thickness, int innerThickness) {
    }

    /**
     * Paints the border for the specified component with the
     * specified position and size.
     *
     * @param c      the component for which this border is being painted
     * @param g      the paint graphics
     * @param x      the x position of the painted border
     * @param y      the y position of the painted border
     * @param width  the width of the painted border
     * @param height the height of the painted border
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height);

    /**
     * Returns the insets of the border.
     *
     * @param c the component for which this border insets value applies
     */
    public Insets getBorderInsets(Component c);

    /**
     * Reinitialize the insets parameter with this Border's current Insets.
     *
     * @param c      the component for which this border insets value applies
     * @param insets the object to be reinitialized
     */
    public Insets getBorderInsets(Component c, Insets insets);

    /**
     * Returns the color of the border.
     *
     * @return the color of the border.
     */
    public Color getBorderColor();

    /**
     * Sets the color of the border.
     *
     * @param borderColor the color of the border.
     */
    public void setBorderColor(Color borderColor);

    /**
     * Returns the inner thickness of the border.
     *
     * @return Returns the inner thickness of the border.
     */
    public int getInnerThickness();

    /**
     * Sets the inner thickness of the border.
     *
     * @param innerThickness Returns the inner thickness of the border.
     */
    public void setInnerThickness(int innerThickness);

    /**
     * Returns the thickness of the border.
     *
     * @return Returns the thickness of the border.
     */
    public int getThickness();

    /**
     * Sets the thickness of the border.
     *
     * @param thickness the thickness of the border.
     */
    public void setThickness(int thickness);

    /**
     * Returns whether or not the border is opaque.
     */
    public boolean isBorderOpaque();
}
