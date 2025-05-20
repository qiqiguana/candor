/*
 * Copyright (C) 2010-2011 VTT Technical Research Centre of Finland.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package fi.vtt.noen.mfw.unittests.agent.server;

import fi.vtt.noen.mfw.bundle.blackboard.Blackboard;
import fi.vtt.noen.mfw.bundle.blackboard.BlackboardImpl;
import fi.vtt.noen.mfw.bundle.server.plugins.derivedmeasure.DMProcessingException;
import fi.vtt.noen.mfw.bundle.server.plugins.derivedmeasure.DMProcessor;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.BMDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.DMDefinition;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.TargetDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.Value;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Teemu Kanstren
 * @testtype A
 */
public class DMTests {
  private static BMDescription bm1;
  private static BMDescription bm2;
  private static BMDescription bm3;
  private static BMDescription bm4;
  private static ScriptEngine jsEngine;

  @BeforeClass
  public static void setup() {
    bm1 = new BMDescription(new TargetDescription("targetType1", "targetName1"), "bmClass1", "bmName1", "Nice BM 1");
    bm2 = new BMDescription(new TargetDescription("targetType1", "targetName1"), "bmClass2", "bmName2", "Nice BM 2");
    bm3 = new BMDescription(new TargetDescription("targetType1", "targetName1"), "bmClass3", "bmName3", "Nice BM 3");
    bm4 = new BMDescription(new TargetDescription("targetType1", "targetName1"), "bmClass4", "bmName4", "Nice BM 4");
    ScriptEngineManager mgr = new ScriptEngineManager();
    jsEngine = mgr.getEngineByName("JavaScript");
  }

  @Test
  public void scriptCreationWithMissingValues() throws Exception {
    String script = "";
    DMDefinition dm = new DMDefinition(1, "script_test_derived measure", script);
    List<BMDescription> requiredBM = dm.getRequiredBM();
    requiredBM.add(bm1);
    requiredBM.add(bm2);
    requiredBM.add(bm3);

    Value v1 = new Value(bm1, 1, "hiihaa", 5);
    Value v2 = new Value(bm2, 2, "haahii", 2);

    Blackboard bb = new BlackboardImpl();
    DMProcessor processor = new DMProcessor(bb, dm);
    processor.process(v1);
    processor.process(v2);

    try {
      processor.createFullScript();
      fail("Missing values in script creation should throw DMProcessingException");
    } catch (DMProcessingException e) {
      //expected
      String message = e.getMessage();
      assertTrue(message.contains("missing values"));
      String uri = bm3.getMeasureURI();
      assertTrue("message missing BM URI [" + uri + "]:" + message, message.contains(uri));
      uri = bm1.getMeasureURI();
      assertFalse("message should not have BM URI [" + uri + "]:" + message, message.contains(uri));
      uri = bm2.getMeasureURI();
      assertFalse("message should not have BM URI [" + uri + "]:" + message, message.contains(uri));
    }
  }

  @Test
  public void scriptCreationWithTwoValues() throws Exception {
    String script = "dm = bm1+bm2;\n";
    DMDefinition dm = new DMDefinition(1, "script_test_derived measure", script);
    List<BMDescription> requiredBM = dm.getRequiredBM();
    requiredBM.add(bm1);
    requiredBM.add(bm2);
    Blackboard bb = new BlackboardImpl();
    DMProcessor processor = new DMProcessor(bb, dm);
    Value v1 = new Value(bm1, 1, "22", 5);
    Value v2 = new Value(bm2, 2, "33", 2);
    processor.process(v1);
    processor.process(v2);
    String actual = processor.createFullScript();
    //for now numericals are represented also as "33" which needs to be fixed it DM is ever supported..
    String expected = "bm1 = \"22\";\n"+"bm2 = \"33\";\n"+script;
    assertEquals("Created script for two BM with values 22 and 33:", expected, actual);
    Object result = jsEngine.eval(actual);
//    assertEquals("Result for executing script "+actual, 55.0d, result);
  }


  @Test
  public void scriptCreationWithFourValues() throws Exception {
    String script = "x = 3;\n"; //:=3
    script += "x += bm3;\n"; //:3+3=6
    script += "x -= bm2;\n"; //:6-2=4
    script += "x *= bm1;\n"; //:4*5=20
    script += "y = (bm1 - bm2) / bm3\n"; //:3/3=1
    script += " y += bm4;\n"; //:1+7=8
    script += "dm = x+y;\n"; //:20+8=28
    DMDefinition dm = new DMDefinition(1, "script_test_derived measure", script);
    List<BMDescription> requiredBM = dm.getRequiredBM();
    requiredBM.add(bm1);
    requiredBM.add(bm2);
    requiredBM.add(bm3);
    requiredBM.add(bm4);
    Blackboard bb = new BlackboardImpl();
    DMProcessor processor = new DMProcessor(bb, dm);
    Value v1 = new Value(bm1, 1, "5", 5);
    Value v2 = new Value(bm2, 2, "2", 2);
    Value v3 = new Value(bm3, 1, "3", 5);
    Value v4 = new Value(bm4, 1, "7", 2);
    processor.process(v1);
    processor.process(v2);
    processor.process(v3);
    processor.process(v4);
    String actual = processor.createFullScript();
    //for now numericals are represented also as "33" which needs to be fixed it DM is ever supported..
    String expected =
            "bm1 = \"5\";\n"+
            "bm2 = \"2\";\n"+
            "bm3 = \"3\";\n"+
            "bm4 = \"7\";\n"+
            script;
    assertEquals("Created script for four BM with values 5,2,3,7:", expected, actual);
    Object result = jsEngine.eval(actual);
//    assertEquals("Result for executing script "+actual, 28.0d, result);
  }

  /**
   * The fourth should not appear since if is not required and the processor should drop it.
   */
  @Test
  public void scriptCreationWithThreeValuesRequiredFourProvided() throws Exception {
    String script = "x = 3;\n"; //:=3
    script += "x += bm3;\n"; //:3+3=6
    script += "x -= bm2;\n"; //:6-2=4
    script += "x *= bm1;\n"; //:4*5=20
    script += "y = (bm1 - bm2) / bm3\n"; //:3/3=1
    script += "dm = x+y;\n"; //:20+1=21
    DMDefinition dm = new DMDefinition(1, "script_test_derived measure", script);
    List<BMDescription> requiredBM = dm.getRequiredBM();
    requiredBM.add(bm1);
    requiredBM.add(bm2);
    requiredBM.add(bm3);
    Blackboard bb = new BlackboardImpl();
    DMProcessor processor = new DMProcessor(bb, dm);
    Value v1 = new Value(bm1, 1, "5", 5);
    Value v2 = new Value(bm2, 2, "2", 2);
    Value v3 = new Value(bm3, 1, "3", 5);
    Value v4 = new Value(bm4, 1, "7", 2);
    processor.process(v1);
    processor.process(v2);
    processor.process(v3);
    processor.process(v4);
    String actual = processor.createFullScript();
    //for now numericals are represented also as "33" which needs to be fixed it DM is ever supported..
    String expected =
            "bm1 = \"5\";\n"+
            "bm2 = \"2\";\n"+
            "bm3 = \"3\";\n"+
            script;
    assertEquals("Created script for three BM with values 5,2,3:", expected, actual);
    Object result = jsEngine.eval(actual);
//    assertEquals("Result for executing script "+actual, 21.0d, result);
  }

  /**
   * Values with highest precision should be picked...
   */
  @Test
  public void scriptCreationWithCompetingBMPrecision() throws Exception {
    String script = "dm = bm1+bm2;\n";
    DMDefinition dm = new DMDefinition(1, "script_test_derived measure", script);
    List<BMDescription> requiredBM = dm.getRequiredBM();
    BMDescription bmx2 = new BMDescription(new TargetDescription("targetType2", "targetName2"), "bmClass2", "bmName2", "Nice BM 2");
    requiredBM.add(bm1);
    requiredBM.add(bmx2);
    Blackboard bb = new BlackboardImpl();
    DMProcessor processor = new DMProcessor(bb, dm);
    Value v1 = new Value(bm1, 1, "5", 5);
    Value v2 = new Value(bmx2, 2, "6", 2);
    Value v3 = new Value(bmx2, 1, "7", 5);
    Value v4 = new Value(bm1, 2, "8", 2);
    Value v5 = new Value(bm1, 3, "9", 2); //highest for bm1
    Value v6 = new Value(bmx2, 3, "10", 2); //highest for bm2
    Value v7 = new Value(bmx2, 1, "100", 2); //precision too low
    processor.process(v1);
    processor.process(v2);
    processor.process(v3);
    processor.process(v4);
    processor.process(v5);
    processor.process(v6);
    processor.process(v7);
    String actual = processor.createFullScript();
    //for now numericals are represented also as "33" which needs to be fixed it DM is ever supported..
    String expected =
            "bm1 = \"9\";\n"+
            "bm2 = \"10\";\n"+
            script;
    assertEquals("Created script for three BM with several values and different precisions:", expected, actual);
    Object result = jsEngine.eval(actual);
//    assertEquals("Result for executing script "+actual, 19.0d, result);
  }

  /**
   * Different value types FTW.
   */
  @Test
  public void booleanValue() throws Exception {
    String script = "if (bm1) x = \"it is true\";else x=\"falsified\"\n";
    DMDefinition dm = new DMDefinition(1, "script_test_derived measure", script);
    List<BMDescription> requiredBM = dm.getRequiredBM();
    requiredBM.add(bm1);
    Blackboard bb = new BlackboardImpl();
    DMProcessor processor = new DMProcessor(bb, dm);
    Value v1 = new Value(bm1, 1, "true", 5);
    processor.process(v1);
    String actual = processor.createFullScript();
    //for now we describe boolean as "true", but that is since for now we do not really need the DM functionality..
    String expected = "bm1 = \"true\";\n"+script;
    assertEquals("Created script for one BM with boolean (true) value:", expected, actual);
    Object result = jsEngine.eval(actual);
    assertEquals("Result for executing script "+actual, "it is true", result);

    Value v2 = new Value(bm1, 1, "false", 5);
    processor.process(v2);
    actual = processor.createFullScript();
    //for now booleans are represented also as "33" which needs to be fixed it DM is ever supported..
    expected =
            "bm1 = \"false\";\n"+
            script;
    assertEquals("Created script for one BM with boolean (false) value:", expected, actual);
    result = jsEngine.eval(actual);
//    assertEquals("Result for executing script "+actual, "falsified", result);
  }

  /**
   * Different value types FTW.
   */
  @Test
  public void stringValue() throws Exception {
    String script = "if (bm1.indexOf('epix') > 0) x = \"it is epix\";else x=\"green beans\"\n";
    DMDefinition dm = new DMDefinition(1, "script_test_derived measure", script);
    List<BMDescription> requiredBM = dm.getRequiredBM();
    requiredBM.add(bm1);
    Blackboard bb = new BlackboardImpl();
    DMProcessor processor = new DMProcessor(bb, dm);
    Value v1 = new Value(bm1, 1, "mostly epixxxx", 5);
    processor.process(v1);
    String actual = processor.createFullScript();
    String expected =
            "bm1 = \"mostly epixxxx\";\n"+
            script;
    assertEquals("Created script for one BM with string value:", expected, actual);
    Object result = jsEngine.eval(actual);
    assertEquals("Result for executing script "+actual, "it is epix", result);

    Value v2 = new Value(bm1, 1, "bob was here", 5);
    processor.process(v2);
    actual = processor.createFullScript();
    expected =
            "bm1 = \"bob was here\";\n"+
            script;
    assertEquals("Created script for one BM with string value:", expected, actual);
    result = jsEngine.eval(actual);
    assertEquals("Result for executing script "+actual, "green beans", result);
  }

  @Test
  public void javascripting() {
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
    try {
      Object o = jsEngine.eval("print('Hello, world!');bob = true;");
      System.out.println("o:"+o.getClass());
    } catch (ScriptException ex) {
      ex.printStackTrace();
    }
  }

}
