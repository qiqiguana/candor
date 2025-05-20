/*
 * Copyright (C) 2009 VTT Technical Research Centre of Finland.
 *
 * This file is part of NOEN framework.
 *
 * NOEN framework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2.
 *
 * NOEN framework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package fi.vtt.noen.testgen.parser;

import org.junit.Test;
import org.junit.Before;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.in.XMxmlParser;
import org.deckfour.xes.info.XLogInfo;
import org.deckfour.xes.info.XLogInfoFactory;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.Iterator;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author Teemu Kanstrén
 */
public class PromParserUnitTests {
  private InputStream in;
  private PromParser parser;

  @Before
  public void setup() {
    in = getClass().getResourceAsStream("prom-test-output.txt");
    parser = new PromParser();
  }

  @Test
  public void parseProducesOneLog() throws Exception {
    Set<XLog> logs = new XMxmlParser().parse(in);
    assertEquals("There should be only one log file from MXML parser", 1, logs.size());
  }

  @Test
  public void summaryContainsCorrectEvents() {
    XLog log = parser.parseLog(in);
    XLogInfo summary = XLogInfoFactory.createLogInfo(log);
    assertEquals("Number of events in log", 45, summary.getNumberOfEvents());
    assertEquals("Number of events classes in log", 15, summary.getEventClasses().size());
  }

  @Test
  public void filtersAreAllCreated() {
    XLog log = parser.parseLog(in);
    XLogInfo summary = XLogInfoFactory.createLogInfo(log);
    Collection<String> filters = parser.createFilters(summary);
    assertEquals("Number of filters from summary", 15, filters.size());
    
  }
}
