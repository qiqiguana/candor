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
import java.util.ResourceBundle;

/**
 * Action utilisée dans JTail.
 *
 * @author Pierre Laporte
 *         Date: 9 avr. 2010
 */
public abstract class JTailAbstractAction extends AbstractAction {
    protected static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    /**
     * Constructeur d'action sans description ni raccourci clavier.
     *
     * @param name     le nom de l'action
     * @param mnemonic le mnémonique associé
     */
    public JTailAbstractAction(String name, String mnemonic) {
        JTailLogger.debug("Creating action with name '{}' and type {}", //NON-NLS
                name, getClass().getSimpleName());

        this.putValue(NAME, name);
        this.putValue(MNEMONIC_KEY, KeyStroke.getKeyStroke(mnemonic).getKeyCode());
    }

    /**
     * Constructeur d'action sans description ni raccourci clavier.
     *
     * @param name             le nom de l'action
     * @param shortDescription La description associée
     * @param mnemonic         le mnémonique associé
     * @param icon             L'icône
     */
    public JTailAbstractAction(String name, String shortDescription, String mnemonic, Icon icon) {
        this(name, mnemonic);

        this.putValue(SHORT_DESCRIPTION, shortDescription);
        this.putValue(SMALL_ICON, icon);
    }

    /**
     * Constructeur d'action sans icône.
     *
     * @param name             Le nom de l'action
     * @param shortDescription La description associée
     * @param mnemonic         Le mnémonique associé
     * @param accelerator      Le raccourci clavier de l'action
     */
    public JTailAbstractAction(String name, String shortDescription, String mnemonic, String accelerator) {
        this(name, mnemonic);

        this.putValue(SHORT_DESCRIPTION, shortDescription);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(accelerator));
    }

    /**
     * Constructeur d'action avec icône.
     *
     * @param name             Le nom de l'action
     * @param shortDescription La description associée
     * @param mnemonic         Le mnémonique associé
     * @param accelerator      Le raccourci clavier de l'action
     * @param icon             L'icône
     */
    public JTailAbstractAction(String name, String shortDescription, String mnemonic, String accelerator, Icon icon) {
        this(name, shortDescription, mnemonic, accelerator);

        this.putValue(SMALL_ICON, icon);
    }
}
