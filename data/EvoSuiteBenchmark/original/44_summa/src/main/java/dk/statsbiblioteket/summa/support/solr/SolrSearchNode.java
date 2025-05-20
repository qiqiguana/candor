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
package dk.statsbiblioteket.summa.support.solr;

import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.lucene.index.IndexUtils;
import dk.statsbiblioteket.summa.common.util.Pair;
import dk.statsbiblioteket.summa.facetbrowser.api.FacetKeys;
import dk.statsbiblioteket.summa.search.SearchNodeImpl;
import dk.statsbiblioteket.summa.search.api.Request;
import dk.statsbiblioteket.summa.search.api.ResponseCollection;
import dk.statsbiblioteket.summa.search.api.document.DocumentKeys;
import dk.statsbiblioteket.summa.search.document.DocumentSearcher;
import dk.statsbiblioteket.summa.support.api.LuceneKeys;
import dk.statsbiblioteket.summa.support.summon.search.FacetQueryTransformer;
import dk.statsbiblioteket.summa.support.summon.search.SolrFacetRequest;
import dk.statsbiblioteket.summa.support.summon.search.SolrResponseBuilder;
import dk.statsbiblioteket.util.Strings;
import dk.statsbiblioteket.util.qa.QAInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryparser.classic.ParseException;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.*;

/**
 * A wrapper for Solr web calls, transforming requests and responses from and to Summa equivalents.
 * </p><p>
 * Besides the keys stated below, it is highly advisable to specify {@link SolrResponseBuilder#CONF_RECORDBASE}.
 * If 'recordBase' is requested as a facet and the response has no tags in that facet, a tag with the given ID as
 * content will be added with a count equal to the number of found documents.
 **/
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.IN_DEVELOPMENT,
        author = "te")
// TODO: Always request the recordID field
public class SolrSearchNode extends SearchNodeImpl  { // TODO: implements DocumentSearcher
    private static Log log = LogFactory.getLog(SolrSearchNode.class);

    // TODO: Assign mandatory ID, use it for timing and result delivery
    /**
     * The entry point for calls to Solr.
     * </p><p>
     * Optional. Default is localhost:8983 (Solr default).
     */
    public static final String CONF_SOLR_HOST = "solr.host";
    public static final String DEFAULT_SOLR_HOST = "localhost:8983";
    /**
     * The rest call at {@link #CONF_SOLR_HOST}.
     * </p><p>
     * Optional. Default is '/solr' (Solr default).
     */
    public static final String CONF_SOLR_RESTCALL = "solr.restcall";
    public static final String DEFAULT_SOLR_RESTCALL = "/solr/select";
    /**
     * The timeout in milliseconds for establishing a connection to the remote Solr.
     * </p><p>
     * Optional. Default is 2000 milliseconds.
     */
    public static final String CONF_SOLR_CONNECTION_TIMEOUT = "solr.connection.timeout";
    public static final int DEFAULT_SOLR_CONNECTION_TIMEOUT = 2000;
    /**
     * The timeout in milliseconds for receiving data after a connection has been established to the remote Solr.
     * </p><p>
     * Optional. Default is 8000 milliseconds.
     */
    public static final String CONF_SOLR_READ_TIMEOUT = "solr.read.timeout";
    public static final int DEFAULT_SOLR_READ_TIMEOUT = 8000;
    /**
     * The prefix will be added to all IDs returned by Solr.
     * </p><p>
     * Optional. Default is empty.
     */
    public static final String CONF_SOLR_IDPREFIX = "solr.id.prefix";
    public static final String DEFAULT_SOLR_IDPREFIX = "";
    /**
     * The default number of documents results to return from a search.
     * </p><p>
     * This can be overridden with {@link dk.statsbiblioteket.summa.search.api.document.DocumentKeys#SEARCH_MAX_RECORDS}.
     * </p><p>
     * Optional. Default is 15.
     */
    public static final String CONF_SOLR_DEFAULTPAGESIZE = "solr.defaultpagesize";
    public static final int DEFAULT_SOLR_DEFAULTPAGESIZE = 15;
    /**
     * The minimum number of counts for a single tag to show up in the result list.
     * </p><p>
     * Optional. Default is 1.
     */
    public static final String CONF_SOLR_MINCOUNT = "solr.mincount";
    public static final int DEFAULT_SOLR_MINCOUNT = 1;
    /**
     * The default facets if none are specified. The syntax is a comma-separated
     * list of facet names, optionally with max tags in paranthesis.
     * This can be overridden with {@link dk.statsbiblioteket.summa.facetbrowser.api.FacetKeys#SEARCH_FACET_FACETS}.
     * Specifying the empty string turns off faceting.
     * </p><p>
     * Optional. Default is {@link #DEFAULT_SOLR_FACETS}.
     */
    public static final String CONF_SOLR_FACETS = "solr.facets";
    public static final String DEFAULT_SOLR_FACETS = "";
    /**
     * The default number of tags tags to show in a facet if not specified
     * elsewhere.
     * </p><p>
     * Optional. Default is 15.
     */
    public static final String CONF_SOLR_FACETS_DEFAULTPAGESIZE = "solr.facets.defaultpagesize";
    public static final int DEFAULT_SOLR_FACETS_DEFAULTPAGESIZE = 15;
    /**
     * Whether facets should be searched with and or or.
     * Optional. Default is 'and'. Can only be 'and' or 'or'.
     */
    public static final String CONF_SOLR_FACETS_COMBINEMODE = "solr.facets.combinemode";
    public static final String DEFAULT_SOLR_FACETS_COMBINEMODE = "and";


    /**
     * If true, calls to Solr assumes that pure negative filters (e.g. "NOT foo NOT bar") are supported.
     * If false, pure negative filters are handled by rewriting the query to "(query) filter", so if query is "baz"
     * and the filter is "NOT foo NOT bar", the result is "(baz) NOT foo NOT bar".
     * Note that rewriting also requires the {@link DocumentKeys#SEARCH_FILTER_PURE_NEGATIVE} parameter to be true.
     * </p><p>
     * Optional. Default is false.
     */
    public static final String CONF_SUPPORTS_PURE_NEGATIVE_FILTERS = "solr.purenegativefilters.support";
    public static final boolean DEFAULT_SUPPORTS_PURE_NEGATIVE_FILTERS = false;

    /**
     * If true, the SolrSearchNode does not attempt to extract facet-query from the query and passes the query and
     * filter through unmodified. Mainly used for debugging.
     * </p><p>
     * Optional. Default is false.
     */
    public static final String SEARCH_PASSTHROUGH_QUERY = "solr.passthrough.query";
    public static final boolean DEFAULT_PASSTHROUGH_QUERY = false;

    /**
     * Properties with this prefix are added to the Solr query. Values are
     * lists of Strings. If one or more #CONF_SOLR_PARAM_PREFIX are
     * specified as part of a search query, the parameters are added to the
     * configuration defaults. Existing params with the same key are
     * overwritten.
     * </p><p>
     * Optional. Default is empty.
     */
    public static final String CONF_SOLR_PARAM_PREFIX = "solrparam.";
    /**
     * Search-time variation of {@link #CONF_SOLR_PARAM_PREFIX}.
     */

    /**
     * If true, {@link DocumentKeys#SEARCH_FILTER} must contain simple facet queries only. A simple facet query is
     * one or more {@code facet:term} pairs, optionally prefixed with {@code -} or {@code NOT}.
     * </p><p>
     * Valid sample query: {@code foo:bar -zoo:baz +ak:ve AND loo:poo NOT bum:bam}.
     * </p><p>
     * Note: This is basically an ugly hack until we switch to treating facet filtering as first class.
     */
    public static final String SEARCH_SOLR_FILTER_IS_FACET = "solr.filterisfacet";

    /**
     * The Solr field with the unique ID for a document.
     * </p><p>
     * Optional. Default is 'recordID'.
     */
    public static final String CONF_ID_FIELD = "solr.field.id";
    public static final String DEFAULT_ID_FIELD = IndexUtils.RECORD_FIELD;

    /**
     * Old hack that rewrote all queries so that there was a space after all parentheses in order to compensate
     * for the bug fixed by https://issues.apache.org/jira/browse/SOLR-3377
     */
    public static final String DEPRECATED_COMPENSATE_FOR_PARENTHESIS_BUG = "solr.solr3377.hack";

    /**
     * If true, faceting is handled with the facet.filter parameter. This works well for the facet pars but also means
     * that the document results will be invalid.
     * </p><p>
     * Optional. Default is false.
     */
    public static final String CONF_EXPLICIT_FACET_FILTERING = "solr.facet.explicit.filter";
    public static final boolean DEFAULT_EXPLICIT_FACET_FILTERING = false;

    //    private static final DateFormat formatter =
    //        new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US);
    protected SolrResponseBuilder responseBuilder;
    protected final String host;
    protected final String restCall;
    protected final int connectionTimeout;
    protected final int readTimeout;
    protected final String idPrefix;
    protected final int defaultPageSize;
    protected final int minCount;
    protected final int defaultFacetPageSize;
    protected final String defaultFacets;
    protected final String combineMode;
    protected final Map<String, List<String>> solrDefaultParams;
    protected final boolean supportsPureNegative;
    protected final FacetQueryTransformer facetQueryTransformer;
    protected final Set<String> nonescapedFields = new HashSet<String>(10);
    protected final String fieldID;
    protected final boolean explicitFacetFilter;

    public SolrSearchNode(Configuration conf) throws RemoteException {
        super(conf);
        setID(conf.getString(CONF_ID, "solr"));
        List<String> nonEscaped = conf.getStrings(DocumentSearcher.CONF_NONESCAPED_FIELDS, new ArrayList<String>(0));
        nonescapedFields.addAll(nonEscaped);
        responseBuilder = createResponseBuilder(conf);
        responseBuilder.setNonescapedFields(nonescapedFields);
        solrDefaultParams = new HashMap<String, List<String>>();

        host = conf.getString(CONF_SOLR_HOST, DEFAULT_SOLR_HOST);
        restCall = conf.getString(CONF_SOLR_RESTCALL, DEFAULT_SOLR_RESTCALL);
        connectionTimeout = conf.getInt(CONF_SOLR_CONNECTION_TIMEOUT, DEFAULT_SOLR_CONNECTION_TIMEOUT);
        readTimeout = conf.getInt(CONF_SOLR_READ_TIMEOUT, DEFAULT_SOLR_READ_TIMEOUT);
        idPrefix =   conf.getString(CONF_SOLR_IDPREFIX, DEFAULT_SOLR_IDPREFIX);
        defaultPageSize = conf.getInt(CONF_SOLR_DEFAULTPAGESIZE, DEFAULT_SOLR_DEFAULTPAGESIZE);
        minCount = conf.getInt(CONF_SOLR_MINCOUNT, DEFAULT_SOLR_MINCOUNT);
        defaultFacetPageSize = conf.getInt(CONF_SOLR_FACETS_DEFAULTPAGESIZE, DEFAULT_SOLR_FACETS_DEFAULTPAGESIZE);
        defaultFacets = conf.getString(CONF_SOLR_FACETS, DEFAULT_SOLR_FACETS);
        combineMode = conf.getString(CONF_SOLR_FACETS_COMBINEMODE, DEFAULT_SOLR_FACETS_COMBINEMODE);
        fieldID = conf.getString(CONF_ID_FIELD, DEFAULT_ID_FIELD);
        supportsPureNegative = conf.getBoolean(
                CONF_SUPPORTS_PURE_NEGATIVE_FILTERS, DEFAULT_SUPPORTS_PURE_NEGATIVE_FILTERS);
        facetQueryTransformer = createFacetQueryTransformer(conf);
        if (conf.valueExists(DEPRECATED_COMPENSATE_FOR_PARENTHESIS_BUG)) {
            log.warn("The property " + DEPRECATED_COMPENSATE_FOR_PARENTHESIS_BUG
                     + " serves no purpose in Solr 4 BETA+ and should be removed");
        }
        explicitFacetFilter = conf.getBoolean(CONF_EXPLICIT_FACET_FILTERING, DEFAULT_EXPLICIT_FACET_FILTERING);
        readyWithoutOpen();
        log.info("Created SolrSearchNode(" + getID() + ")");
    }

    /**
     * Create a response builder from Solr to Summa responses. Override this to get parsing of responses that differ
     * from standard Solr.
     * @param conf base configuration for the transformer.
     * @return a search backend specific transformer.
     */
    protected SolrResponseBuilder createResponseBuilder(Configuration conf) {
        return new SolrResponseBuilder(conf);
    }

    /**
     * Create a transformer from Lucene search syntax queries into Solr facet queries. Override this to get specific
     * facet queries for searchers that differ from standard Solr.
     * @param conf base configuration for the transformer.
     * @return a search backend specific transformer.
     */
    protected FacetQueryTransformer createFacetQueryTransformer(Configuration conf) {
        return new FacetQueryTransformer(conf);
    }

    /**
     * Sort and optionally urlencodes a query string
     * @param queryParameters A Map<String, List<String>> containing the query parameters
     * @param urlencode Whether or not to urlencode the query parameters
     * @return A sorted and urlencoded query string
     */
    protected static String computeSortedQueryString(Map<String, List<String>> queryParameters, boolean urlencode) {
        List<String> parameterStrings = new ArrayList<String>();

        // for each parameter, get its key and values
        for (Map.Entry<String, List<String>> entry : queryParameters.entrySet()) {
            // for each value, create a string in the format key=value
            for (String value : entry.getValue()) {
                if (urlencode) {
                    try {
                        parameterStrings.add(entry.getKey() + "=" + URLEncoder.encode(value, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException("Unable to encode '" + value + "' to UTF-8. UTF-8 support is "
                                                   + "required for Summa to function", e);
                    }
                } else {
                    parameterStrings.add(entry.getKey() + "=" + value);
                }
            }
        }

        // sort the individual parameters
        Collections.sort(parameterStrings);
        return Strings.join(parameterStrings, "&");
    }

    @Override
    protected void managedSearch(Request request, ResponseCollection responses) throws RemoteException {
        try {
            barrierSearch(request, responses);
        } catch (StackOverflowError e) {
            String message = String.format(
                "Caught StackOverflow at outer level during handling of Solr request %s:\n%s",
                request.toString(true), reduceStackTrace(request, e));
            log.error(message, e);
            throw new RemoteException("SolrSearchNode.managedSearch: " + message);
        }
    }

    private void barrierSearch(Request request, ResponseCollection responses) throws RemoteException {
        long startTime = System.currentTimeMillis();
        if (request.containsKey(LuceneKeys.SEARCH_MORELIKETHIS_RECORDID)) {
            log.trace("MoreLikeThis search is not supported yet, returning immediately");
            return;
        }

        if (request.containsKey(DocumentKeys.SEARCH_IDS)) {
            throw new UnsupportedOperationException(
                    "The ID-search API at summon only goes live 2013-03-07 and needs to be tested before this " +
                            "functionality is made available i Summa");
        }
        String rawQuery = getEmptyIsNull(request, DocumentKeys.SEARCH_QUERY);
        String filter =  getEmptyIsNull(request, DocumentKeys.SEARCH_FILTER);
        String sortKey = getEmptyIsNull(request, DocumentKeys.SEARCH_SORTKEY);
        if (DocumentKeys.SORT_ON_SCORE.equals(sortKey)) {
            sortKey = null; // null equals relevance ranking
        }
        boolean reverseSort = request.getBoolean(DocumentKeys.SEARCH_REVERSE, false);
        int startIndex = request.getInt(DocumentKeys.SEARCH_START_INDEX, 0);
        int maxRecords = request.getInt(DocumentKeys.SEARCH_MAX_RECORDS, (int)DocumentSearcher.DEFAULT_RECORDS);
        boolean collectdocIDs = request.getBoolean(DocumentKeys.SEARCH_COLLECT_DOCIDS, false);
        boolean passThroughQuery = request.getBoolean(SEARCH_PASSTHROUGH_QUERY, DEFAULT_PASSTHROUGH_QUERY);

        if (rawQuery == null && filter == null) {
            log.debug("No filter or query, proceeding anyway as other params might be specified");
        }

        String query;
        query = "*".equals(rawQuery) ? "*:*" : rawQuery;

        String facetsDef =  request.getString(FacetKeys.SEARCH_FACET_FACETS, defaultFacets);
        if ("".equals(facetsDef)) {
            facetsDef = null;
        }
        SolrFacetRequest facets = null == facetsDef || "".equals(facetsDef) ? null :
                                  createFacetRequest(facetsDef, minCount, defaultFacetPageSize, combineMode);

        Map<String, List<String>> solrSearchParams = new HashMap<String, List<String>>(solrDefaultParams);
        for (Map.Entry<String, Serializable> entry : request.entrySet()) {
            convertSolrParam(solrSearchParams, entry);
        }
        if (query != null && !passThroughQuery) {
            query = convertQuery(query, solrSearchParams);
        }
        if ("".equals(query)) {
            query = null;
        }
        if (filter != null && !passThroughQuery && !request.getBoolean(SEARCH_SOLR_FILTER_IS_FACET, false)) {
            filter = convertQuery(filter, solrSearchParams);
        }
        if ("".equals(filter)) {
            filter = null;
        }

        long searchTime = -System.currentTimeMillis();
        if (request.containsKey(LuceneKeys.SEARCH_MORELIKETHIS_RECORDID)) {
            if (fieldID != null && !"".equals(fieldID)) {
                query = fieldID + ":\"" + request.getString(LuceneKeys.SEARCH_MORELIKETHIS_RECORDID) + "\"";
            } else {
                query = request.getString(LuceneKeys.SEARCH_MORELIKETHIS_RECORDID);
            }
            solrSearchParams.put("mlt", Arrays.asList("true"));
            log.debug("Performing MoreLikeThis search for '" + query);
        }
        log.trace("Performing search for '" + query + "' with facets '" + facets + "'");
        String solrResponse;
        String solrTiming;
        try {
            Pair<String, String> sums = solrSearch(
                request, filter, query, solrSearchParams, collectdocIDs ? facets : null,
                startIndex, maxRecords, sortKey, reverseSort, responses);
            solrResponse = sums.getKey();
            solrTiming = sums.getValue();
        } catch (StackOverflowError e) {
            String message = String.format("Caught StackOverflow while performing Solr request %s:\n%s",
                                           request.toString(true), reduceStackTrace(request, e));
            log.error(message, e);
            throw new RemoteException("SolrSearchNode.barrierSearch: " + message);
        }
        if (solrResponse == null || "".equals(solrResponse)) {
            throw new RemoteException("Solr search for '" + query + "' yielded empty result");
        }
        searchTime += System.currentTimeMillis();

        long buildResponseTime = -System.currentTimeMillis();
        long hitCount;
        try {
            //System.out.println(solrResponse.replace(">", ">\n"));
            hitCount = responseBuilder.buildResponses(request, facets, responses, solrResponse, solrTiming);
        } catch (XMLStreamException e) {
            String message = "Unable to transform Solr XML response to Summa response for '" + request + "'";
            if (log.isDebugEnabled()) {
                log.debug(message + ". Full XML follows:\n" + solrResponse);
            }
            throw new RemoteException(message, e);
        } catch (StackOverflowError e) {
            String message = String.format(
                "Caught StackOverflow while building response for Solr request %s\nReduced stack trace:\n%s\n"
                + "Reduced raw Solr response:\n%s",
                request.toString(true), reduceStackTrace(request, e),
                solrResponse.length() > 2000 ? solrResponse.substring(0, 2000) : solrResponse);
            log.error(message, e);
            throw new RemoteException("SolrSearchNode.barrierSearch: " + message);

        }
        buildResponseTime += System.currentTimeMillis();

        if (log.isDebugEnabled()) {
            log.debug("fullSearch(" + request.toString(true) + ", " + filter + ", " + rawQuery + ", " + startIndex
                      + ", " + maxRecords + ", " + sortKey + ", " + reverseSort + ") with " + hitCount
                      + " hits finished in " + searchTime + " ms (" + searchTime + " ms for remote search call, "
                      + buildResponseTime + " ms for converting to Summa response)");
        }
        responses.addTiming(getID() + ".search.buildresponses", buildResponseTime);
        responses.addTiming(getID() + ".search.total", System.currentTimeMillis() - startTime);
    }

    // Override this to get search backend specific facet request syntax
    protected SolrFacetRequest createFacetRequest(
        String facetsDef, int minCount, int defaultFacetPageSize, String combineMode) {
        return new SolrFacetRequest(facetsDef, minCount, defaultFacetPageSize, combineMode);
    }

    /**
     * Extracts parameters with key prefix "solrparam." and stores the truncated keys with their value(s) as a list of
     * Strings.
     * </p><p>
     * If the key is not prefixed by "solrparam.", it is ignored.
     * </p>
     * @param solrParam the map where the converted key/value will be stored.
     * @param entry the source for the key/value pair.
     * @return true if the entry was added to solrParam, else false.
     */
    protected boolean convertSolrParam(Map<String, List<String>> solrParam, Map.Entry<String, Serializable> entry) {
        if (!entry.getKey().startsWith(CONF_SOLR_PARAM_PREFIX)) {
            log.trace("convertSolrParam got unsupported key " + entry.getKey() + ". Ignoring entry");
            return false;
        }
        String key = entry.getKey().substring(CONF_SOLR_PARAM_PREFIX.length(), entry.getKey().length());
        if (entry.getValue() instanceof List) {
            ArrayList<String> values = new ArrayList<String>();
            for (Object v: (List)entry.getValue()) {
                if (v instanceof String) {
                    values.add((String)v);
                } else {
                    log.warn("Expected List entry of type String in Solr parameter " + key + ", but got Object of "
                             + "class " + v.getClass());
                }
            }
            if (values.isEmpty()) {
                log.warn("Got empty list for Solr param " + key + ". Ignoring");
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("convertSolrParam assigning list " + key + ":" + Strings.join(values, ", "));
                }
                solrParam.put(key, values);
            }
        } else if (entry.getValue() instanceof String[]) {
            if (log.isTraceEnabled()) {
                log.trace("convertSolrParam assigning array " + key + ":"
                          + Strings.join((String[]) entry.getValue(), ", "));
            }
            solrParam.put(key, Arrays.asList((String[]) entry.getValue()));
        } else { // Simple type (String, Integer...)
            solrParam.put(key, Arrays.asList(entry.getValue().toString()));
            if (log.isTraceEnabled()) {
                log.trace("convertSolrParam assigning " + key + ":" + entry.getValue());
            }
        }
        return true;
    }

    private String getEmptyIsNull(Request request, String key) {
        String response = request.getString(key, null);
        return "".equals(response) ? null : response;
    }

    private boolean warmupCalled = false;
    @Override
    protected void managedWarmup(String request) {
        if (!warmupCalled) {
            log.debug("No warmup for '" + request + "' as warmup is handled externally. Further requests "
                      + "for warmup will be silently ignored");
            warmupCalled = true;
        }
    }

    @Override
    protected void managedOpen(String location) throws RemoteException {
        log.info("Open called with location '" + location + "' which is "
                 + "ignored by this search node as it is stateless");
    }

    @Override
    protected void managedClose() throws RemoteException {
        log.debug("managedClose() called. No effect as this search node is stateless");
    }


    /**
     * Optionally converts the query to conform to searcher specific syntax.
     * @param query            the input query.
     * @param solrSearchParams parameters that will be passed to Solr.
     * @return the converted query.
     */
    protected String convertQuery(String query, Map<String, List<String>> solrSearchParams) {
        log.trace("Default convertQuery does not change the query");
        return query;
    }

    /**
     * Perform a search in Solr. Override this to get different behaviour for search backends other than standard Solr.
     *
     * @param request    a standard Summa Search request, primarily filled with values from {@link DocumentKeys}.
     * @param filter     a Solr-style filter (same syntax as query). This is based on {@link DocumentKeys#SEARCH_FILTER}
     *                   but might have been rewritten.
     * @param query      a Solr-style query. This is based on {@link DocumentKeys#SEARCH_QUERY} but might have been
     *                   rewritten.
     * @param solrParams optional extended params for Solr. If not null, these will be added to the Solr request.
     * @param facets     which facets to request or null if no facets are wanted.
     * @param startIndex the index for the first Record to return, counting from 0.
     * @param maxRecords number of items per page.
     * @param sortKey    the field to sort on. If null, default ranking sort is used.
     * @param reverseSort if true, sort order is reversed.
     * @param responses  results are stored here.
     * @return XML with the search result as per Solr API followed by timing information.
     * @throws java.rmi.RemoteException if there were an error performing the remote search call.
     */
    protected Pair<String, String> solrSearch(
        Request request, String filter, String query, Map<String, List<String>> solrParams, SolrFacetRequest facets,
        int startIndex, int maxRecords, String sortKey, boolean reverseSort, ResponseCollection responses)
                                                                                                throws RemoteException {
        long buildQuery = -System.currentTimeMillis();
        log.trace("Calling simpleSearch(" + query + ", " + facets + ", " + startIndex + ", " + maxRecords + ")");
        Map<String, List<String>> queryMap;
        try {
            queryMap = buildSolrQuery(
                request, filter, query, solrParams, facets, startIndex, maxRecords, sortKey, reverseSort);
        } catch (ParseException e) {
            throw new RemoteException("Unable to build Solr query", e);
        }
        String queryString = computeSortedQueryString(queryMap, true);
        buildQuery += System.currentTimeMillis();
        log.trace("Parameter preparation done in " + buildQuery + "ms");
        String result;

        try {
            result = getData(restCall + "?" + queryString, responses);
        } catch (Exception e) {
            throw new RemoteException("SolrSearchNode: Unable to perform remote call to "  + host + restCall
                                      + " with argument '" + queryString + " and message " + e.getMessage());
        }
        log.trace("simpleSearch done in " + (System.currentTimeMillis() - buildQuery) + "ms");
        return new Pair<String, String>(result, "solr.buildquery:" + buildQuery);

    }
       // {start=[0], q=[gense], spellcheck.dictionary=[summa_spell], qt=[/didyoumean], rows=[15]}
    //  {spellcheck=[true], start=[0], q=[gense], spellcheck.dictionary=[summa_spell], spellcheck.count=[5], qt=[/didyoumean], rows=[15]}

    private String getData(String command, ResponseCollection responses) throws IOException {
        StringBuilder retval = new StringBuilder();

        if (log.isDebugEnabled()) {
            log.debug("Performing Solr request for '" + command + "'");
        }

        URL url = new URL("http://" + host + command);
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (Exception e) {
            String message = "Exception while calling HttpURLConnection(" + url.toExternalForm() + ").openConnection()";
            log.error(message, e);
            throw (ConnectException)new ConnectException(message).initCause(e);
        }
        conn.setRequestProperty("Host", host);
        conn.setRequestProperty("Accept", "application/xml");
        conn.setRequestProperty("Accept-Charset", "utf-8");
        conn.setConnectTimeout(connectionTimeout);
        conn.setReadTimeout(readTimeout);
        Long readStart = System.currentTimeMillis();
    	long summonConnect = -System.currentTimeMillis();
        try {
            conn.connect();
        } catch (Exception e) {
            String message = "Unable to connect to remote Solr with URL '" + url.toExternalForm()
                             + "' and connection timeout " + connectionTimeout;
            log.error(message, e);
            throw (ConnectException)new ConnectException(message).initCause(e);
        }
        summonConnect += System.currentTimeMillis();

        BufferedReader in;
        try {
        	long rawCall = -System.currentTimeMillis();
        	in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                retval.append(str);
            }
            log.trace("Reading from Solr done in " + (System.currentTimeMillis() - readStart) + "ms");
            in.close();
            rawCall += System.currentTimeMillis();
            responses.addTiming(getID() + ".connect", summonConnect);
            responses.addTiming(getID() + ".rawcall", rawCall);

        } catch (Exception e) {
            String error = String.format(
                "getData(host='%s', command='%s') for %s failed with error stream\n%s",
                "http://" + host, command, getID(),
                Strings.flush(new InputStreamReader(conn.getErrorStream(), "UTF-8")));
            log.warn(error, e);
            throw new IOException(error, e);
        }
        // TODO: Should we disconnect?
        //System.out.println(retval.toString());
        return retval.toString();
    }

    /**
     * Generate a map of search backend specific request parameters.
     * @param request    a standard Summa Search request, primarily filled with values from {@link DocumentKeys}.
     * @param filter     a Solr-style filter (same syntax as query). This is based on {@link DocumentKeys#SEARCH_FILTER}
     *                   but might have been rewritten.
     * @param query      a Solr-style query. This is based on {@link DocumentKeys#SEARCH_QUERY} but might have been
     *                   rewritten.
     * @param solrParams optional extended params for Solr. If not null, these will be added to the Solr request.
     * @param facets     which facets to request or null if no facets are wanted.
     * @param startIndex the index for the first Record to return, counting from 0.
     * @param maxRecords number of items per page.
     * @param sortKey    the field to sort on. If null, default ranking sort is used.
     * @param reverseSort if true, sort order is reversed.
     * @return key-value map with multiple values/key.
     * @throws ParseException if the facets could not be parsed.
     */
    protected Map<String, List<String>> buildSolrQuery(
        Request request, String filter, String query, Map<String, List<String>> solrParams, SolrFacetRequest facets,
        int startIndex, int maxRecords, String sortKey, boolean reverseSort) throws ParseException {
        int startPage = Math.max(0, startIndex); // Solr pages exactly as Lucene
        //int startPage = Math.max(0, maxRecords == 0 ? 0 : (startIndex-1) / maxRecords);
        Map<String, List<String>> queryMap = new HashMap<String, List<String>>();
        if (request.containsKey(DocumentKeys.SEARCH_RESULT_FIELDS)) {
            queryMap.put("fl", Arrays.asList(Strings.join(request.getStrings(DocumentKeys.SEARCH_RESULT_FIELDS), ",")));
        }
        if (filter != null) { // We allow missing filter
            boolean facetsHandled = false;
            if (explicitFacetFilter && request.getBoolean(SEARCH_SOLR_FILTER_IS_FACET, false)) {
                Map<String, List<String>> facetRequest = facetQueryTransformer.convertQueryToFacet(filter);
                if (facetRequest == null) {
                    log.debug("Unable to convert facet filter '" + filter + "' to Solr facet request. Switching to "
                              + "filter/query based handling");
                } else {
                    log.debug("Successfully converted filter '" + filter + "' to Solr facet query");
                    queryMap.putAll(facetRequest);
                    facetsHandled = true;
                }
            }
            if (!facetsHandled) {
                if (supportsPureNegative || !request.getBoolean(DocumentKeys.SEARCH_FILTER_PURE_NEGATIVE, false)) {
                    queryMap.put("fq", Arrays.asList(filter)); // FilterQuery
                } else {
                    if (query == null) {
                        throw new IllegalArgumentException(
                            "No query and filter marked with '" + DocumentKeys.SEARCH_FILTER_PURE_NEGATIVE
                            + "' is not possible in Solr. Filter is '" + filter + "'");
                    }
                    query = "(" + query + ") " + filter;
                    log.debug("Munging filter after query as the filter '" + filter + "' is marked '"
                              + DocumentKeys.SEARCH_FILTER_PURE_NEGATIVE + "' and Solr is set up to not support pure "
                              + "negative filters natively. resulting query is '" + query + "'");
                }
            }
        }
        if (query != null) { // We allow missing query
            queryMap.put("q", Arrays.asList(query));
        }

        queryMap.put("start", Arrays.asList(Integer.toString(startPage)));
        queryMap.put("rows", Arrays.asList(Integer.toString(maxRecords)));

        // TODO: Add support for sorting on multiple fields
        if (reverseSort && sortKey == null) {
            sortKey = "score"; // Relevance sorting
        }
        if (sortKey != null) {
            queryMap.put("sort", Arrays.asList(sortKey + " " + (reverseSort ? "desc" : "asc")));
        }

        if (facets != null) { // The facets to display
            queryMap.put("facet", Arrays.asList(Boolean.TRUE.toString()));
            facets.addFacetQueries(queryMap);
        }

        if (solrParams != null) {
            queryMap.putAll(solrParams);
        }
        return queryMap;
    }

}
