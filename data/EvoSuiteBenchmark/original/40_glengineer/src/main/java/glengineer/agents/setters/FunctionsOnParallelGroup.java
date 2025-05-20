package glengineer.agents.setters;

import javax.swing.GroupLayout.*;

/**
 * 
 */
public interface FunctionsOnParallelGroup
		extends FunctionsOnGroup
{
	/**
	 * Устанавливает способ выравнивания содержимого параллельной группы.
	 * 
	 * @param alignment	способ выравнивания
	 */
	public void setContentAlignment(Alignment alignment);
	
	/**
	 * Determines the {@code resize} behavior of a {@code ParallelGroup}.
	 * 
	 * @param resizable	{@code true} if the group is resizable; if the group
     *        			is not resizable the preferred size is used for the
     *        			minimum and maximum size of the group
	 */
	public void setResizable(boolean resizable);
	
	/**
	 * Находит среди "непосредственных детей" параллельной группы
	 * компонент с указанным названием.
	 * <p>
	 * Возвращает интерфейс {@code FunctionsOnParallelGroupAndElement}
	 * установки выравнивания элемента в параллельной группе.
	 * 
	 * @param componentName	название искомого компонента
	 * @return				интерфейс
	 * 						{@code FunctionsOnParallelGroupAndElement}
	 * 						установки выравнивания элемента
	 */
	public FunctionsOnParallelGroupAndElement getComponent(String name);
	
	/**
	 * Находит среди "непосредственных детей" параллельной группы
	 * подгруппу с указанными названиями первого и последнего компонентов.
	 * <p>
	 * Возвращает интерфейс {@code FunctionsOnParallelGroupAndElement}
	 * установки выравнивания элемента в параллельной группе.
	 * 
	 * @param firstName	название первого компонента искомой группы
	 * @param lastName	название последнего компонента искомой группы
	 * @return			интерфейс
	 * 					{@code FunctionsOnParallelGroupAndElement}
	 * 					установки выравнивания элемента
	 */
	public FunctionsOnParallelGroupAndElement getGroup(
			String firstName, String lastName);
	
}
