/*
 * FileDiffPatch.java
 *
 * Created on 01.11.2007, 15:36:03
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.beiri22.filedp;

import de.beiri22.stringincrementor.StringIncrementor;
import de.beiri22.stringincrementor.helper.StringFromFile;
import de.beiri22.stringincrementor.relativestring.RelativeString;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Klasse, die StringIncrementor auf Dateien anwendet.
 * \author Rico
 */
public class FileDiffPatch {

    /**
     * erstellt einen Patch ohne Debugausgaben.
     * \param origfn Dateiname der Originaldatei
     * \param newfn Dateiname der neuen Datei
     * \param patch Datei der erstellten Patchdatei
     */
    public static void createPatch(String origfn, String newfn, String patch) {
        createPatch(origfn, newfn, patch, false);
    }
 
    /**
     * erstellt einen Patch.
     * \param origfn Dateiname der Originaldatei
     * \param newfn Dateiname der neuen Datei
     * \param patch Datei der erstellten Patchdatei
     * \param verbose gibt an, ob debugausgaben gemacht werden sollen.
     */
    public static void createPatch(String origfn, String newfn, String patch, boolean verbose) {
        try {
            if (verbose) {
                System.out.println("Reading file A...");
            }
            String a = StringFromFile.readString(origfn);
            if (verbose) {
                System.out.println("Reading file B...");
            }
            String b = StringFromFile.readString(newfn);
            if (verbose) {
                System.out.println("Looking for links...");
            }
            long t = System.currentTimeMillis();
            RelativeString r = StringIncrementor.diff(a, b, verbose);
            FileOutputStream C = new FileOutputStream(patch);
            r.bytesToStream(C);
            C.close();
            if (verbose) {
                System.out.println("Patch successfully created in " + (System.currentTimeMillis() - t) / 1000.0 + " seconds");
            }
            if (verbose) {
                System.out.println(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * wendet einen Patch ohne Debugausgaben an.
     * \param origfn Dateiname der Originaldatei
     * \param newfn Dateiname der neu zu erstellenden Datei
     * \param patch Datei der Patchdatei
     */
    public static void doPatch(String origfn, String newfn, String patch) {
        doPatch(origfn, newfn, patch, false);
    }

    /**
     * wendet einen Patch an.
     * \param origfn Dateiname der Originaldatei
     * \param newfn Dateiname der neu zu erstellenden Datei
     * \param patch Datei der Patchdatei
     * \param verbose gibt an, ob debugausgaben gemacht werden sollen.
     */
    public static void doPatch(String origfn, String newfn, String patch, boolean verbose) {
        FileInputStream A = null;
        try {
            if (verbose) {
                System.out.println("Reading file A...");
            }
            String a = StringFromFile.readString(origfn);
            if (verbose) {
                System.out.println("Reading Patch...");
            }
            FileInputStream B = new FileInputStream(patch);
            RelativeString r = new RelativeString(B);
            B.close();
            System.out.println(r);
            long t = System.currentTimeMillis();
            String c = StringIncrementor.patch(a, r, verbose);
            FileOutputStream C = new FileOutputStream(newfn);
            DataOutputStream DC = new DataOutputStream(C);
            DC.writeBytes(c);
            DC.flush();
            DC.close();
            if (verbose) {
                System.out.println("Patch successfully applied in " + (System.currentTimeMillis() - t) / 1000.0 + " seconds");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}