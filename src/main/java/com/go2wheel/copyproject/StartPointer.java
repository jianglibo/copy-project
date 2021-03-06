package com.go2wheel.copyproject;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.util.StringUtils;

/**
 * Components scan start from this class's package.
 * @author jianglibo@gmail.com
 *
 */

// StandardAPIAutoConfiguration

@SpringBootApplication(exclude= {})
public class StartPointer {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
        String[] disabledCommands = {"--spring.shell.command.stacktrace.enabled=false"}; 
        String[] fullArgs = StringUtils.concatenateStringArrays(args, disabledCommands);
        ConfigurableApplicationContext context = new SpringApplicationBuilder(StartPointer.class).logStartupInfo(false).run(fullArgs);
//		ConfigurableApplicationContext context = SpringApplication.run(StartPointer.class, fullArgs);
	}
	
	@Bean
	public PromptProvider myPromptProvider() {
		return () -> new AttributedString("my-shell:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
	}
}
