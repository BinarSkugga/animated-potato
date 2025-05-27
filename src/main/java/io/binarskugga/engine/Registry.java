package io.binarskugga.engine;

import java.util.concurrent.ConcurrentHashMap;

public abstract class Registry<T> extends ConcurrentHashMap<String, T> implements ILowLevelResource {
	@Override
	public void initialize() {}

	@Override
	public void destroy() {}
}
