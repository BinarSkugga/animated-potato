package io.binarskugga.engine.rendering;

import io.binarskugga.engine.ILowLevelResource;
import io.binarskugga.math.Mat4f;
import io.binarskugga.math.Vec2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class MeshBatch implements ILowLevelResource {
	// Size of all the component for one instance: 2 floats for atlas position + 16 floats for transformations
	private static final int INSTANCE_SIZE = 18;
	private FloatBuffer instanceBuffer;

	private final int maxInstances;
	private final int[] indices;
	private final float[] vertices;

	private int vao;
	private int verticesVbo;
	private int instancedVbo;
	private int ebo;

	public MeshBatch(int[] indices, float[] vertices, int maxInstances) {
		this.maxInstances = maxInstances;
		this.indices = indices;
		this.vertices = vertices;
		this.instanceBuffer = BufferUtils.createFloatBuffer(this.maxInstances * INSTANCE_SIZE);
	}

	@Override
	public void initialize() {
		// Create VAO
		this.vao = glGenVertexArrays();
		glBindVertexArray(this.vao);

		// Create Vertices VBO
		this.verticesVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.verticesVbo);
		glBufferData(GL_ARRAY_BUFFER, this.vertices, GL_STATIC_DRAW);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
		glEnableVertexAttribArray(0);

		// Create Instanced VBO
		this.instancedVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.instancedVbo);
		glBufferData(GL_ARRAY_BUFFER, (long) this.maxInstances * INSTANCE_SIZE, GL_DYNAMIC_DRAW);

		int stride = INSTANCE_SIZE * Float.BYTES;

		// Atlas Position
		glVertexAttribPointer(1, 2, GL_FLOAT, false, stride, 0);
		glEnableVertexAttribArray(1);
		glVertexAttribDivisor(1,1);

		// Transformation
		glVertexAttribPointer(2, 4, GL_FLOAT, false, stride, 2 * Float.BYTES);
		glVertexAttribPointer(3, 4, GL_FLOAT, false, stride, (2 + 4) * Float.BYTES);
		glVertexAttribPointer(4, 4, GL_FLOAT, false, stride, (2 + 8) * Float.BYTES);
		glVertexAttribPointer(5, 4, GL_FLOAT, false, stride, (2 + 12) * Float.BYTES);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		glEnableVertexAttribArray(4);
		glEnableVertexAttribArray(5);
		glVertexAttribDivisor(2,1);
		glVertexAttribDivisor(3,1);
		glVertexAttribDivisor(4,1);
		glVertexAttribDivisor(5,1);

		// Create EBO
		this.ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.indices, GL_STATIC_DRAW);

		glBindVertexArray(0);
	}

	public void draw(int count) {
		glBindVertexArray(this.vao);
		glDrawElementsInstanced(GL_TRIANGLE_STRIP, this.indices.length, GL_UNSIGNED_INT, 0, count);
	}

	public void update(List<Vec2f> atlasPosition, List<Mat4f> transformations) {
		glBindBuffer(GL_ARRAY_BUFFER, this.instancedVbo);

		this.instanceBuffer.clear();
		for (int i = 0; i < atlasPosition.size(); i++) {
			Vec2f position = atlasPosition.get(i);
			this.instanceBuffer.put(position.x).put(position.y);

			Mat4f transformation = transformations.get(i);
			this.instanceBuffer.put(transformation.toArray());
		}
		this.instanceBuffer.flip();

		glBufferSubData(GL_ARRAY_BUFFER, 0, this.instanceBuffer);
	}

	@Override
	public void destroy() {
		if (this.instanceBuffer != null) {
			this.instanceBuffer.clear();
		}

		glDeleteVertexArrays(this.vao);
		glDeleteBuffers(this.verticesVbo);
		glDeleteBuffers(this.instancedVbo);
		glDeleteBuffers(this.ebo);
	}
}
