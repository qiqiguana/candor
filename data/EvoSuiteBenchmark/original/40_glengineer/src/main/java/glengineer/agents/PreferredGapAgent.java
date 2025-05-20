package glengineer.agents;

import glengineer.agents.settings.*;

import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.ComponentPlacement;


public class PreferredGapAgent extends Agent
{
	public PreferredGapAgent(ComponentPlacement type)
	{
		settings = new PreferredGapSettings(type);
	}
	public PreferredGapAgent(ComponentPlacement type, int pref, int max)
	{
		settings = new PreferredGapSettings(type,pref,max);
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

	public ComponentAgent findDependingComponentByName(String name)
	{
		return null;
	}
	public GroupAgent findDependingGroupByNames(
			String first, String last) {
		return null;
	}
	public ParallelGroupAgent findDependingParallelGroupByNames(
			String first, String last) {
		return null;
	}
	public SequentialGroupAgent findDependingSequentialGroupByNames(
			String first, String last) {
		return null;
	}
	public PreferredGapSettings getSettings()
	{
		return (PreferredGapSettings)settings;
	}
	
	public void setSettings(ComponentPlacement type, int pref, int max)
	{
		settings = new PreferredGapSettings(type,pref,max);
	}
	/**
	 * Добавляет представляемый предпочитаемый промежуток в группу,
	 * учитывая имеющиеся для него настройки.
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
		if( !(targetGroup instanceof SequentialGroup) )
			throw new IllegalArgumentException(
					"cannot add preferred gaps into non-sequential groups" );
		PreferredGapSettings settings = getSettings();
		SpecialGapSizes sizes = settings.sizes;
		((SequentialGroup)targetGroup).
			addPreferredGap(settings.type, sizes.pref, sizes.max);
	}
	
	public String toString() {
		return getSettings().type + " gap";
//				+ ", " + getSettings().sizes;
	}
	
}
