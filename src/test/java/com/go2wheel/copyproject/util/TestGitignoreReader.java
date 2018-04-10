package com.go2wheel.copyproject.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;

import org.junit.Test;

import com.go2wheel.copyproject.UtilForTe;

public class TestGitignoreReader {
	
	@Test
	public void t() {
		Path ip = UtilForTe.getPathInThisProjectRelative("copyignore.txt");
		Path cp = UtilForTe.getPathInThisProjectRelative("bin");
		long c = GitIgnoreFileReader.ignoreMatchers(ip).stream().filter(pm -> {
				return pm.matches(cp);
			}
		).count();
		
		assertThat(c, equalTo(1L));
	}
	
	@Test
	public void t1() {
		Path ip = UtilForTe.getPathInThisProjectRelative("fixtures/folderignore.txt");
		long mc = GitIgnoreFileReader.ignoreMatchers(ip).stream().count();
		
		assertThat(mc, equalTo(3L));

		Path cp = UtilForTe.getPathInThisProjectRelative("bin");
		long c = GitIgnoreFileReader.ignoreMatchers(ip).stream().filter(pm -> {
				return pm.matches(cp);
			}
		).count();
		
		assertThat(c, equalTo(1L));
	}

}
