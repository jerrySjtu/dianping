package index;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;


public class InvertedIndex {
	//TODO the data structure should be improved
	private Map<String, List<PostElement>> index;
	
	public InvertedIndex(int initSize) {
		index = new HashMap<String, List<PostElement>>(initSize);
	}
	
	public static void main(String[] args) {
		InvertedIndex index = new InvertedIndex(50);
		index.put("中国", new PostElement(2, 1));
		index.put("中国", new PostElement(2, 9));
		index.put("中国", new PostElement(30, 1));
		index.put("日本", new PostElement(1, 1));
		List<PostElement> postList = index.getPostList("中国");
		System.out.println(postList);
	}
	
	public int size() {
		return index.size();
	}
	
	public void put(String key, PostElement element) {
		PostElement oldElement = null;
		List<PostElement> postList = index.get(key);
		//the key word is not in the index
		if(postList == null) {
			postList = new LinkedList<PostElement>();
			postList.add(element);
			index.put(key, postList);
		}
		else {
			oldElement = getPostElement(postList, element.getDocID());
			//the key word appears in the document firstly
			if(oldElement == null)
				postList.add(element);
			else
				oldElement.setFrequency(oldElement.getFrequency() + element.getFrequency());
		}
	}
	
	public List<PostElement> getPostList(String key) {
		return index.get(key);
	}
	
	private PostElement getPostElement(List<PostElement> postList, int docID) {
		if(postList == null)
			return null;
		else {
			Iterator<PostElement> iterator = postList.iterator();
			while(iterator.hasNext()) {
				PostElement element = iterator.next();
				if(element.getDocID() == docID) 
					return element;
			}
		}
		return null;
	}

}
