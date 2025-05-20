package ghm.follow.test;

import java.util.Date;
import java.util.Random;

import javax.swing.JTextPane;

import org.junit.Before;
import org.junit.Test;

import ghm.follow.io.JTextComponentDestination;
import ghm.follow.io.OutputDestination;

import static org.junit.Assert.*;

public class FileFollowerTest extends BaseTestCase
{

	@Before
	public void setUp() throws Exception
	{
		super.setUp();
		testination = new Testination();
		follower.addOutputDestination(testination);
	}

	@Test
	public void textShouldMatchInputWhenAddingAfterStart() throws Exception
	{
		follower.start();
		String control = "control";
		writeToFollowedFileAndWait(control);
		assertEquals(control, testination.strBuf.toString());
		String control2 = "control2";
		writeToFollowedFileAndWait(control2);
		assertEquals(control + control2, testination.strBuf.toString());
	}

	@Test
	public void testShouldMatchInputWhenLatencyIsShort() throws Exception
	{
		follower.setLatency(100);
		follower.start();
		String control = "control";
		writeToFollowedFileAndWait(control);
		assertEquals(control, testination.strBuf.toString());
	}

	@Test
	public void textShouldMatchInputWhenBufferSizeIsSmall() throws Exception
	{
		int bufferSize = 10;
		follower.setBufferSize(bufferSize);
		follower.start();
		String control = "32098jaspfj234-08uewrfiojsad;lfkjqw4poiru2340ruwefkjasd;lkjq2po43iu123-4r098uasdfl;asdclkjasdfasdf9834roaerf";
		String subcontrol = control.substring(control.length() - bufferSize);
		// initial read of the 'file' will only contain as many characters as
		// the buffer size
		writeToFollowedFileAndWait(control);
		assertEquals(subcontrol, testination.strBuf.toString());
		// subsequent reads of the 'file' will contain all previous characters
		// and any newly added ones
		writeToFollowedFileAndWait(control);
		assertEquals(subcontrol + control, testination.strBuf.toString());
	}

	@Test
	public void textInMultipleDestinationsShouldMatch() throws Exception
	{
		Testination testination2 = new Testination();
		follower.addOutputDestination(testination2);
		follower.start();
		String control = "control";
		writeToFollowedFileAndWait(control);
		assertEquals(control, testination.strBuf.toString());
		assertEquals(control, testination2.strBuf.toString());
	}

	@Test
	public void testJTextComponentDestinationFor2Minutes() throws Exception
	{
		JTextPane textPane = new JTextPane();
		JTextComponentDestination dest = new JTextComponentDestination(textPane, true);
		follower.addOutputDestination(dest);
		follower.start();
		Date start = new Date();
		long end = start.getTime() + (2 * 1000 * 60);
		Random rand = new Random();
		while (end > new Date().getTime())
		{
			StringBuffer sb = new StringBuffer();
			double length = rand.nextDouble() * 100;
			while (length > 0)
			{
				sb.append((char) (length-- % 26));
				// if (rand.nextDouble() >= .8) {
				// sb = new StringBuffer();
				// dest.clear();
				// // clearFollowedFile();
				// break;
				// }
			}
			if (sb.length() > 0 && rand.nextBoolean())
			{
				sb.append("\n");
			}
			writeToFollowedFileAndWait(sb.toString());
			assertEquals(sb.toString().length(), dest.getJTextComponent().getDocument().getLength());
		}
	}

	private Testination testination;

	class Testination implements OutputDestination
	{
		public void print(String s)
		{
			strBuf.append(s);
		}

		public void clear()
		{
			strBuf.delete(0, strBuf.length());
		}

		StringBuffer strBuf = new StringBuffer();
	}

}
