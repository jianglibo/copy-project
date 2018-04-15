package com.go2wheel.copyproject.copyperformer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;
import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;

@Component
public class GradleBuildCopy implements CopyPerformer {
	
	private Logger logger = LoggerFactory.getLogger(GradleBuildCopy.class);

	@Override
	public StepResult<Void> copy(CopyEnv copyEnv, CopyDescription copyDescription) {
		Path target = fileToProcess(copyDescription);
		createParentDirectories(copyDescription);
		if (!"build.gradle".equalsIgnoreCase(copyDescription.getSrcFileName())) {
			return StepResult.tsudukuStepResult();
		}
		try {
			List<String> lines = Files.readAllLines(target);
			lines = lines.stream().map(line -> replaceNewPackageName(copyEnv, line)).collect(Collectors.toList());
			Files.write(copyDescription.getDstAb(), lines);
			setFileCopySuccessState(copyDescription);
			return StepResult.tsudukuStepResult();
		} catch (IOException e) {
			logger.info(e.getMessage());
			copyDescription.setState(COPY_STATE.FILE_COPY_FAILED);
			return StepResult.tsudukanaiStepResult();
		}
	}

	@Override
	public String name() {
		return "build-gradle";
	}

}
