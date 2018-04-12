## Copy a java project from a template java project.

如果您经常做类似的项目，十有八九会有一个像是模板的项目。此工具用来复制一个项目，考虑到以下的需求：

1. 忽略不需要复制的部分，比如bin/ build/等目录
2. 修改Java源文件内的package名，以适应新的包名。
3. 修改build.gradle或者pom.xml里的相关内容

## 如何添加自定义的复制？

只要编写一个实现CopyExecutor的类即可，系统会自动加载这个类。你可以获取整个文件，所以你可以决定怎么处理它。

```java
@Component
@Priority(Integer.MAX_VALUE)
public class DefaultCopyExecutor implements CopyExecutor {
	
	private Logger logger = LoggerFactory.getLogger(DefaultCopyExecutor.class);

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