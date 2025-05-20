/*
 * Option.java
 *
 * Created on 04.11.2007, 16:58:29
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.beiri22.commandline;

/**
 * Klasse, die eine Kommandozeilenoption darstellt.
 * \author Rico
 */
public class Option {

    private String mname; ///<Optionsname
    private String mbeschriebung;///<Beschreibung der Option fuer eine Debugausgabe.
    private String mdelim;///<Trennzeichen zwischen Option und uebergebenem Wert.
    private boolean mhasArg;///<Gibt an, ob ein Wert zu dieser Option erwartet wird.
    private String mdefaultv;///<Defaultwert, der verwendet wird, wenn kein Wert uebergeben wurde.
    private String mvalue;///<Uebergebener Wert, der nach Durchlauf des Checks abgerufen werden kann.
    private boolean misSet;///<Gibt an, ob diese Option bereits gesetzt ist.

    /**
     * erstellt eine neue Option.
     * \param name Optionsname
     * \param beschreibung Optionsbeschreibung
     * \param hasArg erwartet die Option einen Wert?
     * \param delim Trennzeichen zwischen Option und uebergebenem Wert 
     * \param defaultv Defaultwert, der verwendet wird, wenn kein Wert uebergeben wurde
     */
    public Option(String name, String beschreibung, boolean hasArg, String delim, String defaultv) {
        this.mname = name;
        this.mbeschriebung = beschreibung;
        this.mdelim = delim;
        this.mhasArg = hasArg;
        this.mdefaultv = defaultv;
        misSet = false;
    }
    
    /**
     * erstellt eine neue Option, die keine Parameter erwartet.
     * \param name Optionsname
     * \param beschreibung Optionsbeschreibung
     */
    public Option(String name, String beschreibung) {
        this(name, beschreibung, false, "", "");
    }
    
    /**
     * erstellt eine neue Option, die keine Parameter erwartet und keine Beschreibung
     * enthaelt.
     * \param name Optionsname
     */
    public Option(String name) {
        this(name, "", false, "", "");
    }
    
    /**
     * erstellt eine neue Option ohne Beschreibung.
     * \param name Optionsname
     * \param hasArg erwartet die Option einen Wert?
     * \param delim Trennzeichen zwischen Option und uebergebenem Wert 
     * \param defaultv Defaultwert, der verwendet wird, wenn kein Wert uebergeben wurde
     */
    public Option(String name, boolean hasArg, String delim, String defaultv) {
        this(name, "", hasArg, delim, defaultv);
    }

    /**
     * gibt die Beschreibung zurück.
     * \return Beschreibung der Option
     */
    public String getBeschriebung() {
        return mbeschriebung;
    }
    
    /**
     * gibt das Trennzeichen zurück.
     * \return Trennezeichen zwischen Optionsname und Wert
     */
    public String getDelim() {
        return mdelim;
    }

    /**
     * gibt zurück, ob die Option einen Wert erwartet.
     * \return braucht Option?
     */
    public boolean isHasArg() {
        return mhasArg;
    }

    /**
     * gibt den Namen zurück.
     * \return Name der Option
     */
    public String getName() {
        return mname;
    }

    /**
     * gibt den Defaultwert zurück.
     * \return Defaultwert der Option
     */
    public String getDefaultv() {
        return mdefaultv;
    }

    /**
     * gibt zurück, ob die Option schon gesetzt ist.
     * \return schon gesetzt?
     */
    public boolean isIsSet() {
        return misSet;
    }

    /**
     * gibt den uebergebenen Wert zurück.
     * \return uebergebener Wert
     * \throws RuntimeException falls die Option nicht gesetzt wurde oder keinen
     * Wert besitzt.
     */
    public String getValue() {
        if (!mhasArg) {
            throw new RuntimeException("Option has no argument.");
        }
        if (!misSet) {
            throw new RuntimeException("Option is not set.");
        }
        return mvalue;
    }

    /**
     * prueft, ob der uebergebene String zu dieser Option passt(sie darstellt)
     * \param s zu pruefender String.
     * \return Ergebnis dieser Prüfung
     */
    public boolean is(String s) {
        return s.startsWith((mname.isEmpty() ? "" : "-") + mname + (mhasArg ? mdelim : "")) && !misSet;
    }

    /**
     * liest den Wert, falls erwartet, ein und setzt die Option.
     * \param s zu parsender String.
     */
    public void parse(String s) {
        misSet = true;
        if (mhasArg) {
            mvalue = s.substring(mname.length() + (mname.isEmpty() ? 0 : 1) + mdelim.length());
        } else {
            mvalue = mdefaultv;
        }
    }
}