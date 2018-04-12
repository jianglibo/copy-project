package com.go2wheel.copyproject.copyperformer;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.go2wheel.copyproject.util.PriorityComparator;
import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;

public class CopyHub implements CopyPerformer {
	
	private List<CopyPerformer> copyPerformers;

	@Override
	public boolean copy(CopyEnv copyEnv, CopyDescription copyDescription) {
		for(CopyPerformer ce : copyPerformers) {
			if (ce.copy(copyEnv, copyDescription)) {
				return true;
			}
		}
		return false;
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
