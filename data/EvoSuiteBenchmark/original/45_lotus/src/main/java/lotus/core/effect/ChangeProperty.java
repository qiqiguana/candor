package lotus.core.effect;

import lotus.core.card.*;
import java.lang.reflect.*;
public class ChangeProperty extends Effect
{
	Card card;
	String property;
	Object value;
	
	public ChangeProperty(Card c, String property, Object value)
	{
		card = c;
		this.property = property;
		this.value = value;
	}
	
	//TODO : jar files
	@SuppressWarnings("unchecked")
	@Override
	public void resolve()
	{
		try
		{
			for(Class c : Card.subclasses)
			{
				if(c.isInstance(card))
				{
					Field[] fields = c.getFields();
					for(Field f : fields)
					{
						if(f.getName() == property)
						{
							f.set(card,value);
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			lotus.core.Error.Report(e);
		}
	}
}