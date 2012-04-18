package index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Indexer {
	private static InvertedIndex index;

//	public static void main(String[] args) throws Exception {
//		InvertedIndex index = getIndex();
//		System.out.println(index.size());
//		System.out.println(index.getIdfMap().size());
//	}
//	
	public static InvertedIndex getIndex() throws SQLException {
		if(index == null) {
			index = new InvertedIndex(4000);
			//get the id of all documents
			List<Integer> docList = IndexDAO.getAllDocs();
			index.setDocNum(docList.size());
			Iterator<Integer> iterator = docList.iterator();
			while(iterator.hasNext()) {
				int docID = iterator.next();
				//get a document
				Doc doc = IndexDAO.getDoc(docID);
				//parse the document
				parseDoc(doc);
			}
			//update the idf map
			index.updateIdfMap();
		}
		return index;
	}
	
	private static void parseDoc(Doc doc) {
		//segament the document
		Map<String, Integer> wordMap = doc.getTFMap();
		//insert the information into the index
		Set<String> keySet = wordMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while(iterator.hasNext()) {
			String word = iterator.next();
			PostElement element = new PostElement(doc.getId(), wordMap.get(word));
			index.put(word, element);
		}
	}

	private static void storeInfo() throws IOException, SQLException {
		String baseDir = PropertyUitl.getProperty("baseDir");
		String classFileName = PropertyUitl.getProperty("classFile");
		String sampleDir = PropertyUitl.getProperty("sampleDir"); 
		File classFile = new File(baseDir + "/" + classFileName); 
		FileInputStream in = new FileInputStream(classFile);
		InputStreamReader sr = new InputStreamReader(in, "GBK");
		BufferedReader bw = new BufferedReader(sr);
		String line;
		while((line = bw.readLine()) != null) {
			//strs[0] is the class id and strs[1] is the description
			//line = new String(line.getBytes(), "utf-8");
			System.out.println(line);
			String[] strs = line.split("\t");
			ClassInfo info = new ClassInfo(strs[0], strs[1]);
			IndexDAO.storeClass(info);
			//get all the documents under each class
			String dirPath = sampleDir + "\\" + strs[0];
			System.out.println(dirPath);
			File dir = new File(dirPath);
			File[] files = dir.listFiles();
			for(int i = 0; i < files.length; i++) {
				String content = readDoc(files[i]);
				Doc doc = new Doc(-1, strs[0], content);
				IndexDAO.storeDoc(doc);
			}
		}
	}
	
	private static String readDoc(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		InputStreamReader sr = new InputStreamReader(in, "GBK");
		BufferedReader bw = new BufferedReader(sr);
		String line;
		StringBuffer buffer = new StringBuffer();
		while((line = bw.readLine()) != null) 
			buffer.append(line + "\n");
		return buffer.toString();
	}


}
