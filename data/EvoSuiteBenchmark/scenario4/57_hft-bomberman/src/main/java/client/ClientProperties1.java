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

    private XmlFunctions xmlFunctions;

    // if set to true, class is ignoring invocations
    private boolean fileError = false;

    private static final Logger logger = Logger.getLogger(ClientProperties.class);

    /**
     * constructor, opens file for reading if exists
     *
     * @param fileName -
     *            Name to xml file
     */
    public ClientProperties(String fileName) {
    }

    /**
     * checks if local properties file is present, if not tries to copy empty
     * file to location. If this fails, flag fileError is set to true and read
     * or write access is bypassed
     *
     * @param fileName -
     *            name of file
     */
    private void CheckPropertiesFile(String fileName);

    /**
     * get property value by element-name returns empty string if fileError is
     * true
     *
     * @param element -
     *            Name of element
     * @return String - value
     */
    public String getProperty(String element);

    /**
     * (over)writes value of specific element
     * does nothing if fileError is true
     *
     * @param element -
     *            Name of element
     * @param value -
     *            new value
     */
    public void setProperty(String element, String value);

    /**
     * copies a file from a to b
     * used InputStream as provided from ResourceService class as
     * source and File as destination
     *
     * @param sourceFile -
     *            InputStream - File which should be copied
     * @param destFile -
     *            destination where file should be copied to
     * @return boolean - true if successfully copied, otherwise false
     */
    public static boolean fileCopy(InputStream sourceFile, File destFile);
}
