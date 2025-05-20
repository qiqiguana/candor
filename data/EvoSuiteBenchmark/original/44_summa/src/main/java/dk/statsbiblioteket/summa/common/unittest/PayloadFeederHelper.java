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
package dk.statsbiblioteket.summa.common.unittest;

import dk.statsbiblioteket.summa.common.Record;
import dk.statsbiblioteket.summa.common.configuration.Resolver;
import dk.statsbiblioteket.summa.common.filter.Filter;
import dk.statsbiblioteket.summa.common.filter.Payload;
import dk.statsbiblioteket.summa.common.filter.object.ObjectFilter;
import dk.statsbiblioteket.util.qa.QAInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Takes a list of Payloads in the constructor and delivers the Payloads when
 * requested. Used for testing.
 */
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.IN_DEVELOPMENT,
        author = "te",
        comment = "JavaDoc needed")
public class PayloadFeederHelper implements ObjectFilter {
    /** Local logger instance. */
    private static Log log = LogFactory.getLog(PayloadFeederHelper.class);
    /** List of payloads. */
    private List<Payload> payloads;
    /** Delay. */
    private int delay = 0;
    /** Timestamp of last delivery time. */
    private Long lastDeliveryTime = 0L;

    /**
     * Creates a payload feeder with a list of payloads.
     * @param payloads The  list of payloads.
     */
    public PayloadFeederHelper(List<Payload> payloads) {
        //noinspection DuplicateStringLiteralInspection
        log.debug("Creating feeder with " + payloads.size() + " Payloads");
        this.payloads = new ArrayList<Payload>(payloads.size());
        this.payloads.addAll(payloads);
    }

    /**
     * Creates a payload feeder with a list of Stream based payloads.
     * @param inputFiles an array of files which will be used for the Payloads..
     */
    public PayloadFeederHelper(String... inputFiles) throws IOException {
        //noinspection DuplicateStringLiteralInspection
        log.debug("Creating feeder from " + inputFiles.length + " input files");
        payloads = new ArrayList<Payload>(inputFiles.length);
        for (String inputFile: inputFiles) {
            payloads.add(new Payload(Resolver.getURL(inputFile).openStream(), inputFile));
        }
    }

    /**
     * Creates a payload feeder with a list of Record based payloads.
     * @param startID    used as the starting point for the generated IDs.
     * @param inputFiles an array of files which will be used for the Payloads..
     */
    public PayloadFeederHelper(int startID, String... inputFiles) throws IOException {
        //noinspection DuplicateStringLiteralInspection
        log.debug("Creating feeder from " + inputFiles.length + " input files");
        payloads = new ArrayList<Payload>(inputFiles.length);
        int counter = startID;
        for (String inputFile: inputFiles) {
            payloads.add(new Payload(new Record(
                "doc" + counter++, "dummy", Resolver.getUTF8Content(inputFile).getBytes("utf-8"))));
        }
    }

    /**
     * Creates payload feeder which delay each delivery a small or large period.
     * @param payloads The list of payloads.
     * @param delayBetweenPayloads The delay between each payload.
     */
    public PayloadFeederHelper(List<Payload> payloads, int delayBetweenPayloads) {
        //noinspection DuplicateStringLiteralInspection
        log.debug("Creating feeder with " + payloads.size() + " Payloads");
        this.payloads = new ArrayList<Payload>(payloads.size());
        this.payloads.addAll(payloads);
        delay = delayBetweenPayloads;
    }

    @Override
    public final boolean hasNext() {
        return !payloads.isEmpty();
    }

    @Override
    public final void setSource(Filter filter) {
        throw new UnsupportedOperationException("setSource not supported");
    }

    @Override
    public final boolean pump() throws IOException {
        return next() != null && hasNext();
    }

    @Override
    public final void close(boolean success) {
        payloads.clear();
    }

    @Override
    public final Payload next() {
        long sleepTime = lastDeliveryTime + delay - System.currentTimeMillis();
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                log.debug("Interrupted while sleeping " + sleepTime + "ms");
            }
        }
        Payload payload = !hasNext() ? null : payloads.remove(0);
        if (log.isTraceEnabled()) {
            log.trace("Delivering " + payload);
        }
        lastDeliveryTime = System.currentTimeMillis();
        return payload;
    }

    /**
     * Remove throws {@link UnsupportedOperationException}.
     */
    @Override
    public final void remove() {
        //noinspection DuplicateStringLiteralInspection
        throw new UnsupportedOperationException("Remove not supported");
    }
}
