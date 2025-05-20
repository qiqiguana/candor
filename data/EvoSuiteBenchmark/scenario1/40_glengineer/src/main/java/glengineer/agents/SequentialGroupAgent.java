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

    public SequentialGroup groupContent(GroupCreatorAndElementAdder creatorAndAdder) {
        SequentialGroup result = creatorAndAdder.createSequentialGroup();
        for (Agent child : childrenAgents) child.addContentToGroup(result, creatorAndAdder);
        return result;
    }
}
