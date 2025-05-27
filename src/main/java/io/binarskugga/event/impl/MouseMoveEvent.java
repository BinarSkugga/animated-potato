package io.binarskugga.event.impl;

import io.binarskugga.event.IEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MouseMoveEvent implements IEvent {
	public long window;
	public double x, y;
}
