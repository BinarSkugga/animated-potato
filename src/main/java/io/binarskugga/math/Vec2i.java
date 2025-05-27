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
public final class Vec2i implements IVector<Vec2i> {
  public int x;

  public int y;

  public Vec2i(int w) {
    this.x = w;
    this.y = w;
  }

  public Vec2i set(int x, int y) {
    this.x = x;
    this.y = y;
    return this;
  }

  public Vec2i set(int w) {
    this.x = w;
    this.y = w;
    return this;
  }

  public Vec2i set(@NonNull Vec2i other) {
    this.x = other.x;
    this.y = other.y;
    return this;
  }

  @Contract(
      pure = true
  )
  public int[] toArray() {
    return new int[]{this.x, this.y};
  }

  @Contract(
      pure = true
  )
  public String toString() {
    return "[" + this.x + ", " + this.y + "]";
  }

  public Vec2i zero() {
    this.x = 0;
    this.y = 0;
    return this;
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i negate() {
    return this.mul(-1);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i add(int x, int y) {
    return new Vec2i(this.x + x, this.y + y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i add(int w) {
    return new Vec2i(this.x + w, this.y + w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i add(@NonNull Vec2i other) {
    return new Vec2i(x + other.x, y + other.y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i sub(int x, int y) {
    return new Vec2i(this.x - x, this.y - y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i sub(int w) {
    return new Vec2i(this.x - w, this.y - w);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i sub(@NonNull Vec2i other) {
    return new Vec2i(x - other.x, y - other.y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i mul(int x, int y) {
    return new Vec2i(this.x * x, this.y * y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i mul(float scalar) {
    return new Vec2i((int)(x * scalar), (int)(y * scalar));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i mul(@NonNull Vec2i other) {
    return new Vec2i(x * other.x, y * other.y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i div(int x, int y) {
    return new Vec2i(this.x / x, this.y / y);
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i div(float scalar) {
    return new Vec2i((int)(x / scalar), (int)(y / scalar));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i div(@NonNull Vec2i other) {
    return new Vec2i(x / other.x, y / other.y);
  }

  @Contract(
      pure = true
  )
  public int dot(@NonNull Vec2i other) {
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
  public float distance(int x, int y, boolean squared) {
    double squaredDistance = ((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y));
    if(!squared) return (float) sqrt(squaredDistance);
    return (float) squaredDistance;
  }

  @Contract(
      pure = true
  )
  public float distance(int x, int y) {
    return this.distance(x, y, false);
  }

  @Contract(
      pure = true
  )
  public float distance(@NonNull Vec2i other, boolean squared) {
    double squaredDistance = ((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y));
    if(!squared) return (float) sqrt(squaredDistance);
    return (float) squaredDistance;
  }

  @Contract(
      pure = true
  )
  public float distance(@NonNull Vec2i other) {
    return this.distance(other, false);
  }

  @NonNull
  @Contract(
      pure = true
  )
  public Vec2i normalize() {
    float vlength = this.length();
    return new Vec2i((int) (this.x / vlength), (int) (this.y / vlength));
  }

  @Contract(
      pure = true
  )
  @NonNull
  public Vec2i lerp(@NonNull Vec2i other, float t) {
    return new Vec2i((int) (this.x + (other.x - this.x) * t), (int) (this.y + (other.y - this.y) * t));
  }
}
