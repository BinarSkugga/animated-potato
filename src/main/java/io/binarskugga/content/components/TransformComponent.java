package io.binarskugga.content.components;

import io.binarskugga.content.BaseComponent;
import io.binarskugga.math.Mat4f;
import io.binarskugga.math.Vec2f;
import io.binarskugga.math.Vec3f;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class TransformComponent extends BaseComponent {
	public Vec3f position = new Vec3f(0f, 0f, 0f);
	public Vec2f size = new Vec2f(32f, 32f);
	public float rotation = 0.0f;
	public Vec3f pivot = new Vec3f(0.5f, 0.5f, 0.0f);

	private Mat4f transformer = new Mat4f();

	public TransformComponent(Vec3f position, Vec2f size) {
		this.position = position;
		this.size = size;
	}

	public TransformComponent(Vec3f position, Vec2f size, float rotation) {
		this.position = position;
		this.size = size;
		this.rotation = rotation;
	}

	public Mat4f transformation() {
		Vec3f worldPivot = this.pivot.mul(this.size.x, this.size.y, 1.0f);
		return this.transformer.identity()
				.translate(this.position)
				.translate(worldPivot)
				.rotateZ(this.rotation)
				.translate(worldPivot.negate())
				.scale(this.size);
	}

	public Vec3f transform(Vec3f point) {
		Vec3f toTransform = point.div(this.size.x, this.size.y, 1.0f);
		return this.transformation().transform(toTransform);
	}
}
