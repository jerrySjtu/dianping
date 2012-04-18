package search;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import index.Doc;
import index.IndexDAO;
import index.Indexer;
import index.InvertedIndex;
import index.PostElement;

public class Search {
	private Query query;
	private InvertedIndex index;
	
	public Search(Query query) throws SQLException {
		this.query = query;
		this.index = Indexer.getIndex();
	}
	
//	public static void main(String[] args) throws SQLException {
//		Query query = new Query("中国人是好样的汽车");
//		Search search = new Search(query);
//		List<SortElement> list = search.search();
//		System.out.println(list);
//	}
	
	public List<Integer> search() throws SQLException {
		List<Integer> resultList = new LinkedList<Integer>();
		List<SortElement> docList = rankBySim();
		for(int i = docList.size()-1; i >= 0; i--)
			resultList.add(docList.get(i).getKey());
		return resultList;
	}
	
	private List<SortElement> rankBySim() throws SQLException {
		Set<Integer> docSet = candidateDocs();
		Iterator<Integer> iterator = docSet.iterator();
		ArrayList<SortElement> array = new ArrayList<SortElement>();
		while(iterator.hasNext()) {
			int docID = iterator.next();
			Doc doc = IndexDAO.getDoc(docID);
			SimUtil simUtil = new SimUtil(query, doc, index);
			double sim = simUtil.getQueryDocSim();
			SortElement element = new SortElement(docID, sim);
			array.add(element);
		}
		Collections.sort(array);
		return array;
	}
	
	private Set<Integer> candidateDocs() {
		Set<Integer> docSet = new HashSet<Integer>();
		List<String> termList = query.getTermList();
		Iterator<String> tIterator = termList.iterator();
		while (tIterator.hasNext()) {
			String key = tIterator.next();
			List<PostElement> postList = index.getPostList(key);
			if (postList != null) {
				Iterator<PostElement> pIterator = postList.iterator();
				while (pIterator.hasNext()) {
					PostElement element = pIterator.next();
					docSet.add(element.getDocID());
				}
			}
		}
		return docSet;
	}

}
