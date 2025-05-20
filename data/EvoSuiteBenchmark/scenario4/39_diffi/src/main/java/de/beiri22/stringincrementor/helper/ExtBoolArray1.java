package de.beiri22.stringincrementor.helper;

/**
 * @author Rico
 */
public final class ExtBoolArray {

    /**
     * Enthaelt die Booleanwerte
     */
    private boolean[] werte;

    /**
     * erstellt ein neues ExtBoolArray mit allen Werten False.
     * @param len Anzahl der Booleanwerte
     */
    public ExtBoolArray(int len) {
    }

    /**
     * setzt den Bereich mit den Indexen a bis b auf True.
     * @param a Startindex
     * @param b Endindex
     */
    public void setTrue(int a, int b);

    /**
     * prueft, ob der Bereich mit den Indexen a bis b ausschließlich False-Werte
     * enthaelt.
     * @param a Startindex
     * @param b Endindex
     * @return True - wenn alles False-Werte sind.
     */
    public boolean isFalse(int a, int b);

    /**
     * Zahl die uebriggebliebenen False-Werte.
     * @return Anzahl der False-Werte.
     */
    public int left();
}
