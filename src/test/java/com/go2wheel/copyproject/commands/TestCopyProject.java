package com.go2wheel.copyproject.commands;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.go2wheel.copyproject.UtilForTe;
import com.go2wheel.copyproject.exception.DstFolderAlreadyExistException;
import com.go2wheel.copyproject.pathadjuster.JavaPackagedFilePathAdjuster;
import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;
import com.go2wheel.copyproject.value.CopyResult;

public class TestCopyProject {
	
	private Path tmpFolder;
	
	private Path origin;
	
	private long fileCount;
	
	@Before
	public void before() throws IOException {
		tmpFolder = Files.createTempDirectory("copytarget");
		origin = UtilForTe.getPathInThisProjectRelative("fixtures/demoproject");
		fileCount = Files.walk(origin).collect(Collectors.counting());
	}
	
	@After
	public void after() throws IOException {
		if (tmpFolder != null) {
			UtilForTe.deleteFolder(tmpFolder);
		}
		tmpFolder = null;
		origin = null;
		fileCount = 0;
	}
	
	@Test(expected = DstFolderAlreadyExistException.class)
	public void tTargetExists() throws IOException {
		Path target = tmpFolder;
		CopyProjectCommands cpc = new CopyProjectCommands();
		cpc.copyProject(origin.toFile(), target.toFile(), "com.demo.pk", "org.demo.pp");
	}
	
	@Test()
	public void copyDemoProject() throws IOException {
		Path target = tmpFolder.resolve("inner");
		CopyProjectCommands cpc = new CopyProjectCommands();
		cpc.setCopyHub(UtilForTe.createCopyHub());
		cpc.setIgnoreHub(UtilForTe.createIgnoreHub());
		cpc.setPathAdjuster(UtilForTe.createPathAdjusterHub(new JavaPackagedFilePathAdjuster()));
		
		CopyResult cr = cpc.copyProject(origin.toFile(), target.toFile(), "com.demo.pk", "org.demo.pp");

		assertThat("all file should be copied.", cr.total(), equalTo(fileCount));
		
		assertThat(cr.getCountMap().get(COPY_STATE.IGNORED), equalTo(6L));
		
		List<CopyDescription> directories = cr.getDescriptionMap().get(COPY_STATE.START);
		
		assertTrue("no item's state are START.", directories.size() == 0);
		
		List<CopyDescription> fileCopyfailed = cr.getDescriptionMap().get(COPY_STATE.FILE_COPY_FAILED);
		
		assertTrue("no file coping failed.", fileCopyfailed.isEmpty());
	}

}
