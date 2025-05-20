/**
 * 
 */
package bierse.model;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import bierse.view.MyKeyMap;


/**
 * @author Rainer Friesen
 *
 */
public class Drink {
	public static final String DRINKS_FOLDER = Model.CONFIG_FOLDER + "drinks/";
	public static final String NEW_DRINK_NAME = "Neues Getränk";

	/**
	 * Name of the drink
	 */
	private String name;
	
	/**
	 * Minimum price(Cent) for what the drink is sold.
	 */
	private int minPrice = 70;
	/**
	 * Maximum price(Cent) for what the drink is sold,
	 */
	private int maxPrice = 130;
	/**
	 * Current price(Cent) of the drink.
	 */
	private int currentPrice = 0;
	/**
	 * Start price for this drink
	 */
	private int startPrice = 100;
	/**
	 * Amount of drinks sold in the last iteration
	 */
	private int lastSold = 0;
	/**
	 * The total amount of drinks sold
	 */
	private int totalSold = 0;
	/**
	 * The average amount of drinks sold in an iteration. Used to calculate the new price.
	 */
	private float averageAmount = 0;
	/**
	 * The average price in cent
	 */
	private float averagePrice = 0;
	/**
	 * The average price (Cent) for what the drink is sold
	 */
	private float averageSoldPrice = 0;
	/**
	 * The maximum difference between two prices in one iteration
	 */
	private int maxStep = 20;
	/**
	 * The target amount of drinks to keep the price stable
	 */
	private int targetAmount = 20;
	/**
	 * The delta to calculate the step width. If the targetAmount - delta is not reached 
	 * the price is decreased by the maximum step. If the targetAmount + delta is reached
	 * the price is increased by the maximum step.
	 */
	private int deltaAmount = 15;
	/**
	 * Show if the drink is used currently
	 */
	private boolean used=true;
	/**
	 * Key used to indicate the sell of a drink
	 */
	private int key = KeyEvent.VK_F1;
	/**
	 * Show if the price is lower (-1), equal(0) or higher(1) than during the last iteration
	 */
	private int trend=0;
	/**
	 * Keep the price and do no new calculation 
	 */
	private boolean keepPrice=false;
	/**
	 * Set the new price directly without calculating 
	 */
	private int directPrice=0;
	
	/**
	 * Model that uses the drink
	 */
	Model model;


	
/**********************************************************************************************************************************************
 * Constructor
 *********************************************************************************************************************************************/
	public Drink(String name, Model model) {
		this.name = name;
		this.model = model;
		load();
		currentPrice = startPrice;
	}
	
	public Drink(String name, Model model, int startPrice) {
		this(name, model);
		currentPrice = startPrice;
	}
	
/**********************************************************************************************************************************************
 * Methods
 *********************************************************************************************************************************************/	
	
	/**
	 * Sell the amount of drinks
	 * 
	 * @param amount Amount of drinks sold
	 */
	public synchronized void sell(int amount) {
		lastSold += amount;
		model.informModelChangedListeners(Model.EVENT_DRINK_SOLD);
	}
	
	public void setDirectPrice(int directPrice) {
		this.directPrice = directPrice;
		model.informModelChangedListeners(Model.EVENT_DRINK_LIST_CHANGED);
	}
	
	/**
	 * Recalculate the current price for this drink
	 */
	public int recalculate() {//Calculate new average values
		totalSold += lastSold;
		averageAmount = (float)totalSold / model.getIteration();
		averagePrice = ((averagePrice * (model.getIteration()-1)) + currentPrice) / model.getIteration();
		if(totalSold > 0) {
			averageSoldPrice = ((averageSoldPrice * (totalSold - lastSold)) + (currentPrice * lastSold)) / totalSold;
		}
		int newPrice;
		
		 if(directPrice != 0) {
				// If a direct price was set, use it
				newPrice = directPrice;
				directPrice = 0;
			} else if(keepPrice) {
				// Keep the price
				newPrice = currentPrice;
			} else {
			// Calculate the new price
			double diffSold = lastSold - targetAmount;
			double factor = diffSold / deltaAmount;
			double change = factor * maxStep;
			
			int priceSteps = model.getSettings().getPriceSteps();
			
			double rest = Math.floor(Math.abs(change % priceSteps));
			double full = Math.floor(Math.abs(change / priceSteps));
	
			int priceChange = 0;
			if(rest >= priceSteps/2) {
				priceChange = priceSteps;
			}
			priceChange += full * priceSteps;
			
			// Check that the change of the price is not more than the max step
			if(priceChange > maxStep) {
				priceChange = maxStep;
			}
			
			if(model.getSettings().isStandardLogic()) {
				if(factor >= 0) {
					newPrice = currentPrice + priceChange;
				} else {
					newPrice = currentPrice - priceChange;
				}
			} else {
				// Inverse logic
				if(factor >= 0) {
					newPrice = currentPrice - priceChange;
				} else {
					newPrice = currentPrice + priceChange;
				}
			}
		}
		// Check that the new price is not above the max price or below the min price
		if(newPrice > maxPrice) {
			newPrice = maxPrice;
		} else if(newPrice < minPrice) {
			newPrice = minPrice;
		}
		
		
		// Calculate the trend
		if(newPrice>currentPrice) {
			trend = 1;
		} else if(newPrice<currentPrice) {
			trend = -1;
		} else {
			trend = 0;
		}

		currentPrice = newPrice;

		//Debug output
		model.getLog().debug("Recalculating drink: " + name + " for iteration: " + model.getIteration());
		model.getLog().debug("Last sold: " + lastSold);
		model.getLog().debug("Total sold:" + totalSold);
		model.getLog().debug("Average sold: " + averageAmount);
		model.getLog().debug("Average price: " + averagePrice);
		model.getLog().debug("Average sold price: " + averageSoldPrice);
		model.getLog().debug("New price: " + currentPrice);
		
		// Reset values for the next iteration
		lastSold = 0;
		
		return currentPrice;
	}
	
	/**
	 * Save the drink description to a XML file
	 */
	public File save() {
		if (!NEW_DRINK_NAME.equals(name)) {
			model.getLog().debug("Save drink: " + name);
			// Build JDOM document representing the settings
			Element root = new Element("drink");
			Element minPriceElem = new Element("minPrice");
			minPriceElem.setText(String.valueOf(minPrice));
			root.addContent(minPriceElem);
			Element maxPriceElem = new Element("maxPrice");
			maxPriceElem.setText(String.valueOf(maxPrice));
			root.addContent(maxPriceElem);
			Element startPriceElem = new Element("startPrice");
			startPriceElem.setText(String.valueOf(startPrice));
			root.addContent(startPriceElem);
			Element targetAmountElem = new Element("targetAmount");
			targetAmountElem.setText(String.valueOf(targetAmount));
			root.addContent(targetAmountElem);
			Element deltaAmountElem = new Element("deltaAmount");
			deltaAmountElem.setText(String.valueOf(deltaAmount));
			root.addContent(deltaAmountElem);
			Element maxStepElem = new Element("maxStep");
			maxStepElem.setText(String.valueOf(maxStep));
			root.addContent(maxStepElem);
			Element usedElem = new Element("used");
			usedElem.setText(String.valueOf(used));
			root.addContent(usedElem);
			Element keyElem = new Element("key");
			keyElem.setText(String.valueOf(key));
			root.addContent(keyElem);
			
			Document settingsDoc = new Document(root);
			
			// Save JDOM document to file system
			File folder = new File(DRINKS_FOLDER);
			File file = new File(DRINKS_FOLDER + name + ".xml");
			try {
				if(!folder.exists()) {
					folder.mkdirs();
				}
				model.getLog().debug("File for Drink: " + file.getAbsolutePath());
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
		return null;
	}
	
	/**
	 * Load the description from a XML file
	 */
	public void load() {
		model.getLog().debug("Load drink: " + name);
		try {
			File file = new File(DRINKS_FOLDER + name + ".xml");
			model.getLog().debug("File for Drink: " + file.getAbsolutePath());
			if(file.exists()) {
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(file);
				Element root = doc.getRootElement();
				minPrice = Integer.valueOf(root.getChildText("minPrice"));
				maxPrice = Integer.valueOf(root.getChildText("maxPrice"));
				startPrice = Integer.valueOf(root.getChildText("startPrice"));
				targetAmount = Integer.valueOf(root.getChildText("targetAmount"));
				deltaAmount = Integer.valueOf(root.getChildText("deltaAmount"));
				maxStep = Integer.valueOf(root.getChildText("maxStep"));
				used = Boolean.parseBoolean(root.getChildText("used"));
				key = Integer.valueOf(root.getChildText("key"));
			}
		} catch(Exception e1) {
			model.getLog().error(this, e1);
		}
	}
	
	public String toString() {
		return name;
	}
	
	public static String getDataHeaderString() {
		return "NAME;MIN_PRICE;START_PRICE;MAX_PRICE;AVG_AMOUNT;AVG_PRICE;AVG_SOLD_PRICE;TOTAL_SOLD";
	}
	
	public String getDataString() {
		StringBuilder result = new StringBuilder();
		result.append(name);
		result.append(Model.DELIMITER);
		result.append(minPrice);
		result.append(Model.DELIMITER);
		result.append(startPrice);
		result.append(Model.DELIMITER);
		result.append(maxPrice);
		result.append(Model.DELIMITER);
		result.append(averageAmount);
		result.append(Model.DELIMITER);
		result.append(averagePrice);
		result.append(Model.DELIMITER);
		result.append(averageSoldPrice);
		result.append(Model.DELIMITER);
		result.append(totalSold);
		return result.toString();
	}
	
/**********************************************************************************************************************************************
 * Getters and Setters
 *********************************************************************************************************************************************/
	
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public int getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}
	public int getLastSold() {
		return lastSold;
	}
	public void setLastSold(int lastSold) {
		this.lastSold = lastSold;
	}
	public int getTotalSold() {
		return totalSold;
	}
	public void setTotalSold(int totalSold) {
		this.totalSold = totalSold;
	}
	public float getAverageAmount() {
		return averageAmount;
	}
	public void setAverageAmount(float averageAmount) {
		this.averageAmount = averageAmount;
	}
	public float getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(float averagePrice) {
		this.averagePrice = averagePrice;
	}
	public float getAverageSoldPrice() {
		return averageSoldPrice;
	}
	public void setAverageSoldPrice(float averageSoldPrice) {
		this.averageSoldPrice = averageSoldPrice;
	}
	public int getMaxStep() {
		return maxStep;
	}
	public void setMaxStep(int maxStep) {
		this.maxStep = maxStep;
	}
	public int getTargetAmount() {
		return targetAmount;
	}
	public void setTargetAmount(int targetAmount) {
		this.targetAmount = targetAmount;
	}
	public int getDeltaAmount() {
		return deltaAmount;
	}
	public void setDeltaAmount(int deltaAmount) {
		this.deltaAmount = deltaAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getTrend() {
		return trend;
	}

	public boolean isKeepPrice() {
		return keepPrice;
	}

	public void setKeepPrice(boolean keepPrice) {
		this.keepPrice = keepPrice;
	}

	public int getDirectPrice() {
		return directPrice;
	}

	@Override
	public boolean equals(Object obj) {
		Drink drink = (Drink) obj;
		if(name == null && drink.getName() == null) {
			return true;
		} else if (name != null && drink.getName() != null) {
			return name.equals(((Drink)obj).getName());
		} else {
			return false;
		}
	}
}
