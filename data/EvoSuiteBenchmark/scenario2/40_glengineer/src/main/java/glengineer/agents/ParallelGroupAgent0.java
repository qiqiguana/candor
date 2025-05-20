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

    /**
     * Verifies whether all of the elements of this parallel group agent
     * are gaps.
     */
    public boolean containsGapsOnly() {
        for (Agent child : childrenAgents) if (!child.isGap())
            return false;
        return true;
    }
}
