package com.go2wheel.copyproject.copyperformer;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;

public interface CopyPerformer {
	
	// if you were sure nothing else need to be process then return true;
	/**
	 * 
	 * @param copyEnv
	 * @param copyDescription
	 * @return
	 */
	boolean copy(CopyEnv copyEnv, CopyDescription copyDescription);

}
