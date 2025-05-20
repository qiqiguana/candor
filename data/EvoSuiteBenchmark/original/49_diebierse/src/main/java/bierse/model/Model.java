/**
 * 
 */
package bierse.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jdom.output.XMLOutputter;

import bierse.controller.TriggerThread;
import bierse.view.IModelChangedListener;

/**
 * @author Rainer Friesen
 *
 */
public class Model {
/*********************************************************************************************
 * Constants
 *********************************************************************************************/
	public static final String CONFIG_FOLDER = "./conf/";
	public static final String DATA_FOLDER = "./data/";
	public static final String DELIMITER = ";";
	
	public static final int EVENT_TIME_LEFT_CHANGED = 1;
	public static final int EVENT_RECALCULATED = 2;
	public static final int EVENT_NEW_VALIDATIONS = 4;
	public static final int EVENT_DRINK_LIST_CHANGED = 8;
	public static final int EVENT_DRINK_SOLD = 16;
	public static final int EVENT_SETTINGS_CHANGED = 32;

/*********************************************************************************************
 * Attributes
 *********************************************************************************************/
	private TriggerThread triggerThread;
	private Vector<Drink> lstDrinks = new Vector<Drink>();
	private Settings settings;
	private boolean running;
	private int iteration = 1;
	private List<IModelChangedListener> lstModelChangedListener = new Vector<IModelChangedListener>();
	private List<String> lstValidations;
	private List<Integer> lstUsedKeys = new Vector<Integer>();
	private Logger log;
	/**
	 * Time left till the next calculation of prices is started
	 */
	private int timeLeft;
	
/*********************************************************************************************
 * Constructors
 *********************************************************************************************/
	public Model() {
		// Configure and create the logger
		BasicConfigurator.configure();
		log = Logger.getLogger(this.getClass());
		log.setLevel(Level.WARN);
		
		settings = new Settings(this);
		settings.load();
		
		loadAllDrinks();
		
		triggerThread = new TriggerThread("TriggerThread", this);
		
		log.debug("Model created: " + this);
	}

/*********************************************************************************************
 * Methods
 *********************************************************************************************/
	
	/**
	 * Recalculate the prices of all drinks
	 */
	public synchronized void recalculate() {
		// Go over all drinks and call the recalculation of them
		log.debug("Recalculate all drinks");
		for(Drink d:getLstUsedDrink()) {
			d.recalculate();
		}
		iteration++;
		informModelChangedListeners(EVENT_RECALCULATED);
	}
	
	/**
	 * Informs all model change listeners when the model has changed
	 */
	public void informModelChangedListeners(int eventType) {
		for(IModelChangedListener mcl : lstModelChangedListener) {
			mcl.fireModelChanged(eventType);
		}
	}
	
	/**
	 * Register a new model change listener at the model
	 * 
	 * @param listener
	 */
	public void registerModelChangedListener(IModelChangedListener listener) {
		lstModelChangedListener.add(listener);
	}
	
	/**
	 * Start the simulation
	 */
	public void start() {
		log.debug("START");
		running = true;
		triggerThread.start();
	}
	
	/**
	 * Stop the simulation
	 */
	public void stop() {
		log.debug("STOP");
		running = false;
		triggerThread.interrupt();
	}
	
	/**
	 * Load all drinks from the file system and put them in the matching list.
	 */
	public void loadAllDrinks() {
		File dir = new File(Drink.DRINKS_FOLDER);
		if(dir.exists()) {
			File[] files = dir.listFiles();
			for(File f : files) {
				Drink d = new Drink(f.getName().substring(0, f.getName().lastIndexOf(".")), this);
				d.load();
				lstDrinks.add(d);
			}
		}
		validate();
		informModelChangedListeners(EVENT_DRINK_LIST_CHANGED);
	}
	
	/**
	 * Enable a drink to be used by the simulation
	 * 
	 * @param d The Drink to enable
	 */
	public void enableDrink(Drink d) {
		d.setUsed(true);
		validate();
		informModelChangedListeners(EVENT_DRINK_LIST_CHANGED);
	}
	
	/**
	 * Disable a drink to be used by the simulation
	 * 
	 * @param d The drink to disable
	 */
	public void disableDrink(Drink d) {
		d.setUsed(false);
		validate();
		informModelChangedListeners(EVENT_DRINK_LIST_CHANGED);
	}
	
	public void changeDrinkKey(Drink d) {
		validate();
		informModelChangedListeners(EVENT_DRINK_LIST_CHANGED);
	}
	
	/**
	 * Validate the model and check if everything is all right
	 */
	public void validate() {
		lstValidations = new Vector<String>();
		validateDrinks();
		if(lstValidations.size() > 0) {
			informModelChangedListeners(EVENT_NEW_VALIDATIONS);
		}
	}
	
	/**
	 * Check if the drinks have no conflicts, e.g. two drinks use the same key
	 */
	public void validateDrinks() {
		lstUsedKeys = new Vector<Integer>();
		for(Drink d : getLstUsedDrink()) {
			if(lstUsedKeys.contains(d.getKey())) {
				addValidation("Die Taste: " + d.getKey() + "wird mehrfach verwendet!");
			}
			lstUsedKeys.add(d.getKey());
		}
	}
	
	public void beforeClose() {
		// Save last data
		// Save JDOM document to file system
		File folder = new File(DATA_FOLDER);
		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		File file = new File(DATA_FOLDER + df.format(new Date()) + ".csv");
		try {
			if(!folder.exists()) {
				folder.mkdirs();
			}
			getLog().debug("Save last data before close");
			if(file.exists()) {
				file.delete();
			}
			file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(Drink.getDataHeaderString());
			bw.write(System.getProperty("line.separator"));
			for(Drink d:getLstDrinks()) {
				bw.write(d.getDataString());
				bw.write(System.getProperty("line.separator"));
			}
			bw.flush();
			bw.close();
		} catch(Exception e) {
			getLog().error(this, e);
		}
	}
	
/**********************************************************************************************
 * Getters and Setters
 *********************************************************************************************/

	public List<Drink> getLstAvailableDrink() {
		Vector<Drink> result = new Vector();
		for(Drink drink:lstDrinks) {
			if(!drink.isUsed()) {
				result.add(drink);
			}
		}
		return result;
	}
	
	public Vector<Drink> getLstUsedDrink() {
		Vector<Drink> result = new Vector();
		for(Drink drink:lstDrinks) {
			if(drink.isUsed()) {
				result.add(drink);
			}
		}
		return result;
	}
	
	public Vector<Drink> getLstDrinks() {
		return lstDrinks;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Logger getLog() {
		return log;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
		informModelChangedListeners(EVENT_TIME_LEFT_CHANGED);
	}

	public List<IModelChangedListener> getLstModelChangeListener() {
		return lstModelChangedListener;
	}

	public void setLstModelChangeListener(
			List<IModelChangedListener> lstModelChangeListener) {
		this.lstModelChangedListener = lstModelChangeListener;
	}

	public List<String> getLstValidations() {
		return lstValidations;
	}

	public void setLstValidations(List<String> lstValidations) {
		this.lstValidations = lstValidations;
	}
	
	public void addValidation(String validationMessage) {
		lstValidations.add(validationMessage);
	}
	
	public List<Integer> getUsedKeys() {
		return lstUsedKeys;
	}

	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}
}
