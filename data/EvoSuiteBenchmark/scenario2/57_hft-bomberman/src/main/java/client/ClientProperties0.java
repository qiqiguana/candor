package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import common.Constants;
import common.XmlFunctions;

/**
 * reads and writes properties to xml-file
 * properties file is saved to user directory
 *
 * @author christian
 */
public class ClientProperties {

    /**
     * get property value by element-name returns empty string if fileError is
     * true
     *
     * @param element -
     *            Name of element
     * @return String - value
     */
    public String getProperty(String element) {
        if (!fileError) {
            String XmlPath = "//" + element;
            return xmlFunctions.getXmlValue(XmlPath);
        } else {
            return new String("");
        }
    }
}
