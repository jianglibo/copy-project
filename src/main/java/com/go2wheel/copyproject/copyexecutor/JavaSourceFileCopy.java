package com.go2wheel.copyproject.copyexecutor;

import org.springframework.stereotype.Component;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;

@Component
public class JavaSourceFileCopy implements CopyExecutor {

	@Override
	public boolean copy(CopyEnv copyEnv, CopyDescription copyDescription) {
		return false;
	}

}
