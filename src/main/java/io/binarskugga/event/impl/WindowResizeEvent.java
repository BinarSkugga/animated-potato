package io.binarskugga.event.impl;

import io.binarskugga.event.IEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WindowResizeEvent implements IEvent {
	public long window;
	public int width, height;
}
