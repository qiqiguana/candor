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

import fr.pingtimeout.jtail.gui.message.ExceptionHandler;
import fr.pingtimeout.jtail.gui.message.UIMessage;
import fr.pingtimeout.jtail.io.LineReader;
import fr.pingtimeout.jtail.util.JTailLogger;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;

public class JTailModel extends Observable {
    final File file;
    LineReader lineReader;
    final BoundedRangeModel rangeModel;

    public JTailModel(File file, LineReader lineReader) {
        JTailLogger.debug("Creating JTailModel for file {}", //NON-NLS 
                file.getAbsolutePath());
        this.file = file;
        this.lineReader = lineReader;

        final int value = 0;
        final int extent = 1;
        final int min = 0;
        final int max = lineReader.getIndexSize();

        JTailLogger.debug("Creating bounded range model with value={}, extent={}, min={}, max={}", //NON-NLS
                new Object[]{value, extent, min, max});

        this.rangeModel = new DefaultBoundedRangeModel(value, extent, min, max);
    }

    public String getContent() {
        // Récupérer le nouveau contenu
        // FIXME optimiser cet appel !
        // FIXME ici, on refait une opération d'entrée/sortie sur tout le block, ce qui est peut être inutile.
        final List<String> newContent;
        try {
            newContent = lineReader.readBlock(rangeModel.getValue(), rangeModel.getValue() + rangeModel.getExtent());
        } catch (IOException e) {
            ExceptionHandler.handle(e, UIMessage.ERROR_READING_FILE);
            return "";
        }

        StringBuffer newContentSb = new StringBuffer();
        for (String line : newContent) {
            newContentSb.append(line).append('\n');
        }
        // Supprimer le dernier caractère \n
        if (newContentSb.length() > 0) {
            newContentSb.deleteCharAt(newContentSb.length() - 1);
        }
        return newContentSb.toString();
    }

    public String getLineNumbers() {
        final StringBuffer buffer = new StringBuffer();

        final int fisrtValue = rangeModel.getValue();
        final int nbLines = rangeModel.getExtent();

        JTailLogger.debug("Generating line numbers from {} to {}", //NON-NLS
                fisrtValue, nbLines);

        // From line 1 to 5, print 1, 2 and 3
        for (int i = fisrtValue; i < fisrtValue + nbLines - 1; i++) {
            buffer.append(i).append('\n');
        }

        // And print 4
        buffer.append(fisrtValue + nbLines - 1);

        return buffer.toString();
    }

    public int getFirstLine() {
        return this.rangeModel.getMinimum();
    }

    public int getFirstDisplayedLine() {
        return this.rangeModel.getValue();
    }

    public int getNbDisplayedLines() {
        return this.rangeModel.getExtent();
    }

    public int getTotalNbLines() {
        return this.rangeModel.getMaximum();
    }

    public String getFileName() {
        return this.file.getName();
    }

    JTailModelEvent createModelEvent(JTailModelEvent.Type type) {
        return new JTailModelEvent(
                type,
                this.rangeModel.getMinimum(),
                this.rangeModel.getValue(),
                this.rangeModel.getExtent(),
                this.rangeModel.getMaximum()
        );
    }

    public void setFirstDisplayedLine(int firstLine) {
        this.rangeModel.setValue(firstLine);

        JTailLogger.debug("New range : value={}, extent={}, min={}, max={}", //NON-NLS
                new Object[]{rangeModel.getValue(), rangeModel.getExtent(), rangeModel.getMinimum(), rangeModel.getMaximum()});

        this.setChanged();
        this.notifyObservers(createModelEvent(JTailModelEvent.Type.SCROLLED));
    }

    public void setNbDisplayedLines(int nbDisplayedLines) {
        this.rangeModel.setExtent(nbDisplayedLines);

        JTailLogger.debug("New range : value={}, extent={}, min={}, max={}", //NON-NLS
                new Object[]{rangeModel.getValue(), rangeModel.getExtent(), rangeModel.getMinimum(), rangeModel.getMaximum()});

        this.setChanged();
        this.notifyObservers(createModelEvent(JTailModelEvent.Type.RESIZED));
    }

    // TODO Prévoir le cas où le fichier est modifié

}
