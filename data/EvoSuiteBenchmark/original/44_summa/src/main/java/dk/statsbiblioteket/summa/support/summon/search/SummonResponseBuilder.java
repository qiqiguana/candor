/* $Id:$
 *
 * The Summa project.
 * Copyright (C) 2005-2010  The State and University Library
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package dk.statsbiblioteket.summa.support.summon.search;

import dk.statsbiblioteket.summa.common.configuration.Configuration;
import dk.statsbiblioteket.summa.common.util.ConvenientMap;
import dk.statsbiblioteket.summa.common.xml.XMLStepper;
import dk.statsbiblioteket.summa.facetbrowser.api.FacetResult;
import dk.statsbiblioteket.summa.facetbrowser.api.FacetResultExternal;
import dk.statsbiblioteket.summa.search.api.Request;
import dk.statsbiblioteket.summa.search.api.ResponseCollection;
import dk.statsbiblioteket.summa.search.api.document.DocumentKeys;
import dk.statsbiblioteket.summa.search.api.document.DocumentResponse;
import dk.statsbiblioteket.summa.support.summon.search.api.RecommendationResponse;
import dk.statsbiblioteket.util.qa.QAInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.exposed.facet.FacetResponse;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.util.*;

/**
 * Takes a Summon response as per
 * http://api.summon.serialssolutions.com/help/api/search/response
 * and converts it to a {@link DocumentResponse} and a {@link FacetResponse}.
 * Mapping between field & facet names as well as score adjustments or tag
 * tweaking is not in the scope of this converter.
 * </p><p>
 * The Summon API does not return namespace-aware XML so the parser is equally
 * relaxed.
 */
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.IN_DEVELOPMENT,
        author = "te")
public class SummonResponseBuilder extends SolrResponseBuilder {
    private static Log log = LogFactory.getLog(SummonResponseBuilder.class);

    public static final String DEFAULT_SUMMON_SORT_FIELD_REDIRECT = "PublicationDate - PublicationDate_xml_iso";

    /**
     * If true, only the year is used when generating shortformat. If false,
     * month and day is included iso-style, if they are available.
     * </p><p>
     * Optional. Default is true.
     */
    public static final String CONF_SHORT_DATE = "summonresponsebuilder.shortformat.shortdate";
    public static final boolean DEFAULT_SHORT_DATE = true;

    /**
     * If a field name and the same field name with "_xml" both exists, attempt to convert the "_xml"-version to
     * a String and override the non-"_xml"-version.
     * </p><p>
     * Note: This is not guaranteed to catch all xml-variations.
     * </p><p>
     * Optional. Default is true.
     */
    public static final String CONF_XML_OVERRIDES_NONXML = "summonresponsebuilder.xmloverrides";
    public static final boolean DEFAULT_XML_OVERRIDES_NONXML = true;

    public static final String DEFAULT_SUMMON_RECORDBASE = "summon";

    private final boolean shortDate;
    private final boolean xmlOverrides;

    public SummonResponseBuilder(Configuration conf) {
        super(adjust(conf));
        shortDate = conf.getBoolean(CONF_SHORT_DATE, DEFAULT_SHORT_DATE);
        xmlOverrides = conf.getBoolean(CONF_XML_OVERRIDES_NONXML, DEFAULT_XML_OVERRIDES_NONXML);
        log.info("Created SummonResponseBuilder inherited from SolrResponseBuilder");
    }

    private static Configuration adjust(Configuration conf) {
        if (!conf.valueExists(CONF_SORT_FIELD_REDIRECT)) {
            conf.set(CONF_SORT_FIELD_REDIRECT, DEFAULT_SUMMON_SORT_FIELD_REDIRECT);
        }
        if (!conf.valueExists(CONF_RECORDBASE)) {
            conf.set(CONF_RECORDBASE, DEFAULT_SUMMON_RECORDBASE);
        }
        // conversion of legacy properties
        legacyCopy(conf, "summonresponsebuilder.recordbase", CONF_RECORDBASE);
        legacyCopy(conf, "summonresponsebuilder.sort.field.redirect", CONF_SORT_FIELD_REDIRECT);
        return conf;
    }
    private static void legacyCopy(Configuration conf, String oldKey, String newKey) {
        if (conf.valueExists(oldKey) && !conf.valueExists(newKey)) {
            log.warn("The key '" + oldKey + "' is deprecated in favor of '" + newKey
                     + "'. Please adjust settings accordingly to avoid this warning");
            conf.set(newKey, conf.getString(oldKey));
        }
    }

    private boolean rangeWarned = false;
    @Override
    public long buildResponses(Request request, SolrFacetRequest facets, ResponseCollection responses,
                               String solrResponse, String solrTiming) throws XMLStreamException {
        //System.out.println(solrResponse.replace(">", ">\n"));
        long startTime = System.currentTimeMillis();
        boolean facetingEnabled = request.getBoolean(DocumentKeys.SEARCH_COLLECT_DOCIDS, false);
        XMLStreamReader xml;
        try {
            xml = xmlFactory.createXMLStreamReader(new StringReader(solrResponse));
        } catch (XMLStreamException e) {
            throw new IllegalArgumentException("Unable to construct a reader from input for request " + request, e);
        }

        if (!XMLStepper.findTagStart(xml, "response")) {
            log.warn("Could not locate start tag 'response', exiting parsing of response for " + request);
            return 0;
        }
        long searchTime = Long.parseLong(XMLStepper.getAttribute(xml, "totalRequestTime", "0"));
        long hitCount = Long.parseLong(XMLStepper.getAttribute(xml, "recordCount", "0"));
        String summonQueryString = "N/A";
        int maxRecords = -1;
        List<DocumentResponse.Record> records = null;
        // Seek to queries, facets or documents
        String currentTag;
//        long recordTime = 0;
        while ((currentTag = XMLStepper.jumpToNextTagStart(xml)) != null) {
            if ("query".equals(currentTag)) {
                maxRecords = Integer.parseInt(XMLStepper.getAttribute(xml, "pageSize", Integer.toString(maxRecords)));
                summonQueryString = XMLStepper.getAttribute(xml, "queryString", summonQueryString);
                continue;
            }
            if ("rangeFacetFields".equals(currentTag) && facetingEnabled && !rangeWarned) {
                log.warn("buildResponses(...) encountered facet range from summon. Currently there is no support for "
                         + "this. Further encounters of range facets will not be logged");
                rangeWarned = true;
                // TODO: Implement range facets from Summon
            }
            if ("facetFields".equals(currentTag) && facetingEnabled) {
                FacetResult<String> facetResult = extractFacetResult(xml, facets);
                if (facetResult != null) {
                    responses.add(facetResult);
                }
            }
            if ("recommendationLists".equals(currentTag)) {
                RecommendationResponse recommendation = extractRecommendations(xml);
                if (recommendation != null) {
                    responses.add(recommendation);
                }
            }
            if ("documents".equals(currentTag)) {
//                recordTime = -System.currentTimeMillis();
                String sortKey = request.getString(DocumentKeys.SEARCH_SORTKEY, null);
                records = extractRecords(xml, sortKey);
//                recordTime += System.currentTimeMillis();
            }
        }
        if (records == null) {
            log.warn("No records extracted from request " + request + ". Returning 0 hits");
            return 0;
        }
        DocumentResponse documentResponse = createBasicDocumentResponse(request);
        documentResponse.setSearchTime(searchTime);
        documentResponse.setHitCount(hitCount);
        documentResponse.setPrefix(searcherID + ".");
        documentResponse.addTiming("reportedtime", searchTime);
        for (DocumentResponse.Record record: records) {
            documentResponse.addRecord(record);
        }
        addRecordBase(responses, documentResponse.getHitCount());
        documentResponse.addTiming(solrTiming);
        documentResponse.addTiming("buildresponses.documents", System.currentTimeMillis() - startTime);
        responses.add(documentResponse);
        responses.addTiming(searcherID + ".buildresponses.total", System.currentTimeMillis() - startTime);
        return documentResponse.getHitCount();
    }

    private RecommendationResponse extractRecommendations(XMLStreamReader xml) throws XMLStreamException {
        long startTime = System.currentTimeMillis();
        final RecommendationResponse response = new RecommendationResponse();
        XMLStepper.iterateElements(xml, "recommendationLists", "recommendationList",
                                   new XMLStepper.XMLCallback() {
                                       @Override
                                       public void execute(XMLStreamReader xml) throws XMLStreamException {
                                           extractRecommendationList(xml, response);
                                       }
                                   });
        if (response.isEmpty()) {
            return null;
        }
        response.addTiming("buildresponses.recommendations", System.currentTimeMillis() - startTime);
        return response;
    }

    private void extractRecommendationList(
        XMLStreamReader xml, RecommendationResponse response) throws XMLStreamException {
        String type = XMLStepper.getAttribute(xml, "type", null);
        if (type == null) {
            throw new IllegalArgumentException("Type required for recommendationList");
        }
        @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
        final RecommendationResponse.RecommendationList recList = response.newList(type);
        XMLStepper.iterateElements(xml, "recommendationList", "recommendation",
                                   new XMLStepper.XMLCallback() {
                                       @Override
                                       public void execute(XMLStreamReader xml)
                                           throws XMLStreamException {
                                           String title = XMLStepper.getAttribute(xml, "title", null);
                                           if (title == null) {
                                               throw new IllegalArgumentException("Title required for recommendationList");
                                           }
                                           String description = XMLStepper.getAttribute(xml, "description", "");
                                           String link = XMLStepper.getAttribute(xml, "link", "");
                                           recList.addResponse(title, description, link);
                                       }
                                   });
    }

    /**
     * Extracts all facet responses.
     * @param xml    the stream to extract Summon facet information from. Must be positioned at 'facetFields'.
     * @param facets a definition of the facets to extract.
     * @return a Summa FacetResponse.
     * @throws javax.xml.stream.XMLStreamException if there was an error accessing the xml stream.
     */
    private FacetResult<String> extractFacetResult(XMLStreamReader xml, SolrFacetRequest facets)
        throws XMLStreamException {
        long startTime = System.currentTimeMillis();
        HashMap<String, Integer> facetIDs = new HashMap<String, Integer>(facets.getFacets().size());
        // 1 facet = 1 field in Summon-world
        HashMap<String, String[]> fields = new HashMap<String, String[]>(facets.getFacets().size());
        for (int i = 0 ; i < facets.getFacets().size() ; i++) {
            SolrFacetRequest.Facet facet = facets.getFacets().get(i);
            facetIDs.put(facet.getField(), i);
            // TODO: Consider displayname
            fields.put(facet.getField(), new String[]{facet.getField()});
        }
        final FacetResultExternal summaFacetResult = new FacetResultExternal(
            facets.getMaxTags(), facetIDs, fields, facets.getOriginalStructure());
        summaFacetResult.setPrefix(searcherID + ".");
        XMLStepper.iterateElements(xml, "facetFields", "facetField", new XMLStepper.XMLCallback() {
            @Override
            public void execute(XMLStreamReader xml) throws XMLStreamException {
                extractFacet(xml, summaFacetResult);
            }
        });
        summaFacetResult.sortFacets();
        summaFacetResult.addTiming("buildresponses.facets", System.currentTimeMillis() - startTime);
        return summaFacetResult;
    }

    /**
     * Extracts a single facet response and adds it to the facet result.
     * @param xml    the stream to extract Summon facet information from.
     *               Must be positioned at 'facetField'.
     * @param summaFacetResult where to store the extracted facet.
     * @throws javax.xml.stream.XMLStreamException if there was an error
     * accessing the xml stream.
     */
    private void extractFacet(XMLStreamReader xml, final FacetResultExternal summaFacetResult)
        throws XMLStreamException {
         // TODO: Consider fieldname and other attributes?
        final String facetName = XMLStepper.getAttribute(xml, "displayName", null);
        XMLStepper.iterateElements(xml, "facetField", "facetCount", new XMLStepper.XMLCallback() {
            @Override
            public void execute(XMLStreamReader xml) {
                String tagName = XMLStepper.getAttribute(xml, "value", null);
                Integer tagCount = Integer.parseInt(XMLStepper.getAttribute(xml, "count", "0"));
                // <facetCount value="Newspaper Article" count="27" isApplied="true" isNegated="true"
                // isFurtherLimiting="false" removeCommand="removeFacetValueFilter(ContentType,Newspaper Article)"
                // negateCommand="negateFacetValueFilter(ContentType,Newspaper Article)"/>

                boolean isApplied = Boolean.parseBoolean(XMLStepper.getAttribute(xml, "isApplied", "false"));
                boolean isNegated = Boolean.parseBoolean(XMLStepper.getAttribute(xml, "isNegated", "false"));
//                System.out.println("Facet " + facetName + ", tag " + tagName + ", count " + tagCount);
                if (tagCount != 0 && !(isApplied && isNegated)) { // Signifies negative facet value filter
                    summaFacetResult.addTag(facetName, tagName, tagCount);
                }
            }
        });
    }

    /**
     * Extracts all Summon documents and converts them to
     * {@link }DocumentResponse#Record}.
     *
     * @param xml the stream to extract records from. Must be positioned at
     * ELEMENT_START for "documents".
     * @param sortKey if not null, the sort key is assigned to the Record if
     *                it is encountered in the XML.
     * @return an array of record or the empty list if no documents were found.
     * @throws javax.xml.stream.XMLStreamException if there was an error
     * during stream access.
     */
    private List<DocumentResponse.Record> extractRecords(XMLStreamReader xml, final String sortKey)
        throws XMLStreamException {
        // Positioned at documents
        final List<DocumentResponse.Record> records = new ArrayList<DocumentResponse.Record>(50);
        XMLStepper.iterateElements(xml, "documents", "document", new XMLStepper.XMLCallback() {
            float lastScore = 0f;

            @Override
            public void execute(XMLStreamReader xml) throws XMLStreamException {
                DocumentResponse.Record record = extractRecord(xml, sortKey, lastScore);
                if (record != null) {
                    records.add(record);
                    lastScore = record.getScore();
                }
            }

        });
//        fixMissingScores(records);
        return records;
    }
  /*
    final static float ZERO = 0.0f;
    private void fixMissingScores(List<DocumentResponse.Record> records) {
        for (int i = 0 ; i < records.size() ; i++) {
            if (records.get(i).getScore() == ZERO) {
                float newScore
                if (i > 0 && i < records.size() -1) { // Previous and next
                }
            }
        }
    }
    */

    /**
     * Extracts a Summon document and converts it to
     * {@link }DocumentResponse#Record}. The compact representation
     * "shortformat" is generated on the fly and added to the list of fields.
     *
     *
     * @param xml the stream to extract the record from. Must be positioned at
     * ELEMENT_START for "document".
     * @param sortKey if not null, the sort key is assigned to the Record if
     *                it is encountered in the XML.
     * @param lastScore the score for the previous Record. Used if the record does not contain any score.
     * @return a record or null if no document could be extracted.
     * @throws javax.xml.stream.XMLStreamException if there was an error
     * accessing the xml stream.
     */
    private DocumentResponse.Record extractRecord(XMLStreamReader xml, String sortKey, float lastScore)
        throws XMLStreamException {
    // http://api.summon.serialssolutions.com/help/api/search/response/documents
        String openUrl = XMLStepper.getAttribute(xml, "openUrl", null);
        if (openUrl == null) {
            log.warn("Encountered a document without openUrl. Discarding");
            return null;
        }
        String availibilityToken = XMLStepper.getAttribute(xml, "availabilityToken", null);
        String hasFullText =       XMLStepper.getAttribute(xml, "hasFullText", "false");
        String inHoldings =        XMLStepper.getAttribute(xml, "inHoldings", "false");

        final Set<String> wanted = new HashSet<String>(Arrays.asList(
            "ID", "Score", "Title", "Subtitle", "Author", "ContentType", "PublicationDate_xml", "Author_xml"));
        // PublicationDate_xml is a hack
        final String[] sortValue = new String[1]; // Hack to make final mutable
        final ConvenientMap extracted = new ConvenientMap();
        final List<DocumentResponse.Field> fields = new ArrayList<DocumentResponse.Field>(50);
        final String sortField = sortRedirect.containsKey(sortKey) ?
                                 sortRedirect.get(sortKey) : sortKey;

        XMLStepper.iterateElements(xml, "document", "field", new XMLStepper.XMLCallback() {
            @Override
            public void execute(XMLStreamReader xml) throws XMLStreamException {
                DocumentResponse.Field field = extractField(xml);
                if (field != null) {
                    if (wanted.contains(field.getName())) {
                        extracted.put(field.getName(), field.getContent());
                        if ("PublicationDate_xml".equals(field.getName())) {
                            // The iso-thing is a big kludge. If we move the
                            // sort code outside of this loop, it would be
                            // cleaner.
                            extracted.put("PublicationDate_xml_iso", field.getContent());
                            fields.add(new DocumentResponse.Field(
                                "PublicationDate_xml_iso", field.getContent(), false));
                            if ("PublicationDate_xml_iso".equals(sortField)) {
                                sortValue[0] = field.getContent();
                            }
                        }
                    }
                    fields.add(field);
                    if (sortField != null && sortField.equals(field.getName())) {
                        sortValue[0] = field.getContent();
                    }
                }
            }

        });

        if (xmlOverrides) {
            // Author
            for (DocumentResponse.Field field: fields) {
                if ("Author_xml".equals(field.getName())) {
                    for (int i = fields.size() - 1 ; i >= 0 ; i--) {
                        if ("Author".equals(fields.get(i).getName())) {
                            fields.remove(i);
                        }
                    }
                    field.setName("Author");

                    if (extracted.containsKey("Author")) {
                        extracted.put("Author", field.getContent());
                    }

                    break;
                }
            }
        }

        String recordID = extracted.getString("ID", null);

        if (recordID == null) {
            log.warn("Unable to locate field 'ID' in Summon document. Skipping document");
            return null;
        }
        fields.add(new DocumentResponse.Field(DocumentKeys.RECORD_ID, recordID, true));
        fields.add(new DocumentResponse.Field("availibilityToken", availibilityToken, true));
        fields.add(new DocumentResponse.Field("hasFullText", hasFullText, true));
        fields.add(new DocumentResponse.Field("inHoldings", inHoldings, true));

        fields.add(new DocumentResponse.Field("shortformat", createShortformat(extracted), false));

        if (recordBase != null) {
            fields.add(new DocumentResponse.Field("recordBase", recordBase, false));
        }

        String sortV = sortKey == null || sortValue[0] == null ? null : sortValue[0];
        if (!extracted.containsKey("Score")) {
            log.debug("The record '" + recordID + "' did not contain a Score. Assigning " + lastScore);
        }
        DocumentResponse.Record record = new DocumentResponse.Record(
            recordID, searcherID, extracted.getFloat("Score", lastScore), sortV);
        for (DocumentResponse.Field field: fields) {
            record.addField(field);
        }
        return record;
    }

    @Override
    protected String createShortformat(ConvenientMap extracted) {
        String date = extracted.getString("PublicationDate_xml", "????");
        date = shortDate && date.length() > 4 ? date.substring(0, 4) : date;
        extracted.put("Date", date);
        return super.createShortformat(extracted);
    }

    private boolean warnedOnMissingFullname = false;
    private boolean xmlFieldsWarningFired = false;
    /**
     * Extracts a Summon document field and converts it to
     * {@link }DocumentResponse#Field}.
     * </p><p>
     * While this implementation tries to produce fields for all inputs,
     * it is not guaranteed that it will be usable as no authoritative list
     * of possible micro formats used by Summon has been found.
     * @param xml the stream to extract the field from. Must be positioned at
     * ELEMENT_START for "field".
     * @return a field or null if no field could be extracted.
     * @throws javax.xml.stream.XMLStreamException if there was an error
     * accessing the xml stream.
     */
    private DocumentResponse.Field extractField(XMLStreamReader xml) throws XMLStreamException {
        String name = XMLStepper.getAttribute(xml, "name", null);
        if (name == null) {
            log.warn("Could not extract name for field. Skipping field");
            return null;
        }
        // TODO: Implement XML handling and multiple values properly
        if (name.endsWith("_xml")) {
            if ("PublicationDate_xml".equals(name)) {
                /*
                  <field name="PublicationDate_xml">
                    <datetime text="20081215" month="12" year="2008" day="15"/>
                  </field>
                 */
                XMLStepper.findTagStart(xml, "datetime");
                String year = XMLStepper.getAttribute(xml, "year", null);
                if (year == null) {
                    return null;
                }
                String month = XMLStepper.getAttribute(xml, "month", null);
                String day = XMLStepper.getAttribute(xml, "day", null);
                return new DocumentResponse.Field(
                    name, year + (month == null ? "" : month + (day == null ? "" : day)), false);
            }

            if ("Author_xml".equals(name)) {
                /*
                  <field name="Author_xml">
                    <contributor middlename="A" givenname="CHRISTY" surname="VISHER" fullname="VISHER, CHRISTY A"/>
                    <contributor middlename="L" givenname="RICHARD" surname="LINSTER" fullname="LINSTER, RICHARD L"/>
                    <contributor middlename="K" givenname="PAMELA" surname="LATTIMORE" fullname="LATTIMORE, PAMELA K"/>
                  </field>
                 */
                final StringBuffer value = new StringBuffer(50);
                XMLStepper.iterateElements(xml, "field", "contributor", new XMLStepper.XMLCallback() {
                    @Override
                    public void execute(XMLStreamReader xml) throws XMLStreamException {
                        boolean found = false;
                        for (int i = 0; i < xml.getAttributeCount(); i++) {
                            if ("fullname".equals(xml.getAttributeLocalName(i))) {
                                if (value.length() != 0) {
                                    value.append("\n");
                                }
                                value.append(xml.getAttributeValue(i));
                                found = true;
                                break;
                            }
                        }
                        if (!found && !warnedOnMissingFullname) {
                            log.warn("Unable to locate attribute 'fullname' in 'contributor' element in 'Author_xml'. "
                                     + "This warning will not be repeated");
                            warnedOnMissingFullname = true;
                        }
                    }
                });
                if (value.length() == 0) {
                    log.debug("No value for field '" + name + "'");
                    return null;
                } else if (log.isTraceEnabled()) {
                    log.trace("Extracted Author_xml: " + value.toString().replace("\n", ", "));
                }
//                System.out.println(value);
                return new DocumentResponse.Field(name, value.toString(), true);
            }

            if (!xmlFieldsWarningFired) {
                log.warn("XML fields are not supported yet. Skipping '" + name + "'");
                xmlFieldsWarningFired = true;
            }
            return null;
        }
        final StringBuffer value = new StringBuffer(50);
        XMLStepper.iterateElements(xml, "field", "value", new XMLStepper.XMLCallback() {
            @Override
            public void execute(XMLStreamReader xml) throws XMLStreamException {
                value.append(value.length() == 0 ? xml.getElementText() : "\n" + xml.getElementText());
            }
        });
        if (value.length() == 0) {
            log.debug("No value for field '" + name + "'");
            return null;
        }
        return new DocumentResponse.Field(name, value.toString(), true);
    }

}
