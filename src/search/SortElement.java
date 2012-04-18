package search;

public class SortElement implements Comparable<SortElement> {
	private int key;
	private double value;
	
	public SortElement(int key, double value) {
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return key + ":" + value;
	}

	@Override
	public int compareTo(SortElement o) {
		if(this == o || value == o.getValue())
			return 0;
		else if(value < o.getValue())
			return -1;
		return 1;
	}

}
