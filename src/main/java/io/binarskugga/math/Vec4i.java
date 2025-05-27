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
public final class Vec4i implements IVector<Vec4i> {
  public int x;

  public int y;

  public int z;

  public int w;

  public Vec4i(int w) {
    this.x = w;
    this.y = w;
    this.z = w;
    this.w = w;
  }

  public Vec4i set(int x, int y, int z, int w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
    return this;
  }

  public Vec4i set(int w) {
    this.x = w;
    this.y = w;
    this.z = w;
    this.w = w;
    return this;
  }

  public Vec4i set(@NonNull Vec4i other) {
    this.x = other.x;
    this.y = other.y;
    this.z = other.z;
    this.w = other.w;
    return this;
  }

  @Contract(
      pure = true
  )
  public int[] toArray() {
    return new int[]{this.x, this.y, this.z, this.w};
  }

  @Contract(
      pure = true
  )
  public String toString() {
    return "[" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + "]";
  }

  public Vec4i zero() {
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
  public Vec4i negate() {
    return this.mul(-1);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i add(int x, int y, int z, int w) {
    return new Vec4i(this.x + x, this.y + y, this.z + z, this.w + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i add(int w) {
    return new Vec4i(this.x + w, this.y + w, this.z + w, this.w + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i add(@NonNull Vec4i other) {
    return new Vec4i(x + other.x, y + other.y, z + other.z, w + other.w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i sub(int x, int y, int z, int w) {
    return new Vec4i(this.x - x, this.y - y, this.z - z, this.w - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i sub(int w) {
    return new Vec4i(this.x - w, this.y - w, this.z - w, this.w - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i sub(@NonNull Vec4i other) {
    return new Vec4i(x - other.x, y - other.y, z - other.z, w - other.w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i mul(int x, int y, int z, int w) {
    return new Vec4i(this.x * x, this.y * y, this.z * z, this.w * w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i mul(float scalar) {
    return new Vec4i((int)(x * scalar), (int)(y * scalar), (int)(z * scalar), (int)(w * scalar));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i mul(@NonNull Vec4i other) {
    return new Vec4i(x * other.x, y * other.y, z * other.z, w * other.w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i div(int x, int y, int z, int w) {
    return new Vec4i(this.x / x, this.y / y, this.z / z, this.w / w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i div(float scalar) {
    return new Vec4i((int)(x / scalar), (int)(y / scalar), (int)(z / scalar), (int)(w / scalar));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i div(@NonNull Vec4i other) {
    return new Vec4i(x / other.x, y / other.y, z / other.z, w / other.w);
  }

  @Contract(
      pure = true
  )
  public int dot(@NonNull Vec4i other) {
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
  public float distance(int x, int y, int z, int w, boolean squared) {
    double squaredDistance = ((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y)) + ((this.z - z) * (this.z - z)) + ((this.w - w) * (this.w - w));
    if(!squared) return (float) sqrt(squaredDistance);
    return (float) squaredDistance;
  }

  @Contract(
      pure = true
  )
  public float distance(int x, int y, int z, int w) {
    return this.distance(x, y, z, w, false);
  }

  @Contract(
      pure = true
  )
  public float distance(@NonNull Vec4i other, boolean squared) {
    double squaredDistance = ((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y)) + ((this.z - other.z) * (this.z - other.z)) + ((this.w - other.w) * (this.w - other.w));
    if(!squared) return (float) sqrt(squaredDistance);
    return (float) squaredDistance;
  }

  @Contract(
      pure = true
  )
  public float distance(@NonNull Vec4i other) {
    return this.distance(other, false);
  }

  @NonNull
  @Contract(
      pure = true
  )
  public Vec4i normalize() {
    float vlength = this.length();
    return new Vec4i((int) (this.x / vlength), (int) (this.y / vlength), (int) (this.z / vlength), (int) (this.w / vlength));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4i lerp(@NonNull Vec4i other, float t) {
    return new Vec4i((int) (this.x + (other.x - this.x) * t), (int) (this.y + (other.y - this.y) * t), (int) (this.z + (other.z - this.z) * t), (int) (this.w + (other.w - this.w) * t));
  }
}
