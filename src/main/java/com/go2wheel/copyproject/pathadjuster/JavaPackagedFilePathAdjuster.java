package com.go2wheel.copyproject.pathadjuster;

import org.springframework.stereotype.Component;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;

@Component
public class JavaPackagedFilePathAdjuster implements PathAdjuster {
	
	@Override
	public StepResult<Void> adjust(CopyEnv copyEnv, CopyDescription copyDescription) {
		if (copyDescription.getDstAb() == null) {
			if (isSourceAfile(copyDescription)) {
				String src = copyDescription.getSrcRelative().toString().replace('\\', '/');
				String ptn = copyEnv.getSrcRootPackageSlash() + "/";
				if (src.indexOf(ptn) != -1) {
					String dst = src.replaceAll(copyEnv.getSrcRootPackageSlash(), copyEnv.getDstRootPackageSlash());
					copyDescription.setDstAb(copyEnv.getDstFolder().resolve(dst));
					return StepResult.tsudukanaiStepResult();
				}
			}
		}
		return StepResult.tsudukuStepResult();
	}

	@Override
	public String name() {
		return "files-in-java-package";
	}

}
