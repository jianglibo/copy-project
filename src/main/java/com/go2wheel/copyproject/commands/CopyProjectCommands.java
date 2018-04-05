package com.go2wheel.copyproject.commands;

import java.io.File;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.ValueProvider;

@ShellComponent()
public class CopyProjectCommands {

	public static String PACKAGE_PATTERN = "^([a-z][a-z0-9]*?\\.?)*([a-z][a-z0-9]*?)+$";

	@SuppressWarnings("unused")
	private Terminal terminal;
	
	private SourceHolder sourceHolder;

	@Autowired
	@Lazy
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	@ShellMethod(value = "tell which project folder to copy.", key = "from")
	public void source(@NotNull File srcFolder,
			@Pattern(regexp = "^([a-z][a-z0-9]*?\\.?)*([a-z][a-z0-9]*?)+$") String rootPackage,
			@ShellOption(help="semicolon separated file patterns.") ListOfExcludes excludes) {
		this.sourceHolder = new SourceHolder(srcFolder, excludes, rootPackage);
		System.out.println(this.sourceHolder);
	}

	@Bean
	public PromptProvider myPromptProvider() {
		return () -> new AttributedString("my-shell:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
	}
	
	@Bean
	public ValueProvider listOfExcludeValueProvider() {
		return new ListOfExcludeValueProvider();
	}

}
