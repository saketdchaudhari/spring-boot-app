package springbootstarter.service;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import springbootstarter.web.ThreadingController;

@Service
public class ThreadingServiceImpl implements IThreadingService {

	private static final Logger logger = LogManager.getLogger(ThreadingController.class);

	private final ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();

	@Override
	public List<String> createDeadlock() {
		final Object resource1 = new Object();
		final Object resource2 = new Object();

		String firstThreadName = UUID.randomUUID().toString();
		String secondThreadName = UUID.randomUUID().toString();

		logger.info("Creating first thread :: {}", firstThreadName);
		Thread thread1 = new Thread(firstThreadName) {
			@Override
			public void run() {
				synchronized (resource1) {
					logger.info("First thread acquired resource 1");
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch (InterruptedException ignore) {
					}
					synchronized (resource2) {
						logger.info("First thread acquired resource 2");
					}
				}
			}
		};
		thread1.start();

		logger.info("Creating second thread :: {}", secondThreadName);
		Thread thread2 = new Thread(secondThreadName) {
			@Override
			public void run() {
				synchronized (resource2) {
					logger.info("Second thread acquired resource 2");
					synchronized (resource1) {
						logger.info("Second thread acquired resource 1");
					}
				}
			}
		};
		thread2.start();

		List<String> threadNames = new ArrayList<String>(2);
		threadNames.add(firstThreadName);
		threadNames.add(secondThreadName);
		return threadNames;
	}

	@Override
	public boolean detectDeadlock(String firstThreadName, String secondThreadName) {
		long[] deadlockedThreadIds = ThreadingServiceImpl.this.mxBean.findDeadlockedThreads();
		boolean result = false;
		if (deadlockedThreadIds != null) {
			ThreadInfo[] deadlockThreadInfos = ThreadingServiceImpl.this.mxBean.getThreadInfo(deadlockedThreadIds);
			for (ThreadInfo deadlockThreadInfo : deadlockThreadInfos) {
				if (deadlockThreadInfo != null) {
					logger.info("In ThreadingServiceImpl#detectDeadlock Thread Name :: {}",
							deadlockThreadInfo.getThreadName());
					if (!deadlockThreadInfo.getThreadName().equals(firstThreadName)
							&& !deadlockThreadInfo.getThreadName().equals(secondThreadName)) {
						continue;
					}
					if (deadlockThreadInfo.getThreadName().equals(firstThreadName)) {
						logger.info("In ThreadingServiceImpl#detectDeadlock Lock owner for :: {} is {}",
								firstThreadName, deadlockThreadInfo.getLockOwnerName());
						if (!deadlockThreadInfo.getLockOwnerName().equals(secondThreadName)) {
							result = false;
						} else {
							result = true;
						}
					} else if (deadlockThreadInfo.getThreadName().equals(secondThreadName)) {
						logger.info("In ThreadingServiceImpl#detectDeadlock Lock owner for :: {} is {}",
								secondThreadName, deadlockThreadInfo.getLockOwnerName());
						if (!deadlockThreadInfo.getLockOwnerName().equals(firstThreadName)) {
							result = false;
						} else {
							result = true;
						}
					}
				}
			}
		}
		return result;
	}

}