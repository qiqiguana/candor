package httpanalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.apache.http.HttpEntity;

/**
 * @author vlad
 */
public class HttpFileUtils {

    /**
     * Save Http entity to file
     *
     * @param entity HttpEntity
     * @param fileName String
     */
    public long saveEntity(HttpEntity entity, String fileName);
}
