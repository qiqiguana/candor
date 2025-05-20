package bierse.view;

import bierse.model.Drink;

public interface IDrinkSettingsView {
	public String getDrinkName();
	public int getMinPrice();
	public int getMaxPrice();
	public int getStartPrice();
	public int getTargetAmount();
	public int getMaxStep();
	public int getDeltaAmount();
	public boolean isUsed();
	public int getKey();
	public Drink getDrink();
	public void setDrink(Drink drink);
}
