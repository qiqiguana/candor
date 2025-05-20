package lotus.core;

public class Mana
{
	//enum with the different color
	public enum Color { WHITE,BLUE,BLACK,RED,GREEN,COLORLESS}
	//mana pool
	public int white,blue,black,red,green,colorless;

	//ctors
	public Mana(String s)
	{
		//TODO
	}
	public Mana(int w, int u, int b, int r, int g, int c)
	{
		white = w;
		blue = u;
		black = b;
		red = r;
		green = g;
		colorless = c;
	}

	private int getColorCost(Color c)
	{
		switch (c)
		{
			case WHITE: return white;
			case BLUE: return blue;
			case BLACK: return black;
			case RED: return red;
			case GREEN: return green;
			case COLORLESS: return colorless;
			default: return 0;
		}
	}
	public boolean containsColor(Color c)
	{
		return getColorCost(c) != 0;
	}

	public boolean canPay(Mana m)
	{
		int colorlessTotal = 0;
		int diff;
		for(Color c : Color.values())
		{
			diff = this.getColorCost(c) - m.getColorCost(c);
			if(c != Color.COLORLESS && diff >= 0) return false;
			colorlessTotal += diff;
		}
		if(colorlessTotal + this.getColorCost(Color.COLORLESS) < m.getColorCost(Color.COLORLESS)) return false;
		return true;
	}
}
