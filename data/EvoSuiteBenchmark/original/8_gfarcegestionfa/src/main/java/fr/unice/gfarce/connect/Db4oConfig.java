package fr.unice.gfarce.connect;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;


public class Db4oConfig {

	public static String getInfo() throws IOException{
		Properties props = new Properties();
		URL urlFichierProp = new File("db4oconfig.txt").toURI().toURL();
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(urlFichierProp.openStream());
			props.load(bis);
			return props.getProperty("base");
		}
		finally {
			if (bis != null) {
				bis.close();
			}
		}
	}
	
	public static void setInfo(String nombase) throws IOException{
		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File("db4oconfig.txt").getPath());
			props.load(fis);
			props.setProperty("base",nombase);
			FileOutputStream fos = new FileOutputStream(new File("db4oconfig.txt").getPath());
			props.store(fos,null);
		}
		finally {
			if (fis != null) {
				fis.close();
			}
		}
	}
}
