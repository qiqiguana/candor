package visu.handball.moves.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import visu.handball.moves.Main;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.HandballModelListener;
import visu.handball.moves.model.HandballModel.State;


public class PlaceOffenderAction extends AbstractAction implements
		HandballModelListener {

	private HandballModel model;

	public PlaceOffenderAction(HandballModel model) {
		super("Angreifer setzen", Main.createImageIcon("images/offender.gif",
				"Add"));
		putValue(AbstractAction.SHORT_DESCRIPTION,
				"Setzen und Loeschen von Angreifern");
		this.model = model;
		model.addListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		model.setState(State.PLACE_OFFENDERS);
	}

	public void modelChanged() {
		switch (model.getState()) {
		case ANIMATION_RUNNING:
		case EDIT_EVENT:
			setEnabled(false);
			break;
		default:
			setEnabled(true);
			break;
		}
	}

}
