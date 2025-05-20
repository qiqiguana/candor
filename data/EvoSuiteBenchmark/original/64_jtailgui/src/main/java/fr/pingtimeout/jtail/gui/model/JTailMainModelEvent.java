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
 * Date: 18 avr. 2010
 * Time: 10:53:54
 * To change this template use File | Settings | File Templates.
 */
public class JTailMainModelEvent {
    public enum Type {
        CREATED, UPDATED, REMOVED, CHANGED
    }

    public final Type type;
    public final int index;
    public final JTailModel model;

    public JTailMainModelEvent(JTailModel model, int index, Type type) {
        this.type = type;
        this.index = index;
        this.model = model;
    }

    public String toString() {
        return "type : " + type
                + " - index : " + index
                + " - model : " + model;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof JTailMainModelEvent) {
            JTailMainModelEvent event = (JTailMainModelEvent) o;

            return this.type == event.type
                    && this.index == event.index
                    && this.model == event.model;
        }
        return false;
    }
}
