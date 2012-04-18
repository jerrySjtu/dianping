package test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

public class CacheUtil {
	
	  private final Ehcache cache;
	  public CacheUtil(Ehcache cache)
	  {
	    this.cache = cache;
	  }
	  
	public static void main(String[] args) {
		// Create a CacheManager using defaults
		CacheManager manager = CacheManager.create();
		// Create a Cache specifying its configuration.
		Cache testCache = new Cache(new CacheConfiguration("testCache", 2)
				.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
				.overflowToDisk(true).eternal(false).timeToLiveSeconds(60)
				.timeToIdleSeconds(30).diskPersistent(false)
				.diskExpiryThreadIntervalSeconds(0));
		manager.addCache(testCache);
		String[] names = manager.getCacheNames();
		for(int i = 0; i < names.length; i++)
			System.out.println(names[i]);
		
		Cache cache = manager.getCache("testCache");
		Element element = new Element("key1", "value1");
		cache.put(element);
		System.out.println(cache.get("key1").getValue() );
		manager.shutdown();
	}

	  /* read some data, check cache first, otherwise read from sor */
	  public Object readSomeData(String key) 
	  {
	     Element element = cache.get(key);
	     return element.getValue();
	  }
	  /* write some data, write to sor, then update cache */
	  public void writeSomeData(String key, Object value) 
	  {
	     cache.put(new Element(key, value));
	  }

}
