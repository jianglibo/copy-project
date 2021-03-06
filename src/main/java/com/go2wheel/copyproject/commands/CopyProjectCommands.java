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
import com.go2wheel.copyproject.pathadjuster.PathAdjuster;
import com.go2wheel.copyproject.resulthandler.CopyResultHandler;
import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;
import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyEnv;
import com.go2wheel.copyproject.value.CopyResult;
import com.go2wheel.copyproject.value.CopyResult.CopyResultType;
import com.go2wheel.copyproject.value.StepResult;

@ShellComponent()
public class CopyProjectCommands {

	public static String PACKAGE_PATTERN = "^([a-z][a-z0-9]*?\\.?)*([a-z][a-z0-9]*?)+$";

	private CopyEnv copyEnv;

	@SuppressWarnings("unused")
	private Terminal terminal;

	private CopyPerformer copyHub;

	private IgnoreChecker ignoreHub;

	private PathAdjuster pathAdjuster;

	@Autowired
	private CopyResultHandler copyResultHandler;

	@ShellMethod(value = "Get the last copy result.")
	public CopyResult copyResult() {
		if (copyResultHandler.getLastCopyResult() == null) {
			return CopyResult.emptyResult();
		}
		return copyResultHandler.getLastCopyResult().changeResultType(CopyResultType.DETAILED);
	}

	/**
	 * Validates happen before command execution.
	 * 
	 * @param srcFolder
	 * @param dstFolder
	 * @param srcRootPackage
	 * @param dstRootPackage
	 */
	@ShellMethod(value = "copy a project from an existing project.")
	public CopyResult copyProject(
			@ShellOption(help = "The project folder to copy from. Must exist.") @NotNull Path srcFolder,
			@ShellOption(help = "The folder copy to. Mustn't exist.") @NotNull Path dstFolder,
			@ShellOption(defaultValue = "no.source.root.package") @Pattern(regexp = "^([a-z][a-z0-9]*?\\.?)*([a-z][a-z0-9]*?)+$") String srcRootPackage,
			@ShellOption(defaultValue = "no.dst.root.package") @Pattern(regexp = "^([a-z][a-z0-9]*?\\.?)*([a-z][a-z0-9]*?)+$") String dstRootPackage) {
		copyEnv = new CopyEnv(srcFolder.toAbsolutePath().normalize(),
				dstFolder.toAbsolutePath().normalize(), srcRootPackage, dstRootPackage);
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

		try (Stream<Path> files = Files.walk(copyEnv.getSrcFolder())) {
			CopyResult cr = files.map(p -> copyEnv.getSrcFolder().relativize(p).normalize())
					.map(p -> new CopyDescription(copyEnv.getSrcFolder(), p)).map(copyDescription -> {
						pathAdjuster.adjust(copyEnv, copyDescription);
						return copyDescription;
					}).map(copyDescription -> {
						StepResult<Boolean> ignoreResult = ignoreHub.ignore(copyEnv, copyDescription);
						if (ignoreResult.getResult()) {
							copyDescription.setState(COPY_STATE.IGNORED);
						}
						return copyDescription;
					}).map(copyDescription -> {

						if (copyDescription.isIgnored()) {
							return copyDescription;
						}

						copyHub.copy(copyEnv, copyDescription);
						return copyDescription;

					}).collect(CopyResult::new, CopyResult::accept, CopyResult::combine);
			cr.addDescribeLine(String.format("copy task: %s -> %s", copyEnv.getSrcFolder().toAbsolutePath().toString(), copyEnv.getDstFolder().toAbsolutePath().toString()));
			return cr;
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

	@Autowired
	@Qualifier("main")
	public void setPathAdjuster(PathAdjuster pathAdjuster) {
		this.pathAdjuster = pathAdjuster;
	}

	// protected CopyDescription copyOneFile(CopyDescription copyDescription) {
	// try {
	// switch (copyDescription.getCopyType()) {
	// case JAVA_SOURCE:
	//// List<String> lines = Files.lines(copyDescription.getSrcAb()).map(line -> {
	//// // package statement
	////
	//// });
	//// break;
	// default:
	// Files.copy(copyDescription.getSrcAb(), copyDescription.getDstAb());
	// break;
	// }
	// copyDescription.setState(COPY_STATE.FILE_COPY_SUCCESSED);
	// } catch (IOException e) {
	// copyDescription.setState(COPY_STATE.FILE_COPY_FAILED);
	// }
	// return copyDescription;
	// }
}
