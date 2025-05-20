package org.heal.servlet.cataloger;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.ContextURLBean;
import org.heal.module.metadata.ContributorBean;
import org.heal.module.metadata.CopyrightBean;
import org.heal.module.metadata.CopyrightHolderBean;
import org.heal.module.metadata.CopyrightTextBean;
import org.heal.module.metadata.DiseaseDiagnosisBean;
import org.heal.module.metadata.FormatBean;
import org.heal.module.metadata.KeywordBean;
import org.heal.module.metadata.RelationBean;
import org.heal.module.metadata.RequirementBean;
import org.heal.module.metadata.TargetUserGroupBean;
import org.heal.module.metadata.TaxonBean;
import org.heal.module.metadata.TaxonPathBean;
import org.heal.module.metadata.ThumbnailBean;
import org.heal.module.metadata.MetametadataIdentifierBean;
import org.heal.module.metadata.MetametadataContributorBean;
import org.heal.servlet.Action;
import org.heal.util.DateTools;
import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * <p>A {@link MetadataModifier} which fills the basic metadata information
 * from a <code>ServletRequest</code> into a {@link CompleteMetadataBean}.</p>
 */
public class MetadataRecordModifier implements MetadataModifier {

    private static final Map nextActionMap;

    private static final Action NEXT_ACTION_DEFAULT = new EditMetadataAction();

    static {
    }

    public MetadataRecordModifier() {
    }

    public void updateMetadata(CompleteMetadataBean cmb, ServletRequest request);

    /**
     * @param request Used to get the parameter <code>"nextAction"</code>, which
     * 		determines what the next {@link Action} to take will be.  If no
     * 		<code>"nextAction"</code> exists, a valid {@link Action} will still
     * 		be returned.
     * @return The next {@link Action} to take after saving the metadata from
     * 		the form.
     */
    public Action getNextAction(ServletRequest request);
}
