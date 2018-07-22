package com.go2wheel.copyproject.resulthandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.result.ResultHandlerConfig;

/**
 * @see ResultHandlerConfig
 * @author admin
 *
 */
@Configuration
public class ResultHandlerConfigMine {
	@Bean
	public CopyResultHandler copyResultHandler() {
		return new CopyResultHandler();
	}

}
