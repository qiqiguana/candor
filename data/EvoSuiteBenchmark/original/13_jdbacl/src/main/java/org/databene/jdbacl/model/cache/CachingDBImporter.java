/*
 * (c) Copyright 2010-2011 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.jdbacl.model.cache;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import org.databene.commons.ConnectFailedException;
import org.databene.commons.FileUtil;
import org.databene.commons.ImportFailedException;
import org.databene.commons.Period;
import org.databene.commons.SystemInfo;
import org.databene.jdbacl.model.DBMetaDataImporter;
import org.databene.jdbacl.model.Database;
import org.databene.jdbacl.model.xml.XMLModelExporter;
import org.databene.jdbacl.model.xml.XMLModelImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link DBMetaDataImporter} that acts as a proxy to another DBMetaDataImporter, 
 * adding the feature of caching its output. The data file is named '&lt;environment&gt;.meta.xml'
 * and expires after 12 hrs.<br/><br/>
 * Created: 10.01.2011 14:48:00
 * @since 0.6.5
 * @author Volker Bergmann
 */
public class CachingDBImporter implements DBMetaDataImporter, Closeable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CachingDBImporter.class);
	
	private static final String CACHE_FILE_SUFFIX = ".meta.xml";
	private static final long TIME_TO_LIVE = Period.HOUR.getMillis() * 12;
	
	protected DBMetaDataImporter realImporter;
	protected String environment;
	
	public CachingDBImporter(DBMetaDataImporter realImporter, String environment) {
		this.realImporter = realImporter;
		this.environment = environment;
	}

	public Database importDatabase() throws ConnectFailedException, ImportFailedException {
		File file = getCacheFile();
		long now = System.currentTimeMillis();
		if (file.exists() && now - file.lastModified() < TIME_TO_LIVE)
			return readCachedData(file);
		else
			return importFreshData(file);
	}

	public void close() throws IOException {
		if (realImporter instanceof Closeable)
			((Closeable) realImporter).close();
	}
	
	public static File getCacheFile(String environment) {
		String SEP = File.separator;
		String cacheDirectory = SystemInfo.getUserHome() + SEP + "databene" + SEP + "cache";
		return new File(cacheDirectory, environment + CACHE_FILE_SUFFIX);
	}
	
	// non-public helpers ----------------------------------------------------------------------------------------------

	protected File getCacheFile() {
		return getCacheFile(environment);
	}
	
	protected Database readCachedData(File cacheFile) throws ConnectFailedException, ImportFailedException {
		LOGGER.info("Reading cached database meta data from file " + cacheFile.getPath());
		try {
			return new XMLModelImporter(cacheFile).importDatabase();
		} catch (Exception e) {
			LOGGER.info("Error reading cache file, reparsing database", e);
			return importFreshData(cacheFile);
		}
	}

	protected Database importFreshData(File file) throws ConnectFailedException, ImportFailedException {
		Database database = realImporter.importDatabase();
		LOGGER.info("Reading and exporting Database meta data to cache file");
		try {
			FileUtil.ensureDirectoryExists(file.getParentFile());
			new XMLModelExporter(file).export(database);
			LOGGER.debug("Database meta data export completed");
		} catch (Exception e) {
			LOGGER.error("Error writing database meta data file " + file + ": " + e.getMessage());
		}
		return database;
	}

}
