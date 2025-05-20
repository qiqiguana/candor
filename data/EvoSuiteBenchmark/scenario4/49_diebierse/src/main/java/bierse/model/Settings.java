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
    private java.lang.String currency = "�";

    private boolean standardLogic = true;

    private String backgroundImagePath = null;

    private String message = "Das ist eine Standard-Nachrichtf�r das Laufband!";

    private int messageSpeed = 10;

    /**
     * Model that uses these settings
     */
    private Model model;

    public Settings(Model m) {
    }

    /**
     * Save the settings
     */
    public File save();

    /**
     * Load the settings
     */
    public void load();

    public int getTimeInterval();

    public void setTimeInterval(int timeInterval);

    public int getPriceSteps();

    public void setPriceSteps(int priceSteps);

    public java.lang.String getCurrency();

    public void setCurrency(java.lang.String currency);

    public boolean isStandardLogic();

    public void setStandardLogic(boolean standardLogic);

    public String getBackgroundImagePath();

    public void setBackgroundImagePath(String backgroundImagePath);

    public String getMessage();

    public void setMessage(String message);

    public int getMessageSpeed();

    public void setMessageSpeed(int messageSpeed);
}
