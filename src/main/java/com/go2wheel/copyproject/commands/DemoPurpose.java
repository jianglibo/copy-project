package com.go2wheel.copyproject.commands;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent()
public class DemoPurpose {
	
	private Terminal terminal;
	
	@Autowired @Lazy
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
	
	@ShellMethod(value = "A command whose name looks the same as another one.")
	public AttributedString helpMeOut() {
		String s = "You can go " + this.terminal.getWidth();
		AttributedStringBuilder asb = new AttributedStringBuilder().ansiAppend(s).style(AttributedStyle.BOLD).append("  hhhh");
		return asb.toAttributedString();
		
	}

}
