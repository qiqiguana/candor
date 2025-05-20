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
package dk.statsbiblioteket.summa.search.api.tools;

import dk.statsbiblioteket.summa.common.configuration.Configurable;
import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.rpc.ConnectionConsumer;
import dk.statsbiblioteket.summa.search.api.Request;
import dk.statsbiblioteket.summa.search.api.ResponseCollection;
import dk.statsbiblioteket.summa.search.api.SearchClient;
import dk.statsbiblioteket.summa.search.api.SummaSearcher;
import dk.statsbiblioteket.util.qa.QAInfo;

/**
 * Little tool to hook up to a {@link SummaSearcher} and launch a search
 * on it and print the result to stdout.
 */
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.QA_OK,
        author = "mke")
public class SearchTool {
    /**
     * Main method, for this commandline helper tool.
     *
     * @param args Arguments given on the commandline, these are parse and send
     *             to the searcher.
     * @throws Exception If error occur while connection to searcher.
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            printUsage();
            System.exit(1);
        }

        Configuration conf;
        String rpcVendor;

        /* Try and open the system config */
        try {
            conf = Configuration.getSystemConfiguration(false);
        } catch (Configurable.ConfigurationException e) {
            System.err.println("Unable to load system config: " + e.getMessage() + ".\nUsing default configuration");
            conf = Configuration.newMemoryBased();
        }

        /* Make sure the summa.rpc.vendor property is set */
        if (!conf.valueExists(ConnectionConsumer.CONF_RPC_TARGET)) {
            rpcVendor = System.getProperty(ConnectionConsumer.CONF_RPC_TARGET);

            if (rpcVendor != null) {
                conf.set(ConnectionConsumer.CONF_RPC_TARGET, rpcVendor);
            } else {
                conf.set(ConnectionConsumer.CONF_RPC_TARGET, "//localhost:28000/summa-searcher");
            }
        }


        System.err.print("Connecting to searcher on " + conf.getString(ConnectionConsumer.CONF_RPC_TARGET) + " ... ");
        SearchClient searcher = new SearchClient(conf);
        System.err.println("[OK]");

        Request rq = parseArgs(args);

        System.err.print("Performing search ... ");
        long time = System.currentTimeMillis();
        ResponseCollection resp = searcher.search(rq);
        time = System.currentTimeMillis() - time;
        System.err.println("[OK]");

        System.err.println("Result:");
        System.err.flush();
        System.out.println(resp.toXML());
        System.err.println("Response time: " + time + "ms");
    }

    /**
     * Private helper method for print help usage of this commandline tool.
     */
    private static void printUsage() {
        System.err.println("USAGE:\n\tsearch-tool.sh <key=val> [key=val]...");
        System.err.println("Examples:");
        System.err.println("\tsearch-tool.sh search.document.query=foo\n");
        System.err.println("\tsearch-tool.sh search.document.lucene.morelikethis.recordid=myRecordId27\n");
        System.err.println("\tsearch-tool.sh search.index.field=lme search.index.term=danmark\n");
        System.err.println("\tsearch-tool.sh "
                           + "summa.support.didyoumean.query=dolfinns summa.support.didyoumean.maxresults=5\n");
        System.err.println("Optionally you may set the CONFIGURATION" +
                           "variable in your shell and it will be used for" +
                           "the summa.configuration property\n");
    }

    /**
     * Parse key=value pairs from the command line.
     *
     * @param args The args as passed to {@link #main(String[])}.
     * @return A compiled {@link Request} object ready to pass to
     *         {@link SummaSearcher#search}.
     */
    private static Request parseArgs(String[] args) {
        Request rq = new Request();

        for (String arg : args) {
            String[] keyVal = arg.split("=");
            if (keyVal.length != 2) {
                throw new RuntimeException(
                        "Argument '" + arg + "' is not a valid assignment string. Fx summa.foo=bar");
            }
            rq.put(keyVal[0], keyVal[1]);
        }
        return rq;
    }
}