package io.binarskugga.content;

import io.binarskugga.engine.Clock;
import io.binarskugga.engine.ILowLevelResource;
import io.binarskugga.engine.Scene;

public interface ISystem {
	void update(Clock clock, Scene scene);
	ISystem components(ComponentLinker components);
}
