package org.heal.servlet.cataloger;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.servlet.Action;
import org.heal.util.AuthenticationTools;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>An {@link Action} that saves the metadata from a form into
 * the session's {@link CompleteMetadataBean} by mapping {@link MetadataModifier}s
 * to forms.  The {@link MetadataModifier} which matches the form determines
 * the next {@link Action} to execute.</p>
 *
 * <p>The mapping is currently defined as:</p>
 *
 * <ul><li>'metadata' : {@link MetadataRecordModifier}
 * <li>'contextURL' : {@link ContextURLRecordModifier}
 * <li>'contributor' : {@link ContributorRecordModifier}
 * <li>'copyrightHolder' : {@link CopyrightHolderRecordModifier}
 * <li>'controlledVocabulary' : {@link ControlledVocabularyRecordModifier}
 * <li>'requirement' : {@link RequirementRecordModifier}
 * </ul>
 */
public class ModifyMetadataAction implements Action {

    private final Map modifierMap;

    public ModifyMetadataAction() {
    }

    /**
     * <p>This perform method uses the <code>"type"</code> parameter to save the
     * metadata modifications from the <code>request</code> in the
     * {@link CompleteMetadataBean} that is in the session.  If the type or
     * <code>CompleteMetadataBean</code> doesn't exist, the <code>response</code> is
     * redirected to an error page.</o>
     *
     * <p>When a valid type and a <code>CompleteMetadataBean</code> is available
     * in the session, this method uses the appropriate {@link MetadataModifier}
     * to save the motifications to the metadata and to perform the subsequent
     * {@link Action}.</p>
     */
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    /**
     * @return <code>true</code>
     */
    public boolean actionRequiresLogin();
}
