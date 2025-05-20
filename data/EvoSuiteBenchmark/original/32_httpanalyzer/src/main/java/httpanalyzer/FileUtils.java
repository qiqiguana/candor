/*
 * GNU GENERAL PUBLIC LICENSE
 * Version 3, 29 June 2007
 * 
 * Copyright (C) 2010, vlad
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package httpanalyzer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;

/**
 *
 * @author vlad
 */
public class FileUtils {

    static final int MAX_BUFFER_SIZE = 8192;

    /**
     * Save Http entity to file
     * @param entity HttpEntity
     * @param fileName String
     */
    public static void saveEntity(HttpEntity entity, String fileName) {
        long size = entity.getContentLength();
        File file = new File(fileName);
        /* Size buffer according to how much of the
        file is left to download. */
        byte buffer[];

        if (size > MAX_BUFFER_SIZE) {
            buffer = new byte[MAX_BUFFER_SIZE];
        } else {
            buffer = new byte[(int) size];
        }
        // Read from server into buffer.
        InputStream streamEntity;
        RandomAccessFile outFile;
        try {
            streamEntity = entity.getContent();
            outFile = new RandomAccessFile(file, "rw");
            int read = 0;
            // Rewrite file
            outFile.setLength(0);
            System.out.println("Buffer size ="+buffer.length);
            while ((read = streamEntity.read(buffer)) != -1) {
                // Write buffer to file
                System.out.println("write!");
                outFile.write(buffer, 0, read);
            }
            outFile.close();
            streamEntity.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
