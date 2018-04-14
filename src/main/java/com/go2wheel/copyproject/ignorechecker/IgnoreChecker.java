package com.go2wheel.copyproject.ignorechecker;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;

public interface IgnoreChecker {
	
	StepResult<Boolean> ignore(CopyEnv copyEnv, CopyDescription copyDescription);
	
	void initCondition(CopyEnv copyEnv);
	
	String name();

}
