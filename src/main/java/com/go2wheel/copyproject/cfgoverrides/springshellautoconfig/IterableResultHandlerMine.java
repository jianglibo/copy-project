package com.go2wheel.copyproject.cfgoverrides.springshellautoconfig;

import org.springframework.shell.ResultHandler;

public class IterableResultHandlerMine implements ResultHandler<Iterable> {

	private ResultHandler delegate;

	// Setter injection to avoid circular dependency at creation time
	void setDelegate(ResultHandler delegate) {
		this.delegate = delegate;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void handleResult(Iterable result) {
		for (Object o : result) {
			delegate.handleResult(o);
		}
	}
	
}
