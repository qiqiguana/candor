package glengineer.agents.settings;

/**
 * ����� ������������� ���� ����� �����
 * ��� ���������� ���� ���������� ������� "����������� �����������"
 * (�������������� ��� ������������).
 */
public class SpecialGapSizes
{
	public int pref;
	public int max;
	
	public SpecialGapSizes() {}
	
	public SpecialGapSizes(int pref, int max)
	{
		this.pref = pref;
		this.max = max;
	}
	
	public String toString()
	{
		return "(" + pref + "," + max + ")";
	}
}
