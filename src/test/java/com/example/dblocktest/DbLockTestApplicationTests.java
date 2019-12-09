package com.example.dblocktest;

import java.util.concurrent.CountDownLatch;

import com.example.config.TestConfiguration;
import com.example.service.LockService;
import com.example.service.TaskService;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@SpringBootTest
@ContextConfiguration(classes = TestConfiguration.class, loader = AnnotationConfigContextLoader.class)
class DbLockTestApplicationTests {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private LockService lockService;

	@Autowired
	private TaskService taskService;

	@Test
	void test() throws Exception {
		CountDownLatch latch = new CountDownLatch(2);

		Thread t1 = new MyThread("Thread1", latch);
		t1.start();

		Thread t2 = new MyThread("Thread2", latch);
		t2.start();

		latch.await();
		System.out.println("Test Complete");
	}

	class MyThread extends Thread {
		private CountDownLatch latch;
		public MyThread(String name, CountDownLatch latch) {
			super.setName(name);
			this.latch = latch;
		}
		@Override
		public void run() {
			try {
				taskService.doTask();
			} catch (Exception e) {
				e.printStackTrace();
			}

			this.latch.countDown();
		}
	}

}
