package com.go2wheel.copyproject.pathadjuster;

import java.nio.file.Files;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;

public interface PathAdjuster {
	
	StepResult<Void> adjust(CopyEnv copyEnv, CopyDescription copyDescription);
	
	default boolean isSourceAfile(CopyDescription copyDescription) {
		return Files.isRegularFile(copyDescription.getSrcAbsolute());
	}
	
	String name();

}
