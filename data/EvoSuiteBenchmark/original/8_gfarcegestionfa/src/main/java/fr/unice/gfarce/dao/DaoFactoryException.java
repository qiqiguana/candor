package fr.unice.gfarce.dao;

public class DaoFactoryException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	public DaoFactoryException(int code) {
		 super();
		 this.setCode(code);
	 }
	
	public DaoFactoryException(){
		super();
	}
	
	 public DaoFactoryException(String message, int code) {
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
