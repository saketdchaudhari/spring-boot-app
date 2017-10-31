package springbootstarter.dto;

import java.util.Comparator;

public class WordCount implements Comparator<String>{
	String word;
	int count;

	public WordCount(String word, int count) {
		super();
		this.word = word;
		this.count = count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public String getWord() {
		return word;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "WordCount [word=" + word + ", count=" + count + "]";
	}

	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}
}
