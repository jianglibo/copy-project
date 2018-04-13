package com.go2wheel.copyproject.copyperformer;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.go2wheel.copyproject.util.PriorityComparator;
import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;

public class CopyHub implements CopyPerformer {
	
	private List<CopyPerformer> copyPerformers;

	@Override
	public StepResult<Void> copy(CopyEnv copyEnv, CopyDescription copyDescription) {
		StepResult<Void> sr = null;
		for(CopyPerformer ce : copyPerformers) {
			sr = ce.copy(copyEnv, copyDescription);
			if (!sr.isTsuduku()) {
				return sr;
			}
		}
		return sr;
	}
	
	
	@Autowired
	public void setCopyExecutors(List<CopyPerformer> copyPerformers) {
		Collections.sort(copyPerformers, new PriorityComparator());
		this.copyPerformers = copyPerformers;
	}

	protected List<CopyPerformer> getCopyPerformers() {
		return copyPerformers;
	}
	
}
