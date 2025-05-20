package org.heal.servlet.cataloger;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.servlet.Action;

import javax.servlet.ServletRequest;

/**
 * An interface that defines a class which edits the contents of a
 * {@link CompleteMetadataBean} based on information stored in a
 * <code>ServletRequest</code>.
 */
public interface MetadataModifier {
	public void updateMetadata(CompleteMetadataBean cmb, ServletRequest request);
	public Action getNextAction(ServletRequest request);
}
