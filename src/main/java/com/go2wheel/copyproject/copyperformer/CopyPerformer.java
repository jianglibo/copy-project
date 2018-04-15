package com.go2wheel.copyproject.copyperformer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;

public interface CopyPerformer {
	
	StepResult<Void> copy(CopyEnv copyEnv, CopyDescription copyDescription);
	
	String name();
	
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
	
	default String replaceNewPackageName(CopyEnv copyEnv, String line) {
		return line.replaceAll(copyEnv.getEscapedSrcRootPackageDot(), copyEnv.getDstRootPackageDot());
	}
	
	default void setFileCopySuccessState(CopyDescription copyDescription) {
		copyDescription.setState(COPY_STATE.FILE_COPY_SUCCESSED);
	}
	


}
