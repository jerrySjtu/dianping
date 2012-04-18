package search;

import index.Doc;
import index.IndexDAO;
import index.Indexer;
import index.InvertedIndex;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimUtil {
	//there can be different kinds of indexes
	private InvertedIndex index;
	private Query query;
	private Doc doc;
	
	public SimUtil(Query query, Doc doc, InvertedIndex index) {
		this.query = query;
		this.doc = doc;
		this.index = index;
	}
	
//	public static void main(String[] args) throws SQLException {
//		List<Map.Entry<String, Double>> list = sortKeyByIdf();
//		for(int i = list.size()-1; i >= 0; i--)
//			System.out.println(list.get(i));
//	}
	
	public double getQueryDocSim() {
		Map<String, Integer> queryMap = getQueryVector();
		Map<String, Double> docMap = getDocVector();
		Set<String> keyset = queryMap.keySet();
		Iterator<String> iterator = keyset.iterator();
		double product = 0;
		double length1 = 0;
		double length2 = 0;
		while(iterator.hasNext()) {
			String key = iterator.next();
			length1 += queryMap.get(key) * queryMap.get(key);
			if(docMap.containsKey(key))
				product += queryMap.get(key) * docMap.get(key);
		}
		iterator = docMap.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			length2 += docMap.get(key) + docMap.get(key);
		}
		return product / (Math.sqrt(length1) * Math.sqrt(length2));
	}
	
	private Map<String, Integer> getQueryVector() {
		return query.getTfMap();
	}
	
	private Map<String, Double> getDocVector() {
		Map<String, Integer> tfMap = doc.getTFMap();
		Map<String, Double> idfMap = index.getIdfMap();
		Map<String, Double> tfIdfMap = new HashMap<String, Double>();
		Set<String> keyset = tfMap.keySet();
		Iterator<String> iterator = keyset.iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			double value = tfMap.get(key) * idfMap.get(key);
			tfIdfMap.put(key, value);
		}
		return tfIdfMap;
	}
	
	public List<Map.Entry<String, Double>> sortKeyByIdf() {
		Map<String, Double> idfMap = index.getIdfMap();
		List<Map.Entry<String, Double>> list =
		    new ArrayList<Map.Entry<String, Double>>(idfMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {   
		    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {      
		        double value1 = o1.getValue().doubleValue();
		        double value2 = o2.getValue().doubleValue();
		        if(value1 < value2)
		        	return -1;
		        else if(value1 == value2)
		        	return 0;
		        return 1;
		    }
		}); 
		return list;
	}
	

}
