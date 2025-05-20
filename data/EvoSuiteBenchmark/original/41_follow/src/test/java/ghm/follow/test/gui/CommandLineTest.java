package ghm.follow.test.gui;

import ghm.follow.gui.Exit;
import ghm.follow.FollowApp;

import java.io.File;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommandLineTest extends AppLaunchingTestCase
{

	@Test
	public void testNoArgs() throws Exception
	{
		FollowApp.main(appendPropFileArg(null));
		doPostLaunch();
		// should be no file as we're opening a new instance (no history) with
		// no arguments (no requested files)
		assertEquals(true, app.getAttributes().getFollowedFiles().size() == 0);
	}

	@Test
	public void testOneArg() throws Exception
	{
		File temp = createTempFile();
		String[] args = new String[] { temp.toString() };
		args = appendPropFileArg(args);
		FollowApp.main(args);
		doPostLaunch();
		List<File> followedFiles = app.getAttributes().getFollowedFiles();
		assertTrue("Expecting 1 file to be followed.  Found " + followedFiles.size(), followedFiles
				.size() == 1);
		File followedFile = followedFiles.get(0);
		assertEquals("File found doesn't match expected file", temp, followedFile);
	}

	@Test
	public void testOneArgDuplicate() throws Exception
	{
		File temp = createTempFile();
		String[] args = new String[] { temp.toString() };
		args = appendPropFileArg(args);
		FollowApp.main(args);
		doPostLaunch();
		List<File> followedFiles = app.getAttributes().getFollowedFiles();
		assertTrue("Expecting 1 file to be followed.  Found " + followedFiles.size(), followedFiles
				.size() == 1);
		File followedFile = followedFiles.get(0);
		assertEquals("File found doesn't match expected file", temp, followedFile);
		invokeAction(app.getAction(Exit.NAME));
		while (!systemInterface.exitCalled())
		{
			Thread.sleep(250);
		}
		// reopen app with same file as argument
		FollowApp.main(args);
		doPostLaunch();
		followedFiles = app.getAttributes().getFollowedFiles();
		// should still be one because Follow shouldn't open the same file twice
		assertTrue("Expecting 1 file to be followed.  Found " + followedFiles.size(), followedFiles
				.size() == 1);
		// make sure followedFile is the expected file
		followedFile = (File) followedFiles.get(0);
		assertEquals(temp, followedFile);
	}

	@Test
	public void testOneArgReopen() throws Exception
	{
		File temp = createTempFile();
		String[] args = new String[] { temp.toString() };
		args = appendPropFileArg(args);
		FollowApp.main(args);
		doPostLaunch();
		List<File> followedFiles = app.getAttributes().getFollowedFiles();
		assertTrue("Expecting 1 file to be followed.  Found " + followedFiles.size(), followedFiles
				.size() == 1);
		File followedFile = followedFiles.get(0);
		assertEquals("File found doesn't match expected file", temp, followedFile);
		invokeAction(app.getAction(Exit.NAME));
		while (!systemInterface.exitCalled())
		{
			Thread.sleep(250);
		}
		// reopen app with same file as argument
		FollowApp.main(new String[0]);
		doPostLaunch();
		followedFiles = app.getAttributes().getFollowedFiles();
		// should still be one because Follow shouldn't open the same file twice
		assertTrue("Expecting 1 file to be followed.  Found " + followedFiles.size(), followedFiles
				.size() == 1);
		// make sure followedFile is the expected file
		followedFile = (File) followedFiles.get(0);
		assertEquals(temp, followedFile);
	}

	@Test
	public void testTwoArgs() throws Exception
	{
		File[] temp = new File[2];
		temp[0] = createTempFile();
		temp[1] = createTempFile();
		String[] args = new String[] { temp[0].toString(), temp[1].toString() };
		args = appendPropFileArg(args);
		FollowApp.main(args);
		doPostLaunch();
		List<File> followedFiles = app.getAttributes().getFollowedFiles();
		assertEquals(true, followedFiles.size() == 2);
		assertEquals(temp[0], followedFiles.get(0));
		assertEquals(temp[1], followedFiles.get(1));
	}

	@Test
	public void testDuplicateArgs() throws Exception
	{
		File temp = createTempFile();
		String[] args = new String[] { temp.toString(), createTempFile().toString(),
				temp.toString() };
		args = appendPropFileArg(args);
		FollowApp.main(args);
		doPostLaunch();
		List<File> followedFiles = app.getAttributes().getFollowedFiles();
		assertEquals(temp, followedFiles.get(0));
		assertEquals(true, followedFiles.size() == 2);
	}
}