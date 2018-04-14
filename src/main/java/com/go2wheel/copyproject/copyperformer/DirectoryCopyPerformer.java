package com.go2wheel.copyproject.copyperformer;

import java.nio.file.Files;

import javax.annotation.Priority;

import org.springframework.stereotype.Component;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;

@Component
@Priority(1)
public class DirectoryCopyPerformer implements CopyPerformer {

	@Override
	public StepResult<Void> copy(CopyEnv copyEnv, CopyDescription copyDescription) {
		if (Files.isDirectory(copyDescription.getSrcAbsolute())) {
			copyDescription.setState(COPY_STATE.DIR_DETECTED);
			return StepResult.tsudukanaiStepResult();
		} else {
			return StepResult.tsudukuStepResult();
		}
		
	}

	@Override
	public String name() {
		return "directory";
	}

}
