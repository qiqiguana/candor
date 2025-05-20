package glengineer.agents;

import glengineer.agents.settings.Settings;

import javax.swing.GroupLayout.Group;


/**
 * It is known that the {@code GroupLayout} class
 * forces the user to specify all desirable settings of elements and groups
 * exactly at the moment of their addition to the parent groups.
 * The {@code GroupLayoutEngineer} offers a special tool
 * of defining the elements' layout hierarchy, the scheme.
 * However, schemes do not support any means
 * of specifying additional settings of the elements,
 * so, the settings are specified separately
 * by additional methods of the engineer.
 * <p>
 * Since the moment of translating the scheme
 * into the hierarchy of its elements
 * is not precisely the moment of applying the user settings to that elements,
 * the {@code GroupLayoutEngineer} translates the scheme
 * into its own hierarchy of elements,
 * then applies the additional settings,
 * and then, finally, converts the hierarchy
 * to the required hierarchy in the resulting {@code GroupLayout}.
 * <p>
 * Each element of the hierarchy created by the {@code GroupLayoutEngineer}
 * represents the corresponding element (component, group, or gap)
 * in the resulting {@code GroupLayout} hierarchy.
 * The elements of the hierarchy created by the {@code GroupLayoutEngineer}
 * are called <b>agents</b> of the corresponding components, groups or gaps.
 * The agents remember the settings of the corresponding elements,
 * their names (for components), and the agents
 * of children elements (for groups).
 * <p>
 * This class, {@code Agent}, is the base class for the hierarchy.
 * 
 * @see	ComponentAgent
 * @see	GroupAgent
 * @see	ParallelGroupAgent
 * @see	SequentialGroupAgent
 * @see	TopSequentialGroupAgent
 * @see	GapAgent
 * @see	PreferredGapAgent
 * @see	ContainerGapAgent
 * @see	TemporaryGapAgent
 */
public abstract class Agent
{
	/**
	 * Настройки представляемого элемента.
	 */
	protected Settings settings;
	
	/**
	 * Возвращает название первого компонента (не промежутка)
	 * в представляемом содержимом, если это агент группы,
	 * или название компонента, если представляется компонент.
	 */
	public abstract String getFirstName();
	
	/**
	 * Возвращает название последнего компонента (не промежутка)
	 * в представляемом содержимом, если это агент группы,
	 * или название компонента, если представляется компонент.
	 */
	public abstract String getLastName();
	
	/**
	 * Проверяет, является ли данный агент агентом компонента
	 * с указанным названием.
	 * 
	 * @param componentName	название компонента
	 * @return				{@code true}, если агент
	 * 						представляет этот компонент
	 */
	public abstract boolean isComponent(String componentName);
	
	/**
	 * Проверяет, представляет ли данный агент группу, идентифицируемую
	 * указанными названиями её начального и конечного компонентов.
	 * 
	 * @param firstName	название начального компонента группы
	 * @param lastName	название конечного компонента группы
	 * @return			{@code true}, если представляет
	 */
	public abstract boolean isGroup(String firstName, String lastName);
	
	/**
	 * Verifies whether this agent represents a gap of some type.
	 */
	public abstract boolean isGap();
	
	/**
	 * Метод сравнения агентов компонентов и групп на предмет совпадения
	 * их имён. Для агентов промежутков можно возвращать {@code false},
	 * т.к. именами они не обладают.
	 * 
	 * @param agent	агент, сравниваемый с данным
	 * @return		{@code true}, если агент имеет то же имя (имена),
	 * 				что и данный агент.
	 */
	public abstract boolean equals(Agent agent);
	
	/**
	 * Searches for the component agent with the specified name
	 * among this agent and all agents of lower level (if this is a group).
	 * 
	 * @return	the agent found or null if not found.
	 */
	public abstract ComponentAgent findDependingComponentByName(
			String componentName);

	/**
	 * Searches for the group agent with the specified first and last names
	 * among this agent and all agents of lower level (if this is a group).
	 * 
	 * @return	the agent found or null if not found.
	 */
	public abstract GroupAgent findDependingGroupByNames(
			String firstName, String lastName);
	public abstract ParallelGroupAgent findDependingParallelGroupByNames(
			String firstName, String lastName);
	public abstract SequentialGroupAgent findDependingSequentialGroupByNames(
			String firstName, String lastName);

	/**
	 * Adds the 
	 * 
	 * Добавляет всё представляемое содержимое в группу,
	 * используя для получения компонентов по их имеющимся названиям
	 * метод интерфейса {@code ComponentAdder}, а для создания
	 * новых подгрупп - методы интерфейса {@code GroupCreator}.
	 * <p>
	 * Вызывается группой-родителем.
	 * 
	 * @param parentGroup	группа, куда добавляется представляемое содержимое.
	 * @param adder			интерфейс для получения компонентов по названиям.
	 * @param creator		интерфейс для создания новых подгрупп.
	 */
	public abstract void addContentToGroup(
			Group parentGroup, GroupCreatorAndElementAdder creatorAndAdder );
	
	/**
	 * Вывод названия агента.
	 * <p>
	 * Для компонента это должно быть, собственно, его название,
	 * для группы - названия её начального и конечного компонентов,
	 * а для промежутков - их параметры.
	 */
	public abstract String toString();
	
}
