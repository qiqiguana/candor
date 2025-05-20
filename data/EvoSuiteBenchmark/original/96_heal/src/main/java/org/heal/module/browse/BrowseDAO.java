package org.heal.module.browse;
import com.ora.jsp.sql.NoSuchColumnException;
import com.ora.jsp.sql.Row;
import com.ora.jsp.sql.SQLCommandBean;
import com.ora.jsp.sql.UnsupportedConversionException;
import com.ora.jsp.sql.UnsupportedTypeException;

import org.heal.module.metadata.ShortMetadataBean;
import org.heal.util.CommonDAO;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * This DAO was created by Grace, its content came from original BrowseServicesBean.java. 
 * @author Jason Varghese
 * @modify by Grace Yang
 * @version 0.1
 */
 
public class BrowseDAO implements Serializable {
    // Modified by grace, using DataSource and MetadataDAO instead of MetadataDB
    private DataSource dataSource;
    private CommonDAO cd=new CommonDAO();

    /***
     * Sets the dataSource property value. 
     ***/ 
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
 
    public int getCount(String Id)throws SQLException{	
        Connection conn = dataSource.getConnection();
        Row row = null;
        int count=0;
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        StringBuffer sql = new StringBuffer();

        sql.append("SELECT COUNT(DISTINCT TaxonPaths.MetadataID) AS Expr1 FROM Taxons INNER JOIN TaxonPaths ON TaxonPaths.TaxonPathID = Taxons.TaxonPathID ")
        .append("INNER JOIN Metadata ON Metadata.MetadataID = TaxonPaths.MetadataID ")
        .append("WHERE TaxonPaths.Source = 'Mesh' AND Metadata.ApproveDate IS NOT NULL ")
        .append("AND Metadata.Private = '0' AND Taxons.ID LIKE '"+Id+"%';");
        sqlCommandBean.setSqlValue(sql.toString());
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
            Iterator rowIterator = rows.iterator();
            while (rowIterator.hasNext()) {
                row = (Row) rowIterator.next();
                count = row.getInt("Expr1");
            }
        }
    
        catch (UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        }

        catch (NoSuchColumnException ex) {
            throw new SQLException(ex.toString());
        }
        
        catch (UnsupportedConversionException ex) {
            throw new SQLException(ex.toString());
        }

        finally {
            try {
                conn.close();
            } 
            catch (SQLException ex) {
                throw new SQLException(ex.toString());
            }
        }
        return count;
    }
	
    public CategoryBean getCategory(String Id)throws SQLException{
        Connection conn = dataSource.getConnection();
        String id = new String();
        String category = new String();
        Row row = null;
        CategoryBean categoryBean=null;
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ID,Category FROM Headings ")
            .append("WHERE ID = '"+Id+"';");
        sqlCommandBean.setSqlValue(sql.toString());
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
            Iterator rowIterator = rows.iterator();
            while (rowIterator.hasNext()){
                row = (Row) rowIterator.next();
                id = row.getString("ID");
                category = row.getString("Category");
                categoryBean = new CategoryBean();
                categoryBean.setCategoryName(category);
                categoryBean.setCategoryId(id);
            }
        }
        catch (UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        }

        catch (NoSuchColumnException ex) {
            throw new SQLException(ex.toString());
        }
        
        finally {
            try {
                conn.close();
            } 
            catch (SQLException ex) {
                throw new SQLException(ex.toString());
            }
        }
        return categoryBean;
    }

    public Vector getTrail(String Id) throws SQLException{
        Vector a = new Vector();
        StringTokenizer st = new StringTokenizer(Id, ".",true);
        String token = new String();
        if("0".equals(Id))
            return null;
        int count = 0;
        String subToken1 = new String();
		while (st.hasMoreTokens()){
            count++;
            String nextToken = st.nextToken();
            if((count==1)&&(nextToken.length()>1)){
                subToken1=nextToken.substring(0,1);
                if(!".".equals(subToken1))
                a.add(subToken1);
            }
            token=token+nextToken;
            if(!".".equals(nextToken))
                a.add(token);
        }
		
        Vector b = new Vector();
		for(int c=0;c<(a.size());c++){
            CategoryBean categoryBean = new CategoryBean();
            categoryBean=getCategory((String) a.get(c));
            b.add(categoryBean);
		}
		
        return b;
    }
    public Vector getChildrenCategories(String parentId)throws SQLException{
        Connection conn = dataSource.getConnection();
        Vector categories = new Vector();
        String childId = new String();
        String category = new String();
        Row row = null;
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ID,Category FROM Headings ")
        .append("WHERE PID = '"+parentId+"';");
        sqlCommandBean.setSqlValue(sql.toString());
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
            Iterator rowIterator = rows.iterator();
            int count=0;
            while (rowIterator.hasNext()){
                row = (Row) rowIterator.next();
                childId = row.getString("ID");
                category = row.getString("Category");
                CategoryBean categoryBean = new CategoryBean();
                categoryBean.setCategoryName(category);
                categoryBean.setCategoryId(childId);
                count = getCount(childId);
                categoryBean.setCount(count);
                categories.add(categoryBean);
            }
        }
    
        catch (UnsupportedTypeException e){
            throw new SQLException(e.toString());
        }

        catch (NoSuchColumnException ex) {
            throw new SQLException(ex.toString());
        }
        
        finally{
            try {
                conn.close();
            } 

            catch (SQLException ex) {
                throw new SQLException(ex.toString());
            }
        }
        return categories;
    }
	
    public Vector doSQLCommand(String sql) throws SQLException{
        Connection conn = dataSource.getConnection();
        Vector categories = new Vector();
        String category = new String();
        Row row = null;
        CategoryBean categoryBean=null;
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        }
    
        catch (UnsupportedTypeException e){
            throw new SQLException(e.toString());
        }
    
        finally{
            try {
                conn.close();
            } 
        catch (SQLException ex){
            throw new SQLException(ex.toString());
        }
        }
        return rows;
    }
  
    public Vector doBrowse(String id) throws SQLException{
        Vector results = new Vector();
        Vector rows = null;
        Row row = null;
        String Id = null;
        rows=doSQLCommand("SELECT DISTINCT Metadata.MetadataID FROM Metadata INNER JOIN TaxonPaths ON TaxonPaths.MetadataID = Metadata.MetadataID INNER JOIN Taxons ON Taxons.TaxonPathID = TaxonPaths.TaxonPathID "+
            "WHERE Metadata.ApproveDate IS NOT NULL AND Metadata.Private = '0' AND TaxonPaths.Source = 'Mesh' AND"+
            "(Taxons.ID LIKE '" +id+ "%')");
        Iterator rowIterator = rows.iterator();
        try{
            while (rowIterator.hasNext()){
                row = (Row) rowIterator.next();
                Id = new String();
                Id = row.getString("MetadataID");
                results.add(Id);
            }
        }
        catch (NoSuchColumnException ex){
            throw new SQLException(ex.toString());
        }
        return results;
    }
  
    public ShortMetadataBean getSmbById(String id) throws SQLException{
        Connection conn = dataSource.getConnection();
        Vector rows = null;
        Row row = null;
        ShortMetadataBean shortMetadataBean = new ShortMetadataBean();
        rows=doSQLCommand("SELECT Metadata.MetadataID,Metadata.Title,Metadata.Location,Metadata.Description,Metadata.FileSize FROM Metadata  WHERE Metadata.MetadataID = '" +id+ "'");
        Iterator rowIterator = rows.iterator();
        try{
            while (rowIterator.hasNext()){
                row = (Row) rowIterator.next();
                String Id = row.getString("MetadataID");
				String title = row.getString("Title");
				String Location = row.getString("Location");
				String description = row.getString("Description");
				String fileSize = row.getString("FileSize");
				shortMetadataBean.setMetadataId(Id);
				shortMetadataBean.setTitle(title);
				shortMetadataBean.setLocation(Location);
				shortMetadataBean.setDescription(description);
				shortMetadataBean.setFileSize(fileSize);
				shortMetadataBean.setThumbnail(cd.getThumbnail(id,conn));
            }
        }
        catch (NoSuchColumnException ex) {
            throw new SQLException(ex.toString());
        }
        return shortMetadataBean;
    }
}

