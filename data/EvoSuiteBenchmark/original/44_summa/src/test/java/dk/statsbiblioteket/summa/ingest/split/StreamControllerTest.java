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
package dk.statsbiblioteket.summa.ingest.split;

import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.filter.Payload;
import dk.statsbiblioteket.summa.common.filter.object.PushFilter;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;

import static dk.statsbiblioteket.summa.ingest.split.ThreadedStreamParserTest.PayloadFiller;
import static dk.statsbiblioteket.summa.ingest.split.ThreadedStreamParserTest.formatStackTrace;

/**
 * Test cases for StreamController
 *
 * @author mke
 * @since Oct 13, 2009
 */
public class StreamControllerTest extends TestCase {
    private static Log log = LogFactory.getLog(StreamControllerTest.class);
    StreamController controller;
    PayloadFiller filler;

    public void testMultipleSourcePayloads() {
        PushFilter source = new PushFilter(100, 10000);
        source.add(streamPayloadForBytes());
        source.add(streamPayloadForBytes(0, 1, 2));
        source.add(streamPayloadForBytes(3));
        source.add(streamPayloadForBytes(4, 5));
        source.signalEOF();

        controller = new StreamController(Configuration.newMemoryBased(
                StreamController.CONF_PARSER, PayloadFiller.class
        ));
        controller.setSource(source);
        filler = (PayloadFiller)controller.parser;

        int count = 0;
        while (controller.hasNext()) {
            Payload p = controller.next();

            if (count > 6) {
                // We break out here because the test might
                // otherwise end in an endless loop
                fail("Receieved more than 6 payloads, bailing out");
            }

            assertEquals("Ids should match expected sequence",
                         "id" + count, p.getId());

            assertNull("No errors should occur but after "
                       + count + " payloads got:"
                       + formatStackTrace(filler.getLastError()),
                       filler.getLastError());

            count++;
            log.info("Extracted payload " + p);
        }

        assertNull("No errors should occur but at end of run got:\n"
                   + formatStackTrace(filler.getLastError()),
                   filler.getLastError());
        assertEquals("Exactly 6 payloads expected", 6, count);

        controller.close(true);
        log.info("All good");
    }

    static Payload streamPayloadForBytes(int... bytes) {
        return new Payload(new ByteArrayInputStream(toByteArray(bytes)));
    }

    static byte[] toByteArray(int... ints) {
        byte[] bytes = new byte[ints.length];
        // Don't use System.arrayCopy because we want validation
        for (int i = 0; i < ints.length; i++) {
            bytes[i] = (byte)ints[i];
        }
        return bytes;
    }
}