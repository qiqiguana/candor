package org.character.data.map;

import java.io.File;

import org.javathena.core.utiles.Functions;

import junit.framework.TestCase;

public class XMLMapPersistenceTest extends TestCase
{
	private final String TEST_DB_PATH_FILE = "testdb/map_index.xml";
	private final String TEST_BACKUP_DB_PATH_FILE = "testdb/map_index_test.xml";
	private MapIndex mi = null;

	public void setUp()
	{
		mi = new MapIndex();
		XMLMapPersistence tmp = new XMLMapPersistence();
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
		mi.save();
	}

	public void testLoad()
	{
		mi.load();
	}

}
