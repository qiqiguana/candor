package glengineer.agents.settings;


public class ContainerGapSettings extends Settings
{
	public SpecialGapSizes sizes;
	
	public ContainerGapSettings()
	{
		sizes = new SpecialGapSizes();
	}
	
	public ContainerGapSettings(SpecialGapSizes sizes)
	{
		this.sizes = sizes;
	}
	
	public ContainerGapSettings(int pref, int max)
	{
		sizes = new SpecialGapSizes(pref,max);
	}
	
}
