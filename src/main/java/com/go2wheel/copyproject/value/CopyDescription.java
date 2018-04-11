package com.go2wheel.copyproject.value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyDescription {

	public static enum COPY_TYPE {
		JAVA_SOURCE, OTHERS
	}
	
	public static enum COPY_STATE {
		START, IGNORED, DIR_CREATE_SUCCESS, DIR_CREATE_FAILED, DIR_EXISTS, FILE_COPY_FAILED, FILE_COPY_SUCCESSED 
	}

	private Path srcAb;
	private Path dstAb;

	private boolean relativePathChanged;

	private COPY_TYPE copyType = COPY_TYPE.OTHERS;
	
	private COPY_STATE state = COPY_STATE.START;
	
	protected CopyDescription(Path srcAb) {
		super();
		this.srcAb = srcAb;
		this.state = COPY_STATE.IGNORED;
	}

	protected CopyDescription(Path srcAb, Path dstAb, boolean relativePathChanged) {
		super();
		this.srcAb = srcAb;
		this.dstAb = dstAb;
		this.setRelativePathChanged(relativePathChanged);
		initMe();
	}

	private void initMe() {
		String s = srcAb.getFileName().toString();
		if (s.endsWith(".java")) {
			copyType = COPY_TYPE.JAVA_SOURCE;
		}
	}
	
	
	public boolean isIgnored() {
		return this.state == COPY_STATE.IGNORED;
	}
	
	public boolean isDirectoryOperated() {
		return this.state == COPY_STATE.DIR_CREATE_FAILED || this.state == COPY_STATE.DIR_CREATE_SUCCESS || this.state == COPY_STATE.DIR_EXISTS;
	}
	
	
	public boolean createTargetDirectoryIfNeed() {
		if (Files.isDirectory(srcAb)) {
			if (Files.exists(dstAb)) {
				this.state = COPY_STATE.DIR_EXISTS;
			} else {
				try {
					Files.createDirectories(dstAb);
					this.state = COPY_STATE.DIR_CREATE_SUCCESS;
				} catch (IOException e) {
					this.state = COPY_STATE.DIR_CREATE_FAILED;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public Path getSrcAb() {
		return srcAb;
	}

	public void setSrcAb(Path srcAb) {
		this.srcAb = srcAb;
	}

	public Path getDstAb() {
		return dstAb;
	}

	public void setDstAb(Path dstAb) {
		this.dstAb = dstAb;
	}

	public COPY_TYPE getCopyType() {
		return copyType;
	}

	public void setCopyType(COPY_TYPE copyType) {
		this.copyType = copyType;
	}

	public boolean isRelativePathChanged() {
		return relativePathChanged;
	}

	public void setRelativePathChanged(boolean relativePathChanged) {
		this.relativePathChanged = relativePathChanged;
	}

	public COPY_STATE getState() {
		return state;
	}

	public void setState(COPY_STATE state) {
		this.state = state;
	}

}
