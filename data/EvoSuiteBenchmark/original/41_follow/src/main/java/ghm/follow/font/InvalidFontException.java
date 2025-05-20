package ghm.follow.font;

/** Indicates that an invalid font is currently specified */
public class InvalidFontException extends Exception
{
	public InvalidFontException(String msg)
	{
		super(msg);
	}
}