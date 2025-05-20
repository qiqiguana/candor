package glengineer.agents;

import java.util.ListIterator;

import glengineer.agents.setters.*;

import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout.*;

/**
 * Агент, представляющий последовательную группу.
 */
public class SequentialGroupAgent
	extends GroupAgent
{
	/**
	 * Группирует представляемое содержимое в новую последовательную группу
	 * и возвращает её.
	 * 
	 * @param creatorAndAdder	интерфейс для создания групп
	 * 							и добавления в них компонентов
	 * @return					группа, составленная из представляемого
	 * 							содержимого.
	 */
	public SequentialGroup groupContent(
			GroupCreatorAndElementAdder creatorAndAdder )
	{
		SequentialGroup result = creatorAndAdder.createSequentialGroup();
		for (Agent child : childrenAgents)
			child.addContentToGroup(result, creatorAndAdder);
		return result;
	}
	
	
	public FunctionsOnGroup getFunctionsOnGroupImplemenation()
	{
		return getFunctionsOnSequentialGroupImplemenation();
	}
	
	public FunctionsOnSequentialGroup
		getFunctionsOnSequentialGroupImplemenation()
	{
		return new FunctionsOnSequentialGroupImplemenation();
	}
	
	protected class FunctionsOnSequentialGroupImplemenation
		implements FunctionsOnSequentialGroup
	{
		public FunctionsOnSequentialGroupAndElement
				getComponent(String name)
		{
			return new FunctionsOnSequentialGroupAndElementImplementation(
					SequentialGroupAgent.this,
					SequentialGroupAgent.this.getComponent(name) );
		}
		
		public FunctionsOnSequentialGroupAndElement
			getGroup( String firstName, String lastName )
		{
			return new FunctionsOnSequentialGroupAndElementImplementation(
					SequentialGroupAgent.this,
					SequentialGroupAgent.this.getGroup(firstName, lastName) );
		}
	}
	
	private static class FunctionsOnSequentialGroupAndElementImplementation
		extends GroupAgent.FunctionsOnGroupAndElementImplementation
		implements FunctionsOnSequentialGroupAndElement
	{
		public FunctionsOnSequentialGroupAndElementImplementation(
				GroupAgent parent, Agent element )
		{
			super(parent,element);
		}
		
		private SequentialGroupAgent getParentGroupAgent()
		{
			return (SequentialGroupAgent)parentGroupAgent;
		}
		
		/**Добавляет перед элементом родительской группы
		 * "связанный" промежуток.*/
		public void addPrecedingRelatedGap()
		{
			addPrecedingRelatedGap(
					GroupLayout.DEFAULT_SIZE,
					GroupLayout.DEFAULT_SIZE );
		}
		/**Добавляет перед элементом родительской группы
		 * "несвязанный" промежуток.*/
		public void addPrecedingUnrelatedGap()
		{
			addPrecedingUnrelatedGap(
					GroupLayout.DEFAULT_SIZE,
					GroupLayout.DEFAULT_SIZE );
		}

		/**Добавляет после элемента родительской группы
		 * "связанный" промежуток.*/
		public void addFollowingRelatedGap()
		{
			addFollowingRelatedGap(
					GroupLayout.DEFAULT_SIZE,
					GroupLayout.DEFAULT_SIZE );
		}
		/**Добавляет после элемента родительской группы
		 * "несвязанный" промежуток.*/
		public void addFollowingUnrelatedGap()
		{
			addFollowingUnrelatedGap(
					GroupLayout.DEFAULT_SIZE,
					GroupLayout.DEFAULT_SIZE );
		}
		
		/**Добавляет в родительскую группу перед её элементом
		 * "связанный" промежуток и дополнительно определяет для него
		 * предпочитаемый и максимальный размеры.*/
		public void addPrecedingRelatedGap(int pref, int max)
		{
			getParentGroupAgent()
				.addPreferredGapBefore(
					new PreferredGapAgent(
						LayoutStyle.ComponentPlacement.RELATED, pref, max ),
					elementAgent );
		}
		/**Добавляет в родительскую группу перед её элементом
		 * "несвязанный" промежуток и дополнительно определяет для него
		 * предпочитаемый и максимальный размеры.*/
		public void addPrecedingUnrelatedGap(int pref, int max)
		{
			getParentGroupAgent()
				.addPreferredGapBefore(
					new PreferredGapAgent(
						LayoutStyle.ComponentPlacement.UNRELATED, pref, max ),
					elementAgent );
		}
		
		/**Добавляет в родительскую группу после её элемента
		 * "связанный" промежуток и дополнительно определяет для него
		 * предпочитаемый и максимальный размеры.*/
		public void addFollowingRelatedGap(int pref, int max)
		{
			getParentGroupAgent()
				.addPreferredGapAfter(
					new PreferredGapAgent(
						LayoutStyle.ComponentPlacement.RELATED, pref, max ),
					elementAgent );
		}
		/**Добавляет в родительскую группу после её элемента
		 * "несвязанный" промежуток и дополнительно определяет для него
		 * предпочитаемый и максимальный размеры.*/
		public void addFollowingUnrelatedGap(int pref, int max)
		{
			getParentGroupAgent()
				.addPreferredGapAfter(
					new PreferredGapAgent(
						LayoutStyle.ComponentPlacement.UNRELATED, pref, max ),
					elementAgent );
		}
	}
	
	/**
	 * Finds the specified element in the list of
	 * this sequential group's children and inserts
	 * the specified preferred gap into that list before that element.
	 */
	public void addPreferredGapBefore(PreferredGapAgent gap, Agent element)
	{
		if( childrenAgents.isEmpty() ) {
			childrenAgents.add(gap);
			return;
		}
		ListIterator<Agent> iter = childrenAgents.listIterator();
		while ( iter.hasNext() )
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
	 * Finds the specified element in the list of
	 * this sequential group's children and inserts
	 * the specified preferred gap into that list after that element.
	 */
	public void addPreferredGapAfter(PreferredGapAgent gap, Agent element)
	{
		if( childrenAgents.isEmpty() ) {
			childrenAgents.add(gap);
			return;
		}
		ListIterator<Agent> iter = childrenAgents.listIterator();
		while ( iter.hasNext() )
			if( iter.next().equals(element)) {
				iter.add(gap);
				return;
			}
		throw new IllegalArgumentException(
				"the " + element.toString() +
				" in the " + toString() + " not found" );
	}
	
	public String toString()
	{
		return "sequential group <" +
			getFirstName() + "," + getLastName() + ">";
	}
}
