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

package visu.handball.moves.views;

import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import visu.handball.moves.actions.AnimationModusAction;
import visu.handball.moves.actions.EditModusAction;
import visu.handball.moves.actions.PlaceBallAction;
import visu.handball.moves.actions.PlaceDefenderAction;
import visu.handball.moves.actions.PlaceOffenderAction;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.HandballModelListener;


public class PlayerToolBar extends JToolBar implements HandballModelListener {

	private ButtonGroup group;

	private JToggleButton placeOffender;

	private JToggleButton placeDefender;

	private JToggleButton placeBall;

	private JToggleButton edit;

	private JToggleButton animation;

	public void modelChanged() {
		switch (model.getState()) {
		case PLACE_OFFENDERS:
			if (!placeOffender.isSelected()) {
				placeOffender.setSelected(true);
			}
			break;
		case PLACE_DEFENDERS:
			if (!placeDefender.isSelected()) {
				placeDefender.setSelected(true);
			}
			break;
		case PLACE_BALL:
			if (!placeBall.isSelected()) {
				placeBall.setSelected(true);
			}
			break;
		case EDIT:
			if (!edit.isSelected()) {
				edit.setSelected(true);
			}
			break;
		case ANIMATION:
			if (!animation.isSelected()) {
				animation.setSelected(true);
			}
			break;

		}

	}

	private HandballModel model;

	public PlayerToolBar(HandballModel model) {
		super("Player Management");
		this.model = model;
		model.addListener(this);
		group = new ButtonGroup();

		placeOffender = new JToggleButton(new PlaceOffenderAction(model));
		group.add(placeOffender);
		add(placeOffender);
		addSeparator();

		placeDefender = new JToggleButton(new PlaceDefenderAction(model));
		group.add(placeDefender);
		add(placeDefender);
		addSeparator();

		placeBall = new JToggleButton(new PlaceBallAction(model));
		group.add(placeBall);
		add(placeBall);
		addSeparator();

		edit = new JToggleButton(new EditModusAction(model));
		group.add(edit);
		add(edit);
		addSeparator();

		animation = new JToggleButton(new AnimationModusAction(model));
		animation.getAction().setEnabled(false);
		group.add(animation);
		add(animation);
	}

}
