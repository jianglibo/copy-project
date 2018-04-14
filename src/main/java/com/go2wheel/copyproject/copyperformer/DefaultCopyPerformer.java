package com.go2wheel.copyproject.copyperformer;

import java.io.IOException;
import java.nio.file.Files;

import javax.annotation.Priority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;
import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;

@Component
@Priority(Integer.MAX_VALUE)
public class DefaultCopyPerformer implements CopyPerformer {
	
	private Logger logger = LoggerFactory.getLogger(DefaultCopyPerformer.class);

	@Override
	public StepResult<Void> copy(CopyEnv copyEnv, CopyDescription copyDescription) {
		try { // If target file already exists, just skip.
			if (!Files.exists(copyDescription.getDstAb())) {
				createParentDirectories(copyDescription);
				Files.copy(copyDescription.getSrcAbsolute(), copyDescription.getDstAb());
				copyDescription.setState(COPY_STATE.FILE_COPY_SUCCESSED);
			}
		} catch (IOException e) {
			logger.info(e.getMessage());
			copyDescription.setState(COPY_STATE.FILE_COPY_FAILED);
		}
		return StepResult.tsudukuStepResult();
	}

	@Override
	public String name() {
		return "file";
	}
}
