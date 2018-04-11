package com.go2wheel.copyproject.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.Optional;
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
import com.go2wheel.copyproject.exception.FilesWalkException;
import com.go2wheel.copyproject.exception.NoIgnoreFileException;
import com.go2wheel.copyproject.exception.SrcFolderNotExistException;
import com.go2wheel.copyproject.util.GitIgnoreFileReader;
import com.go2wheel.copyproject.util.PathUtil;
import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;
import com.go2wheel.copyproject.value.CopyDescriptionBuilder;
import com.go2wheel.copyproject.value.CopyResult;


@ShellComponent()
public class CopyProjectCommands {

	public static String PACKAGE_PATTERN = "^([a-z][a-z0-9]*?\\.?)*([a-z][a-z0-9]*?)+$";
	
	private static String COPY_IGNORE_FILE = "copyignore.txt";
	
	private static String GIT_IGNORE_FILE = ".gitignore";

	@SuppressWarnings("unused")
	private Terminal terminal;

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
	public CopyResult copyProject(@NotNull File srcFolder,
			@NotNull File dstFolder,
			@ShellOption(defaultValue="world")
			@Pattern(regexp = "^([a-z][a-z0-9]*?\\.?)*([a-z][a-z0-9]*?)+$") String srcRootPackage,
			@Pattern(regexp = "^([a-z][a-z0-9]*?\\.?)*([a-z][a-z0-9]*?)+$") String dstRootPackage) {
		return doCopy(srcFolder.toPath().toAbsolutePath().normalize(), dstFolder.toPath().toAbsolutePath().normalize(), srcRootPackage, dstRootPackage);
	}

	@Bean
	public PromptProvider myPromptProvider() {
		return () -> new AttributedString("my-shell:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
	}
	
	protected CopyResult doCopy(Path srcFolder, Path dstFolder, String srcRootPackage, String dstRootPackage) {
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
		return do1(ignoreFile, srcFolder, dstFolder, PathUtil.replaceDotWithSlash(srcRootPackage), PathUtil.replaceDotWithSlash(dstRootPackage));
	}
	
	protected CopyResult do1(Path ignoreFile, Path srcFolder, Path dstFolder, String srcRootPackageSlash, String dstRootPackageSlash) {
		
		List<PathMatcher> pms = GitIgnoreFileReader.ignoreMatchers(ignoreFile);
		CopyDescriptionBuilder cdBuilder = new CopyDescriptionBuilder(srcFolder, dstFolder, srcRootPackageSlash, dstRootPackageSlash, pms);
		
		try (Stream<Path> files = Files.walk(srcFolder)) {
			return files.map(p -> srcFolder.relativize(p).normalize()).map(p -> cdBuilder.buildOne(p)).map(copyDescription -> {
				
				if (copyDescription.isIgnored()) {
					return copyDescription;
				}
				
				if (copyDescription.createTargetDirectoryIfNeed()) {
					return copyDescription;
				}
				return copyOneFile(copyDescription);

			}).collect(CopyResult::new, CopyResult::accept, CopyResult::combine);
		} catch (IOException e) {
			throw new FilesWalkException(e.getMessage());
		}
	}
	
	protected CopyDescription copyOneFile(CopyDescription copyDescription) {
		try {
			Files.copy(copyDescription.getSrcAb(), copyDescription.getDstAb());
			copyDescription.setState(COPY_STATE.FILE_COPY_SUCCESSED);
		} catch (IOException e) {
			copyDescription.setState(COPY_STATE.FILE_COPY_FAILED);
		}
		return copyDescription;
	}
}
