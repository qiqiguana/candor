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
    public static boolean fileCopy(InputStream sourceFile, File destFile) {
        try {
            InputStream in = sourceFile;
            FileOutputStream out = new FileOutputStream(destFile);
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
            out.close();
            in.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
