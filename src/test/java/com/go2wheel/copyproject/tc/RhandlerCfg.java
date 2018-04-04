package com.go2wheel.copyproject.tc;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RhandlerCfg {
	
	@Bean
	public MyThrowableResultHandler myThrowableResultHandler() {
		return new MyThrowableResultHandler();
	}
	
}
