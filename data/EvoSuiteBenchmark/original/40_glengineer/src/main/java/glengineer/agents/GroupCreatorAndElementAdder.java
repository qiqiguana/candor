package glengineer.agents;

import glengineer.agents.settings.ComponentSettings;
import glengineer.agents.settings.ParallelGroupSettings;
import glengineer.agents.settings.Settings;

import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

/**
 * The interface declares a number of methods used by the agents
 * but belonging to (an inner class in) the {@code GroupLayoutEngineer},
 * which has access to the methods of the {@code GroupLayout}. 
 */
public interface GroupCreatorAndElementAdder
{
	/**
	 * Creates and returns a new {@code SequentialGroup}.
	 */
	public SequentialGroup createSequentialGroup();
	
	/**
	 * Creates and returns a new {@code ParallelGroup}
	 * with the specified settings.
	 */
	public ParallelGroup createParallelGroup(
			ParallelGroupSettings settings );
	
	/**
	 * Adds the component having the specified {@code compName}
	 * to the specified {@code group},
	 * applies to it the specified {@code settings}.
	 */
	public void addComponentToGroup(
			String compName, Group group, ComponentSettings settings );
	
	/**
	 * Adds the specified {@code group} with the specified {@code settings}
	 * to another group {@code parentGroup}.
	 */
	public void addGroupToGroup(
			Group group, Settings settings, Group parentGroup );
}
