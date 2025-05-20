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
     * �����, �������������� ������������ ���������� � ������ ������.
     */
    private ContainerGapAgent precedingContainerGap;

    /**
     * �����, �������������� ������������ ���������� � ����� ������.
     */
    private ContainerGapAgent followingContainerGap;

    /**
     * ���������� �������������� ���������� � ����� ���������������� ������,
     * ��������, ���� ����, ������������ ���������� � ������ � � �����,
     * � ���������� ���������� ������.
     * <p>
     * ����������� ����� {@code TopSequentialGroupAgent.groupContent()},
     * �������� ������ � ������������� ������������.
     *
     * @param creatorAndAdder	��������� ��� �������� �����
     * 							� ���������� � ��� �����������
     * @return					������, ������������ �� ���������������
     * 							�����������.
     */
    public SequentialGroup groupContent(GroupCreatorAndElementAdder creatorAndAdder);

    public FunctionsOnGroup getFunctionsOnGroupImplemenation();

    public FunctionsOnSequentialGroup getFunctionsOnSequentialGroupImplemenation();

    public FunctionsOnTopSequentialGroup getFunctionsOnTopSequentialGroupImplemenation();

    private class FunctionsOnTopSequentialGroupImplemenation extends SequentialGroupAgent.FunctionsOnSequentialGroupImplemenation implements FunctionsOnTopSequentialGroup {

        /**
         * ��������� ������������ ����������
         * � ������ ���������������� ������ �������� ������.
         */
        public void addPrecedingContainerGap() {
            precedingContainerGap = new ContainerGapAgent();
        }

        /**
         * ��������� ������������ ���������� � ���������� ����������� �������
         * � ������ ���������������� ������ �������� ������.
         */
        public void addPrecedingContainerGap(int pref, int max) {
            precedingContainerGap = new ContainerGapAgent(pref, max);
        }

        /**
         * ��������� ������������ ����������
         * � ����� ���������������� ������ �������� ������.
         */
        public void addFollowingContainerGap() {
            followingContainerGap = new ContainerGapAgent();
        }

        /**
         * ��������� ������������ ���������� � ���������� ����������� �������
         * � ����� ���������������� ������ �������� ������.
         */
        public void addFollowingContainerGap(int pref, int max) {
            followingContainerGap = new ContainerGapAgent(pref, max);
        }

        public void addBothContainerGaps() {
            addPrecedingContainerGap();
            addFollowingContainerGap();
        }

        public void addBothContainerGaps(int pref, int max) {
            addPrecedingContainerGap(pref, max);
            addFollowingContainerGap(pref, max);
        }
    }
}
