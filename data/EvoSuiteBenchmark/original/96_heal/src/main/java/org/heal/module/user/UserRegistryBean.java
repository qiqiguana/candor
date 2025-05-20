package org.heal.module.user;

import com.ora.jsp.sql.*;
import com.ora.jsp.sql.value.BooleanValue;
import com.ora.jsp.sql.value.StringValue;

import java.util.Iterator;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

/**
 * This contains methods for authenticating a user,
 * and retrieving and updating user information.
 *
 * @author Seth Wright
 * @modify Jason Varghese
 * @version 0.1
 */
public class UserRegistryBean implements Serializable {
    private DataSource dataSource;

    /**
     * Sets the dataSource property value.
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Returns true if the specified user name and password
     * match an employee in the database.     */
    public UserBean verifyLogin(String userName, String password, boolean emailIsUsername)
            throws SQLException {

        UserBean userInfo = getUser(userName, emailIsUsername);
        if (userInfo != null && password.equals(userInfo.getPassword())) {
            return userInfo;
        }
        return null;
    }

    /**
     * Returns true if the given user has the specified permissions.
     */
    public boolean checkPermissions(UserBean user, String accessLevel) {
        /* XXX not currently implemented */
        return false;
    }

    /**
     * Returns true if the user was found in the database, false
     * otherwise.  Throws an SQLException in the event something goes
     * wrong.
     */
    public boolean userExists(String userName) throws SQLException {

        // Get the user info from the database
        Connection conn = dataSource.getConnection();
        Row userRow = null;
        try {
            userRow = getUserPropertiesFromEmail(userName, conn);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            } // Ignore
        }

        return (userRow != null);
    }
    
    /**
     * Returns a Vector of Strings of all of the usernames in the
     * database.  Returns null on an error or if no entries exist in the
     * database.
     */
    public Vector getAllUserIds() throws SQLException {
        //Get the usernames from the database
        Connection conn = dataSource.getConnection();
        Vector names = null;
        try {
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            String sql = "SELECT UserId FROM USERS";
            sqlCommandBean.setSqlValue(sql);
            Vector rows = null;
            try {
								Row row = null;
                rows = sqlCommandBean.executeQuery();
                if (rows != null && rows.size() > 0) {
                    names = new Vector();
                    Object[] rowArray = rows.toArray();
                    for (int rowIndex = 0; rowIndex < rowArray.length; rowIndex++) {
                        String name = ((Row) rowArray[rowIndex]).getString("UserId");
                        names.add(name);
                    }
                }
            } catch (UnsupportedTypeException e) {
                throw new SQLException(e.toString());
            } catch (NoSuchColumnException e2) {
                throw new SQLException(e2.toString());
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();}
        }
        return names;
    }    

    /**
     * Returns a Vector of Strings of all of the usernames in the
     * database.  Returns null on an error or if no entries exist in the
     * database.
     */
    public Vector getAllUsernames() throws SQLException {
        //Get the usernames from the database
        Connection conn = dataSource.getConnection();
        Vector names = null;
        try {
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            String sql = "SELECT UserName FROM USERS";
            sqlCommandBean.setSqlValue(sql);
            Vector rows = null;
            try {
								Row row = null;
                rows = sqlCommandBean.executeQuery();
                if (rows != null && rows.size() > 0) {
                    names = new Vector();
                    Object[] rowArray = rows.toArray();
                    for (int rowIndex = 0; rowIndex < rowArray.length; rowIndex++) {
                        String name = ((Row) rowArray[rowIndex]).getString("UserName");
                        names.add(name);
                    }
                }
            } catch (UnsupportedTypeException e) {
                throw new SQLException(e.toString());
            } catch (NoSuchColumnException e2) {
                throw new SQLException(e2.toString());
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();}
        }
        return names;
    }


    /**
     * Returns a Vector of Strings of all of the usernames in the
     * database.  Returns null on an error or if no entries exist in the
     * database.
     */
    public int getCountOfUsers() throws SQLException {
        //Get the usernames from the database
        Connection conn = dataSource.getConnection();
		int count = 0;
        try {
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            String sql = "SELECT Count(distinct email) as Expr1 FROM USERS";
            sqlCommandBean.setSqlValue(sql);
            try {
                Vector rows = null;
				Row row = null;
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
            catch (NoSuchColumnException e2) {
                throw new SQLException(e2.toString());
            }
			catch (UnsupportedConversionException ex){
                throw new SQLException(ex.toString());
			}						
        } 
        finally {
            try {
                conn.close();
            } 
            catch (SQLException e) {
            e.printStackTrace();}
        }
        return count;
    }

    /**
     * Returns a Vector of Strings of all of the usernames in the
     * database.  Returns null on an error or if no entries exist in the
     * database.
     */
    public int getAreaofExpertiseSummary(String value) throws SQLException {
        //Get the usernames from the database
        Connection conn = dataSource.getConnection();
		int count = 0;
        try {
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            String sql = "SELECT Count(userid) as Expr1 FROM USERS where professionalSpecialty = ?";
            sqlCommandBean.setSqlValue(sql);
            Vector values = new Vector();
            values.addElement(new StringValue(value));
            sqlCommandBean.setValues(values);
            try {
                Vector rows = null;
                Row row = null;
                rows = sqlCommandBean.executeQuery();
                Iterator rowIterator = rows.iterator();
                while (rowIterator.hasNext()){
                    row = (Row) rowIterator.next();
                    count = row.getInt("Expr1");
				}
            } 
            catch (UnsupportedTypeException e) {
                throw new SQLException(e.toString());
            } 
            catch (NoSuchColumnException e2) {
                throw new SQLException(e2.toString());
            }
			catch (UnsupportedConversionException ex) {
                throw new SQLException(ex.toString());
			}						
        } 
        finally {
            try {
                conn.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    /**
     * Returns an UserBean initialized with the information
     * found in the database for the specified employee, or null if
     * not found.
     */
    public UserBean getUser(String userName,boolean emailIsUsername) throws SQLException {

        // Get the user info from the database
        Connection conn = dataSource.getConnection();
        Row userRow = null;
		Vector userProfessionalRoleRow = null;
		Vector userInstructionalLevel = null;
		UserBean userBean = new UserBean();

        try {
            userRow = getUserProperties(userName, emailIsUsername, conn);
			userBean=rowToBean(userRow);
            if(userRow!=null){
                userProfessionalRoleRow = getUserProfessionalRole(userBean.getUserId(), conn);
                userInstructionalLevel = getUserInstructionalLevel(userBean.getUserId(), conn);
			}
        } 
        finally {
            try {
                conn.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
        if(userProfessionalRoleRow!=null){	
            Iterator it = userProfessionalRoleRow.iterator();
			while (it.hasNext()){
                userBean.addProfessionalRole(rowToProfessionalRole((Row)it.next()));
			}
		}
				
        if(userInstructionalLevel!=null){
            Iterator it2 = userInstructionalLevel.iterator();
			while (it2.hasNext()){
                userBean.addInstructionalLevel(rowToInstructionalLevel((Row)it2.next()));	
			}				
		}
        return userBean;
    }

    /**
     * Returns an UserBean initialized with the information
     * found in the database for the specified email address, or null if
     * not found.
     */
    public UserBean getUserFromEmail(String email) throws SQLException {
        // Get the user info from the database
        Connection conn = dataSource.getConnection();
        Row userRow = null;
        try {
            userRow = getUserPropertiesFromEmail(email, conn);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();}
        }
        return rowToBean(userRow);

    }    
    

    public UserBean getUserFromID(String userID) throws SQLException {
        // Get the user info from the database
        Connection conn = dataSource.getConnection();
        Row userRow = null;
        Vector userProfessionalRoleRow = null;
        Vector userInstructionalLevel = null;
        
        UserBean userBean = new UserBean();
        try {
            userRow = getUserPropertiesFromID(userID, conn);
            userBean = rowToBean(userRow);
            if(userRow!=null)	{
              userProfessionalRoleRow = getUserProfessionalRole(userBean.getUserId(), conn);
              userInstructionalLevel = getUserInstructionalLevel(userBean.getUserId(), conn);
						}            
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();}
        }
		
        if(userProfessionalRoleRow!=null){	
            Iterator it = userProfessionalRoleRow.iterator();
            while (it.hasNext()){
                userBean.addProfessionalRole(rowToProfessionalRole((Row)it.next()));
            }
		}
        
		if(userInstructionalLevel!=null){
            Iterator it = userInstructionalLevel.iterator();
            while (it.hasNext()){
                userBean.addInstructionalLevel(rowToInstructionalLevel((Row)it.next()));	
			}				
		}
        
        return userBean;
    }
    
    /**
     * Returns a Vector of Strings of all of the usernames in the
     * database.  Returns null on an error or if no entries exist in the
     * database.
     */
    public Vector getAreaofExpertiseValues() throws SQLException {
        //Get the usernames from the database
        Connection conn = dataSource.getConnection();
        Vector names = null;
        try {
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            String sql = "SELECT distinct ProfessionalSpecialty FROM USERS where modifiedLogin = '1'";
            sqlCommandBean.setSqlValue(sql);
            Vector rows = null;
            try {
                Row row = null;
                rows = sqlCommandBean.executeQuery();
                if (rows != null && rows.size() > 0) {
                    names = new Vector();
                    Object[] rowArray = rows.toArray();
                    for (int rowIndex = 0; rowIndex < rowArray.length; rowIndex++) {
                        String name = ((Row) rowArray[rowIndex]).getString("ProfessionalSpecialty");
                        names.add(name);
                    }
                }
            } 
            catch (UnsupportedTypeException e) {
                throw new SQLException(e.toString());
            } 
            catch (NoSuchColumnException e2) {
                throw new SQLException(e2.toString());
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();}
        }
        return names;
    }    
    

    /**
     * Takes a Row obtained from the database and translates that into
     * a UserBean.  Returns null if the userRow is null and throws
     * and SQLException if an error occurs.
     */
    private UserBean rowToBean(Row userRow)
            throws SQLException {
        // Create a UserBean if the user was found
        if (userRow == null) {
            // Not found
            return null;
        }

        UserBean userInfo = new UserBean();
        try {
            userInfo.setUserId(userRow.getString("UserID"));
            userInfo.setUserName(userRow.getString("UserName"));
            userInfo.setPassword(userRow.getString("Password"));
            userInfo.setMinor(userRow.getBoolean("Minor"));
            userInfo.setAdministrator(userRow.getBoolean("Administrator"));
            userInfo.setCataloger(userRow.getBoolean("Cataloger"));
            userInfo.setApprover(userRow.getBoolean("Approver"));
            userInfo.setFirstName(userRow.getString("FirstName"));
            userInfo.setLastName(userRow.getString("LastName"));
            userInfo.setMiddleInitial(userRow.getString("MI"));
            userInfo.setEmail(userRow.getString("Email"));
            //userInfo.setProfessionalTitle(userRow.getString("ProfessionalTitle"));
            userInfo.setProfessionalSpecialty(userRow.getString("ProfessionalSpecialty"));
            userInfo.setPhoneNumber(userRow.getString("PhoneNumber"));
            userInfo.setInstitutionName(userRow.getString("InstitutionName"));
            userInfo.setAddress1(userRow.getString("Address1"));
            userInfo.setAddress2(userRow.getString("Address2"));
            userInfo.setCity(userRow.getString("City"));
            userInfo.setState(userRow.getString("State"));
            userInfo.setZipCode(userRow.getString("ZipCode"));
            userInfo.setCountry(userRow.getString("Country"));
            userInfo.setMailingList(userRow.getBoolean("mailingList"));
            userInfo.setLoginModified(userRow.getBoolean("modifiedLogin"));
            userInfo.setEmailValidated(userRow.getBoolean("emailValidated"));
            userInfo.setIAMSEMember(userRow.getBoolean("IAMSEMember"));					
            
        } catch (UnsupportedConversionException ex) {
            throw new SQLException(ex.toString());
        } catch (NoSuchColumnException nsce) {
            throw new SQLException(nsce.toString());
        }

        return userInfo;
    }

    /**
     * Takes a Row obtained from the database and translates that into
     * a UserBean.  Returns null if the userRow is null and throws
     * and SQLException if an error occurs.
     */
    private ProfessionalRoleBean rowToProfessionalRole(Row userRow)
            throws SQLException {
        // Create a UserBean if the user was found
        if (userRow == null) {
            // Not found
            return null;
        }

        ProfessionalRoleBean professionalRole = new ProfessionalRoleBean();
        try {
            professionalRole.setProfessionalRoleId(userRow.getString("ProfessionalRoleId"));
            professionalRole.setUserId(userRow.getString("UserId"));
            professionalRole.setProfessionalRole(userRow.getString("ProfessionalRole"));
        } catch (NoSuchColumnException nsce) {
            throw new SQLException(nsce.toString());
        }
        return professionalRole;
    }
		
    /**
     * Takes a Row obtained from the database and translates that into
     * a UserBean.  Returns null if the userRow is null and throws
     * and SQLException if an error occurs.
     */
    private InstructionalLevelBean rowToInstructionalLevel(Row userRow)
            throws SQLException {
        // Create a UserBean if the user was found
        if (userRow == null) {
            // Not found
            return null;
        }

        InstructionalLevelBean instructionalLevel = new InstructionalLevelBean();
        try {
            instructionalLevel.setInstructionalLevelId(userRow.getString("InstructionalLevelId"));
            instructionalLevel.setUserId(userRow.getString("UserId"));
            instructionalLevel.setInstructionalLevel(userRow.getString("InstructionalLevel"));
        } catch (NoSuchColumnException nsce) {
            throw new SQLException(nsce.toString());
        }
        return instructionalLevel;
    }	


    /**
     * Inserts the information about the specified user, or
     * updates the information if it's already defined.
     */
    public String saveRegistration(UserBean userInfo) throws SQLException {

        // Save the user info from the database
        Connection conn = dataSource.getConnection();
        Connection conn2 = dataSource.getConnection();
		String userId="";
        conn.setAutoCommit(false);
        conn2.setAutoCommit(false);
				
        try {
            saveCompleteUserProperties(userInfo, conn);
            conn.commit();
			userId = userInfo.getUserId();		
			if(userInfo.getUserId() == null || userInfo.getUserId().length()==0){
                UserBean userInfo2=getUserFromEmail(userInfo.getEmail());
				userInfo.setUserId(userInfo2.getUserId());
				userId = userInfo2.getUserId();
			}				
				
			saveUserProfessionalRole(userInfo, conn2);
			saveUserInstructionalLevel(userInfo, conn2);	
            conn2.commit();						
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
                conn2.setAutoCommit(true);
                conn2.close();								
            } catch (SQLException e) {
							e.printStackTrace();} 
        }
		return userId;
    }

    /**
     * Inserts the XML encoded user registration information into the
     * database, or updates the information if it is already defined.
     */
    public void saveRegistration(String xmlRegistration) throws SQLException {
        UserBean userInfo = new UserBean();
        userInfo.parseXML(xmlRegistration);
        saveRegistration(userInfo);
    }

    /**
     * Looks up a user property in the Users table that matches the given
     * UserName.  The property should be a String value such that calling
     * Row.getString() doesn't cause any problems.
     */
    private String getUserStringProperty(String userName,
                                         String propertyName,
                                         Connection conn)
            throws SQLException {
        if (userName == null || propertyName == null) {
            return null;
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        String sql = "SELECT " + propertyName + " FROM USERS WHERE UserName = ?";
        sqlCommandBean.setSqlValue(sql);
        Vector values = new Vector();
        values.addElement(new StringValue(userName));
        sqlCommandBean.setValues(values);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        } catch (UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        } // Can not happen here

        if (rows == null || rows.size() == 0) {
            // User not found
            return null;
        }
        Row aRow = (Row) rows.firstElement();
        String result = null;
        try {
            result = aRow.getString(propertyName);
        } catch (NoSuchColumnException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Returns a Row with all information about the specified
     * user or null if the user is not found.
     */
    private Row getUserProperties(String userName, boolean emailIsUsername, Connection conn)
            throws SQLException {

        if (userName == null) {
            return null;
        }

        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
		String sql="";
		if (emailIsUsername){
            sql = "SELECT * FROM USERS WHERE Email = ? and modifiedLogin = 1";
		}
		else{
            sql = "SELECT * FROM USERS WHERE UserName = ?";
		}
        sqlCommandBean.setSqlValue(sql);
        Vector values = new Vector();
        values.addElement(new StringValue(userName));
        sqlCommandBean.setValues(values);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        } catch (UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        } // Can not happen here

        if (rows == null || rows.size() == 0) {
            // User not found
            return null;
        }
        return (Row) rows.firstElement();
    }

    /**
     * Returns a Row with all information about the specified
     * user or null if the user is not found.
     */
    private Vector getUserProfessionalRole(String userId, Connection conn)
            throws SQLException {

        if (userId == null) {
            return null;
        }

        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        String sql = "SELECT * FROM UsersProfessionalRole WHERE UserId = ?";
        sqlCommandBean.setSqlValue(sql);
        Vector values = new Vector();
        values.addElement(new StringValue(userId));
        sqlCommandBean.setValues(values);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        } catch (UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        } // Can not happen here

        if (rows == null || rows.size() == 0) {
            // User not found
            return null;
        }
        //return (Row) rows.firstElement();
		return rows;
    }
		
    /**
     * Returns a Row with all information about the specified
     * user or null if the user is not found.
     */
    private Vector getUserInstructionalLevel(String userId, Connection conn)
            throws SQLException {

        if (userId == null) {
            return null;
        }

        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        String sql = "SELECT * FROM UsersInstructionalLevel WHERE UserId = ?";
        sqlCommandBean.setSqlValue(sql);
        Vector values = new Vector();
        values.addElement(new StringValue(userId));
        sqlCommandBean.setValues(values);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        } catch (UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        } // Can not happen here

        if (rows == null || rows.size() == 0) {
            // User not found
            return null;
        }
        //return (Row) rows.firstElement();
		return rows;
    }		

    /**
     * Returns a Row with all information about the specified
     * user or null if the user is not found.
     */
    private Row getUserPropertiesFromID(String userID, Connection conn)
            throws SQLException {

        if (userID == null) {
            return null;
        }

        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        String sql = "SELECT * FROM USERS WHERE UserID = ?";
        sqlCommandBean.setSqlValue(sql);
        Vector values = new Vector();
        values.addElement(new StringValue(userID));
        sqlCommandBean.setValues(values);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        } catch (UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        } // Can not happen here

        if (rows == null || rows.size() == 0) {
            // User not found
            return null;
        }
        return (Row) rows.firstElement();
    }
    
       /**
     * Returns a Row with all information about the specified
     * user or null if the user's email address is not found.
     */
    private Row getUserPropertiesFromEmail(String email, Connection conn)
            throws SQLException {

        if (email == null) {
            return null;
        }

        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        String sql = "SELECT * FROM USERS WHERE email = ?";
        sqlCommandBean.setSqlValue(sql);
        Vector values = new Vector();
        values.addElement(new StringValue(email));
        sqlCommandBean.setValues(values);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        } catch (UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        } // Can not happen here

        if (rows == null || rows.size() == 0) {
            // User not found
            return null;
        }
        return (Row) rows.firstElement();
    } 

    /**
     * Given a user ID and a set of permissions, looks up the user in the
     * database and if the user is found, updates their permissions.  Returns
     * false if either parameter is null, the user was not found in the
     * database, or an error occured.  Otherwise the method returns true.
     */
    public boolean setPermissions(String userId,
                                  UserPermissionsBean permissions) {
        if (userId == null || permissions == null) {
            return false;
        }
        boolean success = false;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            Row userRow = getUserProperties(userId, false, conn);
            UserBean user = rowToBean(userRow);
            if (user != null) {
                user.setMinor(permissions.isMinor());
                user.setAdministrator(permissions.isAdministrator());
                user.setCataloger(permissions.isCataloger());
                user.setApprover(permissions.isApprover());
                saveUserProperties(user, conn);
                success = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.commit();
                    conn.close();
                }
            } catch (SQLException ex2) {
                ex2.printStackTrace();
            }
        }
        return success;
    }

    /**
     * Inserts the information about the specified user,
     * or updates the information if it's already defined.
     */
    private void saveCompleteUserProperties(UserBean userInfo, Connection conn)
            throws SQLException {

        if (userInfo == null) {
            return;
        }
		saveUserProperties(userInfo, conn);
		//conn.commit();				
		//if(userInfo.getUserId() == null || userInfo.getUserId().length()==0)
        //{
            //userInfo=getUserFromEmail(userInfo.getEmail());
		//}				
				
		//saveUserProfessionalRole(userInfo, conn);
		//saveUserInstructionalLevel(userInfo, conn);
	}

    /**
     * Inserts the information about the specified user,
     * or updates the information if it's already defined.
     */
    private void saveUserProperties(UserBean userInfo, Connection conn)
            throws SQLException {

        if (userInfo == null) {
            return;
        }

        UserBean dbInfo = getUser(userInfo.getUserName(),false);
        StringBuffer sql = new StringBuffer();
        if (dbInfo == null) {
            // Use INSERT statement
            sql.append("INSERT INTO Users ").
                    append("(Password, Minor, Administrator, ").
                    append("Cataloger, Approver, FirstName, LastName, MI, ").
                    //append("Email, ProfessionalTitle, ProfessionalSpecialty, ").
                    append("Email, ProfessionalSpecialty, ").		
                    append("PhoneNumber, InstitutionName, Address1, Address2, ").
                    append("City, State, ZipCode, Country, mailingList, modifiedLogin, emailValidated, IAMSEMember, UserName) ").
                    append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ").
                    append("?, ?, ?, ?, ?, ?, ?, ?,?,?)");
        } else {
            // Use UPDATE statement
            sql.append("UPDATE Users SET ").
                    append("Password = ?, Minor = ?, Administrator = ?, ").
                    append("Cataloger = ?, Approver = ?, FirstName = ?, ").
                    append("LastName = ?, MI = ?, Email = ?, ").
                    //append("ProfessionalTitle = ?, ProfessionalSpecialty = ?, ").
                    append("ProfessionalSpecialty = ?, ").
                    append("PhoneNumber = ?, InstitutionName = ?, Address1 = ?, ").
                    append("Address2 = ?, City = ?, State = ?, ZipCode = ?, ").
                    append("Country = ?, mailingList = ?, modifiedLogin = ?, emailValidated = ?, IAMSEMember = ? WHERE UserName= ?");
        }

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, userInfo.getPassword());
            ps.setBoolean(2, userInfo.isMinor());
            ps.setBoolean(3, userInfo.isAdministrator());
            ps.setBoolean(4, userInfo.isCataloger());
            ps.setBoolean(5, userInfo.isApprover());
            ps.setString(6, userInfo.getFirstName());
            ps.setString(7, userInfo.getLastName());
            ps.setString(8, userInfo.getMiddleInitial());
            ps.setString(9, userInfo.getEmail());
            //ps.setString(10, userInfo.getProfessionalTitle());
            ps.setString(10, userInfo.getProfessionalSpecialty());
            ps.setString(11, userInfo.getPhoneNumber());
            ps.setString(12, userInfo.getInstitutionName());
            ps.setString(13, userInfo.getAddress1());
            ps.setString(14, userInfo.getAddress2());
            ps.setString(15, userInfo.getCity());
            ps.setString(16, userInfo.getState());
            ps.setString(17, userInfo.getZipCode());
            ps.setString(18, userInfo.getCountry());
            ps.setBoolean(19, userInfo.getMailingList());
            ps.setBoolean(20, true);
            ps.setBoolean(21, userInfo.isEmailValidated());
            ps.setBoolean(22, userInfo.isIAMSEMember());
            ps.setString(23, userInfo.getUserName());
            ps.executeUpdate();
        } finally {
            if(null != ps) {
                // If this isn't in a finally block, then an SQL Exception
                // will leave the prepared statement open when it's returned
                // to the connection pool, causing cloned connection transaction
                // exceptions
                ps.close();
            }
        }
    }
		
		
		public void reset(String username){	
            PreparedStatement ps = null;
            try {
                Connection conn = dataSource.getConnection();
                ps = conn.prepareStatement("UPDATE Users SET modifiedLogin = 0 where username = ?");
                ps.setString(1, username);
                ps.executeUpdate();
            }    
			catch(SQLException sqlEx){
                sqlEx.printStackTrace();
            }
			finally {
                if(null != ps) {
                // If this isn't in a finally block, then an SQL Exception
                // will leave the prepared statement open when it's returned
                // to the connection pool, causing cloned connection transaction
                // exceptions
                    try{
                        ps.close();
					}
					catch(SQLException sqlEx){
                        sqlEx.printStackTrace();
                    }
                }   
            }		
		}

    public boolean validateEmail(String email, int userId){
        PreparedStatement ps = null;
        int rowCount = 0;
        boolean update = false;
        try{
            Connection conn = dataSource.getConnection();
            ps = conn.prepareStatement("UPDATE Users SET emailValidated = 1 where email = ? and userId = ?");
            ps.setString(1, email);
            ps.setInt(2, userId);
            rowCount = ps.executeUpdate();
        } 
        catch(SQLException sqlEx){
            sqlEx.printStackTrace();
        }
		finally {
            if(null != ps){
            // If this isn't in a finally block, then an SQL Exception
            // will leave the prepared statement open when it's returned
            // to the connection pool, causing cloned connection transaction
            // exceptions
                try{
                    ps.close();
				}
				catch(SQLException sqlEx){
                    sqlEx.printStackTrace();
                }
            }
        }
      
        if (rowCount > 0){
            return true;
        }  
        else{
            return false;
        }  
    }
		public void updateEmail(String email, String newemail)
		{
			
							PreparedStatement ps = null;
        try {
						Connection conn = dataSource.getConnection();

            ps = conn.prepareStatement("UPDATE Users SET emailValidated = 0, UserName = ?, email= ? where email = ?");
            ps.setString(1, newemail);
            ps.setString(2, newemail);
            ps.setString(3, email);
            ps.executeUpdate();
        } 
				catch(SQLException sqlEx){
				}
				finally {
            if(null != ps) {
                
								try{
                ps.close();
								}
								catch(SQLException sqlEx){
				}
            }
        }		

		}
			
    /**
     * Inserts the information about the specified user,
     * or updates the information if it's already defined.
     */
    private void saveUserProfessionalRole(UserBean userInfo, Connection conn)
            throws SQLException {

        if (userInfo == null) {
            return;
        }
		
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("DELETE UsersProfessionalRole where UserId = ?");
            ps.setString(1, userInfo.getUserId());
            ps.executeUpdate();
        } finally {
            if(null != ps) {
                // If this isn't in a finally block, then an SQL Exception
                // will leave the prepared statement open when it's returned
                // to the connection pool, causing cloned connection transaction
                // exceptions
                ps.close();
            }
        }

            String userId = userInfo.getUserId();				
			ProfessionalRoleBean prb = null;
			ArrayList prbs = userInfo.getProfessionalRole();
            Iterator it = prbs.iterator();
			while (it.hasNext()){
                prb=(ProfessionalRoleBean)it.next();
                StringBuffer sql = new StringBuffer();
                // Use INSERT statement
                sql.append("INSERT INTO UsersProfessionalRole ").
                    append("(UserId, ProfessionalRole) ").
                    append("VALUES (?,?)");
                    
                try {
                    ps = conn.prepareStatement(sql.toString());
                    ps.setInt(1,Integer.parseInt(userId));
                    ps.setString(2, prb.getProfessionalRole());
                    ps.executeUpdate();//System.err.println("heree");
                } 
                finally {
                    if(null != ps) {
                    // If this isn't in a finally block, then an SQL Exception
                    // will leave the prepared statement open when it's returned
                    // to the connection pool, causing cloned connection transaction
                    // exceptions
                        ps.close();
                    }
                }
            }
        }


    /**
     * Inserts the information about the specified user,
     * or updates the information if it's already defined.
     */
    private void saveUserInstructionalLevel(UserBean userInfo, Connection conn)
            throws SQLException {

        if (userInfo == null) {
            return;
        }

		PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE UsersInstructionalLevel where UserId = ?");
            ps.setString(1, userInfo.getUserId());
            ps.executeUpdate();
        } finally {
            if(null != ps) {
                // If this isn't in a finally block, then an SQL Exception
                // will leave the prepared statement open when it's returned
                // to the connection pool, causing cloned connection transaction
                // exceptions
                ps.close();
            }
        }				
		String userId = userInfo.getUserId();
		InstructionalLevelBean ilb = null;
		ArrayList ilbs = userInfo.getInstructionalLevel();
        Iterator it = ilbs.iterator();
		while (it.hasNext()){
            ilb=(InstructionalLevelBean)it.next();
			StringBuffer sql = new StringBuffer();

            // Use INSERT statement
            sql.append("INSERT INTO UsersInstructionalLevel ").
                    append("(UserId, InstructionalLevel) ").
                    append("VALUES (?,?)");
										
            try {
                ps = conn.prepareStatement(sql.toString());
                ps.setInt(1,Integer.parseInt(userId));
                ps.setString(2, ilb.getInstructionalLevel());
                ps.executeUpdate();
            } 
            finally {
                if(null != ps) {
                // If this isn't in a finally block, then an SQL Exception
                // will leave the prepared statement open when it's returned
                // to the connection pool, causing cloned connection transaction
                // exceptions
                    ps.close();
                }
            }
		}
    }		

    /**
     * Looks up the user's ID in the Users table and adds an entry for it
     * in the Subscriber's table (if it doesn't already exist).
     * Returns true if successful (also if it already exists in the table)
     * and false if an error occurs.
     */
    public boolean addUserToSubscribers(String userName) {
        boolean success = false;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            String userId = getUserStringProperty(userName, "UserID", conn);
            if (userId != null) {
                if (getSubscriber(userId, conn) != null) {
                    success = true;
                } else {
                    success = addSubscriber(userId, conn);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                //ignore, we just want the connection closed.
            }
        }
        return success;
    }

    /**
     * Looks up the user's ID in the Users table and removes any entries for it
     * in the Subscriber's table.  Returns true if on success and false if
     * an error occurs.
     */
    public boolean removeUserFromSubscribers(String userName) {
        boolean success = false;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            String userId = getUserStringProperty(userName, "UserID", conn);
            if (userId != null) {
                success = deleteSubscriber(userId, conn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                //ignore, we just want the connection closed.
            }
        }
        return success;
    }

    /**
     * Attempts to delete a subscriber from the subscribers table.
     * This method will delete all subscribers found with the given
     * UserID (there should only be one, but just in case).
     */
    private boolean deleteSubscriber(String userId, Connection conn)
            throws SQLException {
        boolean success = false;
        if (userId == null || conn == null) {
            return false;
        }
        try {
            StringBuffer sql = new StringBuffer();
            // Use DELETE statement
            sql.append("DELETE FROM Subscribers WHERE UserID = ?");
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            sqlCommandBean.setSqlValue(sql.toString());
            Vector values = new Vector();
            values.addElement(new StringValue(userId));

            sqlCommandBean.setValues(values);
            sqlCommandBean.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }

    /**
     * Attempts to add a subscriber to the subscribers table.
     * This method will insert a subscriber regardless of whether or not
     * it already exists in the tabel.
     */
    private boolean addSubscriber(String userId, Connection conn) {
        boolean success = false;
        if (userId == null || conn == null) {
            return false;
        }
        try {
            StringBuffer sql = new StringBuffer();
            // Use INSERT statement
            sql.append("INSERT INTO Subscribers (UserID) VALUES(?)");
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            sqlCommandBean.setSqlValue(sql.toString());
            Vector values = new Vector();
            values.addElement(new StringValue(userId));

            sqlCommandBean.setValues(values);
            sqlCommandBean.executeUpdate();
            success = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }

    /**
     * If the given userId is found in the subscriber table, then
     * its corresponding Row is returned.  Otherwise, null is returned
     * if no subscriber is found.  In case of an error, an SQLException
     * is throw.
     */
    private Row getSubscriber(String userId, Connection conn)
            throws SQLException {
        if (userId == null || conn == null) {
            return null;
        }

        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        String sql = "SELECT * FROM Subscribers WHERE UserID = ?";
        sqlCommandBean.setSqlValue(sql);
        Vector values = new Vector();
        values.addElement(new StringValue(userId));
        sqlCommandBean.setValues(values);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        } catch (UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        } // Can not happen here

        if (rows == null || rows.size() == 0) {
            // User not found
            return null;
        }
        return (Row) rows.firstElement();
    }

    /**
     * Looks up all entries in the Users table with a true value for the
     * administrator setting.  Returns an ArrayList of Strings containing
     * the email addresses of the found users.
     */
    public ArrayList getAdminEmailAddresses() {
        //Get the useraddresses from the database
        Connection conn = null;
        ArrayList addresses = null;
        try {
            conn = dataSource.getConnection();
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            String sql = "SELECT Email FROM Users WHERE Administrator = ?";
            sqlCommandBean.setSqlValue(sql);
            Vector values = new Vector();
            values.addElement(new BooleanValue(true));
            sqlCommandBean.setValues(values);
            Vector rows = null;
            String name;
            Object[] rowArray;
            try {
                rows = sqlCommandBean.executeQuery();
                if (rows != null && rows.size() > 0) {
                    addresses = new ArrayList();
                    rowArray = rows.toArray();
                    for (int rowIndex = 0; rowIndex < rowArray.length; rowIndex++) {
                        name = ((Row) rowArray[rowIndex]).getString("Email");
                        addresses.add(name);
                    }
                }
            } catch (UnsupportedTypeException e) {
                throw new SQLException(e.toString());
            } catch (NoSuchColumnException e2) {
                throw new SQLException(e2.toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            addresses = null;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            } // Ignore
        }
        return addresses;
    }

    /**
     * This method gets all of the usernames corresponding to user ids
     * found in the subscribers table.
     * Returns an ArrayList of strings.
     */
    public ArrayList getAllSubscribers() {
        //Get the useraddresses from the database
        Connection conn = null;
        ArrayList users = null;
        try {
            conn = dataSource.getConnection();
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            String sql = "SELECT UserName FROM Users WHERE UserID =some (SELECT UserID FROM Subscribers)";
            sqlCommandBean.setSqlValue(sql);
            Vector rows = null;
            String name;
            Object[] rowArray;
            try {
                rows = sqlCommandBean.executeQuery();
                if (rows != null && rows.size() > 0) {
                    users = new ArrayList();
                    rowArray = rows.toArray();
                    for (int rowIndex = 0; rowIndex < rowArray.length; rowIndex++) {
                        name = ((Row) rowArray[rowIndex]).getString("UserName");
                        users.add(name);
                    }
                }
            } catch (UnsupportedTypeException e) {
                throw new SQLException(e.toString());
            } catch (NoSuchColumnException e2) {
                throw new SQLException(e2.toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            users = null;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            } // Ignore
        }
        return users;
    }

    /**
     * This method gets all of the email addresses found in the users table
     * corresponding to the given usernames.  Only users with email addresses
     * in the table will have a value returned.  If an error occurs, null is
     * returned, other wise a possibly empty (if no user email addresses were
     * found) ArrayList is returned.
     */
    public ArrayList getEmailAddresses(String[] userNames) {
        //Get the useraddresses from the database
        if (userNames == null || userNames.length <= 0) {
            return null;
        }
        Connection conn = null;
        ArrayList addresses = null;
        try {
            conn = dataSource.getConnection();
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            StringBuffer sqlBuffer = new StringBuffer("SELECT Email FROM Users WHERE ");
            sqlBuffer.append("UserName = '" + userNames[0] + "'");
            for (int i = 1; i < userNames.length; i++) {
                sqlBuffer.append(" or UserName = '" + userNames[i] + "'");
            }
            sqlCommandBean.setSqlValue(sqlBuffer.toString());

            Vector rows = null;
            String name;
            Object[] rowArray;
            try {
                rows = sqlCommandBean.executeQuery();
                if (rows != null && rows.size() > 0) {
                    addresses = new ArrayList();
                    rowArray = rows.toArray();
                    for (int rowIndex = 0; rowIndex < rowArray.length; rowIndex++) {
                        name = ((Row) rowArray[rowIndex]).getString("Email");
                        addresses.add(name);
                    }
                }
            } catch (UnsupportedTypeException e) {
                throw new SQLException(e.toString());
            } catch (NoSuchColumnException e2) {
                throw new SQLException(e2.toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            addresses = null;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            } // Ignore
        }
        return addresses;
    }

    /**
     * This method gets all of the userIDs found in the subscribers table and
     * then looks up all the email addresses of those entries from the
     * Users table.
     */
    public ArrayList getSubscriberEmailAddresses() {
        //Get the useraddresses from the database
        Connection conn = null;
        ArrayList addresses = null;
        try {
            conn = dataSource.getConnection();
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            String sql = "SELECT Email FROM Users WHERE UserID =some (SELECT UserID FROM Subscribers)";
            sqlCommandBean.setSqlValue(sql);
            Vector rows = null;
            String name;
            Object[] rowArray;
            try {
                rows = sqlCommandBean.executeQuery();
                if (rows != null && rows.size() > 0) {
                    addresses = new ArrayList();
                    rowArray = rows.toArray();
                    for (int rowIndex = 0; rowIndex < rowArray.length; rowIndex++) {
                        name = ((Row) rowArray[rowIndex]).getString("Email");
                        addresses.add(name);
                    }
                }
            } catch (UnsupportedTypeException e) {
                throw new SQLException(e.toString());
            } catch (NoSuchColumnException e2) {
                throw new SQLException(e2.toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            addresses = null;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            } // Ignore
        }
        return addresses;
    }
}
