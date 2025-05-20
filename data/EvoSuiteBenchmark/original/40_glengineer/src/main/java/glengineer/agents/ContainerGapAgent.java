package glengineer.agents;

import glengineer.agents.settings.*;

import javax.swing.GroupLayout.*;

/**
 * ����� "������", ��������������� ������������ ����������.
 * <p>
 * � ������� �� ���� ��������� �������, ���� � ����������������
 * �� �������� ���������� ������������ ������ {@code Agent},
 * ��������� �� ����������� � ������ ����� ������,
 * � ������ �� ��������� � ����� ���������������� ������ {@code Agent}.
 */
public class ContainerGapAgent
{
	public ContainerGapSettings settings;
	
	public ContainerGapAgent()
	{
		settings = new ContainerGapSettings();
	}
	public ContainerGapAgent(int pref, int max)
	{
		settings = new ContainerGapSettings(pref,max);
	}
	
	public boolean isGap()
	{
		return true;
	}
	
	/**
	 * ��������� �������������� ������������ ����������
	 * �� ����� ����������� � ��������� ���������������� ������.
	 */
	public void addOneselfToGroup(SequentialGroup targetGroup)
	{
		SpecialGapSizes sizes = settings.sizes;
		if( sizes != null )
			targetGroup.addContainerGap(sizes.pref, sizes.max);
		else
			targetGroup.addContainerGap();
	}
	
}
