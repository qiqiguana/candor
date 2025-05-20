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
package dk.statsbiblioteket.summa.common.configuration;

import dk.statsbiblioteket.summa.common.configuration.storage.JStorage;
import dk.statsbiblioteket.util.Strings;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JStorageTest extends ConfigurationStorageTestCase {
    private static Log log = LogFactory.getLog(JStorageTest.class);
    private static final String CONFIGURATIONJS = "common/configuration.js";
    public JStorageTest() {
        super(new JStorage());
    }

    public void testLoadFromResource() throws Exception {
        JStorage js = new JStorage(CONFIGURATIONJS);
        Configuration conf = new Configuration(js);
        checkSampleConfig(conf);
    }

    public void testLoadFromSysProp() throws Exception {
        System.setProperty(Configuration.CONF_CONFIGURATION_PROPERTY,
                CONFIGURATIONJS);
        try {
            Configuration conf = Configuration.getSystemConfiguration(false);
            checkSampleConfig(conf);
        } finally {
            System.clearProperty(Configuration.CONF_CONFIGURATION_PROPERTY);
        }

    }

    public void checkSampleConfig(Configuration conf) throws Exception {
        assertFalse(conf.valueExists("summa.nonexisting"));

        assertEquals(27, conf.getInt("summa.test.int"));
        assertEquals(27, conf.getLong("summa.test.int"));

        assertTrue(conf.getBoolean("summa.test.boolean"));

        assertEquals(1, conf.getInt("summa.test.closure"));
        assertEquals(2, conf.getInt("summa.test.closure"));
        assertEquals(3, conf.getInt("summa.test.closure"));
    }

    public void testNewNested() throws Exception {
        JStorage sto = new JStorage();
        assertEquals(0, sto.size());

        JStorage sub = sto.createSubStorage("summa.sub");

        assertEquals(0, sub.size());
    }

    public void testLoadNested() throws Exception {
        JStorage sto = new JStorage(CONFIGURATIONJS);
        JStorage sub = sto.getSubStorage("summa.test.nested");

        assertEquals(1, sub.size());
        assertEquals(27, asInt(sub.get("summa.test.subint")));
    }

    public void testIteration() throws Exception {
        JStorage js = new JStorage(CONFIGURATIONJS);
        Configuration conf = new Configuration(js);
        checkSampleConfig(conf);

        for (Map.Entry<String, Serializable> entry : conf) {
            log.info(entry.getKey() + " = " + entry.getValue()
                               + "   (" + entry.getValue().getClass() + ")");
            // TODO use assert
        }
    }

    public void testLoadSubStorages() throws Exception {
        JStorage js = new JStorage(CONFIGURATIONJS);
        JStorage sub = js.getSubStorage("summa.test.nested");
        List<ConfigurationStorage> subs =
                                     js.getSubStorages("summa.test.nestedlist");

        assertEquals(1, sub.size());
        assertEquals(27, asInt(sub.get("summa.test.subint")));

        assertEquals(2, subs.size());
        assertEquals(1, subs.get(0).size());
        assertEquals(1, subs.get(1).size());
        assertEquals(27, asInt(subs.get(0).get("summa.test.int")));
        assertEquals(true, asBoolean(subs.get(1).get("summa.test.boolean")));
    }

    public void testListOfStrings() throws Exception {
        JStorage js = new JStorage(CONFIGURATIONJS);
        List vals = (List)js.get("summa.test.listofstrings");
        assertEquals(Arrays.asList("one", "two", "three"), vals);
    }

    public void testToString() throws Exception {
        JStorage js = new JStorage();

        // Empty storage
        assertEquals("var config = {\n}", js.toString());

        // Storage with a single string
        js = new JStorage();
        js.put("foo", "bar");
        assertEquals("var config = {\n  'foo' : \"bar\"\n}", js.toString());

        // Storage with two booleans
        js = new JStorage();
        js.put("boolTrue", true);
        js.put("boolFalse", false);
        assertEquals("var config = {\n  'boolFalse' : false,\n  'boolTrue' : true\n}",
                     js.toString());

        // Storage with a list of strings
        js = new JStorage();
        js.put("foo", (Serializable)Arrays.asList("one", "two"));
        assertEquals("var config = {\n  'foo' : [\"one\", \"two\"]\n}", js.toString());

        // Storage with a single sub storage
        js = new JStorage();
        JStorage sub = js.createSubStorage("foo");
        sub.put("bar" , 27);
               
        assertEquals("var config = {\n  'foo' : {\n    'bar' : 27\n  }\n}",
                     js.toString());

        // Storage with a list of two sub storages
        js = new JStorage();
        List<ConfigurationStorage> subs = js.createSubStorages("foo", 2);
        assertEquals(2, subs.size());
        subs.get(0).put("bar" , 27);
        subs.get(1).put("bar" , false);
        assertEquals(
        "var config = {\n" +
        "  'foo' : [\n" +
        "    {\n" +
        "      'bar' : 27\n" +
        "    }, {\n" +
        "      'bar' : false\n" +
        "    }\n" +
        "  ]\n" +
        "}", js.toString());

        log.info("--------------");
        js = new JStorage(CONFIGURATIONJS);
        log.info(js.toString());
        log.info("--------------");
    }

    public void testSimpleTypes() throws Exception {
        JStorage js = new JStorage();

        js.put("foo", 27);
        assertTrue("27 should turn into a Integer, but was: "
                   + js.get("foo").getClass().getName(),
                   js.get("foo") instanceof Integer);

        js.put("bar", true);
        assertTrue("true should turn into a Boolean, but was: "
                   + js.get("bar").getClass().getName(),
                   js.get("bar") instanceof Boolean);
    }

    public void testSimpleStream() throws Exception {
        String script =
                "config = {\n" +
                "  foo : 27.0\n" +
                "}";
        JStorage js = new JStorage(new StringReader(script));

        assertEquals(1, js.size());
        assertEquals(27, asInt(js.get("foo")));
    }

    public void testBootstrap() {
        JStorage orig = new JStorage();

        try {
        	// Test empty storage
            assertBootstrap(orig);
            orig = new JStorage();
            orig.put("foo", 27);                        
            assertBootstrap(orig);

            orig = new JStorage();
            orig.put("bool", true);
            assertBootstrap(orig);

            orig = new JStorage();
            orig.put("bool", true);
            orig.put("myint", 128);
            assertBootstrap(orig);

            orig = new JStorage();
            orig.put("bool", true);
            orig.put("myint", 128);
            orig.put("mylist", (Serializable)Arrays.asList("one", "two"));
            assertBootstrap(orig);

            orig = new JStorage();
            orig.put("bool", true);
            orig.put("myint", 128);
            orig.put("mylist", (Serializable)Arrays.asList("one", "two"));
            orig.createSubStorage("emptysub");
            assertBootstrap(orig);

            orig = new JStorage();
            orig.put("bool", true);
            orig.put("myint", 128);
            orig.put("mylist", (Serializable)Arrays.asList("one", "two"));
            orig.createSubStorage("mysub").put("subint", 27);
            assertBootstrap(orig);
        } catch (Exception e) {
            fail("Error bootstrapping:\n" + orig.toString()
                 + "\nError was: " + Strings.getStackTrace(e));
        }
    }

    public static void assertBootstrap(JStorage test) {
        JStorage clone = new JStorage(new StringReader(test.toString()));
        
        assertEquals(test.toString(), clone.toString());               
        assertEquals(test, clone);
    }

    public static boolean asBoolean(Serializable s) {
        return Boolean.parseBoolean(s.toString());
    }

    public static int asInt(Serializable s) {
        return (int)Double.parseDouble(s.toString());
    }
}