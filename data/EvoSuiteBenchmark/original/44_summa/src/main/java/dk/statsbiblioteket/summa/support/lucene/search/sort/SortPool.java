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
package dk.statsbiblioteket.summa.support.lucene.search.sort;

import dk.statsbiblioteket.summa.common.index.IndexField;
import dk.statsbiblioteket.summa.common.lucene.LuceneIndexDescriptor;
import dk.statsbiblioteket.summa.common.lucene.LuceneIndexField;
import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.exposed.ExposedCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Uses the search description to generate a pool of sorters. The pool is lazy,
 * so sorters are only created when requested. If the IndexDescription is
 * changed, a new SortPool should be constructed to reflect the change.
 */
public class SortPool {
    private static final Logger log = Logger.getLogger(SortPool.class);

    /**
     * Maintains cached structures and makes is cheap to construct new sorters.
     */
    private Map<String, SortFactory> sortFactories = new HashMap<String, SortFactory>(100);

    /**
     * Comparators does the heavy lifting when sorting. They are shared between
     * sortFactories.
     */
    private Map<String, ReusableSortComparator> comparators = new HashMap<String, ReusableSortComparator>(10);

    private boolean naturalOrder = false;
    private SortFactory.COMPARATOR comparatorImplementation;
    private int bufferSize;


    /**
     * The constructor steps through all fields in the index descriptor and
     * stores the sort locale for each.
     *
     * @param comparator the comparator-implementation to use.
     * @param buffer     currently not used by any comparators.
     * @param descriptor a description of the fields in the index.
     */
    public SortPool(SortFactory.COMPARATOR comparator, int buffer, LuceneIndexDescriptor descriptor) {
        log.debug("Creating lazy sort pool with comparator implementation " + comparator);
        long startTime = System.currentTimeMillis();
        comparatorImplementation = comparator;
        bufferSize = buffer;
        for (Map.Entry<String, LuceneIndexField> entry : descriptor.getFields().entrySet()) {
            updateField(entry.getValue());
        }
        log.debug("Lazy sort pool finished creating in " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private void updateField(IndexField field) {
/*        if (field.getSortLocale() == null) {
            return;
        }
        */
        if (!sortFactories.containsKey(field.getName())) {
            String sortLocale = field.getSortLocale();
            if (ExposedCache.getInstance().isConcatField(field.getName())) {
                log.info("Field '" + field.getName() + "' is marked as a concat field. Search-time locale sorting is "
                        + "disabled in favor of direct collator key sorting");
                sortLocale = null;
            }
            log.debug("Adding sort locale '" + field.getSortLocale() + "' to Field '" + field.getName() + "'");
            sortFactories.put(field.getName(), new SortFactory(
                    comparatorImplementation, bufferSize, field.getName(), sortLocale, comparators));
        } else {
            SortFactory oldFactory = sortFactories.get(field.getName());
            if (!oldFactory.getSortLanguage().equals(field.getSortLocale())) {
                log.warn("New sort locale '" + field.getSortLocale() + "' overrides old sort locale '"
                         + oldFactory.getSortLanguage() + "' for Field '" + field.getName() + "'");
                sortFactories.remove(field.getName());
                updateField(field);
            }
        }
    }

    /**
     * Creates a sort for the field or returns an existing one. It is the
     * responsibility of the caller to ensure that a given sort is only used
     * for by one thread at a time.
     *
     * @param field   the field to get a - potentially localized - sort for.
     * @param reverse if true, the sort-order is reversed.
     * @return a Lucene Sort, ready for use.
     */
    public Sort getSort(String field, boolean reverse) {
        log.debug("getSort called for field '" + field + "', reverse " + reverse);
        // TODO: Remove the following 3 lines to enable caching
//        if (field != null) {
//            return new Sort(new SortField(field, new Locale("da"), reverse));
//        }
        if (naturalOrder) {
            log.warn("Returning sort in natural order. This effectively ignores all localization on sort");
            return new Sort(new SortField(field, SortField.Type.STRING, reverse));
        }
        if (!sortFactories.containsKey(field)) {
            log.debug("No explicit sort specified for field '" + field + "'. Returning standard sort");
            return new Sort(new SortField(field, SortField.Type.STRING, reverse));
        }
        try {
            return sortFactories.get(field).getSort(reverse);
        } catch (Exception e) {
            log.warn("Could not get sorter for '" + field + "', Defaulting to standard sort, without caching", e);
            return new Sort(new SortField(field, SortField.Type.STRING, reverse));
        }
    }

    /**
     * Toggles whether to use the localized or the natural order. Setting this
     * to true effectively turns off all extra functionality for the SortPool.
     * Used mainly for testing and debugging.
     *
     * @param useNaturalOrder true if the SortPool should use only the natural
     *                        order for sorting.
     */
    public void setNaturalOrder(boolean useNaturalOrder) {
        log.debug("useNaturalOrder: " + useNaturalOrder);
        naturalOrder = useNaturalOrder;
    }

    /**
     * Must be called before any sort comparators can be returned and must be
     * called whenever the underlying index changes.
     *
     * @param reader the new reader to use for sorting.
     */
    public void indexChanged(IndexReader reader) {
        for (Map.Entry<String, SortFactory> factory : sortFactories.entrySet()) {
            factory.getValue().indexChanged(reader);
        }
    }
}
