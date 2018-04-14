package com.go2wheel.copyproject;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.go2wheel.copyproject.copyperformer.CopyHub;
import com.go2wheel.copyproject.copyperformer.CopyPerformer;
import com.go2wheel.copyproject.copyperformer.DefaultCopyPerformer;
import com.go2wheel.copyproject.copyperformer.DirectoryCopyPerformer;
import com.go2wheel.copyproject.ignorechecker.GlobIgnoreChecker;
import com.go2wheel.copyproject.ignorechecker.IgnoreChecker;
import com.go2wheel.copyproject.ignorechecker.IgnoreCheckerHub;
import com.go2wheel.copyproject.pathadjuster.DefaultPathAdjuster;
import com.go2wheel.copyproject.pathadjuster.PathAdjuster;
import com.go2wheel.copyproject.pathadjuster.PathAdjusterHub;
import com.go2wheel.copyproject.value.CopyEnv;

public class UtilForTe {
	
	private static DisabledPlugin emptyDisabledPlugin() {
		DisabledPlugin dp = new DisabledPlugin();
		dp.setIgnorecheckers(new HashSet<>());
		dp.setPathadjusters(new HashSet<>());
		dp.setPerformers(new HashSet<>());
		return dp;
	}
	
	public static void printme(Object o) {
		System.out.println(o);
	}

	public static Path getPathInThisProjectRelative(String fn) {
		Path currentRelativePath = Paths.get("").toAbsolutePath();
		return currentRelativePath.relativize(currentRelativePath.resolve(fn));
	}
	
	public static CopyHub createCopyHub(CopyPerformer...ces) {
		CopyHub ch = new CopyHub();
		ch.setDisabledPlugin(emptyDisabledPlugin());
		List<CopyPerformer> cesl = new ArrayList<>();
		cesl.addAll(Arrays.asList(ces));
		cesl.add(new DefaultCopyPerformer());
		cesl.add(new DirectoryCopyPerformer());
		ch.setCopyExecutors(cesl);
		return ch;
	}
	
	
	public static IgnoreCheckerHub createIgnoreHub(IgnoreChecker...igs) {
		IgnoreCheckerHub igh = new IgnoreCheckerHub();
		igh.setDisabledPlugin(emptyDisabledPlugin());
		List<IgnoreChecker> cesl = new ArrayList<>();
		cesl.addAll(Arrays.asList(igs));
		if (igs.length == 0) {
			cesl.add(new GlobIgnoreChecker());
		}
		igh.setCheckers(cesl);
		return igh;
	}
	
	public static PathAdjusterHub createPathAdjusterHub(PathAdjuster...pas) {
		PathAdjusterHub igh = new PathAdjusterHub();
		igh.setDisabledPlugin(emptyDisabledPlugin());
		List<PathAdjuster> cesl = new ArrayList<>();
		cesl.addAll(Arrays.asList(pas));
		cesl.add(new DefaultPathAdjuster());
		igh.setAdjusters(cesl);
		return igh;

		
	}
	
	public static void deleteFolder(Path folder) throws IOException {
		Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
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
	
	
	public static CopyEnv copyEnv() {
		return new CopyEnv(Paths.get(""), Paths.get("..", "abc"), "a.b.c", "c.d.e");
	}
	
	public static CopyEnv copyEnv(String srcRootPackageDot, String dstRootPackageDot) {
		return new CopyEnv(Paths.get(""), Paths.get("..", "abc"), srcRootPackageDot, dstRootPackageDot);
	}
	
	public static CopyEnv copyEnvDemoproject(Path dstFolder, String srcRootPackageDot, String dstRootPackageDot) {
		return new CopyEnv(Paths.get("fixtures", "demoproject"), dstFolder, srcRootPackageDot, dstRootPackageDot);
	}
	
	public static Path createTmpDirectory() throws IOException {
		return Files.createTempDirectory("tmpdirforpc");
	}
	
	public static Path createFileTree(String...fns) throws IOException {
		Path tmpFolder = Files.createTempDirectory("tmpfiletrees");
		for(String fn : fns) {
			String sanitized = fn.trim().replace('\\', '/');
			if (sanitized.startsWith("/")) {
				sanitized = sanitized.substring(1);
			}
			Path p = Paths.get(sanitized);
			String fileName = p.getFileName().toString();
			Path parent = p.getParent();
			Path brandNewDirectory;
			if (parent != null) {
				brandNewDirectory = Files.createDirectories(tmpFolder.resolve(parent));
			} else {
				brandNewDirectory = tmpFolder;
			}
			if (fileName.indexOf('.') != -1) { // it's a file.
				Files.write(brandNewDirectory.resolve(fileName), "hello".getBytes());
			} else {
				Files.createDirectories(brandNewDirectory.resolve(fileName));
			}
		}
		return tmpFolder;
	}

}
