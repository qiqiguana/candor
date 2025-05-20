package ghm.follow.systemInterface;

import ghm.follow.FollowApp;

import java.awt.Cursor;
import java.io.File;
import javax.swing.JFileChooser;

/**
 * Default implementation of {@link SystemInterface} for the Follow application.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class DefaultSystemInterface implements SystemInterface
{

	public DefaultSystemInterface(FollowApp app)
	{
		this.app = app;
	}

	public File getFileFromUser()
	{
		app.setCursor(Cursor.WAIT_CURSOR);
		JFileChooser chooser = new JFileChooser(app.getAttributes().getLastFileChooserDirectory());
		app.setCursor(Cursor.DEFAULT_CURSOR);
		int returnVal = chooser.showOpenDialog(app.getFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			return chooser.getSelectedFile();
		}
		return null;
	}

	public void exit(int code)
	{
		System.exit(code);
	}

	protected FollowApp app;
}
