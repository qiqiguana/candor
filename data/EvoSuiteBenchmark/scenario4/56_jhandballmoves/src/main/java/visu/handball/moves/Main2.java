package visu.handball.moves;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import visu.handball.moves.actions.AboutAction;
import visu.handball.moves.actions.ChangeColorsAction;
import visu.handball.moves.actions.CloseAction;
import visu.handball.moves.actions.CreateMovePdfAction;
import visu.handball.moves.actions.DeleteEventAction;
import visu.handball.moves.actions.NewAction;
import visu.handball.moves.actions.NewMoveEventAction;
import visu.handball.moves.actions.NewPassEventAction;
import visu.handball.moves.actions.NewSequenceAction;
import visu.handball.moves.actions.OpenAction;
import visu.handball.moves.actions.PauseAnimationAction;
import visu.handball.moves.actions.PrintActualSequenzAction;
import visu.handball.moves.actions.PrintMoveAction;
import visu.handball.moves.actions.SaveAction;
import visu.handball.moves.actions.SetMoveNameAction;
import visu.handball.moves.actions.StartAnimationAction;
import visu.handball.moves.actions.StartOneSequenceAnimationAction;
import visu.handball.moves.actions.StopAnimationAction;
import visu.handball.moves.controller.CommentController;
import visu.handball.moves.controller.JMenuHelper;
import visu.handball.moves.controller.MouseController;
import visu.handball.moves.model.ColorModel;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.TableHandballModel;
import visu.handball.moves.model.TableSelectionListener;
import visu.handball.moves.resources.Resources;
import visu.handball.moves.views.CommentView;
import visu.handball.moves.views.EventTableCellDelayEditor;
import visu.handball.moves.views.EventTableCellRenderer;
import visu.handball.moves.views.Field;
import visu.handball.moves.views.PlayerToolBar;
import visu.handball.moves.views.StatusBar;

/**
 * Hauptklasse zum Starten der Anwendung plus den Zugriff auf die entsprechenden Objekt-Instanzen
 *
 * @author tommy
 */
public class Main {

    private static SaveAction saveAction;

    private static CloseAction closeAction;

    private static SetMoveNameAction setNameAction;

    private static JFrame window;

    private static Field field;

    private static CommentView commentView;

    private static ColorModel colorModel;

    /**
     * Liefert die Instanz des ColorModels
     *
     * @return colorModel
     */
    public static ColorModel getColorModel();

    /**
     * Main-Methode zum Starten der Anwendung
     *
     * @param args
     */
    public static void main(String[] args);

    private static JMenuBar createHandballMenu(HandballModel handballModel);

    private static JPanel createTablePanel(HandballModel handballModel);

    private static StatusBar createStatusBar(HandballModel model);

    /**
     * Basierend auf der relativen Pfad (relativ zum Ort dieser Klasse) und der Beschreibung
     * wird ein Grafik geladen und ein ImageIcon-Objekt erstellt.
     * @param path
     * @param description
     *
     * @return imageIcon
     */
    public static ImageIcon createImageIcon(String path, String description);

    /**
     * Erzeugt auf Basis eines relativen Pfads (relativ zum Ort dieser Klasse) eine URL.
     * @param path
     *
     * @return url
     */
    public static URL getResource(String path);

    /**
     * Methode um Component-Objekte (z.B. Fenster) in der Mitte des Bildschirms zu positionieren
     * @param component
     */
    public static void locateOnScreenCenter(Component component);

    private static HandballModel createHandballModel();

    /**
     * Die Instanz des Anwendungsfensters
     *
     * @return window
     */
    public static JFrame getWindow();

    /**
     * Instanz des Spielfeld-Panels
     *
     * @return panel
     */
    public static Field getField();

    /**
     * Instanz der Speichern-Aktion
     *
     * @return saveAction
     */
    public static SaveAction getSaveAction();

    public static SetMoveNameAction getSetNameAction();

    /**
     * Instanz der Schlie�en-Aktion
     *
     * @return close Action
     */
    public static CloseAction getCloseAction();

    /**
     * Methode um eine tiefe Objektkopie per Serialisierung zu erzeugen!
     *
     * @param oldObj (muss Serializable implementieren)
     * @return tiefe Kopie
     * @throws Exception
     */
    public static Object deepCopy(Object oldObj) throws Exception;

    /**
     * Liefert die Version der Anwendung als String
     *
     * @return version
     */
    public static String getVersion();
}
