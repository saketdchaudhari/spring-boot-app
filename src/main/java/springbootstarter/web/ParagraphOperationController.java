package springbootstarter.web;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springbootstarter.dto.WordCount;
import springbootstarter.service.IParagraphService;

/**
 * ParagraphOperationController exposes REST endpoint to perform paragraph operations.
 * 
 * @author saket chaudhari
 */
@RestController
@RequestMapping("/paragraph")
public class ParagraphOperationController {

	private static final Logger logger = LogManager.getLogger(ParagraphOperationController.class);

	@Autowired
	private IParagraphService service;

	/**
	 * This method finds all unique words and their counts in given paragraph.
	 * 
	 * @return {@link ResponseEntity<Set<WordCount>>} Set of WordCount.
	 */
	@RequestMapping(value = "/wordCount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<WordCount>> findWordCount(@RequestParam("paragraph") String paragraph) {
		logger.info("Received request for ParagraphOperationController#findWordCount.");
		Set<WordCount> wordCount = service.findWordCount(paragraph);
		logger.info("Return response for ParagraphOperationController#findWordCount. Result ::  {}", wordCount);
		return new ResponseEntity<Set<WordCount>>(wordCount, HttpStatus.OK);
	}
}
