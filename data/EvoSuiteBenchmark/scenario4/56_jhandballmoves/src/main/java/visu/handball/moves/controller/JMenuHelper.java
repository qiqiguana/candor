package visu.handball.moves.controller;

import java.awt.event.*;
import javax.swing.*;

public class JMenuHelper {

    /**
     * Add an element to the menubar.
     *
     * @param menuBar a JMenuBar
     * @param s a String
     *
     * @return added JMenu
     */
    public static JMenu addMenuBarItem(JMenuBar menuBar, String s);

    private static JMenuItem processAction(Action a);

    /**
     * Setze das Zeichen nach '_' als Mnemonic des MenuItems
     * @param s
     */
    private static void processMnemonic(JMenuItem menuItem, String s);

    /**
     * Insert a JMenuItem to a given JMenu.
     *
     * @param m
     *            a JMenu
     * @param s
     *            a String
     * @param keyChar
     *            a char
     * @param al
     *            an ActionListener
     *
     * @return a JMenuItem
     */
    public static JMenuItem addMenuItem(JMenu m, String s, char keyChar, Action a, ActionListener al);

    public static JMenuItem addMenuItem(JMenu m, String s, char c);

    public static JMenuItem addMenuItem(JMenu m, String s);

    public static JMenuItem addMenuItem(JMenu m, String s, ActionListener al);

    public static JMenuItem addMenuItem(JMenu m, String s, Action a);

    public static JMenuItem addMenuItem(JMenu m, String s, char c, Action a);
}
