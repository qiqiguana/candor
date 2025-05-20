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
 */
public class Drink {

    public static final String DRINKS_FOLDER = Model.CONFIG_FOLDER + "drinks/";

    public static final String NEW_DRINK_NAME = "Neues Getr�nk";

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
    private boolean used = true;

    /**
     * Key used to indicate the sell of a drink
     */
    private int key = KeyEvent.VK_F1;

    /**
     * Show if the price is lower (-1), equal(0) or higher(1) than during the last iteration
     */
    private int trend = 0;

    /**
     * Keep the price and do no new calculation
     */
    private boolean keepPrice = false;

    /**
     * Set the new price directly without calculating
     */
    private int directPrice = 0;

    /**
     * Model that uses the drink
     */
    Model model;

    /**
     * *******************************************************************************************************************************************
     *  Constructor
     * *******************************************************************************************************************************************
     */
    public Drink(String name, Model model) {
    }

    public Drink(String name, Model model, int startPrice) {
    }

    /**
     * Sell the amount of drinks
     *
     * @param amount Amount of drinks sold
     */
    public synchronized void sell(int amount);

    public void setDirectPrice(int directPrice);

    /**
     * Recalculate the current price for this drink
     */
    public int recalculate();

    /**
     * Save the drink description to a XML file
     */
    public File save();

    /**
     * Load the description from a XML file
     */
    public void load();

    public String toString();

    public static String getDataHeaderString();

    public String getDataString();

    public int getMinPrice();

    public void setMinPrice(int minPrice);

    public int getMaxPrice();

    public void setMaxPrice(int maxPrice);

    public int getCurrentPrice();

    public void setCurrentPrice(int currentPrice);

    public int getLastSold();

    public void setLastSold(int lastSold);

    public int getTotalSold();

    public void setTotalSold(int totalSold);

    public float getAverageAmount();

    public void setAverageAmount(float averageAmount);

    public float getAveragePrice();

    public void setAveragePrice(float averagePrice);

    public float getAverageSoldPrice();

    public void setAverageSoldPrice(float averageSoldPrice);

    public int getMaxStep();

    public void setMaxStep(int maxStep);

    public int getTargetAmount();

    public void setTargetAmount(int targetAmount);

    public int getDeltaAmount();

    public void setDeltaAmount(int deltaAmount);

    public String getName();

    public void setName(String name);

    public int getStartPrice();

    public void setStartPrice(int startPrice);

    public boolean isUsed();

    public void setUsed(boolean used);

    public int getKey();

    public void setKey(int key);

    public int getTrend();

    public boolean isKeepPrice();

    public void setKeepPrice(boolean keepPrice);

    public int getDirectPrice();

    @Override
    public boolean equals(Object obj);
}
