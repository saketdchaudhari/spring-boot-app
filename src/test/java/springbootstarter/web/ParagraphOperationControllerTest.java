package springbootstarter.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import springbootstarter.dto.WordCount;
import springbootstarter.service.IParagraphService;

@RunWith(SpringRunner.class)
@WebMvcTest(ParagraphOperationController.class)
public class ParagraphOperationControllerTest {

	@Autowired
	private MockMvc mockedMvc;

	@MockBean
	private IParagraphService mockedService;
	
	@Test
	public void findWordCountTest() throws Exception {
		final String input = "A1 a1 b2 b2 b3 c1";
		final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

		when(mockedService.findWordCount(input)).thenReturn(prepareExpectedResult());

		mockedMvc.perform(get("/paragraph/wordCount").param("paragraph", "A1 a1 b2 b2 b3 c1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().json("[{\"word\":\"A1\",\"count\":1},{\"word\":\"a1\",\"count\":1},{\"word\":\"b2\",\"count\":2},{\"word\":\"c\",\"count\":1}]"));

		verify(mockedService, times(1)).findWordCount(captor.capture());
		verifyNoMoreInteractions(mockedService);
		String param = captor.getValue();
		assertEquals("Argument matched.", input, param);
	}

	private Set<WordCount> prepareExpectedResult() {
		SortedSet<WordCount> result = new TreeSet<>(
		        Comparator.comparing(WordCount::getWord));
		result.add(new WordCount("A1", 1));
		result.add(new WordCount("a1", 1));
		result.add(new WordCount("b2", 2));
		result.add(new WordCount("c", 1));
		return result;
	}

}