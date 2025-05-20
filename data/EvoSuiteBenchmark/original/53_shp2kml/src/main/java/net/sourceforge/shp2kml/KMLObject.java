package net.sourceforge.shp2kml;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.net.URL;

public class KMLObject {
	private String placemarks = "";

	private String KML = "";

	private FileWriter fwr;

	public KMLObject(String file_path) {
		this.KML = file_path;
	}

	public boolean StartWrite() {
		boolean retval = false;
		try {
			this.fwr = new FileWriter(this.KML);
			this.fwr
					.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><kml xmlns=\"http://earth.google.com/kml/2.0\"><Document>\n");
			retval = true;
		} catch (Exception ex) {
			System.err.print("Unable to create KML file");
		}
		return retval;
	}

	public boolean FinishWrite() {
		boolean retval = false;
		try {
			this.fwr.write("\n</Document></kml>");
			this.fwr.close();
			retval = true;
		} catch (Exception ex) {
			System.err.print("Unable to write the KML footer.");
		}
		return retval;
	}

	public boolean addPlacemark(String place) {
		boolean retval = false;
		try {
			this.fwr.write('\n');
			this.fwr.write(place);
			retval = true;
		} catch (Exception ex) {
			System.err.print("Unable to write placemark");
		}
		return retval;
	}
}
