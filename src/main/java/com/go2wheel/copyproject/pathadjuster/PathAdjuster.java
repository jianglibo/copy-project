package com.go2wheel.copyproject.pathadjuster;

import java.nio.file.Files;
import java.nio.file.Path;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;

public interface PathAdjuster {
	
	StepResult<Void> adjust(CopyEnv copyEnv, CopyDescription copyDescription);
	
	default String getExtWithoutDot(Path path) {
		String s = path.getFileName().toString();
		int p = s.lastIndexOf('.');
		if (p != -1) {
			return s.substring(p);
		}
		return "";
	}
	
	default boolean isSourceAfile(CopyDescription copyDescription) {
		return Files.isRegularFile(copyDescription.getSrcAbsolute());
	}

}
