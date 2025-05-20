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

import fr.pingtimeout.jtail.gui.controller.FileIndexerWorker;
import fr.pingtimeout.jtail.gui.message.ExceptionHandler;
import fr.pingtimeout.jtail.gui.message.UIMessage;
import fr.pingtimeout.jtail.gui.model.JTailMainModel;
import fr.pingtimeout.jtail.gui.model.JTailModel;
import fr.pingtimeout.jtail.gui.model.OpenFileModel;
import fr.pingtimeout.jtail.gui.view.OpenFileDialog;
import fr.pingtimeout.jtail.io.FileIndexer;
import fr.pingtimeout.jtail.io.LineReader;
import fr.pingtimeout.jtail.io.index.FileIndex;
import fr.pingtimeout.jtail.io.index.RamFileIndex;
import fr.pingtimeout.jtail.io.index.RomFileIndex;
import fr.pingtimeout.jtail.util.JTailLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;


/**
 * Action d'ouverture d'un fichier.
 *
 * @author Pierre Laporte
 *         Date: 9 avr. 2010
 */
public class OpenFileAction extends JTailAbstractAction {
    /**
     * Buddle.
     */
    private static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    /**
     * Icône associée à l'action.
     */
    private static final ImageIcon OPEN_ICON = new ImageIcon(OpenFileAction.class.getResource("add16.png"));

    /**
     * Le modèle de l'application
     */
    private final JTailMainModel jTailMainModel;

    /**
     * Le modèle associé à l'ouverture de fichier.
     */
    private final OpenFileModel openFileModel;

    /**
     * La boite de dialogue utilisée pour choisir le fichier à ouvrir.
     */
    private final OpenFileDialog openFileDialog;

    /**
     * Constructeur de l'action "Ouvrir un fichier"
     *
     * @param jTailMainModel le modèle utilisé par l'application
     */
    public OpenFileAction(JTailMainModel jTailMainModel, OpenFileModel openFileModel, OpenFileDialog openFileDialog) {
        super(bundle.getString("action.open.label"),
                bundle.getString("action.open.tooltip"),
                bundle.getString("action.open.mnemonic"),
                bundle.getString("action.open.accelerator"),
                OPEN_ICON);
        this.jTailMainModel = jTailMainModel;
        this.openFileModel = openFileModel;
        this.openFileDialog = openFileDialog;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JTailLogger.debug("Action 'Open' performed"); //NON-NLS

        this.openFileDialog.setVisible(true);
    }
}
