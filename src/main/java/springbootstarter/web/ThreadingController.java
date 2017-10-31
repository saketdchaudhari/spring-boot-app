package springbootstarter.web;

import java.net.URI;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import springbootstarter.service.IThreadingService;

/**
 * ThreadingController exposes REST endpoint to perform multi-threading
 * operations.
 * 
 * @author saket chaudhari
 */
@RestController
@RequestMapping("/threads")
public class ThreadingController {

	private static final Logger logger = LogManager.getLogger(ThreadingController.class);

	@Autowired
	private IThreadingService service;

	/**
	 * This method generate two threads which creates deadlock situation.
	 * 
	 * @return {@link ResponseEntity<Link>} Link to find whether newly created
	 *         threads are in deadlock situation.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Link> createThreads() {
		logger.info("Received request for ThreadingController#createThreads.");
		List<String> threadNames = service.createDeadlock();
		URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().pathSegment("threads", "deadlockStatus")
				.queryParam("firstThreadName", threadNames.get(0)).queryParam("secondThreadName", threadNames.get(1))
				.build().toUri();
		Link link = new Link(location.toString(), "deadlockStatus");
		logger.info("Return response for ThreadingController#createThreads. Result :: {}", link);
		return new ResponseEntity<Link>(link, HttpStatus.CREATED);
	}

	/**
	 * This method checks whether two given threads are in deadlock situation.
	 * 
	 * @param firstThreadName
	 *            first thread name.
	 * @param secondThreadName
	 *            second thread name.
	 * 
	 * @return {@link ResponseEntity<Boolean>}. True if threads are in deadlock
	 *         otherwise False.
	 */
	@RequestMapping(value = "/deadlockStatus", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deadlockStatus(
			@RequestParam(value = "firstThreadName", required = true) String firstThreadName,
			@RequestParam(value = "secondThreadName", required = true) String secondThreadName) {
		logger.info(
				"Received request for ThreadingController#deadlockStatus. firstThreadName :: {} secondThreadName :: {}",
				firstThreadName, secondThreadName);
		boolean result = service.detectDeadlock(firstThreadName, secondThreadName);
		logger.info("Return response for ThreadingController#deadlockStatus. Result :: {}", result);
		return new ResponseEntity<String>(Boolean.valueOf(result).toString(), HttpStatus.OK);
	}
}