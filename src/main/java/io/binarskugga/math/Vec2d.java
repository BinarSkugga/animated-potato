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
public final class Vec2d implements IVector<Vec2d> {
  public double x;

  public double y;

  public Vec2d(double w) {
    this.x = w;
    this.y = w;
  }

  public Vec2d set(double x, double y) {
    this.x = x;
    this.y = y;
    return this;
  }

  public Vec2d set(double w) {
    this.x = w;
    this.y = w;
    return this;
  }

  public Vec2d set(@NonNull Vec2d other) {
    this.x = other.x;
    this.y = other.y;
    return this;
  }

  @Contract(
      pure = true
  )
  public double[] toArray() {
    return new double[]{this.x, this.y};
  }

  @Contract(
      pure = true
  )
  public String toString() {
    return "[" + this.x + ", " + this.y + "]";
  }

  public Vec2d zero() {
    this.x = 0;
    this.y = 0;
    return this;
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d negate() {
    return this.mul(-1);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d add(double x, double y) {
    return new Vec2d(this.x + x, this.y + y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d add(double w) {
    return new Vec2d(this.x + w, this.y + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d add(@NonNull Vec2d other) {
    return new Vec2d(x + other.x, y + other.y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d sub(double x, double y) {
    return new Vec2d(this.x - x, this.y - y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d sub(double w) {
    return new Vec2d(this.x - w, this.y - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d sub(@NonNull Vec2d other) {
    return new Vec2d(x - other.x, y - other.y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d mul(double x, double y) {
    return new Vec2d(this.x * x, this.y * y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d mul(float scalar) {
    return new Vec2d(x * scalar, y * scalar);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d mul(@NonNull Vec2d other) {
    return new Vec2d(x * other.x, y * other.y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d div(double x, double y) {
    return new Vec2d(this.x / x, this.y / y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d div(float scalar) {
    return new Vec2d(x / scalar, y / scalar);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d div(@NonNull Vec2d other) {
    return new Vec2d(x / other.x, y / other.y);
  }

  @Contract(
      pure = true
  )
  public double dot(@NonNull Vec2d other) {
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
  public double distance(double x, double y, boolean squared) {
    double squaredDistance = ((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y));
    if(!squared) return sqrt(squaredDistance);
    return squaredDistance;
  }

  @Contract(
      pure = true
  )
  public double distance(double x, double y) {
    return this.distance(x, y, false);
  }

  @Contract(
      pure = true
  )
  public double distance(@NonNull Vec2d other, boolean squared) {
    double squaredDistance = ((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y));
    if(!squared) return sqrt(squaredDistance);
    return squaredDistance;
  }

  @Contract(
      pure = true
  )
  public double distance(@NonNull Vec2d other) {
    return this.distance(other, false);
  }

  @NonNull
  @Contract(
      pure = true
  )
  public Vec2d normalize() {
    double vlength = this.length();
    return new Vec2d((this.x / vlength), (this.y / vlength));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2d lerp(@NonNull Vec2d other, float t) {
    return new Vec2d((this.x + (other.x - this.x) * t), (this.y + (other.y - this.y) * t));
  }
}
