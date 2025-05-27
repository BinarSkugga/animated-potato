package io.binarskugga.engine.rendering;

import io.binarskugga.math.Vec2f;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vertex {
	private Vec2f position;
	private Vec2f texCoords;

	public float[] toArray() {
		return new float[]{
				position.x, position.y,
				texCoords.x, texCoords.y
		};
	}

	public static int size() {
		return 4 * Float.BYTES;
	}
}
