package com.go2wheel.copyproject.ignorechecker;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.go2wheel.copyproject.util.PriorityComparator;
import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.StepResult;

public class IgnoreCheckerHub implements IgnoreChecker {
	
	private List<IgnoreChecker> checkers;


	@Override
	public StepResult<Boolean> ignore(CopyEnv copyEnv, CopyDescription copyDescription) {
		StepResult<Boolean> sr = null;
		for( IgnoreChecker ic : checkers) {
			sr = ic.ignore(copyEnv, copyDescription);
			if (!sr.isTsuduku()) {
				return sr;
			}
		}
		return sr;
	}

	public List<IgnoreChecker> getCheckers() {
		return checkers;
	}

	@Autowired
	public void setCheckers(List<IgnoreChecker> checkers) {
		Collections.sort(checkers, new PriorityComparator());
		this.checkers = checkers;
	}

	@Override
	public void initCondition(CopyEnv copyEnv) {
		for (IgnoreChecker ic : checkers) {
			ic.initCondition(copyEnv);
		}
	}

}
