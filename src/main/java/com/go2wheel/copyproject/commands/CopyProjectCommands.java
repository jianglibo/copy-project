package com.go2wheel.copyproject.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.go2wheel.copyproject.exception.DstFolderAlreadyExistException;
import com.go2wheel.copyproject.exception.NoIgnoreFileException;
import com.go2wheel.copyproject.exception.SrcFolderNotExistException;
import com.go2wheel.copyproject.util.GitIgnoreFileReader;
import com.go2wheel.copyproject.util.OneFileCopyResult;
import com.go2wheel.copyproject.util.PathUtil;


@ShellComponent()
public class CopyProjectCommands {

	public static String PACKAGE_PATTERN = "^([a-z][a-z0-9]*?\\.?)*([a-z][a-z0-9]*?)+$";
	
	private static String COPY_IGNORE_FILE = "copyignore.txt";
	
	private static String GIT_IGNORE_FILE = ".gitignore";

	@SuppressWarnings("unused")
	private Terminal terminal;
	
	private SourceHolder sourceHolder;

	@Autowired
	@Lazy
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	/**
	 * Validates happen before command execution.
	 * 
	 * @param srcFolder
	 * @param dstFolder
	 * @param srcRootPackage
	 * @param dstRootPackage
	 */
	@ShellMethod(value = "tell which project folder to copy.", key = "from")
	public void copyProject(@NotNull File srcFolder,
			@NotNull File dstFolder,
			@ShellOption(defaultValue="world")
			@Pattern(regexp = "^([a-z][a-z0-9]*?\\.?)*([a-z][a-z0-9]*?)+$") String srcRootPackage,
			@Pattern(regexp = "^([a-z][a-z0-9]*?\\.?)*([a-z][a-z0-9]*?)+$") String dstRootPackage) {
		this.sourceHolder = new SourceHolder(srcFolder, srcRootPackage);
		doCopy(srcFolder.toPath().toAbsolutePath().normalize(), dstFolder.toPath().toAbsolutePath().normalize(), srcRootPackage, dstRootPackage);
	}

	@Bean
	public PromptProvider myPromptProvider() {
		return () -> new AttributedString("my-shell:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
	}
	
	protected void doCopy(Path srcFolder, Path dstFolder, String srcRootPackage, String dstRootPackage) {
		if (!(Files.exists(srcFolder) && Files.isDirectory(srcFolder))) {
			throw new SrcFolderNotExistException(srcFolder.toAbsolutePath().normalize().toString());
		}
		if (Files.exists(dstFolder)) {
			throw new DstFolderAlreadyExistException(dstFolder.toAbsolutePath().normalize().toString());
		}
		
		Path ignoreFile = srcFolder.resolve(COPY_IGNORE_FILE);
		if (!Files.exists(ignoreFile)) {
			ignoreFile = srcFolder.resolve(GIT_IGNORE_FILE);
		}
		
		if (!Files.exists(ignoreFile)) {
			throw new NoIgnoreFileException();
		}
		do1(ignoreFile, srcFolder, dstFolder, PathUtil.replaceDotWithSlash(srcRootPackage), PathUtil.replaceDotWithSlash(dstRootPackage));
	}
	
	protected void do1(Path ignoreFile, Path srcFolder, Path dstFolder, String srcRootPackageSlash, String dstRootPackageSlash) {
		List<PathMatcher> pms = GitIgnoreFileReader.ignoreMatchers(ignoreFile);
		try (Stream<Path> files = Files.walk(srcFolder)) {
			files.map(p -> srcFolder.relativize(p)).filter(p ->	!(pms.stream().anyMatch(pm -> pm.matches(p)))).map(p -> {
				return new Path[] {srcFolder.resolve(p), dstFolder.resolve(calNewPath(p, srcRootPackageSlash, dstRootPackageSlash))};
			}).map(pathPair -> {
				try {
					if (Files.isDirectory(pathPair[0]) && !Files.exists(pathPair[1])) {
						Files.createDirectories(pathPair[1]);
					}
					Files.copy(pathPair[0], pathPair[1]);
					return new OneFileCopyResult();
				} catch (IOException e) {
					return new OneFileCopyResult(pathPair);
				}
			}); //.reduce(identity, accumulator, combiner)
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected Path calNewPath(Path originRelative, String srcRootPackage, String dstRootPackage) {
		String replaced = originRelative.normalize().toString().replaceAll("\\\\", "/").replace(srcRootPackage, dstRootPackage);
		return Paths.get(replaced);
	}

}
