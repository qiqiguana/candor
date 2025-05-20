package glengineer.agents.setters;

/**
 * »нтерфейс добавлени€ предпочитаемых контейнерных промежутков
 * в начало и в конец последовательной группы верхнего уровн€.
 */
public interface FunctionsOnTopSequentialGroup
		extends FunctionsOnSequentialGroup
{
	/**ƒобавл€ет контейнерный промежуток
	 * в начало последовательной группы верхнего уровн€.*/
	public void addPrecedingContainerGap();
	/**ƒобавл€ет контейнерный промежуток с указанными параметрами размера
	 * в начало последовательной группы верхнего уровн€.*/
	public void addPrecedingContainerGap(int pref, int max);
	
	/**ƒобавл€ет контейнерный промежуток
	 * в конец последовательной группы верхнего уровн€.*/
	public void addFollowingContainerGap();
	/**ƒобавл€ет контейнерный промежуток с указанными параметрами размера
	 * в конец последовательной группы верхнего уровн€.*/
	public void addFollowingContainerGap(int pref, int max);
	
	public void addBothContainerGaps();
	public void addBothContainerGaps(int pref, int max);
	
}
