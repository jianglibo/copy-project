package com.go2wheel.copyproject;

import java.nio.file.Path;
import java.nio.file.Paths;

public class UtilForTe {
	
	public static void printme(Object o) {
		System.out.println(o);
	}
	
	
	public static Path getPathInThisProjectRelative(String fn) {
		Path currentRelativePath = Paths.get("").toAbsolutePath();
		return currentRelativePath.relativize(currentRelativePath.resolve(fn));
	}

}
