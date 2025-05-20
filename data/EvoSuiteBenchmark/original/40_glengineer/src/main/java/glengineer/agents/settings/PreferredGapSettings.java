package glengineer.agents.settings;

import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.*;

public class PreferredGapSettings extends Settings
{
	public ComponentPlacement type;
	public SpecialGapSizes sizes;
	
	public PreferredGapSettings(ComponentPlacement type)
	{
		this.type = type;
		sizes = new SpecialGapSizes(
				GroupLayout.DEFAULT_SIZE,
				GroupLayout.DEFAULT_SIZE);
	}
	
	public PreferredGapSettings(ComponentPlacement type, int pref, int max)
	{
		this.type = type;
		sizes = new SpecialGapSizes(pref,max);
	}
	
}
