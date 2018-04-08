package com.go2wheel.copyproject.cfgoverrides.jlineshellautoconfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.shell.CommandRegistry;
import org.springframework.shell.MethodTargetRegistrar;
import org.springframework.shell.ParameterResolver;
import org.springframework.shell.standard.CommandValueProvider;
import org.springframework.shell.standard.EnumValueProvider;
import org.springframework.shell.standard.FileValueProvider;
import org.springframework.shell.standard.StandardAPIAutoConfiguration;
import org.springframework.shell.standard.StandardMethodTargetRegistrar;
import org.springframework.shell.standard.StandardParameterResolver;
import org.springframework.shell.standard.ValueProvider;

/**
 * copy from {@link StandardAPIAutoConfiguration}
 * @author admin
 *
 */

@Configuration
public class StandardAPIAutoConfigurationMine {

	@Bean
	public ValueProvider commandValueProvider(@Lazy CommandRegistry commandRegistry) {
		return new CommandValueProvider(commandRegistry);
	}

	@Bean
	public ValueProvider enumValueProvider() {
		return new EnumValueProvider();
	}

	@Bean
	public ValueProvider fileValueProvider() {
		return new FileValueProvider();
	}

	@Bean
	public MethodTargetRegistrar standardMethodTargetResolver() {
		return new StandardMethodTargetRegistrar();
	}

	@Bean
	public ParameterResolver standardParameterResolver(@Qualifier("spring-shell") ConversionService conversionService) {
		return new StandardParameterResolver(conversionService);
	}
}