package fi.vtt.probeframework.javaclient.protocol;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import fi.vtt.probeframework.javaclient.protocol.messages.OutputBuilder;
import fi.vtt.probeframework.javaclient.protocol.messages.Precision;
import fi.vtt.probeframework.javaclient.api.probe.PFTest;

public class OutputTest extends BaseTest {
  //TODO kommunikaatio komponentti, sinne starttime, packetid, jne
  private OutputBuilder builder = null;

  @Before
  public void setup() {
    PFTest test = new PFTest(null, null, null, Precision.MILLIS, null, null);
    builder = new OutputBuilder(test);
    //State.reset();
  }
  
  @Test
  public void intOutput() throws Exception {
    byte[] msg = builder.output1Msg(1, (byte)0x1, 0xfaced);
    byte[] expected = new byte[] {0x11, 0x0, 0x1, 0x1, 0x0, 0x0, 0x0, 0x0, 0x0, 0xf, (byte)0xac, (byte)0xed, 0x0, 0x0, 0x0, 0x0};
    assertArrayEqualsIgnoreTime(expected, msg);
  }

  @Test
  public void booleanOutput() throws Exception {
    byte[] msg = builder.output1Msg(5, (byte)0x2, true);
    byte[] expected = new byte[] {0x11, 0x0, 0x5, 0x2, 0x1, 0x0, 0x0, 0x0, 0x0};
    assertArrayEqualsIgnoreTime(expected, msg);
  }

  @Test
  public void textOutput() throws Exception {
    byte[] msg = builder.output1Msg(7, (byte)0x3, textFor2Bytes);
    assertEquals(760+10, msg.length);
    assertEquals(0x11, msg[0]);
    assertEquals(0x7, msg[2]);
//    System.out.println(hex(msg));
    //length is now described in 2 bytes, first byte containing value 2
    assertEquals(2, msg[4]);
    //total length is 760, remaining value after 2*256 is in second byte
    assertEquals((byte)760%256, (byte)msg[5]);
  }

  @Test
  public void byteOutput() throws Exception {
    byte[] data = new byte[] {(byte)0xca, (byte)0xce, (byte)0xfa, (byte)0xce};
    byte[] msg = builder.output1Msg(0xffffff, (byte)0x4, data);
    byte[] expected = new byte[] {0x11, (byte)0xff, (byte)0xff, 0x4, 0x0, 0x4, (byte)0xca, (byte)0xce, (byte)0xfa, (byte)0xce, 0x0, 0x0, 0x0, 0x0};
    assertArrayEqualsIgnoreTime(expected, msg);
  }
}
