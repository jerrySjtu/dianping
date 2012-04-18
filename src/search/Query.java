package search;

import index.EncodeUtil;
import index.Segmenter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Query {
	private String query;
	private Map<String, Integer> tfMap;
	
	public Query(String query) {
		this.query = query;
	}
	
	public Map<String, Integer> getTfMap() {
		if (tfMap == null) {
			String[] array = Segmenter.segment(query);
			tfMap = new HashMap<String, Integer>();
			for (int i = 0; i < array.length; i++) {
				// the word is in Chinese
				if (EncodeUtil.isChinese(array[i])) {
					if (tfMap.containsKey(array[i])) {
						int value = tfMap.get(array[i]) + 1;
						tfMap.put(array[i], value);
					} else
						tfMap.put(array[i], 1);
				}
			}
		}
		return tfMap;
	}
	
	public List<String> getTermList() {
		List<String> list = new LinkedList<String>();
		Set<String> keyset = getTfMap().keySet();
		Iterator<String> iterator = keyset.iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			if(EncodeUtil.isChinese(key))
				list.add(key);
		}
		return list;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	@Override
	public String toString() {
		return query;
	}

}
