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
 * The <code>FileIndex</code> class is a data structure that associates a number with an offset in a given file.
 * It can be used to track the position of every line in a file. Basically, this class is only an associative array.
 * It doesn't handle the indexation of a file.
 *
 * @author Pierre Laporte
 */
public interface FileIndex {
    /**
     * Return the index size.
     *
     * @return the index size
     */
    int getIndexSize();


    /**
     * Return the indexed file name.
     *
     * @return the indexed file name
     */
    String getFileName();

    /**
     * Return the offset of the given line in the indexed file.
     *
     * @param lineNumber the line whose offset shall be retrieved
     * @return the offset of the given line.
     */
    long getOffsetOfLine(int lineNumber);

    /**
     * Set the offset of the given line to the given value in the index
     *
     * @param lineNumber the line number whose offset is <code>offset</code>
     * @param offset     the offset of the line
     */
    void setOffsetOfLine(int lineNumber, long offset);

    /**
     * Replace the offset of lines between <code>fromLine</code> and <code>toLine</code> with the values of <code>newIndex</code>
     *
     * @param newIndex the index to update;
     * @param fromLine the first line whose offset shall be updated
     * @param toLine   the last line (exclusive) whose offset shall be updated
     */
    void updateIndex(List<Long> newIndex, int fromLine, int toLine);

    /**
     * Clear the index.
     */
    void clear();

    /**
     * Return the index type.
     * @return the index type.
     */
    Type getType();

    /**
     * Type of index.
     */
    public enum Type {
        MEMORY_BASED,
        FILE_BASED;

        public String getLabelKey() {
            return "index.type." + this.name() + ".label";
        }

        public String getTooltipKey() {
            return "index.type." + this.name() + ".tooltip";
        }
    }
}
