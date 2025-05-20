package fi.vtt.probeframework.javaclient.protocol;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import fi.vtt.probeframework.javaclient.protocol.messages.OutputType;
import fi.vtt.probeframework.javaclient.protocol.messages.OutputTypeBuilder;
import fi.vtt.probeframework.javaclient.protocol.messages.Precision;
import fi.vtt.probeframework.javaclient.api.probe.PFTest;

public class OutputTypeTest {
  @Test
  public void outputType() throws Exception {
    PFTest test = new PFTest(null, null, null, Precision.MILLIS, null, null);
    OutputTypeBuilder builder = new OutputTypeBuilder(test);
    byte[] msg = builder.outputTypeMsg(OutputType.BINARY, 1, "test-type");
    byte[] expected = new byte[] {0x10, 0x1, 0x9, 0x74, 0x65, 0x73, 0x74, 0x2d, 0x74, 0x79, 0x70, 0x65, 0x9};
    assertArrayEquals(expected, msg);

    msg = builder.outputTypeMsg(OutputType.INTEGER, 5, "int-type");
    expected = new byte[] {0x10, 0x5, 0x8, 0x69, 0x6e, 0x74, 0x2d, 0x74, 0x79, 0x70, 0x65, 0x6};
    assertArrayEquals(expected, msg);
  }

  @After
  public void teardown() {
  }
}
