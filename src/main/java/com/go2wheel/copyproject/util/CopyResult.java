package com.go2wheel.copyproject.util;

import java.util.ArrayList;
import java.util.List;

public class CopyResult {
	
	private List<OneFileCopyResult> failed = new ArrayList<>();

	public List<OneFileCopyResult> getFailed() {
		return failed;
	}

	public void setFailed(List<OneFileCopyResult> failed) {
		this.failed = failed;
	}

	public CopyResult add(OneFileCopyResult ocr) {
		this.failed.add(ocr);
		return this;
	}

	public CopyResult plus(CopyResult cr2) {
		this.getFailed().addAll(cr2.getFailed());
		return this;
	}
}
