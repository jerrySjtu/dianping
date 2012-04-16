package index;

public class PostElement {
	private int docID;
	private int frequency;
	
	public PostElement(int docID, int frequency) {
		this.docID = docID;
		this.frequency = frequency;
	}

	public int getDocID() {
		return docID;
	}

	public void setDocID(int docID) {
		this.docID = docID;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		PostElement element = (PostElement)obj;
		return (this.docID == element.docID);
	}
	
	@Override
	public String toString() {
		return "(" + docID + ":" + frequency + ")";
	}

}
