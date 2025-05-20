package ghm.follow;

public class InvalidVkException extends Exception
{
  public InvalidVkException()
  {
    super();
  }

  public InvalidVkException(String message)
  {
    super(message);
  }

  public InvalidVkException(Throwable cause)
  {
    super(cause);
  }

  public InvalidVkException(String message, Throwable cause)
  {
    super(message, cause);
  }
}