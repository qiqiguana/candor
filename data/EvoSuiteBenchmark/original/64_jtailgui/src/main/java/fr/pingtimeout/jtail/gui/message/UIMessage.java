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

/**
 * Enumeration de toutes les clés des messages d'erreur de l'application.
 *
 * @author Pierre Laporte
 *         Date: 8 avr. 2010
 */
public enum UIMessage {
    /**
     * Message d'erreur générique. Ce message peut contenir une zone variable : {0}.
     */
    ERROR_GENERIC_MESSAGE("error.generic.message"),

    /**
     * Titre générique d'un message d'erreur.
     */
    ERROR_GENERIC_TITLE("error.generic.title"),

    /**
     * Une erreur est survenue pendant la lecture du fichier.
     */
    ERROR_READING_FILE("error.reading.file"),

    /**
     * Le fichier n'existe pas.
     */
    ERROR_FILE_NOT_FOUND("error.file.not.found"),

    /**
     * Titre générique d'un message d'information.
     */
    INFO_GENERIC_MESSAGE("info.generic.message"),

    /**
     * Titre générique d'un message d'information.
     */
    INFO_GENERIC_TITLE("info.generic.title"),

    /**
     * Le chargement du fichier a été annulé.
     */
    INFO_LOAD_CANCELLED("info.load.cancelled"),

    /**
     * Aucun fichier n'est ouvert.
     */
    INFO_NO_FILE_OPENED("info.no.file.opened"),

    /**
     * Aucun fichier n'est ouvert.
     */
    INFO_NO_FILE_SELECTED("info.no.file.selected"),

    /**
     * Fonctionnalité non développée.
     */
    INFO_NOT_IMPLEMENTED_YET("info.not.implemented.yet"),

    /**
     * Consulter les logs pour plus d'informations.
     */
    CHECK_LOGS_FOR_INFORMATION("check.logs.for.information"),;

    /**
     * La clé du message.
     */
    public final String key;

    private UIMessage(String key) {
        this.key = key;
    }
}
