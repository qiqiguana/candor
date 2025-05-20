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
package dk.statsbiblioteket.summa.storage.api;

import dk.statsbiblioteket.summa.common.Record;
import dk.statsbiblioteket.summa.common.util.StringMap;
import dk.statsbiblioteket.util.Strings;
import dk.statsbiblioteket.util.qa.QAInfo;

import java.io.Serializable;

/**
 * {@code QueryFilter} is an immutable filter that is passed into the methods
 * in the {@link ReadableStorage} interface to add additional criteria the
 * records must match.
 * <p/>
 * There are four core options one can set on a query. It is possible to add
 * additional custom options to the storage by passing them in via the
 * {@code meta} argument.
 * <p/>
 * The main options are:
 * <ul>
 *   <li>{@code deletedFilter} : If {@code null} allow all records disregarding
 *                              their state. If {@code true} match only deleted
 *                              records, and if {@code false} match only records
 *                              that are not deleted</li>
 *   <li>{@code indexableFilter} : If {@code null} allow all records disregarding
 *                             their state. If {@code false} match only records
 *                             not indexable, and if {@code true} match only
 *                             records that are marked indexable</li>
 *   <li>{@code childDepth} : The number of child levels to expand children to.
 *                            If this value is {@code 0} then no expansion will
 *                            occur, if it is {@code -1} expansion will occur
 *                            recursively to any depth</li>
 *   <li>{@code parentHeight} : Number of levels to expand parent records
 *                              upwards. If this value is {@code 0} then no
 *                              parent expansion occurs, and if it is
 *                             {@code -1} do recursive expansion up to the root
 *                             record</li>
 * </ul>
 *
 * <h3>Null Options</h3>
 * If the whole {@code QueryOptions} object or any one of the options on it
 * is {@code null} it indicates a fully permisive option.
 * A {@code QueryOptions} set to {@code null} will allow all records and do no
 * expansion of children or parents.
 *
 * <h3>Meta flags</h3>
 * Query options can host an arbitrary map of key/value pairs known as meta
 * flags. These flags can be used to alter the behaviour of queries a to pass
 * sepcial information to batch jobs.
 * <p/>
 * Pre defined meta flags:
 * <ul>
 *   <li><tt>ALLOW_PRIVATE</tt> - Set this to {@code "true"} to allow
 *       {@code getRecord()} requests to access storage private records,
 *       like {@code __holdings__}</li>
 *   <li><tt>TRY_UPDATE</tt> - If this flag is set this to {@code "true"} any
 *       calls to {@code flush()} or {@code flushAll()} will not update
 *       the record if it already exists with the exact same fields. This also
 *       means that the record modication time will not be updated if it is
 *       already known</li>
 * </ul>
 */
@QAInfo(level = QAInfo.Level.NORMAL,
        state = QAInfo.State.QA_NEEDED,
        author = "mke, hbk")
public class QueryOptions implements Serializable {
    private static final long serialVersionUID = 196855L;

    /* The order of these are significant as they are used by StatementHandler to generate SQL queries */
    public static enum ATTRIBUTES {
        ID,
        BASE,
        DELETED,
        INDEXABLE,
        HAS_RELATIONS,
        CONTENT,
        CREATIONTIME,
        MODIFICATIONTIME,
        META,
        PARENTS,
        CHILDREN
    }

    public static final ATTRIBUTES[] ATTRIBUTES_SANS_CONTENT_AND_META = new ATTRIBUTES[]{
        ATTRIBUTES.ID,
        ATTRIBUTES.BASE,
        ATTRIBUTES.DELETED,
        ATTRIBUTES.INDEXABLE,
        ATTRIBUTES.HAS_RELATIONS,
        ATTRIBUTES.CREATIONTIME,
        ATTRIBUTES.MODIFICATIONTIME,
        ATTRIBUTES.PARENTS,
        ATTRIBUTES.CHILDREN
    };

    public static final ATTRIBUTES[] ATTRIBUTES_ALL = new ATTRIBUTES[]{
        ATTRIBUTES.ID,
        ATTRIBUTES.BASE,
        ATTRIBUTES.DELETED,
        ATTRIBUTES.INDEXABLE,
        ATTRIBUTES.HAS_RELATIONS,
        ATTRIBUTES.CONTENT,
        ATTRIBUTES.CREATIONTIME,
        ATTRIBUTES.MODIFICATIONTIME,
        ATTRIBUTES.META,
        ATTRIBUTES.PARENTS,
        ATTRIBUTES.CHILDREN
    };

    /**
     * Iff {@code true} match only deleted records, and if {@code false} match
     * only records that are not deleted.
     */
    protected Boolean deletedFilter;

    /**
     * If {@code null} allow all records disregarding their state. If
     * {@code false} match only records not indexable, and if {@code true} match
     * only records that are marked indexable.
     */
    protected Boolean indexableFilter;

    /**
     * The number of child levels to expand children to. If this value is
     * {@code 0} then no expansion will occur, if it is {@code -1} expansion
     * will occur recursively to any depth.
     */
    protected int childDepth;

    /**
     * Number of levels to expand parent records upwards. If this value is
     * {@code 0} then no parent expansion occurs, and if it is {@code -1} do
     * recursive expansion up to the root record.
     */
    protected int parentHeight;

    /**
     * Array of attributes.
     */
    protected ATTRIBUTES[] attributes = null;

    /**
     * String map of all metadata.
     */
    protected StringMap meta;

    /**
     * Constructor for an immutable filter that can be passed to
     * {@link ReadableStorage}, for further constraints on which records to
     * fetch. This constructor makes it possible to define attributes from
     * record to be fetched from storage.
     *
     * @param deletedFilter The deleted filter {@link #deletedFilter}.
     * @param indexableFilter The indexable filter {@link #deletedFilter}.
     * @param childDepth The child depth for records to fetch
     * {@link #childDepth}.
     * @param parentHeight The parent heighh for records to fetch
     * {@link #childDepth}.
     * @param meta A local StringMap for storing meta values for these query
     * options.
     * @param attributes Attributes to fetch from storage.
     */
    public QueryOptions(Boolean deletedFilter, Boolean indexableFilter, int childDepth, int parentHeight,
                        StringMap meta, ATTRIBUTES[] attributes) {
        this(deletedFilter, indexableFilter, childDepth, parentHeight, meta);
        this.attributes = attributes;
    }

    /**
     * Constructor for an immutable filter that can be passed to
     * {@link ReadableStorage}, for further constraints on which records to
     * fetch.
     *
     * @param deletedFilter The deleted filter {@link #deletedFilter}.
     * @param indexableFilter The indexable filter {@link #deletedFilter}.
     * @param childDepth The child depth for records to fetch
     * {@link #childDepth}.
     * @param parentHeight The parent height for records to fetch
     * {@link #childDepth}.
     * @param meta A local StringMap for storing meta values for these query
     * options.
     */
    public QueryOptions(Boolean deletedFilter, Boolean indexableFilter,
                        int childDepth, int parentHeight, StringMap meta) {
        this.deletedFilter = deletedFilter;
        this.indexableFilter = indexableFilter;
        this.childDepth = childDepth;
        this.parentHeight = parentHeight;
        this.meta = meta;
    }

    /**
     * Calls
     * {@link QueryOptions#QueryOptions(Boolean, Boolean, int, int, StringMap)}
     * with QueryOptions(Boolean, Boolean, int, int, null).
     *
     * @param deletedFilter The deleted filter {@link #deletedFilter}.
     * @param indexableFilter The indexable filter {@link #deletedFilter}.
     * @param childDepth The child depth for records to fetch
     * {@link #childDepth}.
     * @param parentHeight The parent height for records to fetch
     * {@link #childDepth}.
     */
    public QueryOptions(Boolean deletedFilter, Boolean indexableFilter, int childDepth, int parentHeight) {
        this(deletedFilter, indexableFilter, childDepth, parentHeight, null);
    }

    /**
     * Calls
     * {@link QueryOptions#QueryOptions(Boolean, Boolean, int, int, StringMap)}
     * with QueryOptions(null, null, 0, 0, null).
     */
    public QueryOptions() {
        this(null, null, 0, 0, null);
    }    

    /**
     * Create a clone of {@code original}.
     *
     * @param original A non-{@code null} QueryOptions object.
     */
    public QueryOptions(QueryOptions original) {
        this(original.deletedFilter(),
             original.indexableFilter(),
             original.childDepth(),
             original.parentHeight(),
             original.meta() == null ? null : new StringMap(original.meta()),
             copy(original.attributes));
    }

    private static ATTRIBUTES[] copy(ATTRIBUTES[] attributes) {
        if (attributes == null) {
            return null;
        }
        ATTRIBUTES[] copy = new ATTRIBUTES[attributes.length];
        System.arraycopy(attributes, 0, copy, 0, attributes.length);
        return copy;
    }
    /**
     * Return boolean value for {@link #deletedFilter}.
     *
     * @return value Of {@link #deletedFilter}.
     */
    public Boolean deletedFilter() {
        return deletedFilter;
    }

    /**
     * Return true if this filter has a {@link #deletedFilter} attribute.
     *
     * @return True if this filter has a {@link #deletedFilter} attribute.
     */
    public boolean hasDeletedFilter() {
        return deletedFilter != null;
    }

    /**
     * Return boolean value for {@link #indexableFilter}.
     *
     * @return Value of {@link #indexableFilter}.
     */
    public Boolean indexableFilter () {
        return indexableFilter;
    }

    /**
     * Return true if this filter has an {@link #indexableFilter} defined.
     *
     * @return True if this filter has an {@link #indexableFilter} defined.
     */
    public boolean hasIndexableFilter () {
        return indexableFilter != null;
    }

    /**
     * Return boolean value for {@link #childDepth}.
     *
     * @return Value of {@link #childDepth}.
     */
    public int childDepth () {
        return childDepth;
    }

    /**
     * Return true if this filter has an {@link #parentHeight} defined.
     *
     * @return True if this filter has an {@link #parentHeight} defined.
     */
    public int parentHeight () {
        return parentHeight;
    }

    /**
     * Return true if this filter has {@link #meta} and this StringMap isn't
     * empty.
     *
     * @return True {@link #meta} defined and not empty.
     */
    public boolean hasMeta () {
        return meta != null && !meta.isEmpty();
    }

    /**
     * Return the underlying {@link StringMap} used to store the meta values
     * for these query options.
     * @return The underlying StringMap or {@code null} if the options does
     *         not have any meta values associated with them.
     */
    public StringMap meta() {
        return meta;
    }

    /**
     * Get a meta-value on these query options.
     * @param key The meta key to look up.
     * @return The meta value or {@code null} if no value corresponds to
     *         {@code key}.
     */
    public String meta(String key) {
        return meta == null ? null : meta.get(key);
    }

    /**
     * Set a meta-value on these query options.
     * @param key The key to store the value under.
     * @param value The value to associate with {@code key}.
     */
    public void meta(String key, String value) {
        if (meta == null) {
            meta = new StringMap();
        }
        meta.put(key, value);
    }

    /**
     * Check whether a given record has the required deletion and indexable
     * flags to match this set of query options.
     * @param r The record to check.
     * @return True iff the record has the required deletion and indexable
     * flags.
     */
    public boolean allowsRecord (Record r) {
        if (hasDeletedFilter()) {
            return r.isDeleted() == deletedFilter();
        }

        return !hasIndexableFilter() || r.isIndexable() == indexableFilter();
    }

    /**
     * Return true if QueryOptions is created to only allow a sub-part of a
     * record. Eg. only attribute id is needed for the record.
     * Note: To get new record, call {@link #getNewRecord(Record)}.
     *
     * @return True if these QueryOptions says only a sup-part of record is
     * needed. False otherwise.
     */
    public boolean newRecordNeeded() {
        return attributes != null;
    }

    /**
     * This method is used for only fetching a sub-part of a {@link Record} from
     * storage, this is used for faster retrieving a large amount of records
     * over network.
     *
     * @param r The record which we want a sub-part of.
     * @return New {@link Record} with the specified attributes.
     */
    public Record getNewRecord(Record r) {
        String base = null;
        byte[] content = null;
        long ct = 0;
        long mt = 0;
        String id = null;
        StringMap meta = null;
        boolean indexable = r.isIndexable();
        boolean deleted = r.isDeleted();
        boolean contentCompressed = r.isContentCompressed();
//        List<String> parentIds = null;
//        List<String> childrenIds = null;
//        List<Record> parents = null;
//        List<Record> children = null;
        for(ATTRIBUTES attribute: attributes) {
            switch(attribute) {
                case ID:
                    id = r.getId();
                    break;
                case BASE:
                    base = r.getBase();
                    break;
                case DELETED:
                    deleted = r.isDeleted();
                    break;
                case INDEXABLE:
                    indexable = r.isIndexable();
                    break;
//                case HAS_RELATIONS:
                case CONTENT:
                    content = r.getContent(false);
                    break;
                case CREATIONTIME:
                    ct = r.getCreationTime();
                    break;
                case MODIFICATIONTIME:
                    mt = r.getModificationTime();
                    break;
                case META:
                    meta = r.getMeta();
                    break;
            }
        }
        Record record = new Record(id, base, deleted, indexable, content, ct, mt, r.getParentIds(), r.getChildIds(),
                                   meta == null || meta.isEmpty() ? null : meta, contentCompressed);
        record.setParents(r.getParents());
        record.setChildren(r.getChildren());
        return record;
    }

    public ATTRIBUTES[] getAttributes() {
        return attributes;
    }

    public void setAttributes(ATTRIBUTES[] attributes) {
        this.attributes = attributes;
    }

    public synchronized void addAttribute(ATTRIBUTES addition) {
        if (attributes == null) {
            attributes = new ATTRIBUTES[1];
            attributes[0] = addition;
                return;
        }
        for (ATTRIBUTES a: attributes) {
            if (addition == a) {
                return;
            }
        }
        ATTRIBUTES[] newAttributes = new ATTRIBUTES[attributes.length + 1];
        System.arraycopy(attributes, 0, newAttributes, 0, attributes.length);
        newAttributes[newAttributes.length-1] = addition;
        attributes = newAttributes;
    }

    public synchronized void removeAttribute(ATTRIBUTES remover) {
        if (attributes == null) {
            return;
        }
        if (attributes.length == 1) {
            if (attributes[0] == remover) {
                attributes = null;
            }
            return;
        }
        int pos = 0;
        for (ATTRIBUTES existing : attributes) {
            if (existing != remover) {
                attributes[pos++] = existing;
            }
        }
        if (pos != attributes.length) { // Matched
            ATTRIBUTES[] newAttributes = new ATTRIBUTES[attributes.length-1];
            System.arraycopy(attributes, 0, newAttributes, 0, attributes.length-1);
            attributes = newAttributes;
        }
    }

    @Override
    public String toString() {
        return "QueryOptions(deleted=" + deletedFilter + ", indexable=" + indexableFilter + ", childDepth=" + childDepth
               + ", parentHeight=" + parentHeight + ", #meta=" + (meta == null ? 0 : meta.size()) + ", attributes="
                + (attributes == null ? "none" : Strings.join(attributes, ", ")) + ")";
    }
}
