package glengineer.agents.setters;

/**
 * ��������� ���������� �������������� ������������ �����������
 * � ������ � � ����� ���������������� ������ �������� ������.
 */
public interface FunctionsOnTopSequentialGroup
		extends FunctionsOnSequentialGroup
{
	/**��������� ������������ ����������
	 * � ������ ���������������� ������ �������� ������.*/
	public void addPrecedingContainerGap();
	/**��������� ������������ ���������� � ���������� ����������� �������
	 * � ������ ���������������� ������ �������� ������.*/
	public void addPrecedingContainerGap(int pref, int max);
	
	/**��������� ������������ ����������
	 * � ����� ���������������� ������ �������� ������.*/
	public void addFollowingContainerGap();
	/**��������� ������������ ���������� � ���������� ����������� �������
	 * � ����� ���������������� ������ �������� ������.*/
	public void addFollowingContainerGap(int pref, int max);
	
	public void addBothContainerGaps();
	public void addBothContainerGaps(int pref, int max);
	
}
