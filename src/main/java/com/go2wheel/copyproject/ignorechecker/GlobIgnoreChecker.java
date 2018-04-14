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
import com.go2wheel.copyproject.value.StepResult;

@Component
public class GlobIgnoreChecker implements IgnoreChecker {
	
	private static String COPY_IGNORE_FILE = "copyignore.txt";
	
	private static String GIT_IGNORE_FILE = ".gitignore";
	
	private List<PathMatcher> matchers = new ArrayList<>();

	@Override
	public StepResult<Boolean> ignore(CopyEnv copyEnv, CopyDescription copyDescription) {
		boolean b = matchers.stream().anyMatch(pm -> pm.matches(copyDescription.getSrcRelative()));
		return new StepResult<Boolean>(b, false);
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

	@Override
	public String name() {
		return "glob";
	}
}
