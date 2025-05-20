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

import javax.swing.*;

/**
 * Handler de messages affichés à l'utilisateur.
 * Cette classe propose plusieurs méthodes afin d'indiquer un message à un utilisateur à l'aide d'une popup.
 * Les messages affichés peuvent être typés selon leur nature (information, avertissement, erreur, question).
 *
 * @author Pierre Laporte
 *         Date: 11 avr. 2010
 */
public abstract class MessageHandler {
    /**
     * Affiche une popup contentant le message <code>message</code> et ayant le titre <code>title</code>.
     * La popup est typée avec le paramètre <code>messageType</code>.
     *
     * @param message     Le message à afficher
     * @param title       Le titre de la popup
     * @param messageType Le type du message.
     *                    Ce titre doit être une constante définie dans la classe <code>JOptionPane</code>
     */
    public static void handle(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(
                null,
                message,
                title,
                messageType);
    }
}
