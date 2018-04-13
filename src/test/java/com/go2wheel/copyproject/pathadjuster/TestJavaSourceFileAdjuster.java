package com.go2wheel.copyproject.pathadjuster;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;

public class TestJavaSourceFileAdjuster extends BasePathAdjusterFort {
	
	private JavaPackagedFilePathAdjuster jspa = new JavaPackagedFilePathAdjuster();
	
	private CopyEnv copyEnv = getDemoEnv("a.b.c", "d.e.f");
	
	private CopyDescription createCd(String s) {
		return new CopyDescription(copyEnv.getSrcFolder(), Paths.get(s));
	}
	
	@Test
	public void t1() {
		CopyDescription cd = createCd("a/b/c/finc");
		jspa.adjust(copyEnv, cd);
		Path rp = copyEnv.getDstRelative(cd);
		assertDstPath("even 'a/b/c/finc' is not a java file, it still needs to be changed.", rp, "d/e/f/finc");
		
	}

}
