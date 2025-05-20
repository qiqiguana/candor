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

package fr.pingtimeout.jtail.gui.controller;

import fr.pingtimeout.jtail.util.JTailLogger;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * ProgressMonitor dédié à l'indexation d'un fichier donné.
 *
 * @author Pierre Laporte
 *         Date: 12 mai 2010
 */
public class IndexationProgressMonitor extends ProgressMonitor implements Observer {
    /**
     * Constructs a graphic object that shows progress, typically by filling
     * in a rectangular bar as the process nears completion.
     *
     * @param parentComponent the parent component for the dialog box
     * @param message         a descriptive message that will be shown
     *                        to the user to indicate what operation is being monitored.
     *                        This does not change as the operation progresses.
     *                        See the message parameters to methods in
     *                        {@link javax.swing.JOptionPane#message}
     *                        for the range of values.
     * @param note            a short note describing the state of the
     *                        operation.  As the operation progresses, you can call
     *                        setNote to change the note displayed.  This is used,
     *                        for example, in operations that iterate through a
     *                        list of files to show the name of the file being processes.
     *                        If note is initially null, there will be no note line
     *                        in the dialog box and setNote will be ineffective
     * @param min             the lower bound of the range
     * @param max             the upper bound of the range
     * @see javax.swing.JDialog
     * @see javax.swing.JOptionPane
     */
    public IndexationProgressMonitor(Component parentComponent, Object message, String note, int min, int max) {
        super(parentComponent, message, note, min, max);
    }

    @Override
    public void update(Observable o, Object arg) {
        JTailLogger.debug("Just received an update for Observable {} and with object {}", o, arg);
    }
}
