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
public final class Vec3f implements IVector<Vec3f> {
  public float x;

  public float y;

  public float z;

  public Vec3f(float w) {
    this.x = w;
    this.y = w;
    this.z = w;
  }

  public Vec3f set(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
    return this;
  }

  public Vec3f set(float w) {
    this.x = w;
    this.y = w;
    this.z = w;
    return this;
  }

  public Vec3f set(@NonNull Vec3f other) {
    this.x = other.x;
    this.y = other.y;
    this.z = other.z;
    return this;
  }

  @Contract(
      pure = true
  )
  public float[] toArray() {
    return new float[]{this.x, this.y, this.z};
  }

  @Contract(
      pure = true
  )
  public String toString() {
    return "[" + this.x + ", " + this.y + ", " + this.z + "]";
  }

  public Vec3f zero() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
    return this;
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f negate() {
    return this.mul(-1);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f add(float x, float y, float z) {
    return new Vec3f(this.x + x, this.y + y, this.z + z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f add(float w) {
    return new Vec3f(this.x + w, this.y + w, this.z + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f add(@NonNull Vec3f other) {
    return new Vec3f(x + other.x, y + other.y, z + other.z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f sub(float x, float y, float z) {
    return new Vec3f(this.x - x, this.y - y, this.z - z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f sub(float w) {
    return new Vec3f(this.x - w, this.y - w, this.z - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f sub(@NonNull Vec3f other) {
    return new Vec3f(x - other.x, y - other.y, z - other.z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f mul(float x, float y, float z) {
    return new Vec3f(this.x * x, this.y * y, this.z * z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f mul(float scalar) {
    return new Vec3f(x * scalar, y * scalar, z * scalar);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f mul(@NonNull Vec3f other) {
    return new Vec3f(x * other.x, y * other.y, z * other.z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f div(float x, float y, float z) {
    return new Vec3f(this.x / x, this.y / y, this.z / z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f div(float scalar) {
    return new Vec3f(x / scalar, y / scalar, z / scalar);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f div(@NonNull Vec3f other) {
    return new Vec3f(x / other.x, y / other.y, z / other.z);
  }

  @Contract(
      pure = true
  )
  public float dot(@NonNull Vec3f other) {
    return x * other.x + y * other.y + z * other.z;
  }

  @Contract(
      pure = true
  )
  public float length(boolean squared) {
    if(squared) return x * x + y * y + z * z;
    else return sqrt(x * x + y * y + z * z);
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
  public float distance(float x, float y, float z, boolean squared) {
    double squaredDistance = ((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y)) + ((this.z - z) * (this.z - z));
    if(!squared) return (float) sqrt(squaredDistance);
    return (float) squaredDistance;
  }

  @Contract(
      pure = true
  )
  public float distance(float x, float y, float z) {
    return this.distance(x, y, z, false);
  }

  @Contract(
      pure = true
  )
  public float distance(@NonNull Vec3f other, boolean squared) {
    double squaredDistance = ((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y)) + ((this.z - other.z) * (this.z - other.z));
    if(!squared) return (float) sqrt(squaredDistance);
    return (float) squaredDistance;
  }

  @Contract(
      pure = true
  )
  public float distance(@NonNull Vec3f other) {
    return this.distance(other, false);
  }

  @NonNull
  @Contract(
      pure = true
  )
  public Vec3f normalize() {
    float vlength = this.length();
    return new Vec3f((this.x / vlength), (this.y / vlength), (this.z / vlength));
  }

  @Contract(
      pure = true
  )
  public Vec3f cross(@NonNull Vec3f other) {
    	return new Vec3f(
    		this.y * other.z - this.z * other.y,
    		this.z * other.x - this.x * other.z,
    		this.x * other.y - this.y * other.x
    	);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3f lerp(@NonNull Vec3f other, float t) {
    return new Vec3f((this.x + (other.x - this.x) * t), (this.y + (other.y - this.y) * t), (this.z + (other.z - this.z) * t));
  }
}
