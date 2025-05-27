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
public final class Vec2f implements IVector<Vec2f> {
  public float x;

  public float y;

  public Vec2f(float w) {
    this.x = w;
    this.y = w;
  }

  public Vec2f set(float x, float y) {
    this.x = x;
    this.y = y;
    return this;
  }

  public Vec2f set(float w) {
    this.x = w;
    this.y = w;
    return this;
  }

  public Vec2f set(@NonNull Vec2f other) {
    this.x = other.x;
    this.y = other.y;
    return this;
  }

  @Contract(
      pure = true
  )
  public float[] toArray() {
    return new float[]{this.x, this.y};
  }

  @Contract(
      pure = true
  )
  public String toString() {
    return "[" + this.x + ", " + this.y + "]";
  }

  public Vec2f zero() {
    this.x = 0;
    this.y = 0;
    return this;
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f negate() {
    return this.mul(-1);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f add(float x, float y) {
    return new Vec2f(this.x + x, this.y + y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f add(float w) {
    return new Vec2f(this.x + w, this.y + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f add(@NonNull Vec2f other) {
    return new Vec2f(x + other.x, y + other.y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f sub(float x, float y) {
    return new Vec2f(this.x - x, this.y - y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f sub(float w) {
    return new Vec2f(this.x - w, this.y - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f sub(@NonNull Vec2f other) {
    return new Vec2f(x - other.x, y - other.y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f mul(float x, float y) {
    return new Vec2f(this.x * x, this.y * y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f mul(float scalar) {
    return new Vec2f(x * scalar, y * scalar);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f mul(@NonNull Vec2f other) {
    return new Vec2f(x * other.x, y * other.y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f div(float x, float y) {
    return new Vec2f(this.x / x, this.y / y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f div(float scalar) {
    return new Vec2f(x / scalar, y / scalar);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f div(@NonNull Vec2f other) {
    return new Vec2f(x / other.x, y / other.y);
  }

  @Contract(
      pure = true
  )
  public float dot(@NonNull Vec2f other) {
    return x * other.x + y * other.y;
  }

  @Contract(
      pure = true
  )
  public float length(boolean squared) {
    if(squared) return x * x + y * y;
    else return sqrt(x * x + y * y);
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
  public float distance(float x, float y, boolean squared) {
    double squaredDistance = ((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y));
    if(!squared) return (float) sqrt(squaredDistance);
    return (float) squaredDistance;
  }

  @Contract(
      pure = true
  )
  public float distance(float x, float y) {
    return this.distance(x, y, false);
  }

  @Contract(
      pure = true
  )
  public float distance(@NonNull Vec2f other, boolean squared) {
    double squaredDistance = ((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y));
    if(!squared) return (float) sqrt(squaredDistance);
    return (float) squaredDistance;
  }

  @Contract(
      pure = true
  )
  public float distance(@NonNull Vec2f other) {
    return this.distance(other, false);
  }

  @NonNull
  @Contract(
      pure = true
  )
  public Vec2f normalize() {
    float vlength = this.length();
    return new Vec2f((this.x / vlength), (this.y / vlength));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2f lerp(@NonNull Vec2f other, float t) {
    return new Vec2f((this.x + (other.x - this.x) * t), (this.y + (other.y - this.y) * t));
  }
}
