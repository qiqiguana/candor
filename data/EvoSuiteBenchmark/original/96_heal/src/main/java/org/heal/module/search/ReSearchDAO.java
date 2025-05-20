package org.heal.module.search;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.sql.*;
import com.ora.jsp.sql.*;
import org.heal.module.metadata.*;

/**
 * @author Grace
 * @version 0.1
 */
// This DAO is for users to edit their search results 
public class ReSearchDAO implements Serializable 
{   
  public ReSearchDAO()
  {
  }  
  private DataSource dataSource;   
  /**
    * Sets the dataSource property value. 
    */
  public void setDataSource(DataSource dataSource) 
  {
    this.dataSource = dataSource;
  }         
  /**
    * Here is the process by which the edit search works.
    * First, we get all of the metadata IDs from the Previous search.
    * If you select search within result, The new search scope will be in
    * the previous search. Otherwise this search like a new search.
    * Second we take keyword, media file type and search type  to search in database.
    * The results that are left are packaged into a SearchResultBean and returned.
    * If the search type is 1, it means search any keywords in the database.
    * the keywords list is split (using space as the delimiter) and each 
    * keyword is searched for independently.(like OR)
    * If the search type is 2, it means search all keywords in the database.
    * the keywords list is split (using space as the delimiter) and all the
    * keywords are searched in database.(like AND)
    * If the search type is 3, it means exact search.
    * @param metaids, keywordlist, filterArray, searchtype, filterHidden
    * @return results 
    */
  public SearchResultBean editSearch(String metaids,
                             String keywordList,
                             String[] filterArray,
                             String searchtype,
                             boolean filterHidden)
    throws SQLException 
  {           
    SearchResultBean results = new SearchResultBean();
    results.setShortRecords(null);
    results.setFilterList(filterArray);       
    Vector rows=null;
    Row row = null;
    String metadataId=""; 
    List metaIDs = new ArrayList();
    ShortMetadataResultBean[] metadata = null;           
    Connection conn = dataSource.getConnection();   
    
    try 
    {           
      if (searchtype.equals("Exact")) 
      {                    
        rows = getExactMatches(metaids,keywordList,filterArray,filterHidden,conn);                     
      } 
      else 
      {                   
        rows = getTypeMatches(metaids,keywordList,filterArray,searchtype,filterHidden,conn); 
      }           
      // get the MetadataID and set it to the ShortMetadataBean and ShortMetadataResultBean
      if (rows != null && rows.size() > 0) 
      {
        // found matches...
        Iterator rowIterator = rows.iterator();     
        metadata = new ShortMetadataResultBean[rows.size()];
        int i=0;                 
        while (rowIterator.hasNext()&& i < metadata.length) 
        {
          row = (Row) rowIterator.next();
          try 
          {
            metadataId = row.getString("MetadataID");
            metaIDs.add(metadataId);
            ShortMetadataBean shortMetadata=new ShortMetadataBean();
            shortMetadata.setMetadataId(metadataId);
            ShortMetadataResultBean smb = new ShortMetadataResultBean();
            smb.setMetadataId(metadataId);                   
            metadata[i]=smb;
            i++;                   
          } 
          catch (NoSuchColumnException ex) 
          {
             throw new SQLException(ex.toString());
          }
        }
      }
      results.setShortRecords(metadata);
      results.setMetaIDs(metaIDs);
    }
    finally 
    {
      try 
      {
        conn.close();
      } 
      catch (SQLException ex) 
      {
                //ignore...
      }
    }       
    return results;        
  }

  /**
    * Get MetadataIDs that exact match the keyword and selected media types.
    * If prevoious metadata ids not equal "no", 
    * all the new search will be in the previous search results. 
    * Else the search will be new.
    * If media type is not "All", then call getFilterString(String[]) to get the media type. 
    * Checks for the given keyword and type in the given table and column name using
    * the provided database connection.  If the match is found, a vector of
    * com.ora.jsp.sql.Row is returned. Else return null. 
    * @param mids, keyword, filterArray, hidd, conn
    * @return rows 
   */ 
  private Vector getExactMatches(String mids,String key,String[] filterAry,boolean hidd,Connection conn) throws SQLException 
  {  
    SQLCommandBean sqlCommandBean = new SQLCommandBean();
    sqlCommandBean.setConnection(conn);
    StringBuffer sql = new StringBuffer();
      
    sql.append("SELECT distinct(Metadata.metadataID),Metadata.Title FROM Metadata WHERE ");
    sql.append ("(Metadata.CatalogDate IS NOT NULL AND Metadata.ApproveDate IS NOT NULL");
    if (hidd==false) 
    {
      sql.append(" AND Metadata.Private=0 ");
    }
    sql.append(" AND Metadata.MetadataID NOT IN (select MetadataID from TargetUserGroups where TargetUserGroup LIKE 'consumer Health%' or TargetUserGroup LIKE 'K-12%')");
    sql.append(") AND (Metadata.MetadataID IN (SELECT Metadata.MetadataID FROM Metadata WHERE");     
    sql.append(" (title like '"+key+"' OR title like '% "+key+" %' OR title like '"+key+" %' OR title like '% "+key+"' OR title like '% "+key+".%' OR title like '% "+key+",%'\n");
    sql.append("OR description like '"+key+"' OR description like '% "+key+" %' OR description like '"+key+" %' OR description like '% "+key+"' OR description like '% "+key+".%' OR description like '% "+key+",%'\n");
    sql.append("OR SourceCollection like '"+key+"' OR SourceCollection like '% "+key+" %' OR SourceCollection like '% "+key+"' OR SourceCollection like '"+key+" %' OR SourceCollection like '% "+key+".%' OR SourceCollection like '% "+key+",%'\n");
    sql.append("OR metadataID like '"+key+"' )\n");    
    sql.append("UNION Select keywords.metadataID FROM keywords where keyword like '"+key+"' OR keyword LIKE '% "+key+" %' OR keyword like '"+key+" %' OR keyword like '% "+key+"' OR keyword like '% "+key+".%' OR keyword like '% "+key+",%'\n");
    sql.append("UNION Select Copyrights.metadataID from Copyrights WHERE copyrightTextID IN (Select copyrightTextID from CopyrightTexts WHERE ");
    sql.append("copyrightText like '"+key+"' OR copyrightText LIKE '% "+key+" %' OR copyrightText like '"+key+" %' OR copyrightText like '% "+key+"' OR copyrightText like '% "+key+".%')\n");
    sql.append("UNION Select TaxonPaths.metadataID from TaxonPaths where taxonpathid in (select taxonpathid from taxons where ");
    sql.append("Entry like '"+key+"' OR Entry LIKE '% "+key+" %' OR Entry like '"+key+" %' OR Entry like '% "+key+"' OR Entry like '% "+key+".%' OR Entry like '% "+key+",%')\n");
    sql.append("UNION select copyrightHolders.metadataID from copyrightHolders where vcardID in (select vcardID from vcards where ");
    sql.append("vCard like '"+key+"' OR vCard LIKE '% "+key+" %' OR vCard like '"+key+" %' OR vCard like '% "+key+"' OR vCard like '% "+key+".%' OR vCard like '% "+key+",%')\n");
    sql.append("UNION select Contributors.metadataID from Contributors where vcardID in (select vcardID from vcards where ");        
    sql.append("vCard like '"+key+"' OR vCard LIKE '% "+key+" %' OR vCard like '"+key+" %' OR vCard like '% "+key+"' OR vCard like '% "+key+".%' OR vCard like '% "+key+",%')))\n");        
    if (filterAry != null)  
    {
      String format=getFilterString(filterAry);    
      sql.append(" AND ("+format+")");
    }
    if(mids.equals("no")){}
    else
    { 
      sql.append(" AND Metadata.MetadataID IN ("+mids+")");
    }
    sql.append(" order by Title");
    sqlCommandBean.setSqlValue(sql.toString());
    Vector rows = null;
    try 
    {
      rows = sqlCommandBean.executeQuery();
    }
    catch (UnsupportedTypeException e) 
    {
	    throw new SQLException(e.toString());
    } 
    return rows;
  }    

  /**
    * Get MetadataIDs that match all or any words that user input and selected media types.
    * If prevoious metadata ids not equal "no", all the new search will be in the previous search results.
    * Else the search will be new.
    * Tokenizer the keywords and get each of word.
    * If search type equal "All", add "AND" relation between the word.
    * If search type equal "Any", add "UNION" relation between the word. 
    * If media type is not "All", call getFilterString(String[]) to get the media type. 
    * Checks each keyword of the list and type in the given table and column name using
    * the provided database connection.  If the match is found, a vector of
    * com.ora.jsp.sql.Row is returned. Else return null.
    * @param mids, keywords, filterAry, type, hidd, conn
    * @return rows 
    */      
  private Vector getTypeMatches(String mids, String keywords,String[] filterAry,String type,boolean hidd,Connection conn) throws SQLException 
  {          
    List keys = new ArrayList();
    String key = null;
    StringTokenizer tokenizer = new StringTokenizer(keywords," ");
    while (tokenizer.hasMoreTokens()) 
    {
      keys.add(tokenizer.nextToken());
    } 
    
    SQLCommandBean sqlCommandBean = new SQLCommandBean();
    sqlCommandBean.setConnection(conn);
    StringBuffer sql = new StringBuffer();      
    sql.append("select distinct(Metadata.metadataID),Metadata.Title FROM Metadata WHERE ");
    sql.append ("(Metadata.CatalogDate IS NOT NULL AND Metadata.ApproveDate IS NOT NULL");
    if (hidd==false) 
    {
      sql.append(" AND Metadata.Private=0 ");
    } 
    sql.append(" AND Metadata.MetadataID NOT IN (select MetadataID from TargetUserGroups where TargetUserGroup LIKE 'consumer Health%' or TargetUserGroup LIKE 'K-12%')");
    sql.append(") \n");
    String start=sql.toString();
    for (int i=0; i<keys.size(); i++)
    {
      key = (String)keys.get(i);
      if(type.equals("All") || i == 0)  
      {
        sql.append("AND (Metadata.MetadataID IN \n");
      }
      else if(type.equals("Any") && i >=1) 
      {
        if (filterAry != null) 
        {
          String format=getFilterString(filterAry);
          sql.append(" AND ("+format+")");
        }
        if(mids.equals("no")){}
        else
        { 
          sql.append(" AND Metadata.MetadataID IN ("+mids+")");
        }     
        sql.append(" UNION \n"+start+"AND (Metadata.MetadataID IN \n ");
      } 
      sql.append(" (Select Metadata.MetadataID from metadata where"); 
      sql.append(" (title like '%"+key+"%' \n");
      sql.append("OR description like '%"+key+"%' \n");
      sql.append("OR SourceCollection like '%"+key+"%' \n");
      sql.append("OR metadataID like '"+key+"' )\n");
      sql.append("UNION Select keywords.metadataID FROM keywords where keyword like '%"+key+"%'\n");
      sql.append("UNION Select Copyrights.metadataID from Copyrights WHERE copyrightTextID IN (Select copyrightTextID from CopyrightTexts WHERE ");
      sql.append("copyrightText LIKE '%"+key+"%')\n");
      sql.append("UNION Select TaxonPaths.metadataID from TaxonPaths where taxonpathid in (select taxonpathid from taxons where ");
      sql.append(" Entry LIKE '%"+key+"%')\n");
      sql.append("UNION select copyrightHolders.metadataID from copyrightHolders where vcardID in (select vcardID from vcards where ");
      sql.append(" vCard LIKE '%"+key+"%')\n");
      sql.append("UNION select Contributors.metadataID from Contributors where vcardID in (select vcardID from vcards where ");
      sql.append(" vCard LIKE '%"+key+"%')))\n");
    }
    if (filterAry != null) 
    {
      String format=getFilterString(filterAry);
      sql.append(" AND ("+format+")");
    }
    if(mids.equals("no")){}
    else
    { 
      sql.append(" AND Metadata.MetadataID IN ("+mids+")");
    }
    sql.append(" order by Title");
    sqlCommandBean.setSqlValue(sql.toString());
    Vector rows = null;
    try 
    {
      rows = sqlCommandBean.executeQuery();
    }
    catch (UnsupportedTypeException e) 
    {
	    throw new SQLException(e.toString());
    } 
    return rows;   
  }
  /**
    * Combine some string with each element of filterarray to make SQL statement.
    * @param mids, keywords, filterAry, type, hidd, conn
    * @return rows 
    */
  public String getFilterString(String[] filterAry) 
  {  
    String format="";	
    String f=" OR Metadata.LearningResourceType like '"; 
    int t1=0;   	  
    for( int i=0; i < filterAry.length; i++) 
    {
      if (filterAry.length ==1) 
      {
        format ="Metadata.LearningResourceType like '" + filterAry[0]+"%'"; 
        t1=format.length();
      }
      else 
      {
        format=format+f+filterAry[i]+"%'";            
      }          
    }
    if (t1==0)
    {
      format=format.replaceFirst("OR",""); 
    } 
    return format;
  }

  /**
    * make a String to contain all the metadateIDs that found in search.
    * @param metaID
    * @return inStr 
   */
  public String makeInString(List metaID)
  {
    StringBuffer inStrs=new StringBuffer();
    int msize=metaID.size();
    int i=0;
    while (msize > i)
    {
      inStrs.append(metaID.get(i));
      if (i!=metaID.size()-1)
      {
        inStrs.append(", ");
      }
      i++;
    }    
    String inStr=inStrs.toString();
    return inStr;
  }
  /**
    * Sort the search results as required field.
    * @param filed, mids
    * @return results 
   */
  public SearchResultBean sortResult(String filed, String mids) throws SQLException 
  {  
    SearchResultBean results = new SearchResultBean();
    results.setShortRecords(null);
    Vector rows=null;
    Row row = null;
    String metadataId=""; 
    List metaIDs = new ArrayList();
    ShortMetadataResultBean[] metadata = null;            
    Connection conn = dataSource.getConnection();                   
    try 
    {      
      rows = getSortMetadataID(filed,mids,conn);     
      // get the MetadataID and set it to the ShortMetadataBean and ShortMetadataResultBean
      if (rows != null && rows.size() > 0) 
      {
       // found matches...
        Iterator rowIterator = rows.iterator();     
        metadata = new ShortMetadataResultBean[rows.size()];
        int i=0;                 
        while (rowIterator.hasNext()&& i < metadata.length) 
        {
          row = (Row) rowIterator.next();
          try 
          {
            metadataId = row.getString("MetadataID");
            metaIDs.add(metadataId);
            ShortMetadataBean shortMetadata=new ShortMetadataBean();
            shortMetadata.setMetadataId(metadataId);
            ShortMetadataResultBean smb = new ShortMetadataResultBean();
            smb.setMetadataId(metadataId);                    
            metadata[i]=smb;
            i++;                   
          } 
          catch (NoSuchColumnException ex) 
          {
            throw new SQLException(ex.toString());
          }
        }
      }
      results.setShortRecords(metadata);
      results.setMetaIDs(metaIDs);
    }finally 
    {
      try 
      {
        conn.close();
      } catch (SQLException ex) 
      {
      //ignore...
      }
    }       
    return results;        
  }
  /**
    * Get a sort field and sort in database using order by.
    * Return the sorted rows.
    * @param fd, ids, conn
    * @return rows 
   */
  private Vector getSortMetadataID(String fd, String ids, Connection conn) throws SQLException 
  {           
    SQLCommandBean sqlCommandBean = new SQLCommandBean();
    sqlCommandBean.setConnection(conn);
    StringBuffer sql = new StringBuffer(); 
    if (fd.equals("Format")) 
    {
      sql.append("SELECT MetadataID, LearningResourceType FROM Metadata WHERE metadataID IN ("+ids+") ") 
       .append("ORDER BY LearningResourceType");
    }
    else if (fd.equals("FileSize"))
    {      
      sql.append("SELECT MetadataID,FileSize FROM Metadata WHERE metadataID IN ("+ids+") ")
         .append("ORDER BY CASE when FileSize=0 then CONVERT(int, 99999999) ELSE fileSize END ASC");
    }
    else if (fd.equals("SourceCollection")) 
    {
      sql.append("SELECT MetadataID,SourceCollection FROM Metadata WHERE metadataID IN ("+ids+") ") 
       .append("ORDER BY SourceCollection");
    }
    else 
    {
      sql.append("SELECT MetadataID, Title FROM Metadata WHERE metadataID IN ("+ids+") ") 
       .append("ORDER BY Title");
    }        
    sqlCommandBean.setSqlValue(sql.toString());
    Vector rows = null;
    try 
    {
      rows = sqlCommandBean.executeQuery();
    }
    catch (UnsupportedTypeException e) 
    {
      throw new SQLException(e.toString());
    } 
    return rows;
  }  
}