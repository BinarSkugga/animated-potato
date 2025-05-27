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
public final class Vec2l implements IVector<Vec2l> {
  public long x;

  public long y;

  public Vec2l(long w) {
    this.x = w;
    this.y = w;
  }

  public Vec2l set(long x, long y) {
    this.x = x;
    this.y = y;
    return this;
  }

  public Vec2l set(long w) {
    this.x = w;
    this.y = w;
    return this;
  }

  public Vec2l set(@NonNull Vec2l other) {
    this.x = other.x;
    this.y = other.y;
    return this;
  }

  @Contract(
      pure = true
  )
  public long[] toArray() {
    return new long[]{this.x, this.y};
  }

  @Contract(
      pure = true
  )
  public String toString() {
    return "[" + this.x + ", " + this.y + "]";
  }

  public Vec2l zero() {
    this.x = 0;
    this.y = 0;
    return this;
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l negate() {
    return this.mul(-1);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l add(long x, long y) {
    return new Vec2l(this.x + x, this.y + y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l add(long w) {
    return new Vec2l(this.x + w, this.y + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l add(@NonNull Vec2l other) {
    return new Vec2l(x + other.x, y + other.y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l sub(long x, long y) {
    return new Vec2l(this.x - x, this.y - y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l sub(long w) {
    return new Vec2l(this.x - w, this.y - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l sub(@NonNull Vec2l other) {
    return new Vec2l(x - other.x, y - other.y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l mul(long x, long y) {
    return new Vec2l(this.x * x, this.y * y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l mul(float scalar) {
    return new Vec2l((long)(x * scalar), (long)(y * scalar));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l mul(@NonNull Vec2l other) {
    return new Vec2l(x * other.x, y * other.y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l div(long x, long y) {
    return new Vec2l(this.x / x, this.y / y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l div(float scalar) {
    return new Vec2l((long)(x / scalar), (long)(y / scalar));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l div(@NonNull Vec2l other) {
    return new Vec2l(x / other.x, y / other.y);
  }

  @Contract(
      pure = true
  )
  public long dot(@NonNull Vec2l other) {
    return x * other.x + y * other.y;
  }

  @Contract(
      pure = true
  )
  public double length(boolean squared) {
    if(squared) return x * x + y * y;
    else return sqrt(x * x + y * y);
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
  public double distance(long x, long y, boolean squared) {
    double squaredDistance = ((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y));
    if(!squared) return sqrt(squaredDistance);
    return squaredDistance;
  }

  @Contract(
      pure = true
  )
  public double distance(long x, long y) {
    return this.distance(x, y, false);
  }

  @Contract(
      pure = true
  )
  public double distance(@NonNull Vec2l other, boolean squared) {
    double squaredDistance = ((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y));
    if(!squared) return sqrt(squaredDistance);
    return squaredDistance;
  }

  @Contract(
      pure = true
  )
  public double distance(@NonNull Vec2l other) {
    return this.distance(other, false);
  }

  @NonNull
  @Contract(
      pure = true
  )
  public Vec2l normalize() {
    double vlength = this.length();
    return new Vec2l((long) (this.x / vlength), (long) (this.y / vlength));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2l lerp(@NonNull Vec2l other, float t) {
    return new Vec2l((long) (this.x + (other.x - this.x) * t), (long) (this.y + (other.y - this.y) * t));
  }
}
