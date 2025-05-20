package glengineer.agents;

import javax.swing.GroupLayout.Group;


/**
 * Temporary gap agents replace real gap agents
 * which names are present on the scheme but are not yet
 * associated by the user with concrete types of gaps
 * (at the moment of building the hierarchy of agents).
 * <p>
 * The class {@code TemporaryGapAgent} just encapsulates
 * the name of a gap on the scheme
 * and implements the method {@code equals()} of comparison
 * with other temporary gaps by their names.
 * The other abstract methods of the base class {@code Agent}
 * are implemented in a trivial manner.
 */
public class TemporaryGapAgent extends Agent
{
	private String gapDenotation;
	
	public TemporaryGapAgent(String gapDenotation)
	{
		this.gapDenotation = gapDenotation;
	}
	
	public String getDenotation()
	{
		return gapDenotation;
	}
	
	public String getFirstName() {
		return null;
	}
	public String getLastName() {
		return null;
	}

	public boolean isComponent(String componentName) {
		return false;
	}
	public boolean isGroup(String firstName, String lastName) {
		return false;
	}
	public boolean isGap()
	{
		return true;
	}
	
	public boolean equals(Agent agent)
	{
		return agent instanceof TemporaryGapAgent &&
			((TemporaryGapAgent)agent).gapDenotation.equals(
					this.gapDenotation );
	}

	public ComponentAgent findDependingComponentByName(String componentName) {
		return null;
	}
	public GroupAgent findDependingGroupByNames(
			String firstName, String lastName ) {
		return null;
	}
	public ParallelGroupAgent findDependingParallelGroupByNames(
			String firstName, String lastName ) {
		return null;
	}
	public SequentialGroupAgent findDependingSequentialGroupByNames(
			String firstName, String lastName ) {
		return null;
	}

	public void addContentToGroup(Group parentGroup,
			GroupCreatorAndElementAdder creatorAndAdder) {
		throw new IllegalArgumentException(
				"An attempt to add content of a temporary gap.");
	}

	public String toString() {
		return "temporary gap \"" + gapDenotation + "\"";
	}

}
