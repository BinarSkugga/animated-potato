package io.binarskugga.math;

import lombok.Getter;

import static io.binarskugga.math.StaticMath.cos;
import static io.binarskugga.math.StaticMath.sin;

public class Mat4f {
	@Getter private float m00, m01, m02, m03;
	@Getter private float m10, m11, m12, m13;
	@Getter private float m20, m21, m22, m23;
	@Getter private float m30, m31, m32, m33;

	public Mat4f() {
		this.identity();
	}

	public Mat4f identity() {
		this.m00 = 1.0f; this.m01 = 0.0f; this.m02 = 0.0f; this.m03 = 0.0f;
		this.m10 = 0.0f; this.m11 = 1.0f; this.m12 = 0.0f; this.m13 = 0.0f;
		this.m20 = 0.0f; this.m21 = 0.0f; this.m22 = 1.0f; this.m23 = 0.0f;
		this.m30 = 0.0f; this.m31 = 0.0f; this.m32 = 0.0f; this.m33 = 1.0f;
		return this;
	}

	public Mat4f translate(float x, float y, float z) {
		float n03 = this.m00 * x + this.m01 * y + this.m02 * z + this.m03;
		float n13 = this.m10 * x + this.m11 * y + this.m12 * z + this.m13;
		float n23 = this.m20 * x + this.m21 * y + this.m22 * z + this.m23;
		float n33 = this.m30 * x + this.m31 * y + this.m32 * z + this.m33;
		this.m03 = n03;
		this.m13 = n13;
		this.m23 = n23;
		this.m33 = n33;
		return this;
	}

	public Mat4f translate(Vec2f translation) {
		return this.translate(translation.x, translation.y, 0.0f);
	}

	public Mat4f translate(Vec3f translation) {
		return this.translate(translation.x, translation.y, translation.z);
	}

	public Mat4f scale(float x, float y, float z) {
		this.m00 *= x; this.m01 *= y; this.m02 *= z;
		this.m10 *= x; this.m11 *= y; this.m12 *= z;
		this.m20 *= x; this.m21 *= y; this.m22 *= z;
		this.m30 *= x; this.m31 *= y; this.m32 *= z;
		return this;
	}

	public Mat4f scale(Vec2f scalar) {
		return this.scale(scalar.x, scalar.y, 1.0f);
	}

	public Mat4f scale(Vec3f scalar) {
		return this.scale(scalar.x, scalar.y, scalar.z);
	}

	public Mat4f rotateZ(float angle) {
		float cos = cos(angle);
		float sin = sin(angle);
		float n00 = this.m00 * cos + this.m01 * sin;
		float n01 = this.m00 * -sin + this.m01 * cos;
		float n10 = this.m10 * cos + this.m11 * sin;
		float n11 = this.m10 * -sin + this.m11 * cos;
		float n20 = this.m20 * cos + this.m21 * sin;
		float n21 = this.m20 * -sin + this.m21 * cos;
		float n30 = this.m30 * cos + this.m31 * sin;
		float n31 = this.m30 * -sin + this.m31 * cos;
		this.m00 = n00; this.m01 = n01;
		this.m10 = n10; this.m11 = n11;
		this.m20 = n20; this.m21 = n21;
		this.m30 = n30; this.m31 = n31;
		return this;
	}

	public Mat4f mul(Mat4f other) {
		float n00 = this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20 + this.m03 * other.m30;
		float n01 = this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21 + this.m03 * other.m31;
		float n02 = this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22 + this.m03 * other.m32;
		float n03 = this.m00 * other.m03 + this.m01 * other.m13 + this.m02 * other.m23 + this.m03 * other.m33;

		float n10 = this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20 + this.m13 * other.m30;
		float n11 = this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21 + this.m13 * other.m31;
		float n12 = this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22 + this.m13 * other.m32;
		float n13 = this.m10 * other.m03 + this.m11 * other.m13 + this.m12 * other.m23 + this.m13 * other.m33;

		float n20 = this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20 + this.m23 * other.m30;
		float n21 = this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21 + this.m23 * other.m31;
		float n22 = this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22 + this.m23 * other.m32;
		float n23 = this.m20 * other.m03 + this.m21 * other.m13 + this.m22 * other.m23 + this.m23 * other.m33;

		float n30 = this.m30 * other.m00 + this.m31 * other.m10 + this.m32 * other.m20 + this.m33 * other.m30;
		float n31 = this.m30 * other.m01 + this.m31 * other.m11 + this.m32 * other.m21 + this.m33 * other.m31;
		float n32 = this.m30 * other.m02 + this.m31 * other.m12 + this.m32 * other.m22 + this.m33 * other.m32;
		float n33 = this.m30 * other.m03 + this.m31 * other.m13 + this.m32 * other.m23 + this.m33 * other.m33;

		this.m00 = n00; this.m01 = n01; this.m02 = n02; this.m03 = n03;
		this.m10 = n10; this.m11 = n11; this.m12 = n12; this.m13 = n13;
		this.m20 = n20; this.m21 = n21; this.m22 = n22; this.m23 = n23;
		this.m30 = n30; this.m31 = n31; this.m32 = n32; this.m33 = n33;
		return this;
	}

	public Vec2f transform(float x, float y) {
		float z = 0.0f;
		float w = 1.0f;

		float tx = this.m00 * x + this.m01 * y + this.m02 * z + this.m03 * w;
		float ty = this.m10 * x + this.m11 * y + this.m12 * z + this.m13 * w;
		return new Vec2f(tx, ty);
	}

	public Vec2f transform(Vec2f point) {
		return this.transform(point.x, point.y);
	}

	public Vec3f transform(float x, float y, float z) {
		float w = 1.0f;
		float tx = this.m00 * x + this.m01 * y + this.m02 * z + this.m03 * w;
		float ty = this.m10 * x + this.m11 * y + this.m12 * z + this.m13 * w;
		float tz = this.m20 * x + this.m21 * y + this.m22 * z + this.m23 * w;
		return new Vec3f(tx, ty, tz);
	}

	public Vec3f transform(Vec3f point) {
		return this.transform(point.x, point.y, point.z);
	}

	public Mat4f ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
		float tx = -(right + left) / (right - left);
		float ty = -(top + bottom) / (top - bottom);
		float tz = -(zFar + zNear) / (zFar - zNear);
		this.m00 = 2.0f / (right - left);
		this.m01 = 0.0f;
		this.m02 = 0.0f;
		this.m03 = tx;
		this.m10 = 0.0f;
		this.m11 = 2.0f / (top - bottom);
		this.m12 = 0.0f;
		this.m13 = ty;
		this.m20 = 0.0f;
		this.m21 = 0.0f;
		this.m22 = -2.0f / (zFar - zNear);
		this.m23 = tz;
		this.m30 = 0.0f;
		this.m31 = 0.0f;
		this.m32 = 0.0f;
		this.m33 = 1.0f;
		return this;
	}

	public float[] toArray() {
		return new float[] {
				this.m00, this.m10, this.m20, this.m30, // Column 0
				this.m01, this.m11, this.m21, this.m31, // Column 1
				this.m02, this.m12, this.m22, this.m32, // Column 2
				this.m03, this.m13, this.m23, this.m33  // Column 3
		};
	}
}
