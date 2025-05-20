package fi.vtt.probeframework.javaclient.protocol;

import org.junit.Test;
import fi.vtt.probeframework.javaclient.api.probe.PFTest;
import fi.vtt.probeframework.javaclient.protocol.messages.Precision;

import static junit.framework.Assert.*;

/**
 * @author Teemu Kanstrén
 */
public class TimeTest {
  @Test
  public void seconds() throws Exception {
    PFTest test = new PFTest("project", null, null, Precision.SECOND, "testname", "testsuite");
    Thread.sleep(1000);
    long delta = test.timeDelta();
//    assertTrue("delta in seconds should be between 50-150", 50 < delta);
    assertEquals("delta in seconds", 1, delta);
  }
}
