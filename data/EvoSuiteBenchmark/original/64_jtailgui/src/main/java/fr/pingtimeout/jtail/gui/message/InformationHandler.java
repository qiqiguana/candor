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

package fr.pingtimeout.jtail.gui.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Handler de messages d'informations.
 * Cette classe propose plusieurs méthodes afin d'afficher un message à un utilisateur.
 *
 * @author Pierre Laporte
 *         Date: 8 avr. 2010
 */
public class InformationHandler {
    /**
     * Buddle.
     */
    private static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    /**
     * JTailLogger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(InformationHandler.class);


    /**
     * Affiche un message d'information à l'utilisateur. Le message affiché est tracé dans les logs au niveau INFO.
     *
     * @param withGenericMessage flag indiquant si le message doit être précédé d'un message indiquant
     *                           que l'opération n'a pas pu être effectuée.
     * @param uiMessage          le message d'information à afficher
     * @param params             les paramètres éventuels à utiliser dans le message
     */
    public static void handle(boolean withGenericMessage, UIMessage uiMessage, Object... params) {
        final String informationMessage;
        final String message;
        final String cause = bundle.getString(uiMessage.key);
        final String title = bundle.getString(UIMessage.INFO_GENERIC_TITLE.key);

        if (params.length == 0) {
            informationMessage = cause;
        } else {
            informationMessage = MessageFormat.format(cause, params);
        }

        if (withGenericMessage) {
            final String genericMessage = bundle.getString(UIMessage.INFO_GENERIC_MESSAGE.key);
            message = MessageFormat.format(genericMessage, informationMessage);
        } else {
            message = informationMessage;
        }

        if (LOG.isInfoEnabled()) {
            LOG.info(message);
        }

        MessageHandler.handle(message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Affiche un message d'information à l'utilisateur. Le message affiché est tracé dans les logs au niveau INFO.
     *
     * @param uiMessage le message d'information à afficher
     * @param params    les paramètres éventuels à utiliser dans le message
     */
    public static void handle(UIMessage uiMessage, Object... params) {
        handle(false, uiMessage, params);
    }

}
