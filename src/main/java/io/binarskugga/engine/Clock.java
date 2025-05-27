package io.binarskugga.engine;

import io.binarskugga.event.EventBus;
import io.binarskugga.event.impl.ClockSecondResetEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

import static io.binarskugga.Constants.FPS_CAP;

public class Clock {
	public static final Clock global;
	static {
		global = new Clock();
	}

	private final long secondInNanos = TimeUnit.SECONDS.toNanos(1);
	private final long milliInNanos = TimeUnit.MILLISECONDS.toNanos(1);

	@Setter private int desiredFPS = FPS_CAP;
	@Getter private int fps = 0;
	@Getter private long delta = 0L;

	private long frameStart;
	private long accDelta = 0L;

	public Clock() {
		this.frameStart = this.time();
	}

	public long time() {
		return System.nanoTime();
	}

	public void tick() {
		long aimedDelta = 0;
		if(this.desiredFPS > 0)
			aimedDelta = this.secondInNanos / this.desiredFPS;

		long eventTime = 0L;
		long eventDelta = 0L;
		int eventFPS = 0;
		long renderDelta = 0L;

		try {
			long frameEnd = this.time();
			renderDelta = frameEnd - this.frameStart;

			eventTime = frameEnd;
			eventFPS = this.fps;

			if(renderDelta >= aimedDelta) {
				eventDelta = this.delta;
				this.delta = renderDelta;
				return;
			}

			if(this.desiredFPS != -1) {
				long millis = (aimedDelta - renderDelta) / this.milliInNanos;
				int nanos = (int) ((aimedDelta - renderDelta) % this.milliInNanos);
				Thread.sleep(millis, nanos);
			}

			eventDelta = aimedDelta;
			this.delta = aimedDelta;
		} catch(InterruptedException e) {
		} finally {
			this.fps++;
			this.accDelta += eventDelta;

			if(this.accDelta >= this.secondInNanos) {
				System.out.println("Render time: " + (float) renderDelta / milliInNanos);
				EventBus.global.publish(new ClockSecondResetEvent(eventTime, eventDelta, eventFPS));
				this.fps = 1;
				this.accDelta = this.accDelta - this.secondInNanos;
			}

			this.frameStart = this.time();
		}
	}
}

