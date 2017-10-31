package springbootstarter.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import springbootstarter.SpringBootCodingApplication;
import springbootstarter.dto.Post;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringBootCodingApplication.class)
public class ExternalRestServiceImplTest {

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	ExternalRestServiceImpl service;

	@SuppressWarnings("unchecked")
	@Test
	public void getAllPostsTest() {
		final ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);
		Class<HttpEntity<String>> httpEntityClass =
	              (Class<HttpEntity<String>>)(Class)HttpEntity.class;
		Class<ParameterizedTypeReference<List<Post>>> parameterizedTypeRef = 
				(Class<ParameterizedTypeReference<List<Post>>>)(Class)ParameterizedTypeReference.class;
		
		final ArgumentCaptor<HttpEntity<String>> httpEntityCaptor = ArgumentCaptor.forClass(httpEntityClass);
		final ArgumentCaptor<HttpMethod> httpMethodCaptor = ArgumentCaptor.forClass(HttpMethod.class);
		final ArgumentCaptor<ParameterizedTypeReference<List<Post>>> parameterizedTypeReferenceCaptor = ArgumentCaptor.forClass(parameterizedTypeRef);
		
		HttpHeaders expectedHeaders = new HttpHeaders();
		expectedHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> expectedEntity = new HttpEntity<String>("parameters", expectedHeaders);
		
		when(restTemplate.exchange(Mockito.anyString(),
                Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<String>> any(),
                Mockito.<ParameterizedTypeReference<List<Post>>> any())).thenReturn(prepareResult());
		
		List<Post> result = service.getAllPosts();
		
		verify(restTemplate, times(1)).exchange(uriCaptor.capture(), httpMethodCaptor.capture(), 
				httpEntityCaptor.capture(), parameterizedTypeReferenceCaptor.capture());
		verifyNoMoreInteractions(restTemplate);
		
		assertEquals("http://jsonplaceholder.typicode.com/posts", uriCaptor.getValue());
		assertEquals(HttpMethod.GET.name(), httpMethodCaptor.getValue().name());
		assertEquals(expectedEntity, httpEntityCaptor.getValue());
		
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getId());
		assertEquals("First post title", result.get(0).getTitle());
		assertEquals("First post body", result.get(0).getBody());
		assertEquals(2, result.get(1).getId());
		assertEquals("Second post title", result.get(1).getTitle());
		assertEquals("Second post body", result.get(1).getBody());
	}

	private ResponseEntity<List<Post>> prepareResult() {
		List<Post> posts = new ArrayList<>();
		Post post = new Post(1, 1, "First post title", "First post body");
        posts.add(post);
        post = new Post(2, 2, "Second post title", "Second post body");
        posts.add(post);
		ResponseEntity<List<Post>> postsResult = new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
		return postsResult;
	}

}
