package com.go2wheel.copyproject.pathadjuster;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.go2wheel.copyproject.UtilForTe;
import com.go2wheel.copyproject.value.CopyEnv;

public class BasePathAdjusterFort {
	
	
	public void assertDstPath(String msg, Path rp, String expected) {
		assertThat(msg, rp.toString().replace('\\', '/'), equalTo(expected));
	}
	
	protected CopyEnv getDemoEnv(String srcRootPackageDot, String dstRootPackageDot) {
		return UtilForTe.copyEnvDemoproject(Paths.get("c:/fakedir"), srcRootPackageDot, dstRootPackageDot);
	}

}
