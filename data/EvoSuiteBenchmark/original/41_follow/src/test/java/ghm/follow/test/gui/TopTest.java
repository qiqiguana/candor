package ghm.follow.test.gui;

import org.junit.Test;

import ghm.follow.nav.Top;

import static org.junit.Assert.*;

public class TopTest extends AppLaunchingTestCase
{

	@Test
	public void testTop()
	{
		assertEquals(false, app.getAction(Top.NAME).isEnabled());
	}
}