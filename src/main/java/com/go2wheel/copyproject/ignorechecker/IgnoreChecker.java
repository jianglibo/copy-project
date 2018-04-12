package com.go2wheel.copyproject.ignorechecker;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;

public interface IgnoreChecker {
	
	/**
	 * If stopCheckNext return true, no more checker will be checked.
	 * @return
	 */
	boolean stopCheckNext();
	
	boolean ignore(CopyEnv copyEnv, CopyDescription copyDescription);
	
	void initCondition(CopyEnv copyEnv);

}
