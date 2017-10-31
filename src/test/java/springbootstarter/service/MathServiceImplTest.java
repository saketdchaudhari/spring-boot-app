package springbootstarter.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import springbootstarter.SpringBootCodingApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringBootCodingApplication.class)
public class MathServiceImplTest {

	@InjectMocks
	private MathServiceImpl service;

	@Test
	public void testGreetingTestCase1() {
		List<Integer> result = service.findFirstNFibonacciNumbers(0);
		assertEquals(0, result.size());
	}
	
	@Test
	public void testGreetingTestCase2() {
		List<Integer> result = service.findFirstNFibonacciNumbers(1);
		assertEquals(1, result.size());
		assertEquals(Integer.valueOf(0), Integer.valueOf(result.get(0)));
	}
	
	@Test
	public void testGreetingTestCase3() {
		List<Integer> result = service.findFirstNFibonacciNumbers(2);
		assertEquals(2, result.size());
		assertEquals(Integer.valueOf(0), Integer.valueOf(result.get(0)));
		assertEquals(Integer.valueOf(1), Integer.valueOf(result.get(1)));
	}

}
