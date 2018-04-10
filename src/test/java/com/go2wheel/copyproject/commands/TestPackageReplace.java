package com.go2wheel.copyproject.commands;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class TestPackageReplace {
	
	@Test
	public void tMatchOrigin() {
		CopyProjectCommands cpc = new CopyProjectCommands();
		Path np = cpc.calNewPath(Paths.get("src/main/a/b/c/e.txt"), "a/b/c", "d/e/f");
		assertThat("new path should be 'src/main/d/e/f/e.txt'", np, equalTo(Paths.get("src/main/d/e/f/e.txt")));
	}
	
	@Test
	public void tunMatchOrigin() {
		CopyProjectCommands cpc = new CopyProjectCommands();
		Path np = cpc.calNewPath(Paths.get("src/main/a/b/c/e.txt"), "a/c/b", "d/e/f");
		assertThat("new path should be equal to origin.", np, equalTo(Paths.get("src/main/a/b/c/e.txt")));
	}

}
