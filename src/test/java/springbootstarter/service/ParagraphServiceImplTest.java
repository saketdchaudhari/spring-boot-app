package springbootstarter.service;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import springbootstarter.SpringBootCodingApplication;
import springbootstarter.dto.WordCount;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringBootCodingApplication.class)
public class ParagraphServiceImplTest {

	@InjectMocks
	private ParagraphServiceImpl service;

	// Paragraph is not empty
	@Test
	public void testGreeting_TestCase1() {
		String paragraph = "A-word1 D-word2 C-word3 C-word3 D-word4     G-word5 1-word1 ##$$%% ##$$%%";
		Set<WordCount> result = service.findWordCount(paragraph);
		assertEquals(prepareExpectedResult(), result);
	}
	
	// Paragraph is empty
	@Test
	public void testGreeting_TestCase2() {
		String paragraph = "";
		Set<WordCount> result = service.findWordCount(paragraph);
		assertEquals(Collections.emptySet(), result);
	}

	// Paragraph is null
	@Test
	public void testGreeting_TestCase3() {
		String paragraph = null;
		Set<WordCount> result = service.findWordCount(paragraph);
		assertEquals(Collections.emptySet(), result);
	}
	
	private Set<WordCount> prepareExpectedResult() {
		SortedSet<WordCount> sortedWordCount = new TreeSet<>(
		        Comparator.comparing(WordCount::getWord));
		sortedWordCount.add(new WordCount("1-word1", 1));
		sortedWordCount.add(new WordCount("A-word1", 1));
		sortedWordCount.add(new WordCount("C-word3", 2));
		sortedWordCount.add(new WordCount("D-word2", 1));
		sortedWordCount.add(new WordCount("D-word4", 1));
		sortedWordCount.add(new WordCount("G-word5", 1));
		sortedWordCount.add(new WordCount("##$$%%", 2));
		return sortedWordCount;
	}
}

