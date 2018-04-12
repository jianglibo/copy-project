package com.go2wheel.copyproject.ignorechecker;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.go2wheel.copyproject.util.GitIgnoreFileReader;
import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;

@Component
public class GlobIgnoreChecker implements IgnoreChecker {
	
	private static String COPY_IGNORE_FILE = "copyignore.txt";
	
	private static String GIT_IGNORE_FILE = ".gitignore";
	
	private List<PathMatcher> matchers = new ArrayList<>();

	@Override
	public boolean stopCheckNext() {
		return false;
	}

	@Override
	public boolean ignore(CopyEnv copyEnv, CopyDescription copyDescription) {
		return matchers.stream().anyMatch(pm -> pm.matches(copyDescription.getSrcRelative()));
	}

	@Override
	public void initCondition(CopyEnv copyEnv) {
		Path ignoreFile = copyEnv.getSrcFolder().resolve(COPY_IGNORE_FILE);
		if (!Files.exists(ignoreFile)) {
			ignoreFile = copyEnv.getSrcFolder().resolve(GIT_IGNORE_FILE);
		}
		if (Files.exists(ignoreFile) && Files.isRegularFile(ignoreFile)) {
			matchers = GitIgnoreFileReader.ignoreMatchers(ignoreFile);
		}
	}
}
