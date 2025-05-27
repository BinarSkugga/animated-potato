package io.binarskugga.content.components;

import io.binarskugga.content.BaseComponent;
import io.binarskugga.math.Vec2f;
import io.binarskugga.math.Vec3f;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LightComponent extends BaseComponent {
	public Vec3f offset;
	public Vec3f color;
	public float intensity;

	public float constant = 1.0f;
	public float linear = 0.045f;
	public float quadratic = 0.0075f;

	public boolean isFlickering = false;
	public float flickerSpeed = 0.7f;
	public float flickerAmplitude = 0.01f;

	public LightComponent(float intensity) {
		this.offset = new Vec3f();
		this.color = new Vec3f(1.0f, 1.0f, 1.0f);
		this.intensity = intensity;
	}

	public LightComponent(Vec3f color, float intensity) {
		this.offset = new Vec3f();
		this.color = color;
		this.intensity = intensity;
	}

	public LightComponent(Vec3f offset, Vec3f color, float intensity) {
		this.offset = offset;
		this.color = color;
		this.intensity = intensity;
	}

	public LightComponent(Vec3f offset, Vec3f color, float intensity, boolean flickering) {
		this.offset = offset;
		this.color = color;
		this.intensity = intensity;
		this.isFlickering = flickering;
	}
}
