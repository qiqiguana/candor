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
package dk.statsbiblioteket.summa.common.pool;

import dk.statsbiblioteket.util.qa.QAInfo;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;

/**
 * DiskPool Tester.
 */
@SuppressWarnings({"DuplicateStringLiteralInspection"})
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.IN_DEVELOPMENT,
        author = "te")
public class DiskPoolTest extends StringPoolSuper {
    private Log log = LogFactory.getLog(DiskPoolTest.class);
    public DiskPoolTest(String name) {
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
        return new TestSuite(DiskPoolTest.class);
    }

    @Override
    public SortedPool<String> getPool(File location,
                                      String name) throws Exception {
        DiskStringPool pool = new DiskStringPool(defaultCollator);
        //DiskStringPool pool = new DiskStringPool(null);
        pool.open(location, name, false, true);
        return pool;
    }

    public void testSpeed100() throws Exception {
        testSpeed(100);
    }

    public void testSpeed10K() throws Exception {
        testSpeed(10000);
    }

    public void atestSpeed100K() throws Exception {
        testSpeed(100000);
    }

    public void atestSpeed1M() throws Exception {
        testSpeed(1000000);
    }

    public void atestDirty1M() throws Exception {
        log.info("Dirty: " + createSample(getPool(), 1000000, true).
                getSpendTime());
    }

    public void testSpeed(int count) throws Exception {
        String plain = createSample(getPool(), count, false).getSpendTime();
        String dirty = createSample(getPool(), count, true).getSpendTime();
        log.info("Plain: " + plain);
        log.info("Dirty: " + dirty);
    }
}