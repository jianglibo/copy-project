package com.go2wheel.copyproject.value;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class TestCopyDescription {
	
	private CopyDescriptionBuilder cdBuilder = new CopyDescriptionBuilder(Paths.get(""), Paths.get("../abc"), "a.b", "c.d");

	
	@Test
	public void tPackageUnMatched() {
		Path p2 = Paths.get("abc");
		CopyDescription cd = cdBuilder.buildOne(p2);
		assertFalse("no package path is replaced.", cd.isRelativePathChanged());
	}

	@Test
	public void tPackageMatched() {
		Path p2 = Paths.get("a/b/abc");
		CopyDescription cd = cdBuilder.buildOne(p2);
		assertTrue("package path is replaced.", cd.isRelativePathChanged());
	}
	
	@Test
	public void tPackageMatchedAndType() {
		Path p2 = Paths.get("a/b/abc.java");
		CopyDescription cd = cdBuilder.buildOne(p2);
		assertTrue("package path is replaced.", cd.isRelativePathChanged());
	}

}
