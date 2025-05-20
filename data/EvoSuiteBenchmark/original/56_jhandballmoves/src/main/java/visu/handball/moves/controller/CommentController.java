/* 
 * Created on 29.11.2006 
 * Created by Thomas Halm 
 * Copyright (C) 2006  Richard Doerfler, Thomas Halm
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
package visu.handball.moves.controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.HandballModelListener;
import visu.handball.moves.views.CommentView;

public class CommentController implements DocumentListener,
		HandballModelListener {

	private CommentView view;

	private HandballModel model;

	public CommentController(HandballModel model, CommentView view) {
		this.view = view;
		this.model = model;

		view.getEditorPane().getDocument().addDocumentListener(this);
		model.addListener(this);
	}

	public void changedUpdate(DocumentEvent e) {
		updateComment();
	}

	public void insertUpdate(DocumentEvent e) {
		updateComment();
	}

	public void removeUpdate(DocumentEvent e) {
		updateComment();
	}

	private void updateComment() {
		model.setComment(view.getCommentText());
	}

	public void modelChanged() {
		if (model != null && view != null) {
			if (!model.getComment().equals(view.getCommentText())) {
				view.setCommentText(model.getComment());
			}
		}

	}

}