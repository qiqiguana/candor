package visu.handball.moves.controller;

import java.awt.event.*;
import javax.swing.*;

public class JMenuHelper {

    public static JMenu addMenuBarItem(JMenuBar menuBar, String s) {
        JMenu menu;
        /**
         * Suche nach '_' und nimm das nächste Zeichen als Mnemonic,
         * sonst: normales Menu
         */
        if (s.indexOf("_") > -1) {
            int pos = s.indexOf("_");
            char c = s.charAt(pos + 1);
            StringBuffer sb = new StringBuffer(s).delete(pos, pos + 1);
            menu = new JMenu(sb.toString());
            menu.setMnemonic(c);
        } else
            menu = new JMenu(s);
        menuBar.add(menu);
        return menu;
    }
}
