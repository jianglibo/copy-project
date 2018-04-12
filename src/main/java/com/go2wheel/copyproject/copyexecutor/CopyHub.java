package com.go2wheel.copyproject.copyexecutor;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.go2wheel.copyproject.util.PriorityComparator;
import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;

public class CopyHub implements CopyExecutor {
	
	private List<CopyExecutor> copyExecutors;

	@Override
	public boolean copy(CopyEnv copyEnv, CopyDescription copyDescription) {
		for(CopyExecutor ce : copyExecutors) {
			if (ce.copy(copyEnv, copyDescription)) {
				return true;
			}
		}
		return false;
	}
	
	
	@Autowired
	public void setCopyExecutors(List<CopyExecutor> copyExecutors) {
		Collections.sort(copyExecutors, new PriorityComparator());
		this.copyExecutors = copyExecutors;
	}

	protected List<CopyExecutor> getCopyExecutors() {
		return copyExecutors;
	}
	
}
