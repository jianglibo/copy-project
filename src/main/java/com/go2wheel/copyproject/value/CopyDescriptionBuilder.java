package com.go2wheel.copyproject.value;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.go2wheel.copyproject.exception.TargetOverlapSourceException;

public class CopyDescriptionBuilder {
	
	private final String srcRootPackageSlash;
	private final String dstRootPackageSlash;
	
	private final Path srcFolder;
	private final Path dstFolder;
	
	public CopyDescriptionBuilder(Path srcFolder, Path dstFolder, String srcRootPackage, String dstRootPackage) {
		this.srcFolder = srcFolder.normalize();
		this.dstFolder = dstFolder.normalize();
		this.srcRootPackageSlash = srcRootPackage.replace('.', '/');
		this.dstRootPackageSlash = dstRootPackage.replace('.', '/');
		if (this.dstFolder.startsWith(this.srcFolder) || this.srcFolder.equals(this.dstFolder)) {
			throw new TargetOverlapSourceException(this.srcFolder.toString(), this.dstFolder.toString());
		}
	}
	
	public CopyDescriptionBuilder(CopyEnv copyEnv) {
		this.srcFolder = copyEnv.getSrcFolder();
		this.dstFolder = copyEnv.getDstFolder();
		this.srcRootPackageSlash = copyEnv.getSrcRootPackageDot().replace('.', '/');
		this.dstRootPackageSlash = copyEnv.getDstRootPackageDot().replace('.', '/');
		if (this.dstFolder.startsWith(this.srcFolder) || this.srcFolder.equals(this.dstFolder)) {
			throw new TargetOverlapSourceException(this.srcFolder.toString(), this.dstFolder.toString());
		}
	}
	
	public CopyDescription buildOne(Path toCopyRelative) {
		Path origin = toCopyRelative.normalize();
		String replaced = origin.toString().replaceAll("\\\\", "/").replace(srcRootPackageSlash, dstRootPackageSlash);
		Path target = Paths.get(replaced).normalize();
		boolean changed = !origin.equals(target);
		return new CopyDescription(origin, srcFolder.resolve(origin), dstFolder.resolve(target), changed);
	}
	
}
