package glengineer.agents;

import glengineer.agents.settings.*;

import javax.swing.GroupLayout.*;

/**
 *  ласс "агента", представл€ющего контейнерный промежуток.
 * <p>
 * ¬ отличие от всех остальных агентов, этот в действительности
 * не €вл€етс€ подклассом абстрактного класса {@code Agent},
 * поскольку не добавл€етс€ в список детей группы,
 * и потому не нуждаетс€ в части функциональности класса {@code Agent}.
 */
public class ContainerGapAgent
{
	public ContainerGapSettings settings;
	
	public ContainerGapAgent()
	{
		settings = new ContainerGapSettings();
	}
	public ContainerGapAgent(int pref, int max)
	{
		settings = new ContainerGapSettings(pref,max);
	}
	
	public boolean isGap()
	{
		return true;
	}
	
	/**
	 * ƒобавл€ет представл€емый контейнерный промежуток
	 * со всеми настройками в указанную последовательную группу.
	 */
	public void addOneselfToGroup(SequentialGroup targetGroup)
	{
		SpecialGapSizes sizes = settings.sizes;
		if( sizes != null )
			targetGroup.addContainerGap(sizes.pref, sizes.max);
		else
			targetGroup.addContainerGap();
	}
	
}
