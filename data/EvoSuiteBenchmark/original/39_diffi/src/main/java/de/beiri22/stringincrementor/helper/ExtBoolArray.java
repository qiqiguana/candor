/*
 * ExtBoolArray.java
 *
 * Created on 31.10.2007, 14:34:55
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.beiri22.stringincrementor.helper;

/**
 *
 * @author Rico
 */
public final class ExtBoolArray {

    /**Enthaelt die Booleanwerte*/
    private boolean[] werte;

    /**
     * erstellt ein neues ExtBoolArray mit allen Werten False.
     * @param len Anzahl der Booleanwerte
     */
    public ExtBoolArray(int len) {
        werte = new boolean[len];
    }

    /**
     * setzt den Bereich mit den Indexen a bis b auf True.
     * @param a Startindex
     * @param b Endindex
     */
    public void setTrue(int a, int b) {
        for (int i = a; i <= b; i++) {
            werte[i] = true;
        }
    }

    /**
     * prueft, ob der Bereich mit den Indexen a bis b ausschließlich False-Werte
     * enthaelt.
     * @param a Startindex
     * @param b Endindex
     * @return True - wenn alles False-Werte sind.
     */
    public boolean isFalse(int a, int b) {
        for (int i = a; i <= b; i++) {
            if (werte[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Zahl die uebriggebliebenen False-Werte.
     * @return Anzahl der False-Werte.
     */
    public int left() {
        int result = 0;
        for (int i = 0; i < werte.length; i++) {
            if (!werte[i]) {
                result++;
            }
        }
        return result;
    }
}