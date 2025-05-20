/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package osa.ora.server.beans;

import java.io.Serializable;

/**
 *
 * @author ooransa
 */
public class LoginBean implements Serializable {
    private User user;
    private String secureToken;
    private String authToken;
    public LoginBean(){

    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the secureToken
     */
    public String getSecureToken() {
        return secureToken;
    }

    /**
     * @param secureToken the secureToken to set
     */
    public void setSecureToken(String secureToken) {
        this.secureToken = secureToken;
    }

    /**
     * @return the authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * @param authToken the authToken to set
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
