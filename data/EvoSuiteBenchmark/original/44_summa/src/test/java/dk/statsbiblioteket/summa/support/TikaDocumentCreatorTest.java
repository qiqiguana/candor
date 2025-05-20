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
package dk.statsbiblioteket.summa.support;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import dk.statsbiblioteket.summa.common.filter.Payload;
import dk.statsbiblioteket.summa.common.index.IndexDescriptor;
import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.configuration.Resolver;
import dk.statsbiblioteket.summa.common.unittest.PayloadFeederHelper;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.io.FileInputStream;

public class TikaDocumentCreatorTest extends TestCase {
    private static Logger log = Logger.getLogger(TikaDocumentCreatorTest.class);
    public TikaDocumentCreatorTest(String name) {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public static Test suite() {
        return new TestSuite(TikaDocumentCreatorTest.class);
    }

    public void testStrictXHTML() throws Exception {
        String descriptorLocation = "support/tika/TikaTest_IndexDescriptor.xml";

        Payload strict = new Payload(new FileInputStream(Resolver.getFile(
                "support/tika/strict1.xhtml")),"support/tika/strict1.xhtml");
        Payload lax = new Payload(new FileInputStream(Resolver.getFile(
                "support/tika/lax1.xhtml")),"support/tika/lax1.xhtml");
        PayloadFeederHelper feeder =
                new PayloadFeederHelper(Arrays.asList(strict, lax));
        Configuration conf = Configuration.newMemoryBased();
        Configuration idConf = conf.createSubConfiguration(
                IndexDescriptor.CONF_DESCRIPTOR);
        idConf.set(IndexDescriptor.CONF_ABSOLUTE_LOCATION, descriptorLocation);
        TikaDocumentCreator tika;
        try {
            tika = new TikaDocumentCreator(conf);
            tika.setSource(feeder);
            log.info("**** Dumping strict");
            tika.next();
            log.info("**** Dumping lax");
            tika.next();            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Craeting of TikaDocument fails.");
        }
    }
}