package ghm.follow.test;

import ghm.follow.FileFollower;
import ghm.follow.io.JTextComponentDestination;
import ghm.follow.io.OutputDestination;

import javax.swing.JTextArea;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JTextComponentDestinationTest extends BaseTestCase
{

	@Before
	public void setUp() throws Exception
	{
		super.setUp();
		jTextArea = new JTextArea();
	}

	@Test
	public void testNoAutoscroll() throws Exception
	{
		validateTextContentAndCaretPos(new JTextComponentDestination(jTextArea, false));
	}

	@Test
	public void testAutoscroll() throws Exception
	{
		validateTextContentAndCaretPos(new JTextComponentDestination(jTextArea, true));
	}

	private void validateTextContentAndCaretPos(JTextComponentDestination destination)
			throws Exception
	{
		follower = new FileFollower(followedFile, new OutputDestination[] { destination });
		follower.start();
		String control = "control";
		writeToFollowedFileAndWait(control);
		assertEquals(control, jTextArea.getText());
		validateCaretPosition(destination);
		String control2 = "control2";
		writeToFollowedFileAndWait(control2);
		assertEquals(control + control2, jTextArea.getText());
		validateCaretPosition(destination);
		String control3 = "\n\n230usadlkfjasd;lkfjas\nl;kasdjf;lkajsdf\n\n";
		writeToFollowedFileAndWait(control3);
		assertEquals(control + control2 + control3, jTextArea.getText());
		validateCaretPosition(destination);
	}

	private void validateCaretPosition(JTextComponentDestination destination)
	{
		int caretPos = destination.getJTextComponent().getCaret().getMark();
		if (destination.autoPositionCaret())
		{
			assertEquals(destination.getJTextComponent().getText().length(), caretPos);
		}
		else
		{
			assertEquals(0, caretPos);
		}
	}

	private JTextArea jTextArea;
}