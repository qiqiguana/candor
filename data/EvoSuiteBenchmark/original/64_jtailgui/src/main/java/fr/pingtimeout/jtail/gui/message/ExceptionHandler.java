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
 * Handler d'exceptions techniques.
 * Cette classe propose plusieurs méthodes afin d'indiquer à un utilisateur qu'une exception a
 * empêché le déroulement d'un traitement.
 *
 * @author Pierre Laporte
 *         Date: 8 avr. 2010
 */
public class ExceptionHandler extends MessageHandler {
    /**
     * Buddle.
     */
    private static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    /**
     * JTailLogger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);


    /**
     * Affiche un message d'erreur indiquant que le traitement en cours n'a pas pu être terminé correctement. Le message
     * d'erreur affichée correspond à la cause <code>message</code> passée en paramètre. Ce message est précédé par le
     * message d'erreur générique si le paramètre <code>withGenericMessage</code> vaut <code>true</code>.
     * L'exception associée est tracée dans les logs de l'application au niveau ERROR
     * mais n'est pas affichée dans la popup.
     *
     * @param e                  l'exception associée à l'erreur
     * @param uiMessage          le message à associer à l'erreur
     * @param withGenericMessage flag indiquant si le message d'erreur doit être précédé du message d'erreur générique
     * @param params             les paramètres éventuels à utiliser dans le message
     */
    public static void handle(Exception e, boolean withGenericMessage, UIMessage uiMessage, Object... params) {
        final String errorMessage;
        final String message;
        final String cause = bundle.getString(uiMessage.key);
        final String title = bundle.getString(UIMessage.ERROR_GENERIC_TITLE.key);

        if (params.length == 0) {
            errorMessage = cause;
        } else {
            errorMessage = MessageFormat.format(cause, params);
        }

        if (withGenericMessage) {
            final String genericMessage = bundle.getString(UIMessage.ERROR_GENERIC_MESSAGE.key);
            message = MessageFormat.format(genericMessage, errorMessage);
        } else {
            message = errorMessage;
        }

        if (LOG.isErrorEnabled()) {
            LOG.error(message, e);
        }

        MessageHandler.handle(message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Affiche un message d'erreur indiquant que le traitement en cours n'a pas pu être terminé correctement. Le message
     * d'erreur est constitué du message d'erreur générique auquel est ajouté le message associé à la cause
     * <code>message</code> passée en paramètre.
     * L'exception associée est tracée dans les logs de l'application au niveau ERROR
     * mais n'est pas affichée dans la popup.
     *
     * @param e       l'exception associée à l'erreur
     * @param message le message à associer à l'erreur
     * @param params  les paramètres éventuels à utiliser dans le message
     */
    public static void handle(Exception e, UIMessage message, Object... params) {
        handle(e, true, message, params);
    }

    /**
     * Affiche un message d'erreur générique indiquant que le traitement en cours n'a pas pu être terminé correctement.
     * L'exception associée est tracée dans les logs de l'application au niveau ERROR
     * mais n'est pas affichée dans la popup.
     *
     * @param e l'exception associée à l'erreur
     */
    public static void handle(Exception e) {
        handle(e, false, UIMessage.ERROR_GENERIC_MESSAGE, "");
    }
}
