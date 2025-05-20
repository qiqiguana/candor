package net.sourceforge.beanbin.search.index.lucene;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.configuration.PropertyManager;
import net.sourceforge.beanbin.data.EntityUtils;
import net.sourceforge.beanbin.search.index.IndexEntry;
import net.sourceforge.beanbin.search.index.IndexField;
import net.sourceforge.beanbin.search.index.IndexManager;
import net.sourceforge.beanbin.search.index.IndexUtils;
import net.sourceforge.beanbin.search.index.cache.Properties;
import net.sourceforge.beanbin.search.index.cache.Results;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;

public class LuceneIndexManager implements IndexManager {	
	private String dirPath;

	public LuceneIndexManager() throws BeanBinException {
		this.dirPath = new PropertyManager().getProperty("lucenedir").getValue();
		if(dirPath == null) {
			throw new BeanBinException("Please set the beanbin.lucenedir property in beanbin.properties.");
		}
		if(dirPath.endsWith("/")) {
			dirPath = dirPath.substring(0, dirPath.length());
		}
		File dir = new File(dirPath);
		makeIfDoesntExist(dir);
	}

	public void save(List<IndexEntry> entries) throws BeanBinException {
		try {
			for(IndexEntry entry : entries) {
				save(entry);
			}	
		} catch(Exception e) {
			throw new BeanBinException("LuceneIndexManager error: " + e.getMessage(), e);
		}
	}
	
	private void save(IndexEntry entry) throws IOException, BeanBinException {
		remove(entry.getTargetClass(), entry.getKey());
		for(IndexField field : entry.getFields()) {
			save(entry.getTargetClass(), entry.getKey(), field);
		}
	}
	
	private void save(Class clazz, Object key, IndexField field) throws IOException {
		IndexWriter writer = null;
		try {
			releaseLock(clazz, field.getProperty());
			writer = getWriter(clazz, field.getProperty());
			for(String value : field.getValues()) {
				Document doc = new Document();
				doc.add(new Field("id", key.toString(), Store.YES, Index.UN_TOKENIZED));
				if(value == null) {
					value = "";
				}
				doc.add(new Field("value", value, Store.YES, Index.TOKENIZED));
				writer.addDocument(doc);
			}
		} finally {
			if(writer != null) {
				writer.close();	
			}
		}
	}
	
	private Class getIdType(Class clazz) throws BeanBinException {		
		Class keyClass = EntityUtils.getIdGetter(clazz).getReturnType();
		if(keyClass.isPrimitive()) {
			if(keyClass == long.class) {
				keyClass = Long.class;
			} else if(keyClass == int.class) {
				keyClass = Integer.class;
			} else if(keyClass == float.class) {
				keyClass = Float.class;
			} else if(keyClass == double.class) {
				keyClass = Double.class;
			} else if(keyClass == short.class) {
				keyClass = Short.class;
			} else if(keyClass == byte.class) {
				keyClass = Byte.class;
			} else if(keyClass == char.class) {
				keyClass = Character.class;
			} else if(keyClass == boolean.class) {
				keyClass = Boolean.class;
			}
		}
		
		return keyClass;
	}

	public Set<Object> search(Class clazz, String property, String term) throws BeanBinException {
		Set<Object> keys = new HashSet<Object>();
		IndexSearcher searcher = null;
		try {			
			searcher = getSearcher(clazz, property);
			Class keyType = getIdType(clazz);
			Hits hits = getHits(term, searcher);
			for(int i = 0; i < hits.length(); ++i) {
				Document doc = hits.doc(i);
				String id = doc.get("id");
				keys.add(makeKey(keyType, id));
			}
			return keys;
		} catch (Exception e) {
			throw new BeanBinException("Lucene Search: " + e.getMessage(), e);
		} finally {
			if(searcher != null) {
				try {
					searcher.close();
				} catch (IOException e) {
					throw new BeanBinException("Lucene Search: " + e.getMessage(), e);					
				}
			}
		}
	}
	
	private Object makeKey(Class keyType, String key) throws NoSuchMethodException, BeanBinException {
		Class[] ptypes = new Class[1];
		ptypes[0] = String.class;
		Constructor construct = keyType.getConstructor(ptypes);	
		Object[] params = {key};
		try {
			return construct.newInstance(params);
		} catch (Exception e) {
			throw new BeanBinException("Lucene makeKey: " + e.getMessage(), e);
		}
	}

	public void remove(Class clazz, Object key) throws BeanBinException {		
		try {
			for(String property : IndexUtils.getIndexProperties(clazz)) {
				remove(clazz, property, key);
			}	
		} catch(Exception e) {
			throw new BeanBinException("Lucene Remove: " + e.getMessage(), e);
		}
	}
	
	public void removeAll(Class clazz) throws BeanBinException {
		try {
			for(String property : IndexUtils.getIndexProperties(clazz)) {
				removeAll(clazz, property);
			}
		} catch(Exception e) {
			throw new BeanBinException("Lucene Remove: " + e.getMessage(), e);
		}
	}
	
	private void removeAll(Class clazz, String property) throws IOException {
		if(needsCreation(makePath(clazz, property))) {
			return;
		}
		IndexReader reader = null;
		try {
			releaseLock(clazz, property);
			reader = getReader(clazz, property);
			reader.deleteDocuments(new Term("id", "*"));
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
	}
	
	private void remove(Class clazz, String property, Object key) throws IOException {
		if(needsCreation(makePath(clazz, property))) {
			return;
		}
		IndexReader reader = null;
		try {
			releaseLock(clazz, property);
			reader = getReader(clazz, property);
			reader.deleteDocuments(new Term("id", "" + key));
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
	}
	
	private void releaseLock(Class clazz, String property) throws IOException {
		String path = makePath(clazz, property);
		FSDirectory directory = FSDirectory.getDirectory(path);
		while(IndexReader.isLocked(directory)) {
			IndexReader.unlock(directory);
		}
	}
	
	private Hits getHits(String term, IndexSearcher searcher) throws ParseException, IOException {
		QueryParser parser = new QueryParser("value", new StandardAnalyzer());
		Query query = parser.parse(escape(term));
		Hits hits = searcher.search(query);
		return hits;
	}
	
	private Hits getIdHits(String term, IndexSearcher searcher) throws ParseException, IOException {
		QueryParser parser = new QueryParser("id", new StandardAnalyzer());
		Query query = parser.parse(term);
		Hits hits = searcher.search(query);
		return hits;
	}
	
	private IndexSearcher getSearcher(Class clazz, String property) throws CorruptIndexException, IOException {
		return new IndexSearcher(makePath(clazz, property));
	}
	
	private IndexReader getReader(Class clazz, String property) throws IOException {
		return IndexReader.open(makePath(clazz, property));
	}
	
	private IndexWriter getWriter(Class clazz, String property) throws IOException {
		String path = makePath(clazz, property);
		return new IndexWriter(path, new StandardAnalyzer(), needsCreation(path));
	}
	
	private String makePath(Class clazz, String property) {
		return dirPath + "/" + clazz.getName() + "/" + property;
	}
	
	private boolean needsCreation(String path) {
		File dir = new File(path);
		makeIfDoesntExist(dir);
		for(File file : dir.listFiles()) {
			if(file.getName().equalsIgnoreCase("segments.gen")) {
				return false;
			}
		}
		return true;
	}
	
	private void makeIfDoesntExist(File dir) {
		if(!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	private static String escape(String term) {
		String[] escapeChars = {"\\+", "-", "\\&\\&", "\\|\\|", "!", "\\(", 
			  "\\)", "\\{", "\\}", "\\[", "\\]", "\\^", "\\\\", 
			  "~", "\\?", ":"
		};
		
		for(String value : escapeChars) {
			String string = "\\\\" + value;
			term = term.replaceAll(value, string);
			term = term.replaceAll("\\\\\\\\", "\\\\");
		}
		
		if(term.indexOf("*") == 0) {
			term = term.substring(1);
		}
		
		return term;
	}

	public Results getResults(Class clazz, List<Object> keys) throws BeanBinException {
		try {
			List<String> props = IndexUtils.getSettableIndexProperties(clazz);
			Class idtype = getIdType(clazz);
			
			String term = "";
			for(Object key : keys) {
				term += key + " OR ";
			}
			term = term.substring(0, term.length() - 4);
			Map<String, IndexEntry> entries = new HashMap<String, IndexEntry>();
			for(String prop : props) {				
				IndexSearcher searcher = getSearcher(clazz, prop);
				Hits hits = getIdHits(term, searcher);
				for(int i = 0; i < hits.length(); ++i) {
					Document doc = hits.doc(i);
					String id = doc.get("id");
					String value = doc.get("value");
					IndexEntry entry = entries.get(id);
					if(entry == null) {
						entry = new IndexEntry(clazz, id);
						entries.put(id, entry);
					} 
					
					IndexField field = entry.getField(prop);
					if(field == null) {
						field = new IndexField(prop);
						entry.addField(field);
					}
					field.addValue(value);
				}
			}
			Results results = new Results();
			for(String key : entries.keySet()) {
				IndexEntry entry = entries.get(key);
				Properties properties = new Properties();
				for(IndexField field : entry.getFields()) {
					properties.add(field.getProperty(), field.getValues());
				}
				results.add(makeKey(idtype, key), properties);
			}
			return results;
		} catch(Exception e) {
			throw new BeanBinException("LuceneIndexManager.getResults: " + e.getMessage(), e);
		}
	}

	public List<String> getValues(Class clazz, String property, Object key) throws BeanBinException {
		try {			
			List<String> values = new ArrayList<String>();
			
			IndexSearcher searcher = getSearcher(clazz, property);
			Hits hits = getIdHits(key.toString(), searcher);
			for(int i = 0; i < hits.length(); ++i) {
				Document doc = hits.doc(i);
				values.add(doc.get("value"));			
			}
	
			return values;
		} catch(Exception e) {
			throw new BeanBinException("LuceneIndexManager.getValues: " + e.getMessage(), e);
		}
	}
}
