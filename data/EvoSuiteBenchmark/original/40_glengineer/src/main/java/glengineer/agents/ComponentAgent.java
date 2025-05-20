package glengineer.agents;

import glengineer.agents.setters.FunctionsOnComponent;
import glengineer.agents.settings.ComponentSettings;
import glengineer.agents.settings.Sizes;

import javax.swing.GroupLayout.Group;


/**
 * The agent representing a component.
 */
public class ComponentAgent
		extends Agent
{
	private String componentName;
	
	public ComponentAgent(String componentName)
	{
		this.componentName = componentName;
		settings = new ComponentSettings();
	}
	
	public String getFirstName() {
		return componentName;
	}
	
	public String getLastName() {
		return componentName;
	}
	
	public boolean isComponent(String componentName)
	{
		return this.componentName.equals(componentName);
	}
	public boolean isGroup(String firstName, String lastName)
	{
		return false;
	}
	
	public boolean isGap()
	{
		return false;
	}
	
	public boolean equals(Agent agent)
	{
		return agent.isComponent( componentName );
	}
	
	private ComponentSettings getSettings()
	{
		return (ComponentSettings)settings;
	}
	
	/**
	 * Returns this component agent if its name equals the specified one
	 * or null if it is not the case.
	 */
	public ComponentAgent findDependingComponentByName(String componentName)
	{
		if( isComponent(componentName) )
			return this;
		else return null;
	}
	
	public GroupAgent findDependingGroupByNames(
			String firstName, String lastName ) {
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
	 * Добавляет представляемый компонент в группу, учитывая
	 * имеющиеся для него компонентные настройки.
	 * Для получения компонента по его имеющемуся названию применяется
	 * соответствующий метод интерфейса {@code ComponentAdder}.
	 * <p>
	 * Вызывается группой-родителем.
	 * 
	 * @param targetGroup		группа, куда добавляется представляемый
	 * 							компонент
	 * @param creatorAndAdder	интерфейс для создания групп
	 * 							и добавления в них компонентов
	 */
	public void addContentToGroup(
			Group targetGroup, GroupCreatorAndElementAdder creatorAndAdder )
	{
		creatorAndAdder.addComponentToGroup(
				componentName, targetGroup, getSettings() );
	}

	/**
	 * Returns a class implementing the interface {@code FunctionsOnComponent}
	 * which allows the user to control the sizes of this component.
	 */
	public FunctionsOnComponent getFunctionsOnComponentImplementation()
	{
		return new
		FunctionsOnComponent()
		{
			public void setSize(int size)
			{
				ComponentAgent.this.getSettings().sizes = new Sizes(size);
			}	
			public void setSize(int min, int pref, int max)
			{
				ComponentAgent.this.getSettings().sizes =
										new Sizes(min, pref, max);
			}
		};
	}
	
	public String toString() {
		return "component " + componentName;
	}
	
}
