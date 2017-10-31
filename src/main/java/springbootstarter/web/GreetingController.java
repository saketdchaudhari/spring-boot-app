package springbootstarter.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springbootstarter.service.IGreetingService;

/**
 * GreetingController exposes REST endpoint to greet user.
 * 
 * @author saket chaudhari
 */
@RestController
@RequestMapping("/greeting")
public class GreetingController {

	private static final Logger logger = LogManager.getLogger(GreetingController.class);

	@Autowired
	private IGreetingService service;

	/**
	 * This method greets user.
	 * 
	 * @return {@link ResponseEntity<String>} with "Hello World!" greeting message.
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> greetUser() {
		logger.info("Received request for GreetingController#greetUser.");
		String result = service.greetUser();
		logger.info("Return response for GreetingController#greetUser. Result ::  {}", result);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
