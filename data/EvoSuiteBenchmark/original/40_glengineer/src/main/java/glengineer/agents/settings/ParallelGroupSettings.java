package glengineer.agents.settings;

import javax.swing.GroupLayout.*;

public class ParallelGroupSettings extends Settings
{
	/**
	 * ���������� ������������ ����������� ������������ ������.
	 * <p>
	 * ������������� ��������� {@code alignment} � ������
	 * {@code GroupLayout.createParallelGroup(Alignment alignment)}
	 */
	public Alignment contentAlignment;
	
	/**
	 * ���������� ���������������� ����������� ������������ ������.
	 * <p>
	 * ������������� ��������� {@code resizable} � ������
	 * {@code GroupLayout.createParallelGroup(Alignment alignment,
	 * boolean resizable)}
	 */
	public Boolean resizable;
	
}
