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

    public ParallelGroupAgent() {
    }

    public ParallelGroupSettings getSettings();

    /**
     * Verifies whether all of the elements of this parallel group agent
     * are gaps.
     */
    public boolean containsGapsOnly();

    /**
     * Removes all gaps from this parallel group.
     */
    public void removeAllGaps();

    /**
     * Returns the gap of the type contained in this parallel group
     * or null if this group has no gaps.
     * If the gaps possess different types, throws a runtime exception.
     * <p>
     * After calling this method this parallel group,
     * which typically consists of gaps only,
     * can be replaced by the gap returned by this method.
     */
    public Agent getGapsType();

    /**
     * ���������� �������������� ���������� � ����� ������������ ������
     * � ���������� �.
     *
     * @param creatorAndAdder	��������� ��� �������� �����
     * 							� ���������� � ��� �����������
     * @return					������, ������������ �� ���������������
     * 							�����������.
     */
    public ParallelGroup groupContent(GroupCreatorAndElementAdder creatorAndAdder);

    public FunctionsOnGroup getFunctionsOnGroupImplemenation();

    public FunctionsOnParallelGroup getFunctionsOnParallelGroupImplemenation();

    private class FunctionsOnParallelGroupImplemenation implements FunctionsOnParallelGroup {

        /**
         * ������������� ������ ������������ ����������� ������������ ������.
         *
         * @param alignment	������ ������������
         */
        public void setContentAlignment(Alignment alignment) {
            ParallelGroupAgent.this.getSettings().contentAlignment = alignment;
        }

        /**
         * Determines the {@code resize} behavior of the {@code ParallelGroup}.
         *
         * @param resizable	{@code true} if the group is resizable;
         * 					if the group is not resizable
         * 					the preferred size is used for
         * 					the minimum and maximum size of the group.
         */
        public void setResizable(boolean resizable) {
            ParallelGroupAgent.this.getSettings().resizable = resizable;
        }

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
        public FunctionsOnParallelGroupAndElement getComponent(String componentName) {
            return new FunctionsOnParallelGroupAndElementImplementation(ParallelGroupAgent.this, ParallelGroupAgent.this.getComponent(componentName));
        }

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
        public FunctionsOnParallelGroupAndElement getGroup(String firstName, String lastName) {
            return new FunctionsOnParallelGroupAndElementImplementation(ParallelGroupAgent.this, ParallelGroupAgent.this.getGroup(firstName, lastName));
        }
    }

    private static class FunctionsOnParallelGroupAndElementImplementation extends GroupAgent.FunctionsOnGroupAndElementImplementation implements FunctionsOnParallelGroupAndElement {

        public FunctionsOnParallelGroupAndElementImplementation(GroupAgent parent, Agent element) {
            super(parent, element);
        }

        /**
         * Assigns the specified alignment of the element {@code elementAgent}
         * in the parallel group {@code parentAgent}.
         */
        public void setAlignmentWithinParent(Alignment alignment) {
            elementAgent.settings.alignmentWithRespectToParent = alignment;
        }
    }

    public String toString();
}
