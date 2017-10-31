package springbootstarter.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import springbootstarter.dto.WordCount;

@Service
public class ParagraphServiceImpl implements IParagraphService {

	@Override
	public Set<WordCount> findWordCount(String paragraph) {
		if (paragraph == null || paragraph.trim().isEmpty()) {
			return Collections.emptySet();
		}
		List<String> words = Arrays.asList(paragraph.split("\\s+"));
		Map<String, WordCount> wordCounts = new HashMap<>();
		for (String word : words) {
			System.out.println("word " + word);
			if (wordCounts.containsKey(word)) {
				WordCount wordCnt = wordCounts.get(word);
				wordCnt.setCount(wordCnt.getCount() + 1);
			} else {
				wordCounts.put(word, new WordCount(word, 1));
			}
		}
		SortedSet<WordCount> sortedWordCount = new TreeSet<>(Comparator.comparing(WordCount::getWord));
		for (String key : wordCounts.keySet()) {
			sortedWordCount.add(wordCounts.get(key));
		}
		return sortedWordCount;
	}

}
