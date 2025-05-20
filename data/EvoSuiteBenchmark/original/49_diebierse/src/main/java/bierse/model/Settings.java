package bierse.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;


public class Settings {
	
	private static final String DOM_SETTINGS_ROOT = "settings";
	private static final String DOM_TIME_INTERVAL = "timeInterval";
	private static final String DOM_PRICE_STEPS = "priceSteps";
	private static final String DOM_CURRENCY = "currency";
	private static final String DOM_BACKGROUND_IMG = "backgound";
	private static final String DOM_STANDARD_LOGIC = "standardLogic";
	private static final String DOM_MESSAGE = "message";
	private static final String DOM_MESSAGE_SPEED = "messageSpeed";
	
	private static final String DOM_YES = "y";
	private static final String DOM_NO = "n";
	
	
	public static final String SETTINGS_FOLDER = Model.CONFIG_FOLDER + "settings/";

	/**
	 * Time interval(seconds) in which the current prices are recalculated.
	 */
	private int timeInterval = 10;
	/**
	 * The steps that are available for the prices, e.g. 10, 20 or 50 cents
	 */
	private int priceSteps = 10;
	/**
	 * The currency symbol used
	 */
	private java.lang.String currency = "€";

	private boolean standardLogic = true;
	
	private String backgroundImagePath = null;
	
	private String message = "Das ist eine Standard-Nachrichtfür das Laufband!";
	
	private int messageSpeed = 10;

	/**
	 * Model that uses these settings
	 */
	private Model model;
	
	public Settings(Model m) {
		model = m;
		
		File f = new File(getClass().getResource("/img/background.jpg").getPath());
		backgroundImagePath = f.getAbsolutePath();
		backgroundImagePath = backgroundImagePath.replace("%20", " ");
	}

	/**
	 * Save the settings
	 */
	public File save() {
		// Build JDOM document representing the settings
		Element settingsRoot = new Element(DOM_SETTINGS_ROOT);
		Element timeIntervalElem = new Element(DOM_TIME_INTERVAL);
		timeIntervalElem.setText(String.valueOf(timeInterval));
		settingsRoot.addContent(timeIntervalElem);
		Element priceStepsElem = new Element(DOM_PRICE_STEPS);
		priceStepsElem.setText(String.valueOf(priceSteps));
		settingsRoot.addContent(priceStepsElem);
		Element currencyElem = new Element(DOM_CURRENCY);
		currencyElem.setText(currency);
		settingsRoot.addContent(currencyElem);
		Element backgroundElem = new Element(DOM_BACKGROUND_IMG);
		backgroundElem.addContent(backgroundImagePath);
		settingsRoot.addContent(backgroundElem);
		Element standardLogicElem = new Element(DOM_STANDARD_LOGIC);
		if(standardLogic) {
			standardLogicElem.addContent(DOM_YES);
		} else {
			standardLogicElem.addContent(DOM_NO);
		}
		settingsRoot.addContent(standardLogicElem);
		
		Element messageElem = new Element(DOM_MESSAGE);
		messageElem.addContent(message);
		settingsRoot.addContent(messageElem);
		
		Element messageSpeedElem = new Element(DOM_MESSAGE_SPEED);
		messageSpeedElem.addContent(new Integer(messageSpeed).toString());
		settingsRoot.addContent(messageSpeedElem);
		
		Document settingsDoc = new Document(settingsRoot);
		
		// Save JDOM document to file system
		File file = new File(SETTINGS_FOLDER + "settings.xml");
		try {
			
			File folder = new File(SETTINGS_FOLDER);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			if(file.exists()) {
				file.delete();
			}
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			XMLOutputter serializer = new XMLOutputter();
			serializer.output(settingsDoc, fos);
			fos.flush();
			fos.close();
		} catch(Exception e) {
			model.getLog().error(this, e);
		}
		return file;
	}
	
	/**
	 * Load the settings
	 */
	public void load() {
		try {
			File settingsFile = new File(SETTINGS_FOLDER + "settings.xml");
			if(settingsFile.exists()) {
				SAXBuilder builder = new SAXBuilder();
				Document settingsDoc = builder.build(settingsFile);
				Element settingsRoot = settingsDoc.getRootElement();
				timeInterval = Integer.valueOf(settingsRoot.getChildText(DOM_TIME_INTERVAL));
				model.setTimeLeft(timeInterval);
				priceSteps = Integer.valueOf(settingsRoot.getChildText(DOM_PRICE_STEPS));
				currency = settingsRoot.getChildText(DOM_CURRENCY);
				backgroundImagePath = settingsRoot.getChildText(DOM_BACKGROUND_IMG);
				String standardLogicValue = settingsRoot.getChildText(DOM_STANDARD_LOGIC);
				if(DOM_NO.equals(standardLogicValue)) {
					standardLogic = false;
				} else {
					standardLogic = true;
				}
				message = settingsRoot.getChildText(DOM_MESSAGE);
				setMessageSpeed(new Integer(settingsRoot.getChildText(DOM_MESSAGE_SPEED)).intValue());
			}
		} catch(Exception e1) {
			model.getLog().error(this, e1);
		}
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	public int getPriceSteps() {
		return priceSteps;
	}

	public void setPriceSteps(int priceSteps) {
		this.priceSteps = priceSteps;
	}

	public java.lang.String getCurrency() {
		return currency;
	}

	public void setCurrency(java.lang.String currency) {
		this.currency = currency;
	}

	public boolean isStandardLogic() {
		return standardLogic;
	}

	public void setStandardLogic(boolean standardLogic) {
		this.standardLogic = standardLogic;
	}

	public String getBackgroundImagePath() {
		return backgroundImagePath;
	}

	public void setBackgroundImagePath(String backgroundImagePath) {
		this.backgroundImagePath = backgroundImagePath;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMessageSpeed() {
		return messageSpeed;
	}

	public void setMessageSpeed(int messageSpeed) {
		this.messageSpeed = messageSpeed;
	}
}
