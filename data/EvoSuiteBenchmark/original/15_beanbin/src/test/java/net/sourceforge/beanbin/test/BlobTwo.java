package net.sourceforge.beanbin.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;

import net.sourceforge.beanbin.annotations.blob.Blob;
import net.sourceforge.beanbin.annotations.blob.In;
import net.sourceforge.beanbin.annotations.blob.Out;

@Blob
public class BlobTwo implements Serializable {
	private static final long serialVersionUID = 8513145532834173982L;
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Out
	public InputStream getInputStream() {
		return new ByteArrayInputStream(getValue().getBytes());
	}
	
	@In
	public void setInputStream(InputStream is) throws IOException {
		InputStreamReader reader = new InputStreamReader(is);
		StringWriter writer = new StringWriter();
		int c = -1;
		while((c = reader.read()) != -1) {
			writer.write(c);
		}
		setValue(writer.toString());
	}
}