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
package dk.statsbiblioteket.summa.preingest;

import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.configuration.Resolver;
import dk.statsbiblioteket.summa.common.filter.Payload;
import dk.statsbiblioteket.summa.common.filter.object.ObjectFilter;
import dk.statsbiblioteket.summa.ingest.split.SBMARCParser;
import dk.statsbiblioteket.summa.ingest.split.StreamController;
import dk.statsbiblioteket.summa.ingest.stream.Aleph2XML2;
import dk.statsbiblioteket.summa.ingest.stream.FileReader;
import dk.statsbiblioteket.util.qa.QAInfo;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URL;
import java.util.regex.Pattern;

/**
 * Aleph2XML2 Tester.
 */
@QAInfo(level = QAInfo.Level.NORMAL,
       state = QAInfo.State.IN_DEVELOPMENT,
       author = "te, hal")
public class Aleph2XML2Test extends TestCase {
    private static Log log = LogFactory.getLog(Aleph2XML2Test.class);

    public Aleph2XML2Test(String name) {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    public static Test suite() {
        return new TestSuite(Aleph2XML2Test.class);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public ObjectFilter getStreamReader() {
        URL inputDir = Resolver.getURL("ingest/aleph/");
        log.debug("getStreamReader: Located root " + inputDir.getFile());
        Configuration conf = Configuration.newMemoryBased();
        conf.set(FileReader.CONF_ROOT_FOLDER, inputDir.getFile());
        conf.set(FileReader.CONF_RECURSIVE, true);
        conf.set(FileReader.CONF_FILE_PATTERN, ".*\\.data");
        conf.set(FileReader.CONF_COMPLETED_POSTFIX, "");
        return new FileReader(conf);
    }

    public void testGetStreamReader() throws Exception {
        ObjectFilter reader = getStreamReader();
        assertTrue("There should be at least one file available",
                   reader.hasNext());
        Payload payload = null;
        for (int i = 0 ; i < 3; i++) {
            assertTrue("There should be more payload available",
                       reader.hasNext());
            payload = reader.next();
        }
        assertFalse("There should be no more files available",
                    reader.hasNext());
        //noinspection ConstantConditions
        assertNotNull("The Payload should have a Stream", payload.getStream());
        payload.close();
    }

    public ObjectFilter getAlephChain() throws Exception {
        ObjectFilter streamReader = getStreamReader();
        Configuration conf = Configuration.newMemoryBased();
        conf.set(StreamController.CONF_PARSER, SBMARCParser.class);
        conf.set(SBMARCParser.CONF_BASE, "foo");
        conf.set(SBMARCParser.CONF_ID_PREFIX, "aleph:");

        ObjectFilter alephConverter = new Aleph2XML2(conf);
        alephConverter.setSource(streamReader);

        ObjectFilter danMARC2 = new StreamController(conf);
        danMARC2.setSource(alephConverter);
        return danMARC2;
    }

    public void testSimplePull() throws Exception {
        ObjectFilter alephChain = getAlephChain();
        for (int i = 0 ; i < 3; i++) {
            assertTrue("There should be more payload available",
                       alephChain.hasNext());
            Payload payload = alephChain.next();
            assertNotNull("All payloads should have Records",
                          payload.getRecord());
        }
    }

    public void testFullConversion() throws Exception {
        ObjectFilter alephChain = getAlephChain();
        Payload deleted = alephChain.next();
        assertTrue("The first Record should be marked as deleted",
                   deleted.getRecord().isDeleted());
        assertEquals("The first Record should have the right ID",
                     "aleph:MAT01-000000001", deleted.getId());
        testDeleted(deleted);

        Payload deleted2 = alephChain.next();
        assertTrue("The second Record should be marked as deleted",
                   deleted2.getRecord().isDeleted());
        assertEquals("The second Record should have the right ID",
                     "aleph:GEO01-000005108", deleted2.getId());
        testDeleted(deleted2);

        Payload plain = alephChain.next();
        assertFalse("The third Record should not be marked as deleted",
                    plain.getRecord().isDeleted());
        assertEquals("The third Record should have the right ID",
                     "aleph:KEM01-000000001", plain.getId());
    }

    Pattern storeID =
            Pattern.compile("(?s).*<datafield tag=\"994\" ind1=\"0\" " +
                           "ind2=\"0\">\n<subfield code=\"z\">.+</subfield>.*");
    Pattern invalidID =
            Pattern.compile("(?s).*<datafield tag=\"994\" ind1=\"0\" " +
                   "ind2=\"0\">\n<subfield code=\"z\">-000000001</subfield>.*");

    private void testDeleted(Payload payload) throws Exception {
        assertTrue("There should be a 994.z with the ID for the record in "
                   + payload, storeID.matcher(
                payload.getRecord().getContentAsUTF8()).matches());
        assertFalse("The ID -000000001 should not be generated for " + payload,
                   invalidID.matcher(
                payload.getRecord().getContentAsUTF8()).matches());
    }

}




