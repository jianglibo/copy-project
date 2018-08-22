package com.go2wheel.copyproject.convert;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class String2Path implements Converter<String, Path>{
	@Override
	public Path convert(String source) {
		return Paths.get(source);
	}

}
