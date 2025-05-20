package lotus.core.interfaces;

import lotus.core.decision.Decision;
import lotus.core.*;

public interface UserInterface
{
	public Decision getDecision();
	public void init(Player p);
	public void notifyOfChange();
}