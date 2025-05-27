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
public final class Vec3l implements IVector<Vec3l> {
  public long x;

  public long y;

  public long z;

  public Vec3l(long w) {
    this.x = w;
    this.y = w;
    this.z = w;
  }

  public Vec3l set(long x, long y, long z) {
    this.x = x;
    this.y = y;
    this.z = z;
    return this;
  }

  public Vec3l set(long w) {
    this.x = w;
    this.y = w;
    this.z = w;
    return this;
  }

  public Vec3l set(@NonNull Vec3l other) {
    this.x = other.x;
    this.y = other.y;
    this.z = other.z;
    return this;
  }

  @Contract(
      pure = true
  )
  public long[] toArray() {
    return new long[]{this.x, this.y, this.z};
  }

  @Contract(
      pure = true
  )
  public String toString() {
    return "[" + this.x + ", " + this.y + ", " + this.z + "]";
  }

  public Vec3l zero() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
    return this;
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l negate() {
    return this.mul(-1);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l add(long x, long y, long z) {
    return new Vec3l(this.x + x, this.y + y, this.z + z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l add(long w) {
    return new Vec3l(this.x + w, this.y + w, this.z + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l add(@NonNull Vec3l other) {
    return new Vec3l(x + other.x, y + other.y, z + other.z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l sub(long x, long y, long z) {
    return new Vec3l(this.x - x, this.y - y, this.z - z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l sub(long w) {
    return new Vec3l(this.x - w, this.y - w, this.z - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l sub(@NonNull Vec3l other) {
    return new Vec3l(x - other.x, y - other.y, z - other.z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l mul(long x, long y, long z) {
    return new Vec3l(this.x * x, this.y * y, this.z * z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l mul(float scalar) {
    return new Vec3l((long)(x * scalar), (long)(y * scalar), (long)(z * scalar));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l mul(@NonNull Vec3l other) {
    return new Vec3l(x * other.x, y * other.y, z * other.z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l div(long x, long y, long z) {
    return new Vec3l(this.x / x, this.y / y, this.z / z);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l div(float scalar) {
    return new Vec3l((long)(x / scalar), (long)(y / scalar), (long)(z / scalar));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l div(@NonNull Vec3l other) {
    return new Vec3l(x / other.x, y / other.y, z / other.z);
  }

  @Contract(
      pure = true
  )
  public long dot(@NonNull Vec3l other) {
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
  public double distance(long x, long y, long z, boolean squared) {
    double squaredDistance = ((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y)) + ((this.z - z) * (this.z - z));
    if(!squared) return sqrt(squaredDistance);
    return squaredDistance;
  }

  @Contract(
      pure = true
  )
  public double distance(long x, long y, long z) {
    return this.distance(x, y, z, false);
  }

  @Contract(
      pure = true
  )
  public double distance(@NonNull Vec3l other, boolean squared) {
    double squaredDistance = ((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y)) + ((this.z - other.z) * (this.z - other.z));
    if(!squared) return sqrt(squaredDistance);
    return squaredDistance;
  }

  @Contract(
      pure = true
  )
  public double distance(@NonNull Vec3l other) {
    return this.distance(other, false);
  }

  @NonNull
  @Contract(
      pure = true
  )
  public Vec3l normalize() {
    double vlength = this.length();
    return new Vec3l((long) (this.x / vlength), (long) (this.y / vlength), (long) (this.z / vlength));
  }

  @Contract(
      pure = true
  )
  public Vec3l cross(@NonNull Vec3l other) {
    	return new Vec3l(
    		this.y * other.z - this.z * other.y,
    		this.z * other.x - this.x * other.z,
    		this.x * other.y - this.y * other.x
    	);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec3l lerp(@NonNull Vec3l other, float t) {
    return new Vec3l((long) (this.x + (other.x - this.x) * t), (long) (this.y + (other.y - this.y) * t), (long) (this.z + (other.z - this.z) * t));
  }
}
