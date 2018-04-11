package com.go2wheel.copyproject.commands;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.go2wheel.copyproject.UtilForTe;
import com.go2wheel.copyproject.exception.DstFolderAlreadyExistException;
import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyResult;
import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;

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
			Files.walkFileTree(tmpFolder, new SimpleFileVisitor<Path>() {
			    @Override
			    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			        throws IOException
			    {
			    	Files.delete(file);
			        return FileVisitResult.CONTINUE;
			    }
		    @Override
		    public FileVisitResult postVisitDirectory(Path dir, IOException exc)
		        throws IOException
		    {
		    	Files.delete(dir);
		        if (exc != null)
		            throw exc;
		        return FileVisitResult.CONTINUE;
		    }
			});
		}
	}
	
	@Test(expected = DstFolderAlreadyExistException.class)
	public void tTargetExists() throws IOException {
		Path target = tmpFolder;
		CopyProjectCommands cpc = new CopyProjectCommands();
		cpc.copyProject(origin.toFile(), target.toFile(), "com.demo.pk", "org.demo.pp");
	}
	
	@Test()
	public void t() throws IOException {
		Path target = tmpFolder.resolve("inner");
		CopyProjectCommands cpc = new CopyProjectCommands();
		CopyResult cr = cpc.copyProject(origin.toFile(), target.toFile(), "com.demo.pk", "org.demo.pp");
		
		assertThat("all file should be copied.", cr.total(), equalTo(fileCount));
		
		List<CopyDescription> ignored = cr.getDescriptionMap().get(COPY_STATE.IGNORED);
		ignored.forEach(cd -> {
			System.out.println(cd.getSrcAb());
		});
		assertThat(ignored.size(), equalTo(6));
		
		List<CopyDescription> fileCopyfailed = cr.getDescriptionMap().get(COPY_STATE.FILE_COPY_FAILED);
		List<CopyDescription> dirCopyfailed = cr.getDescriptionMap().get(COPY_STATE.DIR_CREATE_FAILED);
		
		assertTrue("no file coping failed.", fileCopyfailed.isEmpty());
		assertTrue("no directory coping failed.", dirCopyfailed.isEmpty());
		
		
	}

}
