package com.go2wheel.copyproject.commands;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ListOfExcludeConverter implements Converter<String, ListOfExcludes> {

	@Override
	public ListOfExcludes convert(String source) {
		return new ListOfExcludes(source);
	}

}
