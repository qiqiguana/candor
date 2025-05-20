package net.sourceforge.beanbin;

public class BeanBinException extends Exception {
	private static final long serialVersionUID = 7533186047270256316L;
	
	public BeanBinException(String string) {
		super(string);
	}
	
	public BeanBinException(String string, Throwable e) {
		super(string, e);
	}
}
