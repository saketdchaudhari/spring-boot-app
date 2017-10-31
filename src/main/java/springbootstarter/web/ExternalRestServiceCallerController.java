package springbootstarter.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springbootstarter.dto.Post;
import springbootstarter.service.IExternalRestService;

/**
 * ExternalRestServiceCallerControlle exposes REST endpoint to perform external web 
 * service operations.
 * 
 * @author saket chaudhari
 */
@RestController
@RequestMapping("/jsonPlaceHolder")
public class ExternalRestServiceCallerController {
	private static final Logger logger = LogManager.getLogger(ExternalRestServiceCallerController.class);

	@Autowired
	private IExternalRestService service;

	/**
	 * This method finds all {@link Post} from external REST endpoint.
	 * 
	 * @return {@link ResponseEntity<List<Post>>}
	 */
	@RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Post>> getPosts() {
		logger.info("Received request for ExternalRestServiceCallerController#getPosts.");
		List<Post> posts = service.getAllPosts();
		logger.info("Return response for ExternalRestServiceCallerController#getPosts. Result ::  {}", posts);
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}
}
