package net.sourceforge.shp2kml;

import java.net.URL;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.Feature;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import com.vividsolutions.jts.geom.Geometry;

public class Converter {

	public static void main(String[] args) {
		/*
		 * for testing purposes, swap out the "default args" with "args2"
		 */
		//String[] args2 = new String[1];
		//args2[0] = "file:///Users/atheken/Documents/test_data/MA_Data/sn25_d00_Project.shp";
		//args = args2;
		switch (args.length){
		case 0:
			Thread trd = new Thread(new Shp2KMLGUI());
			trd.start();
		break;
		default:
			System.out.println("Usage: java -jar ./shp2kml.jar shapefile_name.shp");
			Converter.convertShp(args[0]);
		break;
		}
	
		}	

	public static boolean convertShp(String shpUrl)
	{
		boolean retval = false;
		try {
			URL u = new URL(shpUrl);
			ShapefileDataStore sfds = new ShapefileDataStore(u);
			long start = System.currentTimeMillis();
			FeatureCollection fc = sfds.getFeatureSource().getFeatures();
			FeatureIterator fi = fc.features();
			Feature currFeature;
			Geometry currGeometry;
			int readcount = 0;
			
			KMLObject outObject = new KMLObject(u.getFile() + ".kml");
			outObject.StartWrite();
			while (fi.hasNext()) {
				readcount++;
				currFeature = fi.next();
				int featCount = currFeature.getNumberOfAttributes();
				
				for(int i = 0; i < featCount; i++)
				{
					System.out.print(currFeature.getAttribute(i));
				}
				System.out.println(currFeature.getAttribute(1));
				currGeometry = currFeature.getDefaultGeometry();
				outObject.addPlacemark(GeomConverter
						.getPlacemarkKML(currGeometry));
				
			}
			outObject.FinishWrite();
			System.out
					.println("Read "
							+ readcount
							+ " geometries in "
							+ ((double) (System.currentTimeMillis() - start) / 1000)
							+ " seconds.");

			fi.close();
			retval = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			retval = false;
		}
		return retval;
	}
}
