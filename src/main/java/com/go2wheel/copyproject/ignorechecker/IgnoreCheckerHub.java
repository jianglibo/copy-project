package com.go2wheel.copyproject.ignorechecker;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.go2wheel.copyproject.util.PriorityComparator;
import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;

public class IgnoreCheckerHub implements IgnoreChecker {
	
	private List<IgnoreChecker> checkers;

	@Override
	public boolean stopCheckNext() {
		return true;
	}

	@Override
	public boolean ignore(CopyEnv copyEnv, CopyDescription copyDescription) {
		for( IgnoreChecker ic : checkers) {
			boolean ig = ic.ignore(copyEnv, copyDescription);
			if (ig) {
				return true;
			} else if(ic.stopCheckNext()) {
				return ig;
			}
		}
		return false;
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
