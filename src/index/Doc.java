package index;

import java.util.HashMap;
import java.util.Map;

public class Doc {
	private int id;
	private String classID;
	private String content;
	private Map<String, Integer> wordMap;
	
	public Doc(int id, String classID, String content) {
		this.id = id;
		this.classID = classID;
		this.content = content;
	}
	
	public Map<String, Integer> getTFMap() {
		if (wordMap == null) {
			wordMap = new HashMap<String, Integer>();
			// segament the document
			String[] wordArray = Segmenter.segment(content);
			// calculate the term frequency
			for (int i = 0; i < wordArray.length; i++) {
				// the word is in Chinese
				if (EncodeUtil.isChinese(wordArray[i])) {
					if (wordMap.containsKey(wordArray[i])) {
						int count = wordMap.get(wordArray[i]) + 1;
						wordMap.put(wordArray[i], count);
					} else
						wordMap.put(wordArray[i], 1);
				}
			}
		}
		return wordMap;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return id + "," + classID + "," + content;
	}
	
}
