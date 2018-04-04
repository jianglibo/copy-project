package com.go2wheel.copyproject;

import java.util.List;
import java.util.stream.Collectors;

import org.jline.reader.ParsedLine;
import org.springframework.shell.Input;

public class MirrorOfParsedLineInput implements Input {

    private final ParsedLine parsedLine;

    MirrorOfParsedLineInput(ParsedLine parsedLine) {
        this.parsedLine = parsedLine;
    }

    @Override
    public String rawText() {
        return parsedLine.line();
    }

    @Override
    public List<String> words() {
		return parsedLine.words().stream()
				.map(s -> s.replaceAll("^\\n+|\\n+$", "")) // CR at beginning/end of line introduced by backslash continuation
				.map(s -> s.replaceAll("\\n+", " ")) // CR in middle of word introduced by return inside a quoted string
				.collect(Collectors.toList());
//        return JLineShellAutoConfiguration.sanitizeInput(parsedLine.words());
    }
}
