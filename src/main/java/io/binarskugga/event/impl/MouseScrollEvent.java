package io.binarskugga.event.impl;

import io.binarskugga.event.IEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MouseScrollEvent implements IEvent {
	public long window;
	public double scrollX, scrollY;
	public double x, y;
}
