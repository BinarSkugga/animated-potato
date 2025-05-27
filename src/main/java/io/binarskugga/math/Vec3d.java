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
public final class Vec3d implements IVector<Vec3d> {
  public double x;

  public double y;

  public double z;

  public Vec3d(double w) {
    this.x = w;
    this.y = w;
    this.z = w;
  }

  public Vec3d set(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
    return this;
  }

  public Vec3d set(double w) {
    this.x = w;
    this.y = w;
    this.z = w;
    return this;
  }

  public Vec3d set(@NonNull Vec3d other) {
    this.x = other.x;
    this.y = other.y;
    this.z = other.z;
    return this;
  }

  @Contract(
      pure = true
  )
  public double[] toArray() {
    return new double[]{this.x, this.y, this.z};
  }

  @Contract(
      pure = true
  )
  public String toString() {
    return "[" + this.x + ", " + this.y + ", " + this.z + "]";
  }

  public Vec3d zero() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
    return this;
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d negate() {
    return this.mul(-1);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d add(double x, double y, double z) {
    return new Vec3d(this.x + x, this.y + y, this.z + z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d add(double w) {
    return new Vec3d(this.x + w, this.y + w, this.z + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d add(@NonNull Vec3d other) {
    return new Vec3d(x + other.x, y + other.y, z + other.z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d sub(double x, double y, double z) {
    return new Vec3d(this.x - x, this.y - y, this.z - z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d sub(double w) {
    return new Vec3d(this.x - w, this.y - w, this.z - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d sub(@NonNull Vec3d other) {
    return new Vec3d(x - other.x, y - other.y, z - other.z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d mul(double x, double y, double z) {
    return new Vec3d(this.x * x, this.y * y, this.z * z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d mul(float scalar) {
    return new Vec3d(x * scalar, y * scalar, z * scalar);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d mul(@NonNull Vec3d other) {
    return new Vec3d(x * other.x, y * other.y, z * other.z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d div(double x, double y, double z) {
    return new Vec3d(this.x / x, this.y / y, this.z / z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d div(float scalar) {
    return new Vec3d(x / scalar, y / scalar, z / scalar);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d div(@NonNull Vec3d other) {
    return new Vec3d(x / other.x, y / other.y, z / other.z);
  }

  @Contract(
      pure = true
  )
  public double dot(@NonNull Vec3d other) {
    return x * other.x + y * other.y + z * other.z;
  }

  @Contract(
      pure = true
  )
  public double length(boolean squared) {
    if(squared) return x * x + y * y + z * z;
    else return sqrt(x * x + y * y + z * z);
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
  public double distance(double x, double y, double z, boolean squared) {
    double squaredDistance = ((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y)) + ((this.z - z) * (this.z - z));
    if(!squared) return sqrt(squaredDistance);
    return squaredDistance;
  }

  @Contract(
      pure = true
  )
  public double distance(double x, double y, double z) {
    return this.distance(x, y, z, false);
  }

  @Contract(
      pure = true
  )
  public double distance(@NonNull Vec3d other, boolean squared) {
    double squaredDistance = ((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y)) + ((this.z - other.z) * (this.z - other.z));
    if(!squared) return sqrt(squaredDistance);
    return squaredDistance;
  }

  @Contract(
      pure = true
  )
  public double distance(@NonNull Vec3d other) {
    return this.distance(other, false);
  }

  @NonNull
  @Contract(
      pure = true
  )
  public Vec3d normalize() {
    double vlength = this.length();
    return new Vec3d((this.x / vlength), (this.y / vlength), (this.z / vlength));
  }

  @Contract(
      pure = true
  )
  public Vec3d cross(@NonNull Vec3d other) {
    	return new Vec3d(
    		this.y * other.z - this.z * other.y,
    		this.z * other.x - this.x * other.z,
    		this.x * other.y - this.y * other.x
    	);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3d lerp(@NonNull Vec3d other, float t) {
    return new Vec3d((this.x + (other.x - this.x) * t), (this.y + (other.y - this.y) * t), (this.z + (other.z - this.z) * t));
  }
}
