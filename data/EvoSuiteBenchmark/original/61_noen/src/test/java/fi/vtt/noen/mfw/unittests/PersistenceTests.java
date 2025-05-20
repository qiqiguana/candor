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

package fi.vtt.noen.mfw.unittests;

import fi.vtt.noen.mfw.bundle.blackboard.Blackboard;
import fi.vtt.noen.mfw.bundle.blackboard.BlackboardActivator;
import fi.vtt.noen.mfw.bundle.server.plugins.persistence.PersistencePluginActivator;
import fi.vtt.noen.mfw.bundle.server.plugins.persistence.PersistencePluginImpl;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.BMDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ProbeDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.Value;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static fi.vtt.noen.mfw.unittests.TestUtils.*;
import static junit.framework.Assert.assertEquals;

/**
 * @author Teemu Kanstren
 */
public class PersistenceTests {
  private PersistencePluginImpl persistence;
  private Blackboard bb;
  private Connection conn;
  private Statement st;
  private ResultSet rs;

  @Before
  public void setup() throws Exception {
    dropDatabase();

    MockBundleContext bc = new MockBundleContext();
//    persistence = new PersistencePluginImpl(bc);
    BlackboardActivator ba = new BlackboardActivator();
    ba.start(bc);
    bb = ba.getBlackboard();
    PersistencePluginActivator pa = new PersistencePluginActivator();
    pa.start(bc);
    persistence = pa.persistence;
    //need to recreate to re-initialize which database we are using
    conn = DriverManager.getConnection("jdbc:mysql://localhost/mfw_db", "noen-mfw", "tru4A7rU");
    st = conn.createStatement();
  }

  @After
  public void teardown() throws Exception {
    persistence.stop();
    rs.close();
    st.close();
    conn.close();
  }

  @Test
  public void itemPersistence() throws Exception {
    persistence.createProbeDescription(createProbeProperties(1));
    persistence.createProbeDescription(createProbeProperties(2));
    persistence.createProbeDescription(createProbeProperties(3));
    persistence.createProbeDescription(createProbeProperties(4));
    persistence.createProbeDescription(createProbeProperties(5));
    persistence.createProbeDescription(createProbeProperties(6));
    persistence.createProbeDescription(createProbeProperties(7));
    persistence.createProbeDescription(createProbeProperties(8));
    persistence.createProbeDescription(createProbeProperties(9));
    persistence.createProbeDescription(createProbeProperties(10));
    String query = "select count(probe_name) from probe_description";
    String query2 = "select count(distinct probe_name) from probe_description";
    rs = st.executeQuery(query);
    int count = -1;
    int count2 = -1;
    if (rs.next()) {
      count = rs.getInt(1);
    }
    rs.close();
    rs = st.executeQuery(query2);
    if (rs.next()) {
      count2 = rs.getInt(1);
    }
    assertEquals("Number of probes stored in database", 10, count);
    assertEquals("Number of distinct probes stored in database", 10, count2);
  }

  @Test
  public void valueCount() throws Exception {
    ProbeDescription pd = persistence.createProbeDescription(createProbeProperties(1));
    BMDescription bm = pd.getBm();
    bb.process(new Value(bm, 1, "hello", System.currentTimeMillis()));
    bb.process(new Value(bm, 1, "hello", System.currentTimeMillis()));
    bb.process(new Value(bm, 1, "hello", System.currentTimeMillis()));
    bb.process(new Value(bm, 1, "hello", System.currentTimeMillis()));
    bb.process(new Value(bm, 1, "hello", System.currentTimeMillis()));
    bb.process(new Value(bm, 1, "hello", System.currentTimeMillis()));
    bb.process(new Value(bm, 1, "hello", System.currentTimeMillis()));
    bb.process(new Value(bm, 1, "hello", System.currentTimeMillis()));
    bb.process(new Value(bm, 1, "hello", System.currentTimeMillis()));
    bb.process(new Value(bm, 1, "hello", System.currentTimeMillis()));
    int count = persistence.getValueCount();
    assertEquals("Number of values stored in database", 10, count);

    String query = "select count(value_string) from bm_value";
    st = conn.createStatement();
    rs = st.executeQuery(query);
    count = -1;
    if (rs.next()) {
      count = rs.getInt(1);
    }
    assertEquals("Number of values stored in database", 10, count);
  }
}
