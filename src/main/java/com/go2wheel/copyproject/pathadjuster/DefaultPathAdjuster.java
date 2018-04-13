package com.go2wheel.copyproject.pathadjuster;

import javax.annotation.Priority;

import org.springframework.stereotype.Component;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;

@Component
@Priority(Integer.MAX_VALUE)
public class DefaultPathAdjuster implements PathAdjuster {

	@Override
	public StepResult<Void> adjust(CopyEnv copyEnv, CopyDescription copyDescription) {
		if (copyDescription.getDstAb() == null) {
			String replaced = copyDescription.getSrcRelative().toString().replace('\\', '/').replace(copyEnv.getSrcRootPackageSlash(), copyEnv.getDstRootPackageSlash());
			copyDescription.setDstAb(copyEnv.getDstFolder().resolve(replaced));
		}
		return StepResult.tsudukuStepResult();
	}

}
