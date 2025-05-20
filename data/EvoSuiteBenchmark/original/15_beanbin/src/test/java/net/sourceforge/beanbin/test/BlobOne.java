package net.sourceforge.beanbin.test;

import java.io.Serializable;

import net.sourceforge.beanbin.annotations.blob.Blob;

@Blob
public class BlobOne implements Serializable {
	private static final long serialVersionUID = -4346788304666999460L;
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}