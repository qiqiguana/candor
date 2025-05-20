package org.character.data.map;

import java.io.File;

import org.javathena.core.utiles.Functions;

import junit.framework.TestCase;

public class TXTMapPersistenceTest extends TestCase
{
	private final String TEST_DB_PATH_FILE = "testdb/map_index.txt";
	private final String TEST_BACKUP_DB_PATH_FILE = "testdb/map_index_test.txt";
	private MapIndex mi = null;
	public void setUp()
	{
		mi = new MapIndex();
		TXTMapPersistence tmp = new TXTMapPersistence();
		Functions.copyfile(TEST_BACKUP_DB_PATH_FILE, TEST_DB_PATH_FILE);
		tmp.setFileDB(TEST_DB_PATH_FILE);
		mi.setPersistenceMethod(tmp);
	}
	
	public void tearDown()
	{
		new File(TEST_DB_PATH_FILE).delete();
	}
	
	public void testSave()
	{
		mi.load();
		mi.save();
	}

	public void testLoad()
	{
		mi.load();
	}
}
