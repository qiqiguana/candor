package glengineer.agents.settings;

/**
 *  ласс инкапсулирует пару целых чисел
 * дл€ назначени€ двух параметров размера "специальных промежутков"
 * (предпочитаемых или контейнерных).
 */
public class SpecialGapSizes
{
	public int pref;
	public int max;
	
	public SpecialGapSizes() {}
	
	public SpecialGapSizes(int pref, int max)
	{
		this.pref = pref;
		this.max = max;
	}
	
	public String toString()
	{
		return "(" + pref + "," + max + ")";
	}
}
