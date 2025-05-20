package fi.vtt.probeframework.javaclient.protocol;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fi.vtt.probeframework.javaclient.protocol.messages.InputBuilder;
import fi.vtt.probeframework.javaclient.protocol.messages.Precision;
import fi.vtt.probeframework.javaclient.api.probe.PFTest;

public class InputTest extends BaseTest {
  private InputBuilder builder = null;

  @Before
  public void setup() {
    PFTest test = new PFTest(null, null, null, Precision.MILLIS, null, null);
    builder = new InputBuilder(test);
  }
  
  @Test
  public void intInput1() {
    byte[] msg = builder.input1Msg(1, 0xcafe);
    byte[] expected = new byte[] {0x0a, 0x0, 0x01, 0x06, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, (byte)0xca, (byte)0xfe, 0x0, 0x0, 0x0, 0x0};
    assertArrayEqualsIgnoreTime(expected, msg);
  }

  @Test
  public void booleanInput1() {
    byte[] msg = builder.input1Msg(2, true);
    byte[] expected = new byte[] {0x0a, 0x0, 0x02, 0x01, 0x01, 0x0, 0x0, 0x0, 0x0};
    assertArrayEqualsIgnoreTime(expected, msg);

    msg = builder.input1Msg(3, false);
    expected[2] = 3;
    expected[4] = 0;
    assertArrayEqualsIgnoreTime(expected, msg);
  }

  @Test
  public void textInput1() {
    byte[] msg = builder.input1Msg(5, "moro");
    byte[] expected = new byte[] {0x0a, 0x0, 0x05, 0x08, 0x00, 0x4, 0x6d, 0x6f, 0x72, 0x6f, 0x0, 0x0, 0x0, 0x0};
    assertArrayEqualsIgnoreTime(expected, msg);
    
    msg = builder.input1Msg(6, textFor2Bytes);
    //length is now described in 2 bytes, first byte containing value 2
    assertEquals(2, msg[4]);
    //total length is 760, remaining value after 2*256 is in second byte
    assertEquals((byte)760%256, (byte)msg[5]);
  }
  
  @Test
  public void byteInput1() {
    byte[] data = new byte[] {0xc, 0xa, 0xf, 0xe, (byte)0xc0, (byte)0xa0, (byte)0xc0, (byte)0xe0};
    byte[] msg = builder.input1Msg(6, data);
    byte[] expected = new byte[] {0x0a, 0x0, 0x6, 0x9, 0x0, 0x8, 
        0xc, 0xa, 0xf, 0xe, (byte)0xc0, (byte)0xa0, (byte)0xc0, (byte)0xe0,
        0x0, 0x0, 0x0, 0x0};
    assertArrayEqualsIgnoreTime(expected, msg);    
  }
}
