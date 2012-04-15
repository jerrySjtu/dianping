package index;

public class ClassInfo {
	private String id;
	private String des;
	
	public ClassInfo(String id, String des) {
		this.id = id;
		this.des = des;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
	
	@Override
	public String toString() {
		return id + ":" + des;
	}

}
