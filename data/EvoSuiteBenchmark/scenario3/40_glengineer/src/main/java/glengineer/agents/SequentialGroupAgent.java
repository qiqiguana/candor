package glengineer.agents;

import java.util.ListIterator;
import glengineer.agents.setters.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout.*;

/**
 * �����, �������������� ���������������� ������.
 */
public class SequentialGroupAgent extends GroupAgent {

    /**
     * ���������� �������������� ���������� � ����� ���������������� ������
     * � ���������� �.
     *
     * @param creatorAndAdder ��������� ��� �������� �����
     * 							� ���������� � ��� �����������
     * @return ������, ������������ �� ���������������
     * 							�����������.
     */
    public SequentialGroup groupContent(GroupCreatorAndElementAdder creatorAndAdder);
}
