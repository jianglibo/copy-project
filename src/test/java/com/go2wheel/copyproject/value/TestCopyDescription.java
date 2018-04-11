package com.go2wheel.copyproject.value;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

import com.go2wheel.copyproject.value.CopyDescription.COPY_TYPE;

public class TestCopyDescription {
	
	private CopyDescriptionBuilder cdBuilder = new CopyDescriptionBuilder(Paths.get(""), Paths.get(""), "a.b", "c.d", new ArrayList<>());

	
	@Test
	public void tPackageUnMatched() {
		Path p2 = Paths.get("abc");
		CopyDescription cd = cdBuilder.buildOne(p2);
		assertThat(cd.getCopyType(), equalTo(COPY_TYPE.OTHERS));
		assertFalse("no package path is replaced.", cd.isRelativePathChanged());
	}

	@Test
	public void tPackageMatched() {
		Path p2 = Paths.get("a/b/abc");
		CopyDescription cd = cdBuilder.buildOne(p2);
		assertThat(cd.getCopyType(), equalTo(COPY_TYPE.OTHERS));
		assertTrue("package path is replaced.", cd.isRelativePathChanged());
	}
	
	@Test
	public void tPackageMatchedAndType() {
		Path p2 = Paths.get("a/b/abc.java");
		CopyDescription cd = cdBuilder.buildOne(p2);
		assertThat(cd.getCopyType(), equalTo(COPY_TYPE.JAVA_SOURCE));
		assertTrue("package path is replaced.", cd.isRelativePathChanged());
	}

}
