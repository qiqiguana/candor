package glengineer.agents;

import glengineer.agents.settings.GapSettings;
import glengineer.agents.settings.Sizes;

import javax.swing.GroupLayout.Group;

/**
 * Агент, представляющий промежуток.
 */
public class GapAgent extends Agent
{
	public GapAgent()
	{
		settings = new GapSettings();
	}
	public GapAgent(Sizes sizes)
	{
		settings = new GapSettings(sizes);
	}
	public GapAgent(int size)
	{
		settings = new GapSettings(size);
	}
	public GapAgent(int min, int pref, int max)
	{
		settings = new GapSettings(min,pref,max);
	}
	
	public String getFirstName() {
		return null;
	}
	public String getLastName() {
		return null;
	}
	
	public boolean isComponent(String componentName)
	{
		return false;
	}
	public boolean isGroup(String firstName, String lastName)
	{
		return false;
	}
	public boolean isGap()
	{
		return true;
	}

	public boolean equals(Agent agent)
	{
		return false;
	}

	public GapSettings getSettings()
	{
		return (GapSettings)settings;
	}
	
	public ComponentAgent findDependingComponentByName(String name)
	{
		return null;
	}
	public GroupAgent findDependingGroupByNames(String first, String last) {
		return null;
	}
	public ParallelGroupAgent findDependingParallelGroupByNames(
			String firstName, String lastName ) {
		return null;
	}
	public SequentialGroupAgent findDependingSequentialGroupByNames(
			String firstName, String lastName ) {
		return null;
	}
	
	/**
	 * Добавляет представляемый промежуток в группу, учитывая
	 * имеющиеся для него настройки.
	 * <p>
	 * Вызывается группой-родителем.
	 * 
	 * @param targetGroup		группа, куда добавляется представляемый
	 * 							промежуток
	 * @param creatorAndAdder	интерфейс для создания групп
	 * 							и добавления в них компонентов
	 */
	public void addContentToGroup(
			Group targetGroup, GroupCreatorAndElementAdder creatorAndAdder )
	{
		Sizes sizes = getSettings().sizes;
		targetGroup.addGap(sizes.min, sizes.pref, sizes.max);
	}
	public void setSizes(int min, int pref, int max)
	{
		getSettings().sizes = new Sizes(min, pref, max);
	}
	
	public String toString() {
		return "gap " + getSettings().sizes;
	}
	
}
