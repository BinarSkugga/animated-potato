package io.binarskugga.engine.rendering;

import io.binarskugga.engine.Window;
import io.binarskugga.event.EventBus;
import io.binarskugga.event.ISubscriber;
import io.binarskugga.event.impl.MouseScrollEvent;
import io.binarskugga.event.impl.WindowResizeEvent;
import io.binarskugga.math.Mat4f;
import io.binarskugga.math.Vec2f;
import io.binarskugga.math.Vec3f;
import lombok.Getter;
import lombok.NonNull;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;


@Listener
public class Camera2D implements ISubscriber {
	@Getter private Vec3f position;
	@Getter private float zoom;
	private Window window;

	private int latestWidth;
	private int latestHeight;

	@Getter private Mat4f projection;

	public Camera2D(@NonNull Window window) {
		EventBus.global.subscribe(this);
		this.window = window;
		this.position = new Vec3f();
		this.zoom = 1.0f;
		this.projection = new Mat4f().identity();

		this.updateProjection(window.width(), window.height());
	}

	public void follow(Vec3f pos, float smoothing) {
		Vec3f delta = pos.sub(this.position).mul(smoothing);
		this.position = this.position.add(delta);
		this.updateProjection();
	}

	private void updateProjection(int width, int height) {
		// Calculate half extents, scaled by zoom
		float halfWidth = (width / 2f) * this.zoom;
		float halfHeight = (height / 2f) * this.zoom;

		// Center the projection around this.position
		float left = this.position.x() - halfWidth;
		float right = this.position.x() + halfWidth;
		float bottom = this.position.y() + halfHeight;
		float top = this.position.y() - halfHeight;

		// Set orthographic projection
		this.projection.identity().ortho(left, right, bottom, top, -100.0f, 100.0f);

		this.latestWidth = width;
		this.latestHeight = height;
	}

	private void updateProjection() {
		if (this.latestWidth == 0 || this.latestHeight == 0) {
			this.updateProjection(this.window.width(), this.window.height());
		} else {
			this.updateProjection(this.latestWidth, this.latestHeight);
		}
	}

	@Handler
	public void onWindowResize(WindowResizeEvent event) {
		this.updateProjection(event.width, event.height);
	}

	@Handler
	public void onScroll(MouseScrollEvent event) {
		this.zoom += (float) -event.scrollY * 0.1f;
		this.zoom = Math.max(this.zoom, 0.5f);
		this.zoom = Math.min(this.zoom, 2f);
		this.updateProjection();
	}
}
