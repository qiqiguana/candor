/*
 * CommandLine.java
 *
 * Created on 04.11.2007, 17:33:26
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.beiri22.commandline;

import java.util.Vector;

/**
 * Klasse, die die Kommandozeile parst.
 * \author Rico
 */
public final class CommandLine {

    Vector<Option> options;///<enthaelt die zu parsenden Optionen.

    /**
     * erstellt einen neuen Kommandozeilenparser mit den uebergebenen Optionen.
     * \param opts Array von Optionen oder einfache Auflistung.
     */
    public CommandLine(Option... opts) {
        options = new Vector<Option>();
        for (Option o : opts) {
            options.add(o);
        }
    }

    /**
     * fuegt eine Option hinzu.
     * \param obj hinzuzufuegende Option
     */
    public void addOption(Option obj) {
        options.addElement(obj);
    }

    /**
     * parst die uebergebene Kommandozeile.
     * \param args StringArray der Kommandozeile.
     */
    public void parse(String[] args) {
        argloop:
        for (String s : args) {
            for (Option o : options) {
                if (o.is(s)) {
                    o.parse(s);
                    continue argloop;
                }
            }
        }
    }
}