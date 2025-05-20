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

package fi.vtt.noen.mfw.webui;

import com.gargoylesoftware.htmlunit.ThreadedRefreshHandler;
import net.sourceforge.jwebunit.html.Table;
import net.sourceforge.jwebunit.htmlunit.HtmlUnitTestingEngineImpl;
import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static fi.vtt.noen.mfw.webui.WebTestUtils.*;
import static junit.framework.Assert.assertTrue;

/**
 * @author Teemu Kanstren
 */
public class TestProbeTests {
  private WebTester t = null;
  private static String WEB_UI_PORT = "8080";

  @BeforeClass
  public static void setupSuite() throws Exception {
    String port = System.getenv("MFW_WEBUI_PORT");
    if (port != null) {
      WEB_UI_PORT = port;
    }
    startRemoteServer();
    startRemoteSuperNode();
    Thread.sleep(5000);
  }

  @AfterClass
  public static void teardown() {
    invokeRestShutdown(WEB_UI_PORT);
  }

  @Before
  public void setup() throws Exception {
    t = new WebTester();
    t.setBaseUrl("http://localhost:"+WEB_UI_PORT+"/");
    HtmlUnitTestingEngineImpl engine = (HtmlUnitTestingEngineImpl) t.getTestingEngine();
    engine.setRefreshHandler(new ThreadedRefreshHandler());
  }

  @Test
  public void historyPage() {
    goToHistoryFrame(t);
    t.assertTablePresent(MEASUREMENTS_TABLE);
    Table measurements = t.getTable(MEASUREMENTS_TABLE);
    ArrayList rows = measurements.getRows();
    String page = t.getPageSource();
    System.out.println(page);
    assertTrue("measurement table should have >= 2 rows to make valid comparisons on sort etc. had "+rows.size(), rows.size() > 1);
  }

  @Test
  public void probesPage() {
    goToProbeFrame(t);
    t.assertTablePresent(PROBES_TABLE);
  }

  @Test
  public void eventsPage() {
    goToEventFrame(t);
    t.assertTablePresent(EVENTS_TABLE);
    //add events for connected probes, test those for sorting, etc.
  }

  /*
  historia:
-sorttaus "time", "measureURI", "value", "precision"

probes and bm:
-"connected probes": 5 probea listattu
-sorttaus "endpoint", "probeName", "measureURI", "precision", "delay"
-action: "select" ja siitä oikeat tiedot "selected probe" tauluun
-"selected probe" taulun sorttaus "key" ja "value" sarakkeilla
-"available bm": neljä bm listattu
-sorttaus: "bmId", "measureURI", "bmDescription"
-events sivu: tänne ei nyt tule mitään, joten mitäs tästä sitte..
   */
}
