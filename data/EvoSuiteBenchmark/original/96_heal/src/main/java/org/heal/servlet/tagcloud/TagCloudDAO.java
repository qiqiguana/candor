package org.heal.servlet.tagcloud;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import com.ora.jsp.sql.NoSuchColumnException;
import com.ora.jsp.sql.Row;
import org.heal.servlet.*;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import com.ora.jsp.sql.SQLCommandBean;
import com.ora.jsp.sql.UnsupportedTypeException;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.http.HttpServlet;

// this is where all the database access work is done.
public class TagCloudDAO extends HttpServlet implements Serializable {
    public TagCloudDAO() {}

    private DataSource dataSource;

    // insert new tag.
    private static final String TAG_INSERT_SQL = "INSERT INTO Tags (Tag, MetaDataID, UserID, EntryDate) VALUES(?, ?, ?, ?)";
    // get tag and tagcount by metadataid 
    private static final String TAG_SELECT_BY_MDID_SQL = "SELECT Tag, COUNT(*) AS tagCount FROM Tags where metadataId = ";
    // get cloud size
    private static final String GET_TAG_CLOUD_SIZE_SQL = "select count(distinct tag) as cloudsize from tags where metadataId = ";
    // get max frequency in cloud
    private static final String GET_MAX_TAG_FREQ_SQL = "select max(tagcount) as maxfreq from (select tag, count(*) as tagcount from tags where metadataid =";
    // get min frequency in cloud
    private static final String GET_MIN_TAG_FREQ_SQL = "select min(tagcount) as minfreq from (select tag, count(*) as tagcount from tags where metadataid =";
    //get the top N tags
    private static final String GET_TOP_TAGS_SQL = "SELECT Tag, tagcount FROM (SELECT TOP 50 COUNT(Tag) AS tagcount, Tag FROM Tags GROUP BY Tag ORDER BY tagcount DESC) DERIVEDTBL ORDER BY Tag";
    // get max and min frequency within the top N
    private static final String GET_MIN_MAX_TAG_COUNT_SQL = "SELECT MAX(tagcount) AS maxc, MIN(tagcount) AS minc FROM (SELECT TOP 50 COUNT(Tag) AS tagcount, Tag FROM Tags GROUP BY Tag ORDER BY tagcount DESC) dt";

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

      // used to open a database connection and then calls the function below.
    public void saveTagEntry(TagBean tagEntry) throws SQLException {
            Connection conn = dataSource.getConnection();

        try {
            saveTagEntry(tagEntry, conn);
        } finally {
            try {
                conn.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            } 
        }
    }

    public void saveTagEntry(TagBean tagEntry, Connection conn) throws SQLException {
        java.util.Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {
            PreparedStatement ps = conn.prepareStatement(TAG_INSERT_SQL);

            // set up all the data to be inputted.
            ps.setString(1, tagEntry.getTag());
            ps.setString(2, tagEntry.getMetadataId());
            ps.setString(3, tagEntry.getUserId());
            ps.setDate(4, sqlDate);

            // execute the database update.
            ps.executeUpdate();
        }			
        catch(SQLException e) {
            System.err.println("In saveTagEntry, SQLException: " + e.getMessage());
        }

    }

    public ArrayList getTags (String metadataId) throws SQLException {
        Connection conn = dataSource.getConnection();
        Vector rs = new Vector();
        Row row = null;
        String tag = null;
        int tagCount = 0;
        int fontSize = 0;
        int cloudSize = 0;
        int maxfreq = 0;
        int minfreq = 0;
        
        ArrayList tagArray = new ArrayList();                
        String getTAGSQL = TAG_SELECT_BY_MDID_SQL + metadataId + " group by tag";
        String cloudSizeSQL = GET_TAG_CLOUD_SIZE_SQL + metadataId;
        String maxFreqSQL = GET_MAX_TAG_FREQ_SQL + metadataId + " group by tag) DERIVEDTBL";
        String minFreqSQL = GET_MIN_TAG_FREQ_SQL + metadataId + " group by tag) DERIVEDTBL";

        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);

        try{
            sqlCommandBean.setSqlValue(cloudSizeSQL);
            rs = sqlCommandBean.executeQuery();
            Iterator rowIterator = rs.iterator();
            while (rowIterator.hasNext()){
                row = (Row) rowIterator.next();
                cloudSize = Integer.parseInt(row.getString("cloudSize"));
            }
            sqlCommandBean.setSqlValue(maxFreqSQL);
            rs = sqlCommandBean.executeQuery();
            rowIterator = rs.iterator();
            while (rowIterator.hasNext()){
                row = (Row) rowIterator.next();
                maxfreq = Integer.parseInt(row.getString("maxfreq"));
            }
            sqlCommandBean.setSqlValue(minFreqSQL);
            rs = sqlCommandBean.executeQuery();
            rowIterator = rs.iterator();
            while (rowIterator.hasNext()){
                row = (Row) rowIterator.next();
                minfreq = Integer.parseInt(row.getString("minfreq"));
            }
            
        }
        catch (NoSuchColumnException ex) {
            ex.printStackTrace();
        }        
        catch (UnsupportedTypeException ute){
            System.err.println("UnsupportedTypeException: " + ute.getMessage());
        }
            
        sqlCommandBean.setSqlValue(getTAGSQL);
        
        try{
            rs = sqlCommandBean.executeQuery();
            Iterator rowIterator = rs.iterator();
            while (rowIterator.hasNext()){
                TagBean tagBean = new TagBean();
                row = (Row) rowIterator.next();
                tag = row.getString("tag");
                tagCount = Integer.parseInt(row.getString("tagCount"));
                tagBean.setTag(tag);
                tagBean.setTagCount(tagCount);
                fontSize = CalcFontSize(tagCount, minfreq, maxfreq);
                tagBean.setFontSize(fontSize);
                tagArray.add(tagBean);
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

        return tagArray;
    }
    
    public ArrayList getTopTags() throws ServletException, SQLException{
        Connection conn = dataSource.getConnection();
        Vector rs = new Vector();
        Row row = null;
        String tag = null;
        int tagcount = 0;
        int fontSize = 0;
        int maxfreq = 0;
        int minfreq = 0;
        
	ArrayList tagArray = new ArrayList();                
        
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);

             
        try{
            sqlCommandBean.setSqlValue(GET_MIN_MAX_TAG_COUNT_SQL);
            rs = sqlCommandBean.executeQuery();
            Iterator rowIterator = rs.iterator();

            while (rowIterator.hasNext()){
                row = (Row) rowIterator.next();
                maxfreq = Integer.parseInt(row.getString("maxc"));
                minfreq = Integer.parseInt(row.getString("minc"));
            }
            
            sqlCommandBean.setSqlValue(GET_TOP_TAGS_SQL);
            rs = sqlCommandBean.executeQuery();
            rowIterator = rs.iterator();

            while (rowIterator.hasNext()){
                TagBean tagBean = new TagBean();
                row = (Row) rowIterator.next();
                tag = row.getString("tag");
                tagcount = Integer.parseInt(row.getString("tagcount"));
                tagBean.setTag(tag);
                fontSize = CalcFontSize(tagcount, minfreq, maxfreq);
                tagBean.setFontSize(fontSize);
                
                // add the bean to the array list.
                tagArray.add(tagBean);
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

        return tagArray;
    }

    private int CalcFontSize (int kfreq, int minfreq, int maxfreq) throws SQLException{
        int minfont = 12;
        int maxfont = 24;
        int fontSize = minfont;
        int fontrange = maxfont - minfont;
        float scalingfactor = 0;
        int freqrange = maxfreq - minfreq;
        try{
            scalingfactor = (kfreq - minfreq)/ (float) freqrange;
            fontSize = (int)(minfont + Math.floor(fontrange * scalingfactor));
        }
        catch (ArithmeticException ex){
            System.err.println("ArithmeticException: " + ex.getMessage());
        }
        
        if (fontSize==0){
            fontSize = minfont;
        }
        
        return fontSize;
    }
}