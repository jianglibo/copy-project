package com.go2wheel.copyproject.copyexecutor;

import java.io.IOException;
import java.nio.file.Files;

import javax.annotation.Priority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;

@Component
@Priority(Integer.MAX_VALUE)
public class DefaultCopyExecutor implements CopyExecutor {
	
	private Logger logger = LoggerFactory.getLogger(DefaultCopyExecutor.class);

	@Override
	public boolean copy(CopyEnv copyEnv, CopyDescription copyDescription) {
		try {
			if (Files.exists(copyDescription.getDstAb())) {
				return true;
			}
			Files.copy(copyDescription.getSrcAb(), copyDescription.getDstAb());
			copyDescription.setState(COPY_STATE.FILE_COPY_SUCCESSED);
		} catch (IOException e) {
			logger.info(e.getMessage());
			copyDescription.setState(COPY_STATE.FILE_COPY_FAILED);
		}
		return true;
	}

}
