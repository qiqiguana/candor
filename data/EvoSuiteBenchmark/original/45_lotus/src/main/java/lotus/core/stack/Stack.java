package lotus.core.stack;

public class Stack extends java.util.Stack<StackObject>
{
	private static final long serialVersionUID = -8499031862020707793L;
	
	
	public void resolveLast()
	{
		StackObject o = this.pop();
		o.createEffect().resolve();
		//TODO : other types
		if(o instanceof Spell) ((Spell)o).card.isInStack = false;
	}
}
