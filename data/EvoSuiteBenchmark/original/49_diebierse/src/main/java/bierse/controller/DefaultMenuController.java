package bierse.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bierse.model.Model;
import bierse.view.DefaultInfoView;
import bierse.view.DefaultSettingsView;
import bierse.view.IDrinkSellView;

public class DefaultMenuController implements ActionListener {

	Model model;
	IDrinkSellView view;
	
	public DefaultMenuController(Model model, IDrinkSellView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(IDrinkSellView.MI_DISPLAY_INFO_VIEW)) {
			showNewInfoView();
		} else if(e.getActionCommand().equals(IDrinkSellView.MI_DISPLAY_SETTINGS_VIEW)) {
			showSettingsView();
		}
	}
	
	private void showNewInfoView() {
		new DefaultInfoView(model);
	}

	private void showSettingsView() {
		DefaultSettingsView dsv = new DefaultSettingsView(model);
		dsv.setSettingsController(new DefaultSettingsController(model, dsv, dsv));
	}
}
