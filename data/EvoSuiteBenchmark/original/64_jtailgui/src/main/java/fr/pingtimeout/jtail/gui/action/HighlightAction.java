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
 * Created by IntelliJ IDEA.
 * User: plaporte
 * Date: 2 mai 2010
 * Time: 22:59:23
 * To change this template use File | Settings | File Templates.
 */
public class HighlightAction extends JTailAbstractAction {
    /**
     * Le modèle de l'application
     */
    private final JTailMainModel jTailMainModel;

    /**
     * Icône associée à l'action.
     */
    private static final ImageIcon HIGHLIGHT_ICON = new ImageIcon(OpenFileAction.class.getResource("image_file16.png"));

    public HighlightAction(JTailMainModel jTailMainModel) {
        super(bundle.getString("action.highlight.label"),
                bundle.getString("action.highlight.tooltip"),
                bundle.getString("action.highlight.mnemonic"),
                bundle.getString("action.highlight.accelerator"),
                HIGHLIGHT_ICON);
        this.jTailMainModel = jTailMainModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTailLogger.debug("Action 'Highlight' performed"); //NON-NLS

        InformationHandler.handle(UIMessage.INFO_NOT_IMPLEMENTED_YET);

        /*
        Component source = (Component) e.getSource();
        while (! (source instanceof JTailMainFrame)) {
            source = source.getParent();
        }

        JTailLogger.debug("source : {}", source); //NON-NLS

        final HighlightDialog dialog = new HighlightDialog((JTailMainFrame)source);
        dialog.pack();
        dialog.setVisible(true);
        */
    }
}
