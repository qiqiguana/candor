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

import dk.statsbiblioteket.summa.common.Record;
import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.configuration.Resolver;
import dk.statsbiblioteket.summa.common.filter.Payload;
import dk.statsbiblioteket.util.Strings;
import junit.framework.TestCase;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Test cases for {@link ThreadedStreamParser}
 */
public class ThreadedStreamParserTest extends TestCase {

    abstract static class BaseParser extends ThreadedStreamParser {

        private int numProcessed;

        BaseParser(Integer queueSize, Integer queueByteSize, Integer timeOut) {
            super(createConfig(queueSize, queueByteSize, timeOut));
            numProcessed = 0;
        }

        private static Configuration createConfig(Integer queueSize, Integer queueByteSize, Integer timeOut) {
            Configuration conf = Configuration.newMemoryBased();
            if (queueSize != null) {
                conf.set(ThreadedStreamParser.CONF_QUEUE_SIZE, queueSize);
            }
            if (queueByteSize != null) {
                conf.set(ThreadedStreamParser.CONF_QUEUE_BYTESIZE, queueByteSize);
            }
            if (timeOut != null) {
                conf.set(ThreadedStreamParser.CONF_QUEUE_TIMEOUT, timeOut);
            }

            return conf;
        }
    }

    /**
     * Creates one payload per byte in the source stream
     */
    public static class PayloadFiller extends BaseParser {

        public PayloadFiller() {
            this(1000);
        }

        public PayloadFiller(Configuration conf) {
            this(conf.getInt("queuesize", 1000));
        }

        public PayloadFiller(int queueSize) {
            super(queueSize, Integer.MAX_VALUE, 1000);
        }

        @Override
        public void protectedRun(Payload source) {
            InputStream in = source.getStream();

            try {
                int codePoint;
                while ((codePoint = in.read()) != -1) {

                    addToQueue(new Record("id" + codePoint, "base", new byte[0]));
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

    /**
     * Run 20 payloads through a parser with room for 5
     */
    public void testExceedCapacity() {
        PayloadFiller filler = new PayloadFiller(5);
        filler.open(new Payload(new ByteArrayInputStream(new byte[20])));

        int count = 0;
        while (filler.hasNext()) {
            filler.next();
            count++;

            if (count > 20) {
                // We break out here because the test might
                // otherwise end in an endless loop
                fail("Receieved more than 20 payloads, bailing out");
            }

            assertNull("No errors should occur but after " + count + " payloads got:"
                       + formatStackTrace(filler.getLastError()), filler.getLastError());
        }

        assertNull("No errors should occur but at end of run got:\n"
                   + formatStackTrace(filler.getLastError()), filler.getLastError());
        assertEquals("Exactly 20 payloads expected", 20, count);
    }

    public void testDoubleDefaultNamespace() throws Exception {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        inputFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
        // No resolving of external DTDs
        inputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(
                Resolver.getURL("ingest/double_default_oai.xml").openStream(), "utf-8");
        while (reader.hasNext()) {
            reader.next();
        }
    }

    static String formatStackTrace(Throwable t) {
        if (t == null) {
            return "<All good>";
        } else {
            return Strings.getStackTrace(t);
        }
    }
}

