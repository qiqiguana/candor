package net.sourceforge.beanbin.search.index.cache;

public class IndexCacheSingleton {
	private static IndexCache cache;
	
	public static IndexCache getInstance() {
		if(cache == null) {
			cache = new IndexCache();
		}
		return cache;
	}
}