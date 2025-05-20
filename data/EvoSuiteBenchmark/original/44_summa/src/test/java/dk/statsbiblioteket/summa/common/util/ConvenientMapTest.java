/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package dk.statsbiblioteket.summa.common.util;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

/**
 * Test cases for {@link ConvenientMap}
 */
public class ConvenientMapTest extends TestCase {

    ConvenientMap map;

    @Override
    public void setUp () throws Exception {
        map = new ConvenientMap();
    }

    @Override
    public void tearDown () throws Exception {

    }

    public void testJSON() throws Exception {
        final String SIMPLE = "{ foo:\"bar\" }";
        final String DUAL = "{ foo:\"bar\", zoo:\"baz\" }";
        final String INT = "{ noo:87 }";
        ConvenientMap map = new ConvenientMap();
        map.addJSON(SIMPLE);
        map.addJSON(DUAL);
        map.addJSON(INT);
        assertEquals("bar", map.getString("foo"));
        assertEquals("baz", map.getString("zoo"));
        assertEquals(Integer.valueOf(87), map.getInt("noo"));
    }

    public void testJSONEscape() throws Exception {
        ConvenientMap map = new ConvenientMap();
        map.addJSON("{ foo:\"bar\\\\ zoo\" }");
        assertEquals("bar\\ zoo", map.getString("foo"));
    }

    public void testClear () throws Exception {
        map.put ("foo", "bar");
        assertEquals(map.size(), 1);

        map.clear();
        assertEquals(map.size(), 0);
    }

    public void testPutString () throws Exception {
        map.put ("foo", "bar");
        assertEquals (map.get("foo"), "bar");
    }

    public void testGetStringFromInt () throws Exception {
        map.put ("foo", 27);
        assertEquals(map.getString("foo"), "27");
    }

    public void testGetStringFromLong () throws Exception {
        map.put ("foo", 27L);
        assertEquals(map.getString("foo"), "27");
    }

    public void testGetStringFromBool () throws Exception {
        map.put ("foo", true);
        assertEquals(map.getString("foo"), "true");
    }

    public void testGetStringDefault () throws Exception {
        assertEquals(map.getString("foo", "bar"), "bar");
    }

    public void testGetStringMissing () throws Exception {
        try {
            map.getString("foo");
            fail ();
        } catch (NullPointerException e) {
            // expected
        }
    }

    public void testGetIntFromString () throws Exception {
        map.put ("foo", "27");
        assertEquals(map.getInt("foo"), new Integer(27));
    }

    public void testGetIntFromLong () throws Exception {
        map.put ("foo", 27L);
        assertEquals(map.getInt("foo"), new Integer(27));
    }


    public void testGetIntDefault () throws Exception {
        assertEquals(map.getInt("foo", 68), new Integer(68));
    }

    public void testGetIntMissing () throws Exception {
        try {
            map.getInt("foo");
            fail ();
        } catch (NullPointerException e) {
            // expected
        }
    }

    public void testGetLongFromString () throws Exception {
        map.put ("foo", "27");
        assertEquals(map.getLong("foo"), new Long (27));
    }

    public void testGetLongFromInt () throws Exception {
        map.put ("foo", 27);
        assertEquals(map.getLong("foo"), new Long (27));
    }

    public void testGetLongDefault () throws Exception {
        assertEquals(map.getLong("foo", 68L), new Long(68));
    }

    public void testGetLongMissing () throws Exception {
        try {
            map.getLong("foo");
            fail ();
        } catch (NullPointerException e) {
            // expected
        }
    }

    public void testGetStringsDefault () throws Exception {
        List<String> strings = Arrays.asList ("bar", "baz");
        List<String> val = map.getStrings("foo", strings);
        assertTrue(strings == val);
    }

    public void testGetStringsMissing () throws Exception {
        try {
            map.getStrings("foo");
            fail ();
        } catch (NullPointerException e) {
            // expected
        }
    }

}




