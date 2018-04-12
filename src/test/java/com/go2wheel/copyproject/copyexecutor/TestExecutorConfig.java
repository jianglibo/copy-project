package com.go2wheel.copyproject.copyexecutor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest("spring.shell.interactive.enabled=false")
@RunWith(SpringRunner.class)
public class TestExecutorConfig {
	
	@Autowired
	private CopyHub copyHub;
	
	@Test
	public void tExecutorNumbers() {
		assertThat(copyHub.getCopyExecutors().size(), equalTo(2));
		assertThat("last executor is defaultExecutor.", copyHub.getCopyExecutors().get(copyHub.getCopyExecutors().size() - 1).getClass(), equalTo(DefaultCopyExecutor.class));
	}

}
