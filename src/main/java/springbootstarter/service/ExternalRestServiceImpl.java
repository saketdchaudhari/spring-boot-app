package springbootstarter.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import springbootstarter.dto.Post;

@Service
public class ExternalRestServiceImpl implements IExternalRestService {

	private final String URI = "http://jsonplaceholder.typicode.com/posts";
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public List<Post> getAllPosts() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<List<Post>> result = restTemplate.exchange(URI, HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Post>>() {});
		return result.getBody();
	}

}
