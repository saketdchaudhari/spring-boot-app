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
public class ThreadingServiceImplTest {

	@InjectMocks
	private ThreadingServiceImpl service;

	// Detect deadlock when no threads created.
	@Test
	public void detectDeadlock_TestCase1() {
		boolean result = service.detectDeadlock("thread1", "thread2");
		assertEquals(result, false);
	}

	//Detect deadlock positive scenario
	@Test
	public void detectDeadlock_TestCase2() throws InterruptedException {
		List<String> result = service.createDeadlock();
		assertEquals(result.size(), 2);
		Thread.sleep(2000);
		boolean deadlockResult = service.detectDeadlock(result.get(0), result.get(1));
		assertEquals(deadlockResult, true);
	}
	
	//Detect deadlock when second thread name is invalid.
	@Test
	public void detectDeadlock_TestCase3() throws InterruptedException {
		List<String> result = service.createDeadlock();
		assertEquals(result.size(), 2);
		Thread.sleep(2000);
		boolean deadlockResult = service.detectDeadlock(result.get(0), "wrongThreadName");
		assertEquals(deadlockResult, false);
	}
	
	//Detect deadlock when dirst thread name is invalid.
	@Test
	public void detectDeadlock_TestCase4() throws InterruptedException {
		List<String> result = service.createDeadlock();
		assertEquals(result.size(), 2);
		Thread.sleep(2000);
		boolean deadlockResult = service.detectDeadlock("wrongThreadName", result.get(0));
		assertEquals(deadlockResult, false);
	}
	
	//Detect deadlock when first thread is null.
	@Test
	public void detectDeadlock_TestCase5() throws InterruptedException {
		List<String> result = service.createDeadlock();
		assertEquals(result.size(), 2);
		Thread.sleep(2000);
		boolean deadlockResult = service.detectDeadlock(null, result.get(1));
		assertEquals(deadlockResult, false);
	}
	
	//Detect deadlock when second thread is null.
	@Test
	public void detectDeadlock_TestCase6() throws InterruptedException {
		List<String> result = service.createDeadlock();
		assertEquals(result.size(), 2);
		Thread.sleep(2000);
		boolean deadlockResult = service.detectDeadlock(result.get(0), null);
		assertEquals(deadlockResult, false);
	}
}
