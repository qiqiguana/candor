package org.heal.module.metadata;

import org.heal.util.DateTools;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>This class contains extended information about heal metadata.</p>
 * <p/>
 * <p>All of the parameters contained by this object are stored in the
 * form of a <code>String</code>.  The reasoning behind storing the
 * information in this manner is that it can be meaningful if a value
 * is <code>null</code> (this is the default state of all parameters).
 * An example of where this behavior is used is in an advanced search:
 * if a boolean (represented as a <code>String</code> is
 * <code>null</code> then it isn't used for the search, and that value
 * is not used for comparison in the database.</p>
 * <p/>
 * <p>Boolean values are considered true only if the string is not null
 * and equal (ignoring case) to <code>"true"</code>, <code>"yes"</code>,
 * or <code>"1"</code>.  It also should be noted that the boolean values
 * may return their value in two separate ways: <ol>
 * <li>By calling getXXX, the user gets the actual String value.</li>
 * <li>By calling isXXX, the user will get the boolean value.</li>
 * </ol></p>
 *
 * @author Seth Wright
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>)
 * @see ShortMetadataBean
 * @see CompleteMetadataBean
 */
public class MetadataBean extends ShortMetadataBean implements Serializable {
    // Properties
    private String sourceCollectionId = null;
    private Date contributeDate = null;
    private String annotated = null;  //really a boolean
    private String inappropriate = null; //really a boolean
    private String archived = null; //really a boolean
    private String publicationName = null;
    private String publicationId = null;
    private String submissionAgreement = null;
    /*
     * There cannot be an instance variable named "private"
     * because Java uses the term private as a keyword.  So, we define the
     * instance variable 'hidden' and then still have the isPrivate and
     * setPrivate methods that act on the variable named hidden.
     */
    private String hidden = null; //really a boolean
    private String specimenType = null;
    private String radiographType = null;
    private String orientation = null;
    private String magnification = null;
    private String clinicalHistory = null;
    private String duration = null; //really a date/timestamp value
    private Date approveDate = null;
    private Date catalogDate = null;
    private Date rejectDate = null;
    private Date creationDate = null;
    private Date publicationDate = null;
    private String languageType = null;


    /**
     * @return the sourceCollectionId property value.
     */
    public String getSourceCollectionId() {
        return sourceCollectionId;
    }

    /**
     * Sets the sourceCollectionId property value.
     *
     * @param sourceCollectionId
     */
    public void setSourceCollectionId(String sourceCollectionId) {
        this.sourceCollectionId = sourceCollectionId;
    }


    /**
     * Returns the contributeDate property value.
     */
    public Date getContributeDate() {
        return this.contributeDate;
    }

    /**
     * Sets the contributeDate property value.
     */
    public void setContributeDate(Date newContributeDate) {
        this.contributeDate = newContributeDate;
    }

    /**
     * Returns the annotated property value.
     */
    public String getAnnotated() {
        return this.annotated;
    }

    /**
     * Returns true if the passed string is not null and (ignoring case)
     * is equal to true, 1, or yes.
     */
    protected boolean checkTrue(String boolStr) {
        if(boolStr != null &&
                ("true".equalsIgnoreCase(boolStr) ||
                "1".equalsIgnoreCase(boolStr) ||
                "yes".equalsIgnoreCase(boolStr))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the property is not null and either "1","yes" or "true"
     */
    public boolean isAnnotated() {
        return checkTrue(annotated);
    }

    /**
     * Sets the annotated property value.
     */
    public void setAnnotated(String newAnnotated) {
        this.annotated = newAnnotated;
    }

    /**
     * Sets the annotated property value.
     */
    public void setAnnotated(boolean newAnnotated) {
        this.annotated = String.valueOf(newAnnotated);
    }

    /**
     * Returns the inappropriate property value.
     */
    public String getInappropriate() {
        return this.inappropriate;
    }

    /**
     * Returns true if the property is not null and either "1","yes" or "true"
     */
    public boolean isInappropriate() {
        return checkTrue(inappropriate);
    }

    /**
     * Sets the inappropriate property value.
     */
    public void setInappropriate(String newInappropriate) {
        this.inappropriate = newInappropriate;
    }

    /**
     * Sets the inappropriate property value.
     */
    public void setInappropriate(boolean newInappropriate) {
        this.inappropriate = String.valueOf(newInappropriate);
    }

    /**
     * Returns true if the property is not null and either "1","yes" or "true"
     */
    public boolean isApproved() {
        return (getApproveDate() != null);
    }

    /**
     * Returns true if the property is not null and either "1","yes" or "true"
     */
    public boolean isCataloged() {
        return (getCatalogDate() != null);
    }

    /**
     * Returns the archived property value.
     */
    public String getArchived() {
        return this.archived;
    }

    /**
     * Returns true if the property is not null and either "1","yes" or "true"
     */
    public boolean isArchived() {
        return checkTrue(archived);
    }

    /**
     * Sets the archived property value.
     */
    public void setArchived(String newArchived) {
        this.archived = newArchived;
    }

    /**
     * Sets the archived property value.
     */
    public void setArchived(boolean newArchived) {
        this.archived = String.valueOf(newArchived);
    }

    /**
     * Returns true if the property is not null and either "1","yes" or "true"
     */
    public boolean isRejected() {
        return (getRejectDate() != null);
    }

    /**
     * Returns the hidden property value.
     *
     * @see #getPrivate()
     * @deprecated Use {@link #getPrivate()} instead.
     */
    public String getHidden() {
        return this.hidden;
    }

    /**
     * Returns true if the property is not null and either "1","yes" or "true"
     *
     * @see #isPrivate()
     * @deprecated Use {@link #isPrivate()} instead.
     */
    public boolean isHidden() {
        return checkTrue(hidden);
    }

    /**
     * Sets the hidden property value.
     *
     * @see #setPrivate(String)
     * @deprecated Use {@link #setPrivate(String)} instead.
     */
    public void setHidden(String newHidden) {
        this.hidden = newHidden;
    }

    /**
     * Sets the hidden property value.
     *
     * @see #setPrivate(boolean)
     * @deprecated Use {@link #setPrivate(boolean)} instead.
     */
    public void setHidden(boolean newHidden) {
        this.hidden = String.valueOf(newHidden);
    }

    /**
     * Why do we have the isPrivate/setPrivate methods without the
     * corresponding instance variable?  Well, the heal metadata calls for
     * having a field called "private."  Unfortunately, we cannot use that
     * because Java uses the term private as a keyword.  So, we define the
     * instance variable hidden and then still have the isPrivate and
     * setPrivate methods that act on the variable named hidden.
     *
     * @return Returns the private (aka hidden) property value.
     */
    public String getPrivate() {
        return this.hidden;
    }

    /**
     * Returns true if the property is not null and either "1" or "yes" or "true"
     */
    public boolean isPrivate() {
        return checkTrue(hidden);
    }

    /**
     * Sets the private (aka hidden) property value.
     */
    public void setPrivate(String newPrivate) {
        this.hidden = newPrivate;
    }

    /**
     * Sets the private property value.
     */
    public void setPrivate(boolean newPrivate) {
        this.hidden = String.valueOf(newPrivate);
    }


    /**
     * Returns the specimenType property value.
     */
    public String getSpecimenType() {
        return this.specimenType;
    }

    /**
     * Sets the specimenType property value.
     */
    public void setSpecimenType(String newSpecimenType) {
        this.specimenType = newSpecimenType;
    }

    /**
     * Returns the radiographType property value.
     */
    public String getRadiographType() {
        return this.radiographType;
    }

    /**
     * Sets the radiographType property value.
     */
    public void setRadiographType(String newRadiographType) {
        this.radiographType = newRadiographType;
    }

    /**
     * Returns the orientation property value.
     */
    public String getOrientation() {
        return this.orientation;
    }

    /**
     * Sets the orientation property value.
     */
    public void setOrientation(String newOrientation) {
        this.orientation = newOrientation;
    }

    /**
     * Returns the magnification property value.
     */
    public String getMagnification() {
        return this.magnification;
    }

    /**
     * Sets the magnification property value.
     */
    public void setMagnification(String newMagnification) {
        this.magnification = newMagnification;
    }

    /**
     * Returns the clinicalHistory property value.
     */
    public String getClinicalHistory() {
        return this.clinicalHistory;
    }

    /**
     * Sets the clinicalHistory property value.
     */
    public void setClinicalHistory(String newClinicalHistory) {
        this.clinicalHistory = newClinicalHistory;
    }


    /**
     * Returns the duration property value.
     */
    public String getDuration() {
        return this.duration;
    }

    /**
     * Sets the duration property value.
     */
    public void setDuration(String newDuration) {
        this.duration = newDuration;
    }

    /**
     * Returns the approveDate property value.
     */
    public Date getApproveDate() {
        return this.approveDate;
    }

    /**
     * Sets the approveDate property value.
     */
    public void setApproveDate(Date newApproveDate) {
        this.approveDate = newApproveDate;
    }

    /**
     * Returns the catalogDate property value.
     */
    public Date getCatalogDate() {
        return this.catalogDate;
    }

    /**
     * Sets the catalogDate property value.
     */
    public void setCatalogDate(Date newCatalogDate) {
        this.catalogDate = newCatalogDate;
    }

    /**
     * Returns the rejectDate property value.
     */
    public Date getRejectDate() {
        return this.rejectDate;
    }

    /**
     * Sets the rejectDate property value.
     */
    public void setRejectDate(Date newRejectDate) {
        this.rejectDate = newRejectDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Returns the publicationName property value.
     */
    public String getPublicationName() {
        return this.publicationName;
    }

    /**
     * Sets the publicationName property value.
     */
    public void setPublicationName(String publication) {
        this.publicationName = publication;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Returns the submission agreement property value.
     */
    public String getSubmissionAgreement() {
        return this.submissionAgreement;
    }

    /**
     * Sets the submission agreement property value.
     */
    public void setSubmissionAgreement(String submissionAgreement) {
        this.submissionAgreement = submissionAgreement;
    }
    
    /**
     * Returns the Language Type property value.
     */
    public String getLanguageType() {
        return this.languageType;
    }

    /**
     * Sets the Language Type property value.
     */
    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }


    public String toString() {
        StringBuffer ret = new StringBuffer(super.toString());
        ret.append(System.getProperty("line.separator"));
        ret.append("Extended Metadata: " +
                " sourceCollectionId:" + sourceCollectionId +
                " annotated:" + String.valueOf(isAnnotated()) +
                " inappropriate:" + String.valueOf(isInappropriate()) +
                " archived:" + String.valueOf(isArchived()) +
                " private:" + String.valueOf(isPrivate()) +
                " specimenType:" + specimenType +
                " radiographType:" + radiographType +
                " orientation:" + orientation +
                " magnification:" + magnification +
                " clinicalHistory:" + clinicalHistory +
                " duration:" + duration +
                " publicationId:" + publicationId +
                " publicationName:" +publicationName+
                " publicationDate:" + DateTools.format(publicationDate) +
                " approveDate:" + DateTools.format(approveDate) +
                " catalogDate:" + DateTools.format(catalogDate) +
                " rejectDate:" + DateTools.format(rejectDate) +
                " creationDate:" + DateTools.format(creationDate) +
                " languageType:" + languageType);
        return ret.toString();
    }
}
