package springbootstarter.service;

import java.util.Set;

import springbootstarter.dto.WordCount;

/**
 * Provides methods to perform paragraph operations.
 * 
 * @author saket chaudhari
 *
 */
public interface IParagraphService {
	/**
	 * This method finds out unique words and their counts {@code WordCount} in
	 * given paragraph. Each word can be separated by space(s).
	 * 
	 * @param String paragraph
	 * @return Set<WordCount>
	 *
	 */
	Set<WordCount> findWordCount(String paragraph);
}
