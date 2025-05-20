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

import dk.statsbiblioteket.summa.common.Logging;
import dk.statsbiblioteket.summa.common.Record;
import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.configuration.Resolver;
import dk.statsbiblioteket.summa.common.filter.Filter;
import dk.statsbiblioteket.summa.common.filter.Payload;
import dk.statsbiblioteket.summa.common.filter.object.ObjectFilter;
import dk.statsbiblioteket.summa.common.rpc.ConnectionConsumer;
import dk.statsbiblioteket.summa.storage.api.QueryOptions;
import dk.statsbiblioteket.summa.storage.api.ReadableStorage;
import dk.statsbiblioteket.summa.storage.api.StorageIterator;
import dk.statsbiblioteket.summa.storage.api.StorageReaderClient;
import dk.statsbiblioteket.summa.storage.api.watch.StorageChangeListener;
import dk.statsbiblioteket.summa.storage.api.watch.StorageWatcher;
import dk.statsbiblioteket.util.qa.QAInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Retrieves Records from storage based on the criteria given in the properties.
 * Supports stopping during retrieval and resuming by timestamp.
 * </p><p>
 * Note: Besides the configurations stated below, the address for the Storage must be specified with the key
 * {@link ConnectionConsumer#CONF_RPC_TARGET}. The address can be a standard RMI address, such as
 * {@code //localhost:6789/storage}.
 */
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.IN_DEVELOPMENT,
        author = "te")
// TODO: If keepalive, the state should be saved at regular intervals
// TODO: Change the + 1000 ms when the time granularity of JavaDB improves
public class RecordReader implements ObjectFilter, StorageChangeListener {
    /**
     * Local log instance.
     */
    private static Log log = LogFactory.getLog(RecordReader.class);

    /**
     * The state of progress is stored in this file upon close. This allows for a workflow where a Storage is harvested
     * in parts.
     * </p><p>
     * The progress file is resolved to the default dir if it is not absolute.
     * </p><p>
     * This property is optional. Default is "<base>.progress.xml",
     * for example "horizon.progress.xml".
     * If no base is defined, the default value is "progress.xml".
     */
    public static final String CONF_PROGRESS_FILE = "summa.storage.recordreader.progressfile";
    /**
     * Default value for {@link #CONF_PROGRESS_FILE}.
     */
    public static final String DEFAULT_PROGRESS_FILE_POSTFIX = "progress.xml";

    /**
     * If true, the state of progress is stored in {@link #CONF_PROGRESS_FILE}.
     * This means that new runs will continue where the previous run left.
     * If no progress-file exists, a new one will be created.
     * </p><p>
     * Note that progress will only be stored in the event of a call to
     * {@link #close(boolean)} with a value of true.
     * </p><p>
     * This property is optional. Default is true.
     */
    public static final String CONF_USE_PERSISTENCE = "summa.storage.recordreader.usepersistence";
    /**
     * Default value for {@link #CONF_USE_PERSISTENCE}.
     */
    public static final boolean DEFAULT_USE_PERSISTENCE = true;

    /**
     * If true, any existing progress is ignored and the harvest from the Storage is restarted.
     * </p><p>
     * This property is optional. Default is false.
     */
    public static final String CONF_START_FROM_SCRATCH = "summa.storage.recordreader.startfromscratch";
    /**
     * Default value for {@link #CONF_START_FROM_SCRATCH}.
     */
    public static final boolean DEFAULT_START_FROM_SCRATCH = false;

    /**
     * The maximum number of Records to read before signaling EOF onwards in the filter chain.
     * Specifying -1 means no limit.
     * </p><p>
     * This property is optional. Default is -1 (disabled).
     */
    public static final String CONF_MAX_READ_RECORDS = "summa.storage.recordreader.maxread.records";
    /**
     * Default value for {@link #CONF_MAX_READ_RECORDS}.
     */
    public static final int DEFAULT_MAX_READ_RECORDS = -1;

    /**
     * The maximum number of seconds before signaling EOF onwards in the filter chain.
     * Specifying -1 means no limit.
     * </p><p>
     * This property is optional. Default is -1 (disabled).
     */
    public static final String CONF_MAX_READ_SECONDS = "summa.storage.recordreader.maxread.seconds";
    /**
     * Default value for {@link #CONF_MAX_READ_SECONDS}.
     */
    public static final int DEFAULT_MAX_READ_SECONDS = -1;

    /**
     * Only Records with matching base will be retrieved. Specifying the empty string as base means all bases.
     * No wildcards are allowed.
     * </p><p>
     * This property is optional. Default is "" (all records).
     */
    public static final String CONF_BASE = "summa.storage.recordreader.base";
    /**
     * Default value for {@link #CONF_BASE}.
     */
    public static final String DEFAULT_BASE = "";

    /**
     * Number of records to extract before storing a progress file.
     * The file will also be written if {@link #CONF_PROGRESS_GRACETIME} is passed.
     * <p/>
     * If the batch size is negative then progress will never be written  no matter how many records are read, but
     * surpassing the gracetime will still trigger a write.
     * <p/>
     * This property is optional.
     * Default is {@link #DEFAULT_PROGRESS_BATCH_SIZE}
     */
    public static final String CONF_PROGRESS_BATCH_SIZE = "summa.storage.recordreader.progress.batchsize";
    /**
     * Default value for {@link #CONF_PROGRESS_BATCH_SIZE}.
     */
    public static final long DEFAULT_PROGRESS_BATCH_SIZE = 500;

    /**
     * Number of milliseconds to wait while writing a progress file.
     * The progress file will also be written if {@link #CONF_PROGRESS_BATCH_SIZE} records has been extracted.
     * If the grace time is negative the progress file will never be written  because of time based rules, only by
     * surpassing the batch size.
     * <p/>
     * This property is optional.
     * Default is {@link #DEFAULT_PROGRESS_GRACETIME}
     */
    public static final String CONF_PROGRESS_GRACETIME = "summa.storage.recordreader.progress.gracetime";
    /**
     * Default value for {@link #CONF_PROGRESS_GRACETIME}.
     */
    public static final long DEFAULT_PROGRESS_GRACETIME = 5000;

    /**
     * If true, the connection should stay alive after the initial poll.
     * Calls to hasNext(), next() and pump() will block until new Records are added to the Storage or close is called.
     * </p><p>
     * Note: The property {@link StorageWatcher#CONF_POLL_INTERVAL} controls the  polling interval.
     * </p><p>
     * This property is optional. Default is false.
     */
    public static final String CONF_STAY_ALIVE = "summa.storage.recordreader.stayalive";
    /**
     * Default value for {@link #CONF_STAY_ALIVE}.
     */
    public static final boolean DEFAULT_STAY_ALIVE = false;

    /**
     * A boolean switch deciding whether or not to request expansion of child records.
     * The default value is {@code false}.
     */
    public static final String CONF_EXPAND_CHILDREN = "summa.storage.recordreader.expandchildren";
    /**
     * Default value for {@link #CONF_EXPAND_CHILDREN}.
     */
    public static final boolean DEFAULT_EXPAND_CHILDREN = false;

    /**
     * The maximum depth to expand children to if {@link #CONF_EXPAND_CHILDREN}  is true.
     * </p><p>
     * Optional. Default is 100. -1 means no limit (not recommended due to the possibility of endless recursion).
     */
    public static final String CONF_EXPANSION_DEPTH = "summa.storage.recordreader.expansiondepth";
    /**
     * Default value for {@link #CONF_EXPANSION_DEPTH}.
     */
    public static final int DEFAULT_EXPANSION_DEPTH = 100;

    /**
     * A boolean switch deciding whether or not to request expansion of  parent records.
     * The default value is {@code false}.
     */
    public static final String CONF_EXPAND_PARENTS = "summa.storage.recordreader.expandparents";
    /**
     * Default value for {@link #CONF_EXPAND_PARENTS}.
     */
    public static final boolean DEFAULT_EXPAND_PARENTS = false;

    /**
     * The maximum height to expand parents to if {@link #CONF_EXPAND_PARENTS} is true.
     * </p><p>
     * Optional. Default is 100. -1 means no limit (not recommended due to the possibility of endless recursion).
     */
    public static final String CONF_EXPANSION_HEIGHT = "summa.storage.recordreader.expansionheight";
    /**
     * Default value for {@link #CONF_EXPANSION_HEIGHT}.
     */
    public static final int DEFAULT_EXPANSION_HEIGHT = 100;


    /**
     * Will load the data column for methodsgetAllRecordsModifiedAfter CONF_LOAD_DATA_COLUMN
     * Ingest method will be much faster if data are not loaded. But for indexing, data are needed of course.
     */
    public static final String CONF_LOAD_DATA_COLUMN = "summa.storage.recordreader.load.data.column";
    /**
     * Default value for {@link #CONF_LOAD_DATA_COLUMN}.
     */
    public static final boolean DEFAULT_LOAD_DATA_COLUMN = true;

    /**
     * If true the current time is stored as start_time when the first Record is received. If a Record with
     * modified_time > start_time is received, no further Records are requested.
     * </p><p>
     * If the RecordReader is used for batch processing of Records from and to the same Storage, it is recommended
     * to set this to true to guard against infinite loops.
     * </p><p>
     * Optional. Default is false.
     */
    public static final String CONF_STOP_ON_NEWER = "summa.storage.recordreader.stop.on.newer";
    public static final boolean DEFAULT_STOP_ON_NEWER = false;

    /**
     * The batch size is the number of Records that are sent for each remote request to the Storage.
     * Setting this number very low (10-) will probably lead to poor performance. Setting this number very high (10000+)
     * will probably lead to OOM on either the Storage- or the client-side.
     * </p><p>
     * Note: The batch size is a soft request, as the Storage is free to ignore the parameter. However, a Storage should
     * not send more than the requested number and will normally send exactly the specified number of Records.
     * </p><p>
     * Optional. Default is {@link StorageIterator#DEFAULT_MAX_QUEUE_SIZE}, which is 100.
     */
    public static final String CONF_BATCH_SIZE = "summa.storage.recordreader.batch.size";
    public static final int DEFAULT_BATCH_SIZE = StorageIterator.DEFAULT_MAX_QUEUE_SIZE;


    /**
     * The readable storage.
     */
    @SuppressWarnings({"FieldCanBeLocal"})
    private ReadableStorage storage;
    /**
     * The base.
     */
    @SuppressWarnings({"FieldCanBeLocal"})
    private String base = DEFAULT_BASE;
    /**
     * The progress tracker.
     */
    private ProgressTracker progressTracker;
    private boolean usePersistence = DEFAULT_USE_PERSISTENCE;
    private boolean startFromScratch = DEFAULT_START_FROM_SCRATCH;
    private boolean expandChildren = DEFAULT_EXPAND_CHILDREN;
    private int maxExpansionDepth = DEFAULT_EXPANSION_DEPTH;
    private boolean expandParents = DEFAULT_EXPAND_PARENTS;
    private int maxExpansionHeight = DEFAULT_EXPANSION_HEIGHT;
    private int maxReadRecords = DEFAULT_MAX_READ_RECORDS;
    private int maxReadSeconds = DEFAULT_MAX_READ_SECONDS;
    private int batchSize = DEFAULT_BATCH_SIZE;
    private boolean loadData = DEFAULT_LOAD_DATA_COLUMN;

    private final boolean stopOnNewer;
    private long firstRecordReceivedTime = -1; // -1 means no records received

    /**
     * The storage watcher used to check for changes.
     */
    private final StorageWatcher storageWatcher;
    /**
     * True if end of file is reached.
     */
    private boolean eofReached = false;
    /**
     * Number of read records.
     */
    private long recordCounter = 0;
    /**
     * Start time for this record reader instance.
     */
    private long startTime = System.currentTimeMillis();
    /**
     * Time stamp for last processed record.
     */
    private long lastRecordTimestamp;
    /**
     * Time stamp for last iterator update.
     */
    private long lastIteratorUpdate;
    /**
     * Record iterator.
     */
    private Iterator<Record> recordIterator = null;

    /**
     * Connects to the Storage specified in the configuration and request an
     * iteration of the Records specified by the properties.
     *
     * @param conf contains setup information.
     * @throws java.io.IOException if it was not possible to connect to the
     *                             Storage or if the filename for the progress file was illegal.
     * @see #CONF_BASE
     * @see #CONF_MAX_READ_RECORDS
     * @see #CONF_MAX_READ_SECONDS
     * @see ConnectionConsumer#CONF_RPC_TARGET
     * @see StorageWatcher#CONF_POLL_INTERVAL
     * @see #CONF_PROGRESS_FILE
     * @see #CONF_START_FROM_SCRATCH
     * @see #CONF_USE_PERSISTENCE
     */
    public RecordReader(Configuration conf) throws IOException {
        log.trace("Constructing RecordReader");
        storage = new StorageReaderClient(conf);
        base = conf.getString(CONF_BASE, DEFAULT_BASE);

        if ("*".equals(base) || "".equals(base)) {
            log.trace("Catch-all base '" + base + "' was specified");
            base = null;
        }

        String progressFileString = conf.getString(CONF_PROGRESS_FILE, null);
        File progressFile;
        if (progressFileString == null || "".equals(progressFileString)) {
            progressFile = new File((base == null ? "" : base + ".") + DEFAULT_PROGRESS_FILE_POSTFIX);
            log.debug("No progress-file defined in key " + CONF_PROGRESS_FILE + ". Constructing progress file '"
                      + progressFile + "'");
        } else {
            progressFile = new File(progressFileString);
            //log.debug("Progress.file is " + progressFile.getCanonicalFile());
        }
        progressFile = Resolver.getPersistentFile(progressFile);

        usePersistence = conf.getBoolean(CONF_USE_PERSISTENCE, DEFAULT_USE_PERSISTENCE);
        startFromScratch = conf.getBoolean(CONF_START_FROM_SCRATCH, DEFAULT_START_FROM_SCRATCH);
        expandChildren = conf.getBoolean(CONF_EXPAND_CHILDREN, DEFAULT_EXPAND_CHILDREN);
        maxExpansionDepth = conf.getInt(CONF_EXPANSION_DEPTH, maxExpansionDepth);
        expandParents = conf.getBoolean(CONF_EXPAND_PARENTS, DEFAULT_EXPAND_PARENTS);
        maxExpansionHeight = conf.getInt(CONF_EXPANSION_HEIGHT, maxExpansionHeight);
        maxReadRecords = conf.getInt(CONF_MAX_READ_RECORDS, DEFAULT_MAX_READ_RECORDS);
        maxReadSeconds = conf.getInt(CONF_MAX_READ_SECONDS, DEFAULT_MAX_READ_SECONDS);
        batchSize = conf.getInt(CONF_BATCH_SIZE, DEFAULT_BATCH_SIZE);
        loadData = conf.getBoolean(CONF_LOAD_DATA_COLUMN, DEFAULT_LOAD_DATA_COLUMN);

        if (usePersistence) {
            log.debug("Enabling progress tracker");
            progressTracker = new ProgressTracker(
                    progressFile,
                    conf.getLong(CONF_PROGRESS_BATCH_SIZE, DEFAULT_PROGRESS_BATCH_SIZE),
                    conf.getLong(CONF_PROGRESS_GRACETIME, DEFAULT_PROGRESS_GRACETIME));
        } else {
            progressTracker = null;
        }

        if (conf.getBoolean(CONF_STAY_ALIVE, DEFAULT_STAY_ALIVE)) {
            storageWatcher = new StorageWatcher(conf);
            storageWatcher.addListener(this, base == null ? null : Arrays.asList(base), null);
            storageWatcher.start();
            log.trace("Enabled storage watching for base " + base);
        } else {
            log.trace("No storage watching enabled as " + CONF_STAY_ALIVE + " was false");
            storageWatcher = null;
        }

        lastRecordTimestamp = getStartTime();
        lastIteratorUpdate = lastRecordTimestamp;
        stopOnNewer = conf.getBoolean(CONF_STOP_ON_NEWER, DEFAULT_STOP_ON_NEWER);
        log.info(String.format(
                "RecordReader(startFromScratch=%b, storage=%s, bases=%s, startMTime=%s, progressFile='%s', "
                + "progressTrack=%b, maxRecords=%d, maxSeconds=%d, batchSize=%d, loadDate=%b, stayAlive=%b, "
                + "stopOnNewer=%b) started", startFromScratch, storage,
                base == null || "".equals(base) ? "*" : base,
                String.format(ProgressTracker.ISO_TIME, lastRecordTimestamp), progressFile,
                progressFile != null, maxReadRecords, maxReadSeconds, batchSize, loadData,
                storageWatcher != null, stopOnNewer));
    }

    /**
     * If !START_FROM_SCRATCH && USE_PERSISTENCE then get last timestamp
     * from persistence file, else return 0.
     *
     * @return the timestamp to continue harvesting from.
     */
    private long getStartTime() {
        if (startFromScratch || !usePersistence) {
//            log.info("Starting extraction of base '" + base + "' from time 0");
            if (progressTracker != null) {
                progressTracker.updated(0);
            }
            return 0;
        }

        if (progressTracker != null) {
            progressTracker.loadProgress();
            log.info("Resuming for base '" + base + "' from " + progressTracker.getLastUpdateStr());
            return progressTracker.getLastUpdate();
        } else {
            log.info("No progress tracker defined. Starting base '" + base + "'from time 0");
            return 0;
        }
    }

    /**
     * If the iterator is null, a new iterator is requested. If the iterator has reached the end, the method checks to
     * see is the Storage has been updated since last iterator creation. If so, a new iterator is created.
     * If not, the method waits for an update from StorageWatcher.
     *
     * @return {@code true} iff the iterator was good.
     * @throws java.io.IOException if an iterator could not be created.
     */
    private boolean checkIterator() throws IOException {
        log.trace("checkIterator() called");
        if (isEof()) {
            return false;
        }
        if (recordIterator == null) {
            log.debug(String.format(
                    "Creating initial record iterator for Records modified after " + ProgressTracker.ISO_TIME,
                    lastRecordTimestamp));

            // Detect if we need special query options and perform the query as
            // we are configured
            long iterKey = storage.getRecordsModifiedAfter(lastRecordTimestamp, base, getQueryOptions());

            lastIteratorUpdate = System.currentTimeMillis();
            recordIterator = new StorageIterator(storage, iterKey, batchSize);

            return false;
        } else if (recordIterator.hasNext()) {
            return true;
        }

        if (storageWatcher == null) {
            log.trace("storageWatcher is null, so no renew of iterator");
            return false;
        }
        // We have an iterator but it is empty
        log.debug(String.format(
                "Updating record iterator for Records modified after " + ProgressTracker.ISO_TIME,
                lastRecordTimestamp));

        long iterKey = storage.getRecordsModifiedAfter(lastRecordTimestamp, base, getQueryOptions());


        lastIteratorUpdate = System.currentTimeMillis();
        recordIterator = new StorageIterator(storage, iterKey, batchSize);

        if (!recordIterator.hasNext()) {
            log.debug("Received update notification from StorageWatcher, but no new Records is available from the "
                      + "record iterator");
            recordIterator = null;
            return false;
        }
        return true;
    }

    private QueryOptions getQueryOptions() {
        QueryOptions opts = new QueryOptions(null, null,
                                             expandChildren ? maxExpansionDepth : 0,
                                             expandParents ? maxExpansionHeight : 0,
                                             null, QueryOptions.ATTRIBUTES_ALL);
        if (!expandChildren) {
            opts.removeAttribute(QueryOptions.ATTRIBUTES.CHILDREN);
        }
        if (!expandParents) {
            opts.removeAttribute(QueryOptions.ATTRIBUTES.PARENTS);
        }
        if (!loadData) {
            opts.removeAttribute(QueryOptions.ATTRIBUTES.CONTENT);
        }
        return opts;
    }

    /**
     * Mark end of record iterator reached.
     */
    private void markEof() {
        eofReached = true;
        recordIterator = null; // Allow finalization of the recordIterator
    }

    /**
     * Return true if end of record iterator is reached.
     *
     * @return True if end of record iterator is reached.
     */
    private boolean isEof() {
        return eofReached;
    }

    /* ObjectFilter interface */

    @Override
    public boolean hasNext() {
        if (isEof()) {
            return false;
        }

        try {
            log.trace("hasNext: Calling checkIterator()");
            checkIterator();
        } catch (IOException e) {
            log.warn("hasNext: An exception occured while checking for a new iterator. Returning false", e);
            return false;
        }

        //noinspection LoopConditionNotUpdatedInsideLoop
        while (!recordIterator.hasNext()) {
            log.trace("hasNext: RecordIterater does not have next. Waiting and checking");
            try {
                waitForStorageChange();
                checkIterator();
                if (storageWatcher == null || recordIterator == null || !recordIterator.hasNext()) {
                    break;
                }
            } catch (IOException e) {
                log.warn("hasNext: An exception occured while checking for a new iterator. Returning false");
                return false;
            }
        }
        return !isEof();
    }

    /**
     * Wait for storage change. This method blocks until there is a storage
     * change.
     */
    private void waitForStorageChange() {
        log.trace("waitForStorageChange() called");
        try {
            if (checkIterator() && recordIterator.hasNext()) {
                return;
            }
            if (storageWatcher == null) { // We don't wait here
                log.trace("waitForStorageChange: No storageWatcher, no records: Mark EOF");
                markEof();
                return;
            }
        } catch (IOException e) {
            log.error("IOException prepraring iterator for wait-phase", e);
            markEof();
        }


        // We have to check this in a loop. See Javadoc for Object.wait()
        while (true) {
            // If there is no watcher, just stop watching.
            // We might be closing down or something
            if (!storageWatcher.isRunning()) {
                log.info("Storage watcher not running. Aborting wait");
                break;
            }

            try {
                // Check if there has been changes since we last checked
                // Keep the monitor on the storageWatcher until we have
                // finished waiting
                // on it to make sure we don't drop any events
                synchronized (storageWatcher) {
                    if (storageWatcher.getLastNotify(base) > lastIteratorUpdate) {
                        log.debug("Detected changes on base '" + base + "'since last check. Skipping wait");
                        break;
                    }
                    log.debug("No changes on base '" + base + "' since last check. Waiting for storage watcher...");
                    storageWatcher.wait();
                }
            } catch (InterruptedException e) {
                log.debug("Interrupted while waiting for StorageWatcher");
            }
        }
        log.debug("Got notification from storage");
    }

    @Override
    public Payload next() {
        //noinspection DuplicateStringLiteralInspection
        log.trace("next() called");

        if (!hasNext()) {
            throw new NoSuchElementException("No more Records available");
        }

        Payload payload = new Payload(recordIterator.next());
        if (firstRecordReceivedTime == -1) {
            firstRecordReceivedTime = System.currentTimeMillis();
        }
        lastRecordTimestamp = payload.getRecord().getLastModified();
        if (stopOnNewer && lastRecordTimestamp < firstRecordReceivedTime) {
            // TODO: Avoid sending the duplicate record
            log.info("Stopping further Record requests as the Record " + payload.getId() + " has a timestamp newer"
                     + " than the start time for the RecordReader. The current Record will be the last");
            markEof();
        }
        recordCounter++;

        log.debug("Emitting " + payload);

        if (log.isTraceEnabled()) {
            log.trace("next(): Got lastModified timestamp " +
                      String.format(ProgressTracker.ISO_TIME, payload.getRecord().getLastModified())
                      + " for " + payload);
        }

        if (maxReadRecords != -1 && maxReadRecords <= recordCounter) {
            log.debug("Reached maximum number of Records to read (" + maxReadRecords + ")");
            markEof();
        }

        if (maxReadSeconds != -1 &&
            maxReadSeconds * 1000 <= System.currentTimeMillis() - startTime) {
            log.debug("Reached maximum allow time usage (" + maxReadSeconds + ") seconds");
            markEof();
        }

        if (progressTracker != null) {
            progressTracker.updated(lastRecordTimestamp);
        }

        return payload;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("No removal of Payloads for RecordReader");
    }

    @Override
    public void setSource(Filter filter) {
        throw new UnsupportedOperationException("RecordReader must be the first filter in the chain");
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
        Logging.logProcess("RecordReader", "Calling close for Payload as part of pump()",
                           Logging.LogLevel.TRACE, next);
        next.close();
        return true;
    }

    /**
     * If success is true and persistence enabled, the current progress in the
     * harvest is stored. If success is false, no progress is stored.
     *
     * @param success whether the while ingest has been successfull or not.
     */
    // TODO: Check why this is not called in FacetTest
    @Override
    public void close(boolean success) {
        //noinspection DuplicateStringLiteralInspection
        log.debug("close(" + success + ") entered");
        markEof();
        if (success) {
            if (progressTracker != null) {
                progressTracker.updated(lastRecordTimestamp);
                progressTracker.updateProgressFile(); // Force a flush of the progress
                log.info("Closed with success=true and persistent timestamp " + progressTracker.getLastUpdateStr());
            } else {
                log.info("Closed with success=true and no progress tracker");
            }
        } else {
            if (progressTracker != null) {
                log.info("Closed with success=false. Timestamp not explicitly updated. Last persistent timestamp was "
                         + progressTracker.getLastUpdateStr());
            } else {
                log.info("Closed with success=false and no progress tracker");
            }
        }

        if (storageWatcher != null) {
            log.debug("Stopping storage watcher");
            storageWatcher.stop();
            log.info("Storage watcher stopped");
        }
    }

    /**
     * If a progress-file is existing, it is cleared.
     */
    public void clearProgressFile() {
        if (progressTracker != null) {
            progressTracker.clearProgressFile();
        }
    }

    @Override
    public void storageChanged(StorageWatcher watch, String base, long timeStamp, Object userData) {
        log.trace("Storage was changed for base " + base + " and timestamp " + timeStamp);
        watch.notifyAll();
        // TODO : Update the Semaphore with at most 1   (remember syns)
    }

    @Override
    public String toString() {
        return "RecordReader(storage=" + storage + ", persistent progress="
               + (progressTracker == null ? "N/A" : progressTracker.getLastUpdateStr()) + " )";
    }
}
