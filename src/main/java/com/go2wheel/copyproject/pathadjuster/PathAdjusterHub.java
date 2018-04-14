package com.go2wheel.copyproject.pathadjuster;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.go2wheel.copyproject.DisabledPlugin;
import com.go2wheel.copyproject.util.PriorityComparator;
import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;

public class PathAdjusterHub implements PathAdjuster {

	private List<PathAdjuster> adjusters;

	private DisabledPlugin disabledPlugin;

	@Autowired
	public void setDisabledPlugin(DisabledPlugin disabledPlugin) {
		this.disabledPlugin = disabledPlugin;
	}

	@Override
	public StepResult<Void> adjust(CopyEnv copyEnv, CopyDescription copyDescription) {
		StepResult<Void> sr = null;
		for (PathAdjuster pa : adjusters) {
			if (!disabledPlugin.getPerformers().contains(pa.name())) {
				sr = pa.adjust(copyEnv, copyDescription);
				if (!sr.isTsuduku()) {
					return sr;
				}
			}
		}
		return sr;
	}

	public List<PathAdjuster> getAdjusters() {
		return adjusters;
	}

	@Autowired
	public void setAdjusters(List<PathAdjuster> adjusters) {
		Collections.sort(adjusters, new PriorityComparator());
		this.adjusters = adjusters;
	}

	@Override
	public String name() {
		return "adjuster-hub";
	}

}
