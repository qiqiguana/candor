package ghm.follow.font;

/** Indicates that no font size is currently specified */
public class NoFontSizeSpecifiedException extends InvalidFontException
{
	public NoFontSizeSpecifiedException(String msg)
	{
		super(msg);
	}
}