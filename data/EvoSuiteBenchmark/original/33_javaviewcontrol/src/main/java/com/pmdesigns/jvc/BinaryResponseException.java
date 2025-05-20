package com.pmdesigns.jvc;

import java.io.InputStream;


/**
 * This exception can be thrown during request processing to
 * allow binary data to be returned instead of a string.
 */
public final class BinaryResponseException extends RuntimeException {

	public final InputStream in;
	
	public BinaryResponseException(InputStream in) {
		this.in = in;
	}
}
