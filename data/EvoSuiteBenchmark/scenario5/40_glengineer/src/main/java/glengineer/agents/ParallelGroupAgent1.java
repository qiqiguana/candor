package glengineer.agents;

import glengineer.agents.setters.FunctionsOnGroup;
import glengineer.agents.setters.FunctionsOnParallelGroup;
import glengineer.agents.setters.FunctionsOnParallelGroupAndElement;
import glengineer.agents.settings.ParallelGroupSettings;
import java.util.Iterator;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;

/**
 * The interface declares a number of methods used by the agents
 * but belonging to (an inner class in) the {@code GroupLayoutEngineer},
 * which has access to the methods of the {@code GroupLayout}.
 */
public interface GroupCreatorAndElementAdder {

    /**
     * Creates and returns a new {@code ParallelGroup}
     * with the specified settings.
     */
    public ParallelGroup createParallelGroup(ParallelGroupSettings settings);
}

/**
 * It is known that the {@code GroupLayout} class
 * forces the user to specify all desirable settings of elements and groups
 * exactly at the moment of their addition to the parent groups.
 * The {@code GroupLayoutEngineer} offers a special tool
 * of defining the elements' layout hierarchy, the scheme.
 * However, schemes do not support any means
 * of specifying additional settings of the elements,
 * so, the settings are specified separately
 * by additional methods of the engineer.
 * <p>
 * Since the moment of translating the scheme
 * into the hierarchy of its elements
 * is not precisely the moment of applying the user settings to that elements,
 * the {@code GroupLayoutEngineer} translates the scheme
 * into its own hierarchy of elements,
 * then applies the additional settings,
 * and then, finally, converts the hierarchy
 * to the required hierarchy in the resulting {@code GroupLayout}.
 * <p>
 * Each element of the hierarchy created by the {@code GroupLayoutEngineer}
 * represents the corresponding element (component, group, or gap)
 * in the resulting {@code GroupLayout} hierarchy.
 * The elements of the hierarchy created by the {@code GroupLayoutEngineer}
 * are called <b>agents</b> of the corresponding components, groups or gaps.
 * The agents remember the settings of the corresponding elements,
 * their names (for components), and the agents
 * of children elements (for groups).
 * <p>
 * This class, {@code Agent}, is the base class for the hierarchy.
 *
 * @see ComponentAgent
 * @see GroupAgent
 * @see ParallelGroupAgent
 * @see SequentialGroupAgent
 * @see TopSequentialGroupAgent
 * @see GapAgent
 * @see PreferredGapAgent
 * @see ContainerGapAgent
 * @see TemporaryGapAgent
 */
public abstract class Agent {

    /**
     * Adds the
     *
     * ��������� �� �������������� ���������� � ������,
     * ��������� ��� ��������� ����������� �� �� ��������� ���������
     * ����� ���������� {@code ComponentAdder}, � ��� ��������
     * ����� �������� - ������ ���������� {@code GroupCreator}.
     * <p>
     * ���������� �������-���������.
     *
     * @param parentGroup ������, ���� ����������� �������������� ����������.
     * @param adder ��������� ��� ��������� ����������� �� ���������.
     * @param creator ��������� ��� �������� ����� ��������.
     */
    public abstract void addContentToGroup(Group parentGroup, GroupCreatorAndElementAdder creatorAndAdder);
}

/**
 * �����, �������������� ������������ ������.
 */
public class ParallelGroupAgent extends GroupAgent {

    /**
     * ���������� �������������� ���������� � ����� ������������ ������
     * � ���������� �.
     *
     * @param creatorAndAdder ��������� ��� �������� �����
     * 							� ���������� � ��� �����������
     * @return ������, ������������ �� ���������������
     * 							�����������.
     */
    public ParallelGroup groupContent(GroupCreatorAndElementAdder creatorAndAdder) {
        ParallelGroup result = creatorAndAdder.createParallelGroup(getSettings());
        for (Agent child : childrenAgents) child.addContentToGroup(result, creatorAndAdder);
        return result;
    }

    public ParallelGroupSettings getSettings();
}
