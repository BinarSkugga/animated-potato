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
public final class Vec3i implements IVector<Vec3i> {
  public int x;

  public int y;

  public int z;

  public Vec3i(int w) {
    this.x = w;
    this.y = w;
    this.z = w;
  }

  public Vec3i set(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
    return this;
  }

  public Vec3i set(int w) {
    this.x = w;
    this.y = w;
    this.z = w;
    return this;
  }

  public Vec3i set(@NonNull Vec3i other) {
    this.x = other.x;
    this.y = other.y;
    this.z = other.z;
    return this;
  }

  @Contract(
      pure = true
  )
  public int[] toArray() {
    return new int[]{this.x, this.y, this.z};
  }

  @Contract(
      pure = true
  )
  public String toString() {
    return "[" + this.x + ", " + this.y + ", " + this.z + "]";
  }

  public Vec3i zero() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
    return this;
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i negate() {
    return this.mul(-1);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i add(int x, int y, int z) {
    return new Vec3i(this.x + x, this.y + y, this.z + z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i add(int w) {
    return new Vec3i(this.x + w, this.y + w, this.z + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i add(@NonNull Vec3i other) {
    return new Vec3i(x + other.x, y + other.y, z + other.z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i sub(int x, int y, int z) {
    return new Vec3i(this.x - x, this.y - y, this.z - z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i sub(int w) {
    return new Vec3i(this.x - w, this.y - w, this.z - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i sub(@NonNull Vec3i other) {
    return new Vec3i(x - other.x, y - other.y, z - other.z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i mul(int x, int y, int z) {
    return new Vec3i(this.x * x, this.y * y, this.z * z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i mul(float scalar) {
    return new Vec3i((int)(x * scalar), (int)(y * scalar), (int)(z * scalar));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i mul(@NonNull Vec3i other) {
    return new Vec3i(x * other.x, y * other.y, z * other.z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i div(int x, int y, int z) {
    return new Vec3i(this.x / x, this.y / y, this.z / z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i div(float scalar) {
    return new Vec3i((int)(x / scalar), (int)(y / scalar), (int)(z / scalar));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i div(@NonNull Vec3i other) {
    return new Vec3i(x / other.x, y / other.y, z / other.z);
  }

  @Contract(
      pure = true
  )
  public int dot(@NonNull Vec3i other) {
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
  public float distance(int x, int y, int z, boolean squared) {
    double squaredDistance = ((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y)) + ((this.z - z) * (this.z - z));
    if(!squared) return (float) sqrt(squaredDistance);
    return (float) squaredDistance;
  }

  @Contract(
      pure = true
  )
  public float distance(int x, int y, int z) {
    return this.distance(x, y, z, false);
  }

  @Contract(
      pure = true
  )
  public float distance(@NonNull Vec3i other, boolean squared) {
    double squaredDistance = ((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y)) + ((this.z - other.z) * (this.z - other.z));
    if(!squared) return (float) sqrt(squaredDistance);
    return (float) squaredDistance;
  }

  @Contract(
      pure = true
  )
  public float distance(@NonNull Vec3i other) {
    return this.distance(other, false);
  }

  @NonNull
  @Contract(
      pure = true
  )
  public Vec3i normalize() {
    float vlength = this.length();
    return new Vec3i((int) (this.x / vlength), (int) (this.y / vlength), (int) (this.z / vlength));
  }

  @Contract(
      pure = true
  )
  public Vec3i cross(@NonNull Vec3i other) {
    	return new Vec3i(
    		this.y * other.z - this.z * other.y,
    		this.z * other.x - this.x * other.z,
    		this.x * other.y - this.y * other.x
    	);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3i lerp(@NonNull Vec3i other, float t) {
    return new Vec3i((int) (this.x + (other.x - this.x) * t), (int) (this.y + (other.y - this.y) * t), (int) (this.z + (other.z - this.z) * t));
  }
}
