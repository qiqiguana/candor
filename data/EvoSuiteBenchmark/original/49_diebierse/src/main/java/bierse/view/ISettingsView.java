package bierse.view;

public interface ISettingsView {
	
	public static final String SETTINGS_ACTION_OK = "SETTINGS_ACTION_OK";
	public static final String SETTINGS_ACTION_CANCEL = "SETTINGS_ACTION_CANCEL";
	public static final String SETTINGS_ACTION_DRINK_SELECTED = "SETTINGS_ACTION_DRINK_SELECTED";
	public static final String SETTINGS_ACTION_CHOOSE_BACKGROUND = "SETTINGS_ACTION_CHOOSE_BACKGROUND";
	
	public int getTimeInterval();
	public String getCurrency();
	public int getPriceSteps();
	public String getBackgroundPicturePath();
	public void setBackgronudPicturePath(String path);
	public boolean isStandardLogic();
	public String getRunningMessage();
	public int getRunningMessageSpeed();
	public void close();
}
