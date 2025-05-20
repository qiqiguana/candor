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

    public String getProperty(String element) {
        if (!fileError) {
            String XmlPath = "//" + element;
            return xmlFunctions.getXmlValue(XmlPath);
        } else {
            return new String("");
        }
    }
}
