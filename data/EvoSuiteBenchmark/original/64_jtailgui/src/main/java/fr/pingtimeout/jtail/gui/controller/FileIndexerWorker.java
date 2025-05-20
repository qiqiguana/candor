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

import fr.pingtimeout.jtail.gui.message.ExceptionHandler;
import fr.pingtimeout.jtail.gui.message.InformationHandler;
import fr.pingtimeout.jtail.gui.message.UIMessage;
import fr.pingtimeout.jtail.io.FileIndexer;
import fr.pingtimeout.jtail.util.JTailLogger;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

/**
 * Wrapper de FileIndexer dans une classe SwingWorker.
 * User: plaporte
 * Date: 17 avr. 2010
 * Time: 20:23:26
 * To change this template use File | Settings | File Templates.
 */
public class FileIndexerWorker extends SwingWorker<Void, Void> implements Observer {
    /**
     * Buddle.
     */
    private static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    final FileIndexer fileIndexer;
    final ProgressMonitor progressMonitor;

    public FileIndexerWorker(FileIndexer fileIndexer) {
        JTailLogger.debug("Creating an instance of FileIndexerWorker"); //NON-NLS

        this.fileIndexer = fileIndexer;
        this.fileIndexer.addObserver(this);

        // Se préparer à suivre l'avancement de l'indexation du fichier
        this.progressMonitor = new ProgressMonitor(null,
                bundle.getString("indexing.file"),
                bundle.getString("indexing.file.description"),
                0,
                100);
        this.progressMonitor.setMillisToDecideToPopup(0);
        this.progressMonitor.setMillisToPopup(0);
        this.progressMonitor.setProgress(0);
    }

    @Override
    protected Void doInBackground() {
        try {
            JTailLogger.debug("Working in background..."); //NON-NLS
            this.fileIndexer.index();
        } catch (InterruptedException e) {
            InformationHandler.handle(true, UIMessage.INFO_LOAD_CANCELLED, this.fileIndexer.getFile().getName());
        }

        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        JTailLogger.debug("FileIndexerWorker just received a notification : " //NON-NLS 
                + arg);

        // Lorsque l'indexation progresse, signaler la progression
        setProgress((Integer) arg);
        progressMonitor.setProgress((Integer) arg);

        if (progressMonitor.isCanceled() && !this.isCancelled()) {
            JTailLogger.debug("The user cancelled the progress monitor"); //NON-NLS

            // le progress monitor a été annulé.
            // => Annuler la tâche
            this.cancel(true);
        } else if (this.isCancelled()) {
            JTailLogger.debug("The task has stopped"); //NON-NLS
            // La tâche a été annulée, mais pas le progress monitor
            // Ce cas est rencontré si la tâche lève une erreur
            // => Fermer le progressMonitor
            progressMonitor.close();
        } else if (this.isDone()) {
            JTailLogger.debug("The task is done"); //NON-NLS
        }
    }

    @Override
    protected void done() {
        if (! this.isCancelled()) {
            try {
                get();
                JTailLogger.debug("The task is done"); //NON-NLS
            }
            catch (ExecutionException e) {
                JTailLogger.debug("The task has thrown an Exception"); //NON-NLS
                progressMonitor.close();
                ExceptionHandler.handle(e);
            }
            catch (InterruptedException e) {
                JTailLogger.debug("The task has been interrupted"); //NON-NLS
            }
        }
    }
}
