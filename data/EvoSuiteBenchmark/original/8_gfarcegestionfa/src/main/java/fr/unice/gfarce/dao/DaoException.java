package fr.unice.gfarce.dao;

public class DaoException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	
	 public DaoException(int code) {
		 super();
		 this.setCode(code);
	 }
	
	 public DaoException(String message, int code) {
		 super(message);
		 this.setCode(code);
	 }

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
