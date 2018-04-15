package com.go2wheel.copyproject.copyperformer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.go2wheel.copyproject.copyperformer.CopyHub;
import com.go2wheel.copyproject.copyperformer.DefaultCopyPerformer;

@SpringBootTest("spring.shell.interactive.enabled=false")
@RunWith(SpringRunner.class)
public class TestPerformerConfig {
	
	@Autowired
	private CopyHub copyHub;
	
	@Test
	public void tExecutorNumbers() {
		assertThat(copyHub.getCopyPerformers().size(), equalTo(4));
		assertThat("last executor is defaultExecutor.", copyHub.getCopyPerformers().get(copyHub.getCopyPerformers().size() - 1).getClass(), equalTo(DefaultCopyPerformer.class));
	}

}
