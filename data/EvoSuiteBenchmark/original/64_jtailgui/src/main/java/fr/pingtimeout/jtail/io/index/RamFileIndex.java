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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A <code>RamFileIndex</code> is a FileIndex stored only in RAM (Heap).
 * This class may be used only with small indexes as its size can grow rapidly. It provides the fastest read/write operations.
 *
 * @author Pierre Laporte
 */
public class RamFileIndex implements FileIndex {
    /**
     * The file pointed by this index.
     */
    final File indexedFile;

    /**
     * The associated index.
     */
    final List<Long> index;

    /**
     * Build an new instance of <code>RamFileIndex</code> referencing the given file as the indexed file.
     *
     * @param indexedFile the file that shall be indexed
     */
    public RamFileIndex(File indexedFile) {
        this.indexedFile = indexedFile;
        this.index = new ArrayList<Long>();
    }

    @Override
    public final int getIndexSize() {
        return this.index.size();
    }

    @Override
    public final String getFileName() {
        return this.indexedFile.getName();
    }

    @Override
    public final long getOffsetOfLine(int lineNumber) {
        return index.get(lineNumber);
    }

    @Override
    public final void setOffsetOfLine(int lineNumber, long offset) {
        if (lineNumber > this.getIndexSize()) {
            throw new IndexOutOfBoundsException("The index does not contains any value between "
                    + this.getIndexSize() + " and " + lineNumber);
        } else if (lineNumber == this.getIndexSize()) {
            this.index.add(offset);
        } else {
            this.index.set(lineNumber, offset);
        }
    }

    @Override
    public final void updateIndex(List<Long> newIndex, int fromLine, int toLine) {
        for (int lineToUpdate = fromLine; lineToUpdate < toLine; lineToUpdate++) {
            this.setOffsetOfLine(lineToUpdate, newIndex.get(lineToUpdate));
        }
    }

    @Override
    public void clear() {
        this.index.clear();
    }

    @Override
    public Type getType() {
        return Type.MEMORY_BASED;
    }

    @Override
    public String toString() {
        return this.index.toString();
    }
}
