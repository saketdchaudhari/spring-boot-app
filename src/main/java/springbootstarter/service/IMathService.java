package springbootstarter.service;

import java.util.List;

/**
 * Provides methods to perform math operations.
 * 
 * @author saket chaudhari
 *
 */
public interface IMathService {

	/**
	 * This method generates List of first N Fibonacci numbers.
	 * 
	 * @param number
	 * @return List of Integer
	 */
	List<Integer> findFirstNFibonacciNumbers(int number);
}
