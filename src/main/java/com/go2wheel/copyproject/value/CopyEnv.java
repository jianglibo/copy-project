package com.go2wheel.copyproject.value;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;

public class CopyEnv {
	
	private Path srcFolder;
	private Path dstFolder;
	
	private String srcRootPackageDot;
	private String dstRootPackageDot;
	
	private Path ignoreFile;
	
	private List<PathMatcher> pathMatchers;

	public CopyEnv(Path srcFolder, Path dstFolder, String srcRootPackageDot, String dstRootPackageDot) {
		super();
		this.srcFolder = srcFolder.normalize();
		this.dstFolder = dstFolder.normalize();
		this.srcRootPackageDot = srcRootPackageDot;
		this.dstRootPackageDot = dstRootPackageDot;
	}
	
	

	public String getSrcRootPackageDot() {
		return srcRootPackageDot;
	}



	public void setSrcRootPackageDot(String srcRootPackageDot) {
		this.srcRootPackageDot = srcRootPackageDot;
	}



	public String getDstRootPackageDot() {
		return dstRootPackageDot;
	}



	public void setDstRootPackageDot(String dstRootPackageDot) {
		this.dstRootPackageDot = dstRootPackageDot;
	}



	public Path getIgnoreFile() {
		return ignoreFile;
	}



	public Path getSrcFolder() {
		return srcFolder;
	}
	public void setSrcFolder(Path srcFolder) {
		this.srcFolder = srcFolder;
	}
	public Path getDstFolder() {
		return dstFolder;
	}
	public void setDstFolder(Path dstFolder) {
		this.dstFolder = dstFolder;
	}

	public void setIgnoreFile(Path ignoreFile) {
		this.ignoreFile = ignoreFile;
	}

	public List<PathMatcher> getPathMatchers() {
		return pathMatchers;
	}

	public void setPathMatchers(List<PathMatcher> pathMatchers) {
		this.pathMatchers = pathMatchers;
	}
}
