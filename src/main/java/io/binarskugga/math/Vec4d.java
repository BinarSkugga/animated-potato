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
public final class Vec4d implements IVector<Vec4d> {
  public double x;

  public double y;

  public double z;

  public double w;

  public Vec4d(double w) {
    this.x = w;
    this.y = w;
    this.z = w;
    this.w = w;
  }

  public Vec4d set(double x, double y, double z, double w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
    return this;
  }

  public Vec4d set(double w) {
    this.x = w;
    this.y = w;
    this.z = w;
    this.w = w;
    return this;
  }

  public Vec4d set(@NonNull Vec4d other) {
    this.x = other.x;
    this.y = other.y;
    this.z = other.z;
    this.w = other.w;
    return this;
  }

  @Contract(
      pure = true
  )
  public double[] toArray() {
    return new double[]{this.x, this.y, this.z, this.w};
  }

  @Contract(
      pure = true
  )
  public String toString() {
    return "[" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + "]";
  }

  public Vec4d zero() {
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
  public Vec4d negate() {
    return this.mul(-1);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d add(double x, double y, double z, double w) {
    return new Vec4d(this.x + x, this.y + y, this.z + z, this.w + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d add(double w) {
    return new Vec4d(this.x + w, this.y + w, this.z + w, this.w + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d add(@NonNull Vec4d other) {
    return new Vec4d(x + other.x, y + other.y, z + other.z, w + other.w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d sub(double x, double y, double z, double w) {
    return new Vec4d(this.x - x, this.y - y, this.z - z, this.w - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d sub(double w) {
    return new Vec4d(this.x - w, this.y - w, this.z - w, this.w - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d sub(@NonNull Vec4d other) {
    return new Vec4d(x - other.x, y - other.y, z - other.z, w - other.w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d mul(double x, double y, double z, double w) {
    return new Vec4d(this.x * x, this.y * y, this.z * z, this.w * w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d mul(float scalar) {
    return new Vec4d(x * scalar, y * scalar, z * scalar, w * scalar);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d mul(@NonNull Vec4d other) {
    return new Vec4d(x * other.x, y * other.y, z * other.z, w * other.w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d div(double x, double y, double z, double w) {
    return new Vec4d(this.x / x, this.y / y, this.z / z, this.w / w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d div(float scalar) {
    return new Vec4d(x / scalar, y / scalar, z / scalar, w / scalar);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d div(@NonNull Vec4d other) {
    return new Vec4d(x / other.x, y / other.y, z / other.z, w / other.w);
  }

  @Contract(
      pure = true
  )
  public double dot(@NonNull Vec4d other) {
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
  public double distance(double x, double y, double z, double w, boolean squared) {
    double squaredDistance = ((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y)) + ((this.z - z) * (this.z - z)) + ((this.w - w) * (this.w - w));
    if(!squared) return sqrt(squaredDistance);
    return squaredDistance;
  }

  @Contract(
      pure = true
  )
  public double distance(double x, double y, double z, double w) {
    return this.distance(x, y, z, w, false);
  }

  @Contract(
      pure = true
  )
  public double distance(@NonNull Vec4d other, boolean squared) {
    double squaredDistance = ((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y)) + ((this.z - other.z) * (this.z - other.z)) + ((this.w - other.w) * (this.w - other.w));
    if(!squared) return sqrt(squaredDistance);
    return squaredDistance;
  }

  @Contract(
      pure = true
  )
  public double distance(@NonNull Vec4d other) {
    return this.distance(other, false);
  }

  @NonNull
  @Contract(
      pure = true
  )
  public Vec4d normalize() {
    double vlength = this.length();
    return new Vec4d((this.x / vlength), (this.y / vlength), (this.z / vlength), (this.w / vlength));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec4d lerp(@NonNull Vec4d other, float t) {
    return new Vec4d((this.x + (other.x - this.x) * t), (this.y + (other.y - this.y) * t), (this.z + (other.z - this.z) * t), (this.w + (other.w - this.w) * t));
  }
}
