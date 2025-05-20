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

    public SequentialGroup groupContent(GroupCreatorAndElementAdder creatorAndAdder) {
        SequentialGroup result = creatorAndAdder.createSequentialGroup();
        if (precedingContainerGap != null)
            precedingContainerGap.addOneselfToGroup(result);
        for (Agent child : childrenAgents) child.addContentToGroup(result, creatorAndAdder);
        if (followingContainerGap != null)
            followingContainerGap.addOneselfToGroup(result);
        return result;
    }
}
