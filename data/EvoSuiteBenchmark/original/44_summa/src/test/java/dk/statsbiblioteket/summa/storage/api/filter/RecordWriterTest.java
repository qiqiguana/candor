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
package dk.statsbiblioteket.summa.storage.api.filter;

import dk.statsbiblioteket.summa.common.Record;
import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.filter.Filter;
import dk.statsbiblioteket.summa.common.filter.Payload;
import dk.statsbiblioteket.summa.common.filter.object.ObjectFilter;
import dk.statsbiblioteket.summa.storage.api.Storage;
import dk.statsbiblioteket.summa.storage.api.StorageFactory;
import dk.statsbiblioteket.summa.storage.api.StorageIterator;
import dk.statsbiblioteket.summa.storage.database.DatabaseStorage;
import dk.statsbiblioteket.util.Files;
import dk.statsbiblioteket.util.qa.QAInfo;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings({"DuplicateStringLiteralInspection"})
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.IN_DEVELOPMENT,
        author = "te")
public class RecordWriterTest extends TestCase {

    Log log = LogFactory.getLog(RecordWriterTest.class);

    private static final File storageLocation =
            new File(System.getProperty("java.io.tmpdir"), "kabloey");

    Storage storage;
    RecordWriter writer;

    public RecordWriterTest(String name) {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        if (storageLocation.exists()) {
            Files.delete(storageLocation);
        }
        assertTrue("Storage location '" + storageLocation
                   + "' should be created", storageLocation.mkdirs());

        Configuration conf = Configuration.newMemoryBased();
        Files.delete(storageLocation);
        conf.set(Storage.CONF_CLASS,
                 "dk.statsbiblioteket.summa.storage.database.h2.H2Storage");
        conf.set(DatabaseStorage.CONF_LOCATION, storageLocation.toString());
        storage = StorageFactory.createStorage(conf);
        assertNotNull("A storage should be available now", storage);

        writer = new RecordWriter(storage, 100, 1000);

        // We need to not emit interrupt() on the DB thread before it is ready
        Thread.sleep(200);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public static Test suite() {
        return new TestSuite(RecordWriterTest.class);
    }

    public void testFewRecords() throws Exception {
        writer.setSource(new ObjectProvider(5));
        while (writer.pump()) {
            // Wait
        }
        writer.close(true);
        assertBaseCount("fooBase", 5);
    }

    public void testBatchSize() throws Exception {
        writer.setSource(new ObjectProvider(100));
        while (writer.pump()) {
            // Wait
        }
        writer.close(true);
        assertBaseCount("fooBase", 100);
    }

    public void disabledtestBatchOvershoot() throws Exception {
        writer.setSource(new ObjectProvider(100007));
        while (writer.pump()) {
            // Wait
        }
        writer.close(true);
        log.info("Flushed records. Checking count");
        assertBaseCount("fooBase", 100007);
    }

    /* ObjectFilter test-implementation */
    class ObjectProvider implements ObjectFilter {
        List<Record> records;

        public ObjectProvider(int objectCount) {
            records = new ArrayList<Record>(objectCount);
            for (int i = 0 ; i < objectCount ; i++) {
                records.add(new Record("Dummy-" + i, "fooBase", new byte[10]));
            }
        }


        @Override
        public boolean hasNext() {
            return !records.isEmpty();
        }

        @Override
        public Payload next() {
            if (!hasNext()) {
                //noinspection DuplicateStringLiteralInspection
                throw new NoSuchElementException("No more Records");
            }
            return new Payload(records.remove(0));
        }

        @Override
        public void remove() {
            if (!hasNext()) {
                //noinspection DuplicateStringLiteralInspection
                throw new NoSuchElementException("No more Records");
            }
            records.remove(0);
        }

        @Override
        public void setSource(Filter filter) {
            // Do nothing
        }

        @Override
        public boolean pump() throws IOException {
            if (!hasNext()) {
                return false;
            }
            Payload next = next();
            if (next == null) {
                return false;
            }
            next.close();
            return true;
        }

        @Override
        public void close(boolean success) {
            records.clear();
        }
    }

    public void assertBaseCount (String base, long expected) throws Exception {
        long iterKey = storage.getRecordsModifiedAfter(0, base, null);
        Iterator<Record> iter = new StorageIterator(storage, iterKey);
        long actual = 0;
        while (iter.hasNext()) {
            iter.next();
            actual++;
        }

        if (actual != expected) {
            fail("Base '" + base + "' should contain " + expected
                 + " records, but found " + actual);
        }
    }
}




