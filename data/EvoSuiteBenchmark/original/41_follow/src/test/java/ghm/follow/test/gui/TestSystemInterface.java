package ghm.follow.test.gui;

import ghm.follow.systemInterface.SystemInterface;

import java.io.File;

public class TestSystemInterface implements SystemInterface
{
	private File fileFromUser;
	private boolean exitCalled;

	public File getFileFromUser()
	{
		return fileFromUser;
	}

	public void setFileFromUser(File file)
	{
		fileFromUser = file;
	}

	public void exit(int code)
	{
		exitCalled = true;
	}

	public boolean exitCalled()
	{
		return exitCalled;
	}

	public void reset()
	{
		exitCalled = false;
	}
}