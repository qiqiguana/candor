package org.heal.module.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * This class contains a complete set of metadata about an item
 * of heal content.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class CompleteMetadataBean extends MetadataBean implements Serializable {
    // Properties
    private ArrayList diseaseDiagnoses = new ArrayList();
    private ArrayList copyrights = new ArrayList();
    private ArrayList taxonPaths = new ArrayList();
    private ArrayList copyrightHolders = new ArrayList();
    private ArrayList contextURLs = new ArrayList();
    private ArrayList requirements = new ArrayList();
    private ArrayList relations = new ArrayList();
    private ArrayList keywords = new ArrayList();
    private ArrayList formats = new ArrayList();
    private ArrayList targetUserGroups = new ArrayList();

    private List metametadataIdentifiers = new ArrayList();
    private List metametadataContributors = new ArrayList();

    /* The contributors are stored as follows.  The map is an association of
    * contributor roles to an ArrayList of ContributorBeans.  Why?  Well, the
    * IMS specification wants the XML representation of the metadata to have
    * multiple contributor entries, each with a different role, but with
    * possibly many actual contributors under that role (vCards).  But the
    * data is not stored that way in the database, so we store it this way
    * in the CompleteMetadataBean.
    */
    private TreeMap contributors = new TreeMap();

    /**
     * Returns the metadata property value.
     *
     * @deprecated This class no longer contains a {@link MetadataBean} --
     *             it <em>is</em> a {@link MetadataBean}.
     */
    public MetadataBean getMetadata() {
        return this;
    }

    /**
     * Sets the metadata property value.  This method should no longer
     * be used, but it has been made backwards-compatible in order to
     * accomodate older code that uses it.
     *
     * @deprecated This class no longer contains a {@link MetadataBean} --
     *             it <em>is</em> a {@link MetadataBean}.
     */
    public void setMetadata(MetadataBean mb) {
        setAnnotated(mb.getAnnotated());
        setApproveDate(mb.getApproveDate());
        setArchived(mb.getArchived());
        setCatalogDate(mb.getCatalogDate());
        setClinicalHistory(mb.getClinicalHistory());
        setContributeDate(mb.getContributeDate());
        setContributeName(mb.getContributeName());
        setContributeUserId(mb.getContributeUserId());
        setCreationDate(mb.getCreationDate());
        setDescription(mb.getDescription());
        setDuration(mb.getDuration());
        setFileHeight(mb.getFileHeight());
        setFileName(mb.getFileName());
        setFileSize(mb.getFileSize());
        setFileWidth(mb.getFileWidth());
        setGlobalId(mb.getGlobalId());
        setInappropriate(mb.getInappropriate());
        setLocation(mb.getLocation());
        setLearningResourceType(mb.getLearningResourceType());
        setMagnification(mb.getMagnification());
        setMetadataId(mb.getMetadataId());
        setOrientation(mb.getOrientation());
        setPrivate(mb.getPrivate());
        setPublicationName(mb.getPublicationName());
        setPublicationId(mb.getPublicationId());
        setPublicationDate(mb.getPublicationDate());
        setRadiographType(mb.getRadiographType());
        setRejectDate(mb.getRejectDate());
        setSourceCollection(mb.getSourceCollection());
        setSourceCollectionId(mb.getSourceCollectionId());
        setSpecimenType(mb.getSpecimenType());
        setThumbnail(mb.getThumbnail());
        setTitle(mb.getTitle());
    }

    /**
     * Returns a vector of DiseaseDiagnosisBean objects each containing
     * a diseaseDiagnosis for this metadata.
     */
    public ArrayList getDiseaseDiagnoses() {
        return diseaseDiagnoses;
    }

    /**
     * Sets the diseaseDiagnosis property value.
     * DO NOT use NULL as a parameter.
     */
    public void setDiseaseDiagnoses(ArrayList newDiseaseDiagnoses) {
        diseaseDiagnoses = newDiseaseDiagnoses;
    }

    /**
     * Adds a disease diagnosis to the set of diagnoses for
     * this metadata object.
     */
    public void addDiseaseDiagnosis(DiseaseDiagnosisBean newDiseaseDiagnosis) {
        diseaseDiagnoses.add(newDiseaseDiagnosis);
    }

    /**
     * @return An <code>ArrayList</code> of TargetUserGroups, possibly empty but
     *         should not be <code>null</code>
     */
    public ArrayList getTargetUserGroups() {
        return targetUserGroups;
    }

    /**
     * Sets the targetUserGroups to a new <code>ArrayList</code>.
     */
    public void setTargetUserGroups(ArrayList newTargetUserGroups) {
        if(newTargetUserGroups != null) {
            targetUserGroups = newTargetUserGroups;
        }
    }

    /**
     * Adds a target user group to the <code>ArrayList</code> of
     * target user groups for this metadata object.
     */
    public void addTargetUserGroup(TargetUserGroupBean t) {
        targetUserGroups.add(t);
    }

    /**
     * Returns a vector of CopyrightBean objects each containing
     * a CopyrightTextBean for this metadata.
     */
    public ArrayList getCopyrights() {
        return copyrights;
    }

    /**
     * Returns the first copyright text
     */
    public String getCopyright() {
        String retval = null;
        if(copyrights != null && copyrights.size() > 0) {
            CopyrightBean copyright = (CopyrightBean)copyrights.get(0);
            if(copyright != null) {
                CopyrightTextBean copyrightText = copyright.getCopyrightText();
                if(copyrightText != null) {
                    retval = copyrightText.getCopyrightText();
                }
            }
        }
        return retval;
    }

    /**
     * Sets the copyright property value.
     * DO NOT use NULL as a parameter.
     */
    public void setCopyrights(ArrayList newCopyrights) {
        copyrights = newCopyrights;
    }

    /**
     * Adds a copyright to the set of copyrights for
     * this metadata object.
     */
    public void addCopyright(CopyrightBean newCopyright) {
        copyrights.add(newCopyright);
    }

    /**
     * Returns a vector of TaxonPathBean objects each containing
     * a taxonPath for this metadata.
     */
    public ArrayList getTaxonPaths() {
        return taxonPaths;
    }

    /**
     * Sets the taxonPath property value.
     * DO NOT use NULL as a parameter.
     */
    public void setTaxonPaths(ArrayList newTaxonPaths) {
        taxonPaths = newTaxonPaths;
    }

    /**
     * Adds a taxonPath to the set of taxonPaths for
     * this metadata object.
     */
    public void addTaxonPath(TaxonPathBean newTaxonPath) {
        taxonPaths.add(newTaxonPath);
    }

    /**
     * Returns a vector of CopyrightHolderBean objects each containing
     * a copyrightHolder for this metadata.
     */
    public ArrayList getCopyrightHolders() {
        return copyrightHolders;
    }

    /**
     * Sets the copyrightHolder property value.
     * DO NOT use NULL as a parameter.
     */
    public void setCopyrightHolders(ArrayList newCopyrightHolders) {
        copyrightHolders = newCopyrightHolders;
    }

    /**
     * Adds a copyrightHolder to the set of copyrightHolders for
     * this metadata object.
     */
    public void addCopyrightHolder(CopyrightHolderBean newCopyrightHolder) {
        copyrightHolders.add(newCopyrightHolder);
    }

    /**
     * Returns a vector of ContextURLBean objects each containing
     * a contextURL for this metadata.
     */
    public ArrayList getContextURLs() {
        return contextURLs;
    }

    /**
     * Sets the contextURL property value.
     * DO NOT use NULL as a parameter.
     */
    public void setContextURLs(ArrayList newContextURLs) {
        contextURLs = newContextURLs;
    }

    /**
     * Adds a contextURL to the set of contextURLs for
     * this metadata object.
     */
    public void addContextURL(ContextURLBean newContextURL) {
        contextURLs.add(newContextURL);
    }

    /**
     * Returns a vector of RequirementBean objects each containing
     * a requirement for this metadata.
     */
    public ArrayList getRequirements() {
        return requirements;
    }

    /**
     * Sets the requirement property value.
     * DO NOT use NULL as a parameter.
     */
    public void setRequirements(ArrayList newRequirements) {
        requirements = newRequirements;
    }

    /**
     * Adds a requirement to the set of requirements for
     * this metadata object.
     */
    public void addRequirement(RequirementBean newRequirement) {
        requirements.add(newRequirement);
    }

    /**
     * @return A <code>List</code> of {@link org.heal.module.metadata.ContributorBean Contributors} for
     *         this metadata record.
     */
    public List getContributorList() {
        List ret = new ArrayList();
        for(Iterator iter = contributors.values().iterator(); iter.hasNext();) {
            Collection temp = (Collection)iter.next();
            ret.addAll(temp);
        }
        return ret;
    }

    /**
     * Returns a vector of ContributorBean objects each containing
     * a contributor for this metadata.
     */
    public TreeMap getContributors() {
        return contributors;
    }

    /**
     * Sets the contributor property value.
     * DO NOT use NULL as a parameter.
     */
    public void setContributors(TreeMap newContributors) {
        contributors = newContributors;
    }

    /**
     * Adds a contributor to the set of contributors for
     * this metadata object.
     */
    public void addContributor(ContributorBean newContributor) {
        String role = newContributor.getRole();
        ArrayList contribList = (ArrayList)contributors.get(role);
        if(contribList == null) {
            contribList = new ArrayList();
            contributors.put(role, contribList);
        }
        contribList.add(newContributor);
    }

    /**
     * Returns a vector of RelationBean objects each containing
     * a relation for this metadata.
     */
    public ArrayList getRelations() {
        return relations;
    }

    /**
     * Sets the relation property value.
     * DO NOT use NULL as a parameter.
     */
    public void setRelations(ArrayList newRelations) {
        relations = newRelations;
    }

    /**
     * Adds a relation to the set of relations for
     * this metadata object.
     */
    public void addRelation(RelationBean newRelation) {
        relations.add(newRelation);
    }

    /**
     * Returns a vector of KeywordBean objects each containing
     * a keyword for this metadata.
     */
    public ArrayList getKeywords() {
        return keywords;
    }

    /**
     * Sets the keyword property value.
     * DO NOT use NULL as a parameter.
     */
    public void setKeywords(ArrayList newKeywords) {
        keywords = newKeywords;
    }

    /**
     * Adds a keyword to the set of keywords for
     * this metadata object.
     */
    public void addKeyword(KeywordBean newKeyword) {
        keywords.add(newKeyword);
    }

    /**
     * Returns the format property value.
     */
    public ArrayList getFormats() {
        return this.formats;
    }

    /**
     * Overrides {@link ShortMetadataBean#setFormat(String)} in order
     * to ensure that we clear out the formats stored in this object.
     *
     * @see ShortMetadataBean#setFormat(String)
     */
    public void setFormat(String format) {
        super.setFormat(format);
        ArrayList list = new ArrayList();
        FormatBean fb = new FormatBean();
        fb.setFormat(format);
        // Is it right to fb.setMetadataId here? -BS
        fb.setMetadataId(getMetadataId());
        list.add(fb);
        setFormats(list);
    }

    /**
     * Sets the format property value.
     * DO NOT use NULL as a parameter.
     */
    public void setFormats(ArrayList newFormats) {
        this.formats = newFormats;
        if(null != formats && formats.size() > 0) {
            super.setFormat(((FormatBean)formats.get(0)).getFormat());
        }
    }

    /**
     * Adds a format to the set of formats for
     * this metadata object.
     */
    public void addFormat(FormatBean newFormat) {
        if(0 == formats.size()) {
            super.setFormat(newFormat.getFormat());
        }
        formats.add(newFormat);
    }

    public List getMetametadataIdentifiers() {
        return metametadataIdentifiers;
    }

    public void setMetametadataIdentifiers(List metametadataIdentifiers) {
        this.metametadataIdentifiers = metametadataIdentifiers;
    }

    public List getMetametadataContributors() {
        return metametadataContributors;
    }

    public void setMetametadataContributors(List metametadataContributors) {
        this.metametadataContributors = metametadataContributors;
    }

    public String toString() {
        StringBuffer ret = new StringBuffer(super.toString());
        ret.append(System.getProperty("line.separator"));
        ret.append("Complete Metadata: ");
        String str = collectionToString(diseaseDiagnoses);
        if(str != null) {
            ret.append("Disease Diagnoses: \n").append(str);
        }
        str = collectionToString(copyrights);
        if(str != null) {
            ret.append("Copyrights: \n").append(str);
        }
        str = collectionToString(taxonPaths);
        if(str != null) {
            ret.append("TaxonPaths: \n").append(str);
        }
        str = collectionToString(copyrightHolders);
        if(str != null) {
            ret.append("CopyrightHolders: \n").append(str);
        }
        str = collectionToString(contextURLs);
        if(str != null) {
            ret.append("ContextURLs: \n").append(str);
        }
        str = collectionToString(requirements);
        if(str != null) {
            ret.append("requirements: \n").append(str);
        }
        str = collectionToString(metametadataIdentifiers);
        if(null != str) {
            ret.append("Metametadata Identifiers: \n").append(str);
        }
        str = collectionToString(metametadataContributors);
        if(null != str) {
            ret.append("Metametadata Contributors: \n").append(str);
        }


        if(contributors != null) {
            for(Object o : contributors.values()) {
                ArrayList list = (ArrayList)o;
                str = collectionToString(list);
                ret.append("contributor list: \n").append(str);
            }
        }
        str = collectionToString(relations);
        if(str != null) {
            ret.append("relations: \n").append(str);
        }
        str = collectionToString(keywords);
        if(str != null) {
            ret.append("keywords: \n").append(str);
        }
        str = collectionToString(formats);
        if(str != null) {
            ret.append("formats: \n").append(str);
        }
        ret.append("TargetUserGroups: \n").append(collectionToString(targetUserGroups));
        return ret.toString();
    }

    private String collectionToString(Collection col) {
        if(col == null) {
            return null;
        }
        StringBuffer sbuff = new StringBuffer();
        for(Object obj : col) {
            if(null == obj) {
                sbuff.append("NULL\n");
            } else {
                sbuff.append(obj.toString()).append("\n");
            }
        }
        return sbuff.toString();
    }
}






