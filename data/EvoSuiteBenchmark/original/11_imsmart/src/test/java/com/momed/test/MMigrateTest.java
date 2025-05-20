package com.momed.test;

import java.io.File;

import com.momed.main.MImageMigrator;

public class MMigrateTest
{

	public static final String IMAGE_DIR = "C:/Documents and Settings/gowerdh/My Documents/Infocrossing/Migration/sample_data/Graphics/Auto_Claims";

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		//MProperties properties = MProperties.getInstance();

		int threadCount = 3;
		File imageDir = new File(IMAGE_DIR);
		//String files[] = imageDir.list();
		//MImageMigrater.setFileListToMigrate(files);
		MImageMigrator.setDirectoryToMigrate(imageDir);
		for (int i = 0; i < threadCount; i++)
		{
			MImageMigrator imageMigrationThread = new MImageMigrator("Thread_"+i);

			imageMigrationThread.start();
		}
	}
}
