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

    static final int MAX_BUFFER_SIZE = 8192;

    /**
     * Save Http entity to file
     * @param entity HttpEntity
     * @param fileName String
     */
    public long saveEntity(HttpEntity entity, String fileName);

    /**
     * Filter for FileChooser *.XML
     */
    private class MyXmlFilter extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File file) {
            // Allow only directories, or files with ".txt" extension
            return file.isDirectory() || file.getAbsolutePath().endsWith(".xml");
        }

        @Override
        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "XML files (*.xml)";
        }
    }

    /**
     * Filter for FileChooser *.TXT
     */
    private class MyTxtFilter extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File file) {
            // Allow only directories, or files with ".txt" extension
            return file.isDirectory() || file.getAbsolutePath().endsWith(".txt");
        }

        @Override
        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "Text documents (*.txt)";
        }
    }

    /**
     * Method saves your template to file
     * Creates JFileChooser and save into XML file
     * @param properties
     * @param parentFrame
     */
    public void savePreferenceToFile(Properties properties, JFrame parentFrame);

    /**
     * Method load your template from file
     * Creates JFileChooser and  load from XML file
     * @param parentFrame
     */
    public Properties loadPreferenceFromFile(JFrame parentFrame);

    /**
     * Save information from ReplayTab in file
     * @param mainView
     * @param parentFrame
     */
    public void saveSessionInfo(HttpAnalyzerView mainView, JFrame parentFrame);

    /**
     * Show JChooseFile dialog and
     * put your choice in toFileTextField
     * @param mainView
     * @param parentFrame
     */
    public void fillToFileField(HttpAnalyzerView mainView, JFrame parentFrame);
}
