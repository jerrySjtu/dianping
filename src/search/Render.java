package search;


import java.util.Iterator;
import java.util.List;

import index.Doc;
import index.EncodeUtil;
import index.IndexDAO;

public class Render {
	
	public static void main(String[] args) throws Exception {
		Query query = new Query("Æû³µ");
		Search s = new Search(query);
	 	List<Integer> docList = s.search();
	 	Iterator<Integer> iterator = docList.iterator();
	 	while(iterator.hasNext()) {
	 		int docID = iterator.next();
	 		Doc doc = IndexDAO.getDoc(docID);
	 		String content = Render.render(query, doc);
	 		System.out.println(content + "</br>");
	 	}
	}
	
	public static String render(Query query, Doc doc) {
		String str = getFocus(query, doc);
		//get the keywords
		List<String> strs = query.getTermList();
		String[] keys = new String[strs.size() + 1];
		keys[0] = query.getQuery();
		for(int i = 0; i < strs.size(); i++)
			keys[i + 1] = strs.get(i);
		//color the key words
		StringBuffer buffer = new StringBuffer(str);
		for(int i = 0; i < buffer.length(); i++) {
			for(int j = 0; j < keys.length; j++) {
				//there may be a key word
				int start = i;
				int end = i + keys[j].length();
				if(buffer.charAt(start) == keys[j].charAt(0) &&  (end - 1 < buffer.length())) {
					String temp = buffer.substring(start, end);
					//there is a match
					if(temp.equals(keys[j])) {
						buffer.insert(start, "<font color=red>");
						buffer.insert(end + 16, "</font>");
						i = end + 16 + 6;
						break;
					}
				}
			}//end for
		}//end for
		return buffer.toString();
	}
	
	private static String getFocus(Query query, Doc doc) {
		StringBuffer buffer = new StringBuffer(doc.getContent());
		List<String> keys = query.getTermList();
		for(int i = 0; i < buffer.length(); i++) {
			for(int j = 0; j < keys.size(); j++) {
				//there may be a key word
				int start = i;
				int end = i + keys.get(j).length();
				if(buffer.charAt(start) == keys.get(j).charAt(0) &&  (end - 1 < buffer.length())) {
					String temp = buffer.substring(start, end);
					//there is a match
					if(temp.equals(keys.get(j))) {
						//look backward
						while(EncodeUtil.isChinese(buffer.charAt(start)) && start > 0)
							start--;
						//look 
						while(EncodeUtil.isChinese(buffer.charAt(end)) && end < buffer.length()-1)
							end++;
						return buffer.substring(start+1, end);
					}
				}
			}
		}
		return null;
	}
}
