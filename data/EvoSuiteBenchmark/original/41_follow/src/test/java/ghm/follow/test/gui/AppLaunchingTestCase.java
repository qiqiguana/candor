package ghm.follow.test.gui;

import java.io.File;

import ghm.follow.config.FollowAppAttributes;
import ghm.follow.gui.Exit;
import ghm.follow.FollowApp;
import ghm.follow.test.BaseTestCase;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.Before;

public abstract class AppLaunchingTestCase extends BaseTestCase
{

	protected FollowApp app;
	protected TestSystemInterface systemInterface;
	protected String propertyFileName;

	@Before
	public void setUp() throws Exception
	{
		String[] args = appendPropFileArg(null);
		FollowApp.main(args);
		doPostLaunch();
	}

	protected void doPostLaunch() throws Exception
	{
		app = FollowApp.getInstance();
		systemInterface = new TestSystemInterface();
		app.setSystemInterface(systemInterface);
	}

	@After
	public void tearDown() throws Exception
	{
		invokeAction(app.getAction(Exit.NAME));
		while (!systemInterface.exitCalled())
		{
			Thread.sleep(250);
		}
		File propFile = new File(propertyFileName);
		propFile.delete();
	}

	protected void invokeAndWait(Runnable runnable)
	{
		try
		{
			SwingUtilities.invokeAndWait(runnable);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}

	protected void invokeAction(final Action action)
	{
		invokeAndWait(new Runnable()
		{
			public void run()
			{
				action.actionPerformed(null);
			}
		});
	}

	protected String[] appendPropFileArg(String[] argv)
	{
		propertyFileName = System.getProperty("java.io.tmpdir")
				+ System.getProperty("file.separator") + FollowAppAttributes.PROPERTY_FILE_NAME;
		int length = ((argv != null) ? argv.length : 0) + 2;
		String[] args = new String[length];
		for (int i = 0; i < args.length - 2; i++)
		{
			args[i] = argv[i];
		}
		args[length - 2] = "-propFile";
		args[length - 1] = propertyFileName;
		return args;
	}
}