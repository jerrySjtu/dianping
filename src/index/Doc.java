package index;

public class Doc {
	private String classID;
	private String content;
	
	public Doc(String classID, String content) {
		this.classID = classID;
		this.content = content;
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
		return classID + ":" + content;
	}
	
}
