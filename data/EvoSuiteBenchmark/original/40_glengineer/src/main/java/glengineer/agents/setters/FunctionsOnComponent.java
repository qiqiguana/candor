package glengineer.agents.setters;

/**
 * Declares methods which allow the user to control
 * the sizes of a component.
 */
public interface FunctionsOnComponent
{
	public void setSize(int size);
	
	public void setSize(int min, int pref, int max);
	
}
