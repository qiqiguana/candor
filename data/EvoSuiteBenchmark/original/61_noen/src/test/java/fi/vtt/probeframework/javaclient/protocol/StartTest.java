package fi.vtt.probeframework.javaclient.protocol;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fi.vtt.probeframework.javaclient.protocol.messages.Precision;
import fi.vtt.probeframework.javaclient.protocol.messages.StartBuilder;
import fi.vtt.probeframework.javaclient.api.probe.PFTest;

public class StartTest extends BaseTest {
  private String textFor2Bytes =
          "Alice is sitting by her sister lazily and became a bit tired, " +
                  "and she sees a White Rabbit in a waistcoat carrying a watch. " +
                  "She follows it down a rabbit hole, and falls down a very long chamber " +
                  "full of strange things on shelves. After landing safely on the ground, " +
                  "she goes into a long hallway with a glass table with a gold key. " +
                  "Alice opens up a curtain and finds a small door, which the key fits in perfectly, " +
                  "and behind it is a beautiful garden, but she can't fit in. " +
                  "Alice then finds a small bottle labeled 'DRINK ME,' and drinks it. " +
                  "The drink causes her to shrink. Alice accidentally leaves the key on the table, " +
                  "and with her diminished stature can no longer reach it and becomes very scared. " +
                  "She then sees a cake that says 'EAT ME,' and proceeds to eat it";

  @Before
  public void setup() {
  }

  @Test
  public void startMsgCreate() throws Exception {
    PFTest test = new PFTest(null, null, null, Precision.MILLIS, null, "short");
    byte[] msg = new StartBuilder(test).startMsg();
    byte[] expected = new byte[]{(byte) 0xff, (byte) 0xff, 0x2, 0x0, 0x0, 0x0, 0x0, 0x5, 0x73, 0x68, 0x6f, 0x72, 0x74, 0x2, 0, 0, 0, 0, 0, 0, 0};
    assertArrayEqualsIgnoreLastBytes(expected, msg, 7);

    test = new PFTest(null, null, null, Precision.MILLIS, null, textFor2Bytes);
    msg = new StartBuilder(test).startMsg();
//    System.out.println("msglen:"+textFor2Bytes.length());
//    System.out.println(hex(msg));
    assertEquals((byte) 0xFF, msg[0]);
    assertEquals((byte) 0xFF, msg[1]);
    assertEquals((byte) 0xFF, msg[7]);
    assertEquals(0x2, msg[2]);
    assertEquals(0x2, msg[msg.length - 8]);
    assertTrue(msg[msg.length - 7] < 5);
    assertEquals(271, msg.length);
  }

  @Test
  public void projectandTestName() throws Exception {
    PFTest test = new PFTest("short", null, null, Precision.MILLIS, "test", "");
    byte[] msg = new StartBuilder(test).startMsg();
//    System.out.println(hex(msg));
    byte[] expected = new byte[]{(byte) 0xff, (byte) 0xff, 0x2, 0x5, 0x73, 0x68, 0x6f, 0x72, 0x74, 0x0, 0x4, 0x74, 0x65, 0x73, 0x74, 0x0, 0x0, 0x2, 0, 0, 0, 0, 0, 0, 0};
    assertArrayEqualsIgnoreLastBytes(expected, msg, 7);

    test = new PFTest(textFor2Bytes, null, null, Precision.MILLIS, null, null);
    msg = new StartBuilder(test).startMsg();
//    System.out.println("msglen:"+textFor2Bytes.length());
//    System.out.println(hex(msg));
    assertEquals((byte) 0xFF, msg[0]);
    assertEquals((byte) 0xFF, msg[1]);
    assertEquals((byte) 0xFF, msg[3]);
    //protocol version
    assertEquals(0x2, msg[2]);
    //millisecond precision
    assertEquals(0x2, msg[msg.length - 8]);
    //time under threshold
    assertTrue(msg[msg.length - 7] < 5);
    assertEquals(271, msg.length);
  }

  @Test
  public void longToBytes() {
    byte[] expected = {(byte) 0xef, (byte) 0xff, (byte) 0xff, (byte) 0xff};
    byte[] actual = new byte[4];
    new StartBuilder(null).longToBytes(0xefffffff, actual, 0, 4);
//    System.out.println("---------");
//    System.out.println(hex(actual));
    assertArrayEquals(expected, actual);
  }

  @Test
  public synchronized void setTime() throws Exception {
    Configuration.precision = Precision.MILLIS;
    byte[] time = new byte[4];
    PFTest test = new PFTest(null, null, null, Precision.MILLIS, null, null);
    new StartBuilder(test).setTime(time, 0);
    assertEquals(0, time[0]);
    assertEquals(0, time[1]);
    assertEquals(0, time[2]);
    //not enough precision to assert last byte
    //unless we wait for 200ms or so
  }

  @Test
  public void stringToBytes() {
    byte[] msg = new byte[6];
    //5 bytes and ascii codes for "testi"
    byte[] expected = new byte[]{0x05, 0x74, 0x65, 0x73, 0x74, 0x69};
    new StartBuilder(null).stringToBytes("testi", msg, 0, 1);
    assertArrayEquals(expected, msg);
  }
}
