package io.binarskugga.engine.light;

import io.binarskugga.math.Vec3f;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AmbientLight implements ILight {
	@Getter public Vec3f color = new Vec3f(1.0f, 1.0f, 1.0f);
	@Getter public float intensity = 0.3f;
}
