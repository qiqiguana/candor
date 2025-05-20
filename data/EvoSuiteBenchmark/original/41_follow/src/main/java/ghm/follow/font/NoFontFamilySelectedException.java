package ghm.follow.font;

/** Indicates that no font family is currently selected */
public class NoFontFamilySelectedException extends InvalidFontException
{
	public NoFontFamilySelectedException(String msg)
	{
		super(msg);
	}
}