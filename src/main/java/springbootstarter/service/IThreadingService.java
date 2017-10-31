package springbootstarter.service;

import java.util.List;

/**
 * Provides methods to perform multi-threading operations.
 * 
 * @author saket chaudhari
 *
 */
public interface IThreadingService {

	/**
	 * This method creates two threads and generate deadlock situation.
	 * 
	 * @return List<String> Generated thread names.
	 *
	 */
	List<String> createDeadlock();

	/**
	 * This method checks for deadlock condition.
	 * 
	 * @param String
	 *            firstThreadName
	 * @param String
	 *            secondThreadName
	 * @return boolean
	 *
	 */
	boolean detectDeadlock(String firstThreadName, String secondThreadName);
}