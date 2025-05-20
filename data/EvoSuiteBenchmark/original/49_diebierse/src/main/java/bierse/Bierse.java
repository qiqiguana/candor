package bierse;

import bierse.controller.DefaultMenuController;
import bierse.controller.DefaultTableModelController;
import bierse.controller.DrinkSellController;
import bierse.model.Model;
import bierse.view.DefaultDrinkSellView;
import bierse.view.DefaultInfoView;

public class Bierse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Model m = new Model();
		
		DefaultDrinkSellView view = new DefaultDrinkSellView("Die Bierse", m);
		DrinkSellController dsc = new DrinkSellController(m, view);
		view.setDrinkSellController(dsc);
		DefaultMenuController dmc = new DefaultMenuController(m, view);
		view.setMenuController(dmc);
		DefaultTableModelController dtml = new DefaultTableModelController(m, view);
		view.setTableModelController(dtml);
		// Open a new drink info view
		new DefaultInfoView(m);
		
		m.start();
	}
}
