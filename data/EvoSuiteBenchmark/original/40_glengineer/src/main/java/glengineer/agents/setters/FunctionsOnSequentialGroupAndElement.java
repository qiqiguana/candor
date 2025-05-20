package glengineer.agents.setters;

/**
 * Интерфейс добавления предпочитаемых (related и unrelated) промежутков
 * перед или после элементов последовательной группы. 
 */
public interface FunctionsOnSequentialGroupAndElement
		extends FunctionsOnGroupAndElement
{
	/**Добавляет перед элементом группы "связанный" промежуток.*/
	public void addPrecedingRelatedGap();
	/**Добавляет перед элементом группы "несвязанный" промежуток.*/
	public void addPrecedingUnrelatedGap();
	
	/**Добавляет после элемента группы "связанный" промежуток.*/
	public void addFollowingRelatedGap();
	/**Добавляет после элемента группы "несвязанный" промежуток.*/
	public void addFollowingUnrelatedGap();
	
	/**Добавляет в группу перед её элементом "связанный" промежуток
	 * и дополнительно определяет для него
	 * предпочитаемый и максимальный размеры.*/
	public void addPrecedingRelatedGap(int pref, int max);
	/**Добавляет в группу перед её элементом "несвязанный" промежуток
	 * и дополнительно определяет для него
	 * предпочитаемый и максимальный размеры.*/
	public void addPrecedingUnrelatedGap(int pref, int max);
	
	/**Добавляет в группу после её элемента "связанный" промежуток
	 * и дополнительно определяет для него
	 * предпочитаемый и максимальный размеры.*/
	public void addFollowingRelatedGap(int pref, int max);
	/**Добавляет в группу после её элемента "несвязанный" промежуток
	 * и дополнительно определяет для него
	 * предпочитаемый и максимальный размеры.*/
	public void addFollowingUnrelatedGap(int pref, int max);
}
