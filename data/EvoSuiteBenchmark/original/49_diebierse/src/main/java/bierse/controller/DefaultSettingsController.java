package bierse.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import bierse.model.Drink;
import bierse.model.Model;
import bierse.view.IDrinkSettingsView;
import bierse.view.ISettingsView;

public class DefaultSettingsController implements ActionListener {

	private Model model;
	private ISettingsView view;
	private IDrinkSettingsView dsView;
	
	
	public DefaultSettingsController(Model model, ISettingsView view, IDrinkSettingsView drinkSettingsView) {
		this.model = model;
		this.view = view;
		this.dsView = drinkSettingsView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(ISettingsView.SETTINGS_ACTION_OK)) {
			model.getSettings().setTimeInterval(view.getTimeInterval());
			model.getSettings().setPriceSteps(view.getPriceSteps());
			model.getSettings().setCurrency(view.getCurrency());
			model.getSettings().setStandardLogic(view.isStandardLogic());
			model.getSettings().setBackgroundImagePath(view.getBackgroundPicturePath());
			model.getSettings().setMessage(view.getRunningMessage());
			model.getSettings().setMessageSpeed(view.getRunningMessageSpeed());
			
			takeOverDrinkSettings();

			model.getSettings().save();
			for(Drink drinkToSave : model.getLstDrinks()) {
				drinkToSave.save();
			}
			model.informModelChangedListeners(Model.EVENT_SETTINGS_CHANGED);
			model.informModelChangedListeners(Model.EVENT_DRINK_LIST_CHANGED);
			view.close();
		} else if(e.getActionCommand().equals(ISettingsView.SETTINGS_ACTION_CANCEL)) {
			view.close();
		} else if(e.getActionCommand().equals(ISettingsView.SETTINGS_ACTION_DRINK_SELECTED)) {
			JComboBox cb = (JComboBox)e.getSource();
			takeOverDrinkSettings();
			dsView.setDrink((Drink)cb.getSelectedItem());
		} else if(e.getActionCommand().equals(ISettingsView.SETTINGS_ACTION_CHOOSE_BACKGROUND)) {
			File datei;
		    int  result;

		    final JFileChooser fc = new JFileChooser(".");
		    result=fc.showDialog(new JFrame(), "OK"); 

		    if(result==0)
		    {
		      datei=fc.getSelectedFile();
		      view.setBackgronudPicturePath(datei.getAbsolutePath());
		    }
		}
	}
	
	private void takeOverDrinkSettings() {
		Drink drink = dsView.getDrink();
		drink.setName(dsView.getDrinkName());
		drink.setUsed(dsView.isUsed());
		if(dsView.getKey() != -1) {
			drink.setKey(dsView.getKey());
			model.changeDrinkKey(drink);
		}
		if(dsView.getMinPrice() >= 0) {
			drink.setMinPrice(dsView.getMinPrice());
		}
		if(dsView.getMaxPrice() >= 0) {
			drink.setMaxPrice(dsView.getMaxPrice());
		}
		if(dsView.getStartPrice() >= 0) {
			drink.setStartPrice(dsView.getStartPrice());
		}
		if(dsView.getTargetAmount() >= 0) {
			drink.setTargetAmount(dsView.getTargetAmount());
		}
		if(dsView.getMaxStep() >= 0) {
			drink.setMaxStep(dsView.getMaxStep());
		}
		if(dsView.getDeltaAmount() >= 0) {
			drink.setDeltaAmount(dsView.getDeltaAmount());
		}
	}

}
