package org.heal.servlet.userreview;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.sql.DataSource;
import com.ora.jsp.sql.NoSuchColumnException;
import com.ora.jsp.sql.Row;
import com.ora.jsp.sql.UnsupportedConversionException;
import org.heal.servlet.*;
import org.heal.module.metadata.ShortMetadataBean;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import com.ora.jsp.sql.SQLCommandBean;
import com.ora.jsp.sql.UnsupportedTypeException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.http.HttpServlet;
import org.heal.module.metadata.ThumbnailBean;
import org.heal.util.FileLocator;

// this is where all the database access work is done.
public class UserReviewDAO extends HttpServlet implements Serializable {
    public UserReviewDAO() {}

    private DataSource dataSource;

    // insert new user review.
    private static final String USER_REVIEW_INSERT_SQL = "INSERT INTO UserReviews (MetaDataID, UserID, UserRating, Comments, ReviewDate, Approved, Anonymous) VALUES(?, ?, ?, ?, ?, ?, ?)";
    // approves user reivew.
    private static final String USER_REVIEW_APPROVAL_SQL = "UPDATE UserReviews SET ApprovalDate = ?, Approved = ? where ReviewID = ?";
    // get user review.
    private static final String USER_REVIEW_SELECT_SQL = "SELECT ReviewID, MetaDataID, UserRating, Comments, ReviewDate, ApprovalDate, Approved, Anonymous, UserID FROM UserReviews WHERE MetaDataID = '";
    // calculates average star rating for a reviewed resource.
    private static final String USER_RATING_AVERAGE_SQL = "SELECT AVG(UserRating) as userReviewAVG FROM UserReviews WHERE Approved=1 and MetaDataID = '";	
    // clears all approved tags.
    private static final String USER_CLEAR_APPROVAL_SQL = "UPDATE UserReviews SET Approved = ? where MetaDataID = ?";
    // gets all the reviews pending approval.
    private static final String ALL_USER_REVIEW_SELECT_SQL = "SELECT UserReviews.metadataid AS usermetadataid, UserReviews.approved, Metadata.MetadataID AS metametadataid, Metadata.LearningResourceType, Metadata.Title, Metadata.Location AS location, Thumbnails.MetadataID AS thumbmetadataid, Thumbnails.Location AS thumblocation FROM Metadata INNER JOIN (SELECT DISTINCT (metadataid), approved  FROM userreviews) UserReviews ON UserReviews.metadataid = Metadata.MetadataID LEFT OUTER JOIN Thumbnails ON Metadata.MetadataID = Thumbnails.MetadataID WHERE (UserReviews.approved = 0)";
    // gets the top 10 most highly rated resources.
    private static final String GET_TOP_10_POPULAR_SELECT_SQL = "SELECT TOP 10 AVG(UserReviews.userrating) AS avgur, UserReviews.metadataid AS usermetadataid, Metadata.LearningResourceType, Metadata.Title, Metadata.Location AS location, Thumbnails.Location AS thumblocation FROM Metadata INNER JOIN (SELECT DISTINCT (metadataid), approved, userrating FROM userreviews) UserReviews ON UserReviews.metadataid = Metadata.MetadataID LEFT OUTER JOIN Thumbnails ON Metadata.MetadataID = Thumbnails.MetadataID WHERE     (UserReviews.approved = 1) GROUP BY UserReviews.metadataid, UserReviews.approved, UserReviews.metadataid, Metadata.LearningResourceType, Metadata.Title, Metadata.Location, Thumbnails.Location ORDER BY avgur DESC";
    // get HEAL user info given the user ID.
    private static final String GET_USER_SELECT_SQL = "SELECT FirstName, LastName, Email FROM Users WHERE UserID = '";
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // gets the average star rating of a resource. 
    public int getUserRatingAVG (String metadataId) throws SQLException {
        Connection conn = dataSource.getConnection();
            Vector rs = new Vector();
            Row row = null;
        String sql = USER_RATING_AVERAGE_SQL + metadataId + "'";
        int AVG=0;

        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql);
        
        try{
            rs = sqlCommandBean.executeQuery();
            Iterator rowIterator = rs.iterator();
            while (rowIterator.hasNext()){
                row = (Row) rowIterator.next();
                AVG = row.getInt("userReviewAVG");
            }
        }
        catch (UnsupportedConversionException ex) {
            ex.printStackTrace();
        }                
        catch (NoSuchColumnException ex) {
            ex.printStackTrace();
        }        
        catch (UnsupportedTypeException ute){
            System.err.println("UnsupportedTypeException: " + ute.getMessage());
        }
        finally {
            try {
                conn.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            } 
        }

        return AVG;
    }
    
    // get all the user review data for a particular resource.
    public ArrayList getUserReview (String metadataId, String status, String orderby) throws SQLException {
        Connection conn = dataSource.getConnection();
        String sql = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        String getUsersql = null;

        // this is for sorting the user reviews by different columns. not used.
        if (status.equals("approved")){
                sql = USER_REVIEW_SELECT_SQL + metadataId + "' and Approved=1 order by " + orderby;
        }
        else if (status.equals("pending")){
                sql = USER_REVIEW_SELECT_SQL + metadataId + "' and Approved=0 order by " + orderby;
        }
        else{
                sql = USER_REVIEW_SELECT_SQL + metadataId + "' order by " + orderby;
        }	

        // array for holding user reivew beans.
        ArrayList userReviewArray = new ArrayList();                
        
        Statement selectReview = conn.createStatement();
        Statement selectUser = conn.createStatement();
        rs = selectReview.executeQuery(sql);
        
        while (rs.next()){
            UserReviewBean userReviewBean = new UserReviewBean();

            // put review data retrieved from the database into the bean.
            userReviewBean.setReviewId(Integer.parseInt(rs.getString("reviewId")));
            userReviewBean.setMetaDataId(metadataId);
            userReviewBean.setUserRating(Integer.parseInt(rs.getString("userRating")));
            userReviewBean.setComments(rs.getString("comments"));
            userReviewBean.setReviewDate((java.util.Date)rs.getDate("reviewDate"));
            userReviewBean.setApproved(rs.getBoolean("approved"));
            
            // get the user name and email if the review is not anonymous.            
            if (!rs.getBoolean("anonymous")){
                getUsersql = GET_USER_SELECT_SQL + rs.getString("UserID") + "'";
                rs2 = selectUser.executeQuery(getUsersql);
                while (rs2.next()){
                    userReviewBean.setUser(rs2.getString("FirstName")+ " " + rs2.getString("LastName"));
                    userReviewBean.setEmail(rs2.getString("Email"));
                }
            }
            else{
                userReviewBean.setUser("Anonymous");
            }
            
            // add the bean to the array.
            userReviewArray.add(userReviewBean);
        }
        try {
                conn.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
        } 

        return userReviewArray;
    }

    // for user review manager.  lists all user reviews pending approval.
    public ArrayList getAllUserReview(FileLocator healFileLocator) throws ServletException, SQLException{
        Connection conn = dataSource.getConnection();
        Vector rs = new Vector();
        Row row = null;
	ArrayList userReviewArray = new ArrayList();                
        
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(ALL_USER_REVIEW_SELECT_SQL);
             
        try{
            rs = sqlCommandBean.executeQuery();
            Iterator rowIterator = rs.iterator();
            while (rowIterator.hasNext()){
                ShortMetadataBean smBean = new ShortMetadataBean();
                ThumbnailBean thBean = new ThumbnailBean();
                row = (Row) rowIterator.next();
                
                String restype = row.getString("learningResourceType");
                
                // get the thumbnail location.
                if (row.getString("thumblocation") != null){
                    thBean.setLocation(healFileLocator.getThumbnailURL(row.getString("thumblocation")));
                    
                }
                else{
                    // finding the correct thumbnail for the corresponding type of resource.
                    if (restype.equals("Animation")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_animation.jpg"));}
                    else if (restype.equals("Audio")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_audio.jpg"));}
                    else if (restype.equals("Case Study")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_caseStudy.jpg"));}
                    else if (restype.equals("Diagram")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_illustration.gif"));}
                    else if (restype.equals("Exercise")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_exercise.jpg"));}
                    else if (restype.equals("Lecture")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_lecture.jpg"));}
                    else if (restype.equals("Other")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_other.jpg"));}
                    else if (restype.equals("Tutorial")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_tutorial.jpg"));}
                    else if (restype.equals("Video")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_video.jpg"));}
                    else if (restype.equals("Virtual Patient")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_virtualPatient.jpg"));}
                }
                
                smBean.setThumbnail(thBean);
                smBean.setLearningResourceType(restype);
                smBean.setLocation(healFileLocator.getContentURL(row.getString("location")));                
                smBean.setTitle(row.getString("title"));
                smBean.setMetadataId(row.getString("usermetadataid"));
                
                userReviewArray.add(smBean);
            }
        }
        catch (NoSuchColumnException ex) {
        }        
        catch (UnsupportedTypeException ute){
            System.err.println("UnsupportedTypeException: " + ute.getMessage());
        }
        finally {
            try {
                conn.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            } 
        }

        return userReviewArray;
    }

    // get the top 10 most highly rated resources.
    public ArrayList getTop10(FileLocator healFileLocator) throws ServletException, SQLException{
        Connection conn = dataSource.getConnection();
        Vector rs = new Vector();
        Row row = null;

	ArrayList userReviewArray = new ArrayList();                
        
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(GET_TOP_10_POPULAR_SELECT_SQL);
             
        try{
            rs = sqlCommandBean.executeQuery();
            Iterator rowIterator = rs.iterator();

            while (rowIterator.hasNext()){
                ShortMetadataBean smBean = new ShortMetadataBean();
                ThumbnailBean thBean = new ThumbnailBean();
                row = (Row) rowIterator.next();
                
                String restype = row.getString("learningResourceType");
                
                // get the thumbnail location.
                if (row.getString("thumblocation") != null){
                    thBean.setLocation(healFileLocator.getThumbnailURL(row.getString("thumblocation")));
                    
                }
                else{
                    // finding the correct thumbnail for the corresponding type of resource.
                    if (restype.equals("Animation")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_animation.jpg"));}
                    else if (restype.equals("Audio")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_audio.jpg"));}
                    else if (restype.equals("Case Study")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_caseStudy.jpg"));}
                    else if (restype.equals("Diagram")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_illustration.gif"));}
                    else if (restype.equals("Exercise")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_exercise.jpg"));}
                    else if (restype.equals("Lecture")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_lecture.jpg"));}
                    else if (restype.equals("Other")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_other.jpg"));}
                    else if (restype.equals("Tutorial")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_tutorial.jpg"));}
                    else if (restype.equals("Video")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_video.jpg"));}
                    else if (restype.equals("Virtual Patient")) {thBean.setLocation(healFileLocator.getThumbnailURL("../images/thumbnails/thb_virtualPatient.jpg"));}
                }
                
                // put all info into the short metadata bean.
                smBean.setThumbnail(thBean);
                smBean.setLearningResourceType(restype);
                smBean.setLocation(healFileLocator.getContentURL(row.getString("location")));                
                smBean.setTitle(row.getString("title"));
                smBean.setMetadataId(row.getString("usermetadataid"));
                
                // add the bean to the array list.
                userReviewArray.add(smBean);
            }
        }
        catch (NoSuchColumnException ex) {
            ex.printStackTrace();
        }        
        catch (UnsupportedTypeException ute){
            System.err.println("UnsupportedTypeException: " + ute.getMessage());
        }
        finally {
            try {
                conn.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            } 
        }

        return userReviewArray;
    }

    // used to open a database connection and then calls the function below.
    public void saveUserReview(UserReviewBean userReviewBean) throws SQLException {
        Connection conn = dataSource.getConnection();

        try {
            saveUserReview(userReviewBean, conn);
        } finally {
            try {
                conn.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            } 
        }
    }

    // used to save user reviews into the database.
    public void saveUserReview(UserReviewBean userReviewBean, Connection conn) throws SQLException {
        if(userReviewBean == null) {
            return;
        }

        java.util.Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {
            PreparedStatement ps = conn.prepareStatement(USER_REVIEW_INSERT_SQL);

            // set up all the data to be inputted.
            ps.setString(1, userReviewBean.getMetaDataId());
            ps.setString(2, userReviewBean.getUserId());
            ps.setInt(3, userReviewBean.getUserRating());
            ps.setString(4, userReviewBean.getComments());
            ps.setDate(5, sqlDate);
            ps.setBoolean(6, false);           
            ps.setBoolean(7, userReviewBean.getAnonymous());

            // execute the database update.
            ps.executeUpdate();
        }			
        catch(SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }
    
    // used to open a database connection and then calls the function below.
    public void approveUserReview(UserReviewBean userReviewBean) throws SQLException {
        Connection conn = dataSource.getConnection();
        try {
            approveUserReview(userReviewBean, conn);
            conn.commit();
        } finally {
            try {
                conn.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            } 
        }
    }

    // used to approve user reviews. used after the clearUserReviewApproval function below.
    public void approveUserReview(UserReviewBean userReviewBean, Connection conn) throws SQLException {
        if(userReviewBean == null) {
            System.err.println("userReviewBean is null");
            return;
        }

        java.util.Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {
            PreparedStatement ps = conn.prepareStatement(USER_REVIEW_APPROVAL_SQL);
            ps.setDate(1, sqlDate);
            ps.setBoolean(2, userReviewBean.getApproved());                        
            ps.setInt(3, userReviewBean.getReviewId());
            ps.executeUpdate();
        }			
        catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }
    
    // used to clear all the existing reviews approved flag.
    public void clearUserReviewApproval(String metadataId) throws SQLException {
        Connection conn = dataSource.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(USER_CLEAR_APPROVAL_SQL);
            ps.setBoolean(1, false);                        
            ps.setString(2, metadataId);
            ps.executeUpdate();
        }			
        catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
        finally {
            try {
                conn.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            } 
        }
    }
}
