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
 *
 */
public class MetadataRecordModifier implements MetadataModifier {
	private static final Map nextActionMap;
	private static final Action NEXT_ACTION_DEFAULT = new EditMetadataAction();

	static {
		Map temp = new HashMap();
		temp.put("editContextURL", new EditContextURLAction());
		temp.put("editContributor", new EditContributorAction());
		temp.put("editCopyrightHolder", new EditCopyrightHolderAction());
		temp.put("editControlledVocab", new EditControlledVocabularyAction());
		temp.put("editRequirement", new EditRequirementAction());
		temp.put("editRelation", new RelationRecordModifier());
        temp.put("editMetametadataIdentifier", new MetametadataIdentifierRecordModifier());
        temp.put("editMetametadataContributor", new MetametadataContributorRecordModifier());
        temp.put("saveMetadata", new SaveMetadataAction());

		nextActionMap = Collections.unmodifiableMap(temp);
	}

	public MetadataRecordModifier() {
	}

	public void updateMetadata(CompleteMetadataBean cmb, ServletRequest request) {
		// Fills the complete metadata bean with the form information
		cmb.setAnnotated(request.getParameter("annotated"));
		cmb.setApproveDate(DateTools.parse(request.getParameter("approveDate")));
		cmb.setArchived(request.getParameter("archived"));
		cmb.setCatalogDate(DateTools.parse(request.getParameter("catalogDate")));
		cmb.setClinicalHistory(request.getParameter("clinicalHistory"));

		// Removes any deleted Context URLs
		List oldContextURLs = cmb.getContextURLs();
		ArrayList updatedContextURLs = new ArrayList();
		String[] contextURLs = request.getParameterValues("contextURLs");
		if(null != contextURLs) {
			for(int i = 0; i < contextURLs.length; ++i) {
				final String contextURLId = contextURLs[i];
				for(Iterator iter = oldContextURLs.iterator(); iter.hasNext();) {
					ContextURLBean cub = (ContextURLBean)iter.next();
					if(cub.getContextURLId().equals(contextURLId)) {
						updatedContextURLs.add(cub);
						break;
					}
				}
			}
		}
		cmb.setContextURLs(updatedContextURLs);

		cmb.setContributeDate(DateTools.parse(request.getParameter("contributeDate")));

		// Removes any deleted Contributors
		List oldContributors = cmb.getContributorList();
		cmb.setContributors(new TreeMap());
		String[] contributors = request.getParameterValues("contributors");
		if(null != contributors) {
			for(int i = 0; i < contributors.length; ++i) {
				final String contributorId = contributors[i];
				for(Iterator iter = oldContributors.iterator(); iter.hasNext();) {
					ContributorBean cb = (ContributorBean)iter.next();
					if(cb.getContributorId().equals(contributorId)) {
						cmb.addContributor(cb);
						break;
					}
				}
			}
		}

		// Removes any deleted Copyright Holders
		List oldCopyrightHolders = cmb.getCopyrightHolders();
		cmb.setCopyrightHolders(new ArrayList());
		String[] copyrightHolders = request.getParameterValues("copyrightHolders");
		if(null != copyrightHolders) {
			for(int i = 0; i < copyrightHolders.length; ++i) {
				final String copyrightHolderId = copyrightHolders[i];
				for(Iterator iter = oldCopyrightHolders.iterator(); iter.hasNext();) {
					CopyrightHolderBean chb = (CopyrightHolderBean)iter.next();
					if(chb.getCopyrightHolderId().equals(copyrightHolderId)) {
						cmb.addCopyrightHolder(chb);
						break;
					}
				}
			}
		}

		// Removes any deleted Controlled Vocabulary items
		List oldTaxonPaths = cmb.getTaxonPaths();
		cmb.setTaxonPaths(new ArrayList());
		String[] taxons = request.getParameterValues("controlledVocab");
		if(null != taxons) {
			for(Iterator iterOne = oldTaxonPaths.iterator(); iterOne.hasNext();) {
				final TaxonPathBean taxonPath = (TaxonPathBean)iterOne.next();
				SortedSet taxonSet = new TreeSet();
				for(Iterator iterTwo = taxonPath.getTaxons().iterator(); iterTwo.hasNext();) {
                    final TaxonBean taxon = (TaxonBean)iterTwo.next();
					for(int i = 0; i < taxons.length; ++i) {
						final String taxonId = taxons[i];
						if(taxonId.equals(taxon.getTaxonId())) {
							taxonSet.add(taxon);
							break;
						}
					}
				}
				if(0 < taxonSet.size()) {
					taxonPath.setTaxons(taxonSet);
					cmb.addTaxonPath(taxonPath);
				}
			}
		}

		String[] copyrightValues = request.getParameterValues("copyrights");
		ArrayList copyrights = new ArrayList();
		if(null != copyrightValues) {
			for(int i = 0; i < copyrightValues.length; ++i) {
				CopyrightTextBean ctb = new CopyrightTextBean();
				ctb.setCopyrightText(copyrightValues[i]);

				CopyrightBean cb = new CopyrightBean();
				cb.setCopyrightText(ctb);

				copyrights.add(cb);
			}
		}
		cmb.setCopyrights(copyrights);

		cmb.setCreationDate(DateTools.parse(request.getParameter("creationDate")));
		cmb.setDescription(request.getParameter("description"));

		String[] diseaseDxValues = request.getParameterValues("diseaseDiagnoses");
		ArrayList diseaseDiagnoses = new ArrayList();
		if(null != diseaseDxValues) {
			for(int i = 0; i < diseaseDxValues.length; ++i) {
				DiseaseDiagnosisBean ddb = new DiseaseDiagnosisBean();
				ddb.setDiseaseDiagnosis(diseaseDxValues[i]);
				diseaseDiagnoses.add(ddb);
			}
		}
		cmb.setDiseaseDiagnoses(diseaseDiagnoses);

		cmb.setDuration(request.getParameter("duration"));
		cmb.setFileHeight(request.getParameter("fileHeight"));
		cmb.setFileWidth(request.getParameter("fileWidth"));
		cmb.setFileName(request.getParameter("filename"));
		cmb.setFileSize(request.getParameter("fileSize"));

		String[] formatValues = request.getParameterValues("formats");
		ArrayList formats = new ArrayList();
		if(null != formatValues) {
			for(int i = 0; i < formatValues.length; ++i) {
				FormatBean fb = new FormatBean();
				fb.setFormat(formatValues[i]);
				formats.add(fb);
			}
		}
		cmb.setFormats(formats);

		cmb.setGlobalId(request.getParameter("globalId"));

		String[] keywordValues = request.getParameterValues("keywords");
		ArrayList keywords = new ArrayList();
		if(null != keywordValues) {
			for(int i = 0; i < keywordValues.length; ++i) {
				KeywordBean kb = new KeywordBean();
				kb.setKeyword(keywordValues[i]);
				keywords.add(kb);
			}
		}
		cmb.setKeywords(keywords);

		cmb.setLearningResourceType(request.getParameter("learningResourceType"));
		cmb.setLocation(request.getParameter("location"));
		cmb.setMagnification(request.getParameter("magnification"));

        // Removes any deleted Metametadata Identifiers
        List oldMmIdentifiers = cmb.getMetametadataIdentifiers();
        List<MetametadataIdentifierBean> updatedMmIdentifiers = new ArrayList<MetametadataIdentifierBean>();
        String[] mmIdentifiers = request.getParameterValues("metametadataIdentifiers");
        if(null != mmIdentifiers) {
            for(int i = 0; i < mmIdentifiers.length; ++i) {
                final String metametadataIdentifierId = mmIdentifiers[i];
                for(Object o : oldMmIdentifiers) {
                    MetametadataIdentifierBean mib = (MetametadataIdentifierBean)o;
                    if(metametadataIdentifierId.equals(mib.getMetametadataIdentifierId())) {
                        updatedMmIdentifiers.add(mib);
                        break;
                    }
                }
            }
        }
        cmb.setMetametadataIdentifiers(updatedMmIdentifiers);

        // Removes any deleted Metametadata Contributors
        List oldMmContributors = cmb.getMetametadataContributors();
        List<MetametadataContributorBean> updatedMmContributors = new ArrayList<MetametadataContributorBean>();
        String[] mmContributors = request.getParameterValues("metametadataContributors");
        if(null != mmContributors) {
            for(int i = 0; i < mmContributors.length; ++i) {
                final String metametadataContributorId = mmContributors[i];
                for(Object o : oldMmContributors) {
                    MetametadataContributorBean mcb = (MetametadataContributorBean)o;
                    if(metametadataContributorId.equals(mcb.getMetametadataContributorId())) {
                        updatedMmContributors.add(mcb);
                        break;
                    }
                }
            }
        }
        cmb.setMetametadataContributors(updatedMmContributors);

        cmb.setOrientation(request.getParameter("orientation"));
		cmb.setPrivate(request.getParameter("private"));
		cmb.setPublicationName(request.getParameter("publicationName"));
		cmb.setRadiographType(request.getParameter("radiographType"));

		// TODO Removes any deleted Relations
		List oldRelations = cmb.getRelations();
		ArrayList updatedRelations = new ArrayList();
		String[] relations = request.getParameterValues("relations");
		if(null != relations) {
			for(int i = 0; i < relations.length; ++i) {
				final String relationId = relations[i];
				for(Iterator iter = oldRelations.iterator(); iter.hasNext();) {
					RelationBean rb = (RelationBean)iter.next();
					if(relationId.equals(rb.getRelationId())) {
						updatedRelations.add(rb);
						break;
					}
				}
			}
		}
		cmb.setRelations(updatedRelations);

		// Removes any deleted Requirements
		List oldRequirements = cmb.getRequirements();
		ArrayList updatedRequirements = new ArrayList();
		String[] requirements = request.getParameterValues("requirements");
		if(null != requirements) {
			for(int i = 0; i < requirements.length; ++i) {
				final String requirementId = requirements[i];
				for(Iterator iter = oldRequirements.iterator(); iter.hasNext();) {
					RequirementBean rb = (RequirementBean)iter.next();
					if(rb.getRequirementId().equals(requirementId)) {
						updatedRequirements.add(rb);
						break;
					}
				}
			}
		}
		cmb.setRequirements(updatedRequirements);

		cmb.setRejectDate(DateTools.parse(request.getParameter("rejectedDate")));
		cmb.setSourceCollection(request.getParameter("sourceCollection"));
		cmb.setSourceCollectionId(request.getParameter("sourceCollectionId"));
		cmb.setSpecimenType(request.getParameter("specimenType"));
		cmb.setTitle(request.getParameter("title"));

		String[] targetUserGroupValues = request.getParameterValues("targetUserGroups");
		ArrayList targetUserGroups = new ArrayList();
		if(null != targetUserGroupValues) {
			for(int i = 0; i < targetUserGroupValues.length; ++i) {
				TargetUserGroupBean tug = new TargetUserGroupBean();
				tug.setTargetUserGroup(targetUserGroupValues[i]);
				targetUserGroups.add(tug);
			}
		}
		cmb.setTargetUserGroups(targetUserGroups);

		if(null != request.getParameter("thumbnailLocation")) {
			ThumbnailBean tb = new ThumbnailBean();
			tb.setLocation(request.getParameter("thumbnailLocation"));
			tb.setFileHeight(request.getParameter("thumbnailFileHeight"));
			tb.setFileWidth(request.getParameter("thumbnailFileWidth"));
			cmb.setThumbnail(tb);
		}
	}

	/**
	 *
	 * @param request Used to get the parameter <code>"nextAction"</code>, which
	 * 		determines what the next {@link Action} to take will be.  If no
	 * 		<code>"nextAction"</code> exists, a valid {@link Action} will still
	 * 		be returned.
	 * @return The next {@link Action} to take after saving the metadata from
	 * 		the form.
	 */
	public Action getNextAction(ServletRequest request) {
		Action ret;

		String nextActionKey = request.getParameter("nextAction");
		if(nextActionMap.containsKey(nextActionKey)) {
			ret = (Action)nextActionMap.get(nextActionKey);
		} else {
			ret = NEXT_ACTION_DEFAULT;
		}

		return ret;
	}
}
