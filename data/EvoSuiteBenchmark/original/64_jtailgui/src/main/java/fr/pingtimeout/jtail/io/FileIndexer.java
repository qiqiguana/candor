/*
 * Copyright (c) 2010 Pierre Laporte.
 *
 * This file is part of JTailPlus.
 *
 * JTailPlus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTailPlus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTailPlus.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.pingtimeout.jtail.io;

import fr.pingtimeout.jtail.io.index.FileIndex;
import fr.pingtimeout.jtail.io.index.ImmutableFileIndex;
import fr.pingtimeout.jtail.io.index.RamFileIndex;
import fr.pingtimeout.jtail.io.index.RomFileIndex;
import fr.pingtimeout.jtail.util.JTailLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;

/**
 * A <code>FileIndexer</code> is used to generate an index associating every lines of a file to an offset.
 * To generate the index, this class fully reads a file, using a 1 MB buffer. The indexation shall be stated by the
 * <code>index()</code> method.
 * A <code>FileIndexer</code> can handle files up to 2 GB.
 *
 * @author Pierre Laporte
 */
public class FileIndexer extends Observable {
    /**
     * Default size for the buffer used to read the file.
     */
    private static final int DEFAULT_BUFFER_SIZE = 1048576;

    /**
     * Constant representing 10 MB in bytes.
     */
    private static final long FILE_SIZE_10MB = 10 * 1024 * 1024;

    /**
     * The file that shall be indexed.
     */
    private final File file;

    /**
     * The generated index.
     */
    private final FileIndex index;


    /**
     * Create a new instance of <code>FileIndexer</code> to index the <code>fileToIndex</code> file.
     * If the file <code>fileToIndex</code> is larger that 10 MB, a <code>RomFileIndex</code> will be used to handle the
     * index. Otherwise, a <code>RamFileIndex</code> will be used.
     *
     * @param fileToIndex the file that shall be indexed
     * @throws java.io.FileNotFoundException if any I/O error occurs
     */
    public FileIndexer(final File fileToIndex) throws FileNotFoundException {
        JTailLogger.debug("Creating FileIndexer for file : {}", //NON-NLS
                fileToIndex.toString());

        this.file = fileToIndex;
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }

        // Build the index.
        if (file.length() < FILE_SIZE_10MB) {
            this.index = new RamFileIndex(this.file);
        } else {
            this.index = new RomFileIndex(this.file);
        }

        // The first line of the file always begins at position 0.
        this.index.setOffsetOfLine(0, 0L);
    }

    /**
     * Create a new instance of <code>FileIndexer</code> to index the <code>fileToIndex</code> file.
     * This instance will use a new <code>FileIndex</code> whose class is specified in <code>fileIndexClass</code>.
     *
     * @param fileToIndex    the file that shall be indexed
     * @param fileIndexClass the FileIndex that shall be used to index the file
     * @throws java.io.FileNotFoundException if any I/O error occurs
     */
    public FileIndexer(
            final File fileToIndex,
            final Class<? extends FileIndex> fileIndexClass
    ) throws FileNotFoundException {
        JTailLogger.debug("Creating FileIndexer for file : {}", //NON-NLS
                fileToIndex.toString());

        this.file = fileToIndex;
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }

        // Build the index.
        if (fileIndexClass.equals(RomFileIndex.class)) {
            this.index = new RomFileIndex(this.file);
        } else if (fileIndexClass.equals(RamFileIndex.class)) {
            this.index = new RamFileIndex(this.file);
        } else {
            throw new IllegalArgumentException("Unknown FileIndex Class : " + fileIndexClass);
        }

        // The first line of the file always begins at position 0.
        this.index.setOffsetOfLine(0, 0L);
    }

    /**
     * Return an immutable instance of the index.
     * <strong>The <code>index()</code> method MUST have been called before calling this method.</code>
     *
     * @return an immutable instance of the file index
     */
    public FileIndex getIndex() {
        return new ImmutableFileIndex(this.index);
    }

    /**
     * Return the indexed file.
     *
     * @return the indexed fileé
     */
    public File getFile() {
        return file;
    }

    /**
     * Return the indexed file size.
     *
     * @return la taille du fichier indexé.
     */
    @Deprecated
    public long getFileLength() {
        return file.length();
    }

    /**
     * Index the file starting at the beggining of the file content.
     *
     * @throws InterruptedException if the process is interrupted
     */
    public void index() throws InterruptedException {
        JTailLogger.debug("Indexing file : {}", //NON-NLS 
                this.file.toString());

        // Full index = index from line 0
        this.indexFromLine(0);
    }

    /**
     * Index the file beginning at the given line.
     *
     * @param lineNumber the line number from which the indexation shall start
     * @throws InterruptedException if the process is interrupted
     */
    public void indexFromLine(final int lineNumber) throws InterruptedException {
        JTailLogger.debug("Indexing file {} starting at line {}", //NON-NLS
                this.file.toString(), lineNumber);

        // Critical section, the index will be modified
        // TODO return an immutable instance of the index so that the synchronised block is not necessary anymore
        synchronized (this.index) {
            try {
                final long fileLenght = this.file.length();

                JTailLogger.debug("Current index {}", this.index);

                if (lineNumber == 0) {
                    this.index.setOffsetOfLine(0, 0L);
                }

                // Create a buffer and an input stream for the file
                final FileInputStream inputStream = new FileInputStream(this.file);
                byte buffer[] = new byte[DEFAULT_BUFFER_SIZE];

                // Put the file pointer at the right position
                final long nbBytesSkipped = inputStream.skip(this.index.getOffsetOfLine(lineNumber));
                JTailLogger.debug("Offset of line {} : {}", lineNumber, this.index.getOffsetOfLine(lineNumber));
                JTailLogger.debug("Skipped {} bytes", nbBytesSkipped);

                // Read the file
                int totalNbCharRead = 0;
                int nbLinesRead = 0;
                int nbCharRead;
                int currentBlock = 0;
                while ((nbCharRead = inputStream.read(buffer)) != -1) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }

                    for (int i = 0; i < nbCharRead; i++) {
                        if (buffer[i] == '\n') {
                            // Store the (file pointer + 1) position (beginning of the next line)
                            nbLinesRead += 1;
                            this.index.setOffsetOfLine(lineNumber + nbLinesRead, nbBytesSkipped + (currentBlock * DEFAULT_BUFFER_SIZE) + i + 1);
                        }
                    }
                    currentBlock++;
                    totalNbCharRead += nbCharRead;

                    this.setChanged();
                    this.notifyObservers((int) (((double) (totalNbCharRead) / fileLenght) * 100));
                }
                inputStream.close();
            } catch (IOException e) {
                // Empty index and throw exception
                this.index.clear();
                throw new IllegalStateException("An I/O Exception occurred during indexing", e);
            }
        }

        JTailLogger.debug("Indexing done."); //NON-NLS
    }
}
