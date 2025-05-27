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
public final class Vec4l implements IVector<Vec4l> {
  public long x;

  public long y;

  public long z;

  public long w;

  public Vec4l(long w) {
    this.x = w;
    this.y = w;
    this.z = w;
    this.w = w;
  }

  public Vec4l set(long x, long y, long z, long w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
    return this;
  }

  public Vec4l set(long w) {
    this.x = w;
    this.y = w;
    this.z = w;
    this.w = w;
    return this;
  }

  public Vec4l set(@NonNull Vec4l other) {
    this.x = other.x;
    this.y = other.y;
    this.z = other.z;
    this.w = other.w;
    return this;
  }

  @Contract(
      pure = true
  )
  public long[] toArray() {
    return new long[]{this.x, this.y, this.z, this.w};
  }

  @Contract(
      pure = true
  )
  public String toString() {
    return "[" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + "]";
  }

  public Vec4l zero() {
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
  public Vec4l negate() {
    return this.mul(-1);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l add(long x, long y, long z, long w) {
    return new Vec4l(this.x + x, this.y + y, this.z + z, this.w + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l add(long w) {
    return new Vec4l(this.x + w, this.y + w, this.z + w, this.w + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l add(@NonNull Vec4l other) {
    return new Vec4l(x + other.x, y + other.y, z + other.z, w + other.w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l sub(long x, long y, long z, long w) {
    return new Vec4l(this.x - x, this.y - y, this.z - z, this.w - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l sub(long w) {
    return new Vec4l(this.x - w, this.y - w, this.z - w, this.w - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l sub(@NonNull Vec4l other) {
    return new Vec4l(x - other.x, y - other.y, z - other.z, w - other.w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l mul(long x, long y, long z, long w) {
    return new Vec4l(this.x * x, this.y * y, this.z * z, this.w * w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l mul(float scalar) {
    return new Vec4l((long)(x * scalar), (long)(y * scalar), (long)(z * scalar), (long)(w * scalar));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l mul(@NonNull Vec4l other) {
    return new Vec4l(x * other.x, y * other.y, z * other.z, w * other.w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l div(long x, long y, long z, long w) {
    return new Vec4l(this.x / x, this.y / y, this.z / z, this.w / w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l div(float scalar) {
    return new Vec4l((long)(x / scalar), (long)(y / scalar), (long)(z / scalar), (long)(w / scalar));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l div(@NonNull Vec4l other) {
    return new Vec4l(x / other.x, y / other.y, z / other.z, w / other.w);
  }

  @Contract(
      pure = true
  )
  public long dot(@NonNull Vec4l other) {
    return x * other.x + y * other.y + z * other.z + w * other.w;
  }

  @Contract(
      pure = true
  )
  public double length(boolean squared) {
    if(squared) return x * x + y * y + z * z + w * w;
    else return sqrt(x * x + y * y + z * z + w * w);
  }

  @Contract(
      pure = true
  )
  public double length() {
    return this.length(false);
  }

  @Contract(
      pure = true
  )
  public double distance(long x, long y, long z, long w, boolean squared) {
    double squaredDistance = ((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y)) + ((this.z - z) * (this.z - z)) + ((this.w - w) * (this.w - w));
    if(!squared) return sqrt(squaredDistance);
    return squaredDistance;
  }

  @Contract(
      pure = true
  )
  public double distance(long x, long y, long z, long w) {
    return this.distance(x, y, z, w, false);
  }

  @Contract(
      pure = true
  )
  public double distance(@NonNull Vec4l other, boolean squared) {
    double squaredDistance = ((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y)) + ((this.z - other.z) * (this.z - other.z)) + ((this.w - other.w) * (this.w - other.w));
    if(!squared) return sqrt(squaredDistance);
    return squaredDistance;
  }

  @Contract(
      pure = true
  )
  public double distance(@NonNull Vec4l other) {
    return this.distance(other, false);
  }

  @NonNull
  @Contract(
      pure = true
  )
  public Vec4l normalize() {
    double vlength = this.length();
    return new Vec4l((long) (this.x / vlength), (long) (this.y / vlength), (long) (this.z / vlength), (long) (this.w / vlength));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4l lerp(@NonNull Vec4l other, float t) {
    return new Vec4l((long) (this.x + (other.x - this.x) * t), (long) (this.y + (other.y - this.y) * t), (long) (this.z + (other.z - this.z) * t), (long) (this.w + (other.w - this.w) * t));
  }
}
