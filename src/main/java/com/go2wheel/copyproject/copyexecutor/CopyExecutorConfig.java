package com.go2wheel.copyproject.copyexecutor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CopyExecutorConfig {

	@Bean
	@Qualifier("main")
	public CopyExecutor mainCopyExecutor() {
		return new CopyHub();
	}
}
