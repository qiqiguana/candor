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
 * The agent representing a component.
 */
public class ComponentAgent extends Agent {

    /**
     * Returns a class implementing the interface {@code FunctionsOnComponent}
     * which allows the user to control the sizes of this component.
     */
    public FunctionsOnComponent getFunctionsOnComponentImplementation();
}

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

    /**
     * Finds the agent of the component with the specified name
     * and returns an interface for adjusting that component.
     *
     * @param axis layout direction
     * @param componentName the name of the component to be found
     * @return an interface {@code FunctionsOnComponent}
     * 						for adjusting the component
     */
    public FunctionsOnComponent getComponent(Axis axis, String componentName) {
        Agent agent = getAgent(axis, componentName);
        if (agent == null)
            throw new IllegalArgumentException("no such component found: " + componentName);
        return ((ComponentAgent) agent).getFunctionsOnComponentImplementation();
    }

    /**
     * Returns the agent of the component with the specified name
     * under the specified layout direction.
     *
     * @param axis	layout direction
     * @param name	the name of the component to be found
     * @return		the corresponding agent
     */
    private Agent getAgent(Axis axis, String name);
}
