package com.go2wheel.copyproject.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GitIgnoreFileReader {
	
	public static List<PathMatcher> ignoreMatchers(Path igfile) {
		try (Stream<String> lines = Files.lines(igfile)) {
			return lines.map(line -> line.trim()).filter(line -> !line.startsWith("#")).map(line -> line.replaceAll("\\\\", "/")).flatMap(line -> {
				List<String> ls = new ArrayList<>();
				if (line.startsWith("/")) {
					line = line.substring(1);
				}
				if (line.endsWith("/")) {
					ls.add(line.substring(0, line.length() - 1));
					ls.add(line);
					line = line + "*";
				}
				ls.add(line);
				ls.sort(new Comparator<String>() {

					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}
				});
				return ls.stream();
			}).distinct().map(line -> "glob:" + line).map(FileSystems.getDefault()::getPathMatcher).collect(Collectors.toList());
		} catch (IOException e) {
			return new ArrayList<>();
		}
	}
}
