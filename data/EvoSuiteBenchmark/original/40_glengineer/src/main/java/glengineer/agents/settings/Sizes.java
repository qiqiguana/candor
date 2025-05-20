package glengineer.agents.settings;

/**
 * Класс объединяет тройку целых чисел для назначения 
 * трёх типов размеров компонентов.
 */
public class Sizes
{
	public int min;
	public int pref;
	public int max;
	
	public Sizes() {}
	
	public Sizes(int size)
	{
		this(size,size,size);
	}
	
	public Sizes(int min, int pref, int max)
	{
		this.min = min;
		this.pref = pref;
		this.max = max;
	}
	
	public String toString()
	{
		return "(" + min + "," + pref + "," + max + ")";
	}
}
