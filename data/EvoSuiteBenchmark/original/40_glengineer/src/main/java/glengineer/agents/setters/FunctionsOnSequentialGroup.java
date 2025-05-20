package glengineer.agents.setters;

public interface FunctionsOnSequentialGroup
		extends FunctionsOnGroup
{
	/**
	 * Находит среди "непосредственных детей" последовательной группы
	 * компонент с указанным названием.
	 * <p>
	 * Возвращает интерфейс {@code PrefGapsAdderAroundElement}
	 * добавления промежутка после или перед найденным элементом
	 * в данную последовательную группу.
	 * 
	 * @param componentName	название искомого компонента
	 * @return				интерфейс {@code PrefGapsAdderAroundElement}
	 * 						добавления промежутка
	 */
	public FunctionsOnSequentialGroupAndElement
		getComponent(String name);
	
	/**
	 * Находит среди "непосредственных детей" последовательной группы
	 * группу, имеющую указанные названия первого и последнего компонентов.
	 * <p>
	 * Возвращает интерфейс {@code PreferredGapsAdderAroundElement}
	 * добавления промежутка после или перед найденной группы
	 * в данную последовательную группу.
	 * 
	 * @param firstName	название первого компонента искомой группы
	 * @param lastName	название последнего компонента искомой группы
	 * @return			интерфейс {@code PrefGapsAdderAroundElement}
	 * 					добавления промежутка
	 */
	public FunctionsOnSequentialGroupAndElement
		getGroup( String firstName, String lastName );
	
}
