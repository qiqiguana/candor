package glengineer.agents;

import javax.swing.GroupLayout.SequentialGroup;
import glengineer.agents.setters.*;

/**
 * �����, �������������� ���������������� ������ �������� ������.
 * <p>
 * ��������� ��������� {@code FunctionsOnTopSequentialGroup}
 * ���������� �������������� ������������ �����������
 * � ������ � � ����� ���������������� ������ �������� ������.
 */
public class TopSequentialGroupAgent extends SequentialGroupAgent {

    /**
     * ���������� �������������� ���������� � ����� ���������������� ������,
     * ��������, ���� ����, ������������ ���������� � ������ � � �����,
     * � ���������� ���������� ������.
     * <p>
     * ����������� ����� {@code TopSequentialGroupAgent.groupContent()},
     * �������� ������ � ������������� ������������.
     *
     * @param creatorAndAdder ��������� ��� �������� �����
     * 							� ���������� � ��� �����������
     * @return ������, ������������ �� ���������������
     * 							�����������.
     */
    public SequentialGroup groupContent(GroupCreatorAndElementAdder creatorAndAdder);
}
