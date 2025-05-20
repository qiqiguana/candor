package ghm.follow.systemInterface;

import ghm.follow.FollowApp;

import java.io.File;

/**
 * Various system calls are routed through an instance of this class; this enables test code to
 * interject itself where appropriate by assigning a different instance of SystemInterface to
 * {@link FollowApp}.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public interface SystemInterface
{

	/**
	 * Normally, this method should delegate to a file chooser or other appropriate file selection
	 * mechanism. However, it can be overriden by tests to return temporary files.
	 * 
	 * @return the File selected by the user
	 */
	public File getFileFromUser();

	/**
	 * Used in lieu of System.exit();
	 * 
	 * @param code
	 *            exit status code
	 */
	public void exit(int code);

}
