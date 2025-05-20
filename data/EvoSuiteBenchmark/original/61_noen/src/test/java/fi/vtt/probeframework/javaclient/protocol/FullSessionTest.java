package fi.vtt.probeframework.javaclient.protocol;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import fi.vtt.probeframework.javaclient.api.probe.*;
import fi.vtt.probeframework.javaclient.api.probe.PFTest;
import fi.vtt.probeframework.javaclient.protocol.messages.Precision;

import static org.junit.Assert.*;

public class FullSessionTest extends BaseTest {
  @Before
  public void setup() {
    IO.reset();
    BaseProbe.reset();
    PF.reset();
    Configuration.testMode = true;
  }

  @After
  public void teardown() {
    Configuration.testMode = false;
  }
  
  @Test
  public void testInt() throws Exception { 
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    IO.file = bos;
//    State.description = "fulltest";
    PFTest test = new PFTest(null, null, null, Precision.MILLIS, null, "fulltest");
    PF.startTest(test);
    IntProbe probe = new IntProbe(test, (byte)0xaa, "latency");
    probe.data(11);
    probe.data(22);
    probe.data(33);

    byte[] msg = bos.toByteArray();
    //clear start time for deterministic test comparison
    msg[17] = 0;
    msg[18] = 0;
    msg[19] = 0;
    msg[20] = 0;
    msg[50] = 0;
    msg[66] = 0;
    msg[82] = 0;
//    System.out.println("msg:"+hex(msg));
//    System.out.println("file:"+IO.file);

    byte[] expected = new byte[] {
        (byte)0xff, (byte)0xff, 2, 0,0,0,0, 
        8, 0x66, 0x75, 0x6c, 0x6c, 0x74, 0x65, 0x73, 0x74, //last:15
        0x2, 0,0,0,0, 0,0,1, //last:23
        
        0x10, (byte)0xaa, 7, 0x6c, 0x61, 0x74, 0x65, 0x6e, 0x63, 0x79, 6, //last:34
        
        0x11, 0,1, (byte)0xaa, 0,0,0,0,0,0,0,11, 0,0,0,0,//last:50
        0x11, 0,2, (byte)0xaa, 0,0,0,0,0,0,0,22, 0,0,0,0,
        0x11, 0,3, (byte)0xaa, 0,0,0,0,0,0,0,33, 0,0,0,0
        };
    assertArrayEquals(expected, msg);
  }
  
  @Test
  public void testAll() throws Exception {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    IO.file = bos;
//    State.description = "fulltest2";
    PFTest test = new PFTest(null, null, null, Precision.MILLIS, null, "fulltest2");
    PF.startTest(test);
    IntProbe intProbe = new IntProbe(test, (byte)0xaa, "latency");
    BooleanProbe booleanProbe = new BooleanProbe(test, (byte)0x55, "boolean");
    ByteProbe byteProbe = new ByteProbe(test, (byte)0x88, "data");
    TextProbe textProbe = new TextProbe(test, (byte)0xcc, "text");
    intProbe.data(11);
    booleanProbe.data(true);
    byteProbe.data(new byte[] {(byte)0xca, (byte)0xfe});
    textProbe.data("hello");

    byte[] msg = bos.toByteArray();
    //System.out.println(hex(msg));
    //clear start time for deterministic test comparison
    msg[18] = 0;
    msg[19] = 0;
    msg[20] = 0;
    msg[21] = 0;

    //clear msg times to ignore small deltasq
    msg[78] = 0;
    msg[87] = 0;
    msg[99] = 0;
    msg[114] = 0;

    byte[] expected = new byte[] {
        (byte)0xff, (byte)0xff, 2, 0,0,0,0, //6 
        9, 0x66,0x75,0x6c,0x6c,0x74,0x65,0x73,0x74,0x32, //16
        0x2, 0,0,0,0, 0,0,1, //24
        
        0x10, (byte)0xaa, 7, 0x6c,0x61,0x74,0x65,0x6e,0x63,0x79, 6, //35
        0x10, (byte)0x55, 7, 0x62,0x6f,0x6f,0x6c,0x65,0x61,0x6e, 1, //46
        0x10, (byte)0x88, 4, 0x64,0x61,0x74,0x61, 9, //54
        0x10, (byte)0xcc, 4, 0x74,0x65,0x78,0x74, 8, //62
        
        0x11, 0,1, (byte)0xaa, 0,0,0,0,0,0,0,11, 0,0,0,0, //78
        0x11, 0,2, (byte)0x55, 1, 0,0,0,0, //87
        0x11, 0,3, (byte)0x88, 0,2, (byte)0xca,(byte)0xfe, 0,0,0,0, //99
        0x11, 0,4, (byte)0xcc, 0,5, 0x68,0x65,0x6c,0x6c,0x6f, 0,0,0,0 //114
        };
//    System.out.println(hex(msg));
    assertEquals(2, msg[17]);
    assertArrayEquals(expected, msg);
  }
}
