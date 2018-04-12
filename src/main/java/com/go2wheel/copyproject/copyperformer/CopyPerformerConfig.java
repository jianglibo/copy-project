package com.go2wheel.copyproject.copyperformer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CopyPerformerConfig {

	@Bean
	@Qualifier("main")
	public CopyPerformer mainCopyPerformer() {
		return new CopyHub();
	}
}
