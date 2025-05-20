package bierse.controller;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import bierse.model.Drink;
import bierse.model.Model;
import bierse.view.IDrinkSellView;

public class DefaultTableModelController implements TableModelListener {

	private Model model;
	private IDrinkSellView view;
	
	public DefaultTableModelController(Model model, IDrinkSellView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		if(row >= 0) {
			TableModel tm = (TableModel)e.getSource();
			String name = (String)tm.getValueAt(row, IDrinkSellView.TABLE_INDEX_NAME);
			for(Drink d : model.getLstUsedDrink()) {
				if(d.getName().equals(name)) {
					d.setKeepPrice((Boolean)tm.getValueAt(row, IDrinkSellView.TABLE_INDEX_KEEP_PRICE));
				}
			}
		}
	}

}
