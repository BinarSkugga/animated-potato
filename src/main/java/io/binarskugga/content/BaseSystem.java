package io.binarskugga.content;

import io.binarskugga.event.EventBus;
import io.binarskugga.event.ISubscriber;
import lombok.Getter;
import lombok.Setter;

public abstract class BaseSystem implements ISystem, ISubscriber {
	@Getter @Setter private ComponentLinker components;

	public BaseSystem() {
		EventBus.global.subscribe(this);
	}
}
