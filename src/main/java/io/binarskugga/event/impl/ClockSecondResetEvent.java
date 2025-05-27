package io.binarskugga.event.impl;

import io.binarskugga.event.IEvent;
import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class ClockSecondResetEvent implements IEvent {
	private final long secondInNanos = TimeUnit.SECONDS.toNanos(1);
	private final long milliInNanos = TimeUnit.MILLISECONDS.toNanos(1);

	public long time, delta;
	public int fps;

	public float deltaMS() {
		return this.delta / (float)this.milliInNanos;
	}

	public float deltaS() {
		return this.delta / (float)this.secondInNanos;
	}
}
