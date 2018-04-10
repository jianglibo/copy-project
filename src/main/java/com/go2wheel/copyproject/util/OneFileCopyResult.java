package com.go2wheel.copyproject.util;

import java.nio.file.Path;

public class OneFileCopyResult {

	private boolean success;
	
	private Path srcPath;
	
	private Path dstPath;
	
	public OneFileCopyResult() {
		this.success = true;
	}
	
	public OneFileCopyResult(Path srcPath, Path dstPath) {
		this.success = false;
		this.srcPath = srcPath;
		this.dstPath = dstPath;
	}
	

	public OneFileCopyResult(Path[] pathPair) {
		this.success = false;
		this.srcPath = pathPair[0];
		this.dstPath = pathPair[1];
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Path getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(Path srcPath) {
		this.srcPath = srcPath;
	}

	public Path getDstPath() {
		return dstPath;
	}

	public void setDstPath(Path dstPath) {
		this.dstPath = dstPath;
	}
	
}
