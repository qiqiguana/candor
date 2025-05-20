package org.heal.servlet.cataloger;

import org.heal.servlet.Action;
import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.TaxonPathBean;
import org.heal.module.metadata.TaxonBean;

import javax.servlet.ServletRequest;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Saves changes to a {@link org.heal.module.metadata.TaxonBean}.
 */
public class ControlledVocabularyRecordModifier implements MetadataModifier {
	private static Action NEXT_ACTION = new EditMetadataAction();

	public void updateMetadata(CompleteMetadataBean cmb, ServletRequest request) {
		final String taxonId = request.getParameter("taxonId");
		final String source = request.getParameter("source");
		final String id = request.getParameter("id");
		final String entry = request.getParameter("entry");

		TaxonBean taxon = null;
		TaxonPathBean taxonPath = null;
		int lowestTaxonPathId = 0;
		int lowestTaxonId = 0;
		for(Iterator iterOne = cmb.getTaxonPaths().iterator(); iterOne.hasNext();) {
			final TaxonPathBean tempTaxonPath = (TaxonPathBean)iterOne.next();
			final int tempTaxonPathId = Integer.parseInt(tempTaxonPath.getTaxonPathId());
			for(Iterator iterTwo = tempTaxonPath.getTaxons().iterator(); iterTwo.hasNext();) {
				final TaxonBean tempTaxon = (TaxonBean)iterTwo.next();
				final int tempTaxonId = Integer.parseInt(tempTaxon.getTaxonId());
				if(taxonId.equals(tempTaxon.getTaxonId())) {
					taxonPath = tempTaxonPath;
					taxon = tempTaxon;
				}
				if(lowestTaxonId > tempTaxonId) {
					lowestTaxonId = tempTaxonId;
				}
			}
			if(lowestTaxonPathId > tempTaxonPathId) {
				lowestTaxonPathId = tempTaxonPathId;
			}
		}

		if(null == taxon) {
			taxon = new TaxonBean();
			taxon.setTaxonId(String.valueOf(lowestTaxonId-1));
		} else if(!source.equals(taxonPath.getSource())) {
			SortedSet newTaxonSet = new TreeSet();
			for(Iterator iter = taxonPath.getTaxons().iterator(); iter.hasNext();) {
				TaxonBean tempTaxon = (TaxonBean)iter.next();
				if(!taxonId.equals(tempTaxon.getTaxonId())) {
					newTaxonSet.add(tempTaxon);
				}
			}
			if(0 == newTaxonSet.size()) {
				cmb.getTaxonPaths().remove(taxonPath);
			} else {
				taxonPath.setTaxons(newTaxonSet);
			}
			taxonPath = null;
		}

		if(null == taxonPath) {
			for(Iterator iter = cmb.getTaxonPaths().iterator(); iter.hasNext();) {
				final TaxonPathBean tempTaxonPath = (TaxonPathBean)iter.next();
				if(source.equals(tempTaxonPath.getSource())) {
					taxonPath = tempTaxonPath;
					break;
				}
			}
			if(null == taxonPath) {
				taxonPath = new TaxonPathBean();
				taxonPath.setTaxonPathId(String.valueOf(lowestTaxonPathId - 1));
				taxonPath.setSource(source);
				cmb.addTaxonPath(taxonPath);
				// We need to make this a new taxon, because in the situation
				// where the TaxonPath of an existing Taxon is changed
				// and the old TaxonPath becomes empty, the existing Taxon
				// will be deleted because of a relationship in the database,
				// which results in an SQL UPDATE for a non-existing TaxonId
				// and hence... data loss.
				taxon.setTaxonId(String.valueOf(lowestTaxonId - 1));
			}
			taxonPath.addTaxon(taxon);
		}
		taxon.setId(id);
		taxon.setEntry(entry);
		taxon.setTaxonPathId(taxonPath.getTaxonPathId());
	}

	public Action getNextAction(ServletRequest request) {
		return NEXT_ACTION;
	}
}
