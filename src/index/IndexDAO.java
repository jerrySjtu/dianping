package index;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


public class IndexDAO {
	private static Connection conn;
	
	public static void main(String[] args) throws Exception {
		Doc doc = getDoc(2);
		System.out.println(doc);
	}
	
	public static LinkedList<ClassInfo> getAllClasses() throws SQLException {
		String classTable = PropertyUitl.getProperty("classTable");
		String sql = "select classID,des from " + classTable;
		Connection conn = getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		LinkedList<ClassInfo> list = new LinkedList<ClassInfo>();
		ResultSet resultset = statement.executeQuery();
		while(resultset.next()) {
			ClassInfo info = new ClassInfo(resultset.getString(1), resultset.getString(2));
			list.add(info);
		}
		resultset.close();
		statement.close();
		return list;
	}
	
	public static ClassInfo getClassInfo(String classID) {
		
	}
	
	public static Doc getDoc(int id) throws SQLException,
			UnsupportedEncodingException {
		String docTable = PropertyUitl.getProperty("docTable");
		String sql = "select classID,content from " + docTable + " where id=?;";
		Connection conn = getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet resultset = statement.executeQuery();
		Doc doc = null;
		if (resultset.next()) {
			String content = new String(resultset.getString(2).getBytes(),"GBK");
			doc = new Doc(resultset.getString(1), content);
			resultset.close();
			statement.close();
		}
		return doc;
	}
	
	
	
	public static boolean storeDoc(Doc doc) throws SQLException {
		String docTable = PropertyUitl.getProperty("docTable");
		String sql = "insert into " + docTable + "(classID,content) values(?,?);";
		Connection conn = getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, doc.getClassID());
		statement.setString(2, doc.getContent());
		boolean flag = statement.execute();
		statement.close();
		return flag;
	}
	
	public static boolean storeClass(ClassInfo info) throws SQLException {
		String classTable = PropertyUitl.getProperty("classTable");
		String sql = "insert into " + classTable + "(classID,des) values(?,?);";
		Connection conn = getConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, info.getId());
		statement.setString(2, info.getDes());
		boolean flag = statement.execute();
		statement.close();
		return flag;
	}
	
	private static Connection getConnection() {
		if (conn == null) {
			String connStr = "jdbc:mysql://localhost/"; 
			connStr += PropertyUitl.getProperty("database") + "?";
			connStr += "user=" + PropertyUitl.getProperty("user");
			connStr += "&password=" + PropertyUitl.getProperty("password");
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(connStr); 
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}// end if
		return conn;
	}

}
