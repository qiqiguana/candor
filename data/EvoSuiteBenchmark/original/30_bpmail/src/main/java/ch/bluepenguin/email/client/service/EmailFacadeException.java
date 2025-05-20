/*
 * Created on 16.05.2005
 *
 */
package ch.bluepenguin.email.client.service;

/**
 * all purpose exception for email facade
 *
 */
public class EmailFacadeException extends Exception {
	private Exception technicalException;
	/**
	 * @return Returns the technicalException.
	 */
	public Exception getTechnicalException() {
		return technicalException;
	}
	/**
	 * @param technicalException The technicalException to set.
	 */
	public void setTechnicalException(Exception technicalException) {
		this.technicalException = technicalException;
	}
}
