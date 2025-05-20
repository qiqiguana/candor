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

package fr.pingtimeout.jtail.gui.model;

/**
 * Created by IntelliJ IDEA.
 * User: plaporte
 * Date: 26 avr. 2010
 * Time: 22:45:24
 * To change this template use File | Settings | File Templates.
 */
public class JTailModelEvent {
    public enum Type {
        SCROLLED, RESIZED
    }

    public final Type type;
    public final int minLine;
    public final int firstLine;
    public final int nbDisplayedLines;
    public final int maxLine;

    public JTailModelEvent(Type type, int minLine, int firstLine, int nbDisplayedLines, int maxLine) {
        this.type = type;
        this.minLine = minLine;
        this.firstLine = firstLine;
        this.nbDisplayedLines = nbDisplayedLines;
        this.maxLine = maxLine;
    }

    public String toString() {
        return "type : " + type
                + " - minLine : " + minLine
                + " - firstLine : " + firstLine
                + " - nbDisplayedLines : " + nbDisplayedLines
                + " - maxLine : " + maxLine
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof JTailModelEvent) {
            JTailModelEvent event = (JTailModelEvent) o;

            return this.type == event.type
                    && this.minLine == event.minLine
                    && this.firstLine == event.firstLine
                    && this.nbDisplayedLines == event.nbDisplayedLines
                    && this.maxLine == event.maxLine;
        }
        return false;
    }
}
