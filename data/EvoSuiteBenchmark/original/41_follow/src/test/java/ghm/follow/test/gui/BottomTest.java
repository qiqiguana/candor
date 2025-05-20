package ghm.follow.test.gui;

import ghm.follow.gui.Close;
import ghm.follow.gui.Open;
import ghm.follow.nav.Bottom;

import java.io.File;

import org.junit.Test;

import static org.junit.Assert.*;

public class BottomTest extends AppLaunchingTestCase
{

	@Test
	public void testEnabled() throws Exception
	{
		assertEquals(false, app.getAction(Bottom.NAME).isEnabled());
		File file = createTempFile();
		systemInterface.setFileFromUser(file);
		invokeAction(app.getAction(Open.NAME));
		assertEquals(true, app.getAction(Bottom.NAME).isEnabled());
		invokeAction(app.getAction(Close.NAME));
		assertEquals(false, app.getAction(Bottom.NAME).isEnabled());
	}
}