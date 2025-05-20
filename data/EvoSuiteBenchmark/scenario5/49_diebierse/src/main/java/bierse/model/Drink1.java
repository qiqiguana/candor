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
     * Save the drink description to a XML file
     */
    public File save() {
        if (!NEW_DRINK_NAME.equals(name)) {
            model.getLog().debug("Save drink: " + name);
            // Build JDOM document representing the settings
            Element root = new Element("drink");
            Element minPriceElem = new Element("minPrice");
            minPriceElem.setText(String.valueOf(minPrice));
            root.addContent(minPriceElem);
            Element maxPriceElem = new Element("maxPrice");
            maxPriceElem.setText(String.valueOf(maxPrice));
            root.addContent(maxPriceElem);
            Element startPriceElem = new Element("startPrice");
            startPriceElem.setText(String.valueOf(startPrice));
            root.addContent(startPriceElem);
            Element targetAmountElem = new Element("targetAmount");
            targetAmountElem.setText(String.valueOf(targetAmount));
            root.addContent(targetAmountElem);
            Element deltaAmountElem = new Element("deltaAmount");
            deltaAmountElem.setText(String.valueOf(deltaAmount));
            root.addContent(deltaAmountElem);
            Element maxStepElem = new Element("maxStep");
            maxStepElem.setText(String.valueOf(maxStep));
            root.addContent(maxStepElem);
            Element usedElem = new Element("used");
            usedElem.setText(String.valueOf(used));
            root.addContent(usedElem);
            Element keyElem = new Element("key");
            keyElem.setText(String.valueOf(key));
            root.addContent(keyElem);
            Document settingsDoc = new Document(root);
            // Save JDOM document to file system
            File folder = new File(DRINKS_FOLDER);
            File file = new File(DRINKS_FOLDER + name + ".xml");
            try {
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                model.getLog().debug("File for Drink: " + file.getAbsolutePath());
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                XMLOutputter serializer = new XMLOutputter();
                serializer.output(settingsDoc, fos);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                model.getLog().error(this, e);
            }
            return file;
        }
        return null;
    }
}

/**
 * @author Rainer Friesen
 */
public class Model {

    public Logger getLog();
}
