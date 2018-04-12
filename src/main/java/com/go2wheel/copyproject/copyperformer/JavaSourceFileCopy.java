package com.go2wheel.copyproject.copyperformer;

import org.springframework.stereotype.Component;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;

@Component
public class JavaSourceFileCopy implements CopyPerformer {

	@Override
	public boolean copy(CopyEnv copyEnv, CopyDescription copyDescription) {
		return false;
	}

}
