package org.heal.servlet.userreview;

import java.io.Serializable;
import java.util.Date;

// this bean will hold all the data for each user review.
public class UserReviewBean implements Serializable {

    private int reviewId = 0;
    private String metadataId = null;
    private String userId = null;
    private int userRating = 0;
    private String comments = null;  
    private Date reviewDate = null;
    private Date approvalDate = null;
    private Boolean approved = false;
    private Boolean anonymous = false;
    private String user = null;  
    private String email = null;      

    
    // gets and sets user review ID.
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId (int newReviewId) {
        this.reviewId = newReviewId;
    }

    // gets and sets the metadata ID of the resource reviewed.
    public String getMetaDataId() {
        return metadataId;
    }

    public void setMetaDataId (String newMetaDataId) {
        this.metadataId = newMetaDataId;
    }

    // gets and sets HEAL user ID of the reviewer.
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String newUserId) {
        this.userId = newUserId;
    }

    // gets and sets the star rating (1-5) given to a resource by a reviewer.
    public int getUserRating() {
        return this.userRating;
    }

    public void setUserRating(int newUserRating) {
        this.userRating = newUserRating;
    }

    // gets and sets the comments on a resource made by the reviewer.
    public String getComments() {
        return this.comments;
    }

    public void setComments(String newComments) {
        this.comments = newComments;
    }

    // gets and sets the date the resource was reviewed.
    public Date getReviewDate() {
        return this.reviewDate;
    }

    public void setReviewDate(Date newReviewDate) {
        this.reviewDate = newReviewDate;
    }
    
    // gets and sets the date the review was approved by HEAL personnel.
    public Date getApprovalDate() {
        return this.approvalDate;
    }

    public void setApprovalDate(Date newApprovalDate) {
        this.approvalDate = newApprovalDate;
    }

    // gets and sets whether a review has been approved.
    public Boolean getApproved() {
        return this.approved;
    }

    public void setApproved(Boolean newApproved) {
        this.approved = newApproved;
    }

    // used to show whether a review was submitted anonymously.
    public Boolean getAnonymous() {
        return this.anonymous;
    }

    public void setAnonymous(Boolean newAnon) {
        this.anonymous = newAnon;
    }

    // gets and sets the reviewer's full name if not anonymous. 
    public String getUser() {
        return this.user;
    }

    public void setUser(String newUser) {
        this.user = newUser;
    }

    // gets and sets the reviewer's email address. 
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }
}
