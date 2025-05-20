package glengineer.agents;

import glengineer.agents.setters.FunctionsOnGroup;
import glengineer.agents.setters.FunctionsOnParallelGroup;
import glengineer.agents.setters.FunctionsOnParallelGroupAndElement;
import glengineer.agents.settings.ParallelGroupSettings;
import java.util.Iterator;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;

/**
 * �����, �������������� ������������ ������.
 */
public class ParallelGroupAgent extends GroupAgent {

    public ParallelGroup groupContent(GroupCreatorAndElementAdder creatorAndAdder) {
        ParallelGroup result = creatorAndAdder.createParallelGroup(getSettings());
        for (Agent child : childrenAgents) child.addContentToGroup(result, creatorAndAdder);
        return result;
    }
}
