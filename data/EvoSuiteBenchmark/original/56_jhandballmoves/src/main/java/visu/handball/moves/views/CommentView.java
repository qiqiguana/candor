/* 
 * Created on 31.10.2006 
 * Created by N. Rudolph 
 * Copyright (C) 2006 N. Rudolph 
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation; either version 2 
 * of the License, or (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU General Public License for more details. 
 *
 * You should have received a copy of the GNU General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
 */
package visu.handball.moves.views;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

public class CommentView extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private JEditorPane jep;

	/**
	 * Baut ein scrollable JEditorPane in ein Internal Frame
	 * 
	 * @param text Im EditorPane angezeigter Text
	 */
	public CommentView(String text) {
		super(text, true, false, false, true);
		jep = new JEditorPane();
		jep.setFont(new Font("Arial", Font.PLAIN, 10));
		jep.setBackground(new Color(240,255,189));
		JScrollPane textView = new JScrollPane(jep);
		this.add(textView);
		jep.setCaretPosition(0);
	}
	
	
	public JEditorPane getEditorPane() {
		return jep;
	}


	public String getCommentText() {
		return jep.getText();
	}
	public void setCommentText(String text) {
		jep.setText(text);
	}
}