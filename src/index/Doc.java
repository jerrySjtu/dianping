package index;

public class Doc {
	private int id;
	private String classID;
	private String content;
	
	public Doc(int id, String classID, String content) {
		this.id = id;
		this.classID = classID;
		this.content = content;
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
