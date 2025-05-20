package bierse.view;

public interface IDrinkSellView {
	
	public static final String MI_DISPLAY_INFO_VIEW = "MI_DISPLAY_INFO_VIEW";
	public static final String MI_DISPLAY_SETTINGS_VIEW = "MI_DISPLAY_SETTINGS_VIEW";
	
	public static final int TABLE_INDEX_NAME = 0;
	public static final int TABLE_INDEX_KEEP_PRICE = 11;
	
	public int getAmount();
	public void setAmount(int amount);
	
	public int getDirectPrice();
	public void setDirectPrice(int directPrice);
}
