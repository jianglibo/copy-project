package com.go2wheel.copyproject.copyperformer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.go2wheel.copyproject.UtilForTe;
import com.go2wheel.copyproject.commands.CopyProjectCommands;
import com.go2wheel.copyproject.pathadjuster.JavaPackagedFilePathAdjuster;
import com.go2wheel.copyproject.value.CopyEnv;

public class TestJavaSourceFileCopy {
	
	private Path srcPath;
	private Path dstPath;
	
	@After
	public void after() throws IOException {
		if (srcPath != null) {
			UtilForTe.deleteFolder(srcPath);
		}
		if (dstPath != null && Files.exists(dstPath)) {
			UtilForTe.deleteFolder(dstPath);	
		}
		srcPath = null;
		dstPath = null;
	}

	@Test
	public void tReplacePackage() {
		CopyEnv copyEnv = UtilForTe.copyEnv("a.b.c", "d.e");
		JavaSourceFileCopy jsf = new JavaSourceFileCopy();
		
		String line = "package x.a.b.c;";
		String line1 = jsf.replaceNewPackageName(copyEnv, line);
		assertThat(line1, equalTo("package x.d.e;"));
		
		line = "package x.axb.c;";
		line1 = jsf.replaceNewPackageName(copyEnv, line);
		assertThat(line1, equalTo(line));
		
		
		line = "package x.ab.c;";
		line1 = jsf.replaceNewPackageName(copyEnv, line);
		assertThat(line1, equalTo(line));
	}
	
	@Test
	public void tIntergration() throws IOException {
		CopyProjectCommands cpc = new CopyProjectCommands();
		cpc.setCopyHub(UtilForTe.createCopyHub(new JavaSourceFileCopy()));
		cpc.setIgnoreHub(UtilForTe.createIgnoreHub());
		cpc.setPathAdjuster(UtilForTe.createPathAdjusterHub(new JavaPackagedFilePathAdjuster()));
		
		srcPath = UtilForTe.createFileTree("a/b/c.java", "a/b/c/java.txt");
		dstPath = UtilForTe.createTmpDirectory().resolve("ya");
		
		Files.write(srcPath.resolve("a/b/c.java"), Arrays.asList("package a.b.c.d;", "# a1b1c1d;"));
		Files.write(srcPath.resolve("a/b/java.txt"), Arrays.asList("package a.b.c.d;", "# a1b1c1d;"));
		
		cpc.copyProject(srcPath.toFile(), dstPath.toFile(), "a.b.c", "d.e.f");
		
		List<String> lines1 = Files.readAllLines(dstPath.resolve("a/b/c.java"));
		List<String> lines2 = Files.readAllLines(dstPath.resolve("a/b/java.txt"));
		
		assertThat("java file should be replaced.", lines1.get(0), equalTo("package d.e.f.d;"));
		assertThat("java file should be replaced.", lines1.get(1), equalTo("# a1b1c1d;"));
		
		assertThat("text file should'nt be replaced.", lines2.get(0), equalTo("package a.b.c.d;"));
		assertThat("text file should'nt be replaced.", lines2.get(1), equalTo("# a1b1c1d;"));
		
	}
}
