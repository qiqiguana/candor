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

package fr.pingtimeout.jtail.io.index;

import fr.pingtimeout.jtail.util.JTailLogger;

import java.io.*;
import java.util.List;

/**
 * A <code>RomFileIndex</code> is a FileIndex stored in a temporary file.
 * This class may be used only with large indexes as it doesn't consume any memory. Caution : the read/write operations
 * provided by a <code>RomFileIndex</code> are slower that those provided by a <code>RamFileIndex</code>.
 *
 * @author Pierre Laporte
 */
public class RomFileIndex implements FileIndex {
    /**
     * The file that is indexed.
     */
    final File indexedFile;

    /**
     * The associated index file.
     */
    final File indexFile;

    /**
     * The stream used to read/write the index file.
     */
    final RandomAccessFile randomAccessFile;

    /**
     * Constant representing the size of an integer in bytes.
     */
    private static final int INTEGER_SIZE_IN_BYTES = (Integer.SIZE / 4);

    /**
     * Build an new instance of <code>RomFileIndex</code> referencing the given file as the indexed file.
     *
     * @param indexedFile the file that shall be indexed
     */
    public RomFileIndex(File indexedFile) {
        this.indexedFile = indexedFile;

        // Write the index in a temporary file
        // The tmp file shall be deleted at the end of the JVM
        try {
            this.indexFile = File.createTempFile(indexedFile.getName(), ".JTailPlus");
            this.indexFile.deleteOnExit();

            JTailLogger.debug("Index file : {}", indexFile);
            
            // Open an input stream on the index file
            this.randomAccessFile = new RandomAccessFile(this.indexFile, "rw");
        } catch (IOException e) {
            throw new RuntimeException("Impossible de créer un RomFileIndexer", e);
        }
    }

    @Override
    public final  int getIndexSize() {
        try {
            return (int) (this.randomAccessFile.length() / RomFileIndex.INTEGER_SIZE_IN_BYTES);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de récupérer la taille de l'index", e);
        }
    }

    @Override
    public final  String getFileName() {
        return this.indexedFile.getName();
    }

    @Override
    public final long getOffsetOfLine(int lineNumber) {
        try {
            this.randomAccessFile.seek(lineNumber * RomFileIndex.INTEGER_SIZE_IN_BYTES);
            return this.randomAccessFile.readLong();
        } catch (IOException e) {
            throw new RuntimeException("Impossible de récupérer l'offset de la ligne " + lineNumber, e);
        }
    }

    @Override
    public void setOffsetOfLine(int lineNumber, long offset) {
        if (lineNumber > this.getIndexSize()) {
            throw new IndexOutOfBoundsException("The index does not contains any value between "
                    + this.getIndexSize() + " and " + lineNumber);
        } else {
            try {
                this.randomAccessFile.seek(lineNumber * RomFileIndex.INTEGER_SIZE_IN_BYTES);
                this.randomAccessFile.writeLong(offset);
            } catch (IOException e) {
                throw new RuntimeException("Impossible d'écrire l'offset de la ligne dans le fichier", e);
            }
        }
    }

    @Override
    public void updateIndex(List<Long> newIndex, int fromLine, int toLine) {
        for (int lineToUpdate = fromLine; lineToUpdate < toLine; lineToUpdate++) {
            this.setOffsetOfLine(lineToUpdate, newIndex.get(lineToUpdate));
        }
    }

    @Override
    public void clear() {
        try {
            this.randomAccessFile.setLength(0);
        } catch (IOException e) {
            throw new RuntimeException("Impossible de vider le fichier", e);
        }
    }

    @Override
    public Type getType() {
        return Type.FILE_BASED;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer("[0");

        final int indexSize= this.getIndexSize();
        for(int i=1; i<indexSize; i++) {
            result.append(",").append(this.getOffsetOfLine(i));
        }

        result.append("]");

        return result.toString();
    }
}
