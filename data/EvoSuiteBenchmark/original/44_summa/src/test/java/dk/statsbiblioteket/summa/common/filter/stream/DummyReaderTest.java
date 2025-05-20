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
package dk.statsbiblioteket.summa.common.filter.stream;

import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.configuration.storage.MemoryStorage;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * DummyReader Tester.
 *
 * @author <Authors name>
 * @since <pre>02/18/2008</pre>
 * @version 1.0
 */
public class DummyReaderTest extends TestCase {
    public DummyReaderTest(String name) {
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
        return new TestSuite(DummyReaderTest.class);
    }

    public void testBasics() throws Exception {
        long BODY_SIZE = 100;
        int  BODY_COUNT =  3;

        MemoryStorage memStore = new MemoryStorage();
        memStore.put(DummyReader.CONF_BODY_COUNT, BODY_COUNT);
        memStore.put(DummyReader.CONF_BODY_SIZE, BODY_SIZE);
        Configuration conf = new Configuration(memStore);
        DummyReader dummy = new DummyReader(conf);

        for (int b = 0 ; b < BODY_COUNT ; b++) {
            long size = BODY_SIZE;
            int[] sizeArray = new int[8];
            for (int i = 7 ; i >= 0 ; i--) {
                sizeArray[i] = (byte)size;
                size >>>= 8;
            }

            assertTrue("There should be a record", dummy.hasNext());

            byte[] content = dummy.next().getRecord().getContent();

            for (int l=0; l<8; l++) {
                assertEquals("The length-bytes should match", sizeArray[l], content[l]);
            }
            for (int i = 8 ; i < BODY_SIZE+8 ; i++) {
                assertNotNull(content[i]);
            }
        }
        assertFalse("No more records", dummy.hasNext());
    }
}



