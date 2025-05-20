package glengineer.agents.setters;

import javax.swing.GroupLayout.*;

/**
 * 
 */
public interface FunctionsOnParallelGroup
		extends FunctionsOnGroup
{
	/**
	 * ������������� ������ ������������ ����������� ������������ ������.
	 * 
	 * @param alignment	������ ������������
	 */
	public void setContentAlignment(Alignment alignment);
	
	/**
	 * Determines the {@code resize} behavior of a {@code ParallelGroup}.
	 * 
	 * @param resizable	{@code true} if the group is resizable; if the group
     *        			is not resizable the preferred size is used for the
     *        			minimum and maximum size of the group
	 */
	public void setResizable(boolean resizable);
	
	/**
	 * ������� ����� "���������������� �����" ������������ ������
	 * ��������� � ��������� ���������.
	 * <p>
	 * ���������� ��������� {@code FunctionsOnParallelGroupAndElement}
	 * ��������� ������������ �������� � ������������ ������.
	 * 
	 * @param componentName	�������� �������� ����������
	 * @return				���������
	 * 						{@code FunctionsOnParallelGroupAndElement}
	 * 						��������� ������������ ��������
	 */
	public FunctionsOnParallelGroupAndElement getComponent(String name);
	
	/**
	 * ������� ����� "���������������� �����" ������������ ������
	 * ��������� � ���������� ���������� ������� � ���������� �����������.
	 * <p>
	 * ���������� ��������� {@code FunctionsOnParallelGroupAndElement}
	 * ��������� ������������ �������� � ������������ ������.
	 * 
	 * @param firstName	�������� ������� ���������� ������� ������
	 * @param lastName	�������� ���������� ���������� ������� ������
	 * @return			���������
	 * 					{@code FunctionsOnParallelGroupAndElement}
	 * 					��������� ������������ ��������
	 */
	public FunctionsOnParallelGroupAndElement getGroup(
			String firstName, String lastName);
	
}
