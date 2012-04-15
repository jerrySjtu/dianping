package index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Indexer {

	public static void main(String[] args) throws Exception {
		storeInfo();
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
			line = new String(line.getBytes(), "utf-8");
			String[] strs = line.split("\t");
			ClassInfo info = new ClassInfo(strs[0], strs[1]);
			IndexDAO.storeClass(info);
			//get all the documents under each class
			String dirPath = sampleDir + "/" + strs[0];
			File dir = new File(dirPath);
			File[] files = dir.listFiles();
			for(int i = 0; i < files.length; i++) {
				String content = readDoc(files[i]);
				Doc doc = new Doc(strs[0], content);
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
