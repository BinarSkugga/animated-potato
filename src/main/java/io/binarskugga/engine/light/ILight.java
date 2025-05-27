package io.binarskugga.engine.light;


import io.binarskugga.math.Vec3f;

public interface ILight {
	Vec3f color();
	float intensity();
}
