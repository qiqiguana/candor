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

import fi.vtt.noen.mfw.bundle.common.Const;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Teemu Kanstren
 */
public class TestUtils {
  public static InputStream streamFor(String str) {
    return new ByteArrayInputStream(str.getBytes());
  }

  public static void main(String[] args) throws Exception {
    dropDatabase();
  }


  public static void dropDatabase() throws Exception {
    System.out.println("deleteing everything from the database");
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/mfw_db", "noen-mfw", "tru4A7rU");
    String deleteValues = "delete from bm_value";
    Statement st = conn.createStatement();
    st.executeUpdate(deleteValues);
    String deleteProbes = "delete from probe_description";
    st.executeUpdate(deleteProbes);
    String deleteBM = "delete from bm_description";
    st.executeUpdate(deleteBM);
    String deleteEvents = "delete from event";
    st.executeUpdate(deleteEvents);
    String deleteTargets = "delete from target_description";
    st.executeUpdate(deleteTargets);
    st.close();
    conn.close();
    System.out.println("delete done");
  }

  public static Map<String, String> createProbeProperties(String probeName, String targetType, String targetName, String bmClass, String bmName, int precision) {
    Map<String, String> properties = new HashMap<String, String>();
    properties.put(Const.PROBE_NAME, probeName);
    properties.put(Const.PROBE_TARGET_TYPE, targetType);
    properties.put(Const.PROBE_TARGET_NAME, targetName);
    properties.put(Const.PROBE_BM_CLASS, bmClass);
    properties.put(Const.PROBE_BM_NAME, bmName);
    properties.put(Const.PROBE_PRECISION, "" + precision);
    return properties;
  }

  public static Map<String, String> createProbeProperties(int i) {
    return createProbeProperties("my probe " + i, "target type" + i, "target name" + i, "bm class " + i, "bm name " + i, 1);
  }

}

