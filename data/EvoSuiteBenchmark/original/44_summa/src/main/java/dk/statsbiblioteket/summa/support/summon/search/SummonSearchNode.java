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
package dk.statsbiblioteket.summa.support.summon.search;

import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.lucene.index.IndexUtils;
import dk.statsbiblioteket.summa.common.util.Pair;
import dk.statsbiblioteket.summa.facetbrowser.api.FacetKeys;
import dk.statsbiblioteket.summa.search.api.Request;
import dk.statsbiblioteket.summa.search.api.ResponseCollection;
import dk.statsbiblioteket.summa.search.api.document.DocumentKeys;
import dk.statsbiblioteket.summa.search.api.document.DocumentResponse;
import dk.statsbiblioteket.summa.search.tools.QueryRewriter;
import dk.statsbiblioteket.summa.support.solr.SolrSearchNode;
import dk.statsbiblioteket.util.Strings;
import dk.statsbiblioteket.util.qa.QAInfo;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.security.Security;
import java.security.SignatureException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Transforms requests based on {@link DocumentKeys} and {@link FacetKeys} to
 * calls to Serial Solutions Summon API and transform the result to
 * {@link DocumentResponse} and
 * {@link dk.statsbiblioteket.summa.facetbrowser.api.FacetResult}.
 * See http://api.summon.serialssolutions.com/help/api/ for Summon API.
 * </p><p>
 * Supported DocumentKeys: {@link DocumentKeys#SEARCH_QUERY},
 * {@link DocumentKeys#SEARCH_START_INDEX} and
 * {@link DocumentKeys#SEARCH_MAX_RECORDS}. To adjust for differences between
 * Summa and Summon, 1 is added to the given start index.
 * Supported FacetKeys: {@link FacetKeys#SEARCH_FACET_FACETS} without the sort
 * option.
 */
// Map contenttype, language. Convert date (til år lige nu), potentially library
    // TODO: Check if startpos is 0 or 1, adjuct accordingly
    // TODO: Implement configurable rangefacets
    // TODO Implement getShortRecord
    // TODO: Implement getRecord in Storage
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.QA_NEEDED,
        author = "te, mv")
public class SummonSearchNode extends SolrSearchNode {
    private static Log log = LogFactory.getLog(SummonSearchNode.class);

    /**
     * If true, all configuration parameters, except the explicit ones in this class, that starts with "summon."
     * are converted to start with "solr." in order to conform with the superclass.
     * </p><p>
     * Optional. Default is true;
     */
    public static final String CONF_LEGACY_PREFIX_CONVERT = "summon.legacyprefixconvert";
    public static final boolean DEFAULT_LEGACY_PREFIX_CONVERT = true;

    public static final String DEFAULT_SUMMON_HOST = "api.summon.serialssolutions.com";
//    public static final String DEFAULT_SUMMON_RESTCALL = "/search";
    public static final String DEFAULT_SUMMON_RESTCALL = "/2.0.0/search";

    /**
     * Summon requires filtering on facets to be in summon-format and not as standard filters.
     */
    private static final boolean DEFAULT_SUMMON_EXPLICIT_FACET_FILTERING = true;
    /**
     * The access ID for Summon. Serial Solutions controls this and in order to use this filter, a valid access ID must
     * be provided.
     * </p><p>
     * Mandatory.
     * @see {@link #CONF_SUMMON_ACCESSKEY}.
     */
    public static final String CONF_SUMMON_ACCESSID = "summon.accessid";

    /**
     * The access key for Summon. Serial Solutions controls this and in order to use this filter, a valid access key
     * must be provided.
     * </p><p>
     * Mandatory.
     * @see {@link #CONF_SUMMON_ACCESSID}.
     */
    public static final String CONF_SUMMON_ACCESSKEY = "summon.accesskey";

    public static final String DEFAULT_SUMMON_IDPREFIX = "summon_";

//    See http://api.summon.serialssolutions.com/help/api/search/commands/add-facet-field
    public static final String DEFAULT_SUMMON_FACETS =
        "Author, " // Map
        + "ContentType, " // Map 1:n
//        + "Genre, " // Direct // Does not exist anymore as of 20120402
        + "IsScholarly, " // Direct
        + "Language, " // Map
        + "IsFullText, "
        //"Library,and,1,5",
        //"PackageID,and,1,5",
        //"SourceID,and,1,5",
        + "SubjectTerms, "; // SB:subject
        //+ "TemporalSubjectTerms"; // Direct // Does not exist anymore as of 20120402


    /**
     * If true, queries will be actively re-written to ensure that they do not conform to any format handled by the
     * DisMax-like parser for summon.
     * </p><p>
     * Optional. Default is false.
     */
    public static final String CONF_DISMAX_SABOTAGE = "summon.dismax.sabotage";
    public static final String SEARCH_DISMAX_SABOTAGE = CONF_DISMAX_SABOTAGE;
    public static final boolean DEFAULT_DISMAX_SABOTAGE = false;

    public static final String DEFAULT_SUMMON_ID_FIELD = "id";

    /**
     * Used for emulating recordBase:notsummon in facet requests.
     * The facet must be specified as "existingfacet:nonexistingvalue".
     * </p><p>
     * Optional. Default is 'Author:nonexisting34538'.
     */
    public static final String CONF_NONMATCHING_FACET = "summon.facet.nonmatching";
    public static final String DEFAULT_NONMATCHING_FACET = "Author:nonexisting34538";

    /**
     * Used for emulating recordBase:notsummon in query and filter requests.
     * The query must be specified as "existingfield:nonexistingvalue".
     * </p><p>
     * Optional. Default is 'Author:nonexisting34538'.
     */
    public static final String CONF_NONMATCHING_QUERY = "summon.query.nonmatching";
    public static final String DEFAULT_NONMATCHING_QUERY = "Author:nonexisting34538";

    /**
     * If true, range queries of the type {@code PublicationYear:[YYYY TO yyyy]} are treated as
     * {@code PublicationYear:[YYYY TO yyyy-12-31]}.
     * </p><p>
     * Optional. Default is true.
     */
    public static final String CONF_FIX_RANGE_PUBLICATION = "summon.range.publication.fix";
    public static final boolean DEFAULT_FIX_RANGE_PUBLICATION = true;

    private final String accessID;
    private final String accessKey;
    private final Configuration conf; // Used when constructing QueryRewriter
    private final boolean sabotageDismax;
    private final String nonMatchingFacet;
    private final TermQuery nonMatchingQuery;

    public SummonSearchNode(Configuration conf) throws RemoteException {
        super(legacyConvert(conf));
        setID("summon");
        this.conf = conf;
        accessID =   conf.getString(CONF_SUMMON_ACCESSID);
        accessKey =  conf.getString(CONF_SUMMON_ACCESSKEY);
        for (Map.Entry<String, Serializable> entry : conf) {
            convertSolrParam(solrDefaultParams, entry);
        }
        sabotageDismax = conf.getBoolean(CONF_DISMAX_SABOTAGE, DEFAULT_DISMAX_SABOTAGE);
        nonMatchingFacet = conf.getString(CONF_NONMATCHING_FACET, DEFAULT_NONMATCHING_FACET);
        String[] qt = conf.getString(CONF_NONMATCHING_QUERY, DEFAULT_NONMATCHING_QUERY).split(":");
        nonMatchingQuery = new TermQuery(new Term(qt[0], qt[1]));
//        boolean fixPublication = conf.getBoolean(CONF_FIX_RANGE_PUBLICATION, DEFAULT_FIX_RANGE_PUBLICATION);
        readyWithoutOpen();
        log.info(String.format("Created Summon wrapper (host=%s, sabotageDismax=%b)", host, sabotageDismax));
    }

    @Override
    protected SolrResponseBuilder createResponseBuilder(Configuration conf) {
        return new SummonResponseBuilder(conf);
    }

    @Override
    protected FacetQueryTransformer createFacetQueryTransformer(final Configuration conf) {
        return new FacetQueryTransformer(conf) {
            String NONMATCHING = null;
            @Override
            protected void addFacetQuery(
                Map<String, List<String>> queryMap, String field, String value, boolean negated) {
                if (NONMATCHING == null) { // Ugly, but initialization order dictates it
                    NONMATCHING = nonMatchingFacet.replace(":", ",") + ",false";
                }
                if ("recordBase".equals(field)) {
                    if ((negated && responseBuilder.getRecordBase().equals(value))
                    || (!negated && !responseBuilder.getRecordBase().equals(value))) {
                        append(queryMap, "s.fvf", NONMATCHING);
                    }
                } else {
                    append(queryMap, "s.fvf", field + "," + value + "," + negated);
                }
            }
        };
    }

    // Converts the Configuration so that legacy-versions of the properties (those starting with "summon.") are
    // copied to new versions (prefix ".solr").
    private static Configuration legacyConvert(Configuration conf) {
        if (conf.getBoolean(CONF_LEGACY_PREFIX_CONVERT, DEFAULT_LEGACY_PREFIX_CONVERT)) {
            final String[] SKIP = new String[]{CONF_SUMMON_ACCESSID, CONF_SUMMON_ACCESSKEY};
            Configuration adds = Configuration.newMemoryBased();
            for (Map.Entry<String, Serializable> entry: conf) {
                if (entry.getKey().startsWith("summon.") && Arrays.binarySearch(SKIP, entry.getKey()) < 0) {
                    adds.set("solr." + entry.getKey().substring(0, 7), entry.getValue());
                }
                if (entry.getKey().startsWith("summonparam.") && Arrays.binarySearch(SKIP, entry.getKey()) < 0) {
                    adds.set("solr.param." + entry.getKey().substring(0, 12), entry.getValue());
                }
            }
            for (Map.Entry<String, Serializable> entry: adds) {
                conf.set(entry.getKey(), entry.getValue());
            }
        }

        if (!conf.valueExists(CONF_SOLR_HOST)) {
            conf.set(CONF_SOLR_HOST, DEFAULT_SUMMON_HOST);
        }
        if (!conf.valueExists(CONF_SOLR_RESTCALL)) {
            conf.set(CONF_SOLR_RESTCALL, DEFAULT_SUMMON_RESTCALL);
        }
        if (!conf.valueExists(CONF_SOLR_IDPREFIX)) {
            conf.set(CONF_SOLR_IDPREFIX, DEFAULT_SUMMON_IDPREFIX);
        }
        if (!conf.valueExists(CONF_SOLR_FACETS)) {
            conf.set(CONF_SOLR_FACETS, DEFAULT_SUMMON_FACETS);
        }
        if (!conf.valueExists(CONF_ID_FIELD)) {
            conf.set(CONF_ID_FIELD, DEFAULT_SUMMON_ID_FIELD);
        }
        if (!conf.valueExists(CONF_EXPLICIT_FACET_FILTERING)) {
            conf.set(CONF_EXPLICIT_FACET_FILTERING, DEFAULT_SUMMON_EXPLICIT_FACET_FILTERING);
        }
        return conf;
    }

    /**
     * It seems that the Summon query parser does not support ranges. Instead it expects ranges to be stated in 's.rf'
     * as 'field,minvalue:maxvalue'. This method parses the query, extracts & removes the range query parts and adds
     * them to the Summon search parameters.
     * </p><p>
     * Similarly, term queries with the field 'ID' are special as the text section must be modified by stripping leading
     * {@link #idPrefix}.
     * @param query as entered by the user.
     * @param summonSearchParams range-queries are added to this.
     * @return the query minus range queries.
     */
    @Override
    public String convertQuery(final String query, final Map<String, List<String>> summonSearchParams) {
        // TODO: Perform legacy conversion "summonparam.*" -> "solrparam.*"
        final String RF = "s.rf"; // RangeField
        final String BASE = "recordBase";
        try {
            return new QueryRewriter(conf, null, new QueryRewriter.Event() {
                @Override
                public Query onQuery(BooleanQuery query) {
                    // Rule: At least 1 clause must match
                    boolean noMatches = true;
                    boolean match = false;
                    List<BooleanClause> reduced = new ArrayList<BooleanClause>(query.clauses().size());
                    for (BooleanClause clause: query.clauses()) {
                        if (clause.getQuery() instanceof TermQuery
                            && BASE.equals(((TermQuery)clause.getQuery()).getTerm().field())) {
                            TermQuery tq = (TermQuery)clause.getQuery();
                            boolean baseMatch = responseBuilder.getRecordBase().equals(tq.getTerm().text());

                            if (BooleanClause.Occur.MUST == clause.getOccur()) {
                                if (baseMatch) {
                                    noMatches = false; // Match encountered
                                    match = true;
                                } else {
                                    return nonMatchingQuery;
                                }
                            } else if (BooleanClause.Occur.MUST_NOT == clause.getOccur()) {
                                if (baseMatch) {
                                    return nonMatchingQuery;
                                }
                                // MUST_NOT unfulfilled queries does not affect the evaluation as Lucene does not
                                // support all negative clauses
                            } else { // SHOULD
                                if (baseMatch) {
                                    noMatches = false; // Match encountered
                                    match = true;
                                }
                                // SHOULD unfulfilled queries does not affect the evaluation
                            }
                        } else {
                            noMatches = false;
                            reduced.add(clause);
                        }
                    }
                    if (noMatches) {
                        // There are no matches
                        return nonMatchingQuery;
                    }
                    if (reduced.isEmpty() && match) {
                        // No queries left, but we encountered at least 1 match
                        return null; // As we do not have match-all
                    }
                    // There may be matches
                    query.clauses().clear();
                    query.clauses().addAll(reduced);
                    return query;
                }

                @Override
                public Query onQuery(TermRangeQuery query) {
                    if (summonSearchParams == null ) {
                        return query;
                    }
                    List<String> sq = summonSearchParams.get(RF);
                    if (sq == null) {
                        sq = new ArrayList<String>();
                        summonSearchParams.put(RF, sq);
                    }
                    if ("PublicationYear".equals(query.getField())
                        && query.getLowerTerm().utf8ToString().length() == 4
                        && query.getUpperTerm().utf8ToString().length() == 4) {
                        sq.add("PublicationDate," + query.getLowerTerm().utf8ToString() + ":"
                               + query.getUpperTerm().utf8ToString() + "-12-31");
                    } else {
                        sq.add(query.getField() + "," + query.getLowerTerm().utf8ToString() + ":"
                               + query.getUpperTerm().utf8ToString());
                    }
                    return null;
                }

                @Override
                public Query onQuery(TermQuery query) {
                    // ID is first class so no configuration here
                    if ("ID".equals(query.getTerm().field()) ||
                        IndexUtils.RECORD_FIELD.equals(query.getTerm().field())) {
                        String text = query.getTerm().text();
                        if (idPrefix != null && text != null && text.startsWith(idPrefix)) {
                            text = text.substring(idPrefix.length());
                        }
                        TermQuery tq = new TermQuery(new Term("ID", text));
                        tq.setBoost(query.getBoost());
                        return tq;
                    } else if ("recordBase".equals(query.getTerm().field())) {
                        if (responseBuilder.getRecordBase().equals(query.getTerm().text())) {
                            return null; // The same as matching (in a klugdy hacky way)
                        } else {
                            return nonMatchingQuery;
                        }
                    }
                    return query;
                }

/*                @Override
                public BooleanClause createBooleanClause(Query query, BooleanClause.Occur occur) {
                    if (query instanceof TermQuery) {
                        TermQuery tq = (TermQuery)query;
                        if ("recordBase".equals(tq.getTerm().field())) {
                            if (occur == BooleanClause.Occur.MUST &&
                                responseBuilder.getRecordBase().equals(tq.getTerm().text())) {
                                return null; // The same as matching (in a klugdy hacky way)
                            }
                            if (occur == BooleanClause.Occur.MUST_NOT &&
                                !responseBuilder.getRecordBase().equals(tq.getTerm().text())) {
                                // Guaranteed non matching
                                return super.createBooleanClause(nonMatchingQuery, BooleanClause.Occur.MUST);
                            }
                        }
                    }
                    return super.createBooleanClause(query, occur);
                }*/
            }).rewrite(query);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error parsing '" + query + "'", e);
        }
    }

    /**
     * Perform a search in Summon.
     *
     * @param request    the basic request.
     * @param filter     a Solr-style filter (same syntax as query).
     * @param query      a Solr-style query.
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
    @Override
    protected Pair<String, String> solrSearch(
        Request request, String filter, String query, Map<String, List<String>> solrParams, SolrFacetRequest facets,
        int startIndex, int maxRecords, String sortKey, boolean reverseSort, ResponseCollection responses)
                                                                                                throws RemoteException {
        long buildQuery = -System.currentTimeMillis();

        // Summon treats startIndex as pages and counts from 1
        // This is handled in buildSolrQuery now
//        startIndex = startIndex == 0 ? 1 : maxRecords / startIndex + 1;

        log.trace("Calling simpleSearch(" + query + ", " + facets + ", " + startIndex + ", " + maxRecords + ")");
        Map<String, List<String>> queryMap;
        try {
            queryMap = buildSolrQuery(
                request, filter, query, solrParams, facets, startIndex, maxRecords, sortKey, reverseSort);
        } catch (ParseException e) {
            throw new RemoteException("Unable to build Solr query", e);
        }

        Date date = new Date();
        String idstring = computeIdString("application/xml", summonDateFormat.format(date), host, restCall, queryMap);
        String queryString = computeSortedQueryString(queryMap, true);
        buildQuery += System.currentTimeMillis();
        log.trace("Parameter preparation done in " + buildQuery + "ms");
        String result;

        try {
            result = getData("http://" + host, restCall + "?" + queryString, date, idstring, null,responses);
        } catch (Exception e) {
            throw new RemoteException("SummonSearchNode: Unable to perform remote call to "  + host + restCall
                                      + " with argument '" + queryString, e);
        }
        long prefixIDs = -System.currentTimeMillis();
        String retval = prefixIDs(result, idPrefix);
        prefixIDs += System.currentTimeMillis();
        return new Pair<String, String>(retval, "summon.buildquery:" + buildQuery +  "|summon.prefixIDs:" + prefixIDs);
    }

    @Override
    protected SolrFacetRequest createFacetRequest(
        String facetsDef, int minCount, int defaultFacetPageSize, String combineMode) {
        return new SolrFacetRequest(facetsDef, minCount, defaultFacetPageSize, combineMode) {
            @Override
            protected void addFacetQuery(
                Map<String, List<String>> queryMap, String field, String combineMode, int startPage, int pageSize) {
                append(queryMap, "s.ff", field + "," + combineMode + ",1," + pageSize);
            }
        };
    }

    @Override
    protected Map<String, List<String>> buildSolrQuery(
        Request request, String filter, String query, Map<String, List<String>> solrParams, SolrFacetRequest facets,
        int startIndex, int maxRecords, String sortKey, boolean reverseSort) throws ParseException {

        if (maxRecords <= 0) {
            maxRecords = DocumentKeys.DEFAULT_MAX_RECORDS;
        }
        @SuppressWarnings("UnnecessaryLocalVariable")
        int perPage = maxRecords;

        // Solr starts at page 1
        int startPage = startIndex == 0 ? 1 : startIndex / perPage + 1;
        Map<String, List<String>> queryMap = new HashMap<String, List<String>>();

        queryMap.put("s.dym", Arrays.asList("true"));
        queryMap.put("s.ho", Arrays.asList("true"));
        if (filter != null) { // We allow missing filter
            boolean facetsHandled = false;
            if (request.getBoolean(SEARCH_SOLR_FILTER_IS_FACET, false)) {
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
                    String reducedFilter = convertQuery(filter, null);
                    if (reducedFilter != null) {
                        queryMap.put("s.fq", Arrays.asList(reducedFilter)); // FilterQuery
                    }
                } else {
                    if (query == null) {
                        throw new IllegalArgumentException(
                            "No query and filter marked with '" + DocumentKeys.SEARCH_FILTER_PURE_NEGATIVE
                            + "' is not possible in summon. Filter is '" + filter + "'");
                    }
                    query = "(" + query + ") " + filter;
                    log.debug("Munging filter after query as the filter '" + filter + "' is marked '"
                              + DocumentKeys.SEARCH_FILTER_PURE_NEGATIVE + "' and summon is set up to not support pure "
                              + "negative filters natively. resulting query is '" + query + "'");
                }
            }
        }
        if (query != null) { // We allow missing query
            if (request.getBoolean(SEARCH_DISMAX_SABOTAGE, sabotageDismax)) {
                query = "(" + query + ")";
                log.debug("Sabotaging summon DisMax by putting the query in parentheses: '" + query + "'");
            }
            queryMap.put("s.q", Arrays.asList(query));
        }

        queryMap.put("s.ps", Arrays.asList(Integer.toString(perPage)));
        queryMap.put("s.pn", Arrays.asList(Integer.toString(startPage)));

        // TODO: Add support for sorting on multiple fields
        if (sortKey != null) {
            queryMap.put("s.sort", Arrays.asList(sortKey + ":" + (reverseSort ? "desc" : "asc")));
        }

        if (facets != null) {
            facets.addFacetQueries(queryMap);
        } else {
            log.debug("No facets specified, skipping faceting");
        }

        List<String> rangefacet = new ArrayList<String>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        StringBuilder ranges = new StringBuilder();
        // generate 15 entries in the publication date facet starting from current year and working our way back
        ranges.append(currentYear).append(":");
        for (int i=1; i<15; i++) {
            ranges.append(",");
            ranges.append(currentYear - i).append(":").append(currentYear - i);
        }
        ranges.append(",").append(":").append(currentYear - 15);
        rangefacet.add("PublicationDate," + ranges.toString());
        queryMap.put("s.rff", rangefacet);

        if (solrParams != null) {
            queryMap.putAll(solrParams);
        }
        return queryMap;
    }

    private static String computeIdString(String acceptType, String  date, String  host, String  path,
                                          Map<String, List<String>> queryParameters) {
        return appendStrings(acceptType, date, host, path, computeSortedQueryString(queryParameters, false));
    }


    /**
     * Append the strings together with '\n' as a delimiter
     * @param strings The Strings to append together
     * @return A new String containing all the input Strings separated by '\n'
     */
    private static String appendStrings(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            stringBuilder.append(string).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Calculate a base64 sha-1 digest for the input.
     * @param key The key to use while calculating the digest.
     * @param idString The String to digest.
     * @return A String containing a base64 encoded sha-1 digest.
     * @throws SignatureException in case of Signature problems.
     */
    private static String buildDigest(String key, String idString) throws SignatureException {
        try {
            String algorithm = "HmacSHA1";
            Charset charset = Charset.forName("utf-8");
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(signingKey);
            return new String(Base64.encodeBase64(mac.doFinal(idString.getBytes(charset))), charset);
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
    }


    static {
        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    }
    DateFormat summonDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US);
    /*
     * Gets the data from the remote Summon server
     * @param target
     * @param content
     * @param date
     * @param idstring
     * @param sessionId
     * @return A String containing XML describing the result of the call
     * @throws Exception
     */
    private String getData(String target, String content, Date date, String idstring,
                           String sessionId, ResponseCollection responses) throws Exception {
        StringBuilder retval = new StringBuilder();

        if (log.isDebugEnabled()) {
            log.debug("Performing Summon request for '" + content + "'");
        }

        URL url = new URL(target + content);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Host", host);
        conn.setRequestProperty("Accept", "application/xml");
        conn.setRequestProperty("Accept-Charset", "utf-8");
        conn.setRequestProperty("x-summon-date", summonDateFormat.format(date));
        conn.setRequestProperty("Authorization", "Summon " + accessID + ";" + buildDigest(accessKey, idstring));
        if (sessionId != null && !"".equals(sessionId)) {
            conn.setRequestProperty("x-summon-session-id", sessionId);
        }
        conn.setConnectTimeout(connectionTimeout);
        conn.setReadTimeout(readTimeout);
        Long readStart = System.currentTimeMillis();
    	long summonConnect = -System.currentTimeMillis();
        conn.connect();
        summonConnect += System.currentTimeMillis();
        
        BufferedReader in;
        try {
        	long rawCall = -System.currentTimeMillis();             
        	in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String str;
        
            while ((str = in.readLine()) != null) {
                retval.append(str);
            }
            log.trace("Reading from Summon done in " + (System.currentTimeMillis() - readStart) + "ms");
            in.close();
            rawCall += System.currentTimeMillis();
            responses.addTiming(getID() + ".connect", summonConnect);
            responses.addTiming(getID() + ".rawcall", rawCall);
            
        } catch (SocketTimeoutException e) {
            String error = String.format(
                "getData(target='%s', content='%s', date=%s, idstring='%s', sessionID=%s) timed out",
                target, content, date, idstring, sessionId);
            log.warn(error, e);
            throw new IOException(error, e);
        } catch (Exception e) {
            String error = String.format(
                "getData(target='%s', content='%s', date=%s, idstring='%s', sessionID=%s) failed with error stream\n%s",
                target, content, date, idstring, sessionId,
                conn.getErrorStream() == null ? "N/A" :
                Strings.flush(new InputStreamReader(conn.getErrorStream(), "UTF-8")));
            log.warn(error, e);
            throw new IOException(error, e);
        }
        // TODO: Should we disconnect?

        return retval.toString();
    }

    /**
     * Takes the xml result from a Summon query and prefixes all IDs with prefix. It looks for the value-tag in
     * <field name="ID">.
     * @param content xml result from a Summon query as a String
     * @param prefix the String the use as prefix
     * @return content with all IDs prefixed with prefix
     */
    private static String prefixIDs(String content, String prefix) {
        // <field name="ID">
        // <value>

        StringBuilder retval = new StringBuilder();

        Pattern pattern = Pattern.compile("<field name=\"ID\">\\s*<value>([^>]+)</value>");
        Matcher matcher = pattern.matcher(content);

        int index = 0;
        while (matcher.find()) {
            retval.append(content.substring(index, matcher.start(1)));
            retval.append(prefix).append(matcher.group(1));
            index = matcher.end(1);
        }
        retval.append(content.substring(index));

        return retval.toString();
    }
}
