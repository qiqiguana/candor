package bierse.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class Settings {

    public File save() {
        // Build JDOM document representing the settings
        Element settingsRoot = new Element(DOM_SETTINGS_ROOT);
        Element timeIntervalElem = new Element(DOM_TIME_INTERVAL);
        timeIntervalElem.setText(String.valueOf(timeInterval));
        settingsRoot.addContent(timeIntervalElem);
        Element priceStepsElem = new Element(DOM_PRICE_STEPS);
        priceStepsElem.setText(String.valueOf(priceSteps));
        settingsRoot.addContent(priceStepsElem);
        Element currencyElem = new Element(DOM_CURRENCY);
        currencyElem.setText(currency);
        settingsRoot.addContent(currencyElem);
        Element backgroundElem = new Element(DOM_BACKGROUND_IMG);
        backgroundElem.addContent(backgroundImagePath);
        settingsRoot.addContent(backgroundElem);
        Element standardLogicElem = new Element(DOM_STANDARD_LOGIC);
        if (standardLogic) {
            standardLogicElem.addContent(DOM_YES);
        } else {
            standardLogicElem.addContent(DOM_NO);
        }
        settingsRoot.addContent(standardLogicElem);
        Element messageElem = new Element(DOM_MESSAGE);
        messageElem.addContent(message);
        settingsRoot.addContent(messageElem);
        Element messageSpeedElem = new Element(DOM_MESSAGE_SPEED);
        messageSpeedElem.addContent(new Integer(messageSpeed).toString());
        settingsRoot.addContent(messageSpeedElem);
        Document settingsDoc = new Document(settingsRoot);
        // Save JDOM document to file system
        File file = new File(SETTINGS_FOLDER + "settings.xml");
        try {
            File folder = new File(SETTINGS_FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }
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
}
