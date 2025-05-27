package io.binarskugga.engine.light;

import io.binarskugga.math.Vec3f;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Light implements ILight {
	@Getter public Vec3f position;
	@Getter public Vec3f color;
	@Getter public float intensity;

	// Attenuation
	public float constant = 1.0f;
	public float linear = 0.045f;
	public float quadratic = 0.0075f;

	public Light(Vec3f position, Vec3f color, float intensity) {
		this.position = position;
		this.color = color;
		this.intensity = intensity;
	}

	public float[] toUBOData() {
		return new float[] {
				this.position.x, this.position.y, this.position.z, 0.0f,
				this.color.x, this.color.y, this.color.z,
				this.intensity, this.constant, this.linear, this.quadratic,
		};
	}
}
