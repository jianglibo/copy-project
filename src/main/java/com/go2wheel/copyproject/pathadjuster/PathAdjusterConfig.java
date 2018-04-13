package com.go2wheel.copyproject.pathadjuster;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathAdjusterConfig {

	@Bean
	@Qualifier("main")
	public PathAdjuster mainPathAdjuster() {
		return new PathAdjusterHub();
	}
}
