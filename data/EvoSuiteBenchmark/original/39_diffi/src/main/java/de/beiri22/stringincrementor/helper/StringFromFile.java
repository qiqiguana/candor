/*
 * StringFromFile.java
 *
 * Created on 03.11.2007, 13:32:58
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.beiri22.stringincrementor.helper;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author Rico
 */
public final class StringFromFile {

    public static String readString(String fn) throws IOException {
        FileInputStream A = new FileInputStream(fn);
        BufferedInputStream B = new BufferedInputStream(A);
        byte b;
        StringBuffer sb = new StringBuffer();
        synchronized (sb) {
            while (B.available() > 0) {
                sb.append((char) B.read());
            }
        }
        B.close();
        return sb.toString();
    }
}