package net.sourceforge.shp2kml;

import java.util.ArrayList;
import com.vividsolutions.jts.algorithm.*;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

public class GeomConverter {
	public static String convertPoint(Coordinate[] coords) {
		return GeomConverter.convertPointWithAltitude(coords, 0);
	}

	public static String convertLine(Coordinate[] coords) {
		return GeomConverter.convertLineWithAltitude(coords, 0);
	}

	public static String convertPolygon(Coordinate[] coords) {
		return GeomConverter.convertPolygonWithAltitude(coords, 0);
	}

	public static String convertPolygonWithAltitude(Coordinate[] coords,
			double altitude) {
		String str = "<Polygon><extrude>" + altitude
				+ "</extrude><altitudeMode>relativeToGround</altitudeMode>";
		ArrayList coordsTemp = new ArrayList();
		int indexOfFirst = 0;
		int currentIndex = 0;
		while (currentIndex < coords.length) {
			coordsTemp.add(coords[currentIndex]);
			if (indexOfFirst != currentIndex
					&& Math.abs(indexOfFirst - currentIndex) > 2
					&& coords[currentIndex].equals(coords[indexOfFirst])) {
				str += GeomConverter.getLinearRingKML((Coordinate[]) coordsTemp
						.toArray(new Coordinate[0]));
				coordsTemp.clear();
				indexOfFirst = currentIndex + 1;
			}
			currentIndex++;
		}
		str += "</Polygon>";
		return str;
	}

	public static String convertLineWithAltitude(Coordinate[] coords,
			double altitude) {
		String str = "<LineString><extrude>" + altitude
				+ "</extrude><altitudeMode>relativeToGround</altitudeMode>";
		str += GeomConverter.getCoordinatesKML(coords);
		str += "</LineString>";
		return str;
	}

	public static String convertPointWithAltitude(Coordinate[] coords,
			double altitude) {
		String str = "<Point><extrude>" + altitude
				+ "</extrude><altitudeMode>relativeToGround</altitudeMode>";
		str += GeomConverter.getCoordinatesKML(coords);
		str += "</Point>";
		return str;
	}

	public static String getLinearRingKML(Coordinate[] coords) {
		String str = "";
		str += GeomConverter.getCoordinatesKML(coords);

		str = "<LinearRing>" + str + "</LinearRing>";
		// TODO make sure this isn't inverted
		if (!CGAlgorithms.isCCW(coords)) {
			str = "<outerBoundaryIs>" + str + "</outerBoundaryIs>";
		} else {
			str = "<innerBoundaryIs>" + str + "</innerBoundaryIs>";
		}
		return str;
	}

	public static String getCoordinatesKML(Coordinate[] coords) {
		String str = "<coordinates>";

		for (int i = 0; i < coords.length; i++) {
			str += coords[i].x + "," + coords[i].y + ",0 ";
		}
		str += "</coordinates>";
		return str;
	}

	public static String getPlacemarkKML(Geometry geom, String name,
			String description, double altitude) {
		Coordinate[] coords = geom.getCoordinates();
		String str = "<Placemark><description><![CDATA[" + description
				+ "]]></description><name>" + name + "</name>";

		switch (geom.getDimension()) {
		case 0:
			// handle a point geometry
			str += GeomConverter.convertPointWithAltitude(coords, altitude);
			break;
		case 1:
			// handle a line geometry
			str += GeomConverter.convertLineWithAltitude(coords, altitude);
			break;
		case 2:
			// handle a polygon geometry
			str += GeomConverter.convertPolygonWithAltitude(coords, altitude);
			break;
		}
		str += "</Placemark>";
		return str;
	}

	public static String getPlacemarkKML(Geometry geom) {
		return GeomConverter.getPlacemarkKML(geom, "", "", 0);
	}
}
