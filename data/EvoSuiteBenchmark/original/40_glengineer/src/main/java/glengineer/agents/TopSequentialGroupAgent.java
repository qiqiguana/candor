package glengineer.agents;

import javax.swing.GroupLayout.SequentialGroup;

import glengineer.agents.setters.*;

/**
 * Агент, представляющий последовательную группу верхнего уровня.
 * <p>
 * Реализует интерфейс {@code FunctionsOnTopSequentialGroup}
 * добавления предпочитаемых контейнерных промежутков
 * в начало и в конец последовательной группы верхнего уровня.
 */
public class TopSequentialGroupAgent
		extends SequentialGroupAgent
{
	/**
	 * Агент, представляющий контейнерный промежуток в начале группы.
	 */
	private ContainerGapAgent precedingContainerGap;
	
	/**
	 * Агент, представляющий контейнерный промежуток в конце группы.
	 */
	private ContainerGapAgent followingContainerGap;
	
	/**
	 * Группирует представляемое содержимое в новую последовательную группу,
	 * добавляя, если надо, контейнерные промежутки в начале и в конце,
	 * и возвращает полученную группу.
	 * <p>
	 * Перегружает метод {@code TopSequentialGroupAgent.groupContent()},
	 * добавляя работу с контейнерными промежутками.
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
		if( precedingContainerGap != null )
			precedingContainerGap.addOneselfToGroup(result);
		for (Agent child : childrenAgents)
			child.addContentToGroup(result, creatorAndAdder);
		if( followingContainerGap != null )
			followingContainerGap.addOneselfToGroup(result);
		return result;
	}
	
	public FunctionsOnGroup getFunctionsOnGroupImplemenation()
	{
		return getFunctionsOnTopSequentialGroupImplemenation();
	}
	
	public FunctionsOnSequentialGroup
		getFunctionsOnSequentialGroupImplemenation()
	{
		return getFunctionsOnTopSequentialGroupImplemenation();
	}
	
	public FunctionsOnTopSequentialGroup
		getFunctionsOnTopSequentialGroupImplemenation()
	{
		return new FunctionsOnTopSequentialGroupImplemenation();
	}
	
	private class FunctionsOnTopSequentialGroupImplemenation
		extends SequentialGroupAgent.FunctionsOnSequentialGroupImplemenation
		implements FunctionsOnTopSequentialGroup
	{
		/**
		 * Добавляет контейнерный промежуток
		 * в начало последовательной группы верхнего уровня.
		 */
		public void addPrecedingContainerGap()
		{
			precedingContainerGap = new ContainerGapAgent();
		}
		/**
		 * Добавляет контейнерный промежуток с указанными параметрами размера
		 * в начало последовательной группы верхнего уровня.
		 */
		public void addPrecedingContainerGap(int pref, int max)
		{
			precedingContainerGap = new ContainerGapAgent(pref,max);
		}
		
		/**
		 * Добавляет контейнерный промежуток
		 * в конец последовательной группы верхнего уровня.
		 */
		public void addFollowingContainerGap()
		{
			followingContainerGap = new ContainerGapAgent();
		}
		/**
		 * Добавляет контейнерный промежуток с указанными параметрами размера
		 * в конец последовательной группы верхнего уровня.
		 */
		public void addFollowingContainerGap(int pref, int max)
		{
			followingContainerGap = new ContainerGapAgent(pref,max);
		}
		
		public void addBothContainerGaps()
		{
			addPrecedingContainerGap();
			addFollowingContainerGap();
		}
		public void addBothContainerGaps(int pref, int max)
		{
			addPrecedingContainerGap(pref,max);
			addFollowingContainerGap(pref,max);
		}
	}
	
}
