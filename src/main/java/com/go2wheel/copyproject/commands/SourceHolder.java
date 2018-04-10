package com.go2wheel.copyproject.commands;

import java.io.File;

public class SourceHolder {
	
	private File directory;
	
	private String rootPackage;

	public SourceHolder(File directory, String rootPackage) {
		super();
		this.directory = directory;
		this.rootPackage = rootPackage;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public String getRootPackage() {
		return rootPackage;
	}

	public void setRootPackage(String rootPackage) {
		this.rootPackage = rootPackage;
	}
	
	
	

}
