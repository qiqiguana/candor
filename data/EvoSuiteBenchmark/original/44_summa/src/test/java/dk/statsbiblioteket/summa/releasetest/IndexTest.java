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
package dk.statsbiblioteket.summa.releasetest;

import dk.statsbiblioteket.summa.common.Record;
import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.configuration.Resolver;
import dk.statsbiblioteket.summa.common.filter.Filter;
import dk.statsbiblioteket.summa.common.filter.FilterControl;
import dk.statsbiblioteket.summa.common.filter.object.FilterSequence;
import dk.statsbiblioteket.summa.common.lucene.LuceneIndexUtils;
import dk.statsbiblioteket.summa.common.unittest.LuceneTestHelper;
import dk.statsbiblioteket.summa.common.unittest.NoExitTestCase;
import dk.statsbiblioteket.summa.control.api.Status;
import dk.statsbiblioteket.summa.control.service.FilterService;
import dk.statsbiblioteket.summa.index.IndexControllerImpl;
import dk.statsbiblioteket.summa.storage.api.Storage;
import dk.statsbiblioteket.util.Files;
import dk.statsbiblioteket.util.Streams;
import dk.statsbiblioteket.util.qa.QAInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;

/*
 * IMPORTANT: Due to problems with releasing JDBC, the tests cannot be run
 * in succession, but must be started one at a time in their own JVM.
 */
@SuppressWarnings({"DuplicateStringLiteralInspection"})
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.IN_DEVELOPMENT,
        author = "te")
public class IndexTest extends NoExitTestCase {
    private static Log log = LogFactory.getLog(IndexTest.class);

    public static final String TESTBASE = "fagref";
    public static final int NUM_RECORDS = 3;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        ReleaseHelper.cleanup();
        if (INDEX_ROOT.exists()) {
            Files.delete(INDEX_ROOT);
        }
        INDEX_ROOT.mkdirs();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        if (INDEX_ROOT.exists()) {
            Files.delete(INDEX_ROOT);
        }
    }

    public void testIngest() throws Exception {
        final String STORAGE_ID = "intesttest_storage";
        createSampleStorage(STORAGE_ID).close();
    }

    public void testNonIterativeIndexing() throws Exception {
        final String STORAGE_ID = "noniterative_storage";
        setFagrefProperties(STORAGE_ID);
        Storage storage = createSampleStorage(STORAGE_ID);

        assertEquals("There should be no existing indexes", 0, countIndexes());
        Configuration indexConf = loadFagrefProperties(
                STORAGE_ID, "integration/search/IndexTest_IndexConfiguration.xml");
        updateIndex(indexConf);
        assertEquals("After update one the number of indexes should be correct", 1, countIndexes());
        updateIndex(indexConf);
        assertEquals("After update two the number of indexes should be correct", 2, countIndexes());
        storage.close();
    }

    public void testIterativeIndexing() throws Exception {
        final String STORAGE_ID = "iterative_storage";
        setFagrefProperties(STORAGE_ID);
        Storage storage = createSampleStorage(STORAGE_ID);

        assertEquals("There should be no existing indexes", 0, countIndexes());
        Configuration indexConf = loadFagrefProperties(
                STORAGE_ID, "integration/search/IndexTest_IndexConfiguration.xml");
        indexConf.getSubConfigurations(FilterControl.CONF_CHAINS).get(0).
                getSubConfigurations(FilterSequence.CONF_FILTERS).get(4).
//                getSubConfiguration("IndexUpdate").
        set(IndexControllerImpl.CONF_CREATE_NEW_INDEX, false);
        updateIndex(indexConf);
        assertEquals("After update one the number of indexes should be correct", 1, countIndexes());
        updateIndex(indexConf);
        assertEquals("After update two the number of indexes should be correct", 1, countIndexes());
        storage.close();
    }

    private int countIndexes() {
        if (!INDEX_ROOT.exists()) {
            return 0;
        }
        return INDEX_ROOT.listFiles().length;
    }

    // TODO: Implement proper shutdown of single tests

    public static final File INDEX_ROOT = new File(System.getProperty("java.io.tmpdir"), "testindex");

    /**
     * Tests the workflow from files on disk to finished index.
     *
     * @throws Exception if the workflow failed.
     */
    public void testWorkflow() throws Exception {
        final String STORAGE_ID = "workflow_storage";
        Storage storage = createSampleStorage(STORAGE_ID);

        // Index chain setup
        URL xsltLocation = Thread.currentThread().getContextClassLoader().getResource
                ("integration/fagref/fagref_index.xsl");
        assertNotNull("The original xslt location should not be null", xsltLocation);
        String descriptorLocation = "file://" + Thread.currentThread().getContextClassLoader().getResource(
                "integration/fagref/fagref_IndexDescriptor.xml").getFile();
//        System.out.println(descriptorLocation);
        Configuration indexConf = loadFagrefProperties(STORAGE_ID, "integration/fagref/fagref_index_setup.xml");

        assertNotNull("Configuration should contain "
                      + FilterControl.CONF_CHAINS, indexConf.getSubConfigurations(FilterControl.CONF_CHAINS));

        FilterService indexService = new FilterService(indexConf);
        indexService.start();

        waitForService(indexService);

        List<Filter> filters = indexService.getFilterControl().getPumps().get(0).getFilters();
        File indexLocation = ((IndexControllerImpl) filters.get(filters.size() - 1)).
                getIndexLocation();

        String[] EXPECTED_IDS = new String[]{"fagref:gm@example.com", "fagref:hj@example.com", "fagref:jh@example.com"};
        LuceneTestHelper.verifyContent(new File(indexLocation, LuceneIndexUtils.LUCENE_FOLDER), EXPECTED_IDS);

        storage.close();
    }

    /**
     * Create a Storage and fill it with test-data, ready for indexing.
     *
     * @param storageName the RMI-exposed name for the Storage.
     * @return the StorageService containing the filled Storage.
     * @throws Exception if the fill failed.
     */
    public static Storage createSampleStorage(String storageName) throws Exception {
        Storage storage = ReleaseHelper.startStorage(storageName);
        fillStorage(storageName);
        return storage;
    }

    public void testGetResource() {
        URL dataLocation = Resolver.getURL("integration/fagref/fagref_testdata.txt");
        assertNotNull("The test data resource (.txt) should not be null", dataLocation);
    }


    // TODO: Use property substitution instead of replace
    public static void fillStorage(String storage) throws Exception {
        // Ingest
        URL dataLocation = Resolver.getURL("integration/fagref/fagref_testdata.txt");
        assertNotNull("The data location should not be null", dataLocation);
        File ingestRoot = new File(dataLocation.getFile()).getParentFile();
        System.setProperty("fagref_filter_storage", ReleaseHelper.STORAGE_RMI_PREFIX + storage);
        String filterConfString = Streams.getUTF8Resource("integration/fagref/fagref_filter_setup.xml");
        filterConfString = filterConfString.replace("/tmp/summatest/data/fagref", ingestRoot.toString());
        assertFalse("Replace should work", filterConfString.contains("/tmp/summatest/data/fagref"));
        File filterConfFile = new File(System.getProperty("java.io.tmpdir"), "filterConf.xml");
        Files.saveString(filterConfString, filterConfFile);

        assertTrue("The filter conf. should exist", filterConfFile.exists());
        Configuration filterConf = loadFagrefProperties(storage, filterConfFile.getPath());
        assertNotNull("Configuration should contain " + FilterControl.CONF_CHAINS,
                      filterConf.getSubConfigurations(FilterControl.CONF_CHAINS));

        FilterService ingester = new FilterService(filterConf);
        ingester.start();
        waitForService(ingester);

        int recordCount = ReleaseHelper.getRecords(storage).size();
        assertEquals("The number of Records in Storage should be as expected", NUM_RECORDS, recordCount);

        Record gurli = ReleaseHelper.getRecords(storage).get(0);
        String fileContent = Resolver.getUTF8Content("integration/fagref/gurli.margrethe.xml");
        assertEquals("The stored content should match the file-content",
                     fileContent.trim(), gurli.getContentAsUTF8().trim());
    }

    public static void waitForService(FilterService service) throws RemoteException, InterruptedException {
        int TIMEOUT = 10000;
        waitForService(service, TIMEOUT);
    }

    public static void waitForService(FilterService service, int timeout) throws RemoteException, InterruptedException {
        long endTime = System.currentTimeMillis() + timeout;
        log.debug("Waiting a maximum of " + timeout + " ms for service");
        while (service.getStatus().getCode() != Status.CODE.stopped && System.currentTimeMillis() < endTime) {
            log.trace("Sleeping a bit");
            Thread.sleep(100);
        }
        assertEquals("The service '" + service + "' should have stopped by now",
                     service.getStatus().getCode(), Status.CODE.stopped);
        log.debug("Finished waiting for service");
    }

    public static Configuration loadFagrefProperties(String storage, String location) {
        setFagrefProperties(storage);
        return ReleaseHelper.loadGeneralConfiguration(storage, location);
    }

    private static void setFagrefProperties(String storageID) {
        URL xsltLocation = Resolver.getURL("integration/search/fagref_xslt/fagref_index.xsl");
        assertNotNull("The fagref xslt location should not be null", xsltLocation);
        System.setProperty("fagref_xslt", xsltLocation.getFile());

        URL descriptorLocation = Resolver.getURL("integration/search/SearchTest_IndexDescriptor.xml");
        assertNotNull("The descriptor location should not be null", descriptorLocation);
        System.setProperty("fagref_descriptor", descriptorLocation.getFile());

        // TODO: Consider cleanup of the index folder on tearDown
    }

    public static void updateIndex(Configuration conf) throws Exception {

        /*
        Configuration chain = conf.getSubConfigurations(
                FilterControl.CONF_CHAINS).get(0);
        chain.getSubConfigurations(FilterSequence.CONF_FILTERS).get(1).
//        chain.getSubConfiguration("FagrefTransformer").
                set(XMLTransformer.CONF_XSLT, xsltLocation.getFile());
        chain.getSubConfigurations(FilterSequence.CONF_FILTERS).get(3).
//        chain.getSubConfiguration("DocumentCreator").
                getSubConfiguration(
                IndexDescriptor.CONF_DESCRIPTOR).
                set(IndexDescriptor.CONF_ABSOLUTE_LOCATION,
                    descriptorLocation.getFile());
        chain.getSubConfigurations(FilterSequence.CONF_FILTERS).get(4).
//        chain.getSubConfiguration("IndexUpdate").
                set(IndexControllerImpl.CONF_INDEX_ROOT_LOCATION,
                    SearchTest.INDEX_ROOT.toString());

        // Index Descriptor
        chain.getSubConfigurations(FilterSequence.CONF_FILTERS).get(4).
//        chain.getSubConfiguration("IndexUpdate").
        getSubConfiguration(IndexDescriptor.CONF_DESCRIPTOR).
                set(IndexDescriptor.CONF_ABSOLUTE_LOCATION,
                    descriptorLocation.getFile());
          */

        FilterService indexService = new FilterService(conf);
        indexService.start();
        waitForService(indexService, Integer.MAX_VALUE);
        indexService.stop();
    }
}
