package com.go2wheel.copyproject.cfgoverrides.jlineshellautoconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jline.reader.ParsedLine;
import org.springframework.shell.Input;

public class ParsedLineInputMine implements Input {

    private final ParsedLine parsedLine;

    ParsedLineInputMine(ParsedLine parsedLine) {
        this.parsedLine = parsedLine;
    }

    @Override
    public String rawText() {
        return parsedLine.line();
    }

    @Override
    public List<String> words() {
//		return parsedLine.words().stream()
//				.map(s -> s.replaceAll("^\\n+|\\n+$", "")) // CR at beginning/end of line introduced by backslash continuation
//				.map(s -> s.replaceAll("\\n+", " ")) // CR in middle of word introduced by return inside a quoted string
//				.collect(Collectors.toList());
//    	if (parsedLine == null) {
//    		return new ArrayList<>();
//    	}
        return JLineShellAutoConfigurationMine.sanitizeInput(parsedLine.words());
    }
}
