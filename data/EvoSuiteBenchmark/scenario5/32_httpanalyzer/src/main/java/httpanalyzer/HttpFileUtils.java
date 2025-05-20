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
    public long saveEntity(HttpEntity entity, String fileName) {
        long size = 0L;
        File file = new File(fileName);
        byte[] buffer = new byte[MAX_BUFFER_SIZE];
        // Read from server into buffer.
        InputStream streamEntity;
        RandomAccessFile outFile;
        try {
            streamEntity = entity.getContent();
            outFile = new RandomAccessFile(file, "rw");
            int read = 0;
            // Rewrite file
            outFile.setLength(0);
            System.out.println("Buffer size =" + buffer.length);
            while ((read = streamEntity.read(buffer)) != -1) {
                size = size + read;
                // Write buffer to file
                outFile.write(buffer, 0, read);
            }
            outFile.close();
            streamEntity.close();
        } catch (IOException ex) {
            Logger.getLogger(HttpFileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(HttpFileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return size;
    }
}
