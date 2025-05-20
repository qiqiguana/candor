package bierse.model;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import bierse.view.MyKeyMap;

/**
 * @author Rainer Friesen
 */
public class Drink {

    /**
     * Recalculate the current price for this drink
     */
    public int recalculate();
}
