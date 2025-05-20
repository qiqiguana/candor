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
package dk.statsbiblioteket.summa.common.filter.object;

import dk.statsbiblioteket.summa.common.Record;
import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.filter.Payload;
import junit.framework.TestCase;

import java.io.StringReader;

/**
 * Test cases for {@link ScriptFilter}
 */
public class ScriptFilterTest extends TestCase {

    public PayloadBufferFilter prepareFilterChain(ObjectFilter filter,
                                                  Record... records) {
        // Set up the source filter
        PushFilter source = new PushFilter(records.length+1, 2048);

        for (int i = 0; i < records.length; i++) {
            Payload p = new Payload(records[i]);
            source.add(p);
        }
        source.signalEOF();

        // Set up the endpoint filter
        PayloadBufferFilter buf = new PayloadBufferFilter(
                                                Configuration.newMemoryBased());

        // Connect filters
        filter.setSource(source);
        buf.setSource(filter);

        return buf;
    }

    public void testRenameRecordIdJS() throws Exception {
        ObjectFilter filter = new ScriptFilter(
                   new StringReader("payload.getRecord().setId('processed');"));
        PayloadBufferFilter buf = prepareFilterChain(
                       filter,
                       new Record("id1", "base1", "test content 1".getBytes()),
                       new Record("id2", "base1", "test content 2".getBytes()));

        // Flush the filter chain
        while (buf.pump()){}

        assertEquals(2, buf.size());
        assertEquals("processed", buf.get(0).getRecord().getId());
        assertEquals("processed", buf.get(1).getRecord().getId());
    }

    public void testDropAllJS() throws Exception {
        ObjectFilter filter = new ScriptFilter(
                   new StringReader("allowPayload = false;"));
        PayloadBufferFilter buf = prepareFilterChain(
                       filter,
                       new Record("id1", "base1", "test content 1".getBytes()),
                       new Record("id2", "base1", "test content 2".getBytes()));

        // Flush the filter chain
        while (buf.pump()){}

        assertEquals(0, buf.size());
    }

    public void testDropOneJS() throws Exception {
        ObjectFilter filter = new ScriptFilter(
                   new StringReader(
                           "if (payload.getRecord().getId() == 'id1') { " +
                           "    allowPayload = false;" +
                           "}"));
        PayloadBufferFilter buf = prepareFilterChain(
                       filter,
                       new Record("id1", "base1", "test content 1".getBytes()),
                       new Record("id2", "base1", "test content 2".getBytes()));

        // Flush the filter chain
        while (buf.pump()){}

        assertEquals(1, buf.size());
        assertEquals("id2", buf.get(0).getRecord().getId());
    }

    public void testBasePrefixRecordIdJS() throws Exception {
        ObjectFilter filter = new ScriptFilter(
                   new StringReader(
            "var record = payload.getRecord();"+
            "if (!record.getId().startsWith(record.getBase())) {"+
            "    record.setId(record.getBase() + \"_\" + record.getId())"+
            "}"+
            "if (record.getId().endsWith('taboo')) {"+
            "    allowPayload = false;" +
            "    feedbackMessage = 'Record id ends with \"taboo\"';" +
            "}"));

        PayloadBufferFilter buf = prepareFilterChain(
                       filter,
                       new Record("id1", "base1", "test content 1".getBytes()),
                       new Record("id2", "base1", "test content 2".getBytes()),
                       new Record("taboo", "base1", "test content".getBytes()));

        // Flush the filter chain
        while (buf.pump()){}

        assertEquals(2, buf.size());
        assertEquals("base1_id1", buf.get(0).getRecord().getId());
        assertEquals("base1_id2", buf.get(1).getRecord().getId());
    }

    public void testInlineScript() throws Exception {
        ObjectFilter filter = new ScriptFilter(
                                           Configuration.load("common/inline-js.xml"));
        PayloadBufferFilter buf = prepareFilterChain(
                       filter,
                       new Record("id1", "base1", "test content 1".getBytes()));

        // Flush the filter chain
        while (buf.pump()){}

        assertEquals(1, buf.size());
        assertEquals("inlineJavascript", buf.get(0).getRecord().getId());
    }

    public void testJavaCallbacksExternal() throws Exception {
        ObjectFilter filter = new ScriptFilter(
              Configuration.newMemoryBased(
                      ScriptFilter.CONF_SCRIPT_URL, "common/java-callbacks.js"
              ));
        PayloadBufferFilter buf = prepareFilterChain(
                       filter,
                       new Record("id1", "base1",
                                  ("<root>\n"
                                   + "  <child1>Foo</child1>\n"
                                   + "  <child2>Bar</child2>\n"
                                   + "</root>\n").getBytes()));
        // Flush the filter chain
        while (buf.pump()) { }

        assertEquals(1, buf.size());
        assertEquals("Bar", buf.get(0).getRecord().getContentAsUTF8());

    }

}

