package io.binarskugga.content.components;

import io.binarskugga.content.BaseComponent;
import io.binarskugga.math.Vec3f;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AmbientLightComponent extends BaseComponent {
	public Vec3f color = new Vec3f(1.0f, 1.0f, 1.0f);
	public float intensity = 0.3f;
}
