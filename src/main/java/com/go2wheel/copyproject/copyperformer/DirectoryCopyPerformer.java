package com.go2wheel.copyproject.copyperformer;

import javax.annotation.Priority;

import org.springframework.stereotype.Component;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;

@Component
@Priority(1)
public class DirectoryCopyPerformer implements CopyPerformer {

	@Override
	public StepResult<Void> copy(CopyEnv copyEnv, CopyDescription copyDescription) {
		if (copyDescription.isDirectory()) {
			return StepResult.tsudukanaiStepResult();
		} else {
			return StepResult.tsudukuStepResult();
		}
		
	}

}
