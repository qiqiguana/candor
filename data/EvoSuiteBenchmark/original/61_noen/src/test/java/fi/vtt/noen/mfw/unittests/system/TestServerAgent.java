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

package fi.vtt.noen.mfw.unittests.system;

import fi.vtt.noen.mfw.bundle.server.shared.ServerAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Teemu Kanstren
 */
public class TestServerAgent implements ServerAgent {
  private int totalReceivedMeasures = 0;
  private int keepAlives = 0;
  private int registrations = 0;

  private Map<String, ProbeResults> measures = new HashMap<String, ProbeResults>();

  public int getTotalReceivedMeasures() {
    return totalReceivedMeasures;
  }

  public int getRegistrations() {
    return registrations;
  }

  public int getReceivedBooleans(String measureURI) {
    return getResultsFor(measureURI).getBooleans();
  }

  public int getReceivedDoubles(String measureURI) {
    return getResultsFor(measureURI).getDoubles();
  }

  public int getReceivedStrings(String measureURI) {
    return getResultsFor(measureURI).getStrings();
  }

  public int getKeepAlives() {
    return keepAlives;
  }

  private ProbeResults getResultsFor(String measureURI) {
    ProbeResults results = measures.get(measureURI);
    if (results == null) {
      results = new ProbeResults();
    }
    return results;
  }

  public synchronized void waitForMeasure(String measureURI, int timeout) {
    if (measures.get(measureURI) != null) {
      return;
    }
    try {
      wait(timeout);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("measures:" + measures);
    if (measures.get(measureURI) == null) {
      throw new IllegalStateException("No measure received in time for:" + measureURI);
    }
  }

  private ProbeResults getOrCreateProbeResultsFor(String measureURI) {
    ProbeResults results = measures.get(measureURI);
    if (results == null) {
      results = new ProbeResults();
      measures.put(measureURI, results);
    }
    return results;
  }

  public synchronized boolean measurement(long time, String measureURI, int precision, String value, long subscriptionId) {
    System.out.println("received for:"+measureURI);
    ProbeResults results = getOrCreateProbeResultsFor(measureURI);
    totalReceivedMeasures++;
    results.addString();
    notifyAll();
    return true;
  }

  public void event(long time, String type, String source, String message, long subscriptionId) {
  }

  public long register(Map<String, String> properties) {
    registrations++;
    return registrations;
  }

  public boolean keepAlive(long probeId) {
    keepAlives++;
    return true;
  }

  public void unregister(long probeId) {

  }

  private class ProbeResults {
    private int booleans = 0;
    private int doubles = 0;
    private int strings = 0;

    public int getBooleans() {
      return booleans;
    }

    public int getDoubles() {
      return doubles;
    }

    public int getStrings() {
      return strings;
    }

    public void addBoolean() {
      booleans++;
    }

    public void addDouble() {
      doubles++;
    }

    public void addString() {
      strings++;
    }
  }
/*
  public void checkSubscriptions(long probeId) {
    // TODO Auto-generated method stub
    
  }
*/
  public void checkSubscriptions(long probeId, List<Long> currentSubscriptions) {
    // TODO Auto-generated method stub
    
  }

  public boolean BMReport(long time, String measureURI, String value,
      long subscriptionId, boolean matchReference, String reference) {
    // TODO Auto-generated method stub
    return false;
  }

}
