package com.go2wheel.copyproject.ignorechecker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgnoreCheckConfig {

	@Bean
	@Qualifier("main")
	public IgnoreChecker mainIgnoreChecker() {
		return new IgnoreCheckerHub();
	}
}
