package bierse.view;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import bierse.view.ISettingsView;
import bierse.model.Drink;
import bierse.model.Model;

public class DefaultSettingsView extends JFrame  implements ISettingsView, IDrinkSettingsView {

	private static final long serialVersionUID = -1160952914735227669L;

	JButton okButton;
	JButton cancelButton;
	
	JTextField timeIntervalTextField;
	JTextField currencyTextField;
	JRadioButton priceStep5RadioButton;
	JRadioButton priceStep10RadioButton;
	JRadioButton priceStep20RadioButton;
	JRadioButton priceStep50RadioButton;
	JCheckBox standardLogicCheckBox;
	JTextField backgroundFilePathtextField;
	JButton fileChooserButton;
	JTextField runningMessageTextField;
	JSlider runningMessageSpeedSlider;
	
	
	JPanel drinkSettingsPanel;
	
	JComboBox drinkComboBox;
	JTextField drinkNameTextField;
	JComboBox drinkKeyComboBox;
	JTextField drinkStartPriceTextField;
	JTextField drinkMinPriceTextField;
	JTextField drinkMaxPriceTextField;
	JTextField drinkMaxStepTextField;
	JTextField drinkTargetAmountTextField;
	JTextField drinkDeltaAmountTextField;
	JCheckBox drinkIsUsedCheckBox;
	
	private Model model;
	private Drink drink;

	public DefaultSettingsView(Model model) {
		super("Einstellungen");
		this.model = model;
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(tabbedPane, BorderLayout.CENTER);
		getContentPane().add(mainPanel);
		
		JPanel mainSettingsPanel = new JPanel();
		mainSettingsPanel.setLayout(new GridLayout(1, 1));
		JScrollPane settingsScrollPane = new JScrollPane(mainSettingsPanel);
		mainSettingsPanel.add(createSettingsPanel());
		
		drinkSettingsPanel = new JPanel();
		drinkSettingsPanel.setLayout(new BorderLayout());
		JScrollPane drinkSettingsScrollPane = new JScrollPane(drinkSettingsPanel);
		Drink newDrink = new Drink(Drink.NEW_DRINK_NAME, model);
		if (!model.getLstDrinks().contains(newDrink)) {
			model.getLstDrinks().add(newDrink);
		}
		
		if(model.getLstDrinks().size() > 0) {
			drink = model.getLstDrinks().get(0);
		} else {
			drink = newDrink;
		}
		
		drinkComboBox = new JComboBox(model.getLstDrinks());
		drinkComboBox.setActionCommand(ISettingsView.SETTINGS_ACTION_DRINK_SELECTED);
		drinkSettingsPanel.add(drinkComboBox, BorderLayout.NORTH);
		drinkSettingsPanel.add(createDrinkSettingsPanel(drink), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		okButton = new JButton("OK");
		okButton.setMnemonic(KeyEvent.VK_O);
		okButton.setActionCommand(ISettingsView.SETTINGS_ACTION_OK);
		buttonPanel.add(okButton);
		cancelButton = new JButton("Abbrechen");
		cancelButton.setMnemonic(KeyEvent.VK_A);
		cancelButton.setActionCommand(ISettingsView.SETTINGS_ACTION_CANCEL);
		buttonPanel.add(cancelButton);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	// Add settings panel to tabbed pane
		tabbedPane.addTab("Einstellungen", settingsScrollPane);
		tabbedPane.addTab("Drink Einstellungen", drinkSettingsScrollPane);
		
		pack();
		setVisible(true);
	}
	
	private JPanel createDrinkSettingsPanel(Drink drink) {
		JPanel drinkSettings = new JPanel();
		drinkSettings.setLayout(new GridLayout(0, 2));
		JLabel drinkNameLabel = new JLabel("Name");
		drinkSettings.add(drinkNameLabel);
		drinkNameTextField = new JTextField(drink.getName());
		drinkSettings.add(drinkNameTextField);
		JLabel drinkKeyLabel = new JLabel("Taste");
		drinkSettings.add(drinkKeyLabel);
		drinkKeyComboBox = new JComboBox(new KeyMapComboBoxModel());
		drinkKeyComboBox.setSelectedItem(KeyMapComboBoxModel.getMyKeyMapForKeyCode(drink.getKey()));
		drinkSettings.add(drinkKeyComboBox);
		JLabel drinkStartPriceLabel = new JLabel("Start Preis");
		drinkSettings.add(drinkStartPriceLabel);
		drinkStartPriceTextField = new JTextField(String.valueOf(drink.getStartPrice()));
		drinkSettings.add(drinkStartPriceTextField);
		JLabel drinkMinPriceLabel = new JLabel("Minimum Preis");
		drinkSettings.add(drinkMinPriceLabel);
		drinkMinPriceTextField = new JTextField(String.valueOf(drink.getMinPrice()));
		drinkSettings.add(drinkMinPriceTextField);
		JLabel drinkMaxPriceLabel = new JLabel("Maximum Preis");
		drinkSettings.add(drinkMaxPriceLabel);
		drinkMaxPriceTextField = new JTextField(String.valueOf(drink.getMaxPrice()));
		drinkSettings.add(drinkMaxPriceTextField);
		JLabel drinkMaxStep = new JLabel("Maximum Preisschritt");
		drinkSettings.add(drinkMaxStep);
		drinkMaxStepTextField = new JTextField(String.valueOf(drink.getMaxStep()));
		drinkSettings.add(drinkMaxStepTextField);
		JLabel drinkTargetAmountLabel = new JLabel("Verkauf-Soll");
		drinkSettings.add(drinkTargetAmountLabel);
		drinkTargetAmountTextField = new JTextField(String.valueOf(drink.getTargetAmount()));
		drinkSettings.add(drinkTargetAmountTextField);
		JLabel drinkDeltaAmountLabel = new JLabel("VerkaufDifferenz");
		drinkSettings.add(drinkDeltaAmountLabel);
		drinkDeltaAmountTextField = new JTextField(String.valueOf(drink.getDeltaAmount()));
		drinkSettings.add(drinkDeltaAmountTextField);
		drinkIsUsedCheckBox = new JCheckBox("Aktiv");
		drinkIsUsedCheckBox.setSelected(drink.isUsed());
		drinkSettings.add(drinkIsUsedCheckBox);
		
		return drinkSettings;
	}
	
	private JPanel createSettingsPanel() {
	// Create settings panel
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new BorderLayout(10, 10));
		
		
	// Create panel containing the labels
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(0,1));
		
	// Create panel containing the values
		JPanel valuePanel = new JPanel();
		valuePanel.setLayout(new GridLayout(0,1));
		
	// Setting for time interval 
		JLabel timeIntervalLabel = new JLabel("Zeitinterval");
		labelPanel.add(timeIntervalLabel);
		timeIntervalTextField = new JTextField(String.valueOf(model.getSettings().getTimeInterval()));
		valuePanel.add(timeIntervalTextField);
		
	// Setting for price steps
		JLabel priceStepsLabel = new JLabel("Preisschritte");
		labelPanel.add(priceStepsLabel);
	// Create panel for price steps radio buttons
		JPanel buttonGroupPanel = new JPanel();
		buttonGroupPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		ButtonGroup priceStepsButtonGroup = new ButtonGroup();
		priceStep5RadioButton = new JRadioButton("5");
		priceStepsButtonGroup.add(priceStep5RadioButton);
		buttonGroupPanel.add(priceStep5RadioButton);
		priceStep10RadioButton = new JRadioButton("10");
		buttonGroupPanel.add(priceStep10RadioButton);
		priceStepsButtonGroup.add(priceStep10RadioButton);
		priceStep20RadioButton = new JRadioButton("20");
		buttonGroupPanel.add(priceStep20RadioButton);
		priceStepsButtonGroup.add(priceStep20RadioButton);
		priceStep50RadioButton = new JRadioButton("50");
		buttonGroupPanel.add(priceStep50RadioButton);
		priceStepsButtonGroup.add(priceStep50RadioButton);
		if(model.getSettings().getPriceSteps() == 5) {
			priceStep5RadioButton.setSelected(true);
		} else if(model.getSettings().getPriceSteps() == 10) {
			priceStep10RadioButton.setSelected(true);
		} else if(model.getSettings().getPriceSteps() == 20) {
			priceStep20RadioButton.setSelected(true);
		} else if(model.getSettings().getPriceSteps() == 50) {
			priceStep50RadioButton.setSelected(true);
		}
		valuePanel.add(buttonGroupPanel);

	// Setting for Currency
		JLabel currencyLabel = new JLabel("Währung");
		labelPanel.add(currencyLabel);
		currencyTextField = new JTextField(model.getSettings().getCurrency());
		valuePanel.add(currencyTextField);
		
	// Setting for background image
		JLabel backgroundFileLabel = new JLabel("Hintergrundbild");
		labelPanel.add(backgroundFileLabel);
		JPanel backgroundImageChooserPanel = new JPanel();
		backgroundImageChooserPanel.setLayout(new BorderLayout());
		backgroundFilePathtextField = new JTextField(model.getSettings().getBackgroundImagePath());
		backgroundImageChooserPanel.add(backgroundFilePathtextField, BorderLayout.CENTER);
		fileChooserButton = new JButton("...");
		fileChooserButton.setActionCommand(ISettingsView.SETTINGS_ACTION_CHOOSE_BACKGROUND);
		backgroundImageChooserPanel.add(fileChooserButton, BorderLayout.EAST);
		valuePanel.add(backgroundImageChooserPanel);
		
	// Setting for standard logic
		JLabel standardLogicLabel = new JLabel();
		labelPanel.add(standardLogicLabel);
		standardLogicCheckBox = new JCheckBox("Standard Logik");
		standardLogicCheckBox.setToolTipText("Standard Logik: Mehr verkauft -> Preis steigt");
		standardLogicCheckBox.setSelected(model.getSettings().isStandardLogic());
		valuePanel.add(standardLogicCheckBox);
		
	// Setting for running message text
		JLabel runningMessageTextLabel = new JLabel("Lauftext");
		labelPanel.add(runningMessageTextLabel);
		runningMessageTextField = new JTextField(model.getSettings().getMessage());
		valuePanel.add(runningMessageTextField);
		
	// Setting for running message speed
		JLabel runningMessageSpeedLabel = new JLabel("Lauftext Geschwindigkeit");
		labelPanel.add(runningMessageSpeedLabel);
		runningMessageSpeedSlider = new JSlider(1, 70, model.getSettings().getMessageSpeed());
		runningMessageSpeedSlider.setMinorTickSpacing(5);
		runningMessageSpeedSlider.setInverted(true);
		runningMessageSpeedSlider.setToolTipText("Links langsamer, Rechts schneller");
		valuePanel.add(runningMessageSpeedSlider);
		
		
	// Add label and value panel to main panel
		settingsPanel.add(labelPanel, BorderLayout.WEST);
		settingsPanel.add(valuePanel, BorderLayout.CENTER);
		
	// Return main panel
		return settingsPanel;
	}
	
	public void setSettingsController(ActionListener al) {
		okButton.addActionListener(al);
		cancelButton.addActionListener(al);
		drinkComboBox.addActionListener(al);
		fileChooserButton.addActionListener(al);
	}

	@Override
	public void close() {
		dispose();
	}

	@Override
	public int getTimeInterval() {
		try {
			return Integer.valueOf(timeIntervalTextField.getText()).intValue();
		} catch(NumberFormatException nfe) {
			model.getLog().error(this, nfe);
		}
		return -1;
	}

	@Override
	public String getCurrency() {
		return currencyTextField.getText();
	}

	@Override
	public int getPriceSteps() {
		if(priceStep5RadioButton.isSelected()) {
			return 5;
		} else if(priceStep10RadioButton.isSelected()) {
			return 10;
		} else if(priceStep20RadioButton.isSelected()) {
			return 20;
		} else if(priceStep50RadioButton.isSelected()) {
			return 50;
		}
		return 10;
	}

	

	@Override
	public String getDrinkName() {
		return drinkNameTextField.getText();
	}
	
	@Override
	public int getKey() {
		try {
			return ((MyKeyMap)drinkKeyComboBox.getSelectedItem()).getCode();
		} catch(NumberFormatException nfe) {
			model.getLog().error(this, nfe);
		}
		return -1;
	}
	
	@Override
	public int getStartPrice() {
		try {
			return Integer.valueOf(drinkStartPriceTextField.getText());
		} catch (NumberFormatException nfe) {
			model.getLog().error(this, nfe);
		}
		return -1;
	}
	
	@Override
	public int getMinPrice() {
		try {
			return Integer.valueOf(drinkMinPriceTextField.getText());
		} catch (NumberFormatException nfe) {
			model.getLog().error(this, nfe);
		}
		return -1;
	}
	
	@Override
	public int getMaxPrice() {
		try {
			return Integer.valueOf(drinkMaxPriceTextField.getText());
		} catch (NumberFormatException nfe) {
			model.getLog().error(this, nfe);
		}
		return -1;
	}
	
	@Override
	public int getMaxStep() {
		try {
			return Integer.valueOf(drinkMaxStepTextField.getText());
		} catch (NumberFormatException nfe) {
			model.getLog().error(this, nfe);
		}
		return -1;
	}
	
	@Override
	public int getTargetAmount() {
		try {
			return Integer.valueOf(drinkTargetAmountTextField.getText());
		} catch (NumberFormatException nfe) {
			model.getLog().error(this, nfe);
		}
		return -1;
	}
	
	@Override
	public int getDeltaAmount() {
		try {
			return Integer.valueOf(drinkDeltaAmountTextField.getText());
		} catch (NumberFormatException nfe) {
			model.getLog().error(this, nfe);
		}
		return -1;
	}
	
	@Override
	public boolean isUsed() {
		return drinkIsUsedCheckBox.isSelected();
	}

	public Drink getDrink() {
		return drink;
	}

	@Override
	public boolean isStandardLogic() {
		return standardLogicCheckBox.isSelected();
	}
	
	@Override
	public String getBackgroundPicturePath() {
		return backgroundFilePathtextField.getText();
	}
	
	@Override
	public void setBackgronudPicturePath(String path) {
		backgroundFilePathtextField.setText(path);
	}	

	@Override
	public void setDrink(Drink drink) {
		this.drink = drink;
		drinkSettingsPanel.removeAll();
		drinkSettingsPanel.add(drinkComboBox, BorderLayout.NORTH);
		drinkSettingsPanel.add(createDrinkSettingsPanel(drink), BorderLayout.CENTER);
		validate();
		repaint();
	}

	@Override
	public String getRunningMessage() {
		return runningMessageTextField.getText();
	}

	@Override
	public int getRunningMessageSpeed() {
		return runningMessageSpeedSlider.getValue();
	}
}
