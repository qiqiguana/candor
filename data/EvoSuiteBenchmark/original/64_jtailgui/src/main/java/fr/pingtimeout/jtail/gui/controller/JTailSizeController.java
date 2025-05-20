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

import fr.pingtimeout.jtail.gui.model.JTailModel;
import fr.pingtimeout.jtail.gui.view.JTailPanel;
import fr.pingtimeout.jtail.util.JTailLogger;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by IntelliJ IDEA.
 * User: plaporte
 * Date: 18 avr. 2010
 * Time: 23:46:29
 * To change this template use File | Settings | File Templates.
 */
public class JTailSizeController extends ComponentAdapter {
    private final JTailPanel panel;
    private final JTailModel model;

    public JTailSizeController(JTailPanel panel, JTailModel model) {
        this.panel = panel;
        this.model = model;
        JTailLogger.debug("Creating SizeController for panel {} and model {}" //NON-NLS
                , this.panel, this.model);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (e.getComponent() != this.panel) {
            JTailLogger.warn("Composant inconnu : {}", //NON-NLS 
                    e.getComponent().getName());
            return;
        }

        // Attention : il faut mesurer la hauteur du jTailPanel
        // => Le contentTextPane n'est jamais réduit s'il a du contenu
        int displayableLines = panel.getHeight() / panel.getCharacterHeight() - 1;

        JTailLogger.debug("Panel height={}, Characted height={}, displayableLines={}", //NON-NLS
                new Object[]{panel.getHeight(), panel.getCharacterHeight(), displayableLines}
        );

        // Si le nombre de lignes affichables ne correspond pas au nombre de lignes affichées
        // Alors il y a eu un redimensionnement -> rafraîchir le contenu de la fenêtre
        if (displayableLines > 0 && displayableLines != model.getNbDisplayedLines()) {

            JTailLogger.debug("Current number of displayed lines : {} starting at line {}", //NON-NLS
                    model.getNbDisplayedLines(),
                    model.getFirstDisplayedLine());
            JTailLogger.debug("Now displaying : {} lines", //NON-NLS 
                    displayableLines);

            model.setNbDisplayedLines(displayableLines);
            panel.getVerticalScrollBar().getModel().setExtent(displayableLines);
        }
    }
}
