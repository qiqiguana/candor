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

import fi.vtt.noen.testgen.observations.data.ProgramRunSuite;
import fi.vtt.noen.testgen.observations.data.ProgramRun;
import fi.vtt.noen.testgen.observations.data.Event;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Teemu Kanstrén
 */
public class DaikonArrayExperiments {
  private static Random random = new Random();

  public static void main(String[] args) {
    generateAndAssertBetween(0, 100);
    generateAndAssertBetween(-100, 100);
    generateAndAssertBetween(-200, -100);
    generateAndAssertBetween(0, 1);
    generateAndAssertBetween(1, 1);

    System.out.println("----------------(55 to 66)-----------------------");
    int amax = Integer.MIN_VALUE;
    int amin = Integer.MAX_VALUE;
    boolean minFound = false;
    boolean maxFound = false;
    for (int i = 0 ; i < 100 ; i++) {
      int v = cInt(55, 66);
      if (v < amin) {
        amin = v;
      }
      if (v > amax) {
        amax = v;
      }
//      System.out.println(""+ v);
      assertTrue(v >= 55);
      assertTrue(v <= 66);
      if (v == 55) {
        minFound = true;
      }
      if (v == 66) {
        maxFound = true;
      }
    }
    System.out.println("min:"+amin+" max:"+amax);
    assertTrue(minFound);
    assertTrue(maxFound);
  }

  private static void generateAndAssertBetween(int min, int max) {
    System.out.println("----------------("+min+" to "+max+")-----------------------");
    int amax = Integer.MIN_VALUE;
    int amin = Integer.MAX_VALUE;
    for (int i = 0 ; i < 100 ; i++) {
      int v = cInt(min, max);
      if (v < amin) {
        amin = v;
      }
      if (v > amax) {
        amax = v;
      }
//      System.out.println(""+ v);
      assertTrue(v >= min);
      assertTrue(v <= max);
    }
    System.out.println("min:"+amin+" max:"+amax);
  }

  private static int cInt(double min, double max) {
    return (int) Math.round(cDouble(min, max));
  }

  private static double cDouble(double min, double max) {
    double diff = max-min;
    double rnd = random.nextDouble();
    rnd *= diff;
    rnd += min;
    return rnd;
  }

  public static void man(String[] args) throws Exception {
    ProgramRunSuite suite =  new ProgramRunSuite("daikon-array-test", false);
    List<String> values = new ArrayList<String>();
    for (int valueIndex = 1 ; valueIndex < 2 ; valueIndex++) {
      values.add("test"+valueIndex);
    }
    for (int testIndex = 1 ; testIndex < 2 ; testIndex++) {
      ProgramRun run = new ProgramRun("testrun"+testIndex);
      //TODO rename addtest to addrun
      suite.addTest(run);
      for (int eventIndex = 1 ; eventIndex < 3 ; eventIndex++) {
        Event event = new Event("TestEvent");
        int randomIndex = (int)(values.size()*Math.random());
        String random = values.get(randomIndex);
        event.addAttribute("Single", random);
        event.addAttribute("All", values);
        run.add(event);
      }
    }
    suite.close();

    Runtime.getRuntime().exec("java daikon.Daikon --nohierarchy daikon-array-test.dtrace >daikon-array-test.txt");

  }
}
