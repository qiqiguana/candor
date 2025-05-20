package osa.ora.server.client.ui.utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * <code>JFontChooser</code> provides a pane of controls designed to allow
 * a user to manipulate and select a font.
 *
 * This class provides three levels of API:
 * <ol>
 * <li>A static convenience method which shows a modal font-chooser
 * dialog and returns the font selected by the user.
 * <li>A static convenience method for creating a font-chooser dialog
 * where <code>ActionListeners</code> can be specified to be invoked when
 * the user presses one of the dialog buttons.
 * <li>The ability to create instances of <code>JFontChooser</code> panes
 * directly (within any container). <code>PropertyChange</code> listeners
 * can be added to detect when the current "font" property changes.
 * </ol>
 * <p>
 *
 * @author Adrian BER
 */
public class JFontChooser extends JComponent {

    /**
     * The list of possible font sizes.
     */
    private static final Integer[] SIZES = { 8, 9, 10, 11, 12, 13, 14, 16, 18, 20, 24, 26, 28, 32, 36, 40, 48, 56, 64, 72 };

    /**
     * The list of possible fonts.
     */
    private static final String[] FONTS = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    private FontSelectionModel selectionModel;

    private JList fontList;

    private JList sizeList;

    private JCheckBox boldCheckBox;

    private JCheckBox italicCheckBox;

    private JLabel previewLabel;

    /**
     * The preview text, if null the font name will be the preview text.
     */
    private String previewText;

    /**
     * Listener used to update the font of the selection model.
     */
    private SelectionUpdater selectionUpdater = new SelectionUpdater();

    /**
     * Listener used to update the font in the components. This should be registered
     * with the selection model.
     */
    private LabelUpdater labelUpdater = new LabelUpdater();

    /**
     * True if the components are being updated and no event should be generated.
     */
    private boolean updatingComponents = false;

    /**
     * Listener class used to update the font in the components. This should be registered
     * with the selection model.
     */
    private class LabelUpdater implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            updateComponents();
        }
    }

    /**
     * Listener class used to update the font of the preview label.
     */
    private class SelectionUpdater implements ChangeListener, ListSelectionListener {

        public void stateChanged(ChangeEvent e) {
            if (!updatingComponents) {
                setFont(buildFont());
            }
        }

        public void valueChanged(ListSelectionEvent e) {
            if (!updatingComponents) {
                setFont(buildFont());
            }
        }
    }

    /**
     * Shows a modal font-chooser dialog and blocks until the
     * dialog is hidden.  If the user presses the "OK" button, then
     * this method hides/disposes the dialog and returns the selected color.
     * If the user presses the "Cancel" button or closes the dialog without
     * pressing "OK", then this method hides/disposes the dialog and returns
     * <code>null</code>.
     *
     * @param component    the parent <code>Component</code> for the dialog
     * @param title        the String containing the dialog's title
     * @return the selected font or <code>null</code> if the user opted out
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public Font showDialog(Component component, String title);

    /**
     * Creates and returns a new dialog containing the specified
     * <code>ColorChooser</code> pane along with "OK", "Cancel", and "Reset"
     * buttons. If the "OK" or "Cancel" buttons are pressed, the dialog is
     * automatically hidden (but not disposed).  If the "Reset"
     * button is pressed, the color-chooser's color will be reset to the
     * font which was set the last time <code>show</code> was invoked on the
     * dialog and the dialog will remain showing.
     *
     * @param c              the parent component for the dialog
     * @param title          the title for the dialog
     * @param modal          a boolean. When true, the remainder of the program
     *                       is inactive until the dialog is closed.
     * @param okListener     the ActionListener invoked when "OK" is pressed
     * @param cancelListener the ActionListener invoked when "Cancel" is pressed
     * @return a new dialog containing the font-chooser pane
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public JDialog createDialog(Component c, String title, boolean modal, ActionListener okListener, ActionListener cancelListener);

    /**
     * Creates a color chooser pane with an initial font which is the same font
     * as the default font for labels.
     */
    public JFontChooser() {
    }

    /**
     * Creates a font chooser pane with the specified initial font.
     *
     * @param initialFont the initial font set in the chooser
     */
    public JFontChooser(Font initialFont) {
    }

    /**
     * Creates a font chooser pane with the specified
     * <code>FontSelectionModel</code>.
     *
     * @param model the font selection model used by this component
     */
    public JFontChooser(FontSelectionModel model) {
    }

    private void init(Font font);

    private Font buildFont();

    /**
     * Updates the font in the preview component according to the selected values.
     */
    private void updateComponents();

    /**
     * Returns the data model that handles font selections.
     *
     * @return a FontSelectionModel object
     */
    public FontSelectionModel getSelectionModel();

    /**
     * Set the model containing the selected font.
     *
     * @param newModel   the new FontSelectionModel object
     */
    public void setSelectionModel(FontSelectionModel newModel);

    /**
     * Gets the current font value from the font chooser.
     *
     * @return the current font value of the font chooser
     */
    public Font getFont();

    /**
     * Sets the current font of the font chooser to the specified font.
     * The <code>ColorSelectionModel</code> will fire a <code>ChangeEvent</code>
     * @param font the font to be set in the font chooser
     * @see JComponent#addPropertyChangeListener
     */
    public void setFont(Font font);

    /**
     * Returns the preview text displayed in the preview component.
     * @return the preview text, if null the font name will be displayed
     */
    public String getPreviewText();

    /**
     * Sets the preview text displayed in the preview component.
     * @param previewText the preview text, if null the font name will be displayed
     */
    public void setPreviewText(String previewText);
}
