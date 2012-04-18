package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class Test {
	public static void main(String[] args) {
		testCache();
	}
	
	private static void testCache() {
		CacheManager manager = CacheManager.create(); 
		 String names[] = manager.getCacheNames();  
         for(int i=0;i<names.length;i++){  
             System.out.println(names[i]);  
         }  
         Cache cache =  manager.getCache("dianping");
         Element element = new Element("hello", new Double(12));
         cache.put(element);
         Element e = cache.get("f");
         System.out.println(e);
	}
	
	private static void testMap() {
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		map.put(1, 3.0);
		map.put(2, -1.0);
		map.put(3, 5.0);
		List<Map.Entry<Integer, Double>> infoIds =
		    new ArrayList<Map.Entry<Integer, Double>>(map.entrySet());
		System.out.println(infoIds);
		Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Double>>() {   
		    public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {      
		        double value1 = o1.getValue().doubleValue();
		        double value2 = o2.getValue().doubleValue();
		        if(value1 < value2)
		        	return -1;
		        else if(value1 == value2)
		        	return 0;
		        return 1;
		    }
		}); 
		System.out.println(infoIds);
	}
	
}
