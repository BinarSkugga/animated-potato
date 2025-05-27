package io.binarskugga.content.components;

import io.binarskugga.content.BaseComponent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AnimationComponent extends BaseComponent {
	public boolean isYAxis = false;
	public boolean active = true;

	public float frameDuration = 1 / 10f; // In seconds
	public float accDelta = 0.0f;

	public int initialPosition = Integer.MIN_VALUE;
	public int frame = 0;
	public int maxFrames = 16;

	public int initialLine = Integer.MIN_VALUE;
	public int line = 0;

	public AnimationComponent(int maxFrames) {
		this.maxFrames = maxFrames;
	}
}
