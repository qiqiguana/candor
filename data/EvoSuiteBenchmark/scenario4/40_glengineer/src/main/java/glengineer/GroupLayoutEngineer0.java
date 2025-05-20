package glengineer;

import glengineer.agents.*;
import glengineer.agents.setters.*;
import glengineer.agents.settings.*;
import glengineer.blocks.*;
import java.util.*;
import java.awt.Component;
import java.awt.Container;
import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * {@code GroupLayoutEngineer} is a shell for the layout manager
 * {@code GroupLayout}. It allows to control components' placement
 * using simple 2-dimensional textual schemes.
 * <p>
 * Such approach to GUI creation removes the problem
 * of representing a 2-dimensional GUI by 1-dimensional code.
 *
 * <h3>How to use the {@code GroupLayoutEngineer}</h3>
 *
 * <ol>
 * <li> Create an instance of the {@code GroupLayoutEngineer}.
 * The first parameter of the constructor is a reference to the container,
 * and the rest are the lines of the textual scheme. For example:
 * <pre>
 * GroupLayoutEngineer gle = new GroupLayoutEngineer(
 *    getContentPane(),
 * 	"FINDLABEL  TEXTFIELD      FINDBUTTON  ",
 * 	"          --------------+             ",
 * 	"           CB11   CB12  | CANSELBUTTON",
 * 	"                        |             ",
 * 	"           CB21   CB22  |             ");
 * </pre>
 *
 * <li> Associate the names on the scheme with concrete components, for example:
 * <pre>
 * gle.associate("FINDLABEL", new JLabel("Find What:"));
 * gle.associate("TEXTFIELD", new JTextField());
 * gle.associate("FINDBUTTON", new JButton("Find"));
 * gle.associate("CB11", new JCheckBox("Match Case"));
 * gle.associate("CB12", new JCheckBox("Wrap Around"));
 * gle.associate("CB21", new JCheckBox("Whole Words"));
 * gle.associate("CB22", new JCheckBox("Search Backwards"));
 * gle.associate("CANSELBUTTON", new JButton("Cancel"));
 * </pre>
 *
 * <li> Apply optional preferences (such as special alignment), for example:
 * <pre>
 * gle.linkSize("FINDBUTTON", "CANSELBUTTON");
 *
 * gle.getParallelGroup(Axis.VERTICAL, "FINDLABEL","FINDBUTTON")
 * 	.setContentAlignment(Alignment.BASELINE);
 *
 * gle.setAutoCreateGaps(true);
 * gle.setAutoCreateContainerGaps(true);
 * </pre>
 * Note that the alignment in the group above
 * could also be automatically set to {@code Baseline}
 * by calling the method {@code setAutoAlignJTextFields()}.
 * <p><br>
 *
 * <li> Invoke the method
 * <pre>
 * gle.engineer();
 * </pre>
 * </ol>
 *
 * <h3>How to control gaps</h3>
 *
 * If you wish to control the gaps yourself instead of relying
 * on automatic gaps creation, you will have two choices.
 * <p>
 * We recommend to deal with gaps like with components, i.e.,
 * to place the required gaps at the scheme
 * and then to associate them with concrete parameters (types and lengthes),
 * for example:
 * <pre>
 * GroupLayoutEngineer gle = new GroupLayoutEngineer(
 *    getContentPane(),
 * 	"FINDLABEL .. TEXTFIELD      . FINDBUTTON  ",
 * 	"            --------------+       .       ",
 * 	"             CB11 .g CB12 |   CANSELBUTTON",
 * 	"               .          |               ",
 * 	"             CB21    CB22 |               ");
 *
 * gle.associateGap(".", ComponentPlacement.RELATED);
 * gle.associateGap("..", ComponentPlacement.UNRELATED);
 * gle.associateGap(".g1", 20);
 * </pre>
 *
 * Note that each gap on the scheme must be started from a dot
 * (in order to make it looking different from components).
 * <p>
 * Also note that any gap (associated with the required parameters)
 * may appear on the scheme any number of times,
 * unlike the components, which appear on the scheme only once.
 * <p>
 * Note that you may skip the associations of the denotations "." and ".."
 * with related and unrelated gaps respectively,
 * because these associations are always applied by default.
 *
 * <p><br>
 * Alternatively, you may add gaps to the layout
 * without making additional changes to the scheme.
 * This approach requires specifying concrete sequential or parallel groups
 * and their concrete elements near which a gap should be added, for example:
 * <pre>
 * gle.getSequentialGroup(Axis.HORIZONTAL, "CB11", "CB22")
 *   .getGroup("CB12","CB22")
 *     .addPrecedingUnrelatedGap(GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE);
 * </pre>
 * We do not recommend to use the letter approach because it minimizes
 * the advantages of the group layout engineer.
 *
 * <h3>How to draw schemes</h3>
 *
 * In order to avoid formal introduction to this topic,
 * we refer to the examples in the package {@code glengineer.demos}
 * for general introduction to the schemes.
 * <p>
 * Here we mention specific yet essential details only.
 *
 * <ol>
 * <li> The names of components (and gaps) on the scheme may consist of
 * letters of any case, numbers, and underscores '_'.
 * <li> For each layout direction (horizontal and vertical)
 * the engineer tries to divide every non-trivial block of elements
 * on the scheme into a sequential or a parallel
 * (if the former is not possible) group of elements or subblocks.
 * <li> One may place on the scheme horizontal and vertical lines
 * which consist of symbols '-' and '|' respectively
 * (and optionally of symbols '+' for the intersections).
 * <li> If a block on the scheme is intersected by
 * a (horizontal or vertical) line, then this block will
 * not be divided into a sequential group of that direction.
 * Hence, it will be divided into a parallel group.
 * <li> The lines do not influence the layout in the perpendicular direction
 * (but, certainly, this layout direction is influenced
 * by the space under the line).
 * <li> If a block is divided into a parallel group by a number of lines,
 * it is divided only by those of them which have the maximum length,
 * and the smaller lines (if they exist) are left for the subblocks.
 * <li> Gaps on the scheme affect sequential groups only.
 * <li> If two sequential subblocks are divided by a number of gaps,
 * then these gaps will be replaced by a single gap of that type.
 * <li> Gaps on the scheme may lie AT lines.
 * Such lines are considered as being continued under that gaps,
 * and the gaps affect only the direction perpendicular to the
 * corresponding line.
 * <li> A line may be intersected only by one gap.
 * <li> If a gap touches one of the sides of a line,
 * it is also assumed that they intersect.
 * </ol>
 *
 * @author Yevgen Ivakhno
 * @version 1.1, 18/05/2009
 */
public class GroupLayoutEngineer {

    private Scheme scheme;

    /**
     * The mapping of the names on the scheme to the associated components.
     */
    private NamesToComponents namesToComponents;

    private NamesToGapAgents namesToGapAgents;

    /**
     * The layout manager {@code GroupLayout} under control.
     */
    private GroupLayout gl;

    /**
     * The object which can create sequential and parallel
     * {@code GroupLayout} groups and add components to groups
     * using the components' names.
     * <p>
     * A reference to this object is given to the agents of elements.
     *
     * @see glengineer.agents
     */
    private GroupCreatorAndElementAdderClass creatorAndAdder;

    /**
     * The agent of the top level horizontal group.
     */
    private TopSequentialGroupAgent horizontalLayoutAgent;

    /**
     * The agent of the top level vertical group.
     */
    private TopSequentialGroupAgent verticalLayoutAgent;

    /**
     * Whether all vertical parallel groups
     * containing JTextFields but not containing other groups
     * should automatically have baseline alignment.
     */
    private boolean autoBaselineAlignment = false;

    /**
     * @param container	the container that needs to be laid out
     * @param lines		a sequence of lines representing the scheme
     */
    public GroupLayoutEngineer(Container container, String... lines) {
    }

    /**
     * Returns a top sequential group agent for the specified layout direction.
     * <p>
     * A group will be created even if it would contain a single element.
     *
     * @param axis	the layout direction
     *
     * @return		the agent of the new sequential group
     */
    protected TopSequentialGroupAgent compouseTopSequentialGroup(Axis axis);

    /**
     * Returns a new sequential group agent filled with
     * the agents of the specified block content.
     *
     * @param block	the block which content should be placed
     * 				into a sequential group.
     *
     * @return		the agent of the new sequential group.
     */
    protected Agent compouseSequentialGroupFromBlock(Block block);

    /**
     * Verifies whether the specified subblock contains a single element
     * or a set of elements
     * and either returns the agent of the corresponding element,
     * or divides the set of elements into a new parallel subgroup
     * and returns the corresponding agent.
     *
     * @param subblock	a block in a sequential group.
     *
     * @return			the agent of the specified subblock content.
     */
    private Agent processSubblockOfSequentialGroup(Block subblock);

    /**
     * Returns the agent of the unique element (a component or a gap)
     * in the specified block which is added into a sequential group.
     *
     * @param block	a block with an only element in a parallel group.
     *
     * @return		the agent of the unique element in the specified block.
     */
    private Agent getElementFromBlockWithOnlyWord(Block block);

    /**
     * Returns a new parallel group agent filled with
     * the agents of the specified block content.
     *
     * @param block	the block which content should be placed
     * 				into a sequential group.
     *
     * @return		the agent of the new parallel group.
     */
    protected Agent composeParallelGroupFromBlock(Block block);

    /**
     * Verifies whether the specified subblock contains a single element
     * or a set of elements
     * and then either returns the agent of the corresponding element,
     * or divides the set of elements into a new sequential subgroup
     * and returns the corresponding agent.
     * <p>
     * If there is a unique element and it is a gap,
     *
     * @param subblock	a block in a parallel group.
     *
     * @return	the agent of the specified subblock content.
     */
    private Agent processSubblockOfParallelGroup(Block subblock);

    /**
     * Associates the specified {@code name} on the scheme
     * with the specified {@code component}.
     *
     * @param name		the component name present on the scheme.
     * @param component	the component to be associated with the name.
     */
    public void associate(String name, Component component);

    /**
     * Associates the specified {@code denotation} on the scheme
     * with gaps of the specified {@code size}.
     */
    public void associateGap(String denotation, int size);

    /**
     * Associates the specified {@code denotation} on the scheme with gaps
     * of the specified {@code min}, {@code pref}, and {@code max} sizes.
     */
    public void associateGap(String denotation, int min, int pref, int max);

    /**
     * Associates the specified {@code denotation} on the scheme
     * with preferred gaps
     * of the specified component placement type (related or unrelated).
     */
    public void associateGap(String denotation, ComponentPlacement type);

    /**
     * Associates the specified {@code denotation} on the scheme
     * with preferred gaps
     * of the specified component placement type (related or unrelated)
     * and of the specified preferred and maximum sizes.
     */
    public void associateGap(String denotation, ComponentPlacement type, int pref, int max);

    /**
     * Sets whether a gap between components should automatically be
     * created. For example, if this is {@code true} and you add two
     * components to a {@code SequentialGroup} a gap between the
     * two components is automatically be created.
     *
     * @param autoCreatePadding	whether a gap between components is
     *        					automatically created.
     */
    public void setAutoCreateGaps(boolean autoCreatePadding);

    /**
     * Sets whether a gap between the container and components that touch
     * the border of the container should automatically be created.
     *
     * @param autoCreateContainerGaps	whether a gap between the container
     * 									and components that touch the border
     * 									of the container should automatically
     * 									be created.
     */
    public void setAutoCreateContainerGaps(boolean autoCreateContainerGaps);

    /**
     * Sets whether all vertical parallel groups
     * containing JTextFields but not containing other groups
     * should automatically have baseline alignment.
     */
    public void setAutoAlignJTextFields(boolean autoBaselineAlignment);

    /**
     * Forces the specified components to have the same size
     * regardless of their preferred, minimum or maximum sizes.
     * Components that are linked are given the maximum
     * of the preferred size of each of the linked components.
     *
     * @param names	the names of the components	that are
     * 				to have the same size.
     */
    public void linkSize(String... names);

    /**
     * Forces the specified components to have the same size
     * along the specified axis regardless of their
     * preferred, minimum or maximum sizes.
     * Components that are linked are given the maximum
     * of the preferred size of each of the linked components.
     *
     * @param axis	the axis to link the size along.
     * @param names	the names of the components	that are
     * 				to have the same size.
     */
    public void linkSize(Axis axis, String... names);

    /**
     * Disables the components under the specified names.
     * <p>
     * Each component must be associated with its name
     * before this method is applied.
     */
    public void disable(String... componentsNames);

    /**
     * Finds the agent of the component with the specified name
     * and returns an interface for adjusting that component.
     *
     * @param axis			layout direction
     * @param componentName	the name of the component to be found
     * @return				an interface {@code FunctionsOnComponent}
     * 						for adjusting the component
     */
    public FunctionsOnComponent getComponent(Axis axis, String componentName);

    /**
     * Finds the agent of the group which first and last components
     * have the specified names;
     * returns an interface for adjusting that group and adding gaps into it.
     *
     * @param axis		layout direction
     * @param firstName	the name of the first component in the group
     * @param lastName	the name of the last component in the group
     * @return			an interface {@code FunctionsOnGroup}
     * 					for adjusting that group and adding gaps into it
     */
    public FunctionsOnGroup getGroup(Axis axis, String firstName, String lastName);

    /**
     * Finds the agent of the parallel group which first and last components
     * have the specified names;
     * returns an interface for adjusting that group and adding gaps into it.
     *
     * @param axis		layout direction
     * @param firstName	the name of the first component in the group
     * @param lastName	the name of the last component in the group
     * @return			an interface {@code FunctionsOnParallelGroup}
     * 					for adjusting that group and adding gaps into it
     */
    public FunctionsOnParallelGroup getParallelGroup(Axis axis, String firstName, String lastName);

    /**
     * Finds the agent of the sequential group which first and last components
     * have the specified names;
     * returns an interface for adjusting that group and adding gaps into it.
     *
     * @param axis		layout direction
     * @param firstName	the name of the first component in the group
     * @param lastName	the name of the last component in the group
     * @return			an interface {@code FunctionsOnSequentialGroup}
     * 					for adjusting that group and adding gaps into it
     */
    public FunctionsOnSequentialGroup getSequentialGroup(Axis axis, String firstName, String lastName);

    /**
     * Returns the agent of the top level sequential group
     * under the specified layout direction.
     *
     * @param axis	layout direction
     * @return		the agent of the top level sequential group
     * 				under the specified layout direction
     */
    public FunctionsOnTopSequentialGroup getTopSequentialGroup(Axis axis);

    /**
     * Returns the agent of the component with the specified name
     * under the specified layout direction.
     *
     * @param axis	layout direction
     * @param name	the name of the component to be found
     * @return		the corresponding agent
     */
    private Agent getAgent(Axis axis, String name);

    /**
     * Returns the agent of the group with the specified first and last names
     * under the specified layout direction.
     *
     * @param axis		layout direction
     * @param firstName	the name of the first component in the group
     * @param lastName	the name of the last component in the group
     * @return			the agent of the corresponding group
     */
    private GroupAgent getGroupAgent(Axis axis, String firstName, String lastName);

    private SequentialGroupAgent getSequentialGroupAgent(Axis axis, String firstName, String lastName);

    private ParallelGroupAgent getParallelGroupAgent(Axis axis, String firstName, String lastName);

    /**
     * Returns (a reference to) the top agent
     * for the specified layout direction.
     *
     * @param axis	layout direction
     * @return		the corresponding top agent
     */
    private TopSequentialGroupAgent getTopAgent(Axis axis);

    /**
     * Places the components into the container
     * according to the scheme and to all additional preferences.
     */
    public void engineer();

    /**
     * If the specified group is parallel,
     * contains directly a component of type {@code JTextField},
     * and does not contain other groups with components,
     * then this method makes the alignment of the group {@code Baseline}.
     * <p>
     * In the case of a group having subgroups with components
     * this method passes the call to that subgroups.
     * <p>
     * As a result of applying this method,
     * all parallel groups having TextFields (but having no subgroups)
     * will be aligned along baselines.
     */
    private void makeTextFieldsBaselineIn(GroupAgent group);

    /**
     * Outputs the groups hierarchy.
     */
    public void printGroupStructure();

    /**
     * Encapsulates the "names of components - to - components" mapping
     * and the corresponding methods of access and modification.
     */
    private class NamesToComponents {

        /**
         * Holds the association between the names of the components on
         * the scheme and the corresponding components.
         */
        private HashMap<String, Component> compMap = new HashMap<String, Component>();

        public NamesToComponents() {
        }

        @SuppressWarnings("unused")
        public boolean contains(String name) {
            return compMap.containsKey(name);
        }

        @SuppressWarnings("unused")
        public boolean contains(Component component) {
            return compMap.containsValue(component);
        }

        public void put(String name, Component component) {
            if (name == null || component == null || !scheme.containsComponentName(name) || compMap.put(name, component) != null)
                throw new IllegalArgumentException("Cannot associate a component" + " with the name \"" + name + "\"");
        }

        /**
         * Returns the component associated with the specified name.
         *
         * @param name	the name of the component to return
         *
         * @return		the component associated with the specified name
         */
        public Component getComponent(String name) {
            if (name == null)
                throw new IllegalArgumentException("No name specified");
            Component result = compMap.get(name);
            if (result == null)
                throw new IllegalArgumentException("cannot find a component" + " with the specified name \"" + name + "\"");
            return result;
        }

        /**
         * Returns the components associated with the specified names.
         *
         * @param names	the names of the components to return
         *
         * @return		the components associated with the specified names
         */
        public Component[] getComponents(String... names) {
            int l = names.length;
            Component[] components = new Component[l];
            for (int i = 0; i < l; i++) components[i] = getComponent(names[i]);
            return components;
        }
    }

    /**
     * Declares the method of possessing the gap agent
     * by a temporary gap agent.
     */
    public interface TemporaryGapsToGaps {

        /**
         * Returns the gap agent associated with the specified
         * temporary gap agent.
         */
        public Agent gap(TemporaryGapAgent temp);
    }

    /**
     * Encapsulates the "names of the gaps - to - agents of these gaps" mapping
     * and the corresponding methods of access and modification.
     */
    private class NamesToGapAgents implements TemporaryGapsToGaps {

        /**
         * Holds the association between the gaps denotations
         * on the scheme and the corresponding gap types.
         * <p>
         * The gaps (which can be preferred or simple) are represented
         * by instances of {@code Agent}, which is a superclass
         * for both {@code PreferredGapAgent} and {@code GapAgent}.
         */
        private HashMap<String, Agent> gapMap = new HashMap<String, Agent>();

        public NamesToGapAgents() {
            safePut(".", new PreferredGapAgent(LayoutStyle.ComponentPlacement.RELATED));
            safePut("..", new PreferredGapAgent(LayoutStyle.ComponentPlacement.UNRELATED));
        }

        public void put(String name, GapAgent agent) {
            if (name == null)
                throw new IllegalArgumentException("Cannot associate a gap with a null name.");
            if (agent == null)
                throw new IllegalArgumentException("Cannot associate null agents.");
            if (!scheme.containsGapName(name))
                return;
            /*throw new IllegalArgumentException(
					"The gap name " + name + " not found on the scheme.");*/
            safePut(name, agent);
        }

        protected void safePut(String name, GapAgent agent) {
            gapMap.put(name, agent);
        }

        public void put(String name, PreferredGapAgent agent) {
            if (name == null)
                throw new IllegalArgumentException("Cannot associate a gap with a null name.");
            if (agent == null)
                throw new IllegalArgumentException("Cannot associate null agents.");
            if (!scheme.containsGapName(name))
                return;
            /*throw new IllegalArgumentException(
					"The gap name " + name + " not found on the scheme.");*/
            safePut(name, agent);
        }

        protected void safePut(String name, PreferredGapAgent agent) {
            gapMap.put(name, agent);
        }

        /**
         * Returns the gap agent associated with the specified name.
         */
        public Agent gap(String name) {
            if (name == null)
                throw new IllegalArgumentException("No name specified");
            Agent result = gapMap.get(name);
            if (result == null)
                throw new IllegalArgumentException("cannot find a gap denoted by \"" + name + "\"");
            return result;
        }

        /**
         * Returns the gap agent associated with the specified
         * temporary gap agent.
         */
        public Agent gap(TemporaryGapAgent temp) {
            return gap(temp.getDenotation());
        }
    }

    /**
     * An adapter for such functions of the {@code GroupLayout}
     * as creating new groups and adding groups and components to groups.
     */
    private class GroupCreatorAndElementAdderClass implements GroupCreatorAndElementAdder {

        /**
         * Creates and returns a new {@code SequentialGroup}.
         */
        public SequentialGroup createSequentialGroup() {
            return gl.createSequentialGroup();
        }

        /**
         * Creates and returns a new {@code ParallelGroup}
         * with the specified settings.
         */
        public ParallelGroup createParallelGroup(ParallelGroupSettings settings) {
            if (settings.contentAlignment != null)
                if (settings.resizable != null)
                    return gl.createParallelGroup(settings.contentAlignment, settings.resizable);
                else
                    //(resizable == null)
                    return gl.createParallelGroup(settings.contentAlignment);
            else //(settings.contentAlignment == null)
            if (settings.resizable != null)
                return gl.createParallelGroup(Alignment.LEADING, settings.resizable);
            else
                return gl.createParallelGroup();
        }

        /**
         * Adds the component having the specified {@code compName}
         * to the specified {@code group},
         * applies to it the specified {@code settings}.
         */
        public void addComponentToGroup(String compName, Group group, ComponentSettings settings) {
            if (settings.alignmentWithRespectToParent != null)
                //It seems that we must add to a parallel group.
                if (group instanceof ParallelGroup)
                    //Really, to a parallel.
                    if (settings.sizes != null) {
                        Sizes sizes = settings.sizes;
                        ((ParallelGroup) group).addComponent(namesToComponents.getComponent(compName), settings.alignmentWithRespectToParent, sizes.min, sizes.pref, sizes.max);
                    } else
                        ((ParallelGroup) group).addComponent(namesToComponents.getComponent(compName), settings.alignmentWithRespectToParent);
                else
                    //Parallel group not found.
                    throw new IllegalArgumentException("components can be aligned in parallel groups only");
            else //Alignment not specified.
            if (settings.sizes != null) {
                Sizes sizes = settings.sizes;
                group.addComponent(namesToComponents.getComponent(compName), sizes.min, sizes.pref, sizes.max);
            } else
                //Even sizes aren't specified.
                group.addComponent(namesToComponents.getComponent(compName));
        }

        /**
         * Adds the specified {@code group} with the specified {@code settings}
         * to another group {@code parentGroup}.
         */
        public void addGroupToGroup(Group group, Settings settings, Group parentGroup) {
            if (settings.alignmentWithRespectToParent != null)
                if (parentGroup instanceof ParallelGroup)
                    ((ParallelGroup) parentGroup).addGroup(settings.alignmentWithRespectToParent, group);
                else
                    throw new IllegalArgumentException("content can be aligned in parallel groups only");
            parentGroup.addGroup(group);
        }
    }

    /**
     * Provides output of the created groups' structure.
     * <p>
     * Is maintained for debugging purposes.
     */
    public class GroupPrinter {

        /**
         * Outputs the both directions top groups structures.
         */
        public void printTopGroupsStructure() {
            Axis axis;
            axis = Axis.HORIZONTAL;
            System.out.println("\nTop " + axis.toString().toUpperCase() + " group structure.");
            printTopGroupStructure(axis);
            System.out.println("\n-----------------------------\n");
            axis = Axis.VERTICAL;
            System.out.println("Top " + axis.toString().toUpperCase() + " group structure.");
            printTopGroupStructure(axis);
        }

        /**
         * Outputs the specified direction top group structure.
         */
        public void printTopGroupStructure(Axis axis) {
            printGroupStructure(getTopAgent(axis));
        }

        /**
         * Outputs the specified group structure.
         */
        public void printGroupStructure(GroupAgent group) {
            System.out.println();
            System.out.println("Elements of " + group.toString() + ":");
            List<Agent> childrenAgents = group.getChildren();
            for (Agent child : childrenAgents) System.out.println(child.toString());
            for (Agent child : childrenAgents) if (child instanceof GroupAgent)
                printGroupStructure((GroupAgent) child);
        }
    }
}
