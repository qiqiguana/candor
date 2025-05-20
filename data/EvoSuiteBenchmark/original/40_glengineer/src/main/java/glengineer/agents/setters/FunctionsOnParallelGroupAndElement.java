package glengineer.agents.setters;

import javax.swing.GroupLayout.Alignment;

/**
 * Интерфейс содержит метод установки способа выравнивания элемента
 * в содержащей его параллельной группе.
 */
public interface FunctionsOnParallelGroupAndElement
		extends FunctionsOnGroupAndElement
{
	/**
	 * Устанавливает способ выравнивания элемента в параллельной группе.
	 */
	public void setAlignmentWithinParent(Alignment alignment);
	
}
