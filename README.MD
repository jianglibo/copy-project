## 从模板项目拷贝出一个新的项目。（模板项目不限语言和结构）

如果您经常做类似的项目，十有八九会有一个像是模板的项目。此工具用来复制一个项目。除去不需要复制的部分，所有的源文件都会流过一连串的CopyPerformer，根据文件的特征执行相应的操作。比如对于JAVA源文件，可能需要修改文件内package语句的值。

## 如何忽略文件和目录？

默认实现的是基于GLOB规则，可以处理源目录中.gitignore.如果不满足，可以自己模仿写一个。

```java
@Component
public class GlobIgnoreChecker implements IgnoreChecker {
	
	private static String COPY_IGNORE_FILE = "copyignore.txt";
	
	private static String GIT_IGNORE_FILE = ".gitignore";
	
	private List<PathMatcher> matchers = new ArrayList<>();

	@Override
	public boolean stopCheckNext() {
		return false;
	}

	@Override
	public boolean ignore(CopyEnv copyEnv, CopyDescription copyDescription) {
		return matchers.stream().anyMatch(pm -> pm.matches(copyDescription.getSrcRelative()));
	}

	@Override
	public void initCondition(CopyEnv copyEnv) {
		Path ignoreFile = copyEnv.getSrcFolder().resolve(COPY_IGNORE_FILE);
		if (!Files.exists(ignoreFile)) {
			ignoreFile = copyEnv.getSrcFolder().resolve(GIT_IGNORE_FILE);
		}
		if (Files.exists(ignoreFile) && Files.isRegularFile(ignoreFile)) {
			matchers = GitIgnoreFileReader.ignoreMatchers(ignoreFile);
		}
	}
```

## 如何添加自定义的复制？

编写一个实现CopyPerformer的类，系统会自动加载这个类。你可以获取整个文件，所以你可以决定怎么处理它。

```java
@Component
@Priority(Integer.MAX_VALUE)
public class DefaultCopyPerformer implements CopyPerformer {
	
	private Logger logger = LoggerFactory.getLogger(DefaultCopyPerformer.class);

	@Override
	public boolean copy(CopyEnv copyEnv, CopyDescription copyDescription) {
		try {
			if (Files.exists(copyDescription.getDstAb())) {
				return true;
			}
			Files.copy(copyDescription.getSrcAb(), copyDescription.getDstAb());
			copyDescription.setState(COPY_STATE.FILE_COPY_SUCCESSED);
		} catch (IOException e) {
			logger.info(e.getMessage());
			copyDescription.setState(COPY_STATE.FILE_COPY_FAILED);
		}
		return true;
	}

}
```