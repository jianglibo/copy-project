package com.go2wheel.copyproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.SpringShellAutoConfiguration;
import org.springframework.shell.jline.JLineShellAutoConfiguration;

/**
 * Components scan start from this class's package.
 * @author jianglibo@gmail.com
 *
 */

@SpringBootApplication(exclude= {SpringShellAutoConfiguration.class, JLineShellAutoConfiguration.class})
public class StartPointer {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(StartPointer.class, args);
	}
}
