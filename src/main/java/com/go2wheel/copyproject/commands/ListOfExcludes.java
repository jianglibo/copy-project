package com.go2wheel.copyproject.commands;

import java.util.Arrays;
import java.util.List;

public class ListOfExcludes {
	
	private List<String> excludes;
	
	public ListOfExcludes(String concated) {
		this.excludes = Arrays.asList(concated.split(";"));
	}

	public List<String> getExcludes() {
		return excludes;
	}

	public void setExcludes(List<String> excludes) {
		this.excludes = excludes;
	}

}
