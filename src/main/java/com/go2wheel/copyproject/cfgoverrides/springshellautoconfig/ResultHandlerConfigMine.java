package com.go2wheel.copyproject.cfgoverrides.springshellautoconfig;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.ResultHandler;
import org.springframework.shell.TerminalSizeAware;
import org.springframework.shell.result.AttributedCharSequenceResultHandler;
import org.springframework.shell.result.DefaultResultHandler;
import org.springframework.shell.result.IterableResultHandler;
import org.springframework.shell.result.ParameterValidationExceptionResultHandler;
import org.springframework.shell.result.ResultHandlerConfig;
import org.springframework.shell.result.TerminalSizeAwareResultHandler;
import org.springframework.shell.result.ThrowableResultHandler;
import org.springframework.shell.result.TypeHierarchyResultHandler;

/**
 * @see ResultHandlerConfig
 * @author admin
 *
 */
@Configuration
public class ResultHandlerConfigMine {

	@Bean
	@Qualifier("main")
	public ResultHandler<?> mainResultHandler() {
		return new TypeHierachyResultHandlerMine();
	}

	@Bean
	public IterableResultHandlerMine iterableResultHandler() {
		return new IterableResultHandlerMine();
	}

	@PostConstruct
	public void wireIterableResultHandler() {
		iterableResultHandler().setDelegate(mainResultHandler());
	}

	@Bean
	@ConditionalOnClass(TerminalSizeAware.class)
	public TerminalSizeAwareResultHandler terminalSizeAwareResultHandler() {
		return new TerminalSizeAwareResultHandler();
	}

	@Bean
	public AttributedCharSequenceResultHandler attributedCharSequenceResultHandler() {
		return new AttributedCharSequenceResultHandler();
	}

	@Bean
	public DefaultResultHandler defaultResultHandler() {
		return new DefaultResultHandler();
	}

	@Bean
	public ParameterValidationExceptionResultHandler parameterValidationExceptionResultHandler() {
		return new ParameterValidationExceptionResultHandler();
	}

	@Bean
	public ThrowableResultHandler throwableResultHandler() {
		return new ThrowableResultHandler();
	}

}