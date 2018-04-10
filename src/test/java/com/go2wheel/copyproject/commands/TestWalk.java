package com.go2wheel.copyproject.commands;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.Test;

import com.go2wheel.copyproject.UtilForTe;

public class TestWalk {
	
	@Test
	public void t() throws IOException {
		Path wpr = UtilForTe.getPathInThisProjectRelative("fixtures/walk");
		try (Stream<Path> files = Files.walk(wpr)) {
			long c = files.map(p -> wpr.relativize(p)).count();
			assertThat("Should include start folder itself.", c, equalTo(6L)); 
		}
	}

}
