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

import java.util.List;

/**
 * A <code>ImmutableFileIndex</code> is a handler that shall be used to provide a read-only instance of
 * <code>FileIndex</code>.
 * @author Pierre Laporte
 */
public class ImmutableFileIndex implements FileIndex {
    /**
     * The instance of <code>FileIndex</code> to wrap.
     */
    private final FileIndex fileIndex;

    /**
     * Create a new immutable instance of <code>FileIndex</code> that wraps every read-only method to the given file
     * index.
     * @param fileIndex the file index to wrap
     */
    public ImmutableFileIndex(FileIndex fileIndex) {
        this.fileIndex = fileIndex;
    }

    @Override
    public int getIndexSize() {
        return this.fileIndex.getIndexSize();
    }

    @Override
    public String getFileName() {
        return this.fileIndex.getFileName();
    }

    @Override
    public long getOffsetOfLine(int lineNumber) {
        return this.fileIndex.getOffsetOfLine(lineNumber);
    }

    @Override
    public void setOffsetOfLine(int lineNumber, long offset) {
        throw new UnsupportedOperationException("This instance is Immutable");
    }

    @Override
    public void updateIndex(List<Long> newIndex, int fromLine, int toLine) {
        throw new UnsupportedOperationException("This instance is Immutable");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("This instance is Immutable");
    }

    @Override
    public Type getType() {
        return this.fileIndex.getType();
    }
}
