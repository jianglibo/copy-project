package com.go2wheel.copyproject.copyperformer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;

public interface CopyPerformer {
	
	StepResult<Void> copy(CopyEnv copyEnv, CopyDescription copyDescription);
	
	default Path fileToProcess(CopyDescription copyDescription) {
		if (Files.exists(copyDescription.getDstAb())) {
			return copyDescription.getDstAb();
		} else {
			return copyDescription.getSrcAbsolute();
		}
	}
	
	default void createParentDirectories(CopyDescription copyDescription) {
		Path parent = copyDescription.getDstAb().getParent();
		if (Files.exists(parent))return;
		try {
			Files.createDirectories(parent);
		} catch (IOException e) {
		}
	}
	


}
