package glengineer.agents.setters;

/**
 * ��������� ���������� �������������� (related � unrelated) �����������
 * ����� ��� ����� ��������� ���������������� ������. 
 */
public interface FunctionsOnSequentialGroupAndElement
		extends FunctionsOnGroupAndElement
{
	/**��������� ����� ��������� ������ "���������" ����������.*/
	public void addPrecedingRelatedGap();
	/**��������� ����� ��������� ������ "�����������" ����������.*/
	public void addPrecedingUnrelatedGap();
	
	/**��������� ����� �������� ������ "���������" ����������.*/
	public void addFollowingRelatedGap();
	/**��������� ����� �������� ������ "�����������" ����������.*/
	public void addFollowingUnrelatedGap();
	
	/**��������� � ������ ����� � ��������� "���������" ����������
	 * � ������������� ���������� ��� ����
	 * �������������� � ������������ �������.*/
	public void addPrecedingRelatedGap(int pref, int max);
	/**��������� � ������ ����� � ��������� "�����������" ����������
	 * � ������������� ���������� ��� ����
	 * �������������� � ������������ �������.*/
	public void addPrecedingUnrelatedGap(int pref, int max);
	
	/**��������� � ������ ����� � �������� "���������" ����������
	 * � ������������� ���������� ��� ����
	 * �������������� � ������������ �������.*/
	public void addFollowingRelatedGap(int pref, int max);
	/**��������� � ������ ����� � �������� "�����������" ����������
	 * � ������������� ���������� ��� ����
	 * �������������� � ������������ �������.*/
	public void addFollowingUnrelatedGap(int pref, int max);
}
