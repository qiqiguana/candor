package glengineer.agents.settings;

import javax.swing.GroupLayout.*;

public class ParallelGroupSettings extends Settings
{
	/**
	 * Определяет выравнивание содержимого параллельной группы.
	 * <p>
	 * Соответствует параметру {@code alignment} в методе
	 * {@code GroupLayout.createParallelGroup(Alignment alignment)}
	 */
	public Alignment contentAlignment;
	
	/**
	 * Определяет масштабируемость содержимого параллельной группы.
	 * <p>
	 * Соответствует параметру {@code resizable} в методе
	 * {@code GroupLayout.createParallelGroup(Alignment alignment,
	 * boolean resizable)}
	 */
	public Boolean resizable;
	
}
