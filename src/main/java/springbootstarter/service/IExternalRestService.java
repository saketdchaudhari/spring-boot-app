package springbootstarter.service;

import java.util.List;

import springbootstarter.dto.Post;

/**
 * Provides methods to call external REST endpoints.
 * 
 * @author saket chaudhari
 *
 */
public interface IExternalRestService {
	
	/**
	 * This method calls external REST endpoint and get all {@link Post}.
	 * 
	 * @return List of {@link Post}
	 */
	List<Post> getAllPosts();
}
