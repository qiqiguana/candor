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

import fr.pingtimeout.jtail.util.JTailLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action de fermeture de l'application.
 *
 * @author Pierre Laporte
 *         Date: 11 avr. 2010
 */
public class QuitAction extends JTailAbstractAction {
    /**
     * Icône associée à l'action.
     */
    private static final ImageIcon QUIT_ICON = new ImageIcon(OpenFileAction.class.getResource("x16.png"));

    public QuitAction() {
        super(bundle.getString("action.exit.label"),
                bundle.getString("action.exit.tooltip"),
                bundle.getString("action.exit.mnemonic"),
                bundle.getString("action.exit.accelerator"),
                QUIT_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTailLogger.debug("Action 'Quit' performed"); //NON-NLS
        System.exit(0);
    }
}