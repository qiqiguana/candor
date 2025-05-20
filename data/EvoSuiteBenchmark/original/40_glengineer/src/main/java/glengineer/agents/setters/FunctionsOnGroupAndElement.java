package glengineer.agents.setters;

/**
 * Интерфейс содержит методы добавления промежутков в некоторую группу
 * перед или после некоторого её элемента.
 */
public interface FunctionsOnGroupAndElement
{
	/**
     * Добавляет постоянный промежуток в некоторую группу
     * перед некоторым её элементом.
     *
     * @param size	the size of the gap
     */
	public void addPrecedingGap(int size);
	
	/**
	 * Добавляет промежуток в некоторую группу перед некоторым её элементом.
	 * 
     * @param min	the minimum size of the gap
     * @param pref	the preferred size of the gap
     * @param max	the maximum size of the gap
	 */
	public void addPrecedingGap(int min, int pref, int max);
	
	/**
     * Добавляет постоянный промежуток в некоторую группу
     * после некоторого её элемента.
     *
     * @param size	the size of the gap
     */
	public void addFollowingGap(int size);
	
	/**
	 * Добавляет промежуток в некоторую группу после некоторого её элемента.
	 * 
	 * @param min	the minimum size of the gap
	 * @param pref	the preferred size of the gap
	 * @param max	the maximum size of the gap
	 */
	public void addFollowingGap(int min, int pref, int max);
	
}
