package io.binarskugga.math;

import static io.binarskugga.math.StaticMath.sqrt;

import java.lang.String;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Vec4f implements IVector<Vec4f> {
  public float x;

  public float y;

  public float z;

  public float w;

  public Vec4f(float w) {
    this.x = w;
    this.y = w;
    this.z = w;
    this.w = w;
  }

  public Vec4f set(float x, float y, float z, float w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
    return this;
  }

  public Vec4f set(float w) {
    this.x = w;
    this.y = w;
    this.z = w;
    this.w = w;
    return this;
  }

  public Vec4f set(@NonNull Vec4f other) {
    this.x = other.x;
    this.y = other.y;
    this.z = other.z;
    this.w = other.w;
    return this;
  }

  @Contract(
      pure = true
  )
  public float[] toArray() {
    return new float[]{this.x, this.y, this.z, this.w};
  }

  @Contract(
      pure = true
  )
  public String toString() {
    return "[" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + "]";
  }

  public Vec4f zero() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
    this.w = 0;
    return this;
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f negate() {
    return this.mul(-1);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f add(float x, float y, float z, float w) {
    return new Vec4f(this.x + x, this.y + y, this.z + z, this.w + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f add(float w) {
    return new Vec4f(this.x + w, this.y + w, this.z + w, this.w + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f add(@NonNull Vec4f other) {
    return new Vec4f(x + other.x, y + other.y, z + other.z, w + other.w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f sub(float x, float y, float z, float w) {
    return new Vec4f(this.x - x, this.y - y, this.z - z, this.w - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f sub(float w) {
    return new Vec4f(this.x - w, this.y - w, this.z - w, this.w - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f sub(@NonNull Vec4f other) {
    return new Vec4f(x - other.x, y - other.y, z - other.z, w - other.w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f mul(float x, float y, float z, float w) {
    return new Vec4f(this.x * x, this.y * y, this.z * z, this.w * w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f mul(float scalar) {
    return new Vec4f(x * scalar, y * scalar, z * scalar, w * scalar);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f mul(@NonNull Vec4f other) {
    return new Vec4f(x * other.x, y * other.y, z * other.z, w * other.w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f div(float x, float y, float z, float w) {
    return new Vec4f(this.x / x, this.y / y, this.z / z, this.w / w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f div(float scalar) {
    return new Vec4f(x / scalar, y / scalar, z / scalar, w / scalar);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f div(@NonNull Vec4f other) {
    return new Vec4f(x / other.x, y / other.y, z / other.z, w / other.w);
  }

  @Contract(
      pure = true
  )
  public float dot(@NonNull Vec4f other) {
    return x * other.x + y * other.y + z * other.z + w * other.w;
  }

  @Contract(
      pure = true
  )
  public float length(boolean squared) {
    if(squared) return x * x + y * y + z * z + w * w;
    else return sqrt(x * x + y * y + z * z + w * w);
  }

  @Contract(
      pure = true
  )
  public float length() {
    return this.length(false);
  }

  @Contract(
      pure = true
  )
  public float distance(float x, float y, float z, float w, boolean squared) {
    double squaredDistance = ((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y)) + ((this.z - z) * (this.z - z)) + ((this.w - w) * (this.w - w));
    if(!squared) return (float) sqrt(squaredDistance);
    return (float) squaredDistance;
  }

  @Contract(
      pure = true
  )
  public float distance(float x, float y, float z, float w) {
    return this.distance(x, y, z, w, false);
  }

  @Contract(
      pure = true
  )
  public float distance(@NonNull Vec4f other, boolean squared) {
    double squaredDistance = ((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y)) + ((this.z - other.z) * (this.z - other.z)) + ((this.w - other.w) * (this.w - other.w));
    if(!squared) return (float) sqrt(squaredDistance);
    return (float) squaredDistance;
  }

  @Contract(
      pure = true
  )
  public float distance(@NonNull Vec4f other) {
    return this.distance(other, false);
  }

  @NonNull
  @Contract(
      pure = true
  )
  public Vec4f normalize() {
    float vlength = this.length();
    return new Vec4f((this.x / vlength), (this.y / vlength), (this.z / vlength), (this.w / vlength));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4f lerp(@NonNull Vec4f other, float t) {
    return new Vec4f((this.x + (other.x - this.x) * t), (this.y + (other.y - this.y) * t), (this.z + (other.z - this.z) * t), (this.w + (other.w - this.w) * t));
  }
}
