/*
 * Created on 31.08.2006
 * Created by Richard Doerfler, Thomas Halm
 * Copyright (C) 2006  Richard Doerfler, Thomas Halm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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


	private static JMenuItem processAction(Action a) {
		if (a != null){
			return new JMenuItem(a);
		} else {
			return new JMenuItem();
		}
	}
	
	/**
	 * Setze das Zeichen nach '_' als Mnemonic des MenuItems
	 * @param s
	 */
	private static void processMnemonic(JMenuItem menuItem, String s) {
		if (s.indexOf("_") > -1) {
			int pos = s.indexOf("_");
			char c = s.charAt(pos + 1);
			StringBuffer sb = new StringBuffer(s).delete(pos, pos + 1);
			menuItem.setName(sb.toString());
			menuItem.setMnemonic(c);
		}
	}

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
	public static JMenuItem addMenuItem(JMenu m, String s, char keyChar,
			Action a, ActionListener al) {
		if (s.startsWith("-")) {
			m.addSeparator();
			return null;
		}
		
		JMenuItem menuItem = processAction(a);
		
		processMnemonic(menuItem,s);

		//füge den Short-Cut hinzu
		if (keyChar != 0)
			menuItem.setAccelerator(KeyStroke.getKeyStroke(keyChar,
					InputEvent.CTRL_MASK));

		//Actionslistener anmelden
		if (al != null)
			menuItem.addActionListener(al);
		
		//dem Menu hinzufügen
		m.add(menuItem);

		return menuItem;
	}


	public static JMenuItem addMenuItem(JMenu m, String s, char c) {
		return addMenuItem(m, s, c, null, null);
	}

	public static JMenuItem addMenuItem(JMenu m, String s) {
		return addMenuItem(m, s, (char) 0, null, null);
	}

	public static JMenuItem addMenuItem(JMenu m, String s, ActionListener al) {
		return addMenuItem(m, s, (char) 0,  null, al);
	}
	
	public static JMenuItem addMenuItem(JMenu m, String s, Action a){
		return addMenuItem(m, s,(char) 0, a, null);
	}
	public static JMenuItem addMenuItem(JMenu m, String s,char c, Action a){
		return addMenuItem(m, s, c, a, null);
	}
}