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

package fr.pingtimeout.jtail.gui.action;

import fr.pingtimeout.jtail.gui.message.InformationHandler;
import fr.pingtimeout.jtail.gui.message.UIMessage;
import fr.pingtimeout.jtail.gui.model.JTailMainModel;
import fr.pingtimeout.jtail.util.JTailLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action de fermeture de tous les fichiers ouverts.
 *
 * @author Pierre Laporte
 *         Date: 11 avr. 2010
 */
public class CloseAllAction extends JTailAbstractAction {
    /**
     * Le modèle de l'application
     */
    private final JTailMainModel jTailMainModel;

    /**
     * Icône associée à l'action.
     */
    private static final ImageIcon CLOSE_ALL_ICON = new ImageIcon(OpenFileAction.class.getResource("delete_all16.png"));

    public CloseAllAction(JTailMainModel jTailMainModel) {
        super(bundle.getString("action.closeall.label"),
                bundle.getString("action.closeall.tooltip"),
                bundle.getString("action.closeall.mnemonic"),
                bundle.getString("action.closeall.accelerator"),
                CLOSE_ALL_ICON);
        this.jTailMainModel = jTailMainModel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JTailLogger.debug("Action 'Close all' performed"); //NON-NLS

        int currentFile = this.jTailMainModel.getCurrentFileIndex();

        if (currentFile == -1) {
            JTailLogger.debug("Cannot close anything, there is no tab..."); //NON-NLS
            InformationHandler.handle(true, UIMessage.INFO_NO_FILE_OPENED);
            return;
        }

        while (currentFile != -1) {
            this.jTailMainModel.remove(this.jTailMainModel.get(currentFile));
            currentFile = this.jTailMainModel.getCurrentFileIndex();
        }
    }
}

