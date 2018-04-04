package com.go2wheel.copyproject.commands;

import java.io.File;

public class SourceHolder {
	
	private File directory;
	
	private ListOfExcludes excludes;
	
	private String rootPackage;

	public SourceHolder(File directory, ListOfExcludes excludes, String rootPackage) {
		super();
		this.directory = directory;
		this.excludes = excludes;
		this.rootPackage = rootPackage;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public ListOfExcludes getExcludes() {
		return excludes;
	}

	public void setExcludes(ListOfExcludes excludes) {
		this.excludes = excludes;
	}

	public String getRootPackage() {
		return rootPackage;
	}

	public void setRootPackage(String rootPackage) {
		this.rootPackage = rootPackage;
	}
	
	
	

}
