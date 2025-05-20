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

import fr.pingtimeout.jtail.util.JTailLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * Modèle utilisé par l'application.
 * User: plaporte
 * Date: 15 avr. 2010
 * Time: 19:41:19
 * To change this template use File | Settings | File Templates.
 */
public class JTailMainModel extends Observable {
    /**
     * Liste des fichiers ouverts.
     * TODO Avertir les observateurs qu'un JTailModel a changé
     * TODO - Si les observateurs ne le connaissent pas, c'est qu'il a été créé
     * TODO - S'ils le connaissent
     * TODO - - S'il figure encore dans la liste des fichiers this.files, c'est qu'il a été modifié
     * TODO - - Sinon, c'est qu'il a été supprimé.
     */
    final List<JTailModel> files = new ArrayList<JTailModel>();
    int currentFileIndex = -1;

    public JTailMainModel() {
    }

    public void add(JTailModel model) {
        JTailLogger.debug("Adding a new model !"); //NON-NLS

        if (this.files.contains(model)) {
            JTailLogger.debug("This model is already in our list"); //NON-NLS
            return;
        }

        this.files.add(model);
        this.currentFileIndex++;
        if (this.files.size() == 1) {
            JTailLogger.debug("This one is our first model : " //NON-NLS
                    + model.getFileName());
        }

        this.setChanged();
        this.notifyObservers(createMainModelEvent(model, currentFileIndex, JTailMainModelEvent.Type.CREATED));
    }

    JTailMainModelEvent createMainModelEvent(JTailModel model, int index, JTailMainModelEvent.Type type) {
        return new JTailMainModelEvent(model, index, type);
    }

    public void remove(Object o) {
        final JTailModel model = (JTailModel) o;

        JTailLogger.debug("Going to remove model {} from list {}", o, this.files); //NON-NLS

        if (!this.files.contains(model)) {
            JTailLogger.debug("This model wasn't in our list"); //NON-NLS
            return;
        }

        this.files.remove(model);
        if (this.files.size() == 0) {
            JTailLogger.debug("This one was our last model : " //NON-NLS
                    + ((JTailModel) model).getFileName());
        }
        this.currentFileIndex--;

        this.setChanged();
        this.notifyObservers(createMainModelEvent(model, currentFileIndex + 1, JTailMainModelEvent.Type.REMOVED));
    }

    public boolean contains(Object o) {
        return this.files.contains(o);
    }

    public int size() {
        return this.files.size();
    }

    public List<JTailModel> getFiles() {
        return Collections.unmodifiableList(this.files);
    }

    public JTailModel get(int i) {
        return files.get(i);
    }

    public int getCurrentFileIndex() {
        return currentFileIndex;
    }

    public void setCurrentFileIndex(int currentFileIndex) {
        this.currentFileIndex = currentFileIndex;

        this.setChanged();
        this.notifyObservers(createMainModelEvent(
                this.get(currentFileIndex), currentFileIndex, JTailMainModelEvent.Type.CHANGED));
    }
}
