package com.go2wheel.copyproject.value;

import java.nio.file.Files;
import java.nio.file.Path;

public class CopyDescription {

	public static enum COPY_STATE {
		START, IGNORED, FILE_COPY_FAILED, FILE_COPY_SUCCESSED 
	}

	private Path srcRelative;
	private Path srcAb;
	private Path dstAb;
	
	private final boolean directory;

	private boolean relativePathChanged;

	private COPY_STATE state = COPY_STATE.START;
	
	public CopyDescription(Path srcFolder, Path srcRelative) {
		this.srcRelative = srcRelative;
		this.srcAb = srcFolder.resolve(srcRelative);
		this.directory = Files.isDirectory(this.srcAb);
	}

	public CopyDescription(Path srcRelative, Path srcAb, Path dstAb, boolean relativePathChanged) {
		super();
		this.srcRelative = srcRelative;
		this.srcAb = srcAb;
		this.dstAb = dstAb;
		this.setRelativePathChanged(relativePathChanged);
		this.directory = Files.isDirectory(this.srcAb);
		initMe();
	}

	private void initMe() {
	}
	
	@Override
	public String toString() {
		return String.format("[%s, %s, %s -> %s]", getState(), isRelativePathChanged(), getSrcAbsolute(), getDstAb());
	}
	
	
	public boolean isIgnored() {
		return this.state == COPY_STATE.IGNORED;
	}
	
	public Path getSrcRelative() {
		return srcRelative;
	}
	
	public Path getSrcAbsolute() {
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

	public boolean isDirectory() {
		return directory;
	}

}
