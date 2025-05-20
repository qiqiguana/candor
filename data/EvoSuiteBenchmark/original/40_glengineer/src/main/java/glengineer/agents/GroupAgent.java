package glengineer.agents;

import glengineer.GroupLayoutEngineer.TemporaryGapsToGaps;
import glengineer.agents.setters.FunctionsOnGroup;
import glengineer.agents.setters.FunctionsOnGroupAndElement;
import glengineer.agents.settings.Settings;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.GroupLayout.Group;


/**
 * The agent representing a group.
 */
public abstract class GroupAgent
		extends Agent
{
	protected LinkedList<Agent> childrenAgents = new LinkedList<Agent>();
	
	
	public GroupAgent()
	{
		settings = new Settings();
	}
	
	public String getFirstName()
	{
		if( childrenAgents.isEmpty() )  return null;
		String result;
		Iterator<Agent> i = childrenAgents.iterator();
		while( i.hasNext() ) {
			result = i.next().getFirstName();
			if( result != null )
				return result;
		}
		return null;
	}
	public String getLastName()
	{
		if( childrenAgents.isEmpty() )  return null;
		String result;
		Iterator<Agent> i = childrenAgents.descendingIterator();
		while( i.hasNext() ) {
			result = i.next().getLastName();
			if( result != null )
				return result;
		}
		return null;
	}
	
	public boolean isComponent(String componentName)
	{
		return false;
	}
	public boolean isGroup(String firstName, String lastName)
	{
		return getFirstName().equals(firstName) &&
					getLastName().equals(lastName);
	}
	public boolean isGap()
	{
		return false;
	}
	
	public boolean equals(Agent agent)
	{
		return  agent.isGroup( getFirstName(), getLastName() );
	}
	
	public void addAgent(Agent agent)
	{
		if( agent == null )
			throw new IllegalArgumentException("Cannot add null agents.");
		childrenAgents.add( agent );
	}
	
	public List<Agent> getChildren()
	{
		return Collections.unmodifiableList( childrenAgents );
	}
	
	public int getNumberOfChildren()
	{
		return childrenAgents.size();
	}
	
	/**
	 * Finds and returns the child of this group
	 * which is the component with the specified name.
	 * 
	 * @throws	IllegalArgumentException if not found.
	 */
	public ComponentAgent getComponent(String name)
	{
		for (Agent child : childrenAgents)
			if( child.isComponent(name) )
				return (ComponentAgent)child;
		throw new IllegalArgumentException(
				"component " + name +
				" in the " + this + " not found");
	}
	
	/**
	 * Finds and returns the child of this group
	 * which is the group with the specified first and last names.
	 * 
	 * @throws	IllegalArgumentException if not found.
	 */
	public GroupAgent getGroup(String firstName, String lastName)
	{
		for (Agent child : childrenAgents)
			if( child.isGroup(firstName,lastName) )
				return (GroupAgent)child;
		throw new IllegalArgumentException(
				"the group <" + firstName + "," + lastName +
				"> in the " + this + " not found");
	}
	
	/**
	 * Searches for the group agent with the specified first and last names
	 * among this agent and all agents of lower level (if this is a group).
	 * 
	 * @return	the agent found or null if not found.
	 */
	public ComponentAgent findDependingComponentByName(String componentName)
	{
		ComponentAgent result = null;
		for (Agent child : childrenAgents) {
			result = child.findDependingComponentByName(componentName);
			if( result != null )
				return result;
		}
		return null;
	}

	/**
	 * Searches for the group agent with the specified name
	 * among all agents of lower level.
	 * 
	 * @return	the agent found or null if not found.
	 */
	public GroupAgent findDependingGroupByNames(
			String firstName, String lastName )
	{
		if( isGroup(firstName,lastName) )
			return this;
		GroupAgent result = null;
		for (Agent child : childrenAgents) {
			result = child.findDependingGroupByNames(firstName,lastName);
			if( result != null )
				return result;
		}
		return null;
	}
	
	public ParallelGroupAgent findDependingParallelGroupByNames(
			String firstName, String lastName )
	{
		if( isGroup(firstName,lastName) &&
				this instanceof ParallelGroupAgent )
			return (ParallelGroupAgent)this;
		GroupAgent result = null;
		for (Agent child : childrenAgents) {
			result = child.findDependingParallelGroupByNames(
					firstName,lastName);
			if( result != null )
				return (ParallelGroupAgent)result;
		}
		return null;
	}
	
	public SequentialGroupAgent findDependingSequentialGroupByNames(
			String firstName, String lastName )
	{
		if( isGroup(firstName,lastName) &&
				this instanceof SequentialGroupAgent )
			return (SequentialGroupAgent)this;
		GroupAgent result = null;
		for (Agent child : childrenAgents) {
			result = child.findDependingSequentialGroupByNames(
					firstName,lastName);
			if( result != null )
				return (SequentialGroupAgent)result;
		}
		return null;
	}
	
	/**
	 * Gaps must affect sequential groups only.
	 * This method does all the job related to removing gaps from
	 * parallel groups in this group.
	 * In particular, it finds parallel groups which consist of gaps only
	 * and replaces these parallel groups by gaps of corresponding types.
	 * <p>
	 * More precisely:
	 * <ol>
	 * <li> Passes the invocation of this method to all subgroups.
	 * <li> Removes all gaps from every deeper parallel group
	 * containing not only gaps.
	 * <li> Replaces every deeper parallel group containing only gaps
	 * by the gap of that type.
	 * If such parallel group contains gaps of different type,
	 * throws a runtime exception.
	 * <li> Replaces every continuous sequence of gaps
	 * by a gap of that type.
	 * If such sequence contains gaps of different type,
	 * throws a runtime exception.
	 * <li> After all, if some subgroups have begun
	 * to consist of single elements,
	 * this method replaces them by those elements.
	 * </ol>
	 * 
	 * <p> Note that this method
	 * is called from the constructor of the {@code GroupLayoutEngineer},
	 * and hence the gaps added into the hierarchy manually
	 * will not be removed by this method.
	 */
	public void removeGapsFromParallelGroups()
	{
		passTheCallToSubgroups();
		
		removeGapsFromParallelSubgroupsWithOtherContent();
		
		replaceParallelSubgroupsWithGapsByTheirSingleGaps();
		
		replaceContinuousSequencesOfGapsBySingleGaps();
		
		replaceGroupsWithSingleElementsByThatElements();
	}
	
	private void passTheCallToSubgroups()
	{
		Iterator<Agent> i = childrenAgents.iterator();
		Agent child;
		while( i.hasNext() ) {
			child = i.next();
			if( child instanceof GroupAgent )
				((GroupAgent)child).removeGapsFromParallelGroups();
		}
	}
	
	/*
	 * Removes gaps from parallel subgroups containing not only gaps.
	 */
	private void removeGapsFromParallelSubgroupsWithOtherContent()
	{
		Iterator<Agent> i = childrenAgents.iterator();
		Agent child;
		ParallelGroupAgent parallel;
		while( i.hasNext() ) {
			child = i.next();
			if( child instanceof ParallelGroupAgent ) {
				parallel = (ParallelGroupAgent)child;
				if( ! parallel.containsGapsOnly() )
					parallel.removeAllGaps();
			}
		}
	}
	
	private void replaceParallelSubgroupsWithGapsByTheirSingleGaps()
	{
		ListIterator<Agent> li = childrenAgents.listIterator();
		Agent child;
		ParallelGroupAgent parallel;
		Agent gap;
		while( li.hasNext() ) {
			child = li.next();
			if( child instanceof ParallelGroupAgent ) {
				parallel = (ParallelGroupAgent)child;
				gap = parallel.getGapsType();
				if( gap != null ) //gaps found!
					li.set( gap ); //replace the group by its single gap.
			}
		}
	}
	
	private void replaceContinuousSequencesOfGapsBySingleGaps()
	{
		ListIterator<Agent> li = childrenAgents.listIterator();
		Agent child;
		Agent currentGap;
		Agent nextGap;
		while( li.hasNext() ) {
			child = li.next();
			if( ! child.isGap() )  continue;
			currentGap = child; //begins a sequence of gaps.
			//remove elements of the sequence one by one:
			while( li.hasNext() ) {
				nextGap = li.next();
				if( ! nextGap.isGap() )
					break; //the sequence of gaps is finished.
				if( nextGap.equals(currentGap) )
					li.remove();
				else
					throw new IllegalArgumentException(
							"A sequence of gaps possessing different types"
							+ " was found in a group." );
			}
		}
	}
	
	private void replaceGroupsWithSingleElementsByThatElements()
	{
		ListIterator<Agent> li = childrenAgents.listIterator();
		Agent child;
		GroupAgent nextSubgroup;
		while( li.hasNext() ) {
			child = li.next();
			if( child instanceof GroupAgent ) {
				nextSubgroup = (GroupAgent)child;
				if( nextSubgroup.getNumberOfChildren() == 1 )
					li.set( nextSubgroup.getChildren().get(0) );
			}
		}
	}
	
	
	/**
	 * Replaces all temporary gaps in this group and in all lower groups
	 * by the corresponding gaps.
	 * The correspondence is defined by the specified parameter {@code map}.
	 * <p>
	 * This method is (typically) called by the engineer object
	 * at the beginning of its {@code engineer()} method
	 * because at that point all user denotations for the gaps on the scheme
	 * are already associated with concrete gaps types by the user.
	 */
	public void replaceTemporaryGapsByRealGaps(TemporaryGapsToGaps map)
	{
		ListIterator<Agent> li = childrenAgents.listIterator();
		Agent child;
		while( li.hasNext() ) {
			child = li.next();
			if( child instanceof GroupAgent )
				((GroupAgent)child).replaceTemporaryGapsByRealGaps(map);
			if( child instanceof TemporaryGapAgent )
				li.set( map.gap( (TemporaryGapAgent)child ) );
		}
	}
	
	/**
	 * Добавляет всё представляемое содержимое в группу,
	 * используя для получения компонентов по их имеющимся названиям
	 * метод интерфейса {@code ComponentAdder}, а для создания
	 * новых подгрупп - методы интерфейса {@code GroupCreator}.
	 * <p>
	 * Вызывается группой-родителем.
	 * 
	 * @param parentGroup		группа, куда добавляется
	 * 							представляемое содержимое
	 * @param creatorAndAdder	интерфейс для создания групп
	 * 							и добавления в них компонентов
	 */
	public void addContentToGroup(
			Group parentGroup, GroupCreatorAndElementAdder creatorAndAdder )
	{
		creatorAndAdder.addGroupToGroup(
				groupContent(creatorAndAdder),
				settings,
				parentGroup );
	}

	/**
	 * Возвращает новую группу, составленную из представляемого содержимого.
	 * 
	 * @param creatorAndAdder	интерфейс для создания групп
	 * 							и добавления в них компонентов
	 * @return					группа, составленная из представляемого
	 * 							содержимого.
	 */
	public abstract Group groupContent(
			GroupCreatorAndElementAdder creatorAndAdder );
	
	/**
	 * Returns a class implementing the interface
	 * {@code FunctionsOnGroup} (more precisely, its extensions)
	 * which allows the end user to possess access
	 * to the settings of elements of this group.
	 */
	public abstract FunctionsOnGroup getFunctionsOnGroupImplemenation();
	
	/**
	 * This is a base class for two classes
	 * which provide the end user by methods
	 * of controlling settings of elements of groups
	 * and methods of inserting gaps beside elements of groups.
	 * <p>
	 * For this purpose, this class declares the fields
	 * {@code parentGroupAgent} and {@code elementAgent}
	 * which specify the group and its element.
	 * These fields are then used in methods of this class' extensions.
	 * <p>
	 * This class also implements methods of inserting non-preferred gaps
	 * beside the element {@code parentGroupAgent}
	 * of the group {@code elementAgent}.
	 * 
	 * @see
	 * ParallelGroupAgent.FunctionsOnParallelGroupAndElementImplementation
	 * @see
	 * SequentialGroupAgent.FunctionsOnSequentialGroupAndElementImplementation
	 */
	protected static class FunctionsOnGroupAndElementImplementation
		implements FunctionsOnGroupAndElement
	{
		/**
		 * A parent group containing some element
		 * which settings with respect to the parent group will be changed.
		 */
		protected GroupAgent parentGroupAgent;
		
		/**
		 * An element in the parent group
		 * which settings with respect to the parent group will be changed.
		 */
		protected Agent elementAgent;
		
		public FunctionsOnGroupAndElementImplementation(
				GroupAgent parent, Agent element )
		{
			this.parentGroupAgent = parent;
			this.elementAgent = element;
		}
		
		/**
         * Adds a rigid gap of the specified size
         * to the {@code parentGroupAgent} before its {@code elementAgent}.
         */
		public void addPrecedingGap(int size)
		{
			parentGroupAgent.addGapBefore( new GapAgent(size), elementAgent );
		}
		
		/**
         * Adds a gap of the specified sizes
         * to the {@code parentGroupAgent} before its {@code elementAgent}.
         */
		public void addPrecedingGap(int min, int pref, int max)
		{
			parentGroupAgent.addGapBefore(
					new GapAgent(min,pref,max), elementAgent );
		}
		
		/**
         * Adds a rigid gap of the specified size
         * to the {@code parentGroupAgent} after its {@code elementAgent}.
         */
		public void addFollowingGap(int size)
		{
			parentGroupAgent.addGapAfter( new GapAgent(size), elementAgent );
		}
		
		/**
         * Adds a gap of the specified sizes
         * to the {@code parentGroupAgent} after its {@code elementAgent}.
         */
		public void addFollowingGap(int min, int pref, int max)
		{
			parentGroupAgent.addGapAfter(
					new GapAgent(min,pref,max), elementAgent );
		}
		
	}
	
	
	/**
	 * Finds the specified element in the list of this group's children
	 * and inserts the specified gap into that list before that element.
	 */
	public void addGapBefore(GapAgent gap, Agent element)
	{
		if( childrenAgents.isEmpty() ) {
			childrenAgents.add(gap);
			return;
		}
		ListIterator<Agent> iter = childrenAgents.listIterator();
		while( iter.hasNext() )
			if( iter.next().equals(element)) {
				iter.previous();
				iter.add(gap);
				return;
			}
		throw new IllegalArgumentException(
				"the " + element.toString() +
				" in the " + toString() + " not found" );
	}
	
	/**
	 * Finds the specified element in the list of this group's children
	 * and inserts the specified gap into that list after that element.
	 */
	public void addGapAfter(GapAgent gap, Agent element)
	{
		if( childrenAgents.isEmpty() ) {
			childrenAgents.add(gap);
			return;
		}
		ListIterator<Agent> iter = childrenAgents.listIterator();
		while( iter.hasNext() )
			if( iter.next().equals(element)) {
				iter.add(gap);
				return;
			}
		throw new IllegalArgumentException(
				"the " + element.toString() +
				" in the " + toString() + " not found" );
	}
	
}
