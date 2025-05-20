package bierse.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import bierse.model.Drink;
import bierse.model.Model;
import bierse.view.IDrinkSellView;

public class DrinkSellController implements KeyListener {

	Model model;
	IDrinkSellView view;
	
	public DrinkSellController(Model m, IDrinkSellView v) {
		model = m;
		view = v;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(model.getUsedKeys().contains(e.getKeyCode())) {
			for(Drink d: model.getLstUsedDrink()) {
				try {
					if(d.getKey() == e.getKeyCode()) {
						if(view.getDirectPrice() != 0) {
							model.getLog().debug("Direct price set: " + d.getName() + " (" + view.getDirectPrice() + ")");
							d.setDirectPrice(view.getDirectPrice());
						} else {
							model.getLog().debug("Drink sold: " + d.getName() + " (" + view.getAmount() + ")");
							d.sell(view.getAmount());
						}
					}
				} catch (NumberFormatException nfe) {
					model.getLog().error(this, nfe);
				}
			}
			view.setAmount(1);
			view.setDirectPrice(0);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
