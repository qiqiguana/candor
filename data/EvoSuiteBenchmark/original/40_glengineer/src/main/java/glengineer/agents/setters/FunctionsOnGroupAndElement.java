package glengineer.agents.setters;

/**
 * ��������� �������� ������ ���������� ����������� � ��������� ������
 * ����� ��� ����� ���������� � ��������.
 */
public interface FunctionsOnGroupAndElement
{
	/**
     * ��������� ���������� ���������� � ��������� ������
     * ����� ��������� � ���������.
     *
     * @param size	the size of the gap
     */
	public void addPrecedingGap(int size);
	
	/**
	 * ��������� ���������� � ��������� ������ ����� ��������� � ���������.
	 * 
     * @param min	the minimum size of the gap
     * @param pref	the preferred size of the gap
     * @param max	the maximum size of the gap
	 */
	public void addPrecedingGap(int min, int pref, int max);
	
	/**
     * ��������� ���������� ���������� � ��������� ������
     * ����� ���������� � ��������.
     *
     * @param size	the size of the gap
     */
	public void addFollowingGap(int size);
	
	/**
	 * ��������� ���������� � ��������� ������ ����� ���������� � ��������.
	 * 
	 * @param min	the minimum size of the gap
	 * @param pref	the preferred size of the gap
	 * @param max	the maximum size of the gap
	 */
	public void addFollowingGap(int min, int pref, int max);
	
}
