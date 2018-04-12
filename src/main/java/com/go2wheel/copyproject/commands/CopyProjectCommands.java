package com.go2wheel.copyproject.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.go2wheel.copyproject.copyperformer.CopyPerformer;
import com.go2wheel.copyproject.exception.DstFolderAlreadyExistException;
import com.go2wheel.copyproject.exception.FilesWalkException;
import com.go2wheel.copyproject.exception.SrcFolderNotExistException;
import com.go2wheel.copyproject.ignorechecker.IgnoreChecker;
import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;
import com.go2wheel.copyproject.value.CopyDescriptionBuilder;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.CopyResult;


@ShellComponent()
public class CopyProjectCommands {

	public static String PACKAGE_PATTERN = "^([a-z][a-z0-9]*?\\.?)*([a-z][a-z0-9]*?)+$";
	
	private CopyEnv copyEnv;

	@SuppressWarnings("unused")
	private Terminal terminal;
	
	private CopyPerformer copyHub;
	
	private IgnoreChecker ignoreHub;

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
		copyEnv = new CopyEnv(srcFolder.toPath().toAbsolutePath().normalize(), dstFolder.toPath().toAbsolutePath().normalize(), srcRootPackage, dstRootPackage);
		return doCopy(copyEnv);
	}


	
	protected CopyResult doCopy(CopyEnv copyEnv) {
		if (!(Files.exists(copyEnv.getSrcFolder()) && Files.isDirectory(copyEnv.getSrcFolder()))) {
			throw new SrcFolderNotExistException(copyEnv.getSrcFolder().toAbsolutePath().normalize().toString());
		}
		if (Files.exists(copyEnv.getDstFolder())) {
			throw new DstFolderAlreadyExistException(copyEnv.getDstFolder().toAbsolutePath().normalize().toString());
		}
		ignoreHub.initCondition(copyEnv);
		return do1(copyEnv);
	}
	
	protected CopyResult do1(CopyEnv copyEnv) {
		CopyDescriptionBuilder cdBuilder = new CopyDescriptionBuilder(copyEnv);
		try (Stream<Path> files = Files.walk(copyEnv.getSrcFolder())) {
			return files.map(p -> copyEnv.getSrcFolder().relativize(p).normalize())
					.map(p -> cdBuilder.buildOne(p))
					.map(copyDescription -> {
						boolean ignore = ignoreHub.ignore(copyEnv, copyDescription);
						if (ignore) {
							copyDescription.setState(COPY_STATE.IGNORED);
						}
						return copyDescription;
					})
					.map(copyDescription -> {
				
				if (copyDescription.isIgnored()) {
					return copyDescription;
				}
				
				if (copyDescription.createTargetDirectoryIfNeed()) {
					return copyDescription;
				}
				copyHub.copy(copyEnv, copyDescription);
				return copyDescription;

			}).collect(CopyResult::new, CopyResult::accept, CopyResult::combine);
		} catch (IOException e) {
			throw new FilesWalkException(e.getMessage());
		}
	}
	
	@Autowired
	@Qualifier("main")
	public void setCopyHub(CopyPerformer copyHub) {
		this.copyHub = copyHub;
	}

	@Autowired
	@Lazy
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	@Autowired
	@Qualifier("main")
	public void setIgnoreHub(IgnoreChecker ignoreHub) {
		this.ignoreHub = ignoreHub;
	}

	
//	protected CopyDescription copyOneFile(CopyDescription copyDescription) {
//		try {
//			switch (copyDescription.getCopyType()) {
//			case JAVA_SOURCE:
////				List<String> lines = Files.lines(copyDescription.getSrcAb()).map(line -> {
////					// package statement
////					
////				});
////				break;
//			default:
//				Files.copy(copyDescription.getSrcAb(), copyDescription.getDstAb());
//				break;
//			}
//			copyDescription.setState(COPY_STATE.FILE_COPY_SUCCESSED);
//		} catch (IOException e) {
//			copyDescription.setState(COPY_STATE.FILE_COPY_FAILED);
//		}
//		return copyDescription;
//	}
}
